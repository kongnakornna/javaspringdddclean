-- ============================================
-- i18n Multi-Language Schema (Module 12)
-- ============================================

-- ตารางหลักสำหรับกำหนดค่าภาษา / Main table for language configuration
CREATE TABLE IF NOT EXISTS m_language (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID,
    language_code VARCHAR(10) NOT NULL UNIQUE,
    language_name VARCHAR(100) NOT NULL,
    language_name_en VARCHAR(100),
    flag_emoji VARCHAR(10),
    is_rtl BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    is_default BOOLEAN DEFAULT FALSE,
    sort_order INTEGER DEFAULT 0,
    locale VARCHAR(20),
    date_format VARCHAR(50),
    time_format VARCHAR(50),
    number_format VARCHAR(50),
    currency_symbol VARCHAR(10)
);

-- ตารางสำหรับข้อความที่แปลแล้ว / Translation table
CREATE TABLE IF NOT EXISTS m_translation (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID,
    message_key VARCHAR(255) NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    message_text TEXT NOT NULL,
    context VARCHAR(100),
    description TEXT,
    version INTEGER DEFAULT 1,
    is_approved BOOLEAN DEFAULT FALSE,
    approved_by UUID,
    approved_at TIMESTAMP,
    UNIQUE (message_key, language_code, context)
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_language_code ON m_language(language_code);
CREATE INDEX IF NOT EXISTS idx_language_active ON m_language(is_active);
CREATE INDEX IF NOT EXISTS idx_language_default ON m_language(is_default);
CREATE INDEX IF NOT EXISTS idx_language_sort ON m_language(sort_order);
CREATE INDEX IF NOT EXISTS idx_translation_message_key ON m_translation(message_key);
CREATE INDEX IF NOT EXISTS idx_translation_language_code ON m_translation(language_code);
CREATE INDEX IF NOT EXISTS idx_translation_key_language ON m_translation(message_key, language_code);
CREATE INDEX IF NOT EXISTS idx_translation_approved ON m_translation(is_approved);

-- View: ภาษาที่เปิดใช้งานทั้งหมด / Available languages view
CREATE OR REPLACE VIEW v_available_languages AS
SELECT
    id,
    language_code,
    language_name,
    language_name_en,
    flag_emoji,
    is_rtl,
    is_active,
    is_default,
    sort_order,
    locale,
    date_format,
    time_format,
    number_format,
    currency_symbol
FROM m_language
WHERE is_active = TRUE
ORDER BY sort_order ASC;

-- Seed Languages (18 ภาษา)
INSERT INTO m_language (language_code, language_name, language_name_en, flag_emoji, is_rtl, is_active, is_default, sort_order, locale, date_format, time_format, number_format, currency_symbol) VALUES
('th', 'ภาษาไทย', 'Thai', '🇹🇭', FALSE, TRUE, TRUE, 1, 'th_TH', 'วันที่ d/M/2567', 'เวลา H:mm', '#,##0.00', '฿'),
('en', 'English', 'English', '🇬🇧', FALSE, TRUE, FALSE, 2, 'en_US', 'MM/dd/yyyy', 'HH:mm', '#,##0.00', '$'),
('zh', '中文', 'Chinese', '🇨🇳', FALSE, TRUE, FALSE, 3, 'zh_CN', 'yyyy/MM/dd', 'HH:mm', '#,##0.00', '¥'),
('ja', '日本語', 'Japanese', '🇯🇵', FALSE, TRUE, FALSE, 4, 'ja_JP', 'yyyy/MM/dd', 'HH:mm', '#,##0.00', '¥'),
('ko', '한국어', 'Korean', '🇰🇷', FALSE, TRUE, FALSE, 5, 'ko_KR', 'yyyy/MM/dd', 'HH:mm', '#,##0.00', '₩'),
('es', 'Español', 'Spanish', '🇪🇸', FALSE, TRUE, FALSE, 6, 'es_ES', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '€'),
('fr', 'Français', 'French', '🇫🇷', FALSE, TRUE, FALSE, 7, 'fr_FR', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '€'),
('de', 'Deutsch', 'German', '🇩🇪', FALSE, TRUE, FALSE, 8, 'de_DE', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '€'),
('it', 'Italiano', 'Italian', '🇮🇹', FALSE, TRUE, FALSE, 9, 'it_IT', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '€'),
('pt', 'Português', 'Portuguese', '🇵🇹', FALSE, TRUE, FALSE, 10, 'pt_PT', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '€'),
('ru', 'Русский', 'Russian', '🇷🇺', FALSE, TRUE, FALSE, 11, 'ru_RU', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '₽'),
('ar', 'العربية', 'Arabic', '🇸🇦', TRUE, TRUE, FALSE, 12, 'ar_SA', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '﷼'),
('hi', 'हिन्दी', 'Hindi', '🇮🇳', FALSE, TRUE, FALSE, 13, 'hi_IN', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '₹'),
('id', 'Bahasa Indonesia', 'Indonesian', '🇮🇩', FALSE, TRUE, FALSE, 14, 'id_ID', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', 'Rp'),
('ms', 'Bahasa Melayu', 'Malay', '🇲🇾', FALSE, TRUE, FALSE, 15, 'ms_MY', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', 'RM'),
('vi', 'Tiếng Việt', 'Vietnamese', '🇻🇳', FALSE, TRUE, FALSE, 16, 'vi_VN', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '₫'),
('my', 'မြန်မာဘာသာ', 'Burmese', '🇲🇲', FALSE, TRUE, FALSE, 17, 'my_MM', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', 'K'),
('lo', 'ພາສາລາວ', 'Lao', '🇱🇦', FALSE, TRUE, FALSE, 18, 'lo_LA', 'dd/MM/yyyy', 'HH:mm', '#,##0.00', '₭');

-- Seed Translations
INSERT INTO m_translation (message_key, language_code, message_text, context, is_approved) VALUES
('job.status.open', 'th', 'เปิดใบงาน', 'UI', TRUE),
('job.status.in_progress', 'th', 'กำลังดำเนินการ', 'UI', TRUE),
('job.status.completed', 'th', 'เสร็จสมบูรณ์', 'UI', TRUE),
('quotation.approved', 'th', 'อนุมัติใบเสนอราคาแล้ว', 'UI', TRUE),
('quotation.rejected', 'th', 'ปฏิเสธใบเสนอราคา', 'UI', TRUE),
('common.save', 'th', 'บันทึก', 'UI', TRUE),
('common.cancel', 'th', 'ยกเลิก', 'UI', TRUE),
('common.delete', 'th', 'ลบ', 'UI', TRUE),
('common.confirm', 'th', 'ยืนยัน', 'UI', TRUE),

('job.status.open', 'en', 'Open', 'UI', TRUE),
('job.status.in_progress', 'en', 'In Progress', 'UI', TRUE),
('job.status.completed', 'en', 'Completed', 'UI', TRUE),
('quotation.approved', 'en', 'Quotation Approved', 'UI', TRUE),
('quotation.rejected', 'en', 'Quotation Rejected', 'UI', TRUE),
('common.save', 'en', 'Save', 'UI', TRUE),
('common.cancel', 'en', 'Cancel', 'UI', TRUE),
('common.delete', 'en', 'Delete', 'UI', TRUE),
('common.confirm', 'en', 'Confirm', 'UI', TRUE);
