-- ==============================================
-- ตาราง: m_email_template (เทมเพลตอีเมล)
-- Email template master data.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_email_template (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_code VARCHAR(50) UNIQUE NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body_html TEXT,
    body_text TEXT,
    from_email VARCHAR(100),
    from_name VARCHAR(100),
    category VARCHAR(50),
    language VARCHAR(10) DEFAULT 'th',
    version INTEGER DEFAULT 1,
    is_active BOOLEAN DEFAULT TRUE,
    is_default BOOLEAN DEFAULT FALSE,
    variables JSONB,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_email_template_code ON m_email_template(template_code);
CREATE INDEX IF NOT EXISTS idx_m_email_template_category ON m_email_template(category);
CREATE INDEX IF NOT EXISTS idx_m_email_template_language ON m_email_template(language);
CREATE INDEX IF NOT EXISTS idx_m_email_template_active ON m_email_template(is_active);

INSERT INTO m_email_template (template_code, template_name, subject, category, language, is_active, user_id, whitelabel_id)
VALUES
('QUOTATION_EMAIL', 'ใบเสนอราคา', 'ใบเสนอราคา #{quotationNo} - {customerName}', 'QUOTATION', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('INVOICE_EMAIL', 'ใบแจ้งหนี้', 'ใบแจ้งหนี้ #{invoiceNo} - {customerName}', 'INVOICE', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('PO_EMAIL', 'ใบสั่งซื้อ', 'ใบสั่งซื้อ #{poNo} - {supplierName}', 'PO', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('RECEIPT_EMAIL', 'ใบเสร็จรับเงิน', 'ใบเสร็จรับเงิน #{receiptNo} - {customerName}', 'RECEIPT', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('REMINDER_EMAIL', 'อีเมลแจ้งเตือน', 'แจ้งเตือน: {reminderSubject}', 'REMINDER', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- ==============================================
-- ตาราง: t_email_history (ประวัติการส่งอีเมล)
-- Email sending history.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_email_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email_id VARCHAR(50) UNIQUE NOT NULL,
    template_code VARCHAR(50),
    reference_type VARCHAR(30),
    reference_id UUID,
    from_email VARCHAR(100) NOT NULL,
    from_name VARCHAR(100),
    to_email VARCHAR(200) NOT NULL,
    to_name VARCHAR(100),
    cc_email VARCHAR(200),
    bcc_email VARCHAR(200),
    subject VARCHAR(255) NOT NULL,
    body_preview TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    priority VARCHAR(20) DEFAULT 'NORMAL',
    sent_at TIMESTAMP,
    error_message TEXT,
    retry_count INTEGER DEFAULT 0,
    attachments JSONB,
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_email_history_email_id ON t_email_history(email_id);
CREATE INDEX IF NOT EXISTS idx_t_email_history_reference ON t_email_history(reference_type, reference_id);
CREATE INDEX IF NOT EXISTS idx_t_email_history_template ON t_email_history(template_code);
CREATE INDEX IF NOT EXISTS idx_t_email_history_status ON t_email_history(status);
CREATE INDEX IF NOT EXISTS idx_t_email_history_to ON t_email_history(to_email);
CREATE INDEX IF NOT EXISTS idx_t_email_history_sent_at ON t_email_history(sent_at);
CREATE INDEX IF NOT EXISTS idx_t_email_history_whitelabel ON t_email_history(whitelabel_id);

-- ==============================================
-- ตาราง: t_email_queue (คิวอีเมล)
-- Email queue for asynchronous sending.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_email_queue (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email_id VARCHAR(50) UNIQUE NOT NULL,
    template_code VARCHAR(50),
    reference_type VARCHAR(30),
    reference_id UUID,
    from_email VARCHAR(100) NOT NULL,
    to_email VARCHAR(200) NOT NULL,
    to_name VARCHAR(100),
    subject VARCHAR(255) NOT NULL,
    body_html TEXT,
    body_text TEXT,
    attachments JSONB,
    priority VARCHAR(20) DEFAULT 'NORMAL',
    status VARCHAR(20) DEFAULT 'PENDING',
    retry_count INTEGER DEFAULT 0,
    max_retry INTEGER DEFAULT 3,
    next_attempt_at TIMESTAMP DEFAULT NOW(),
    error_message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_email_queue_status ON t_email_queue(status);
CREATE INDEX IF NOT EXISTS idx_t_email_queue_priority ON t_email_queue(priority);
CREATE INDEX IF NOT EXISTS idx_t_email_queue_next_attempt ON t_email_queue(next_attempt_at);
CREATE INDEX IF NOT EXISTS idx_t_email_queue_whitelabel ON t_email_queue(whitelabel_id);

-- ==============================================
-- ฟังก์ชันสร้าง Email ID อัตโนมัติ
-- Function to generate email ID.
-- ==============================================
CREATE OR REPLACE FUNCTION generate_email_id()
RETURNS TRIGGER AS $$
BEGIN
    NEW.email_id := 'EMAIL-' || TO_CHAR(NOW(), 'YYYYMMDD') || '-' || LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(email_id FROM 20) AS INTEGER)), 0) + 1
        FROM t_email_history
        WHERE email_id LIKE 'EMAIL-' || TO_CHAR(NOW(), 'YYYYMMDD') || '-%'
    ) AS TEXT), 4, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_email_id ON t_email_history;
CREATE TRIGGER trg_generate_email_id
BEFORE INSERT ON t_email_history
FOR EACH ROW
EXECUTE FUNCTION generate_email_id();

-- ==============================================
-- VIEW: v_email_analytics (สถิติการส่งอีเมล)
-- Email analytics view.
-- ==============================================
CREATE OR REPLACE VIEW v_email_analytics AS
SELECT
    DATE_TRUNC('day', created_at) AS day,
    COUNT(*) AS total_sent,
    SUM(CASE WHEN status = 'SENT' THEN 1 ELSE 0 END) AS success_count,
    SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) AS failed_count,
    SUM(CASE WHEN status = 'BOUNCED' THEN 1 ELSE 0 END) AS bounced_count,
    category,
    whitelabel_id
FROM t_email_history
GROUP BY DATE_TRUNC('day', created_at), category, whitelabel_id;
