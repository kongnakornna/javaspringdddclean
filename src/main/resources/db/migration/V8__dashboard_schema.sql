-- ==============================================
-- หมายเหตุ: ตาราง Dashboard ส่วนใหญ่เป็น VIEW หรือ Materialized View
-- ที่ดึงข้อมูลจากตารางหลัก (t_job, t_invoice, t_payment, t_inventory)
-- NOTE: Most Dashboard tables are VIEWs or Materialized Views
-- that pull data from main tables.
-- ==============================================

-- ==============================================
-- VIEW: v_dashboard_sales_overview (ภาพรวมยอดขาย)
-- Sales overview dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_sales_overview AS
WITH sales_data AS (
    SELECT 
        i.id AS invoice_id,
        i.customer_id,
        i.job_id,
        i.invoice_date,
        i.total AS invoice_total,
        COALESCE((
            SELECT SUM(p.amount) 
            FROM t_payment p 
            WHERE p.invoice_id = i.id 
            AND p.status = 'COMPLETED'
        ), 0) AS amount_paid,
        i.whitelabel_id
    FROM t_invoice_adjustment i
    WHERE i.deleted = false 
    AND i.type = 'INVOICE'
)
SELECT 
    COUNT(DISTINCT invoice_id) AS total_invoices,
    COUNT(DISTINCT customer_id) AS total_customers,
    COALESCE(SUM(invoice_total), 0) AS total_revenue,
    COALESCE(SUM(invoice_total - amount_paid), 0) AS total_outstanding,
    COALESCE(AVG(invoice_total), 0) AS average_invoice,
    DATE_TRUNC('month', invoice_date) AS period,
    whitelabel_id
FROM sales_data
GROUP BY DATE_TRUNC('month', invoice_date), whitelabel_id;

-- ==============================================
-- VIEW: v_dashboard_job_status (สรุปสถานะใบงาน)
-- Job status summary dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_job_status AS
SELECT 
    status,
    COUNT(*) AS count,
    whitelabel_id
FROM t_job
WHERE deleted = false
GROUP BY status, whitelabel_id;

-- ==============================================
-- VIEW: v_dashboard_inventory_overview (ภาพรวมสินค้าคงคลัง)
-- Inventory overview dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_inventory_overview AS
SELECT 
    COUNT(*) AS total_parts,
    SUM(stock_quantity) AS total_quantity,
    COALESCE(SUM(stock_quantity * unit_cost), 0) AS total_value,
    COUNT(CASE WHEN stock_quantity <= reorder_level THEN 1 END) AS low_stock_count,
    COUNT(CASE WHEN status = 'ACTIVE' THEN 1 END) AS active_parts,
    whitelabel_id
FROM m_part_master
WHERE deleted = false;

-- ==============================================
-- VIEW: v_dashboard_top_parts (อะไหล่ขายดี)
-- Top selling parts dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_top_parts AS
SELECT 
    p.id AS part_id,
    p.part_code,
    p.part_name,
    COALESCE(SUM(jps.quantity), 0) AS total_sold,
    COALESCE(SUM(jps.net_price), 0) AS total_revenue,
    p.whitelabel_id,
    ROW_NUMBER() OVER (PARTITION BY p.whitelabel_id ORDER BY SUM(jps.quantity) DESC) AS rank
FROM m_part_master p
LEFT JOIN t_job_part_sales jps ON jps.part_id = p.id
WHERE p.deleted = false
GROUP BY p.id, p.part_code, p.part_name, p.whitelabel_id;

-- ==============================================
-- VIEW: v_dashboard_service_category (บริการแยกประเภท)
-- Service by category dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_service_category AS
SELECT 
    c.name AS category_name,
    COUNT(js.id) AS service_count,
    COALESCE(SUM(js.net_price), 0) AS total_revenue,
    js.whitelabel_id
FROM t_job_service js
JOIN m_service s ON s.id = js.service_id
JOIN m_category c ON c.id = s.category_id
WHERE js.deleted = false
GROUP BY c.name, js.whitelabel_id;

-- ==============================================
-- VIEW: v_dashboard_financial_summary (สรุปการเงิน)
-- Financial summary dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_financial_summary AS
WITH invoice_data AS (
    SELECT 
        DATE_TRUNC('month', invoice_date) AS month,
        COALESCE(SUM(total), 0) AS total_invoice,
        whitelabel_id
    FROM t_invoice_adjustment
    WHERE deleted = false AND type = 'INVOICE'
    GROUP BY DATE_TRUNC('month', invoice_date), whitelabel_id
),
payment_data AS (
    SELECT 
        DATE_TRUNC('month', payment_date) AS month,
        COALESCE(SUM(amount), 0) AS total_payment,
        whitelabel_id
    FROM t_payment
    WHERE deleted = false AND status = 'COMPLETED'
    GROUP BY DATE_TRUNC('month', payment_date), whitelabel_id
)
SELECT 
    COALESCE(i.month, p.month) AS month,
    COALESCE(i.total_invoice, 0) AS total_invoice,
    COALESCE(p.total_payment, 0) AS total_payment,
    COALESCE(i.total_invoice, 0) - COALESCE(p.total_payment, 0) AS net_income,
    COALESCE(i.whitelabel_id, p.whitelabel_id) AS whitelabel_id
FROM invoice_data i
FULL OUTER JOIN payment_data p ON i.month = p.month AND i.whitelabel_id = p.whitelabel_id;

-- ==============================================
-- VIEW: v_dashboard_revenue_by_period (รายได้แยกช่วงเวลา)
-- Revenue by period dashboard view.
-- ==============================================
CREATE OR REPLACE VIEW v_dashboard_revenue_by_period AS
SELECT 
    DATE_TRUNC('day', invoice_date) AS period,
    COUNT(*) AS invoice_count,
    COALESCE(SUM(total), 0) AS revenue,
    COALESCE(AVG(total), 0) AS average_revenue,
    whitelabel_id
FROM t_invoice_adjustment
WHERE deleted = false AND type = 'INVOICE'
GROUP BY DATE_TRUNC('day', invoice_date), whitelabel_id;

-- ==============================================
-- ตาราง: d_widget_config (กำหนดค่า Widget บน Dashboard)
-- Dashboard widget configuration.
-- ==============================================
CREATE TABLE IF NOT EXISTS d_widget_config (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    widget_id VARCHAR(50) NOT NULL,
    widget_type VARCHAR(20) NOT NULL,
    widget_title VARCHAR(100),
    position INTEGER DEFAULT 0,
    width INTEGER DEFAULT 4,
    height INTEGER DEFAULT 2,
    config JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_d_widget_config_user ON d_widget_config(user_id);
CREATE INDEX IF NOT EXISTS idx_d_widget_config_whitelabel ON d_widget_config(whitelabel_id);
