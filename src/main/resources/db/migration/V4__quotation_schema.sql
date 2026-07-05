-- ==============================================
-- ตาราง: t_quotation (ใบเสนอราคา)
-- Main table for quotation documents.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_quotation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_no VARCHAR(20) UNIQUE NOT NULL,
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE RESTRICT,
    customer_id UUID NOT NULL REFERENCES m_customer(id) ON DELETE RESTRICT,
    quotation_date TIMESTAMP NOT NULL DEFAULT NOW(),
    expiry_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    subtotal DECIMAL(15,2) NOT NULL DEFAULT 0,
    tax_rate DECIMAL(5,2) DEFAULT 7.00,
    tax_amount DECIMAL(15,2) DEFAULT 0,
    discount_type VARCHAR(20),
    discount_value DECIMAL(15,2) DEFAULT 0,
    total DECIMAL(15,2) NOT NULL DEFAULT 0,
    amount_in_words_th TEXT,
    amount_in_words_en TEXT,
    currency VARCHAR(10) DEFAULT 'THB',
    exchange_rate DECIMAL(10,4) DEFAULT 1.0000,
    notes TEXT,
    terms_and_conditions TEXT,
    approved_by UUID REFERENCES m_user(id),
    approved_at TIMESTAMP,
    rejected_reason TEXT,
    converted_to_po BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_quotation_job ON t_quotation(job_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_customer ON t_quotation(customer_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_status ON t_quotation(status);
CREATE INDEX IF NOT EXISTS idx_t_quotation_date ON t_quotation(quotation_date);
CREATE INDEX IF NOT EXISTS idx_t_quotation_whitelabel ON t_quotation(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_deleted ON t_quotation(deleted);

-- ==============================================
-- ฟังก์ชันสร้างเลขที่ใบเสนอราคาอัตโนมัติ
-- Function to generate unique quotation number.
-- ==============================================
CREATE OR REPLACE FUNCTION generate_quotation_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(quotation_no FROM 8) AS INTEGER)), 0) + 1
        FROM t_quotation
        WHERE quotation_no LIKE 'QT-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.quotation_no := 'QT-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_quotation_no ON t_quotation;
CREATE TRIGGER trg_generate_quotation_no
BEFORE INSERT ON t_quotation
FOR EACH ROW
EXECUTE FUNCTION generate_quotation_no();

-- ==============================================
-- ตาราง: t_quotation_part (รายการอะไหล่ในใบเสนอราคา)
-- Parts listed in the quotation.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_quotation_part (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_id UUID NOT NULL REFERENCES t_quotation(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) NOT NULL DEFAULT 0,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) NOT NULL DEFAULT 0,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_quotation_part_quotation ON t_quotation_part(quotation_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_part_part ON t_quotation_part(part_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_part_whitelabel ON t_quotation_part(whitelabel_id);

-- ==============================================
-- ตาราง: t_quotation_service (รายการบริการในใบเสนอราคา)
-- Services listed in the quotation.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_quotation_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_id UUID NOT NULL REFERENCES t_quotation(id) ON DELETE CASCADE,
    service_id UUID NOT NULL REFERENCES m_service(id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) NOT NULL DEFAULT 0,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) NOT NULL DEFAULT 0,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_quotation_service_quotation ON t_quotation_service(quotation_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_service_service ON t_quotation_service(service_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_service_whitelabel ON t_quotation_service(whitelabel_id);

-- ==============================================
-- ตาราง: t_quotation_status_history (ประวัติสถานะ)
-- Track quotation status changes over time.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_quotation_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_id UUID NOT NULL REFERENCES t_quotation(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID NOT NULL REFERENCES m_user(id) ON DELETE RESTRICT,
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_quotation_status_history_quotation ON t_quotation_status_history(quotation_id);
CREATE INDEX IF NOT EXISTS idx_t_quotation_status_history_changed ON t_quotation_status_history(changed_at);
