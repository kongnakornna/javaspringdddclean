 -- =============================================================================
-- FULL SCHEMA + DEMO DATA FOR AUTO REPAIR SHOP MANAGEMENT SYSTEM
-- PostgreSQL 15+
-- Author: Kongnakorn Jantakun
-- Date: 2026-07-04
-- =============================================================================

-- เปิดใช้งาน UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =============================================================================
-- 1. MASTER TABLES
-- =============================================================================

-- 1.1 AUTH
CREATE TABLE IF NOT EXISTS m_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    phone_number VARCHAR(20),
    profile_image_url TEXT,
    last_login TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_permission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    action VARCHAR(50),
    resource VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_user_role (
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES m_role(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS m_role_permission (
    role_id UUID NOT NULL REFERENCES m_role(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES m_permission(id) ON DELETE CASCADE,
    granted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS m_user_token (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    token TEXT UNIQUE NOT NULL,
    token_type VARCHAR(20) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    revoked_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_agent TEXT,
    ip_address INET
);

-- 1.2 CUSTOMER
CREATE TABLE IF NOT EXISTS m_customer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_code VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    display_name VARCHAR(200),
    customer_type VARCHAR(20) NOT NULL DEFAULT 'INDIVIDUAL',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    tax_id VARCHAR(20),
    email VARCHAR(100),
    phone_number VARCHAR(20) NOT NULL,
    secondary_phone VARCHAR(20),
    address TEXT,
    province VARCHAR(100),
    city VARCHAR(100),
    district VARCHAR(100),
    postal_code VARCHAR(10),
    country VARCHAR(50) DEFAULT 'Thailand',
    contact_person VARCHAR(100),
    contact_phone VARCHAR(20),
    notes TEXT,
    last_visit_date TIMESTAMP,
    total_visit_count INTEGER DEFAULT 0,
    total_spent DECIMAL(15,2) DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_car (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL REFERENCES m_customer(id) ON DELETE CASCADE,
    license_plate VARCHAR(20) UNIQUE NOT NULL,
    province VARCHAR(50),
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(100) NOT NULL,
    sub_model VARCHAR(100),
    year INTEGER,
    color VARCHAR(30),
    engine_number VARCHAR(50),
    chassis_number VARCHAR(50),
    fuel_type VARCHAR(20),
    transmission_type VARCHAR(20),
    engine_cc INTEGER,
    seating_capacity INTEGER,
    mileage INTEGER DEFAULT 0,
    last_service_date TIMESTAMP,
    next_service_mileage INTEGER,
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_car_service_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    car_id UUID NOT NULL REFERENCES m_car(id) ON DELETE CASCADE,
    job_id UUID,
    service_date TIMESTAMP NOT NULL,
    service_type VARCHAR(50),
    description TEXT,
    total_cost DECIMAL(15,2),
    mileage_at_service INTEGER,
    mechanic_name VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID
);

-- 1.3 MASTER DATA
CREATE TABLE IF NOT EXISTS m_supplier (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    supplier_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    tax_id VARCHAR(20),
    email VARCHAR(100),
    phone VARCHAR(20) NOT NULL,
    address TEXT,
    contact_person VARCHAR(100),
    contact_phone VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_part_master (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    part_code VARCHAR(50) UNIQUE NOT NULL,
    part_name VARCHAR(200) NOT NULL,
    part_name_en VARCHAR(200),
    category_id UUID,
    brand VARCHAR(50),
    model VARCHAR(100),
    oem_number VARCHAR(50),
    description TEXT,
    unit VARCHAR(20) DEFAULT 'PIECE',
    reorder_level INTEGER DEFAULT 0,
    reorder_quantity INTEGER DEFAULT 0,
    stock_quantity INTEGER DEFAULT 0,
    min_stock INTEGER DEFAULT 0,
    max_stock INTEGER DEFAULT 0,
    unit_cost DECIMAL(15,2),
    selling_price DECIMAL(15,2),
    location_id UUID,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    image_url TEXT,
    notes TEXT,
    last_updated_stock TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    service_code VARCHAR(20) UNIQUE NOT NULL,
    service_name VARCHAR(200) NOT NULL,
    service_name_en VARCHAR(200),
    category_id UUID,
    description TEXT,
    unit VARCHAR(20) DEFAULT 'HOUR',
    unit_price DECIMAL(15,2),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_category (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_code VARCHAR(50) UNIQUE NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    category_name_en VARCHAR(100),
    parent_id UUID,
    level INTEGER DEFAULT 0,
    sort_order INTEGER DEFAULT 0,
    icon_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_stock_location (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    location_code VARCHAR(20) UNIQUE NOT NULL,
    location_name VARCHAR(100) NOT NULL,
    location_type VARCHAR(20) DEFAULT 'SHELF',
    zone VARCHAR(50),
    capacity INTEGER,
    current_usage INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_currency (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    currency_code VARCHAR(10) UNIQUE NOT NULL,
    currency_name VARCHAR(50) NOT NULL,
    symbol VARCHAR(10),
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_exchange_rate (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    base_currency VARCHAR(10) NOT NULL,
    target_currency VARCHAR(10) NOT NULL,
    rate DECIMAL(15,4) NOT NULL,
    effective_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_country (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_code VARCHAR(10) UNIQUE NOT NULL,
    country_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_province (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_id UUID REFERENCES m_country(id),
    province_code VARCHAR(20) UNIQUE NOT NULL,
    province_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_city (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    province_id UUID REFERENCES m_province(id),
    city_code VARCHAR(20) UNIQUE NOT NULL,
    city_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_shop_profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    shop_name VARCHAR(200) NOT NULL,
    shop_name_en VARCHAR(200),
    address TEXT,
    phone VARCHAR(20),
    email VARCHAR(100),
    tax_id VARCHAR(20),
    logo_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_staff (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    staff_code VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(100),
    hire_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

-- 1.4 PAYMENT
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
    user_id UUID,
    whitelabel_id UUID
);

-- 1.5 DOCUMENT
CREATE TABLE IF NOT EXISTS m_document_template (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_code VARCHAR(50) UNIQUE NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    template_type VARCHAR(20) NOT NULL,
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
    user_id UUID,
    whitelabel_id UUID
);

-- 1.6 EMAIL
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
    user_id UUID,
    whitelabel_id UUID
);

-- 1.7 I18N
CREATE TABLE IF NOT EXISTS m_language (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    language_code VARCHAR(10) UNIQUE NOT NULL,
    language_name VARCHAR(50) NOT NULL,
    language_name_en VARCHAR(50),
    flag_emoji VARCHAR(10),
    is_rtl BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    is_default BOOLEAN DEFAULT FALSE,
    sort_order INTEGER DEFAULT 0,
    locale VARCHAR(20),
    date_format VARCHAR(50),
    time_format VARCHAR(50),
    number_format VARCHAR(50),
    currency_symbol VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_translation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message_key VARCHAR(255) NOT NULL,
    language_code VARCHAR(10) NOT NULL REFERENCES m_language(language_code) ON DELETE CASCADE,
    message_text TEXT NOT NULL,
    context VARCHAR(100),
    description TEXT,
    version INTEGER DEFAULT 1,
    is_approved BOOLEAN DEFAULT TRUE,
    approved_by UUID,
    approved_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID,
    UNIQUE (message_key, language_code, context)
);

-- 1.8 BATCH
CREATE TABLE IF NOT EXISTS m_batch_job (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_code VARCHAR(30) UNIQUE NOT NULL,
    job_name VARCHAR(100) NOT NULL,
    job_type VARCHAR(30) NOT NULL,
    description TEXT,
    cron_expression VARCHAR(100) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    max_retry INTEGER DEFAULT 3,
    retry_delay_ms INTEGER DEFAULT 60000,
    timeout_seconds INTEGER DEFAULT 300,
    last_run_time TIMESTAMP,
    next_run_time TIMESTAMP,
    total_runs INTEGER DEFAULT 0,
    last_status VARCHAR(20),
    parameters JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

-- 1.9 IoT
CREATE TABLE IF NOT EXISTS m_iot_device (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id VARCHAR(50) UNIQUE NOT NULL,
    device_name VARCHAR(100) NOT NULL,
    device_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) DEFAULT 'OFFLINE',
    serial_number VARCHAR(50),
    firmware_version VARCHAR(20),
    hardware_version VARCHAR(20),
    manufacturer VARCHAR(100),
    model VARCHAR(50),
    battery_level INTEGER DEFAULT 0,
    last_seen TIMESTAMP,
    last_latitude DECIMAL(10,7),
    last_longitude DECIMAL(10,7),
    last_altitude DECIMAL(10,2),
    last_speed DECIMAL(10,2),
    is_active BOOLEAN DEFAULT TRUE,
    is_online BOOLEAN DEFAULT FALSE,
    metadata JSONB,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_geofence (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    geofence_name VARCHAR(100) NOT NULL,
    geofence_type VARCHAR(20) NOT NULL,
    center_latitude DECIMAL(10,7),
    center_longitude DECIMAL(10,7),
    radius DECIMAL(10,2),
    coordinates JSONB,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    alert_on_enter BOOLEAN DEFAULT TRUE,
    alert_on_exit BOOLEAN DEFAULT TRUE,
    speed_limit DECIMAL(10,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

-- 1.10 WOS
CREATE TABLE IF NOT EXISTS m_catalogue_category (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_code VARCHAR(50) UNIQUE NOT NULL,
    category_name VARCHAR(100) NOT NULL,
    category_name_en VARCHAR(100),
    parent_id UUID REFERENCES m_catalogue_category(id),
    level INTEGER DEFAULT 0,
    sort_order INTEGER DEFAULT 0,
    icon_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_catalogue_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_code VARCHAR(50) UNIQUE NOT NULL,
    item_name VARCHAR(200) NOT NULL,
    item_name_en VARCHAR(200),
    category_id UUID REFERENCES m_catalogue_category(id),
    part_id UUID REFERENCES m_part_master(id),
    description TEXT,
    short_description VARCHAR(500),
    brand VARCHAR(100),
    model_compatibility TEXT,
    image_url TEXT,
    gallery_images JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    is_new BOOLEAN DEFAULT FALSE,
    sort_order INTEGER DEFAULT 0,
    tags TEXT[],
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_sales_price (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_id UUID NOT NULL REFERENCES m_catalogue_item(id) ON DELETE CASCADE,
    price_tier VARCHAR(30) DEFAULT 'DEFAULT',
    unit_price DECIMAL(15,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'THB',
    effective_date TIMESTAMP NOT NULL DEFAULT NOW(),
    expiry_date TIMESTAMP,
    min_quantity INTEGER DEFAULT 1,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS m_promotion (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    promotion_code VARCHAR(50) UNIQUE NOT NULL,
    promotion_name VARCHAR(100) NOT NULL,
    promotion_type VARCHAR(20) NOT NULL,
    discount_value DECIMAL(15,2) NOT NULL,
    min_order_amount DECIMAL(15,2),
    max_discount DECIMAL(15,2),
    applicable_to JSONB,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    usage_limit INTEGER DEFAULT 0,
    used_count INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

-- =============================================================================
-- 2. TRANSACTION TABLES
-- =============================================================================

-- 2.1 JOB
CREATE TABLE IF NOT EXISTS t_job (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_no VARCHAR(20) UNIQUE NOT NULL,
    customer_id UUID NOT NULL REFERENCES m_customer(id),
    car_id UUID NOT NULL REFERENCES m_car(id),
    mechanic_id UUID REFERENCES m_staff(id),
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    start_date TIMESTAMP NOT NULL DEFAULT NOW(),
    end_date TIMESTAMP,
    symptom TEXT,
    diagnosis_note TEXT,
    mileage INTEGER,
    estimated_cost DECIMAL(15,2),
    actual_cost DECIMAL(15,2),
    priority VARCHAR(20) DEFAULT 'NORMAL',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_job_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    service_id UUID NOT NULL REFERENCES m_service(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_job_part_sales (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_job_service_car_symptom (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    symptom_code VARCHAR(20),
    symptom_description TEXT NOT NULL,
    severity VARCHAR(20) DEFAULT 'MEDIUM',
    reported_by VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_job_diag_trouble_code (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    trouble_code VARCHAR(20) NOT NULL,
    description TEXT,
    system VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_job_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id UUID NOT NULL REFERENCES t_job(id) ON DELETE CASCADE,
    from_status VARCHAR(30),
    to_status VARCHAR(30) NOT NULL,
    changed_by UUID REFERENCES m_user(id),
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID
);

-- 2.2 QUOTATION
CREATE TABLE IF NOT EXISTS t_quotation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_no VARCHAR(20) UNIQUE NOT NULL,
    job_id UUID NOT NULL REFERENCES t_job(id),
    customer_id UUID NOT NULL REFERENCES m_customer(id),
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
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_quotation_part (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_id UUID NOT NULL REFERENCES t_quotation(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_quotation_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_id UUID NOT NULL REFERENCES t_quotation(id) ON DELETE CASCADE,
    service_id UUID NOT NULL REFERENCES m_service(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_quotation_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quotation_id UUID NOT NULL REFERENCES t_quotation(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID REFERENCES m_user(id),
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID
);

-- 2.3 PURCHASE ORDER
CREATE TABLE IF NOT EXISTS t_purchase_order_header (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    po_no VARCHAR(20) UNIQUE NOT NULL,
    quotation_id UUID REFERENCES t_quotation(id),
    job_id UUID REFERENCES t_job(id),
    supplier_id UUID NOT NULL REFERENCES m_supplier(id),
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
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_purchase_order_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    po_header_id UUID NOT NULL REFERENCES t_purchase_order_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    quantity_ordered INTEGER NOT NULL DEFAULT 1,
    quantity_received INTEGER DEFAULT 0,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity_ordered * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity_ordered * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_purchase_order_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    po_header_id UUID NOT NULL REFERENCES t_purchase_order_header(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID REFERENCES m_user(id),
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID
);

-- 2.4 INVENTORY
CREATE TABLE IF NOT EXISTS t_inventory (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    transaction_type VARCHAR(20) NOT NULL,
    reference_type VARCHAR(30),
    reference_id UUID,
    quantity INTEGER NOT NULL,
    previous_quantity INTEGER NOT NULL,
    new_quantity INTEGER NOT NULL,
    unit_cost DECIMAL(15,2),
    total_cost DECIMAL(15,2),
    transaction_date TIMESTAMP NOT NULL DEFAULT NOW(),
    note TEXT,
    performed_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_inventory_adjustment_header (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    adjustment_no VARCHAR(20) UNIQUE NOT NULL,
    adjustment_date TIMESTAMP NOT NULL DEFAULT NOW(),
    adjustment_type VARCHAR(20) NOT NULL,
    reason VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    description TEXT,
    approved_by UUID REFERENCES m_user(id),
    approved_at TIMESTAMP,
    total_adjustment_value DECIMAL(15,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_inventory_adjustment_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    adjustment_header_id UUID NOT NULL REFERENCES t_inventory_adjustment_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    quantity INTEGER NOT NULL,
    unit_cost DECIMAL(15,2),
    total_cost DECIMAL(15,2),
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_stocktake_header (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stocktake_no VARCHAR(20) UNIQUE NOT NULL,
    stocktake_date TIMESTAMP NOT NULL DEFAULT NOW(),
    status VARCHAR(20) DEFAULT 'DRAFT',
    started_by UUID REFERENCES m_user(id),
    started_at TIMESTAMP,
    completed_by UUID REFERENCES m_user(id),
    completed_at TIMESTAMP,
    total_discrepancy INTEGER DEFAULT 0,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_stocktake_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    stocktake_header_id UUID NOT NULL REFERENCES t_stocktake_header(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    system_quantity INTEGER NOT NULL,
    counted_quantity INTEGER NOT NULL,
    discrepancy INTEGER GENERATED ALWAYS AS (counted_quantity - system_quantity) STORED,
    note TEXT,
    counted_by UUID REFERENCES m_user(id),
    counted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_part_picking_request (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_no VARCHAR(20) UNIQUE NOT NULL,
    job_id UUID REFERENCES t_job(id),
    quotation_id UUID REFERENCES t_quotation(id),
    requested_date TIMESTAMP NOT NULL DEFAULT NOW(),
    requested_by UUID NOT NULL REFERENCES m_user(id),
    status VARCHAR(20) DEFAULT 'DRAFT',
    priority VARCHAR(20) DEFAULT 'NORMAL',
    notes TEXT,
    picked_by UUID REFERENCES m_user(id),
    picked_date TIMESTAMP,
    confirmed_by UUID REFERENCES m_user(id),
    confirmed_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_part_picking_detail (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    picking_request_id UUID NOT NULL REFERENCES t_part_picking_request(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    requested_quantity INTEGER NOT NULL,
    picked_quantity INTEGER DEFAULT 0,
    unit_price DECIMAL(15,2),
    total_price DECIMAL(15,2),
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

-- 2.5 PAYMENT
CREATE TABLE IF NOT EXISTS t_payment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payment_no VARCHAR(20) UNIQUE NOT NULL,
    invoice_id UUID,
    job_id UUID REFERENCES t_job(id),
    customer_id UUID NOT NULL REFERENCES m_customer(id),
    payment_date TIMESTAMP NOT NULL DEFAULT NOW(),
    payment_method_id UUID NOT NULL REFERENCES m_payment_method(id),
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
    received_by UUID NOT NULL REFERENCES m_user(id),
    approved_by UUID REFERENCES m_user(id),
    approved_at TIMESTAMP,
    refunded_amount DECIMAL(15,2) DEFAULT 0,
    refunded_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_receipt (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    receipt_no VARCHAR(20) UNIQUE NOT NULL,
    payment_id UUID NOT NULL REFERENCES t_payment(id),
    invoice_id UUID,
    customer_id UUID NOT NULL REFERENCES m_customer(id),
    receipt_date TIMESTAMP NOT NULL DEFAULT NOW(),
    receipt_type VARCHAR(20) DEFAULT 'FULL',
    amount DECIMAL(15,2) NOT NULL,
    amount_in_words_th TEXT,
    amount_in_words_en TEXT,
    currency VARCHAR(10) DEFAULT 'THB',
    status VARCHAR(20) DEFAULT 'ISSUED',
    notes TEXT,
    issued_by UUID NOT NULL REFERENCES m_user(id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_payment_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payment_id UUID NOT NULL REFERENCES t_payment(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID REFERENCES m_user(id),
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_outstanding_balance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    invoice_id UUID UNIQUE NOT NULL,
    customer_id UUID NOT NULL REFERENCES m_customer(id),
    invoice_total DECIMAL(15,2) NOT NULL,
    amount_paid DECIMAL(15,2) DEFAULT 0,
    outstanding_amount DECIMAL(15,2) GENERATED ALWAYS AS (invoice_total - amount_paid) STORED,
    last_payment_date TIMESTAMP,
    status VARCHAR(20) DEFAULT 'OUTSTANDING',
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID
);

-- 2.6 INVOICE (reuse t_invoice_adjustment as main invoice)
CREATE TABLE IF NOT EXISTS t_invoice_adjustment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    invoice_no VARCHAR(20) UNIQUE NOT NULL,
    job_id UUID REFERENCES t_job(id),
    customer_id UUID NOT NULL REFERENCES m_customer(id),
    invoice_date TIMESTAMP NOT NULL DEFAULT NOW(),
    type VARCHAR(20) NOT NULL DEFAULT 'INVOICE',
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
    status VARCHAR(20) DEFAULT 'PENDING',
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_invoice_adjustment_part (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    invoice_id UUID NOT NULL REFERENCES t_invoice_adjustment(id) ON DELETE CASCADE,
    part_id UUID NOT NULL REFERENCES m_part_master(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_invoice_adjustment_service (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    invoice_id UUID NOT NULL REFERENCES t_invoice_adjustment(id) ON DELETE CASCADE,
    service_id UUID NOT NULL REFERENCES m_service(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id UUID,
    whitelabel_id UUID
);

-- 2.7 DOCUMENT
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
    generated_by UUID NOT NULL REFERENCES m_user(id),
    generated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    sent_by UUID REFERENCES m_user(id),
    sent_at TIMESTAMP,
    sent_to_email VARCHAR(200),
    description TEXT,
    tags TEXT[],
    metadata JSONB,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    user_id UUID,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_document_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_id UUID NOT NULL REFERENCES t_document(id) ON DELETE CASCADE,
    action VARCHAR(30) NOT NULL,
    performed_by UUID NOT NULL REFERENCES m_user(id),
    performed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    details TEXT,
    whitelabel_id UUID
);

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
    user_id UUID,
    whitelabel_id UUID
);

-- 2.8 EMAIL
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
    user_id UUID,
    whitelabel_id UUID
);

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
    whitelabel_id UUID
);

-- 2.9 BATCH
CREATE TABLE IF NOT EXISTS t_batch_job_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_code VARCHAR(30) NOT NULL REFERENCES m_batch_job(job_code) ON DELETE CASCADE,
    started_at TIMESTAMP NOT NULL DEFAULT NOW(),
    finished_at TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    error_message TEXT,
    result_summary TEXT,
    records_processed INTEGER DEFAULT 0,
    duration_ms INTEGER,
    trigger_type VARCHAR(20) DEFAULT 'SCHEDULED',
    triggered_by UUID REFERENCES m_user(id),
    parameters JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID
);

-- 2.10 IOT
CREATE TABLE IF NOT EXISTS t_gps_data (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL REFERENCES m_iot_device(id) ON DELETE CASCADE,
    device_identifier VARCHAR(50),
    latitude DECIMAL(10,7) NOT NULL,
    longitude DECIMAL(10,7) NOT NULL,
    altitude DECIMAL(10,2),
    speed DECIMAL(10,2),
    heading DECIMAL(5,2),
    accuracy DECIMAL(5,2),
    battery_level INTEGER,
    satelites INTEGER,
    event_type VARCHAR(20),
    timestamp TIMESTAMP NOT NULL,
    received_at TIMESTAMP NOT NULL DEFAULT NOW(),
    metadata JSONB,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_device_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL REFERENCES m_iot_device(id) ON DELETE CASCADE,
    event_type VARCHAR(30) NOT NULL,
    event_description TEXT,
    old_value TEXT,
    new_value TEXT,
    event_timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    triggered_by UUID REFERENCES m_user(id),
    metadata JSONB,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_device_access_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    device_id UUID NOT NULL REFERENCES m_iot_device(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    access_type VARCHAR(20) NOT NULL,
    access_granted BOOLEAN DEFAULT TRUE,
    ip_address INET,
    user_agent TEXT,
    timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_geofence_alert (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    geofence_id UUID NOT NULL REFERENCES m_geofence(id) ON DELETE CASCADE,
    device_id UUID NOT NULL REFERENCES m_iot_device(id) ON DELETE CASCADE,
    alert_type VARCHAR(20) NOT NULL,
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    speed DECIMAL(10,2),
    alert_timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    is_resolved BOOLEAN DEFAULT FALSE,
    resolved_at TIMESTAMP,
    resolved_by UUID REFERENCES m_user(id),
    notes TEXT,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_auto_report (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    report_no VARCHAR(30) UNIQUE NOT NULL,
    report_type VARCHAR(30) NOT NULL,
    report_name VARCHAR(100) NOT NULL,
    report_date DATE NOT NULL,
    device_count INTEGER,
    total_distance DECIMAL(15,2),
    avg_speed DECIMAL(10,2),
    max_speed DECIMAL(10,2),
    total_moving_time INTERVAL,
    total_idle_time INTERVAL,
    battery_avg INTEGER,
    alert_count INTEGER DEFAULT 0,
    report_data JSONB,
    file_path TEXT,
    status VARCHAR(20) DEFAULT 'GENERATED',
    generated_by UUID REFERENCES m_user(id),
    generated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    whitelabel_id UUID
);

-- 2.11 WOS
CREATE TABLE IF NOT EXISTS t_shopping_cart (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id VARCHAR(50) UNIQUE NOT NULL,
    customer_id UUID REFERENCES m_customer(id),
    user_id UUID REFERENCES m_user(id),
    expires_at TIMESTAMP NOT NULL,
    subtotal DECIMAL(15,2) DEFAULT 0,
    discount DECIMAL(15,2) DEFAULT 0,
    tax DECIMAL(15,2) DEFAULT 0,
    shipping DECIMAL(15,2) DEFAULT 0,
    total DECIMAL(15,2) DEFAULT 0,
    promotion_code VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_shopping_cart_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id UUID NOT NULL REFERENCES t_shopping_cart(id) ON DELETE CASCADE,
    item_id UUID NOT NULL REFERENCES m_catalogue_item(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    attributes JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS t_web_order (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_no VARCHAR(30) UNIQUE NOT NULL,
    cart_id UUID REFERENCES t_shopping_cart(id),
    customer_id UUID NOT NULL REFERENCES m_customer(id),
    user_id UUID REFERENCES m_user(id),
    order_date TIMESTAMP NOT NULL DEFAULT NOW(),
    order_source VARCHAR(20) DEFAULT 'WEB',
    status VARCHAR(20) DEFAULT 'PENDING',
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    subtotal DECIMAL(15,2) NOT NULL,
    discount DECIMAL(15,2) DEFAULT 0,
    tax DECIMAL(15,2) DEFAULT 0,
    shipping_cost DECIMAL(15,2) DEFAULT 0,
    total DECIMAL(15,2) NOT NULL,
    promotion_code VARCHAR(50),
    shipping_address TEXT NOT NULL,
    shipping_phone VARCHAR(20),
    shipping_email VARCHAR(100),
    tracking_number VARCHAR(50),
    courier VARCHAR(50),
    notes TEXT,
    payment_method VARCHAR(30),
    payment_transaction_id VARCHAR(100),
    paid_at TIMESTAMP,
    delivered_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    cancellation_reason TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    whitelabel_id UUID
);

CREATE TABLE IF NOT EXISTS t_web_order_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES t_web_order(id) ON DELETE CASCADE,
    item_id UUID NOT NULL REFERENCES m_catalogue_item(id),
    part_id UUID REFERENCES m_part_master(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2) GENERATED ALWAYS AS ((quantity * unit_price) - discount) STORED,
    attributes JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS t_web_order_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES t_web_order(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID REFERENCES m_user(id),
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    whitelabel_id UUID
);

-- =============================================================================
-- 3. FUNCTIONS & TRIGGERS
-- =============================================================================

-- Job Number Generator
CREATE OR REPLACE FUNCTION generate_job_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(job_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_job WHERE job_no LIKE 'JOB-' || year_part || '-%';
    NEW.job_no := 'JOB-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_job_no ON t_job;
CREATE TRIGGER trg_generate_job_no BEFORE INSERT ON t_job FOR EACH ROW EXECUTE FUNCTION generate_job_no();

-- Customer Code Generator
CREATE OR REPLACE FUNCTION generate_customer_code()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(customer_code FROM 10) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM m_customer WHERE customer_code LIKE 'CUST-' || year_part || '-%';
    NEW.customer_code := 'CUST-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_customer_code ON m_customer;
CREATE TRIGGER trg_generate_customer_code BEFORE INSERT ON m_customer FOR EACH ROW EXECUTE FUNCTION generate_customer_code();

-- Quotation Number Generator
CREATE OR REPLACE FUNCTION generate_quotation_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(quotation_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_quotation WHERE quotation_no LIKE 'QT-' || year_part || '-%';
    NEW.quotation_no := 'QT-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_quotation_no ON t_quotation;
CREATE TRIGGER trg_generate_quotation_no BEFORE INSERT ON t_quotation FOR EACH ROW EXECUTE FUNCTION generate_quotation_no();

-- PO Number Generator
CREATE OR REPLACE FUNCTION generate_po_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(po_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_purchase_order_header WHERE po_no LIKE 'PO-' || year_part || '-%';
    NEW.po_no := 'PO-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_po_no ON t_purchase_order_header;
CREATE TRIGGER trg_generate_po_no BEFORE INSERT ON t_purchase_order_header FOR EACH ROW EXECUTE FUNCTION generate_po_no();

-- Payment Number Generator
CREATE OR REPLACE FUNCTION generate_payment_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(payment_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_payment WHERE payment_no LIKE 'PAY-' || year_part || '-%';
    NEW.payment_no := 'PAY-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_payment_no ON t_payment;
CREATE TRIGGER trg_generate_payment_no BEFORE INSERT ON t_payment FOR EACH ROW EXECUTE FUNCTION generate_payment_no();

-- Receipt Number Generator
CREATE OR REPLACE FUNCTION generate_receipt_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(receipt_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_receipt WHERE receipt_no LIKE 'RCP-' || year_part || '-%';
    NEW.receipt_no := 'RCP-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_receipt_no ON t_receipt;
CREATE TRIGGER trg_generate_receipt_no BEFORE INSERT ON t_receipt FOR EACH ROW EXECUTE FUNCTION generate_receipt_no();

-- Adjustment Number Generator
CREATE OR REPLACE FUNCTION generate_adjustment_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(adjustment_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_inventory_adjustment_header WHERE adjustment_no LIKE 'ADJ-' || year_part || '-%';
    NEW.adjustment_no := 'ADJ-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_adjustment_no ON t_inventory_adjustment_header;
CREATE TRIGGER trg_generate_adjustment_no BEFORE INSERT ON t_inventory_adjustment_header FOR EACH ROW EXECUTE FUNCTION generate_adjustment_no();

-- Picking Number Generator
CREATE OR REPLACE FUNCTION generate_picking_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(picking_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_part_picking_request WHERE picking_no LIKE 'PK-' || year_part || '-%';
    NEW.picking_no := 'PK-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_picking_no ON t_part_picking_request;
CREATE TRIGGER trg_generate_picking_no BEFORE INSERT ON t_part_picking_request FOR EACH ROW EXECUTE FUNCTION generate_picking_no();

-- Stocktake Number Generator
CREATE OR REPLACE FUNCTION generate_stocktake_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(stocktake_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_stocktake_header WHERE stocktake_no LIKE 'ST-' || year_part || '-%';
    NEW.stocktake_no := 'ST-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_stocktake_no ON t_stocktake_header;
CREATE TRIGGER trg_generate_stocktake_no BEFORE INSERT ON t_stocktake_header FOR EACH ROW EXECUTE FUNCTION generate_stocktake_no();

-- Document Number Generator
CREATE OR REPLACE FUNCTION generate_document_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(document_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_document WHERE document_no LIKE 'DOC-' || year_part || '-%';
    NEW.document_no := 'DOC-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_document_no ON t_document;
CREATE TRIGGER trg_generate_document_no BEFORE INSERT ON t_document FOR EACH ROW EXECUTE FUNCTION generate_document_no();

-- Auto Report Number Generator
CREATE OR REPLACE FUNCTION generate_auto_report_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(report_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_auto_report WHERE report_no LIKE 'RPT-' || year_part || '-%';
    NEW.report_no := 'RPT-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_auto_report_no ON t_auto_report;
CREATE TRIGGER trg_generate_auto_report_no BEFORE INSERT ON t_auto_report FOR EACH ROW EXECUTE FUNCTION generate_auto_report_no();

-- Email ID Generator
CREATE OR REPLACE FUNCTION generate_email_id()
RETURNS TRIGGER AS $$
DECLARE
    date_part TEXT;
    seq_part TEXT;
BEGIN
    date_part := TO_CHAR(NOW(), 'YYYYMMDD');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(email_id FROM 20) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_email_history WHERE email_id LIKE 'EMAIL-' || date_part || '-%';
    NEW.email_id := 'EMAIL-' || date_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_email_id ON t_email_history;
CREATE TRIGGER trg_generate_email_id BEFORE INSERT ON t_email_history FOR EACH ROW EXECUTE FUNCTION generate_email_id();

-- Web Order Number Generator
CREATE OR REPLACE FUNCTION generate_web_order_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(order_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_web_order WHERE order_no LIKE 'WO-' || year_part || '-%';
    NEW.order_no := 'WO-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_web_order_no ON t_web_order;
CREATE TRIGGER trg_generate_web_order_no BEFORE INSERT ON t_web_order FOR EACH ROW EXECUTE FUNCTION generate_web_order_no();

-- Invoice Number Generator
CREATE OR REPLACE FUNCTION generate_invoice_no()
RETURNS TRIGGER AS $$
DECLARE
    year_part TEXT;
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(invoice_no FROM 9) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_invoice_adjustment WHERE invoice_no LIKE 'INV-' || year_part || '-%';
    NEW.invoice_no := 'INV-' || year_part || '-' || seq_part;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generate_invoice_no ON t_invoice_adjustment;
CREATE TRIGGER trg_generate_invoice_no BEFORE INSERT ON t_invoice_adjustment FOR EACH ROW EXECUTE FUNCTION generate_invoice_no();

-- =============================================================================
-- 4. DASHBOARD VIEWS
-- =============================================================================

CREATE OR REPLACE VIEW v_dashboard_sales_overview AS
WITH sales_data AS (
    SELECT 
        i.id AS invoice_id,
        i.customer_id,
        i.job_id,
        i.invoice_date,
        i.total AS invoice_total,
        COALESCE((
            SELECT SUM(p.amount) FROM t_payment p 
            WHERE p.invoice_id = i.id AND p.status = 'COMPLETED'
        ), 0) AS amount_paid,
        i.whitelabel_id
    FROM t_invoice_adjustment i
    WHERE i.deleted = false AND i.type = 'INVOICE'
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

CREATE OR REPLACE VIEW v_dashboard_job_status AS
SELECT status, COUNT(*) AS count, whitelabel_id
FROM t_job WHERE deleted = false
GROUP BY status, whitelabel_id;

CREATE OR REPLACE VIEW v_dashboard_inventory_overview AS
SELECT 
    COUNT(*) AS total_parts,
    SUM(stock_quantity) AS total_quantity,
    COALESCE(SUM(stock_quantity * unit_cost), 0) AS total_value,
    COUNT(CASE WHEN stock_quantity <= reorder_level THEN 1 END) AS low_stock_count,
    COUNT(CASE WHEN status = 'ACTIVE' THEN 1 END) AS active_parts,
    whitelabel_id
FROM m_part_master WHERE deleted = false
GROUP BY whitelabel_id;

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

CREATE OR REPLACE VIEW v_dashboard_financial_summary AS
WITH invoice_data AS (
    SELECT DATE_TRUNC('month', invoice_date) AS month,
           COALESCE(SUM(total), 0) AS total_invoice,
           whitelabel_id
    FROM t_invoice_adjustment
    WHERE deleted = false AND type = 'INVOICE'
    GROUP BY DATE_TRUNC('month', invoice_date), whitelabel_id
),
payment_data AS (
    SELECT DATE_TRUNC('month', payment_date) AS month,
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

CREATE OR REPLACE VIEW v_available_languages AS
SELECT language_code, language_name, language_name_en, flag_emoji, is_rtl, is_default, locale, date_format, currency_symbol
FROM m_language WHERE is_active = true;

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

-- =============================================================================
-- 5. DEMO DATA
-- =============================================================================

-- 5.1 Insert Languages
INSERT INTO m_language (language_code, language_name, language_name_en, flag_emoji, is_rtl, is_default, sort_order, locale, date_format, currency_symbol, user_id, whitelabel_id) VALUES
('th', 'ภาษาไทย', 'Thai', '🇹🇭', false, true, 1, 'th_TH', 'dd/MM/yyyy', '฿', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('en', 'English', 'English', '🇬🇧', false, false, 2, 'en_US', 'MM/dd/yyyy', '$', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('zh', '中文', 'Chinese', '🇨🇳', false, false, 3, 'zh_CN', 'yyyy/MM/dd', '¥', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('ja', '日本語', 'Japanese', '🇯🇵', false, false, 4, 'ja_JP', 'yyyy/MM/dd', '¥', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.2 Insert Payment Methods
INSERT INTO m_payment_method (method_code, method_name, method_name_en, is_active, user_id, whitelabel_id) VALUES
('CASH', 'เงินสด', 'Cash', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('BANK_TRANSFER', 'โอนเงินผ่านธนาคาร', 'Bank Transfer', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('CREDIT_CARD', 'บัตรเครดิต', 'Credit Card', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('CHEQUE', 'เช็ค', 'Cheque', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('PROMPTPAY', 'พร้อมเพย์', 'PromptPay', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.3 Insert Currencies
INSERT INTO m_currency (currency_code, currency_name, symbol, is_default, user_id, whitelabel_id) VALUES
('THB', 'Thai Baht', '฿', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('USD', 'US Dollar', '$', false, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.4 Insert Country, Province, City
INSERT INTO m_country (id, country_code, country_name, user_id, whitelabel_id) VALUES (gen_random_uuid(), 'TH', 'Thailand', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO m_province (id, country_id, province_code, province_name, user_id, whitelabel_id) VALUES
(gen_random_uuid(), (SELECT id FROM m_country WHERE country_code='TH'), 'BKK', 'Bangkok', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), (SELECT id FROM m_country WHERE country_code='TH'), 'NON', 'Nonthaburi', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO m_city (id, province_id, city_code, city_name, user_id, whitelabel_id) VALUES
(gen_random_uuid(), (SELECT id FROM m_province WHERE province_code='BKK'), 'BKK01', 'Pathum Wan', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), (SELECT id FROM m_province WHERE province_code='BKK'), 'BKK02', 'Bang Na', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.5 Insert Categories
INSERT INTO m_category (category_code, category_name, category_name_en, is_active, user_id, whitelabel_id) VALUES
('ENGINE', 'ระบบเครื่องยนต์', 'Engine System', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('BRAKE', 'ระบบเบรก', 'Brake System', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('SUSPENSION', 'ระบบช่วงล่าง', 'Suspension System', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('ELECTRICAL', 'ระบบไฟฟ้า', 'Electrical System', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.6 Insert Services
INSERT INTO m_service (service_code, service_name, service_name_en, category_id, description, unit, unit_price, is_active, user_id, whitelabel_id) VALUES
('OIL_CHG', 'เปลี่ยนถ่ายน้ำมันเครื่อง', 'Oil Change', (SELECT id FROM m_category WHERE category_code='ENGINE'), 'เปลี่ยนถ่ายน้ำมันเครื่อง พร้อมกรอง', 'HOUR', 500.00, true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('BRAKE_PAD', 'เปลี่ยนผ้าเบรก', 'Brake Pad Replacement', (SELECT id FROM m_category WHERE category_code='BRAKE'), 'เปลี่ยนผ้าเบรกหน้า-หลัง', 'HOUR', 800.00, true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('SHOCK_ABS', 'เปลี่ยนโช้คอัพ', 'Shock Absorber Replacement', (SELECT id FROM m_category WHERE category_code='SUSPENSION'), 'เปลี่ยนโช้คอัพ 4 ข้าง', 'HOUR', 1200.00, true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('BATTERY', 'เปลี่ยนแบตเตอรี่', 'Battery Replacement', (SELECT id FROM m_category WHERE category_code='ELECTRICAL'), 'เปลี่ยนแบตเตอรี่รถยนต์', 'HOUR', 300.00, true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.7 Insert Suppliers
INSERT INTO m_supplier (id, supplier_code, name, tax_id, email, phone, address, status, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'SUP001', 'Toyota Parts Center', '1234567890123', 'info@toyotaparts.co.th', '02-123-4567', '123 Rama 9 Rd, Bangkok', 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'SUP002', 'Honda Genuine Parts', '9876543210987', 'sales@hondaparts.co.th', '02-765-4321', '456 Sukhumvit 71, Bangkok', 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.8 Insert Parts
INSERT INTO m_part_master (part_code, part_name, part_name_en, category_id, brand, model, unit, reorder_level, stock_quantity, unit_cost, selling_price, status, user_id, whitelabel_id) VALUES
('OIL-FILTER-001', 'ไส้กรองน้ำมันเครื่อง', 'Oil Filter', (SELECT id FROM m_category WHERE category_code='ENGINE'), 'Toyota', 'Corolla', 'PIECE', 10, 50, 150.00, 250.00, 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('OIL-5W30', 'น้ำมันเครื่อง 5W-30', 'Engine Oil 5W-30', (SELECT id FROM m_category WHERE category_code='ENGINE'), 'Toyota', 'Corolla', 'LITER', 20, 100, 200.00, 350.00, 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('BRAKE-PAD-FRONT', 'ผ้าเบรกหน้า', 'Brake Pad Front', (SELECT id FROM m_category WHERE category_code='BRAKE'), 'Honda', 'Civic', 'SET', 5, 20, 600.00, 950.00, 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('SHOCK-FRONT', 'โช้คอัพหน้า', 'Front Shock Absorber', (SELECT id FROM m_category WHERE category_code='SUSPENSION'), 'Toyota', 'Corolla', 'PIECE', 3, 8, 1200.00, 1800.00, 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('BATTERY-45AH', 'แบตเตอรี่ 45Ah', 'Battery 45Ah', (SELECT id FROM m_category WHERE category_code='ELECTRICAL'), 'GS', 'Common', 'PIECE', 5, 15, 800.00, 1300.00, 'ACTIVE', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.9 Insert Staff
INSERT INTO m_staff (id, staff_code, full_name, job_title, phone, email, hire_date, is_active, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'MEC001', 'Somchai Suksawat', 'Senior Mechanic', '081-234-5678', 'somchai@autorepair.com', '2020-01-15', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'MEC002', 'Somsak Pongpamorn', 'Junior Mechanic', '089-876-5432', 'somsak@autorepair.com', '2022-03-01', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.10 Insert Users (password: 'password' hashed with BCrypt? we use dummy)
INSERT INTO m_user (id, username, email, password_hash, full_name, status, phone_number, user_id, whitelabel_id) VALUES
('00000000-0000-0000-0000-000000000001', 'admin', 'admin@autorepair.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'System Admin', 'ACTIVE', '081-111-1111', NULL, NULL),
(gen_random_uuid(), 'service1', 'service1@autorepair.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Service Advisor 1', 'ACTIVE', '081-222-2222', NULL, NULL),
(gen_random_uuid(), 'mechanic1', 'mechanic1@autorepair.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Mechanic 1', 'ACTIVE', '081-333-3333', NULL, NULL);

-- 5.11 Insert Roles
INSERT INTO m_role (name, description, user_id, whitelabel_id) VALUES
('ADMIN', 'System Administrator', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('MANAGER', 'Shop Manager', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('SERVICE_ADVISOR', 'Service Advisor', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('MECHANIC', 'Mechanic', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.12 Insert Permissions (sample)
INSERT INTO m_permission (name, description, action, resource, user_id, whitelabel_id) VALUES
('JOB_CREATE', 'Create Job Card', 'CREATE', 'JOB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('JOB_READ', 'Read Job Card', 'READ', 'JOB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('JOB_UPDATE', 'Update Job Card', 'UPDATE', 'JOB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('JOB_DELETE', 'Delete Job Card', 'DELETE', 'JOB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('QUOTATION_CREATE', 'Create Quotation', 'CREATE', 'QUOTATION', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('PO_CREATE', 'Create Purchase Order', 'CREATE', 'PO', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('INVENTORY_READ', 'Read Inventory', 'READ', 'INVENTORY', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('INVENTORY_UPDATE', 'Update Inventory', 'UPDATE', 'INVENTORY', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.13 Assign Roles to Users
INSERT INTO m_user_role (user_id, role_id) VALUES
('00000000-0000-0000-0000-000000000001', (SELECT id FROM m_role WHERE name='ADMIN')),
((SELECT id FROM m_user WHERE username='service1'), (SELECT id FROM m_role WHERE name='SERVICE_ADVISOR')),
((SELECT id FROM m_user WHERE username='mechanic1'), (SELECT id FROM m_role WHERE name='MECHANIC'));

-- 5.14 Insert Customers
INSERT INTO m_customer (id, customer_code, full_name, display_name, customer_type, status, tax_id, email, phone_number, address, province, city, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'CUST-2026-0001', 'John Doe', 'John', 'INDIVIDUAL', 'ACTIVE', '1234567890123', 'john.doe@example.com', '081-111-2222', '123 Soi Sukhumvit 1, Bangkok', 'Bangkok', 'Pathum Wan', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'CUST-2026-0002', 'Jane Smith', 'Jane', 'INDIVIDUAL', 'ACTIVE', '9876543210987', 'jane.smith@example.com', '089-333-4444', '456 Ladprao 10, Bangkok', 'Bangkok', 'Bang Na', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'CUST-2026-0003', 'ABC Transport Co., Ltd.', 'ABC Transport', 'CORPORATE', 'ACTIVE', '1112223334445', 'abc@transport.com', '02-555-6666', '789 Ratchada Rd, Bangkok', 'Bangkok', 'Pathum Wan', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.15 Insert Cars
INSERT INTO m_car (customer_id, license_plate, province, brand, model, sub_model, year, color, fuel_type, transmission_type, mileage, user_id, whitelabel_id) VALUES
((SELECT id FROM m_customer WHERE customer_code='CUST-2026-0001'), '1กก 1234', 'Bangkok', 'Toyota', 'Corolla', '1.8 Hybrid', 2022, 'White', 'HYBRID', 'CVT', 45000, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM m_customer WHERE customer_code='CUST-2026-0002'), '2ขข 5678', 'Bangkok', 'Honda', 'Civic', 'RS', 2023, 'Black', 'GASOLINE', 'CVT', 23000, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM m_customer WHERE customer_code='CUST-2026-0003'), '3คค 9012', 'Nonthaburi', 'Isuzu', 'D-Max', 'Hi-Lander', 2021, 'Silver', 'DIESEL', 'AUTOMATIC', 120000, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.16 Insert Jobs
INSERT INTO t_job (id, job_no, customer_id, car_id, mechanic_id, status, start_date, symptom, diagnosis_note, mileage, estimated_cost, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'JOB-2026-0001', (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0001'), (SELECT id FROM m_car WHERE license_plate='1กก 1234'), (SELECT id FROM m_staff WHERE staff_code='MEC001'), 'IN_PROGRESS', NOW() - INTERVAL '1 day', 'Engine warning light on', 'Check engine code P0300', 45000, 3500.00, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'JOB-2026-0002', (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0002'), (SELECT id FROM m_car WHERE license_plate='2ขข 5678'), (SELECT id FROM m_staff WHERE staff_code='MEC002'), 'QUOTATION_PENDING', NOW() - INTERVAL '2 hours', 'Brake squeaking noise', 'Check brake pads and rotors', 23000, 2500.00, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'JOB-2026-0003', (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0003'), (SELECT id FROM m_car WHERE license_plate='3คค 9012'), (SELECT id FROM m_staff WHERE staff_code='MEC001'), 'OPEN', NOW() - INTERVAL '30 minutes', 'Noise from front suspension', 'Inspect front shocks and ball joints', 120000, 5000.00, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.17 Insert Job Services
INSERT INTO t_job_service (job_id, service_id, quantity, unit_price, discount, user_id, whitelabel_id) VALUES
((SELECT id FROM t_job WHERE job_no='JOB-2026-0001'), (SELECT id FROM m_service WHERE service_code='OIL_CHG'), 1, 500.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_job WHERE job_no='JOB-2026-0001'), (SELECT id FROM m_service WHERE service_code='BRAKE_PAD'), 1, 800.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_job WHERE job_no='JOB-2026-0002'), (SELECT id FROM m_service WHERE service_code='BRAKE_PAD'), 1, 800.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.18 Insert Job Parts
INSERT INTO t_job_part_sales (job_id, part_id, quantity, unit_price, discount, user_id, whitelabel_id) VALUES
((SELECT id FROM t_job WHERE job_no='JOB-2026-0001'), (SELECT id FROM m_part_master WHERE part_code='OIL-FILTER-001'), 1, 250.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_job WHERE job_no='JOB-2026-0001'), (SELECT id FROM m_part_master WHERE part_code='OIL-5W30'), 4, 350.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_job WHERE job_no='JOB-2026-0002'), (SELECT id FROM m_part_master WHERE part_code='BRAKE-PAD-FRONT'), 1, 950.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.19 Insert Quotations
INSERT INTO t_quotation (id, quotation_no, job_id, customer_id, quotation_date, expiry_date, status, subtotal, tax_rate, tax_amount, total, currency, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'QT-2026-0001', (SELECT id FROM t_job WHERE job_no='JOB-2026-0001'), (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0001'), NOW(), NOW() + INTERVAL '7 days', 'PENDING', 3500.00, 7.00, 245.00, 3745.00, 'THB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
(gen_random_uuid(), 'QT-2026-0002', (SELECT id FROM t_job WHERE job_no='JOB-2026-0002'), (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0002'), NOW(), NOW() + INTERVAL '7 days', 'DRAFT', 2500.00, 7.00, 175.00, 2675.00, 'THB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.20 Insert Quotation Parts
INSERT INTO t_quotation_part (quotation_id, part_id, quantity, unit_price, discount, user_id, whitelabel_id) VALUES
((SELECT id FROM t_quotation WHERE quotation_no='QT-2026-0001'), (SELECT id FROM m_part_master WHERE part_code='OIL-FILTER-001'), 1, 250.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_quotation WHERE quotation_no='QT-2026-0001'), (SELECT id FROM m_part_master WHERE part_code='OIL-5W30'), 4, 350.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_quotation WHERE quotation_no='QT-2026-0002'), (SELECT id FROM m_part_master WHERE part_code='BRAKE-PAD-FRONT'), 1, 950.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.21 Insert Purchase Orders
INSERT INTO t_purchase_order_header (id, po_no, quotation_id, supplier_id, po_date, expected_delivery_date, status, subtotal, tax_rate, tax_amount, total, currency, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'PO-2026-0001', (SELECT id FROM t_quotation WHERE quotation_no='QT-2026-0001'), (SELECT id FROM m_supplier WHERE supplier_code='SUP001'), NOW(), NOW() + INTERVAL '5 days', 'SENT', 3500.00, 7.00, 245.00, 3745.00, 'THB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.22 Insert Purchase Order Details
INSERT INTO t_purchase_order_detail (po_header_id, part_id, quantity_ordered, unit_price, discount, user_id, whitelabel_id) VALUES
((SELECT id FROM t_purchase_order_header WHERE po_no='PO-2026-0001'), (SELECT id FROM m_part_master WHERE part_code='OIL-FILTER-001'), 10, 150.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM t_purchase_order_header WHERE po_no='PO-2026-0001'), (SELECT id FROM m_part_master WHERE part_code='OIL-5W30'), 20, 200.00, 0, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.23 Insert Payments
INSERT INTO t_payment (id, payment_no, invoice_id, customer_id, payment_date, payment_method_id, amount, amount_received, currency, status, received_by, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'PAY-2026-0001', NULL, (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0001'), NOW(), (SELECT id FROM m_payment_method WHERE method_code='CASH'), 3745.00, 3745.00, 'THB', 'COMPLETED', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.24 Insert Receipts
INSERT INTO t_receipt (id, receipt_no, payment_id, customer_id, receipt_date, amount, currency, status, issued_by, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'RCP-2026-0001', (SELECT id FROM t_payment WHERE payment_no='PAY-2026-0001'), (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0001'), NOW(), 3745.00, 'THB', 'ISSUED', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.25 Insert Invoices
INSERT INTO t_invoice_adjustment (id, invoice_no, job_id, customer_id, invoice_date, type, subtotal, tax_rate, tax_amount, total, currency, user_id, whitelabel_id) VALUES
(gen_random_uuid(), 'INV-2026-0001', (SELECT id FROM t_job WHERE job_no='JOB-2026-0001'), (SELECT id FROM m_customer WHERE customer_code='CUST-2026-0001'), NOW(), 'INVOICE', 3500.00, 7.00, 245.00, 3745.00, 'THB', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.26 Insert Batch Jobs
INSERT INTO m_batch_job (job_code, job_name, job_type, description, cron_expression, enabled, user_id, whitelabel_id) VALUES
('batch001', 'ส่งอีเมลแจ้งเตือนรายวัน', 'EMAIL', 'ส่งอีเมลแจ้งเตือนรายวัน', '0 30 6 * * *', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('batch002', 'สร้างรายงานประจำวัน', 'REPORT', 'สร้างรายงานสรุปประจำวัน', '0 45 6 * * *', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('batch003', 'อัปเดตสถานะงานค้าง', 'UPDATE', 'อัปเดตสถานะงานที่ค้างนานเกินไป', '0 30 6 * * *', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('batch004', 'ล้างข้อมูล/ซิงค์ฐานข้อมูล', 'CLEANUP', 'ล้างข้อมูลเก่าและซิงค์ฐานข้อมูล', '0 0 3 * * *', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('batch005', 'ซิงค์ข้อมูล Realtime', 'SYNC', 'ซิงค์ข้อมูล Realtime กับระบบภายนอก', '0 0/30 * * * *', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('batch006', 'ส่งสรุปยอดขาย', 'SUMMARY', 'ส่งสรุปยอดขายประจำวัน', '0 30 6 * * *', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.27 Insert Catalogue Categories
INSERT INTO m_catalogue_category (category_code, category_name, category_name_en, is_active, user_id, whitelabel_id) VALUES
('WOS_ENGINE', 'อะไหล่เครื่องยนต์', 'Engine Parts', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('WOS_BRAKE', 'อะไหล่เบรก', 'Brake Parts', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.28 Insert Catalogue Items
INSERT INTO m_catalogue_item (item_code, item_name, item_name_en, category_id, part_id, description, is_active, is_featured, is_new, user_id, whitelabel_id) VALUES
('WOS-001', 'ไส้กรองน้ำมันเครื่อง', 'Oil Filter', (SELECT id FROM m_catalogue_category WHERE category_code='WOS_ENGINE'), (SELECT id FROM m_part_master WHERE part_code='OIL-FILTER-001'), 'ไส้กรองน้ำมันเครื่องคุณภาพสูง', true, true, true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('WOS-002', 'น้ำมันเครื่อง 5W-30', 'Engine Oil 5W-30', (SELECT id FROM m_catalogue_category WHERE category_code='WOS_ENGINE'), (SELECT id FROM m_part_master WHERE part_code='OIL-5W30'), 'น้ำมันเครื่องสังเคราะห์ 100%', true, true, false, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.29 Insert Sales Prices
INSERT INTO m_sales_price (item_id, price_tier, unit_price, currency, effective_date, is_active, user_id, whitelabel_id) VALUES
((SELECT id FROM m_catalogue_item WHERE item_code='WOS-001'), 'DEFAULT', 250.00, 'THB', NOW(), true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
((SELECT id FROM m_catalogue_item WHERE item_code='WOS-002'), 'DEFAULT', 350.00, 'THB', NOW(), true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.30 Insert Promotions
INSERT INTO m_promotion (promotion_code, promotion_name, promotion_type, discount_value, start_date, end_date, is_active, user_id, whitelabel_id) VALUES
('SAVE10', 'ส่วนลด 10%', 'PERCENTAGE', 10.00, NOW(), NOW() + INTERVAL '30 days', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.31 Insert Email Templates (sample)
INSERT INTO m_email_template (template_code, template_name, subject, category, language, is_active, user_id, whitelabel_id) VALUES
('QUOTATION_EMAIL', 'ใบเสนอราคา', 'ใบเสนอราคา #{quotationNo} - {customerName}', 'QUOTATION', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('INVOICE_EMAIL', 'ใบแจ้งหนี้', 'ใบแจ้งหนี้ #{invoiceNo} - {customerName}', 'INVOICE', 'th', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.32 Insert Translations (sample)
INSERT INTO m_translation (message_key, language_code, message_text, context, user_id, whitelabel_id) VALUES
('job.status.open', 'th', 'เปิดใบงาน', 'UI', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('job.status.open', 'en', 'Open', 'UI', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.33 Insert Stock Locations
INSERT INTO m_stock_location (location_code, location_name, location_type, zone, is_active, user_id, whitelabel_id) VALUES
('A-01', 'Shelf A-01', 'SHELF', 'Zone A', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
('B-03', 'Rack B-03', 'RACK', 'Zone B', true, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- 5.34 Insert Shop Profile
INSERT INTO m_shop_profile (shop_name, shop_name_en, address, phone, email, tax_id, user_id, whitelabel_id) VALUES
('ICMON Auto Repair', 'ICMON Auto Repair Center', '123 Main Street, Bangkok', '02-123-4567', 'info@icmon.co.th', '1234567890123', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- =============================================================================
-- 6. FINAL COMMIT
-- =============================================================================

COMMIT; 