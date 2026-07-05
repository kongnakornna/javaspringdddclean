-- ============================================
-- Batch Job Schema (Module 11)
-- ============================================

-- ตารางหลักสำหรับกำหนดค่างาน Batch / Main table for batch job configuration
CREATE TABLE IF NOT EXISTS m_batch_job (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID,
    job_code VARCHAR(50) NOT NULL UNIQUE,
    job_name VARCHAR(200) NOT NULL,
    job_type VARCHAR(50),
    description TEXT,
    cron_expression VARCHAR(100) NOT NULL,
    enabled BOOLEAN DEFAULT true,
    max_retry INTEGER DEFAULT 3,
    retry_delay_ms INTEGER DEFAULT 5000,
    timeout_seconds INTEGER DEFAULT 300,
    last_run_time TIMESTAMP,
    next_run_time TIMESTAMP,
    total_runs INTEGER DEFAULT 0,
    last_status VARCHAR(20),
    parameters JSONB DEFAULT '{}'::jsonb
);

-- ตารางประวัติการทำงานของ Batch Job / Batch job execution history
CREATE TABLE IF NOT EXISTS t_batch_job_history (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID,
    job_code VARCHAR(50) NOT NULL,
    started_at TIMESTAMP NOT NULL,
    finished_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    error_message TEXT,
    result_summary TEXT,
    records_processed INTEGER DEFAULT 0,
    duration_ms INTEGER,
    trigger_type VARCHAR(20),
    triggered_by UUID,
    parameters JSONB DEFAULT '{}'::jsonb
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_batch_job_code ON m_batch_job(job_code);
CREATE INDEX IF NOT EXISTS idx_batch_job_enabled ON m_batch_job(enabled);
CREATE INDEX IF NOT EXISTS idx_batch_job_type ON m_batch_job(job_type);
CREATE INDEX IF NOT EXISTS idx_batch_history_job_code ON t_batch_job_history(job_code);
CREATE INDEX IF NOT EXISTS idx_batch_history_status ON t_batch_job_history(status);
CREATE INDEX IF NOT EXISTS idx_batch_history_started_at ON t_batch_job_history(started_at);
CREATE INDEX IF NOT EXISTS idx_batch_history_job_status ON t_batch_job_history(job_code, status);

-- Demo Data
INSERT INTO m_batch_job (job_code, job_name, job_type, description, cron_expression, enabled, max_retry, timeout_seconds, parameters) VALUES
('batch001', 'ส่งอีเมลแจ้งเตือน', 'EMAIL', 'ส่งอีเมลแจ้งเตือนผู้ใช้ตามกำหนดการ', '0 */5 * * * *', true, 3, 120, '{"max_emails_per_run": 100, "retry_on_failure": true}'),
('batch002', 'สร้างรายงานประจำวัน', 'REPORT', 'สร้างรายงานสรุปข้อมูลประจำวัน', '0 0 2 * * ?', true, 2, 300, '{"report_type": "daily", "format": "pdf"}'),
('batch003', 'ซิงค์ข้อมูลกับระบบภายนอก', 'SYNC', 'ซิงค์ข้อมูลระหว่างระบบ', '0 0 * * * ?', true, 5, 600, '{"sync_direction": "bidirectional", "chunk_size": 1000}'),
('batch004', 'ล้างข้อมูลเก่า', 'CLEANUP', 'ลบข้อมูลที่หมดอายุแล้ว', '0 0 3 * * ?', true, 1, 600, '{"retention_days": 90, "cleanup_types": ["logs", "temp_files"]}'),
('batch005', 'อัปเดตข้อมูลประจำ', 'UPDATE', 'อัปเดตข้อมูลตามรอบเวลา', '0 */30 * * * *', true, 3, 180, '{"update_fields": ["status", "score"], "batch_size": 500}'),
('batch006', 'สรุปผลรายวัน', 'SUMMARY', 'สรุปข้อมูลการดำเนินงานประจำวัน', '0 0 4 * * ?', true, 2, 300, '{"summary_types": ["users", "transactions", "errors"], "format": "json"}');
