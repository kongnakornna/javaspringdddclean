-- ==============================================
-- ตาราง: m_document_template (เทมเพลตเอกสาร)
-- Document template master data (JasperReports, etc.).
-- ==============================================
CREATE TABLE IF NOT EXISTS m_document_template (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_code VARCHAR(50) UNIQUE NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    template_type VARCHAR(20) NOT NULL,
    uploaded_at TIMESTAMP,
    file_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,
    file_size BIGINT,
    version INTEGER DEFAULT 1,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_default BOOLEAN DEFAULT FALSE,
    parameters JSONB,
    preview_image_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_template_code ON m_document_template(template_code);
CREATE INDEX IF NOT EXISTS idx_m_template_type ON m_document_template(template_type);
CREATE INDEX IF NOT EXISTS idx_m_template_active ON m_document_template(is_active);

-- ==============================================
-- ตาราง: t_document (เอกสารที่สร้างแล้ว)
-- Generated document records.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_document (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_no VARCHAR(30) UNIQUE NOT NULL,
    document_type VARCHAR(20) NOT NULL,
    document_sub_type VARCHAR(30),
    reference_type VARCHAR(30),
    reference_id UUID,
    template_id UUID REFERENCES m_document_template(id),
    file_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,
    file_size BIGINT,
    mime_type VARCHAR(50),
    status VARCHAR(20) DEFAULT 'GENERATED',
    generated_by UUID NOT NULL,
    generated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    uploaded_at TIMESTAMP,
    sent_by UUID,
    sent_at TIMESTAMP,
    sent_to_email VARCHAR(200),
    description TEXT,
    tags TEXT[],
    metadata JSONB,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_document_reference ON t_document(reference_type, reference_id);
CREATE INDEX IF NOT EXISTS idx_t_document_type ON t_document(document_type);
CREATE INDEX IF NOT EXISTS idx_t_document_sub_type ON t_document(document_sub_type);
CREATE INDEX IF NOT EXISTS idx_t_document_status ON t_document(status);
CREATE INDEX IF NOT EXISTS idx_t_document_generated_at ON t_document(generated_at);
CREATE INDEX IF NOT EXISTS idx_t_document_whitelabel ON t_document(whitelabel_id);

-- ==============================================
-- ฟังก์ชันสร้างเลขที่เอกสารอัตโนมัติ
-- Function to generate document number.
-- ==============================================
CREATE OR REPLACE FUNCTION generate_document_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(document_no FROM 9) AS INTEGER)), 0) + 1
        FROM t_document
        WHERE document_no LIKE 'DOC-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.document_no := 'DOC-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_document_no ON t_document;
CREATE TRIGGER trg_generate_document_no
BEFORE INSERT ON t_document
FOR EACH ROW
EXECUTE FUNCTION generate_document_no();

-- ==============================================
-- ตาราง: t_document_history (ประวัติเอกสาร)
-- Document history/audit trail.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_document_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_id UUID NOT NULL REFERENCES t_document(id) ON DELETE CASCADE,
    action VARCHAR(30) NOT NULL,
    performed_by UUID NOT NULL,
    performed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    details TEXT,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_doc_history_document ON t_document_history(document_id);
CREATE INDEX IF NOT EXISTS idx_t_doc_history_performed ON t_document_history(performed_at);

-- ==============================================
-- ตาราง: t_ocr_result (ผลลัพธ์ OCR)
-- OCR processing results.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_ocr_result (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_id UUID REFERENCES t_document(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    provider VARCHAR(30) NOT NULL,
    extracted_text TEXT,
    confidence_score DECIMAL(5,2),
    language VARCHAR(10),
    processing_time_ms INTEGER,
    metadata JSONB,
    status VARCHAR(20) DEFAULT 'PENDING',
    error_message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_ocr_result_document ON t_ocr_result(document_id);
CREATE INDEX IF NOT EXISTS idx_t_ocr_result_status ON t_ocr_result(status);
