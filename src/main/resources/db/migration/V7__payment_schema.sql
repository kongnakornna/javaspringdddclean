-- ==============================================
-- ตาราง: m_payment_method (วิธีการชำระเงิน)
-- Payment method master data.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_payment_method (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    method_code VARCHAR(20) UNIQUE NOT NULL,
    method_name VARCHAR(100) NOT NULL,
    method_name_en VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    requires_approval BOOLEAN DEFAULT FALSE,
    fee_percentage DECIMAL(5,2) DEFAULT 0,
    fee_fixed DECIMAL(15,2) DEFAULT 0,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

INSERT INTO m_payment_method (method_code, method_name, method_name_en, is_active, user_id, whitelabel_id)
VALUES 
    ('CASH', 'เงินสด', 'Cash', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    ('BANK_TRANSFER', 'โอนเงินผ่านธนาคาร', 'Bank Transfer', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    ('CREDIT_CARD', 'บัตรเครดิต', 'Credit Card', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    ('CHEQUE', 'เช็ค', 'Cheque', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    ('PROMPTPAY', 'พร้อมเพย์', 'PromptPay', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- ==============================================
-- ตาราง: t_payment (การชำระเงิน)
-- Main table for payment transactions.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_payment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payment_no VARCHAR(20) UNIQUE NOT NULL,
    invoice_id UUID NOT NULL,
    job_id UUID,
    customer_id UUID NOT NULL,
    payment_date TIMESTAMP NOT NULL DEFAULT NOW(),
    payment_method_id UUID NOT NULL REFERENCES m_payment_method(id) ON DELETE RESTRICT,
    amount DECIMAL(15,2) NOT NULL,
    amount_received DECIMAL(15,2) NOT NULL,
    change_amount DECIMAL(15,2) DEFAULT 0,
    currency VARCHAR(10) DEFAULT 'THB',
    exchange_rate DECIMAL(10,4) DEFAULT 1.0000,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    reference_number VARCHAR(50),
    bank_name VARCHAR(100),
    cheque_number VARCHAR(50),
    cheque_bank VARCHAR(100),
    cheque_date DATE,
    notes TEXT,
    received_by UUID NOT NULL,
    approved_by UUID,
    approved_at TIMESTAMP,
    refunded_amount DECIMAL(15,2) DEFAULT 0,
    refunded_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_payment_invoice ON t_payment(invoice_id);
CREATE INDEX IF NOT EXISTS idx_t_payment_customer ON t_payment(customer_id);
CREATE INDEX IF NOT EXISTS idx_t_payment_date ON t_payment(payment_date);
CREATE INDEX IF NOT EXISTS idx_t_payment_status ON t_payment(status);
CREATE INDEX IF NOT EXISTS idx_t_payment_method ON t_payment(payment_method_id);
CREATE INDEX IF NOT EXISTS idx_t_payment_whitelabel ON t_payment(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_t_payment_deleted ON t_payment(deleted);

CREATE OR REPLACE FUNCTION generate_payment_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(payment_no FROM 9) AS INTEGER)), 0) + 1
        FROM t_payment
        WHERE payment_no LIKE 'PAY-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.payment_no := 'PAY-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_payment_no ON t_payment;
CREATE TRIGGER trg_generate_payment_no
BEFORE INSERT ON t_payment
FOR EACH ROW
EXECUTE FUNCTION generate_payment_no();

-- ==============================================
-- ตาราง: t_receipt (ใบเสร็จรับเงิน)
-- Receipt document table.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_receipt (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receipt_no VARCHAR(20) UNIQUE NOT NULL,
    payment_id UUID NOT NULL REFERENCES t_payment(id) ON DELETE RESTRICT,
    invoice_id UUID NOT NULL,
    customer_id UUID NOT NULL,
    receipt_date TIMESTAMP NOT NULL DEFAULT NOW(),
    receipt_type VARCHAR(20) DEFAULT 'FULL',
    amount DECIMAL(15,2) NOT NULL,
    amount_in_words_th TEXT,
    amount_in_words_en TEXT,
    currency VARCHAR(10) DEFAULT 'THB',
    status VARCHAR(20) DEFAULT 'ISSUED',
    notes TEXT,
    issued_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_receipt_payment ON t_receipt(payment_id);
CREATE INDEX IF NOT EXISTS idx_t_receipt_invoice ON t_receipt(invoice_id);
CREATE INDEX IF NOT EXISTS idx_t_receipt_customer ON t_receipt(customer_id);
CREATE INDEX IF NOT EXISTS idx_t_receipt_date ON t_receipt(receipt_date);
CREATE INDEX IF NOT EXISTS idx_t_receipt_whitelabel ON t_receipt(whitelabel_id);

CREATE OR REPLACE FUNCTION generate_receipt_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(receipt_no FROM 9) AS INTEGER)), 0) + 1
        FROM t_receipt
        WHERE receipt_no LIKE 'RCP-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.receipt_no := 'RCP-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_receipt_no ON t_receipt;
CREATE TRIGGER trg_generate_receipt_no
BEFORE INSERT ON t_receipt
FOR EACH ROW
EXECUTE FUNCTION generate_receipt_no();

-- ==============================================
-- ตาราง: t_payment_history (ประวัติสถานะการชำระเงิน)
-- Track payment status changes.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_payment_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payment_id UUID NOT NULL REFERENCES t_payment(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID NOT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_payment_history_payment ON t_payment_history(payment_id);
CREATE INDEX IF NOT EXISTS idx_t_payment_history_changed ON t_payment_history(changed_at);
