-- ==============================================
-- ตาราง: m_part_master (ข้อมูลอะไหล่หลัก)
-- Master table for parts and spare parts.
-- ==============================================
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
    location_id UUID,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    image_url TEXT,
    notes TEXT,
    last_updated_stock TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_part_master_code ON m_part_master(part_code);
CREATE INDEX IF NOT EXISTS idx_m_part_master_name ON m_part_master(part_name);
CREATE INDEX IF NOT EXISTS idx_m_part_master_category ON m_part_master(category_id);
CREATE INDEX IF NOT EXISTS idx_m_part_master_brand ON m_part_master(brand);
CREATE INDEX IF NOT EXISTS idx_m_part_master_oem ON m_part_master(oem_number);
CREATE INDEX IF NOT EXISTS idx_m_part_master_location ON m_part_master(location_id);
CREATE INDEX IF NOT EXISTS idx_m_part_master_status ON m_part_master(status);
CREATE INDEX IF NOT EXISTS idx_m_part_master_whitelabel ON m_part_master(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_m_part_master_deleted ON m_part_master(deleted);

-- ==============================================
-- ตาราง: m_stock_location (ตำแหน่งจัดเก็บสินค้า)
-- Stock location management.
-- ==============================================
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
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_stock_location_code ON m_stock_location(location_code);
CREATE INDEX IF NOT EXISTS idx_m_stock_location_zone ON m_stock_location(zone);

-- ==============================================
-- ตาราง: t_inventory (รายการเคลื่อนไหวสินค้าคงคลัง)
-- Inventory transaction (movement) log.
-- ==============================================
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

CREATE INDEX IF NOT EXISTS idx_t_inventory_part ON t_inventory(part_id);
CREATE INDEX IF NOT EXISTS idx_t_inventory_type ON t_inventory(transaction_type);
CREATE INDEX IF NOT EXISTS idx_t_inventory_reference ON t_inventory(reference_type, reference_id);
CREATE INDEX IF NOT EXISTS idx_t_inventory_date ON t_inventory(transaction_date);
CREATE INDEX IF NOT EXISTS idx_t_inventory_whitelabel ON t_inventory(whitelabel_id);

-- ==============================================
-- ตาราง: t_inventory_adjustment_header (หัวการปรับปรุงสต็อก)
-- Stock adjustment header.
-- ==============================================
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
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_inv_adj_header_no ON t_inventory_adjustment_header(adjustment_no);
CREATE INDEX IF NOT EXISTS idx_t_inv_adj_header_status ON t_inventory_adjustment_header(status);

CREATE OR REPLACE FUNCTION generate_adjustment_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(adjustment_no FROM 9) AS INTEGER)), 0) + 1
        FROM t_inventory_adjustment_header
        WHERE adjustment_no LIKE 'ADJ-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.adjustment_no := 'ADJ-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_adjustment_no ON t_inventory_adjustment_header;
CREATE TRIGGER trg_generate_adjustment_no
BEFORE INSERT ON t_inventory_adjustment_header
FOR EACH ROW
EXECUTE FUNCTION generate_adjustment_no();

-- ==============================================
-- ตาราง: t_inventory_adjustment_detail (รายละเอียดการปรับปรุงสต็อก)
-- Stock adjustment details.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_inventory_adjustment_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    adjustment_header_id UUID NOT NULL REFERENCES t_inventory_adjustment_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL,
    unit_cost DECIMAL(15,2),
    total_cost DECIMAL(15,2),
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_inv_adj_detail_header ON t_inventory_adjustment_detail(adjustment_header_id);
CREATE INDEX IF NOT EXISTS idx_t_inv_adj_detail_part ON t_inventory_adjustment_detail(part_id);

-- ==============================================
-- ตาราง: t_part_picking_request (คำขอเบิกอะไหล่)
-- Part picking request.
-- ==============================================
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
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_picking_request_job ON t_part_picking_request(job_id);
CREATE INDEX IF NOT EXISTS idx_t_picking_request_quotation ON t_part_picking_request(quotation_id);
CREATE INDEX IF NOT EXISTS idx_t_picking_request_status ON t_part_picking_request(status);

CREATE OR REPLACE FUNCTION generate_picking_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(picking_no FROM 8) AS INTEGER)), 0) + 1
        FROM t_part_picking_request
        WHERE picking_no LIKE 'PK-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.picking_no := 'PK-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_picking_no ON t_part_picking_request;
CREATE TRIGGER trg_generate_picking_no
BEFORE INSERT ON t_part_picking_request
FOR EACH ROW
EXECUTE FUNCTION generate_picking_no();

-- ==============================================
-- ตาราง: t_part_picking_detail (รายละเอียดคำขอเบิก)
-- Part picking details.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_part_picking_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_request_id UUID NOT NULL REFERENCES t_part_picking_request(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    requested_quantity INTEGER NOT NULL,
    picked_quantity INTEGER DEFAULT 0,
    unit_price DECIMAL(15,2),
    total_price DECIMAL(15,2),
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_picking_detail_request ON t_part_picking_detail(picking_request_id);
CREATE INDEX IF NOT EXISTS idx_t_picking_detail_part ON t_part_picking_detail(part_id);

-- ==============================================
-- ตาราง: t_stocktake_header (หัวการตรวจนับสต็อก)
-- Stock take header.
-- ==============================================
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
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_stocktake_header_status ON t_stocktake_header(status);

CREATE OR REPLACE FUNCTION generate_stocktake_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(stocktake_no FROM 8) AS INTEGER)), 0) + 1
        FROM t_stocktake_header
        WHERE stocktake_no LIKE 'ST-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.stocktake_no := 'ST-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_stocktake_no ON t_stocktake_header;
CREATE TRIGGER trg_generate_stocktake_no
BEFORE INSERT ON t_stocktake_header
FOR EACH ROW
EXECUTE FUNCTION generate_stocktake_no();

-- ==============================================
-- ตาราง: t_stocktake_detail (รายละเอียดการตรวจนับ)
-- Stock take details.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_stocktake_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stocktake_header_id UUID NOT NULL REFERENCES t_stocktake_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    system_quantity INTEGER NOT NULL,
    counted_quantity INTEGER NOT NULL,
    discrepancy INTEGER DEFAULT 0,
    note TEXT,
    counted_by UUID,
    counted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_stocktake_detail_header ON t_stocktake_detail(stocktake_header_id);
CREATE INDEX IF NOT EXISTS idx_t_stocktake_detail_part ON t_stocktake_detail(part_id);
