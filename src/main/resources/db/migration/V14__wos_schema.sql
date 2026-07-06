-- ==============================================
-- Web Order System (WOS) Schema
-- Module 14: Catalogue, Cart, Order, Sales Price, Promotion
-- ==============================================

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
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_catalogue_category_code ON m_catalogue_category(category_code);
CREATE INDEX IF NOT EXISTS idx_m_catalogue_category_parent ON m_catalogue_category(parent_id);
CREATE INDEX IF NOT EXISTS idx_m_catalogue_category_active ON m_catalogue_category(is_active);

CREATE TABLE IF NOT EXISTS m_catalogue_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    item_code VARCHAR(50) UNIQUE NOT NULL,
    item_name VARCHAR(200) NOT NULL,
    item_name_en VARCHAR(200),
    category_id UUID REFERENCES m_catalogue_category(id),
    part_id UUID,
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
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_catalogue_item_code ON m_catalogue_item(item_code);
CREATE INDEX IF NOT EXISTS idx_m_catalogue_item_category ON m_catalogue_item(category_id);
CREATE INDEX IF NOT EXISTS idx_m_catalogue_item_part ON m_catalogue_item(part_id);
CREATE INDEX IF NOT EXISTS idx_m_catalogue_item_active ON m_catalogue_item(is_active);
CREATE INDEX IF NOT EXISTS idx_m_catalogue_item_whitelabel ON m_catalogue_item(whitelabel_id);

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
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_sales_price_item ON m_sales_price(item_id);
CREATE INDEX IF NOT EXISTS idx_m_sales_price_tier ON m_sales_price(price_tier);
CREATE INDEX IF NOT EXISTS idx_m_sales_price_active ON m_sales_price(is_active);

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
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_m_promotion_code ON m_promotion(promotion_code);
CREATE INDEX IF NOT EXISTS idx_m_promotion_active ON m_promotion(is_active);
CREATE INDEX IF NOT EXISTS idx_m_promotion_date ON m_promotion(start_date, end_date);

CREATE TABLE IF NOT EXISTS t_shopping_cart (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id VARCHAR(50) UNIQUE NOT NULL,
    customer_id UUID,
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
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_shopping_cart_id ON t_shopping_cart(cart_id);
CREATE INDEX IF NOT EXISTS idx_t_shopping_cart_customer ON t_shopping_cart(customer_id);
CREATE INDEX IF NOT EXISTS idx_t_shopping_cart_expires ON t_shopping_cart(expires_at);

CREATE TABLE IF NOT EXISTS t_shopping_cart_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id UUID NOT NULL REFERENCES t_shopping_cart(id) ON DELETE CASCADE,
    item_id UUID NOT NULL REFERENCES m_catalogue_item(id) ON DELETE RESTRICT,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2),
    attributes JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_t_cart_item_cart ON t_shopping_cart_item(cart_id);
CREATE INDEX IF NOT EXISTS idx_t_cart_item_item ON t_shopping_cart_item(item_id);

CREATE TABLE IF NOT EXISTS t_web_order (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_no VARCHAR(30) UNIQUE NOT NULL,
    cart_id UUID REFERENCES t_shopping_cart(id),
    customer_id UUID NOT NULL,
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
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_web_order_no ON t_web_order(order_no);
CREATE INDEX IF NOT EXISTS idx_t_web_order_customer ON t_web_order(customer_id);
CREATE INDEX IF NOT EXISTS idx_t_web_order_status ON t_web_order(status);
CREATE INDEX IF NOT EXISTS idx_t_web_order_payment_status ON t_web_order(payment_status);
CREATE INDEX IF NOT EXISTS idx_t_web_order_date ON t_web_order(order_date);
CREATE INDEX IF NOT EXISTS idx_t_web_order_deleted ON t_web_order(deleted);

CREATE TABLE IF NOT EXISTS t_web_order_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES t_web_order(id) ON DELETE CASCADE,
    item_id UUID NOT NULL REFERENCES m_catalogue_item(id) ON DELETE RESTRICT,
    part_id UUID,
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    total_price DECIMAL(15,2),
    discount DECIMAL(15,2) DEFAULT 0,
    net_price DECIMAL(15,2),
    attributes JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_t_web_order_item_order ON t_web_order_item(order_id);
CREATE INDEX IF NOT EXISTS idx_t_web_order_item_item ON t_web_order_item(item_id);

CREATE TABLE IF NOT EXISTS t_web_order_status_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES t_web_order(id) ON DELETE CASCADE,
    from_status VARCHAR(20),
    to_status VARCHAR(20) NOT NULL,
    changed_by UUID,
    changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    reason TEXT,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    whitelabel_id UUID NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_t_web_order_status_order ON t_web_order_status_history(order_id);
CREATE INDEX IF NOT EXISTS idx_t_web_order_status_changed ON t_web_order_status_history(changed_at);

-- Demo data
INSERT INTO m_catalogue_category (id, category_code, category_name, category_name_en, level, sort_order, is_active, user_id, whitelabel_id)
VALUES
    (gen_random_uuid(), 'BRAKE', 'ระบบเบรก', 'Brake System', 1, 1, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    (gen_random_uuid(), 'ENGINE', 'เครื่องยนต์', 'Engine', 1, 2, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    (gen_random_uuid(), 'SUSPENSION', 'ระบบกันสะเทือน', 'Suspension', 1, 3, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    (gen_random_uuid(), 'ELECTRIC', 'ระบบไฟฟ้า', 'Electrical', 1, 4, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    (gen_random_uuid(), 'FILTER', 'ไส้กรอง', 'Filter', 1, 5, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

INSERT INTO m_catalogue_item (id, item_code, item_name, item_name_en, category_id, short_description, brand, is_active, is_featured, is_new, sort_order, user_id, whitelabel_id)
SELECT gen_random_uuid(), 'BRK-001', 'ผ้าเบรกหน้า TOYOTA VIOS', 'Front Brake Pad TOYOTA VIOS', c.id, 'ผ้าเบรกหน้าคุณภาพสูง อายุการใช้งานยาวนาน', 'TOYOTA', TRUE, TRUE, FALSE, 1, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_category c WHERE c.category_code = 'BRAKE';

INSERT INTO m_catalogue_item (id, item_code, item_name, item_name_en, category_id, short_description, brand, is_active, is_featured, is_new, sort_order, user_id, whitelabel_id)
SELECT gen_random_uuid(), 'OIL-001', 'น้ำมันเครื่อง 5W-30', 'Engine Oil 5W-30', c.id, 'น้ำมันเครื่องคุณภาพสูง สำหรับเครื่องยนต์เบนซิน', 'CASTROL', TRUE, TRUE, TRUE, 1, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_category c WHERE c.category_code = 'ENGINE';

INSERT INTO m_catalogue_item (id, item_code, item_name, item_name_en, category_id, short_description, brand, is_active, is_featured, is_new, sort_order, user_id, whitelabel_id)
SELECT gen_random_uuid(), 'FLT-001', 'ไส้กรองน้ำมันเครื่อง', 'Oil Filter', c.id, 'ไส้กรองน้ำมันเครื่อง คุณภาพสูง', 'TOYOTA', TRUE, FALSE, FALSE, 1, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_category c WHERE c.category_code = 'FILTER';

INSERT INTO m_catalogue_item (id, item_code, item_name, item_name_en, category_id, short_description, brand, is_active, is_featured, is_new, sort_order, user_id, whitelabel_id)
SELECT gen_random_uuid(), 'BAT-001', 'แบตเตอรี่ HB-550', 'Battery HB-550', c.id, 'แบตเตอรี่แห้ง ขนาด 550 CCA', 'GS', TRUE, FALSE, FALSE, 1, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_category c WHERE c.category_code = 'ELECTRIC';

INSERT INTO m_catalogue_item (id, item_code, item_name, item_name_en, category_id, short_description, brand, is_active, is_featured, is_new, sort_order, user_id, whitelabel_id)
SELECT gen_random_uuid(), 'SHP-001', 'โช้คหน้า TOYOTA VIOS', 'Front Shock Absorber TOYOTA VIOS', c.id, 'โช้คหน้าคุณภาพสูง ใช้งานได้ยาวนาน', 'TOYOTA', TRUE, TRUE, FALSE, 1, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_category c WHERE c.category_code = 'SUSPENSION';

INSERT INTO m_sales_price (id, item_id, price_tier, unit_price, currency, min_quantity, is_active, user_id, whitelabel_id)
SELECT gen_random_uuid(), ci.id, 'DEFAULT', 1500.00, 'THB', 1, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_item ci WHERE ci.item_code = 'BRK-001';

INSERT INTO m_sales_price (id, item_id, price_tier, unit_price, currency, min_quantity, is_active, user_id, whitelabel_id)
SELECT gen_random_uuid(), ci.id, 'DEFAULT', 850.00, 'THB', 1, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_item ci WHERE ci.item_code = 'OIL-001';

INSERT INTO m_sales_price (id, item_id, price_tier, unit_price, currency, min_quantity, is_active, user_id, whitelabel_id)
SELECT gen_random_uuid(), ci.id, 'DEFAULT', 250.00, 'THB', 1, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_item ci WHERE ci.item_code = 'FLT-001';

INSERT INTO m_sales_price (id, item_id, price_tier, unit_price, currency, min_quantity, is_active, user_id, whitelabel_id)
SELECT gen_random_uuid(), ci.id, 'DEFAULT', 3500.00, 'THB', 1, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_item ci WHERE ci.item_code = 'BAT-001';

INSERT INTO m_sales_price (id, item_id, price_tier, unit_price, currency, min_quantity, is_active, user_id, whitelabel_id)
SELECT gen_random_uuid(), ci.id, 'DEFAULT', 2800.00, 'THB', 1, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'
FROM m_catalogue_item ci WHERE ci.item_code = 'SHP-001';

INSERT INTO m_promotion (id, promotion_code, promotion_name, promotion_type, discount_value, min_order_amount, max_discount, start_date, end_date, usage_limit, is_active, user_id, whitelabel_id)
VALUES
    (gen_random_uuid(), 'WELCOME10', 'ส่วนลด 10% สำหรับสมาชิกใหม่', 'PERCENTAGE', 10.00, 500.00, 500.00, NOW(), NOW() + INTERVAL '30 days', 100, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    (gen_random_uuid(), 'FREE100', 'ส่วนลด 100 บาท เมื่อซื้อครบ 1000 บาท', 'FIXED', 100.00, 1000.00, 100.00, NOW(), NOW() + INTERVAL '60 days', 200, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001'),
    (gen_random_uuid(), 'SALE20', 'ส่วนลด 20% สินค้าคัดสรร', 'PERCENTAGE', 20.00, 300.00, 1000.00, NOW(), NOW() + INTERVAL '15 days', 50, TRUE, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
