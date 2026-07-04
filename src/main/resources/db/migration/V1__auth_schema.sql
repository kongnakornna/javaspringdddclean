-- ==============================================
-- ตาราง: m_user (ผู้ใช้ระบบ)
-- This table stores system user credentials and profiles.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',   -- PENDING, ACTIVE, INACTIVE, SUSPENDED
    phone_number VARCHAR(20),
    profile_image_url TEXT,
    last_login TIMESTAMP,
    role VARCHAR(20),
    -- Generic Audit Fields (จาก GenericBusinessEntity)
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,         -- ผู้สร้าง (Audit)
    whitelabel_id UUID NOT NULL    -- บริษัท/สาขา (Multi-tenancy)
);

CREATE INDEX IF NOT EXISTS idx_m_user_email ON m_user(email);
CREATE INDEX IF NOT EXISTS idx_m_user_whitelabel ON m_user(whitelabel_id);
CREATE INDEX IF NOT EXISTS idx_m_user_deleted ON m_user(deleted);

-- ==============================================
-- ตาราง: m_role (บทบาท)
-- This table defines roles (e.g., ADMIN, MANAGER, USER).
-- ==============================================
CREATE TABLE IF NOT EXISTS m_role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    -- Audit Fields
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

-- ==============================================
-- ตาราง: m_permission (สิทธิ์การใช้งาน)
-- This table defines granular permissions (e.g., READ_JOB, WRITE_INVENTORY).
-- ==============================================
CREATE TABLE IF NOT EXISTS m_permission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,   -- เช่น "JOB_CREATE", "INVENTORY_READ"
    description TEXT,
    action VARCHAR(50),                   -- CREATE, READ, UPDATE, DELETE, EXECUTE
    resource VARCHAR(50),                 -- JOB, INVENTORY, USER, REPORT
    -- Audit Fields
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

-- ==============================================
-- ตารางเชื่อม: m_user_role (ผู้ใช้ <-> บทบาท)
-- Junction table for many-to-many relationship between users and roles.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_user_role (
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES m_role(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX IF NOT EXISTS idx_user_role_user ON m_user_role(user_id);
CREATE INDEX IF NOT EXISTS idx_user_role_role ON m_user_role(role_id);

-- ==============================================
-- ตารางเชื่อม: m_role_permission (บทบาท <-> สิทธิ์)
-- Junction table for many-to-many relationship between roles and permissions.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_role_permission (
    role_id UUID NOT NULL REFERENCES m_role(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES m_permission(id) ON DELETE CASCADE,
    granted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (role_id, permission_id)
);

CREATE INDEX IF NOT EXISTS idx_role_permission_role ON m_role_permission(role_id);
CREATE INDEX IF NOT EXISTS idx_role_permission_permission ON m_role_permission(permission_id);

-- ==============================================
-- ตาราง: m_user_token (Token ที่ออกให้ผู้ใช้)
-- This table stores issued tokens (JWT, Refresh) for logout/revoke management.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_user_token (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    token TEXT UNIQUE NOT NULL,              -- JWT String
    token_type VARCHAR(20) NOT NULL,         -- ACCESS, REFRESH
    expiry_date TIMESTAMP NOT NULL,          -- เวลาหมดอายุ
    revoked BOOLEAN DEFAULT FALSE,           -- ถูกเพิกถอนหรือไม่
    revoked_at TIMESTAMP,
    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_agent TEXT,
    ip_address VARCHAR(45)
);

CREATE INDEX IF NOT EXISTS idx_user_token_user ON m_user_token(user_id);
CREATE INDEX IF NOT EXISTS idx_user_token_token ON m_user_token(token);
CREATE INDEX IF NOT EXISTS idx_user_token_expiry ON m_user_token(expiry_date);

-- ==============================================
-- ตาราง: m_rate_limit_log (บันทึกการเข้าถึงที่ถูกปฏิเสธเพราะ Rate Limit)
-- Optional: This table logs requests that were rejected due to rate limiting.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_rate_limit_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id VARCHAR(100) NOT NULL,    -- User ID หรือ IP Address
    api_path TEXT NOT NULL,
    method VARCHAR(10) NOT NULL,
    attempted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    rate_limit_key VARCHAR(255)         -- Redis Key ที่ใช้
);

CREATE INDEX IF NOT EXISTS idx_rate_limit_client ON m_rate_limit_log(client_id);
CREATE INDEX IF NOT EXISTS idx_rate_limit_time ON m_rate_limit_log(attempted_at);
