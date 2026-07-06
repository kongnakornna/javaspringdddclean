-- =============================================================================
-- 1. MASTER DATA
-- =============================================================================

CREATE TABLE IF NOT EXISTS m_stock_location (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    location_code VARCHAR(20) UNIQUE NOT NULL,
    location_name VARCHAR(100) NOT NULL,
    location_type VARCHAR(20) DEFAULT 'SHELF',
    zone VARCHAR(50),
    capacity INTEGER,
    current_usage INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);
CREATE INDEX idx_m_stock_location_code ON m_stock_location(location_code);

CREATE TABLE IF NOT EXISTS m_part_master (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    part_code VARCHAR(50) UNIQUE NOT NULL,
    part_name VARCHAR(200) NOT NULL,
    part_name_en VARCHAR(200),
    category_id UUID,
    brand VARCHAR(50),
    model VARCHAR(100),
    oem_number VARCHAR(50),
    description TEXT,
    unit VARCHAR(20) DEFAULT 'PIECE',
    reorder_level INTEGER DEFAULT 0,
    reorder_quantity INTEGER DEFAULT 0,
    stock_quantity INTEGER DEFAULT 0,
    min_stock INTEGER DEFAULT 0,
    max_stock INTEGER DEFAULT 0,
    unit_cost DECIMAL(15,2),
    selling_price DECIMAL(15,2),
    location_id UUID REFERENCES m_stock_location(id),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    image_url TEXT,
    notes TEXT,
    last_updated_stock TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);
CREATE INDEX idx_m_part_master_code ON m_part_master(part_code);
CREATE INDEX idx_m_part_master_name ON m_part_master(part_name);
CREATE INDEX idx_m_part_master_status ON m_part_master(status);
CREATE INDEX idx_m_part_master_whitelabel ON m_part_master(whitelabel_id);

-- =============================================================================
-- 2. TRANSACTION TABLES
-- =============================================================================

CREATE TABLE IF NOT EXISTS t_inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    transaction_type VARCHAR(20) NOT NULL,
    reference_type VARCHAR(30),
    reference_id UUID,
    quantity INTEGER NOT NULL,
    previous_quantity INTEGER NOT NULL,
    new_quantity INTEGER NOT NULL,
    unit_cost DECIMAL(15,2),
    total_cost DECIMAL(15,2),
    transaction_date TIMESTAMP NOT NULL DEFAULT NOW(),
    note TEXT,
    performed_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID NOT NULL
);
CREATE INDEX idx_t_inventory_part ON t_inventory(part_id);
CREATE INDEX idx_t_inventory_type ON t_inventory(transaction_type);
CREATE INDEX idx_t_inventory_date ON t_inventory(transaction_date);

CREATE TABLE IF NOT EXISTS t_inventory_adjustment_header (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    adjustment_no VARCHAR(20) UNIQUE NOT NULL,
    adjustment_date TIMESTAMP NOT NULL DEFAULT NOW(),
    adjustment_type VARCHAR(20) NOT NULL,
    reason VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    description TEXT,
    approved_by UUID,
    approved_at TIMESTAMP,
    total_adjustment_value DECIMAL(15,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);
CREATE INDEX idx_t_inv_adj_header_status ON t_inventory_adjustment_header(status);

CREATE TABLE IF NOT EXISTS t_inventory_adjustment_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    adjustment_header_id UUID NOT NULL REFERENCES t_inventory_adjustment_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    quantity INTEGER NOT NULL,
    unit_cost DECIMAL(15,2),
    total_cost DECIMAL(15,2),
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_stocktake_header (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stocktake_no VARCHAR(20) UNIQUE NOT NULL,
    stocktake_date TIMESTAMP NOT NULL DEFAULT NOW(),
    status VARCHAR(20) DEFAULT 'DRAFT',
    started_by UUID,
    started_at TIMESTAMP,
    completed_by UUID,
    completed_at TIMESTAMP,
    total_discrepancy INTEGER DEFAULT 0,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);
CREATE INDEX idx_t_stocktake_header_status ON t_stocktake_header(status);

CREATE TABLE IF NOT EXISTS t_stocktake_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stocktake_header_id UUID NOT NULL REFERENCES t_stocktake_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    system_quantity INTEGER NOT NULL,
    counted_quantity INTEGER NOT NULL,
    discrepancy INTEGER GENERATED ALWAYS AS (counted_quantity - system_quantity) STORED,
    note TEXT,
    counted_by UUID,
    counted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_part_picking_request (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_no VARCHAR(20) UNIQUE NOT NULL,
    job_id UUID,
    quotation_id UUID,
    requested_date TIMESTAMP NOT NULL DEFAULT NOW(),
    requested_by UUID NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    priority VARCHAR(20) DEFAULT 'NORMAL',
    notes TEXT,
    picked_by UUID,
    picked_date TIMESTAMP,
    confirmed_by UUID,
    confirmed_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    whitelabel_id UUID
);
CREATE INDEX idx_t_picking_request_job ON t_part_picking_request(job_id);
CREATE INDEX idx_t_picking_request_status ON t_part_picking_request(status);

CREATE TABLE IF NOT EXISTS t_part_picking_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_request_id UUID NOT NULL REFERENCES t_part_picking_request(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    requested_quantity INTEGER NOT NULL,
    picked_quantity INTEGER DEFAULT 0,
    unit_price DECIMAL(15,2),
    total_price DECIMAL(15,2),
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

-- =============================================================================
-- 3. FIFO LAYER TABLE
-- =============================================================================

CREATE TABLE IF NOT EXISTS t_inventory_layer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL,
    unit_cost DECIMAL(15,2) NOT NULL,
    received_date TIMESTAMP NOT NULL DEFAULT NOW(),
    reference_type VARCHAR(30),
    reference_id UUID,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    whitelabel_id UUID NOT NULL
);
CREATE INDEX idx_t_inventory_layer_part ON t_inventory_layer(part_id);
CREATE INDEX idx_t_inventory_layer_received ON t_inventory_layer(received_date);

-- =============================================================================
-- 4. LOW STOCK ALERT TABLE
-- =============================================================================

CREATE TABLE IF NOT EXISTS t_inventory_alert_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    alert_date DATE NOT NULL DEFAULT CURRENT_DATE,
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE CASCADE,
    part_code VARCHAR(50) NOT NULL,
    part_name VARCHAR(200) NOT NULL,
    current_stock INTEGER NOT NULL,
    reorder_level INTEGER NOT NULL,
    reorder_quantity INTEGER NOT NULL,
    alert_sent BOOLEAN DEFAULT FALSE,
    alert_sent_at TIMESTAMP,
    resolved BOOLEAN DEFAULT FALSE,
    resolved_at TIMESTAMP,
    note TEXT,
    whitelabel_id UUID NOT NULL
);
CREATE INDEX idx_t_inv_alert_date ON t_inventory_alert_history(alert_date);
CREATE INDEX idx_t_inv_alert_part ON t_inventory_alert_history(part_id);

-- =============================================================================
-- 5. TRIGGERS
-- =============================================================================

CREATE OR REPLACE FUNCTION generate_adjustment_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(adjustment_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_inventory_adjustment_header WHERE adjustment_no LIKE 'ADJ-' || year_part || '-%';
    NEW.adjustment_no := 'ADJ-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS trg_generate_adjustment_no ON t_inventory_adjustment_header;
CREATE TRIGGER trg_generate_adjustment_no BEFORE INSERT ON t_inventory_adjustment_header
FOR EACH ROW EXECUTE FUNCTION generate_adjustment_no();

CREATE OR REPLACE FUNCTION generate_picking_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(picking_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_part_picking_request WHERE picking_no LIKE 'PK-' || year_part || '-%';
    NEW.picking_no := 'PK-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS trg_generate_picking_no ON t_part_picking_request;
CREATE TRIGGER trg_generate_picking_no BEFORE INSERT ON t_part_picking_request
FOR EACH ROW EXECUTE FUNCTION generate_picking_no();

CREATE OR REPLACE FUNCTION generate_stocktake_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(stocktake_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_stocktake_header WHERE stocktake_no LIKE 'ST-' || year_part || '-%';
    NEW.stocktake_no := 'ST-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
DROP TRIGGER IF EXISTS trg_generate_stocktake_no ON t_stocktake_header;
CREATE TRIGGER trg_generate_stocktake_no BEFORE INSERT ON t_stocktake_header
FOR EACH ROW EXECUTE FUNCTION generate_stocktake_no();

-- =============================================================================
-- 6. DEMO DATA
-- =============================================================================

INSERT INTO m_stock_location (id, location_code, location_name, location_type, zone, capacity, whitelabel_id) VALUES
('a0000001-0000-0000-0000-000000000001', 'A-01', 'ชั้นวาง A-01', 'SHELF', 'Zone A', 500, '00000000-0000-0000-0000-000000000001'),
('a0000001-0000-0000-0000-000000000002', 'A-02', 'ชั้นวาง A-02', 'SHELF', 'Zone A', 500, '00000000-0000-0000-0000-000000000001'),
('a0000001-0000-0000-0000-000000000003', 'B-01', 'ชั้นวาง B-01', 'RACK', 'Zone B', 1000, '00000000-0000-0000-0000-000000000001'),
('a0000001-0000-0000-0000-000000000004', 'B-02', 'ชั้นวาง B-02', 'RACK', 'Zone B', 1000, '00000000-0000-0000-0000-000000000001'),
('a0000001-0000-0000-0000-000000000005', 'WH-01', 'คลังสินค้าหลัก', 'WAREHOUSE', 'Main', 10000, '00000000-0000-0000-0000-000000000001');

INSERT INTO m_part_master (id, part_code, part_name, part_name_en, brand, model, unit, reorder_level, reorder_quantity, stock_quantity, min_stock, max_stock, unit_cost, selling_price, location_id, status, whitelabel_id) VALUES
('b0000001-0000-0000-0000-000000000001', 'OIL-001', 'น้ำมันเครื่อง 5W-30', 'Engine Oil 5W-30', 'Castrol', 'Passenger Car', 'LITER', 10, 20, 50, 5, 100, 120.00, 250.00, 'a0000001-0000-0000-0000-000000000001', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000002', 'OIL-002', 'น้ำมันเครื่อง 10W-40', 'Engine Oil 10W-40', 'Castrol', 'Passenger Car', 'LITER', 10, 20, 3, 5, 100, 100.00, 200.00, 'a0000001-0000-0000-0000-000000000001', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000003', 'FILTER-OIL-001', 'ไส้กรองน้ำมันเครื่อง', 'Oil Filter', 'Toyota', 'Corolla', 'PIECE', 5, 10, 25, 3, 50, 80.00, 180.00, 'a0000001-0000-0000-0000-000000000002', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000004', 'BRAKE-PAD-001', 'ผ้าเบรกหน้า', 'Front Brake Pad', 'Brembo', 'Sedan', 'SET', 3, 5, 15, 2, 30, 450.00, 890.00, 'a0000001-0000-0000-0000-000000000003', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000005', 'BATTERY-001', 'แบตเตอรี่ 12V 60Ah', 'Battery 12V 60Ah', 'GS', 'Passenger Car', 'PIECE', 2, 3, 8, 1, 15, 1500.00, 3200.00, 'a0000001-0000-0000-0000-000000000003', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000006', 'TIRE-001', 'ยางรถยนต์ 205/55R16', 'Tire 205/55R16', 'Michelin', 'Sedan', 'PIECE', 4, 8, 2, 2, 20, 2500.00, 4500.00, 'a0000001-0000-0000-0000-000000000004', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000007', 'SPARK-001', 'หัวเทียน', 'Spark Plug', 'NGK', 'Passenger Car', 'SET', 10, 20, 60, 5, 100, 60.00, 150.00, 'a0000001-0000-0000-0000-000000000005', 'ACTIVE', '00000000-0000-0000-0000-000000000001'),
('b0000001-0000-0000-0000-000000000008', 'COOLANT-001', 'น้ำยาหม้อน้ำ', 'Coolant', 'Toyota', 'Passenger Car', 'LITER', 10, 20, 40, 5, 80, 40.00, 100.00, 'a0000001-0000-0000-0000-000000000005', 'ACTIVE', '00000000-0000-0000-0000-000000000001');

INSERT INTO t_inventory (id, part_id, transaction_type, reference_type, reference_id, quantity, previous_quantity, new_quantity, unit_cost, total_cost, performed_by, whitelabel_id) VALUES
('c0000001-0000-0000-0000-000000000001', 'b0000001-0000-0000-0000-000000000001', 'RECEIVE', 'INITIAL', NULL, 50, 0, 50, 120.00, 6000.00, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('c0000001-0000-0000-0000-000000000002', 'b0000001-0000-0000-0000-000000000003', 'RECEIVE', 'INITIAL', NULL, 25, 0, 25, 80.00, 2000.00, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('c0000001-0000-0000-0000-000000000003', 'b0000001-0000-0000-0000-000000000004', 'RECEIVE', 'INITIAL', NULL, 15, 0, 15, 450.00, 6750.00, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

INSERT INTO t_inventory_layer (id, part_id, quantity, unit_cost, received_date, reference_type, is_active, whitelabel_id) VALUES
('d0000001-0000-0000-0000-000000000001', 'b0000001-0000-0000-0000-000000000001', 50, 120.00, NOW(), 'INITIAL', TRUE, '00000000-0000-0000-0000-000000000001'),
('d0000001-0000-0000-0000-000000000002', 'b0000001-0000-0000-0000-000000000003', 25, 80.00, NOW(), 'INITIAL', TRUE, '00000000-0000-0000-0000-000000000001'),
('d0000001-0000-0000-0000-000000000003', 'b0000001-0000-0000-0000-000000000004', 15, 450.00, NOW(), 'INITIAL', TRUE, '00000000-0000-0000-0000-000000000001');
