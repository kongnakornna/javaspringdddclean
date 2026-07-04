-- ==============================================
-- ตาราง: t_job (ใบงานหลัก)
-- Main table for repair work orders.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_job (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_no VARCHAR(20) UNIQUE NOT NULL,                 -- เลขที่ใบงาน (เช่น JOB-2026-0001)
    customer_id UUID NOT NULL,
    car_id UUID NOT NULL,
    mechanic_id UUID NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',         -- OPEN, IN_PROGRESS, QUOTATION_PENDING, etc.
    start_date TIMESTAMP NOT NULL DEFAULT NOW(),
    end_date TIMESTAMP,
    symptom TEXT,
    diagnosis_note TEXT,
    mileage INTEGER,
    estimated_cost DECIMAL(15,2),
    actual_cost DECIMAL(15,2),
    priority VARCHAR(20) DEFAULT 'NORMAL',              -- NORMAL, URGENT, EMERGENCY
    -- Audit Fields (from GenericBusinessEntity)
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_job_customer ON t_job(customer_id);
CREATE INDEX IF NOT EXISTS idx_t_job_car ON t_job(car_id);
CREATE INDEX IF NOT EXISTS idx_t_job_mechanic ON t_job(mechanic_id);
CREATE INDEX IF NOT EXISTS idx_t_job_status ON t_job(status);
CREATE INDEX IF NOT EXISTS idx_t_job_whitelabel ON t_job(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_t_job_deleted ON t_job(deleted);

-- ==============================================
-- ตาราง: t_job_service (รายการบริการที่กำหนดในใบงาน)
-- Services/repairs specified in the job card.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_job_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    service_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    discount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_job_service_job ON t_job_service(job_id);
CREATE INDEX IF NOT EXISTS idx_t_job_service_whitelabel ON t_job_service(whitelabel_id);

-- ==============================================
-- ตาราง: t_job_part_sales (รายการอะไหล่ที่ขาย/ใช้ในใบงาน)
-- Parts used/sold in the job card.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_job_part_sales (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    part_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    discount DECIMAL(15,2) DEFAULT 0,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_job_part_sales_job ON t_job_part_sales(job_id);
CREATE INDEX IF NOT EXISTS idx_t_job_part_sales_whitelabel ON t_job_part_sales(whitelabel_id);

-- ==============================================
-- ตาราง: t_job_service_car_symptom (อาการของรถที่แจ้งเข้ามา)
-- Vehicle symptoms reported by customer.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_job_service_car_symptom (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    symptom_code VARCHAR(20),
    symptom_description TEXT NOT NULL,
    severity VARCHAR(20) DEFAULT 'MEDIUM',              -- LOW, MEDIUM, HIGH, CRITICAL
    reported_by VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_job_symptom_job ON t_job_service_car_symptom(job_id);

-- ==============================================
-- ตาราง: t_job_diag_trouble_code (รหัสข้อผิดพลาดจากการวินิจฉัย - OBD2/Diagnostic)
-- Diagnostic trouble codes (OBD2, etc.) found during inspection.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_job_diag_trouble_code (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    trouble_code VARCHAR(20) NOT NULL,
    description TEXT,
    system VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_job_diag_code_job ON t_job_diag_trouble_code(job_id);

-- ==============================================
-- ตาราง: t_job_status_history (ประวัติการเปลี่ยนสถานะใบงาน)
-- Track job status changes over time.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_job_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    from_status VARCHAR(30),
    to_status VARCHAR(30) NOT NULL,
    changed_by UUID NOT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_job_status_history_job ON t_job_status_history(job_id);
CREATE INDEX IF NOT EXISTS idx_t_job_status_history_changed ON t_job_status_history(changed_at);

-- ==============================================
-- ฟังก์ชันสร้างเลขที่ใบงานอัตโนมัติ (Auto-generate Job Number)
-- Function to generate unique job number with year and sequence.
-- ==============================================
CREATE OR REPLACE FUNCTION generate_job_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(job_no FROM 9) AS INTEGER)), 0) + 1
        FROM t_job
        WHERE job_no LIKE 'JOB-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.job_no := 'JOB-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- เรียกใช้ฟังก์ชันนี้ก่อน INSERT ทุกครั้ง / Apply before INSERT.
CREATE TRIGGER trg_generate_job_no
    BEFORE INSERT ON t_job
    FOR EACH ROW
    WHEN (NEW.job_no IS NULL OR NEW.job_no = '')
EXECUTE FUNCTION generate_job_no();
