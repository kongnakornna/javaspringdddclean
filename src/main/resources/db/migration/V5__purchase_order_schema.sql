-- ==============================================
-- ตาราง: t_purchase_order_header (หัวใบสั่งซื้อ)
-- Main table for purchase order documents.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_purchase_order_header (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    po_no VARCHAR(20) UNIQUE NOT NULL,
    quotation_id UUID REFERENCES t_quotation(id),
    job_id UUID REFERENCES t_job(id),
    supplier_id UUID NOT NULL REFERENCES m_supplier(id) ON DELETE RESTRICT,
    po_date TIMESTAMP NOT NULL DEFAULT NOW(),
    expected_delivery_date TIMESTAMP,
    actual_delivery_date TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    subtotal DECIMAL(15,2) NOT NULL DEFAULT 0,
    tax_rate DECIMAL(5,2) DEFAULT 7.00,
    tax_amount DECIMAL(15,2) DEFAULT 0,
    discount_type VARCHAR(20),
    discount_value DECIMAL(15,2) DEFAULT 0,
    total DECIMAL(15,2) NOT NULL DEFAULT 0,
    currency VARCHAR(10) DEFAULT 'THB',
    exchange_rate DECIMAL(10,4) DEFAULT 1.0000,
    shipping_cost DECIMAL(15,2) DEFAULT 0,
    payment_terms TEXT,
    delivery_address TEXT,
    notes TEXT,
    terms_and_conditions TEXT,
    sent_at TIMESTAMP,
    confirmed_at TIMESTAMP,
    received_by UUID REFERENCES m_user(id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_po_header_supplier ON t_purchase_order_header(supplier_id);
CREATE INDEX IF NOT EXISTS idx_t_po_header_quotation ON t_purchase_order_header(quotation_id);
CREATE INDEX IF NOT EXISTS idx_t_po_header_job ON t_purchase_order_header(job_id);
CREATE INDEX IF NOT EXISTS idx_t_po_header_status ON t_purchase_order_header(status);
CREATE INDEX IF NOT EXISTS idx_t_po_header_date ON t_purchase_order_header(po_date);
CREATE INDEX IF NOT EXISTS idx_t_po_header_whitelabel ON t_purchase_order_header(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_t_po_header_deleted ON t_purchase_order_header(deleted);

CREATE OR REPLACE FUNCTION generate_po_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(po_no FROM 8) AS INTEGER)), 0) + 1
        FROM t_purchase_order_header
        WHERE po_no LIKE 'PO-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.po_no := 'PO-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_po_no ON t_purchase_order_header;
CREATE TRIGGER trg_generate_po_no
BEFORE INSERT ON t_purchase_order_header
FOR EACH ROW
EXECUTE FUNCTION generate_po_no();

-- ==============================================
-- ตาราง: t_purchase_order_detail (รายละเอียด)
-- Purchase order line items.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_purchase_order_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    po_header_id UUID NOT NULL REFERENCES t_purchase_order_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id) ON DELETE RESTRICT,
    quantity_ordered INTEGER NOT NULL DEFAULT 1,
    quantity_received INTEGER DEFAULT 0,
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

CREATE INDEX IF NOT EXISTS idx_t_po_detail_header ON t_purchase_order_detail(po_header_id);
CREATE INDEX IF NOT EXISTS idx_t_po_detail_part ON t_purchase_order_detail(part_id);
CREATE INDEX IF NOT EXISTS idx_t_po_detail_whitelabel ON t_purchase_order_detail(whitelabel_id);

-- ==============================================
-- ตาราง: t_purchase_order_status_history
-- Track purchase order status changes.
-- ==============================================
CREATE TABLE IF NOT EXISTS t_purchase_order_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    po_header_id UUID NOT NULL REFERENCES t_purchase_order_header(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID NOT NULL REFERENCES m_user(id) ON DELETE RESTRICT,
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_po_status_history_po ON t_purchase_order_status_history(po_header_id);
CREATE INDEX IF NOT EXISTS idx_t_po_status_history_changed ON t_purchase_order_status_history(changed_at);
