-- ==============================================
-- ตาราง: m_customer (ข้อมูลลูกค้า)
-- Master table for customer information.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_customer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_code VARCHAR(20) UNIQUE NOT NULL,         -- รหัสลูกค้า (เช่น CUST-2026-001)
    full_name VARCHAR(200) NOT NULL,                   -- ชื่อ-นามสกุล หรือชื่อบริษัท
    display_name VARCHAR(200),                         -- ชื่อที่ใช้แสดง
    customer_type VARCHAR(20) NOT NULL DEFAULT 'INDIVIDUAL', -- INDIVIDUAL, CORPORATE
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',      -- ACTIVE, INACTIVE, BLACKLISTED
    tax_id VARCHAR(20),                                -- เลขประจำตัวผู้เสียภาษี (TIN)
    email VARCHAR(100),
    phone_number VARCHAR(20) NOT NULL,
    secondary_phone VARCHAR(20),
    address TEXT,
    province VARCHAR(100),
    city VARCHAR(100),
    district VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(50) DEFAULT 'Thailand',
    contact_person VARCHAR(100),                       -- ผู้ติดต่อ (กรณีเป็นนิติบุคคล)
    contact_phone VARCHAR(20),
    notes TEXT,
    last_visit_date TIMESTAMP,                         -- วันที่มาใช้บริการล่าสุด
    total_visit_count INTEGER DEFAULT 0,               -- จำนวนครั้งที่มาใช้บริการทั้งหมด
    total_spent DECIMAL(15,2) DEFAULT 0,               -- ยอดเงินที่ใช้บริการทั้งหมด
    -- Audit Fields (from GenericBusinessEntity)
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_customer_phone ON m_customer(phone_number);
CREATE INDEX IF NOT EXISTS idx_m_customer_email ON m_customer(email);
CREATE INDEX IF NOT EXISTS idx_m_customer_tax_id ON m_customer(tax_id);
CREATE INDEX IF NOT EXISTS idx_m_customer_code ON m_customer(customer_code);
CREATE INDEX IF NOT EXISTS idx_m_customer_status ON m_customer(status);
CREATE INDEX IF NOT EXISTS idx_m_customer_whitelabel ON m_customer(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_m_customer_deleted ON m_customer(deleted);

-- ==============================================
-- ฟังก์ชันสร้างรหัสลูกค้าอัตโนมัติ (Auto-generate Customer Code)
-- Function to generate unique customer code with year and sequence.
-- ==============================================
CREATE OR REPLACE FUNCTION generate_customer_code()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(customer_code FROM 10) AS INTEGER)), 0) + 1
        FROM m_customer
        WHERE customer_code LIKE 'CUST-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.customer_code := 'CUST-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_customer_code ON m_customer;
CREATE TRIGGER trg_generate_customer_code
BEFORE INSERT ON m_customer
FOR EACH ROW
EXECUTE FUNCTION generate_customer_code();

-- ==============================================
-- ตาราง: m_car (ข้อมูลรถยนต์)
-- Master table for vehicle information.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_car (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL REFERENCES m_customer(id) ON DELETE CASCADE,
    license_plate VARCHAR(20) UNIQUE NOT NULL,        -- ทะเบียนรถ
    province VARCHAR(50),                              -- จังหวัดที่จดทะเบียน
    brand VARCHAR(50) NOT NULL,                       -- ยี่ห้อ (Toyota, Honda, etc.)
    model VARCHAR(100) NOT NULL,                      -- รุ่น (Camry, Accord, etc.)
    sub_model VARCHAR(100),                           -- รุ่นย่อย (2.0 Hybrid, etc.)
    year INTEGER,                                     -- ปีที่ผลิต
    color VARCHAR(30),
    engine_number VARCHAR(50),
    chassis_number VARCHAR(50),                       -- เลขตัวถัง (VIN)
    fuel_type VARCHAR(20),                            -- GASOLINE, DIESEL, EV, HYBRID, LPG, CNG
    transmission_type VARCHAR(20),                    -- MANUAL, AUTOMATIC, CVT, DCT
    engine_cc INTEGER,                                -- ขนาดเครื่องยนต์ (cc)
    seating_capacity INTEGER,                         -- จำนวนที่นั่ง
    mileage INTEGER DEFAULT 0,                        -- ระยะทางสะสม (กม.)
    last_service_date TIMESTAMP,                      -- วันที่เข้ารับบริการล่าสุด
    next_service_mileage INTEGER,                     -- ระยะทางที่ต้องเข้ารับบริการครั้งถัดไป
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    -- Audit Fields
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_car_customer ON m_car(customer_id);
CREATE INDEX IF NOT EXISTS idx_m_car_license_plate ON m_car(license_plate);
CREATE INDEX IF NOT EXISTS idx_m_car_brand ON m_car(brand);
CREATE INDEX IF NOT EXISTS idx_m_car_whitelabel ON m_car(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_m_car_deleted ON m_car(deleted);

-- ==============================================
-- ตาราง: m_car_service_history (ประวัติการซ่อมบำรุงของรถ)
-- Vehicle service history summary (cached/aggregated data for quick display).
-- NOTE: Requires t_job table from V2 migration.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_car_service_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    car_id UUID NOT NULL REFERENCES m_car(id) ON DELETE CASCADE,
    job_id UUID NOT NULL,                             -- FK to t_job (cannot use REFERENCES until t_job exists)
    service_date TIMESTAMP NOT NULL,
    service_type VARCHAR(50),                         -- ชนิดงาน (OIL_CHANGE, BRAKE, etc.)
    description TEXT,
    total_cost DECIMAL(15,2),
    mileage_at_service INTEGER,
    mechanic_name VARCHAR(100),
    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_car_service_history_car ON m_car_service_history(car_id);
CREATE INDEX IF NOT EXISTS idx_m_car_service_history_date ON m_car_service_history(service_date);
