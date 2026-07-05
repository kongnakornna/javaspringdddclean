/*
 Navicat Premium Dump SQL

 Source Server         : postgres-localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 180003 (180003)
 Source Host           : localhost:5432
 Source Catalog        : icmonapp
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 180003 (180003)
 File Encoding         : 65001

 Date: 05/07/2026 23:41:53
*/


-- ----------------------------
-- Table structure for d_widget_config
-- ----------------------------
DROP TABLE IF EXISTS "public"."d_widget_config";
CREATE TABLE "public"."d_widget_config" (
  "id" uuid NOT NULL,
  "created_at" timestamp(6),
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL,
  "whitelabel_id" uuid NOT NULL,
  "config" jsonb,
  "height" int4,
  "is_active" bool,
  "position" int4,
  "widget_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "widget_title" varchar(100) COLLATE "pg_catalog"."default",
  "widget_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "width" int4
)
;

-- ----------------------------
-- Records of d_widget_config
-- ----------------------------

-- ----------------------------
-- Table structure for m_batch_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_batch_job";
CREATE TABLE "public"."m_batch_job" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "job_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "description" text COLLATE "pg_catalog"."default",
  "cron_expression" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" bool DEFAULT true,
  "max_retry" int4 DEFAULT 3,
  "retry_delay_ms" int4 DEFAULT 60000,
  "timeout_seconds" int4 DEFAULT 300,
  "last_run_time" timestamp(6),
  "next_run_time" timestamp(6),
  "total_runs" int4 DEFAULT 0,
  "last_status" varchar(20) COLLATE "pg_catalog"."default",
  "parameters" jsonb,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of m_batch_job
-- ----------------------------

-- ----------------------------
-- Table structure for m_car
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_car";
CREATE TABLE "public"."m_car" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "customer_id" uuid NOT NULL,
  "license_plate" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "province" varchar(50) COLLATE "pg_catalog"."default",
  "brand" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "model" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "sub_model" varchar(255) COLLATE "pg_catalog"."default",
  "year" int4,
  "color" varchar(30) COLLATE "pg_catalog"."default",
  "engine_number" varchar(255) COLLATE "pg_catalog"."default",
  "chassis_number" varchar(255) COLLATE "pg_catalog"."default",
  "fuel_type" varchar(255) COLLATE "pg_catalog"."default",
  "transmission_type" varchar(255) COLLATE "pg_catalog"."default",
  "engine_cc" int4,
  "seating_capacity" int4,
  "mileage" int4 DEFAULT 0,
  "last_service_date" timestamp(6),
  "next_service_mileage" int4,
  "notes" text COLLATE "pg_catalog"."default",
  "is_active" bool DEFAULT true,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_car
-- ----------------------------

-- ----------------------------
-- Table structure for m_car_service_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_car_service_history";
CREATE TABLE "public"."m_car_service_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "car_id" uuid NOT NULL,
  "job_id" uuid,
  "service_date" timestamp(6) NOT NULL,
  "service_type" varchar(255) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "total_cost" numeric(15,2),
  "mileage_at_service" int4,
  "mechanic_name" varchar(255) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_car_service_history
-- ----------------------------

-- ----------------------------
-- Table structure for m_catalogue_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_catalogue_category";
CREATE TABLE "public"."m_catalogue_category" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "category_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "category_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "category_name_en" varchar(100) COLLATE "pg_catalog"."default",
  "parent_id" uuid,
  "level" int4 DEFAULT 0,
  "sort_order" int4 DEFAULT 0,
  "icon_url" text COLLATE "pg_catalog"."default",
  "is_active" bool DEFAULT true,
  "description" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_catalogue_category
-- ----------------------------
INSERT INTO "public"."m_catalogue_category" VALUES ('00000000-0000-0000-0000-000000000001', 'CAT_ENGINE', 'อะไหล่เครื่องยนต์', 'Engine Parts', NULL, 0, 1, NULL, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_category" VALUES ('00000000-0000-0000-0000-000000000002', 'CAT_BRAKE', 'ระบบเบรก', 'Brake System', NULL, 0, 2, NULL, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_category" VALUES ('00000000-0000-0000-0000-000000000003', 'CAT_ELECTRIC', 'ระบบไฟฟ้า', 'Electrical', NULL, 0, 3, NULL, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_category" VALUES ('00000000-0000-0000-0000-000000000004', 'CAT_SUSPENSION', 'ระบบช่วงล่าง', 'Suspension', NULL, 0, 4, NULL, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_category" VALUES ('00000000-0000-0000-0000-000000000005', 'CAT_BODY', 'ตัวถัง', 'Body Parts', NULL, 0, 5, NULL, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- ----------------------------
-- Table structure for m_catalogue_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_catalogue_item";
CREATE TABLE "public"."m_catalogue_item" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "item_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "item_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "item_name_en" varchar(200) COLLATE "pg_catalog"."default",
  "category_id" uuid,
  "part_id" uuid,
  "description" text COLLATE "pg_catalog"."default",
  "short_description" varchar(500) COLLATE "pg_catalog"."default",
  "brand" varchar(100) COLLATE "pg_catalog"."default",
  "model_compatibility" text COLLATE "pg_catalog"."default",
  "image_url" text COLLATE "pg_catalog"."default",
  "gallery_images" jsonb,
  "is_active" bool DEFAULT true,
  "is_featured" bool DEFAULT false,
  "is_new" bool DEFAULT false,
  "sort_order" int4 DEFAULT 0,
  "tags" text[] COLLATE "pg_catalog"."default",
  "metadata" jsonb,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_catalogue_item
-- ----------------------------
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000010', 'OIL_FILTER', 'กรองน้ำมันเครื่อง', 'Oil Filter', '00000000-0000-0000-0000-000000000001', NULL, 'กรองน้ำมันเครื่องคุณภาพสูง สำหรับเครื่องยนต์เบนซินและดีเซล', NULL, 'Bosch', NULL, NULL, NULL, 't', 'f', 'f', 1, '{oil,filter,engine}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000011', 'AIR_FILTER', 'กรองอากาศ', 'Air Filter', '00000000-0000-0000-0000-000000000001', NULL, 'กรองอากาศประสิทธิภาพสูง ช่วยเพิ่มกำลังเครื่องยนต์', NULL, 'Denso', NULL, NULL, NULL, 't', 'f', 'f', 2, '{air,filter,engine}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000012', 'BRAKE_PAD', 'ผ้าเบรก', 'Brake Pad', '00000000-0000-0000-0000-000000000002', NULL, 'ผ้าเบรกคุณภาพสูง ไร้ฝุ่น เบรกเงียบ', NULL, 'Brembo', NULL, NULL, NULL, 't', 'f', 'f', 1, '{brake,pad,safety}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000013', 'BRAKE_DISC', 'จานเบรก', 'Brake Disc', '00000000-0000-0000-0000-000000000002', NULL, 'จานเบรกแบบเจาะรู ระบายความร้อนได้ดี', NULL, 'Brembo', NULL, NULL, NULL, 't', 'f', 'f', 2, '{brake,disc,rotor}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000014', 'SPARK_PLUG', 'หัวเทียน', 'Spark Plug', '00000000-0000-0000-0000-000000000003', NULL, 'หัวเทียนอิริเดียม อายุการใช้งานยาวนาน', NULL, 'NGK', NULL, NULL, NULL, 't', 'f', 'f', 1, '{spark,plug,ignition}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000015', 'BATTERY', 'แบตเตอรี่', 'Battery', '00000000-0000-0000-0000-000000000003', NULL, 'แบตเตอรี่แห้ง ไม่ต้องเติมน้ำกลั่น', NULL, 'GS', NULL, NULL, NULL, 't', 'f', 'f', 2, '{battery,electrical,power}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000016', 'SHOCK_ABSORBER', 'โช้คอัพ', 'Shock Absorber', '00000000-0000-0000-0000-000000000004', NULL, 'โช้คอัพแก๊ส นุ่มนวลทุกเส้นทาง', NULL, 'KYB', NULL, NULL, NULL, 't', 'f', 'f', 1, '{shock,absorber,suspension}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000017', 'SUSPENSION_ARM', 'ปีกนก', 'Suspension Arm', '00000000-0000-0000-0000-000000000004', NULL, 'ปีกนกคุณภาพสูง แข็งแรง ทนทาน', NULL, 'Lemforder', NULL, NULL, NULL, 't', 'f', 'f', 2, '{arm,control,suspension}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000018', 'HEADLIGHT', 'ไฟหน้า', 'Headlight', '00000000-0000-0000-0000-000000000005', NULL, 'ไฟหน้า LED สว่างไสว ปลอดภัยทุกการขับขี่', NULL, 'Philips', NULL, NULL, NULL, 't', 'f', 'f', 1, '{headlight,lighting,body}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_catalogue_item" VALUES ('00000000-0000-0000-0000-000000000019', 'BUMPER', 'กันชน', 'Bumper', '00000000-0000-0000-0000-000000000005', NULL, 'กันชนแท้ วัสดุคุณภาพดี ทนทาน', NULL, 'Toyota Genuine', NULL, NULL, NULL, 't', 'f', 'f', 2, '{bumper,body,exterior}', NULL, '2026-07-05 22:40:53.140776', NULL, NULL, 'f', '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- ----------------------------
-- Table structure for m_category
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_category";
CREATE TABLE "public"."m_category" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "category_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "category_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "category_name_en" varchar(100) COLLATE "pg_catalog"."default",
  "parent_id" uuid,
  "level" int4 DEFAULT 0,
  "sort_order" int4 DEFAULT 0,
  "icon_url" text COLLATE "pg_catalog"."default",
  "is_active" bool DEFAULT true,
  "description" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_category
-- ----------------------------

-- ----------------------------
-- Table structure for m_city
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_city";
CREATE TABLE "public"."m_city" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "province_id" uuid,
  "city_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "city_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_city
-- ----------------------------

-- ----------------------------
-- Table structure for m_country
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_country";
CREATE TABLE "public"."m_country" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "country_code" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "country_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_country
-- ----------------------------

-- ----------------------------
-- Table structure for m_currency
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_currency";
CREATE TABLE "public"."m_currency" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "currency_code" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "currency_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "symbol" varchar(10) COLLATE "pg_catalog"."default",
  "is_default" bool DEFAULT false,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_currency
-- ----------------------------

-- ----------------------------
-- Table structure for m_customer
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_customer";
CREATE TABLE "public"."m_customer" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "customer_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "display_name" varchar(255) COLLATE "pg_catalog"."default",
  "customer_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'INDIVIDUAL'::character varying,
  "status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'ACTIVE'::character varying,
  "tax_id" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "phone_number" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "secondary_phone" varchar(255) COLLATE "pg_catalog"."default",
  "address" text COLLATE "pg_catalog"."default",
  "province" varchar(100) COLLATE "pg_catalog"."default",
  "city" varchar(100) COLLATE "pg_catalog"."default",
  "district" varchar(100) COLLATE "pg_catalog"."default",
  "postal_code" varchar(10) COLLATE "pg_catalog"."default",
  "country" varchar(50) COLLATE "pg_catalog"."default" DEFAULT 'Thailand'::character varying,
  "contact_person" varchar(255) COLLATE "pg_catalog"."default",
  "contact_phone" varchar(255) COLLATE "pg_catalog"."default",
  "notes" text COLLATE "pg_catalog"."default",
  "last_visit_date" timestamp(6),
  "total_visit_count" int4 DEFAULT 0,
  "total_spent" numeric(15,2) DEFAULT 0,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_customer
-- ----------------------------

-- ----------------------------
-- Table structure for m_document_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_document_template";
CREATE TABLE "public"."m_document_template" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "template_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "template_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "template_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "file_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "file_path" text COLLATE "pg_catalog"."default" NOT NULL,
  "file_size" int8,
  "version" int4 DEFAULT 1,
  "description" text COLLATE "pg_catalog"."default",
  "is_active" bool DEFAULT true,
  "is_default" bool DEFAULT false,
  "parameters" jsonb,
  "preview_image_url" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "uploaded_at" timestamp(6)
)
;

-- ----------------------------
-- Records of m_document_template
-- ----------------------------

-- ----------------------------
-- Table structure for m_email_template
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_email_template";
CREATE TABLE "public"."m_email_template" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "template_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "template_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "subject" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "body_html" text COLLATE "pg_catalog"."default",
  "body_text" text COLLATE "pg_catalog"."default",
  "from_email" varchar(100) COLLATE "pg_catalog"."default",
  "from_name" varchar(100) COLLATE "pg_catalog"."default",
  "category" varchar(50) COLLATE "pg_catalog"."default",
  "language" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'th'::character varying,
  "version" int4 DEFAULT 1,
  "is_active" bool DEFAULT true,
  "is_default" bool DEFAULT false,
  "variables" jsonb,
  "description" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of m_email_template
-- ----------------------------

-- ----------------------------
-- Table structure for m_exchange_rate
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_exchange_rate";
CREATE TABLE "public"."m_exchange_rate" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "base_currency" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "target_currency" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "rate" numeric(15,4) NOT NULL,
  "effective_date" date NOT NULL DEFAULT CURRENT_DATE,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_exchange_rate
-- ----------------------------

-- ----------------------------
-- Table structure for m_geofence
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_geofence";
CREATE TABLE "public"."m_geofence" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "geofence_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "geofence_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "center_latitude" numeric(10,7),
  "center_longitude" numeric(10,7),
  "radius" numeric(10,2),
  "coordinates" jsonb,
  "description" text COLLATE "pg_catalog"."default",
  "is_active" bool DEFAULT true,
  "alert_on_enter" bool DEFAULT true,
  "alert_on_exit" bool DEFAULT true,
  "speed_limit" numeric(10,2),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_geofence
-- ----------------------------

-- ----------------------------
-- Table structure for m_iot_device
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_iot_device";
CREATE TABLE "public"."m_iot_device" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "device_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "device_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "device_type" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'OFFLINE'::character varying,
  "serial_number" varchar(50) COLLATE "pg_catalog"."default",
  "firmware_version" varchar(20) COLLATE "pg_catalog"."default",
  "hardware_version" varchar(20) COLLATE "pg_catalog"."default",
  "manufacturer" varchar(100) COLLATE "pg_catalog"."default",
  "model" varchar(50) COLLATE "pg_catalog"."default",
  "battery_level" int4 DEFAULT 0,
  "last_seen" timestamp(6),
  "last_latitude" numeric(10,7),
  "last_longitude" numeric(10,7),
  "last_altitude" numeric(10,2),
  "last_speed" numeric(10,2),
  "is_active" bool DEFAULT true,
  "is_online" bool DEFAULT false,
  "metadata" jsonb,
  "notes" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_iot_device
-- ----------------------------

-- ----------------------------
-- Table structure for m_language
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_language";
CREATE TABLE "public"."m_language" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "language_code" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "language_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "language_name_en" varchar(100) COLLATE "pg_catalog"."default",
  "flag_emoji" varchar(10) COLLATE "pg_catalog"."default",
  "is_rtl" bool DEFAULT false,
  "is_active" bool DEFAULT true,
  "is_default" bool DEFAULT false,
  "sort_order" int4 DEFAULT 0,
  "locale" varchar(20) COLLATE "pg_catalog"."default",
  "date_format" varchar(50) COLLATE "pg_catalog"."default",
  "time_format" varchar(50) COLLATE "pg_catalog"."default",
  "number_format" varchar(50) COLLATE "pg_catalog"."default",
  "currency_symbol" varchar(10) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted_at" timestamp(6),
  "deleted" bool NOT NULL DEFAULT false
)
;

-- ----------------------------
-- Records of m_language
-- ----------------------------
INSERT INTO "public"."m_language" VALUES ('e4c70ae9-2861-4650-9374-8ee539bb9557', 'th', 'ภาษาไทย', 'Thai', '🇹🇭', 'f', 't', 't', 1, 'th_TH', 'dd/MM/yyyy', NULL, NULL, '฿', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', NULL, 'f');
INSERT INTO "public"."m_language" VALUES ('85124848-faa4-4aa7-a9ec-364f7a141fe6', 'en', 'English', 'English', '🇬🇧', 'f', 't', 'f', 2, 'en_US', 'MM/dd/yyyy', NULL, NULL, '$', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', NULL, 'f');
INSERT INTO "public"."m_language" VALUES ('8afd0c4f-189c-4326-a54c-b5f28fe451c1', 'zh', '中文', 'Chinese', '🇨🇳', 'f', 't', 'f', 3, 'zh_CN', 'yyyy/MM/dd', NULL, NULL, '¥', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', NULL, 'f');
INSERT INTO "public"."m_language" VALUES ('3c96128d-b62f-41f7-8203-59f89b87f289', 'ja', '日本語', 'Japanese', '🇯🇵', 'f', 't', 'f', 4, 'ja_JP', 'yyyy/MM/dd', NULL, NULL, '¥', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001', NULL, 'f');

-- ----------------------------
-- Table structure for m_part_master
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_part_master";
CREATE TABLE "public"."m_part_master" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "part_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "part_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "part_name_en" varchar(200) COLLATE "pg_catalog"."default",
  "category_id" uuid,
  "brand" varchar(50) COLLATE "pg_catalog"."default",
  "model" varchar(100) COLLATE "pg_catalog"."default",
  "oem_number" varchar(50) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "unit" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PIECE'::character varying,
  "reorder_level" int4 DEFAULT 0,
  "reorder_quantity" int4 DEFAULT 0,
  "stock_quantity" int4 DEFAULT 0,
  "min_stock" int4 DEFAULT 0,
  "max_stock" int4 DEFAULT 0,
  "unit_cost" numeric(15,2),
  "selling_price" numeric(15,2),
  "location_id" uuid,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'ACTIVE'::character varying,
  "image_url" varchar(255) COLLATE "pg_catalog"."default",
  "notes" text COLLATE "pg_catalog"."default",
  "last_updated_stock" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_part_master
-- ----------------------------

-- ----------------------------
-- Table structure for m_payment_method
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_payment_method";
CREATE TABLE "public"."m_payment_method" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "method_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "method_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "method_name_en" varchar(100) COLLATE "pg_catalog"."default",
  "is_active" bool DEFAULT true,
  "requires_approval" bool DEFAULT false,
  "fee_percentage" numeric(5,2) DEFAULT 0,
  "fee_fixed" numeric(15,2) DEFAULT 0,
  "description" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of m_payment_method
-- ----------------------------

-- ----------------------------
-- Table structure for m_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_permission";
CREATE TABLE "public"."m_permission" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" text COLLATE "pg_catalog"."default",
  "action" varchar(255) COLLATE "pg_catalog"."default",
  "resource" varchar(255) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_permission
-- ----------------------------

-- ----------------------------
-- Table structure for m_promotion
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_promotion";
CREATE TABLE "public"."m_promotion" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "promotion_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "promotion_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "promotion_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "discount_value" numeric(15,2) NOT NULL,
  "min_order_amount" numeric(15,2),
  "max_discount" numeric(15,2),
  "applicable_to" jsonb,
  "start_date" timestamp(6) NOT NULL,
  "end_date" timestamp(6) NOT NULL,
  "usage_limit" int4 DEFAULT 0,
  "used_count" int4 DEFAULT 0,
  "is_active" bool DEFAULT true,
  "description" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_promotion
-- ----------------------------
INSERT INTO "public"."m_promotion" VALUES ('00000000-0000-0000-0000-000000000020', 'WELCOME10', 'ส่วนลด 10% สำหรับสมาชิกใหม่', 'PERCENTAGE', 10.00, 500.00, NULL, NULL, '2026-07-05 22:40:53.140776', '9999-12-31 23:59:59', 0, 0, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_promotion" VALUES ('00000000-0000-0000-0000-000000000021', 'NEWYEAR2026', 'ส่วนลด 500 บาท ต้อนรับปีใหม่ 2026', 'FIXED', 500.00, 2000.00, NULL, NULL, '2026-07-05 22:40:53.140776', '2026-01-31 23:59:59', 1000, 0, 't', NULL, '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- ----------------------------
-- Table structure for m_province
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_province";
CREATE TABLE "public"."m_province" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "country_id" uuid,
  "province_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "province_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_province
-- ----------------------------

-- ----------------------------
-- Table structure for m_rate_limit_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_rate_limit_log";
CREATE TABLE "public"."m_rate_limit_log" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "client_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "api_path" text COLLATE "pg_catalog"."default" NOT NULL,
  "method" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "attempted_at" timestamp(6) NOT NULL DEFAULT now(),
  "rate_limit_key" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of m_rate_limit_log
-- ----------------------------

-- ----------------------------
-- Table structure for m_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_role";
CREATE TABLE "public"."m_role" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_role
-- ----------------------------
INSERT INTO "public"."m_role" VALUES ('661cd350-eed3-46f7-9d26-0cb832a9cb95', 'Super Admin', 'Super Admin', '2026-07-04 18:09:12', NULL, NULL, 'f', NULL, NULL);
INSERT INTO "public"."m_role" VALUES ('e778dcad-7de9-4bd6-82ab-1b7fa29220db', 'Admin', 'Admin', '2026-07-04 18:09:36', NULL, NULL, 'f', NULL, NULL);

-- ----------------------------
-- Table structure for m_role_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_role_permission";
CREATE TABLE "public"."m_role_permission" (
  "role_id" uuid NOT NULL,
  "permission_id" uuid NOT NULL,
  "granted_at" timestamp(6) NOT NULL DEFAULT now()
)
;

-- ----------------------------
-- Records of m_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for m_sales_price
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_sales_price";
CREATE TABLE "public"."m_sales_price" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "item_id" uuid NOT NULL,
  "price_tier" varchar(30) COLLATE "pg_catalog"."default" DEFAULT 'DEFAULT'::character varying,
  "unit_price" numeric(15,2) NOT NULL,
  "currency" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'THB'::character varying,
  "effective_date" timestamp(6) NOT NULL DEFAULT now(),
  "expiry_date" timestamp(6),
  "min_quantity" int4 DEFAULT 1,
  "is_active" bool DEFAULT true,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_sales_price
-- ----------------------------
INSERT INTO "public"."m_sales_price" VALUES ('ed5bbbf9-75e5-4a16-8ed6-4a3b3bf76a21', '00000000-0000-0000-0000-000000000010', 'DEFAULT', 250.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('56549209-6c0f-4dde-a7f6-c01fe594292f', '00000000-0000-0000-0000-000000000011', 'DEFAULT', 350.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('6650184e-1152-4392-acfd-5c65fbe8130e', '00000000-0000-0000-0000-000000000012', 'DEFAULT', 800.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('900e1755-73c4-462f-9ba8-bd5cf0015193', '00000000-0000-0000-0000-000000000013', 'DEFAULT', 1500.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('5308649b-aa8a-49ab-976b-d93330560539', '00000000-0000-0000-0000-000000000014', 'DEFAULT', 150.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('0e2425a3-6b04-4b2f-90c0-62b2a22d5647', '00000000-0000-0000-0000-000000000015', 'DEFAULT', 3500.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('edd906d3-cd84-4141-85c5-f55fff98ca57', '00000000-0000-0000-0000-000000000016', 'DEFAULT', 2500.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('f35ddd06-a5c7-4a9e-809a-724f569be152', '00000000-0000-0000-0000-000000000017', 'DEFAULT', 1800.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('7459a731-9cc8-49a6-bb93-255f386ce015', '00000000-0000-0000-0000-000000000018', 'DEFAULT', 5000.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_sales_price" VALUES ('f3460cc5-5e6b-4e8b-a718-9b6c5f2c63d0', '00000000-0000-0000-0000-000000000019', 'DEFAULT', 3000.00, 'THB', '2026-07-05 22:40:53.140776', NULL, 1, 't', '2026-07-05 22:40:53.140776', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

-- ----------------------------
-- Table structure for m_service
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_service";
CREATE TABLE "public"."m_service" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "service_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "service_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "service_name_en" varchar(200) COLLATE "pg_catalog"."default",
  "category_id" uuid,
  "description" text COLLATE "pg_catalog"."default",
  "unit" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'HOUR'::character varying,
  "unit_price" numeric(15,2),
  "is_active" bool DEFAULT true,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_service
-- ----------------------------

-- ----------------------------
-- Table structure for m_shop_profile
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_shop_profile";
CREATE TABLE "public"."m_shop_profile" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "shop_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "shop_name_en" varchar(200) COLLATE "pg_catalog"."default",
  "address" text COLLATE "pg_catalog"."default",
  "phone" varchar(20) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "tax_id" varchar(20) COLLATE "pg_catalog"."default",
  "logo_url" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_shop_profile
-- ----------------------------

-- ----------------------------
-- Table structure for m_staff
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_staff";
CREATE TABLE "public"."m_staff" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "staff_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "job_title" varchar(50) COLLATE "pg_catalog"."default",
  "phone" varchar(20) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "hire_date" date,
  "is_active" bool DEFAULT true,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_staff
-- ----------------------------

-- ----------------------------
-- Table structure for m_stock_location
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_stock_location";
CREATE TABLE "public"."m_stock_location" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "location_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "location_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "location_type" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'SHELF'::character varying,
  "zone" varchar(50) COLLATE "pg_catalog"."default",
  "capacity" int4,
  "current_usage" int4 DEFAULT 0,
  "is_active" bool DEFAULT true,
  "notes" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of m_stock_location
-- ----------------------------

-- ----------------------------
-- Table structure for m_supplier
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_supplier";
CREATE TABLE "public"."m_supplier" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "supplier_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "tax_id" varchar(20) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "phone" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "address" text COLLATE "pg_catalog"."default",
  "contact_person" varchar(100) COLLATE "pg_catalog"."default",
  "contact_phone" varchar(20) COLLATE "pg_catalog"."default",
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'ACTIVE'::character varying,
  "notes" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_supplier
-- ----------------------------

-- ----------------------------
-- Table structure for m_translation
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_translation";
CREATE TABLE "public"."m_translation" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "message_key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "language_code" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "message_text" text COLLATE "pg_catalog"."default" NOT NULL,
  "context" varchar(100) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "version" int4 DEFAULT 1,
  "is_approved" bool DEFAULT true,
  "approved_by" uuid,
  "approved_at" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of m_translation
-- ----------------------------

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_user";
CREATE TABLE "public"."m_user" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "password_hash" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'ACTIVE'::character varying,
  "phone_number" varchar(255) COLLATE "pg_catalog"."default",
  "profile_image_url" varchar(255) COLLATE "pg_catalog"."default",
  "last_login" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid,
  "role" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO "public"."m_user" VALUES ('679777bc-fa75-440a-b37c-5a286e8f1a68', 'kongnakornna', 'kongnakornna@gmail.com', '$2a$10$evdhwbzExdJfJ7SFpUcI1O3JoXKGBhnwd0J0X0I3i9/N70WlEYcCq', 'Kongnakorn jantakun', 'ACTIVE', '0955088091', NULL, NULL, '2026-07-05 13:26:17.554836', NULL, NULL, 'f', '6c399236-51cc-4c99-94e5-6ad6e379be02', '65f02d75-8be2-49e9-9899-d76800b6f6b0', 'USER');
INSERT INTO "public"."m_user" VALUES ('9125b2b5-42c5-43af-8360-330cc5274616', 'kongnakorn1', 'kongnakorn1@gmail.com', '$2a$10$vcevCP0pV5jD4Y78Bcv.gucDrmDIEJ/q/VykBE2DNtEfFVzHkUY8i', 'Kongnakorn jantakun', 'ACTIVE', '0955088091', NULL, NULL, '2026-07-05 14:23:20.486076', NULL, NULL, 'f', '84b227b8-f711-4cfd-b957-533ab8fa9f45', '73c2e98d-c5d7-4a0a-b4d6-dc9df07137ab', 'USER');
INSERT INTO "public"."m_user" VALUES ('9fbdba96-6f09-4540-b7df-d66b6d231c76', 'testuser', 'test@test.com', '$2a$10$kjiEpAjDQHIaKA.TpqOOXuDHfD7eVm0dZ2mV1Qf8mBLb1EMCNxkUu', 'Test User', 'ACTIVE', '0810000000', NULL, NULL, '2026-07-05 15:00:15.155009', NULL, NULL, 'f', '50e3a36a-2671-4a8d-8b67-3c2e290aba96', '9bebcfb2-b0bb-40df-ae18-5dd3224921eb', 'USER');
INSERT INTO "public"."m_user" VALUES ('0c54d9a6-86ed-486c-b2b7-a63de4e56a06', 'john_doe', 'john@example.com', '$2a$10$abs7/N8yCXvFUbUkoBaLX.NbErkjNN5SwaFMm07ad/DS2uB8tE8Be', 'John Doe', 'ACTIVE', '0812345678', NULL, NULL, '2026-07-05 15:07:32.078853', NULL, NULL, 'f', 'e159d34c-c0cd-401f-96d4-6c6b4850d697', 'af171ecb-e93b-4713-b3c4-ce9c668f8c29', 'USER');

-- ----------------------------
-- Table structure for m_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_user_role";
CREATE TABLE "public"."m_user_role" (
  "user_id" uuid NOT NULL,
  "role_id" uuid NOT NULL,
  "assigned_at" timestamp(6) NOT NULL DEFAULT now()
)
;

-- ----------------------------
-- Records of m_user_role
-- ----------------------------
INSERT INTO "public"."m_user_role" VALUES ('679777bc-fa75-440a-b37c-5a286e8f1a68', '661cd350-eed3-46f7-9d26-0cb832a9cb95', '2026-07-05 14:33:32');

-- ----------------------------
-- Table structure for m_user_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_user_token";
CREATE TABLE "public"."m_user_token" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "user_id" uuid NOT NULL,
  "token" text COLLATE "pg_catalog"."default" NOT NULL,
  "token_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "expiry_date" timestamp(6) NOT NULL,
  "revoked" bool DEFAULT false,
  "revoked_at" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_agent" text COLLATE "pg_catalog"."default",
  "ip_address" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of m_user_token
-- ----------------------------
INSERT INTO "public"."m_user_token" VALUES ('52f11ba1-0f3c-4002-bc54-afdbf5382b60', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzI3NzcsImV4cCI6MTc4MzIzNjM3N30.LdD6wCvOjj5u8s4jqr8FAIk-2Eje5HpnFIJSAhUQkVE', 'ACCESS', '2026-07-05 14:26:17.591473', 'f', NULL, '2026-07-05 13:26:17.591473', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('5e1dbf00-239b-4e15-9689-a71fcab55b20', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMjc3NywiZXhwIjoxNzgzMzE5MTc3fQ.82Icbzn5S5m2bUZv8KtBLVWWjDrr-15amnyF7tkx1fo', 'REFRESH', '2026-07-06 13:26:17.593474', 'f', NULL, '2026-07-05 13:26:17.593474', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('f95c90f5-44a0-4440-8476-f64f15cd817f', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMwMTIsImV4cCI6MTc4MzIzNjYxMn0.XtEAKvm5E6PQouXMx2UShHN-p6lnplCoyp28mG1d1o0', 'ACCESS', '2026-07-05 14:30:12.307234', 'f', NULL, '2026-07-05 13:30:12.307234', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('b816366a-dca1-4843-bf7a-c30d7a6f49db', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzAxMiwiZXhwIjoxNzgzMzE5NDEyfQ.lUY_qktPmHQOifUupMyopTX13DX0CZrOXcXZU2trlCA', 'REFRESH', '2026-07-06 13:30:12.326237', 'f', NULL, '2026-07-05 13:30:12.326237', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('6ab31d23-49c1-4a34-b1eb-b306f764a59c', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMxMjMsImV4cCI6MTc4MzIzNjcyM30.jGOgyRqa7zWumC8WiVMQRi_xrDEhSD9GbT0SzW5wHPc', 'ACCESS', '2026-07-05 14:32:03.298049', 'f', NULL, '2026-07-05 13:32:03.298049', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('cfa1e213-ba55-4bf9-9c84-63ab1de4c38a', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzEyMywiZXhwIjoxNzgzMzE5NTIzfQ.rmemtp4Qawk7BqjmiGvxfMMeaBwwu9XzqTpJnNDkNEo', 'REFRESH', '2026-07-06 13:32:03.299048', 'f', NULL, '2026-07-05 13:32:03.299048', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('f7fed42f-2a4f-4542-8abb-a9d753490744', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMxNDcsImV4cCI6MTc4MzIzNjc0N30.ZcpkLKWjSF7HKnd73nmG1-AqtHYHamsJwPDH_osWyxg', 'ACCESS', '2026-07-05 14:32:27.618703', 'f', NULL, '2026-07-05 13:32:27.618703', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('9d4bb668-f9f7-4ec0-9a21-46814e83d1b3', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzE0NywiZXhwIjoxNzgzMzE5NTQ3fQ.Dp3m-3lJ1GR48SjdLGpBp0ERMitdcMeuBNIGIMGvg30', 'REFRESH', '2026-07-06 13:32:27.619706', 'f', NULL, '2026-07-05 13:32:27.619706', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('2287208e-8c1a-4b09-8327-a21c038d67e6', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMxNDgsImV4cCI6MTc4MzIzNjc0OH0.cgdN0ECYSNNXUpYlyZvnFsB0PAC88ao94npoM3QxgOA', 'ACCESS', '2026-07-05 14:32:28.601278', 'f', NULL, '2026-07-05 13:32:28.601278', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('95e39738-3d0c-44b2-8c27-2be707227855', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzE0OCwiZXhwIjoxNzgzMzE5NTQ4fQ.oYzQPXsrcURDYfekNDYoHiL0Hcu8kZLb7gFL-N5RTQU', 'REFRESH', '2026-07-06 13:32:28.602278', 'f', NULL, '2026-07-05 13:32:28.602278', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('588f82ae-b984-4100-b68a-a3c6aaa69e13', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMxNTcsImV4cCI6MTc4MzIzNjc1N30.jRUlMzTCrtVMfuER_qYAqIg9XyNlZickGfbRisTSOqk', 'ACCESS', '2026-07-05 14:32:37.903166', 'f', NULL, '2026-07-05 13:32:37.903166', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('619aa7f6-586c-4b19-8049-f4701b63f00b', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzE1NywiZXhwIjoxNzgzMzE5NTU3fQ.mejHPnT3x7KXB8SUZd_sclzdrRY5DnkiQN_eku3rhTM', 'REFRESH', '2026-07-06 13:32:37.903166', 'f', NULL, '2026-07-05 13:32:37.903166', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('a1119311-f95c-47b6-a2be-121926c02c70', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMxNTksImV4cCI6MTc4MzIzNjc1OX0.SX1NzX6QgeHnUxiglufDq7bEu9jT-Q_WlSAiAI_ILEw', 'ACCESS', '2026-07-05 14:32:39.272644', 'f', NULL, '2026-07-05 13:32:39.272644', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('ffa0b75e-bb26-4de0-b00c-e49125efeb94', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzE1OSwiZXhwIjoxNzgzMzE5NTU5fQ.PJYjjh627EaO1iHNcGdfL0g2giblAh_2VfHTpXk8RLU', 'REFRESH', '2026-07-06 13:32:39.272644', 'f', NULL, '2026-07-05 13:32:39.272644', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('117fbb04-d6c3-4724-a19d-551b399178fc', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMxNjAsImV4cCI6MTc4MzIzNjc2MH0.yF7pRwZEVXJbJxzFpnL7fCeBXVoFNF6u62ERfvSzr-4', 'ACCESS', '2026-07-05 14:32:40.292478', 'f', NULL, '2026-07-05 13:32:40.292478', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('e99262eb-0c9f-48e7-91cb-bfa43bbf7ca9', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzE2MCwiZXhwIjoxNzgzMzE5NTYwfQ.SGfjSYlqJATLUTMQ6Pys7CURAQ54eFqhi82LhtMiHuE', 'REFRESH', '2026-07-06 13:32:40.293209', 'f', NULL, '2026-07-05 13:32:40.293209', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('16a3e840-a064-49c2-a378-aeb7064f68ee', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMyMTUsImV4cCI6MTc4MzIzNjgxNX0.C9CuAyf_ePudPiW3Hvgt6MiPbtzVi6qERYitwx1c8mw', 'ACCESS', '2026-07-05 14:33:35.965122', 'f', NULL, '2026-07-05 13:33:35.965122', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('1e792e6f-b263-411c-88c2-5f2fa6d226d0', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzIxNSwiZXhwIjoxNzgzMzE5NjE1fQ.LBSAnn-Wx-7x0lAItk1yutjtY4TduIDq6ctd8MpO2tY', 'REFRESH', '2026-07-06 13:33:35.966131', 'f', NULL, '2026-07-05 13:33:35.966131', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('4e83d521-1e91-4ddb-988a-058e9942cd25', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMyMTYsImV4cCI6MTc4MzIzNjgxNn0.1p0jl2MAJaiChhTOoLZdv3IwNGFSMoUHUoulITbqzXE', 'ACCESS', '2026-07-05 14:33:36.884454', 'f', NULL, '2026-07-05 13:33:36.884454', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('198dccd2-5199-44c0-ba98-38c5f4187db1', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzIxNiwiZXhwIjoxNzgzMzE5NjE2fQ.ogI9AhA8ngJTqwOHskfSKoD6KZy3KO5HJiZi4S_h4k4', 'REFRESH', '2026-07-06 13:33:36.885454', 'f', NULL, '2026-07-05 13:33:36.885454', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('d06cf857-4e21-4d5b-996b-7b9dcfeaa7f8', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzMyMjksImV4cCI6MTc4MzIzNjgyOX0.mMhxBUoRuGOtGdh2qfgO8gFSg_LxMSISCQEkS_gHWns', 'ACCESS', '2026-07-05 14:33:49.971385', 'f', NULL, '2026-07-05 13:33:49.971385', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('03422345-0772-432a-a762-db4b52fd9491', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzMzIyOSwiZXhwIjoxNzgzMzE5NjI5fQ.hPNXfpL60YWp3nFGmqlWuC7O8kS4uHzcZJpbWDUmxkM', 'REFRESH', '2026-07-06 13:33:49.971891', 'f', NULL, '2026-07-05 13:33:49.971891', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('c7f69c0c-34a9-4fe7-bae7-9df20e747585', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQxNTUsImV4cCI6MTc4MzIzNzc1NX0.ehWhI6U2ENj7mIuMaZ4cG_CVQq18qaVyYVfk30Yw15I', 'ACCESS', '2026-07-05 14:49:15.056332', 'f', NULL, '2026-07-05 13:49:15.057332', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('21501273-d6a4-4566-a996-f3f4239b1649', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDE1NSwiZXhwIjoxNzgzMzIwNTU1fQ.2BFTOqugprHhUj-vFkfCsoGp0efobS352YyjI0bk9SY', 'REFRESH', '2026-07-06 13:49:15.05833', 'f', NULL, '2026-07-05 13:49:15.05833', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('aafa1d20-7911-4573-8019-09c0f44ae512', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQxNjMsImV4cCI6MTc4MzIzNzc2M30.bHi93FbcRiXl4ADoLHhsEEOhrPU14FnitgxNh2q8pzk', 'ACCESS', '2026-07-05 14:49:23.928122', 'f', NULL, '2026-07-05 13:49:23.928122', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('7a2ccc4b-0f67-43d0-afb9-7a9cd1a4872e', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDE2MywiZXhwIjoxNzgzMzIwNTYzfQ.PvkqhF0LYW3-9bK6S9X88x70OTTUINK6aY1XiotuJd0', 'REFRESH', '2026-07-06 13:49:23.928122', 'f', NULL, '2026-07-05 13:49:23.928122', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('e66998bc-3b75-4878-9377-3365bed9e8b9', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQxNjUsImV4cCI6MTc4MzIzNzc2NX0.hI5H_DvtI48z7O8qcsqmVuXMXiUr9-aP_6YdeK7jDo4', 'ACCESS', '2026-07-05 14:49:25.473244', 'f', NULL, '2026-07-05 13:49:25.473244', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('ecf69d7e-2826-4443-97f7-2fa3cef6677c', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDE2NSwiZXhwIjoxNzgzMzIwNTY1fQ.B6ecW5TDqBqg8MQ3TZBRqVBYeTCzk7YJLWdPvzN_QVM', 'REFRESH', '2026-07-06 13:49:25.473244', 'f', NULL, '2026-07-05 13:49:25.473244', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('e6d6af6f-c864-4bc3-95cd-e0865f9abff2', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQxNjYsImV4cCI6MTc4MzIzNzc2Nn0.fb_7yx5GSRdFXnGzFAR7q2_SlLnjGC_XgR9lB6WS8xs', 'ACCESS', '2026-07-05 14:49:26.65009', 'f', NULL, '2026-07-05 13:49:26.65009', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('d6c9d2de-a5ca-44e0-868f-4a09a6b61e95', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDE2NiwiZXhwIjoxNzgzMzIwNTY2fQ._CDWDfwDbJQUi1X5TYptqApsHUepdHP6PQLoXRos9LI', 'REFRESH', '2026-07-06 13:49:26.65009', 'f', NULL, '2026-07-05 13:49:26.65009', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('1af101c8-84c4-459e-a5cf-7d4275f917fd', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQxNjcsImV4cCI6MTc4MzIzNzc2N30.6JiIQHRCzOM0M8F-EeWYaQrT2f0P4Pkb2T6AKX_E40c', 'ACCESS', '2026-07-05 14:49:27.903787', 'f', NULL, '2026-07-05 13:49:27.903787', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('e8c1b81e-8c25-480c-a4ed-bcb225c31b00', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDE2NywiZXhwIjoxNzgzMzIwNTY3fQ.0231BIkalPOQKXOaNERfu1Vs-ZHk8kwc9FeFlcfRrZw', 'REFRESH', '2026-07-06 13:49:27.903787', 'f', NULL, '2026-07-05 13:49:27.903787', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('68c3a6d8-48e2-4487-9dc9-9bc17db64b24', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQyMjMsImV4cCI6MTc4MzIzNzgyM30.NNxuyaKLkzhR0mPWn40ejZThxdzhOx66Fe135WVkXhM', 'ACCESS', '2026-07-05 14:50:23.461505', 'f', NULL, '2026-07-05 13:50:23.462514', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('7a39a127-04a5-4df8-818a-ac0153a17966', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDIyMywiZXhwIjoxNzgzMzIwNjIzfQ.pUTLV5W43CYbcV797k9HHw3gjb6lv0-NnMHuteGFRXI', 'REFRESH', '2026-07-06 13:50:23.462514', 'f', NULL, '2026-07-05 13:50:23.462514', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('95920624-9e1b-4562-839b-fca9e2d6acbc', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQyMjYsImV4cCI6MTc4MzIzNzgyNn0.7oM6TlIPc5-syR5dCqpnEwgIDlZAs79TW4xE6SnIGX8', 'ACCESS', '2026-07-05 14:50:26.461575', 'f', NULL, '2026-07-05 13:50:26.461575', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('2a0e5be3-7354-4d77-97c1-5de85c303b55', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDIyNiwiZXhwIjoxNzgzMzIwNjI2fQ.Y8HaWy2NXjB8vPnwaoxu8CCbpl8Frscim_JSLfLHyLM', 'REFRESH', '2026-07-06 13:50:26.462575', 'f', NULL, '2026-07-05 13:50:26.462575', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('66ad9aae-bc06-499c-8255-d9360c0d559f', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQzOTQsImV4cCI6MTc4MzIzNzk5NH0.mBRPAdp66IdUXAwHHv0eyY_7rpAsSW84i5hUqd1lh1Y', 'ACCESS', '2026-07-05 14:53:14.26241', 'f', NULL, '2026-07-05 13:53:14.26241', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('f84736de-bfd1-4430-aa80-545caff9cd34', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDM5NCwiZXhwIjoxNzgzMzIwNzk0fQ.vIQyjkq6qkfvMgzHNAn_hw4VK9PP6ozFZ-vg0q9u8hM', 'REFRESH', '2026-07-06 13:53:14.277419', 'f', NULL, '2026-07-05 13:53:14.277419', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('bfc5c2e6-6a85-4b8c-8402-ebc6119f1ccf', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQ1ODIsImV4cCI6MTc4MzIzODE4Mn0.0YE1o3b6mwjxGKq3HTAqVfuWlogkaprGS3GDiH2-xR8', 'ACCESS', '2026-07-05 14:56:22.094337', 'f', NULL, '2026-07-05 13:56:22.094337', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('c8a2d940-76c9-4867-a2bf-5ddbb507575d', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDU4MiwiZXhwIjoxNzgzMzIwOTgyfQ.lVJ-esZ9CZpnUBGeImYLh0XgFU2IvFrXJxIdfFr5xi0', 'REFRESH', '2026-07-06 13:56:22.113103', 'f', NULL, '2026-07-05 13:56:22.113103', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('73d8806c-634a-49fe-bbc8-f95d26944f34', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzQ1ODksImV4cCI6MTc4MzIzODE4OX0.a6uRLo0gY3SEXwWJH1Y6UkbLGJM61IaSv5ST67rekGc', 'ACCESS', '2026-07-05 14:56:29.913234', 'f', NULL, '2026-07-05 13:56:29.913234', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('34dc930f-22ae-4058-ba97-d1a31e41e086', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNDU4OSwiZXhwIjoxNzgzMzIwOTg5fQ.UmpH5J5-irOhl0e7IczuSb4-oe5KgB9BcjyHphNcDB4', 'REFRESH', '2026-07-06 13:56:29.913752', 'f', NULL, '2026-07-05 13:56:29.913752', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('348d61a6-aacf-42c2-83bf-75ec277e8960', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzUyNzgsImV4cCI6MTc4MzIzODg3OH0.lh8Dd6Con9aSncNTV2px5aj4NiLvGEHTL9_0Sw66a3g', 'ACCESS', '2026-07-05 15:07:58.15234', 'f', NULL, '2026-07-05 14:07:58.15234', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('80aacbb6-e4b7-4204-bb08-fbdf6f3513a1', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNTI3OCwiZXhwIjoxNzgzMzIxNjc4fQ.Xl2DtKRu0uBE38r4_-qxy1KPpzPwdp2pxhOs1ruWpuk', 'REFRESH', '2026-07-06 14:07:58.154345', 'f', NULL, '2026-07-05 14:07:58.154345', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('6aed6456-5786-473c-a9b7-0c706f43da12', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzUyODQsImV4cCI6MTc4MzIzODg4NH0.5qaOItveVCB1oporuYNZKCsGQE3Nxx2kQm2VGpysFcA', 'ACCESS', '2026-07-05 15:08:04.486006', 'f', NULL, '2026-07-05 14:08:04.486006', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('0aea3af8-03de-401c-9780-6ca66a1189f3', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNTI4NCwiZXhwIjoxNzgzMzIxNjg0fQ.3ibdNb8kT6OcOxf6S_33TboqWwKGca0YvjLQwaV83Ow', 'REFRESH', '2026-07-06 14:08:04.488026', 'f', NULL, '2026-07-05 14:08:04.488026', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('eaf68dea-f902-4656-85cd-89e1301bb061', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzU3MjYsImV4cCI6MTc4MzIzOTMyNn0.UvA3MImnkh9yyDlx4a0dIzM0MJh2oIgVjMz2pHcft90', 'ACCESS', '2026-07-05 15:15:26.590453', 'f', NULL, '2026-07-05 14:15:26.590453', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('90fbc1bd-6b6d-49b1-a13d-248582bae334', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNTcyNiwiZXhwIjoxNzgzMzIyMTI2fQ.Z4rqjJHaoWKIPUBWDBYt0aLT7BMnKWiUkMIDclDb9E8', 'REFRESH', '2026-07-06 14:15:26.60411', 'f', NULL, '2026-07-05 14:15:26.60411', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('04ff6861-2217-46a9-89f4-85fd1daf8687', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwMDQsImV4cCI6MTc4MzIzOTYwNH0.oxiweDzU7VE147KV7yyRjJugUHIqWVat3AErYzp1sWQ', 'ACCESS', '2026-07-05 15:20:04.735559', 'f', NULL, '2026-07-05 14:20:04.735559', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('52c666ad-ee74-4947-b7ee-d5805114c82a', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjAwNCwiZXhwIjoxNzgzMzIyNDA0fQ.g3GZEsDZs9WprPx51gtFUyC7Hf9_OAEiJ0NsVsyKU0g', 'REFRESH', '2026-07-06 14:20:04.736559', 'f', NULL, '2026-07-05 14:20:04.736559', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('55e1d4ff-eac4-4088-9aec-2d97457dcdb1', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwMDUsImV4cCI6MTc4MzIzOTYwNX0.g3oi5EwNiTu9yUdUk1dpG2rUkfg1vcgMlylZEnUFzL8', 'ACCESS', '2026-07-05 15:20:05.94803', 'f', NULL, '2026-07-05 14:20:05.94803', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('522a0c4d-73d2-4914-915f-316ff05b598c', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjAwNSwiZXhwIjoxNzgzMzIyNDA1fQ.WilgOjjp1ByVFZRk80GVUegyDYJI5Mupl8JJRPib-_k', 'REFRESH', '2026-07-06 14:20:05.949027', 'f', NULL, '2026-07-05 14:20:05.949027', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('7a8bf399-5a8e-4124-818f-6c146b45e26e', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwMDYsImV4cCI6MTc4MzIzOTYwNn0.u4L2SAJY33rHNoxW7DsfY8mv47SFEL3G4vauIGxP3YU', 'ACCESS', '2026-07-05 15:20:06.5163', 'f', NULL, '2026-07-05 14:20:06.5163', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('90ec1da4-bc7e-4b18-b674-4824a21e113f', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjAwNiwiZXhwIjoxNzgzMzIyNDA2fQ.OIzF85K7lzxXaOZqaq7Kt9cd-g5tjyo8KmfswtW2Mwg', 'REFRESH', '2026-07-06 14:20:06.5173', 'f', NULL, '2026-07-05 14:20:06.5173', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('9f47ca7d-6922-4abc-86fa-a725af35c8bd', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwMDcsImV4cCI6MTc4MzIzOTYwN30.fWoPxT8hhyr0uVYXk1HISa3H5gkv-c0_BVZ9HqQV35k', 'ACCESS', '2026-07-05 15:20:07.133611', 'f', NULL, '2026-07-05 14:20:07.133611', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('a64a2eda-1cdf-447d-a8e1-8fce1a8aba1a', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjAwNywiZXhwIjoxNzgzMzIyNDA3fQ.Bc-8x4PqeSSMBBID0RI00kSRDVJSZEIFFV31LB0FT9Y', 'REFRESH', '2026-07-06 14:20:07.134117', 'f', NULL, '2026-07-05 14:20:07.134117', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('5ccb5708-ca1e-44ae-92c1-d2f02ad57f3d', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwMjAsImV4cCI6MTc4MzIzOTYyMH0.9t22_O2dkZwuoR73Hh-D-z4MZHoPWDOxoSaxNRmks-g', 'ACCESS', '2026-07-05 15:20:20.455924', 'f', NULL, '2026-07-05 14:20:20.455924', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('841ef44f-289e-4ea2-8ee3-ac62116dfd7c', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjAyMCwiZXhwIjoxNzgzMzIyNDIwfQ.zj_TwFLRVdvSAb5K1615cgBDdDYHfHxEQVBrBjUgbzU', 'REFRESH', '2026-07-06 14:20:20.456924', 'f', NULL, '2026-07-05 14:20:20.456924', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('bedb6ec1-745b-495f-b8df-27f4126556a0', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwMjEsImV4cCI6MTc4MzIzOTYyMX0.Rlm-iahLeT4t6wjDFiKdlf0MzNI0c6wmu1y0zJU1pc4', 'ACCESS', '2026-07-05 15:20:21.471334', 'f', NULL, '2026-07-05 14:20:21.471334', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('1c4ca72e-b99e-470b-bdf8-dc96dff1de4a', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjAyMSwiZXhwIjoxNzgzMzIyNDIxfQ.wI6z3RJZJQ34B0I3EBqxDIFlf609hYEkUEu3unGPsEY', 'REFRESH', '2026-07-06 14:20:21.47233', 'f', NULL, '2026-07-05 14:20:21.47233', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('92434b0b-d714-451d-a275-79c33a9f00c1', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwNzAsImV4cCI6MTc4MzIzOTY3MH0.0BTAa2f1Ax9_Sk7_YhvT2CiUeUTg2ecO72WToHnz8ig', 'ACCESS', '2026-07-05 15:21:10.521182', 'f', NULL, '2026-07-05 14:21:10.521182', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('28e8105d-853a-43f6-ac1a-29267eca123d', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjA3MCwiZXhwIjoxNzgzMzIyNDcwfQ.bo00jEdW7ciHziB4miCsWkSuaB4N2DeocwINhaMmjwo', 'REFRESH', '2026-07-06 14:21:10.522187', 'f', NULL, '2026-07-05 14:21:10.522187', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('3a34287d-b077-4950-a758-fc0dbf7b1219', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwNzIsImV4cCI6MTc4MzIzOTY3Mn0.aLuDGCaqrjNJ9B9y1_hHO_9-w9_R28im0zRf4VseAJ0', 'ACCESS', '2026-07-05 15:21:12.690399', 'f', NULL, '2026-07-05 14:21:12.690399', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('3101a3e9-9177-4297-a2f4-dc5e97c3b0f2', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjA3MiwiZXhwIjoxNzgzMzIyNDcyfQ.zYoGDLohTi4hjPSlicPEaOLNCIw4BbB7KpycdJVQ6U4', 'REFRESH', '2026-07-06 14:21:12.691978', 'f', NULL, '2026-07-05 14:21:12.691978', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('3f601268-f413-4f67-98b7-53ad24297fb5', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYwOTMsImV4cCI6MTc4MzIzOTY5M30.PT-6YYPe6RhVdOIV2WVEIR-zNjHOxqXYvMT7Mnut9iM', 'ACCESS', '2026-07-05 15:21:33.798957', 'f', NULL, '2026-07-05 14:21:33.798957', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('8ada5a58-23ea-4980-96fc-52789b79f528', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjA5MywiZXhwIjoxNzgzMzIyNDkzfQ.dIACuBAP7YxlaGIk9TmLt6h6WV-AR0b_5ptJ3Md2z8Q', 'REFRESH', '2026-07-06 14:21:33.799634', 'f', NULL, '2026-07-05 14:21:33.799634', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('004f02e2-d34d-4c29-9800-e88abf48160e', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYxMDMsImV4cCI6MTc4MzIzOTcwM30.wnCtWxPsTrYYspV6b8JV5KE2SqnrrcWULf5RJCNMI1c', 'ACCESS', '2026-07-05 15:21:43.031002', 'f', NULL, '2026-07-05 14:21:43.031002', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('0f3e9fce-1bbf-482a-9581-498de4ec34aa', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjEwMywiZXhwIjoxNzgzMzIyNTAzfQ.N9BcLigXkN2_DCDiJJFWK1TbM0_3n6Q3ZixmYeTRkFM', 'REFRESH', '2026-07-06 14:21:43.031002', 'f', NULL, '2026-07-05 14:21:43.031002', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('f71c9cbf-8944-4d82-b302-a06292e52746', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYxMDcsImV4cCI6MTc4MzIzOTcwN30.OECMHqDzrbiq8aHBtxXbC_rjH3_rpDfgMxRDwi3Ms1A', 'ACCESS', '2026-07-05 15:21:47.945652', 'f', NULL, '2026-07-05 14:21:47.945652', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('0401da24-baab-47cc-8079-191f1a1ea66e', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjEwNywiZXhwIjoxNzgzMzIyNTA3fQ.FxXtWFFLsgtGQcnIIgVUbo_c-ofK9En5uocuTl8cEEg', 'REFRESH', '2026-07-06 14:21:47.946651', 'f', NULL, '2026-07-05 14:21:47.946651', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('11410529-36c1-4dc0-ac50-97fcd02e35f9', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYxMDksImV4cCI6MTc4MzIzOTcwOX0.bbxtBK0qxeaECJLVfKcjT1LJsJNeUMLy6vheLsi8Mgw', 'ACCESS', '2026-07-05 15:21:49.552238', 'f', NULL, '2026-07-05 14:21:49.552238', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('837a7f3c-621d-4369-8c5d-a1f018c5dec8', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjEwOSwiZXhwIjoxNzgzMzIyNTA5fQ.PihjKmEn5HB2tBEH-HJqfG-t7ZquLIoDWFfqjt4O-qY', 'REFRESH', '2026-07-06 14:21:49.552768', 'f', NULL, '2026-07-05 14:21:49.552768', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('8a9d7aac-7e2b-406c-bfc2-57b78610f030', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYxMTEsImV4cCI6MTc4MzIzOTcxMX0.Zabs2m-9rj6WyeWOOG0oN8tCMaEKVeazd0uo7Sr97J0', 'ACCESS', '2026-07-05 15:21:51.938602', 'f', NULL, '2026-07-05 14:21:51.938602', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('ec73adda-11d7-4d11-9512-93a83ab8ff0c', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjExMSwiZXhwIjoxNzgzMzIyNTExfQ.MnaBahEqF-K5gplGlsM4FsGpElwjSvYrljEsHwp15_U', 'REFRESH', '2026-07-06 14:21:51.939123', 'f', NULL, '2026-07-05 14:21:51.939123', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('529aced3-5e61-48ef-8a44-8892082825fc', '9125b2b5-42c5-43af-8360-330cc5274616', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5MTI1YjJiNS00MmM1LTQzYWYtODM2MC0zMzBjYzUyNzQ2MTYiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm7guYUiLCJ3aGl0ZWxhYmVsSWQiOiI5MTI1YjJiNS00MmM1LTQzYWYtODM2MC0zMzBjYzUyNzQ2MTYiLCJ0eXBlIjoiQUNDRVNTIiwiaWF0IjoxNzgzMjM2MjAwLCJleHAiOjE3ODMyMzk4MDB9.RK7za3zTDFM6DCgDwKUn0BABMdcv2DE-Q0o4B_R56gc', 'ACCESS', '2026-07-05 15:23:20.487076', 'f', NULL, '2026-07-05 14:23:20.48808', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('ff6e4d92-6c0d-47d0-8053-75e1617a47a5', '9125b2b5-42c5-43af-8360-330cc5274616', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5MTI1YjJiNS00MmM1LTQzYWYtODM2MC0zMzBjYzUyNzQ2MTYiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjIwMCwiZXhwIjoxNzgzMzIyNjAwfQ.ZguAFP1DNSQK9PKqnMTNf_HD7sGJ0xB8lrmcf2AwNYU', 'REFRESH', '2026-07-06 14:23:20.48808', 'f', NULL, '2026-07-05 14:23:20.48808', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('5c024895-918d-45ce-bc57-e23db0167609', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYyMTIsImV4cCI6MTc4MzIzOTgxMn0.PV98PX0SerAQrypvskWl2hY4u_xX-8UMxk7T5otWqek', 'ACCESS', '2026-07-05 15:23:32.836214', 'f', NULL, '2026-07-05 14:23:32.836214', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('bd5ad716-a53e-4931-bb75-35dcc630595e', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjIxMiwiZXhwIjoxNzgzMzIyNjEyfQ.iijSIHrakFu60SQNzmU_eI9P3Wx03kMSiPxJqQsMxjA', 'REFRESH', '2026-07-06 14:23:32.836214', 'f', NULL, '2026-07-05 14:23:32.836214', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('8f8cac18-9333-4201-9139-713bfd9d1a6d', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYyMTMsImV4cCI6MTc4MzIzOTgxM30.JXPq-LISKJzSYTu9-DFNhFLXtna_9NQSJ2WXksUvmYw', 'ACCESS', '2026-07-05 15:23:33.611302', 'f', NULL, '2026-07-05 14:23:33.611302', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('335b726d-5b56-4cf3-9eb4-f2e224cd7acc', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjIxMywiZXhwIjoxNzgzMzIyNjEzfQ.uDHX6h21jzpdkpqXzCzeufWjlRWvZI2W9Ss5WfJpMaY', 'REFRESH', '2026-07-06 14:23:33.612303', 'f', NULL, '2026-07-05 14:23:33.612303', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('576a1989-782d-4571-a6ef-365b71ac0a55', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYyMTQsImV4cCI6MTc4MzIzOTgxNH0.m8sP_h8ntRCw3nzJzbDD1FV5W6tIWaYIEkJyewhgd1M', 'ACCESS', '2026-07-05 15:23:34.104982', 'f', NULL, '2026-07-05 14:23:34.104982', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('1673f198-0886-4ae8-bfe1-ba3c79d56d10', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjIxNCwiZXhwIjoxNzgzMzIyNjE0fQ.e4iZwQAUOSjEmIxFektQOZ6mlDeyrhBf6QC-JMlIe10', 'REFRESH', '2026-07-06 14:23:34.105986', 'f', NULL, '2026-07-05 14:23:34.105986', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('fb57a03f-03b6-46a0-80a2-3011278cf57b', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYyMTUsImV4cCI6MTc4MzIzOTgxNX0.vgxTMOXl9-B-wgnmABHBn13yA1VQt2KrTjv8mBzmx1k', 'ACCESS', '2026-07-05 15:23:35.216087', 'f', NULL, '2026-07-05 14:23:35.216087', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('b3685e74-bad6-451c-8061-5e79d7957a88', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjIxNSwiZXhwIjoxNzgzMzIyNjE1fQ.jEgXdYl_ykOvlclcqEySu5HlInCSAy5VUTzQSlgRNp0', 'REFRESH', '2026-07-06 14:23:35.216087', 'f', NULL, '2026-07-05 14:23:35.216087', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('6f646924-bf5f-42e2-839a-4da7bc8fb293', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYyNjEsImV4cCI6MTc4MzIzOTg2MX0.zIDJaiVfe5GI_3FFul4ZbUNccl6AuY6MVuYWI5I182w', 'ACCESS', '2026-07-05 15:24:21.824848', 'f', NULL, '2026-07-05 14:24:21.824848', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('a504f0d3-8959-42b7-8ea9-e185c95973d8', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjI2MSwiZXhwIjoxNzgzMzIyNjYxfQ.8jzyKPNKBtZ5vaQrrSEodI5EgdsgFQ4jgPU1yGjb19c', 'REFRESH', '2026-07-06 14:24:21.825848', 'f', NULL, '2026-07-05 14:24:21.825848', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('a137f96e-f266-4d07-848a-c3c20a5fbdae', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYyNjIsImV4cCI6MTc4MzIzOTg2Mn0.Le5ctZ_AuJNseSITcDsQFYnyygwhc869rHjhf2yqoe4', 'ACCESS', '2026-07-05 15:24:22.629699', 'f', NULL, '2026-07-05 14:24:22.629699', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('dd7a13e5-3fb4-499a-9d55-f71ed621ec61', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjI2MiwiZXhwIjoxNzgzMzIyNjYyfQ.K77NmgWDpC7efLTdHL3SJpJ_i3BBZ3W859CDPqz6dXA', 'REFRESH', '2026-07-06 14:24:22.630698', 'f', NULL, '2026-07-05 14:24:22.630698', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('edbb988b-8d49-4012-922c-247e0f5fdbaf', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYzMTUsImV4cCI6MTc4MzIzOTkxNX0.di731ty3mo_r7bLtpf_MS9WXXgAL2gTao4BfTKnAZ0k', 'ACCESS', '2026-07-05 15:25:15.656434', 'f', NULL, '2026-07-05 14:25:15.656434', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('2f4906fe-7cce-440e-a9e5-043966254be9', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjMxNSwiZXhwIjoxNzgzMzIyNzE1fQ.UlKK1Q9-0XCkWa8XpUbIAJL1cDk5geqapj3D21yt5Fc', 'REFRESH', '2026-07-06 14:25:15.656434', 'f', NULL, '2026-07-05 14:25:15.656434', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('0ecf4dba-cafc-4c66-ac96-31f054b50d1d', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzYzNDAsImV4cCI6MTc4MzIzOTk0MH0.3jsHjJBLcJaP1Vkj15X-rOrTLQAiCFqDIRZ82FiI9Pw', 'ACCESS', '2026-07-05 15:25:40.383228', 'f', NULL, '2026-07-05 14:25:40.383228', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('b6b2a723-d692-438b-98b6-e0503b294011', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjM0MCwiZXhwIjoxNzgzMzIyNzQwfQ.HcJz62Hqr0g4ZduOiMsJOllCKg9cOKaOGpHpfIyWheE', 'REFRESH', '2026-07-06 14:25:40.383228', 'f', NULL, '2026-07-05 14:25:40.383228', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('18d7f4a9-b3f9-4b47-a82b-4955b5665bc8', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzY0NTksImV4cCI6MTc4MzI0MDA1OX0.-gCOkBUGF6-_swLMOl7idSiPBLTIhlky0FXJ156ujgI', 'ACCESS', '2026-07-05 15:27:39.506927', 'f', NULL, '2026-07-05 14:27:39.506927', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('9f50bf2f-f773-444c-89d5-33489e0d5ceb', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzNjQ1OSwiZXhwIjoxNzgzMzIyODU5fQ.kllcaixgnN8H-dQXg4UEYsE51jItBkX5cmyIu-dNOiA', 'REFRESH', '2026-07-06 14:27:39.507927', 'f', NULL, '2026-07-05 14:27:39.507927', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('c687edb6-daf6-4a99-a5ee-67857233eeba', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyMzYsImV4cCI6MTc4MzI0MTgzNn0.BtQPb5ptwVJM7lgKEfxxVmQTU7l9a579x662zAZg57Q', 'ACCESS', '2026-07-05 15:57:16.798056', 'f', NULL, '2026-07-05 14:57:16.798056', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('5a9d24dc-0b73-432a-b6b1-5eaff6acb122', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODIzNiwiZXhwIjoxNzgzMzI0NjM2fQ.vFeqRNEbExDyLigv2CWV4MvKHtK3HR0BW3IxLQJw6w8', 'REFRESH', '2026-07-06 14:57:16.812057', 'f', NULL, '2026-07-05 14:57:16.812057', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('23694547-50ed-4604-bc96-11af0f8a1ce5', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyMzcsImV4cCI6MTc4MzI0MTgzN30.ARlrUhPXYidDVG0SivourRLDzhANmmfGoj3MQyPDyIo', 'ACCESS', '2026-07-05 15:57:17.868992', 'f', NULL, '2026-07-05 14:57:17.868992', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('a7a609cc-bc04-4f47-b778-38e494a67bfd', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODIzNywiZXhwIjoxNzgzMzI0NjM3fQ.KpWGYCm3hOLSz9TiDt9C_jfO52nrZ0OyVKlg5khAcng', 'REFRESH', '2026-07-06 14:57:17.869533', 'f', NULL, '2026-07-05 14:57:17.869533', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('36629de7-55e0-469e-bf7e-8e272393923e', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyMzgsImV4cCI6MTc4MzI0MTgzOH0.5T7o8Iko_jr7UO5NFU29C05e-eIgyB3tIG8fokBPd3g', 'ACCESS', '2026-07-05 15:57:18.590621', 'f', NULL, '2026-07-05 14:57:18.590621', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('57928b72-df1c-4652-a039-14912e532146', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODIzOCwiZXhwIjoxNzgzMzI0NjM4fQ.QJAb4ovAhclXHiQgYv014Hdlx6gidcjU4gEEbGVPPbk', 'REFRESH', '2026-07-06 14:57:18.591623', 'f', NULL, '2026-07-05 14:57:18.591623', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('2fef59ce-5c54-439f-9f19-a014f6b98ff6', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyNDMsImV4cCI6MTc4MzI0MTg0M30.ychLo31Q08OuYZEsMmVN57Zt61g_KHXXAUagQearNyA', 'ACCESS', '2026-07-05 15:57:23.838527', 'f', NULL, '2026-07-05 14:57:23.838527', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('c649fda5-bc1b-42b5-b97d-b93a90d641a2', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODI0MywiZXhwIjoxNzgzMzI0NjQzfQ.EZesJahAfhciZlhULAEBOKY7ZqxvTebFCX1-PXT70Ng', 'REFRESH', '2026-07-06 14:57:23.839527', 'f', NULL, '2026-07-05 14:57:23.839527', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('2cd3f59c-e473-4329-a6e0-812a50420efa', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyNDQsImV4cCI6MTc4MzI0MTg0NH0.xxHL4n8cm6Pzj1VdobOGPY2nOgQVn9RiTPu3sLSGM64', 'ACCESS', '2026-07-05 15:57:24.584815', 'f', NULL, '2026-07-05 14:57:24.584815', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('9ff0ffd5-101e-4ac5-9460-5f298d7762a4', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODI0NCwiZXhwIjoxNzgzMzI0NjQ0fQ.yuk4vKUDefSljuukVQhBamgmccJ2rUTQU7pQK31MZWQ', 'REFRESH', '2026-07-06 14:57:24.585344', 'f', NULL, '2026-07-05 14:57:24.585344', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('682324e4-a19b-4fd2-9ddd-24f125ce9235', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyNDUsImV4cCI6MTc4MzI0MTg0NX0.bGqSSp7E7CQEBI0ZHVIa_d8Uz7WLwGMe5_ztpOeYcaU', 'ACCESS', '2026-07-05 15:57:25.175235', 'f', NULL, '2026-07-05 14:57:25.175235', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('b571b52d-7616-4294-a7db-faf02cee73c4', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODI0NSwiZXhwIjoxNzgzMzI0NjQ1fQ.HwaW_0qNNVQwEB3qpL2iKwuEfxoi0A9HjVUWrihINaQ', 'REFRESH', '2026-07-06 14:57:25.176235', 'f', NULL, '2026-07-05 14:57:25.176235', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('6b85efa1-ba11-464e-800f-ec1e0398a629', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyNDYsImV4cCI6MTc4MzI0MTg0Nn0.HloyVgPwsi3tihm0QSJtb98STSFsx8amh876Wnxldhk', 'ACCESS', '2026-07-05 15:57:26.219829', 'f', NULL, '2026-07-05 14:57:26.219829', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('c81fdc32-b4f0-4a7e-8200-db653ae192e4', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODI0NiwiZXhwIjoxNzgzMzI0NjQ2fQ.AVpiMFMl-FrNZScvpIwoOS2U2uC_D5otv5YGQmldqx8', 'REFRESH', '2026-07-06 14:57:26.220832', 'f', NULL, '2026-07-05 14:57:26.220832', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('4cfeb82d-526e-4174-8015-5b509901a2e0', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyNDcsImV4cCI6MTc4MzI0MTg0N30.rMznHw86c3edyB9BdG5isNKgZIOyrct7fn8jlbnGTL8', 'ACCESS', '2026-07-05 15:57:27.346096', 'f', NULL, '2026-07-05 14:57:27.346096', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('86a8f648-fd53-43d7-858f-57c24aa972fe', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODI0NywiZXhwIjoxNzgzMzI0NjQ3fQ.BjhAz4LXQlIYUd0ZRzDyBa65TwLMuasKCLyy1RuN-p8', 'REFRESH', '2026-07-06 14:57:27.346096', 'f', NULL, '2026-07-05 14:57:27.346096', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('12a8e48a-3fdf-44c2-bbe8-2071b9ee3f8f', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzgyNDksImV4cCI6MTc4MzI0MTg0OX0.vDE3Z8CU4UvsbVL8Is3UDSmEo2g36YhSXXIq1bvX12A', 'ACCESS', '2026-07-05 15:57:29.816769', 'f', NULL, '2026-07-05 14:57:29.816769', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('472cdb86-dfdc-40a9-b8ff-85b402c10d1f', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODI0OSwiZXhwIjoxNzgzMzI0NjQ5fQ.x9UY8Zn_2plTIJR9ZhYkgQ2g6w2kfxKhSnTjQGfBHtk', 'REFRESH', '2026-07-06 14:57:29.81777', 'f', NULL, '2026-07-05 14:57:29.81777', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('01e78f5f-0fb1-4e84-a235-e423dd04932c', '9fbdba96-6f09-4540-b7df-d66b6d231c76', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5ZmJkYmE5Ni02ZjA5LTQ1NDAtYjdkZi1kNjZiNmQyMzFjNzYiLCJ1c2VybmFtZSI6InRlc3R1c2VyIiwid2hpdGVsYWJlbElkIjoiOWZiZGJhOTYtNmYwOS00NTQwLWI3ZGYtZDY2YjZkMjMxYzc2IiwidHlwZSI6IkFDQ0VTUyIsImlhdCI6MTc4MzIzODQxNSwiZXhwIjoxNzgzMjQyMDE1fQ.uy7kZcs5pJNwW2bJbBS_biubgHTyF7-gJExSA9J4p6c', 'ACCESS', '2026-07-05 16:00:15.206962', 'f', NULL, '2026-07-05 15:00:15.208466', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('48fb7c8e-b440-4ae3-b5fb-b34f4e548140', '9fbdba96-6f09-4540-b7df-d66b6d231c76', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5ZmJkYmE5Ni02ZjA5LTQ1NDAtYjdkZi1kNjZiNmQyMzFjNzYiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODQxNSwiZXhwIjoxNzgzMzI0ODE1fQ.TkbaRRnwHcGaa6TzOjYSsje4nqOTkxTJH77Rp_PRImU', 'REFRESH', '2026-07-06 15:00:15.210144', 'f', NULL, '2026-07-05 15:00:15.210144', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('47ea661d-d491-49f3-9eb0-da9f21c48ef0', '0c54d9a6-86ed-486c-b2b7-a63de4e56a06', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwYzU0ZDlhNi04NmVkLTQ4NmMtYjJiNy1hNjNkZTRlNTZhMDYiLCJ1c2VybmFtZSI6ImpvaG5fZG9lIiwid2hpdGVsYWJlbElkIjoiMGM1NGQ5YTYtODZlZC00ODZjLWIyYjctYTYzZGU0ZTU2YTA2IiwidHlwZSI6IkFDQ0VTUyIsImlhdCI6MTc4MzIzODg1MiwiZXhwIjoxNzgzMjQyNDUyfQ.8OIka48xeEjWXR52YAIz5G5cFgJ4Mwxkql9YoOgtJ00', 'ACCESS', '2026-07-05 16:07:32.122258', 'f', NULL, '2026-07-05 15:07:32.123257', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('b0b12ade-74f8-423c-8751-488add08f328', '0c54d9a6-86ed-486c-b2b7-a63de4e56a06', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwYzU0ZDlhNi04NmVkLTQ4NmMtYjJiNy1hNjNkZTRlNTZhMDYiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODg1MiwiZXhwIjoxNzgzMzI1MjUyfQ.zKDVW_M2XY6EaQqWbTO_oyN1Y3hftlBYNlf28OgcIko', 'REFRESH', '2026-07-06 15:07:32.125235', 'f', NULL, '2026-07-05 15:07:32.125235', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('d4c6af94-c842-4612-89c8-4e83e1acd1e7', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzg5ODMsImV4cCI6MTc4MzI0MjU4M30.HcBgPia_wKp0ODpBuFxnt4Ye5Cxsw1l2IdjbfyFrTBU', 'ACCESS', '2026-07-05 16:09:43.323251', 'f', NULL, '2026-07-05 15:09:43.323251', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('9c38beb2-eec8-4069-b27a-0590d847c303', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODk4MywiZXhwIjoxNzgzMzI1MzgzfQ.4mSAupsw7DWaS8H1VOAmtMjEFDtM604d4YSMzdVDcA8', 'REFRESH', '2026-07-06 15:09:43.324249', 'f', NULL, '2026-07-05 15:09:43.324249', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('3274761c-8bd5-46b2-a6a1-7b01f2aebbbb', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzg5ODQsImV4cCI6MTc4MzI0MjU4NH0.AHhylkZ39bnQKkfrDgdoEx27hOk83jR5NP8G6mN7quU', 'ACCESS', '2026-07-05 16:09:44.342156', 'f', NULL, '2026-07-05 15:09:44.342156', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('cec87f3f-dbb5-491e-9992-492d9e48481f', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODk4NCwiZXhwIjoxNzgzMzI1Mzg0fQ.iIf3vchap08MzFXafCB5JtFTeU6TBylRQ-ZjSuR3dUY', 'REFRESH', '2026-07-06 15:09:44.343155', 'f', NULL, '2026-07-05 15:09:44.343155', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('d8e0038e-22a9-4e54-aca0-548cdd807ae3', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzg5ODUsImV4cCI6MTc4MzI0MjU4NX0.9Mt0k4hYL3TyBxb7TT5m0Y13dXURY2ZxiwTVWVB7it8', 'ACCESS', '2026-07-05 16:09:45.183066', 'f', NULL, '2026-07-05 15:09:45.183066', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('a981bd16-77ed-4b06-ab86-910eb012eae9', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODk4NSwiZXhwIjoxNzgzMzI1Mzg1fQ.w28rNhogVM5RBC4JIR3T-geGOIpYy-gG8_ssSYWLXsI', 'REFRESH', '2026-07-06 15:09:45.184481', 'f', NULL, '2026-07-05 15:09:45.184481', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('e8ad9ee2-e985-49d0-91e4-a65beee4c090', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzg5ODksImV4cCI6MTc4MzI0MjU4OX0.83Nh0uZMihIpMmYR4gWC1bjFARxJtGXtZllLUjgMRgg', 'ACCESS', '2026-07-05 16:09:49.859204', 'f', NULL, '2026-07-05 15:09:49.859204', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('95253ec5-6734-40f5-a043-050d89d9be62', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODk4OSwiZXhwIjoxNzgzMzI1Mzg5fQ.YAaT0jjIYoapgomhNeUwFGXQktDOUV0M_ZuNFgG7qYE', 'REFRESH', '2026-07-06 15:09:49.860209', 'f', NULL, '2026-07-05 15:09:49.860209', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('46231e77-1aba-4b18-b746-ff96ee96733d', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzg5OTEsImV4cCI6MTc4MzI0MjU5MX0.K6NQFwLp2fstHOCUOdIB1U9L6kTktZD4UfZgZ-VHDUs', 'ACCESS', '2026-07-05 16:09:51.235586', 'f', NULL, '2026-07-05 15:09:51.235586', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('5e779911-7b79-431a-9d03-db78101d8859', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzODk5MSwiZXhwIjoxNzgzMzI1MzkxfQ.Yu-hipPYJdHFJLTJNBxMciC9jGgYnAe2XVsX6BWnXMU', 'REFRESH', '2026-07-06 15:09:51.236593', 'f', NULL, '2026-07-05 15:09:51.236593', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('5d78031e-4824-48cb-8c8f-ae8413f067d1', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ1c2VybmFtZSI6ImtvbmduYWtvcm5uYSIsIndoaXRlbGFiZWxJZCI6IjY3OTc3N2JjLWZhNzUtNDQwYS1iMzdjLTVhMjg2ZThmMWE2OCIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE3ODMyMzkwMzUsImV4cCI6MTc4MzI0MjYzNX0.s8TGVmUm6KIo3WMS17iz-f7WwF5qV7leiPEzP0S9Yvw', 'ACCESS', '2026-07-05 16:10:35.466011', 'f', NULL, '2026-07-05 15:10:35.466011', NULL, NULL);
INSERT INTO "public"."m_user_token" VALUES ('f194afbd-12eb-403a-b500-37558f129157', '679777bc-fa75-440a-b37c-5a286e8f1a68', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Nzk3NzdiYy1mYTc1LTQ0MGEtYjM3Yy01YTI4NmU4ZjFhNjgiLCJ0eXBlIjoiUkVGUkVTSCIsImlhdCI6MTc4MzIzOTAzNSwiZXhwIjoxNzgzMzI1NDM1fQ.MgktETGB3AZ7nv9xqgF8LqBxEhsgywf3kFAWdXmcUdI', 'REFRESH', '2026-07-06 15:10:35.467002', 'f', NULL, '2026-07-05 15:10:35.467002', NULL, NULL);

-- ----------------------------
-- Table structure for t_auto_report
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_auto_report";
CREATE TABLE "public"."t_auto_report" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "report_no" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "report_type" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "report_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "report_date" date NOT NULL,
  "device_count" int4,
  "total_distance" numeric(15,2),
  "avg_speed" numeric(10,2),
  "max_speed" numeric(10,2),
  "total_moving_time" interval(6),
  "total_idle_time" interval(6),
  "battery_avg" int4,
  "alert_count" int4 DEFAULT 0,
  "report_data" jsonb,
  "file_path" text COLLATE "pg_catalog"."default",
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'GENERATED'::character varying,
  "generated_by" uuid,
  "generated_at" timestamp(6) NOT NULL DEFAULT now(),
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_auto_report
-- ----------------------------

-- ----------------------------
-- Table structure for t_batch_job_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_batch_job_history";
CREATE TABLE "public"."t_batch_job_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "started_at" timestamp(6) NOT NULL DEFAULT now(),
  "finished_at" timestamp(6),
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "error_message" text COLLATE "pg_catalog"."default",
  "result_summary" text COLLATE "pg_catalog"."default",
  "records_processed" int4 DEFAULT 0,
  "duration_ms" int4,
  "trigger_type" varchar(30) COLLATE "pg_catalog"."default" DEFAULT 'SCHEDULED'::character varying,
  "triggered_by" uuid,
  "parameters" jsonb,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_batch_job_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_device_access_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_device_access_log";
CREATE TABLE "public"."t_device_access_log" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "device_id" uuid NOT NULL,
  "user_id" uuid NOT NULL,
  "access_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "access_granted" bool DEFAULT true,
  "ip_address" inet,
  "user_agent" text COLLATE "pg_catalog"."default",
  "timestamp" timestamp(6) NOT NULL DEFAULT now(),
  "reason" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_device_access_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_device_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_device_history";
CREATE TABLE "public"."t_device_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "device_id" uuid NOT NULL,
  "event_type" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "event_description" text COLLATE "pg_catalog"."default",
  "old_value" text COLLATE "pg_catalog"."default",
  "new_value" text COLLATE "pg_catalog"."default",
  "event_timestamp" timestamp(6) NOT NULL DEFAULT now(),
  "triggered_by" uuid,
  "metadata" jsonb,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_device_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_document
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_document";
CREATE TABLE "public"."t_document" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "document_no" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "document_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "document_sub_type" varchar(30) COLLATE "pg_catalog"."default",
  "reference_type" varchar(30) COLLATE "pg_catalog"."default",
  "reference_id" uuid,
  "template_id" uuid,
  "file_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "file_path" text COLLATE "pg_catalog"."default" NOT NULL,
  "file_size" int8,
  "mime_type" varchar(50) COLLATE "pg_catalog"."default",
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'GENERATED'::character varying,
  "generated_by" uuid NOT NULL,
  "generated_at" timestamp(6) NOT NULL DEFAULT now(),
  "sent_by" uuid,
  "sent_at" timestamp(6),
  "sent_to_email" varchar(200) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "tags" text[] COLLATE "pg_catalog"."default",
  "metadata" jsonb,
  "deleted_at" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "uploaded_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_document
-- ----------------------------

-- ----------------------------
-- Table structure for t_document_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_document_history";
CREATE TABLE "public"."t_document_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "document_id" uuid NOT NULL,
  "action" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "performed_by" uuid NOT NULL,
  "performed_at" timestamp(6) NOT NULL DEFAULT now(),
  "details" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid,
  "created_at" timestamp(6),
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_document_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_email_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_email_history";
CREATE TABLE "public"."t_email_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "email_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "template_code" varchar(50) COLLATE "pg_catalog"."default",
  "reference_type" varchar(30) COLLATE "pg_catalog"."default",
  "reference_id" uuid,
  "from_email" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "from_name" varchar(100) COLLATE "pg_catalog"."default",
  "to_email" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "to_name" varchar(100) COLLATE "pg_catalog"."default",
  "cc_email" varchar(200) COLLATE "pg_catalog"."default",
  "bcc_email" varchar(200) COLLATE "pg_catalog"."default",
  "subject" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "body_preview" text COLLATE "pg_catalog"."default",
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "priority" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'NORMAL'::character varying,
  "sent_at" timestamp(6),
  "error_message" text COLLATE "pg_catalog"."default",
  "retry_count" int4 DEFAULT 0,
  "attachments" jsonb,
  "metadata" jsonb,
  "category" varchar(50) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_email_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_email_queue
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_email_queue";
CREATE TABLE "public"."t_email_queue" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "email_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "template_code" varchar(50) COLLATE "pg_catalog"."default",
  "reference_type" varchar(30) COLLATE "pg_catalog"."default",
  "reference_id" uuid,
  "from_email" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "to_email" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "to_name" varchar(100) COLLATE "pg_catalog"."default",
  "subject" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "body_html" text COLLATE "pg_catalog"."default",
  "body_text" text COLLATE "pg_catalog"."default",
  "attachments" jsonb,
  "priority" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'NORMAL'::character varying,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "retry_count" int4 DEFAULT 0,
  "max_retry" int4 DEFAULT 3,
  "next_attempt_at" timestamp(6) DEFAULT now(),
  "error_message" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_email_queue
-- ----------------------------

-- ----------------------------
-- Table structure for t_geofence_alert
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_geofence_alert";
CREATE TABLE "public"."t_geofence_alert" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "geofence_id" uuid NOT NULL,
  "device_id" uuid NOT NULL,
  "alert_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "latitude" numeric(10,7),
  "longitude" numeric(10,7),
  "speed" numeric(10,2),
  "alert_timestamp" timestamp(6) NOT NULL DEFAULT now(),
  "is_resolved" bool DEFAULT false,
  "resolved_at" timestamp(6),
  "resolved_by" uuid,
  "notes" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_geofence_alert
-- ----------------------------

-- ----------------------------
-- Table structure for t_gps_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_gps_data";
CREATE TABLE "public"."t_gps_data" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "device_id" uuid NOT NULL,
  "device_identifier" varchar(50) COLLATE "pg_catalog"."default",
  "latitude" numeric(10,7) NOT NULL,
  "longitude" numeric(10,7) NOT NULL,
  "altitude" numeric(10,2),
  "speed" numeric(10,2),
  "heading" numeric(5,2),
  "accuracy" numeric(5,2),
  "battery_level" int4,
  "satelites" int4,
  "event_type" varchar(20) COLLATE "pg_catalog"."default",
  "timestamp" timestamp(6) NOT NULL,
  "received_at" timestamp(6) NOT NULL DEFAULT now(),
  "metadata" jsonb,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_gps_data
-- ----------------------------

-- ----------------------------
-- Table structure for t_inventory
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_inventory";
CREATE TABLE "public"."t_inventory" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "part_id" uuid NOT NULL,
  "transaction_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "reference_type" varchar(30) COLLATE "pg_catalog"."default",
  "reference_id" uuid,
  "quantity" int4 NOT NULL,
  "previous_quantity" int4 NOT NULL,
  "new_quantity" int4 NOT NULL,
  "unit_cost" numeric(15,2),
  "total_cost" numeric(15,2),
  "transaction_date" timestamp(6) NOT NULL DEFAULT now(),
  "note" text COLLATE "pg_catalog"."default",
  "performed_by" uuid NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_inventory
-- ----------------------------

-- ----------------------------
-- Table structure for t_inventory_adjustment_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_inventory_adjustment_detail";
CREATE TABLE "public"."t_inventory_adjustment_detail" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "adjustment_header_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "quantity" int4 NOT NULL,
  "unit_cost" numeric(15,2),
  "total_cost" numeric(15,2),
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_inventory_adjustment_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_inventory_adjustment_header
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_inventory_adjustment_header";
CREATE TABLE "public"."t_inventory_adjustment_header" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "adjustment_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "adjustment_date" timestamp(6) NOT NULL DEFAULT now(),
  "adjustment_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "reason" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'DRAFT'::character varying,
  "description" text COLLATE "pg_catalog"."default",
  "approved_by" uuid,
  "approved_at" timestamp(6),
  "total_adjustment_value" numeric(15,2),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_inventory_adjustment_header
-- ----------------------------

-- ----------------------------
-- Table structure for t_invoice_adjustment
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_invoice_adjustment";
CREATE TABLE "public"."t_invoice_adjustment" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "invoice_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "job_id" uuid,
  "customer_id" uuid NOT NULL,
  "invoice_date" timestamp(6) NOT NULL DEFAULT now(),
  "type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'INVOICE'::character varying,
  "subtotal" numeric(15,2) NOT NULL DEFAULT 0,
  "tax_rate" numeric(5,2) DEFAULT 7.00,
  "tax_amount" numeric(15,2) DEFAULT 0,
  "discount_type" varchar(20) COLLATE "pg_catalog"."default",
  "discount_value" numeric(15,2) DEFAULT 0,
  "total" numeric(15,2) NOT NULL DEFAULT 0,
  "amount_in_words_th" text COLLATE "pg_catalog"."default",
  "amount_in_words_en" text COLLATE "pg_catalog"."default",
  "currency" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'THB'::character varying,
  "exchange_rate" numeric(10,4) DEFAULT 1.0000,
  "notes" text COLLATE "pg_catalog"."default",
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "payment_status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_invoice_adjustment
-- ----------------------------

-- ----------------------------
-- Table structure for t_invoice_adjustment_part
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_invoice_adjustment_part";
CREATE TABLE "public"."t_invoice_adjustment_part" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "invoice_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_invoice_adjustment_part
-- ----------------------------

-- ----------------------------
-- Table structure for t_invoice_adjustment_service
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_invoice_adjustment_service";
CREATE TABLE "public"."t_invoice_adjustment_service" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "invoice_id" uuid NOT NULL,
  "service_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_invoice_adjustment_service
-- ----------------------------

-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_job";
CREATE TABLE "public"."t_job" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_no" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "customer_id" uuid NOT NULL,
  "car_id" uuid NOT NULL,
  "mechanic_id" uuid,
  "status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'OPEN'::character varying,
  "start_date" timestamp(6) NOT NULL DEFAULT now(),
  "end_date" timestamp(6),
  "symptom" text COLLATE "pg_catalog"."default",
  "diagnosis_note" text COLLATE "pg_catalog"."default",
  "mileage" int4,
  "estimated_cost" numeric(15,2),
  "actual_cost" numeric(15,2),
  "priority" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'NORMAL'::character varying,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_job
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_diag_trouble_code
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_job_diag_trouble_code";
CREATE TABLE "public"."t_job_diag_trouble_code" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_id" uuid NOT NULL,
  "trouble_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "description" text COLLATE "pg_catalog"."default",
  "system" varchar(50) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_job_diag_trouble_code
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_part_sales
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_job_part_sales";
CREATE TABLE "public"."t_job_part_sales" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_job_part_sales
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_service
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_job_service";
CREATE TABLE "public"."t_job_service" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_id" uuid NOT NULL,
  "service_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_job_service
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_service_car_symptom
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_job_service_car_symptom";
CREATE TABLE "public"."t_job_service_car_symptom" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_id" uuid NOT NULL,
  "symptom_code" varchar(20) COLLATE "pg_catalog"."default",
  "symptom_description" text COLLATE "pg_catalog"."default" NOT NULL,
  "severity" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'MEDIUM'::character varying,
  "reported_by" varchar(100) COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_job_service_car_symptom
-- ----------------------------

-- ----------------------------
-- Table structure for t_job_status_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_job_status_history";
CREATE TABLE "public"."t_job_status_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_id" uuid NOT NULL,
  "from_status" varchar(255) COLLATE "pg_catalog"."default",
  "to_status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "changed_by" uuid,
  "changed_at" timestamp(6) NOT NULL DEFAULT now(),
  "reason" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_job_status_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_ocr_result
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_ocr_result";
CREATE TABLE "public"."t_ocr_result" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "document_id" uuid,
  "image_url" text COLLATE "pg_catalog"."default" NOT NULL,
  "provider" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "extracted_text" text COLLATE "pg_catalog"."default",
  "confidence_score" numeric(5,2),
  "language" varchar(10) COLLATE "pg_catalog"."default",
  "processing_time_ms" int4,
  "metadata" jsonb,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "error_message" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_ocr_result
-- ----------------------------

-- ----------------------------
-- Table structure for t_outstanding_balance
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_outstanding_balance";
CREATE TABLE "public"."t_outstanding_balance" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "invoice_id" uuid NOT NULL,
  "customer_id" uuid NOT NULL,
  "invoice_total" numeric(15,2) NOT NULL,
  "amount_paid" numeric(15,2) DEFAULT 0,
  "outstanding_amount" numeric(15,2) GENERATED ALWAYS AS (
(invoice_total - amount_paid)
) STORED,
  "last_payment_date" timestamp(6),
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'OUTSTANDING'::character varying,
  "updated_at" timestamp(6) NOT NULL DEFAULT now(),
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_outstanding_balance
-- ----------------------------

-- ----------------------------
-- Table structure for t_part_picking_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_part_picking_detail";
CREATE TABLE "public"."t_part_picking_detail" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "picking_request_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "requested_quantity" int4 NOT NULL,
  "picked_quantity" int4 DEFAULT 0,
  "unit_price" numeric(15,2),
  "total_price" numeric(15,2),
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_part_picking_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_part_picking_request
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_part_picking_request";
CREATE TABLE "public"."t_part_picking_request" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "picking_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "job_id" uuid,
  "quotation_id" uuid,
  "requested_date" timestamp(6) NOT NULL DEFAULT now(),
  "requested_by" uuid NOT NULL,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'DRAFT'::character varying,
  "priority" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'NORMAL'::character varying,
  "notes" text COLLATE "pg_catalog"."default",
  "picked_by" uuid,
  "picked_date" timestamp(6),
  "confirmed_by" uuid,
  "confirmed_date" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_part_picking_request
-- ----------------------------

-- ----------------------------
-- Table structure for t_payment
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_payment";
CREATE TABLE "public"."t_payment" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "payment_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "invoice_id" uuid,
  "job_id" uuid,
  "customer_id" uuid NOT NULL,
  "payment_date" timestamp(6) NOT NULL DEFAULT now(),
  "payment_method_id" uuid NOT NULL,
  "amount" numeric(15,2) NOT NULL,
  "amount_received" numeric(15,2) NOT NULL,
  "change_amount" numeric(15,2) DEFAULT 0,
  "currency" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'THB'::character varying,
  "exchange_rate" numeric(10,4) DEFAULT 1.0000,
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'PENDING'::character varying,
  "reference_number" varchar(50) COLLATE "pg_catalog"."default",
  "bank_name" varchar(100) COLLATE "pg_catalog"."default",
  "cheque_number" varchar(50) COLLATE "pg_catalog"."default",
  "cheque_bank" varchar(100) COLLATE "pg_catalog"."default",
  "cheque_date" date,
  "notes" text COLLATE "pg_catalog"."default",
  "received_by" uuid NOT NULL,
  "approved_by" uuid,
  "approved_at" timestamp(6),
  "refunded_amount" numeric(15,2) DEFAULT 0,
  "refunded_at" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_payment
-- ----------------------------

-- ----------------------------
-- Table structure for t_payment_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_payment_history";
CREATE TABLE "public"."t_payment_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "payment_id" uuid NOT NULL,
  "from_status" varchar(20) COLLATE "pg_catalog"."default",
  "to_status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "changed_by" uuid,
  "changed_at" timestamp(6) NOT NULL DEFAULT now(),
  "reason" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid,
  "created_at" timestamp(6),
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_payment_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_purchase_order_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_purchase_order_detail";
CREATE TABLE "public"."t_purchase_order_detail" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "po_header_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "quantity_ordered" int4 NOT NULL DEFAULT 1,
  "quantity_received" int4 DEFAULT 0,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity_ordered)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity_ordered)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_purchase_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_purchase_order_header
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_purchase_order_header";
CREATE TABLE "public"."t_purchase_order_header" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "po_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "quotation_id" uuid,
  "job_id" uuid,
  "supplier_id" uuid NOT NULL,
  "po_date" timestamp(6) NOT NULL DEFAULT now(),
  "expected_delivery_date" timestamp(6),
  "actual_delivery_date" timestamp(6),
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'DRAFT'::character varying,
  "subtotal" numeric(15,2) NOT NULL DEFAULT 0,
  "tax_rate" numeric(5,2) DEFAULT 7.00,
  "tax_amount" numeric(15,2) DEFAULT 0,
  "discount_type" varchar(20) COLLATE "pg_catalog"."default",
  "discount_value" numeric(15,2) DEFAULT 0,
  "total" numeric(15,2) NOT NULL DEFAULT 0,
  "currency" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'THB'::character varying,
  "exchange_rate" numeric(10,4) DEFAULT 1.0000,
  "shipping_cost" numeric(15,2) DEFAULT 0,
  "payment_terms" varchar(255) COLLATE "pg_catalog"."default",
  "delivery_address" varchar(255) COLLATE "pg_catalog"."default",
  "notes" text COLLATE "pg_catalog"."default",
  "terms_and_conditions" varchar(255) COLLATE "pg_catalog"."default",
  "sent_at" timestamp(6),
  "confirmed_at" timestamp(6),
  "received_by" uuid,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_purchase_order_header
-- ----------------------------

-- ----------------------------
-- Table structure for t_purchase_order_status_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_purchase_order_status_history";
CREATE TABLE "public"."t_purchase_order_status_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "po_header_id" uuid NOT NULL,
  "from_status" varchar(20) COLLATE "pg_catalog"."default",
  "to_status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "changed_by" uuid,
  "changed_at" timestamp(6) NOT NULL DEFAULT now(),
  "reason" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid,
  "created_at" timestamp(6),
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_purchase_order_status_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_quotation
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_quotation";
CREATE TABLE "public"."t_quotation" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "quotation_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "job_id" uuid NOT NULL,
  "customer_id" uuid NOT NULL,
  "quotation_date" timestamp(6) NOT NULL DEFAULT now(),
  "expiry_date" timestamp(6) NOT NULL,
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'DRAFT'::character varying,
  "subtotal" numeric(15,2) NOT NULL DEFAULT 0,
  "tax_rate" numeric(5,2) DEFAULT 7.00,
  "tax_amount" numeric(15,2) DEFAULT 0,
  "discount_type" varchar(20) COLLATE "pg_catalog"."default",
  "discount_value" numeric(15,2) DEFAULT 0,
  "total" numeric(15,2) NOT NULL DEFAULT 0,
  "amount_in_words_th" text COLLATE "pg_catalog"."default",
  "amount_in_words_en" text COLLATE "pg_catalog"."default",
  "currency" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'THB'::character varying,
  "exchange_rate" numeric(10,4) DEFAULT 1.0000,
  "notes" text COLLATE "pg_catalog"."default",
  "terms_and_conditions" text COLLATE "pg_catalog"."default",
  "approved_by" uuid,
  "approved_at" timestamp(6),
  "rejected_reason" text COLLATE "pg_catalog"."default",
  "converted_to_po" bool DEFAULT false,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_quotation
-- ----------------------------

-- ----------------------------
-- Table structure for t_quotation_part
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_quotation_part";
CREATE TABLE "public"."t_quotation_part" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "quotation_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_quotation_part
-- ----------------------------

-- ----------------------------
-- Table structure for t_quotation_service
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_quotation_service";
CREATE TABLE "public"."t_quotation_service" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "quotation_id" uuid NOT NULL,
  "service_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_quotation_service
-- ----------------------------

-- ----------------------------
-- Table structure for t_quotation_status_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_quotation_status_history";
CREATE TABLE "public"."t_quotation_status_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "quotation_id" uuid NOT NULL,
  "from_status" varchar(20) COLLATE "pg_catalog"."default",
  "to_status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "changed_by" uuid,
  "changed_at" timestamp(6) NOT NULL DEFAULT now(),
  "reason" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid,
  "created_at" timestamp(6),
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6),
  "user_id" uuid NOT NULL
)
;

-- ----------------------------
-- Records of t_quotation_status_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_receipt
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_receipt";
CREATE TABLE "public"."t_receipt" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "receipt_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "payment_id" uuid NOT NULL,
  "invoice_id" uuid,
  "customer_id" uuid NOT NULL,
  "receipt_date" timestamp(6) NOT NULL DEFAULT now(),
  "receipt_type" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'FULL'::character varying,
  "amount" numeric(15,2) NOT NULL,
  "amount_in_words_th" text COLLATE "pg_catalog"."default",
  "amount_in_words_en" text COLLATE "pg_catalog"."default",
  "currency" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'THB'::character varying,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'ISSUED'::character varying,
  "notes" text COLLATE "pg_catalog"."default",
  "issued_by" uuid NOT NULL,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for t_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_shopping_cart";
CREATE TABLE "public"."t_shopping_cart" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "cart_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "customer_id" uuid,
  "user_id" uuid,
  "expires_at" timestamp(6) NOT NULL,
  "subtotal" numeric(15,2) DEFAULT 0,
  "discount" numeric(15,2) DEFAULT 0,
  "tax" numeric(15,2) DEFAULT 0,
  "shipping" numeric(15,2) DEFAULT 0,
  "total" numeric(15,2) DEFAULT 0,
  "promotion_code" varchar(50) COLLATE "pg_catalog"."default",
  "notes" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for t_shopping_cart_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_shopping_cart_item";
CREATE TABLE "public"."t_shopping_cart_item" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "cart_id" uuid NOT NULL,
  "item_id" uuid NOT NULL,
  "quantity" int4 NOT NULL DEFAULT 1,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "attributes" jsonb,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_shopping_cart_item
-- ----------------------------

-- ----------------------------
-- Table structure for t_stocktake_detail
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_stocktake_detail";
CREATE TABLE "public"."t_stocktake_detail" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "stocktake_header_id" uuid NOT NULL,
  "part_id" uuid NOT NULL,
  "system_quantity" int4 NOT NULL,
  "counted_quantity" int4 NOT NULL,
  "discrepancy" int4 GENERATED ALWAYS AS (
(counted_quantity - system_quantity)
) STORED,
  "note" text COLLATE "pg_catalog"."default",
  "counted_by" uuid,
  "counted_at" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6),
  "updated_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_stocktake_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_stocktake_header
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_stocktake_header";
CREATE TABLE "public"."t_stocktake_header" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "stocktake_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "stocktake_date" timestamp(6) NOT NULL DEFAULT now(),
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'DRAFT'::character varying,
  "started_by" uuid,
  "started_at" timestamp(6),
  "completed_by" uuid,
  "completed_at" timestamp(6),
  "total_discrepancy" int4 DEFAULT 0,
  "notes" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "user_id" uuid,
  "whitelabel_id" uuid,
  "deleted" bool NOT NULL,
  "deleted_at" timestamp(6)
)
;

-- ----------------------------
-- Records of t_stocktake_header
-- ----------------------------

-- ----------------------------
-- Table structure for t_web_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_web_order";
CREATE TABLE "public"."t_web_order" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "order_no" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "cart_id" uuid,
  "customer_id" uuid NOT NULL,
  "user_id" uuid,
  "order_date" timestamp(6) NOT NULL DEFAULT now(),
  "order_source" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'WEB'::character varying,
  "status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "payment_status" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'PENDING'::character varying,
  "subtotal" numeric(15,2) NOT NULL,
  "discount" numeric(15,2) DEFAULT 0,
  "tax" numeric(15,2) DEFAULT 0,
  "shipping_cost" numeric(15,2) DEFAULT 0,
  "total" numeric(15,2) NOT NULL,
  "promotion_code" varchar(50) COLLATE "pg_catalog"."default",
  "shipping_address" text COLLATE "pg_catalog"."default" NOT NULL,
  "shipping_phone" varchar(20) COLLATE "pg_catalog"."default",
  "shipping_email" varchar(100) COLLATE "pg_catalog"."default",
  "tracking_number" varchar(50) COLLATE "pg_catalog"."default",
  "courier" varchar(50) COLLATE "pg_catalog"."default",
  "notes" text COLLATE "pg_catalog"."default",
  "payment_method" varchar(30) COLLATE "pg_catalog"."default",
  "payment_transaction_id" varchar(100) COLLATE "pg_catalog"."default",
  "paid_at" timestamp(6),
  "delivered_at" timestamp(6),
  "cancelled_at" timestamp(6),
  "cancellation_reason" text COLLATE "pg_catalog"."default",
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_web_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_web_order_item
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_web_order_item";
CREATE TABLE "public"."t_web_order_item" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "order_id" uuid NOT NULL,
  "item_id" uuid NOT NULL,
  "part_id" uuid,
  "quantity" int4 NOT NULL,
  "unit_price" numeric(15,2) NOT NULL,
  "total_price" numeric(15,2) GENERATED ALWAYS AS (
((quantity)::numeric * unit_price)
) STORED,
  "discount" numeric(15,2) DEFAULT 0,
  "net_price" numeric(15,2) GENERATED ALWAYS AS (
(((quantity)::numeric * unit_price) - discount)
) STORED,
  "attributes" jsonb,
  "created_at" timestamp(6) NOT NULL DEFAULT now()
)
;

-- ----------------------------
-- Records of t_web_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for t_web_order_status_history
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_web_order_status_history";
CREATE TABLE "public"."t_web_order_status_history" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "order_id" uuid NOT NULL,
  "from_status" varchar(20) COLLATE "pg_catalog"."default",
  "to_status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "changed_by" uuid,
  "changed_at" timestamp(6) NOT NULL DEFAULT now(),
  "reason" text COLLATE "pg_catalog"."default",
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of t_web_order_status_history
-- ----------------------------

-- ----------------------------
-- Function structure for generate_adjustment_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_adjustment_no"();
CREATE FUNCTION "public"."generate_adjustment_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_auto_report_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_auto_report_no"();
CREATE FUNCTION "public"."generate_auto_report_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_customer_code
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_customer_code"();
CREATE FUNCTION "public"."generate_customer_code"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(customer_code FROM 10) AS INTEGER)), 0) + 1
        FROM m_customer
        WHERE customer_code LIKE 'CUST-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.customer_code := 'CUST-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_document_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_document_no"();
CREATE FUNCTION "public"."generate_document_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_email_id
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_email_id"();
CREATE FUNCTION "public"."generate_email_id"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_invoice_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_invoice_no"();
CREATE FUNCTION "public"."generate_invoice_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_job_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_job_no"();
CREATE FUNCTION "public"."generate_job_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_payment_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_payment_no"();
CREATE FUNCTION "public"."generate_payment_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_picking_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_picking_no"();
CREATE FUNCTION "public"."generate_picking_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_po_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_po_no"();
CREATE FUNCTION "public"."generate_po_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_quotation_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_quotation_no"();
CREATE FUNCTION "public"."generate_quotation_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_receipt_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_receipt_no"();
CREATE FUNCTION "public"."generate_receipt_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_stocktake_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_stocktake_no"();
CREATE FUNCTION "public"."generate_stocktake_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for generate_web_order_no
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."generate_web_order_no"();
CREATE FUNCTION "public"."generate_web_order_no"()
  RETURNS "pg_catalog"."trigger" AS $BODY$
DECLARE
    year_part TEXT;
    sequence_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    sequence_part := LPAD(CAST((
        SELECT COALESCE(MAX(CAST(SUBSTRING(order_no FROM 8) AS INTEGER)), 0) + 1
        FROM t_web_order
        WHERE order_no LIKE 'WO-' || year_part || '-%'
    ) AS TEXT), 4, '0');
    NEW.order_no := 'WO-' || year_part || '-' || sequence_part;
    RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for uuid_generate_v1
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_generate_v1"();
CREATE FUNCTION "public"."uuid_generate_v1"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_generate_v1'
  LANGUAGE c VOLATILE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_generate_v1mc
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_generate_v1mc"();
CREATE FUNCTION "public"."uuid_generate_v1mc"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_generate_v1mc'
  LANGUAGE c VOLATILE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_generate_v3
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_generate_v3"("namespace" uuid, "name" text);
CREATE FUNCTION "public"."uuid_generate_v3"("namespace" uuid, "name" text)
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_generate_v3'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_generate_v4
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_generate_v4"();
CREATE FUNCTION "public"."uuid_generate_v4"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_generate_v4'
  LANGUAGE c VOLATILE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_generate_v5
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_generate_v5"("namespace" uuid, "name" text);
CREATE FUNCTION "public"."uuid_generate_v5"("namespace" uuid, "name" text)
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_generate_v5'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_nil
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_nil"();
CREATE FUNCTION "public"."uuid_nil"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_nil'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_ns_dns
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_ns_dns"();
CREATE FUNCTION "public"."uuid_ns_dns"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_ns_dns'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_ns_oid
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_ns_oid"();
CREATE FUNCTION "public"."uuid_ns_oid"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_ns_oid'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_ns_url
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_ns_url"();
CREATE FUNCTION "public"."uuid_ns_url"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_ns_url'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- Function structure for uuid_ns_x500
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."uuid_ns_x500"();
CREATE FUNCTION "public"."uuid_ns_x500"()
  RETURNS "pg_catalog"."uuid" AS '$libdir/uuid-ossp', 'uuid_ns_x500'
  LANGUAGE c IMMUTABLE STRICT
  COST 1;

-- ----------------------------
-- View structure for v_dashboard_sales_overview
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_sales_overview";
CREATE VIEW "public"."v_dashboard_sales_overview" AS  WITH sales_data AS (
         SELECT i.id AS invoice_id,
            i.customer_id,
            i.job_id,
            i.invoice_date,
            i.total AS invoice_total,
            COALESCE(( SELECT sum(p.amount) AS sum
                   FROM t_payment p
                  WHERE p.invoice_id = i.id AND p.status::text = 'COMPLETED'::text), 0::numeric) AS amount_paid,
            i.whitelabel_id
           FROM t_invoice_adjustment i
          WHERE i.deleted = false AND i.type::text = 'INVOICE'::text
        )
 SELECT count(DISTINCT invoice_id) AS total_invoices,
    count(DISTINCT customer_id) AS total_customers,
    COALESCE(sum(invoice_total), 0::numeric) AS total_revenue,
    COALESCE(sum(invoice_total - amount_paid), 0::numeric) AS total_outstanding,
    COALESCE(avg(invoice_total), 0::numeric) AS average_invoice,
    date_trunc('month'::text, invoice_date) AS period,
    whitelabel_id
   FROM sales_data
  GROUP BY (date_trunc('month'::text, invoice_date)), whitelabel_id;

-- ----------------------------
-- View structure for v_dashboard_inventory_overview
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_inventory_overview";
CREATE VIEW "public"."v_dashboard_inventory_overview" AS  SELECT count(*) AS total_parts,
    sum(stock_quantity) AS total_quantity,
    COALESCE(sum(stock_quantity::numeric * unit_cost), 0::numeric) AS total_value,
    count(
        CASE
            WHEN stock_quantity <= reorder_level THEN 1
            ELSE NULL::integer
        END) AS low_stock_count,
    count(
        CASE
            WHEN status::text = 'ACTIVE'::text THEN 1
            ELSE NULL::integer
        END) AS active_parts,
    whitelabel_id
   FROM m_part_master
  WHERE deleted = false
  GROUP BY whitelabel_id;

-- ----------------------------
-- View structure for v_dashboard_top_parts
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_top_parts";
CREATE VIEW "public"."v_dashboard_top_parts" AS  SELECT p.id AS part_id,
    p.part_code,
    p.part_name,
    COALESCE(sum(jps.quantity), 0::bigint) AS total_sold,
    COALESCE(sum(jps.net_price), 0::numeric) AS total_revenue,
    p.whitelabel_id,
    row_number() OVER (PARTITION BY p.whitelabel_id ORDER BY (sum(jps.quantity)) DESC) AS rank
   FROM m_part_master p
     LEFT JOIN t_job_part_sales jps ON jps.part_id = p.id
  WHERE p.deleted = false
  GROUP BY p.id, p.part_code, p.part_name, p.whitelabel_id;

-- ----------------------------
-- View structure for v_dashboard_service_category
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_service_category";
CREATE VIEW "public"."v_dashboard_service_category" AS  SELECT c.category_name,
    count(js.id) AS service_count,
    COALESCE(sum(js.net_price), 0::numeric) AS total_revenue,
    js.whitelabel_id
   FROM t_job_service js
     JOIN m_service s ON s.id = js.service_id
     JOIN m_category c ON c.id = s.category_id
  WHERE s.is_active = true
  GROUP BY c.category_name, js.whitelabel_id;

-- ----------------------------
-- View structure for v_dashboard_financial_summary
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_financial_summary";
CREATE VIEW "public"."v_dashboard_financial_summary" AS  WITH invoice_data AS (
         SELECT date_trunc('month'::text, t_invoice_adjustment.invoice_date) AS month,
            COALESCE(sum(t_invoice_adjustment.total), 0::numeric) AS total_invoice,
            t_invoice_adjustment.whitelabel_id
           FROM t_invoice_adjustment
          WHERE t_invoice_adjustment.deleted = false AND t_invoice_adjustment.type::text = 'INVOICE'::text
          GROUP BY (date_trunc('month'::text, t_invoice_adjustment.invoice_date)), t_invoice_adjustment.whitelabel_id
        ), payment_data AS (
         SELECT date_trunc('month'::text, t_payment.payment_date) AS month,
            COALESCE(sum(t_payment.amount), 0::numeric) AS total_payment,
            t_payment.whitelabel_id
           FROM t_payment
          WHERE t_payment.deleted = false AND t_payment.status::text = 'COMPLETED'::text
          GROUP BY (date_trunc('month'::text, t_payment.payment_date)), t_payment.whitelabel_id
        )
 SELECT COALESCE(i.month, p.month) AS month,
    COALESCE(i.total_invoice, 0::numeric) AS total_invoice,
    COALESCE(p.total_payment, 0::numeric) AS total_payment,
    COALESCE(i.total_invoice, 0::numeric) - COALESCE(p.total_payment, 0::numeric) AS net_income,
    COALESCE(i.whitelabel_id, p.whitelabel_id) AS whitelabel_id
   FROM invoice_data i
     FULL JOIN payment_data p ON i.month = p.month AND i.whitelabel_id = p.whitelabel_id;

-- ----------------------------
-- View structure for v_dashboard_revenue_by_period
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_revenue_by_period";
CREATE VIEW "public"."v_dashboard_revenue_by_period" AS  SELECT date_trunc('day'::text, invoice_date) AS period,
    count(*) AS invoice_count,
    COALESCE(sum(total), 0::numeric) AS revenue,
    COALESCE(avg(total), 0::numeric) AS average_revenue,
    whitelabel_id
   FROM t_invoice_adjustment
  WHERE deleted = false AND type::text = 'INVOICE'::text
  GROUP BY (date_trunc('day'::text, invoice_date)), whitelabel_id;

-- ----------------------------
-- View structure for v_email_analytics
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_email_analytics";
CREATE VIEW "public"."v_email_analytics" AS  SELECT date_trunc('day'::text, created_at) AS day,
    count(*) AS total_sent,
    sum(
        CASE
            WHEN status::text = 'SENT'::text THEN 1
            ELSE 0
        END) AS success_count,
    sum(
        CASE
            WHEN status::text = 'FAILED'::text THEN 1
            ELSE 0
        END) AS failed_count,
    sum(
        CASE
            WHEN status::text = 'BOUNCED'::text THEN 1
            ELSE 0
        END) AS bounced_count,
    category,
    whitelabel_id
   FROM t_email_history
  GROUP BY (date_trunc('day'::text, created_at)), category, whitelabel_id;

-- ----------------------------
-- Primary Key structure for table d_widget_config
-- ----------------------------
ALTER TABLE "public"."d_widget_config" ADD CONSTRAINT "d_widget_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_batch_job
-- ----------------------------
ALTER TABLE "public"."m_batch_job" ADD CONSTRAINT "m_batch_job_job_code_key" UNIQUE ("job_code");

-- ----------------------------
-- Primary Key structure for table m_batch_job
-- ----------------------------
ALTER TABLE "public"."m_batch_job" ADD CONSTRAINT "m_batch_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_car
-- ----------------------------
CREATE INDEX "idx_m_car_brand" ON "public"."m_car" USING btree (
  "brand" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_car_customer" ON "public"."m_car" USING btree (
  "customer_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_car_deleted" ON "public"."m_car" USING btree (
  "deleted" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_car_license_plate" ON "public"."m_car" USING btree (
  "license_plate" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_car_whitelabel" ON "public"."m_car" USING btree (
  "whitelabel_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table m_car
-- ----------------------------
ALTER TABLE "public"."m_car" ADD CONSTRAINT "m_car_license_plate_key" UNIQUE ("license_plate");

-- ----------------------------
-- Primary Key structure for table m_car
-- ----------------------------
ALTER TABLE "public"."m_car" ADD CONSTRAINT "m_car_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_car_service_history
-- ----------------------------
CREATE INDEX "idx_m_car_service_history_car" ON "public"."m_car_service_history" USING btree (
  "car_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_car_service_history_date" ON "public"."m_car_service_history" USING btree (
  "service_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table m_car_service_history
-- ----------------------------
ALTER TABLE "public"."m_car_service_history" ADD CONSTRAINT "m_car_service_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_catalogue_category
-- ----------------------------
CREATE INDEX "idx_m_catalogue_category_active" ON "public"."m_catalogue_category" USING btree (
  "is_active" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_catalogue_category_code" ON "public"."m_catalogue_category" USING btree (
  "category_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_catalogue_category_parent" ON "public"."m_catalogue_category" USING btree (
  "parent_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table m_catalogue_category
-- ----------------------------
ALTER TABLE "public"."m_catalogue_category" ADD CONSTRAINT "m_catalogue_category_category_code_key" UNIQUE ("category_code");

-- ----------------------------
-- Primary Key structure for table m_catalogue_category
-- ----------------------------
ALTER TABLE "public"."m_catalogue_category" ADD CONSTRAINT "m_catalogue_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_catalogue_item
-- ----------------------------
CREATE INDEX "idx_m_catalogue_item_active" ON "public"."m_catalogue_item" USING btree (
  "is_active" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_catalogue_item_category" ON "public"."m_catalogue_item" USING btree (
  "category_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_catalogue_item_code" ON "public"."m_catalogue_item" USING btree (
  "item_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_catalogue_item_part" ON "public"."m_catalogue_item" USING btree (
  "part_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_catalogue_item_whitelabel" ON "public"."m_catalogue_item" USING btree (
  "whitelabel_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table m_catalogue_item
-- ----------------------------
ALTER TABLE "public"."m_catalogue_item" ADD CONSTRAINT "m_catalogue_item_item_code_key" UNIQUE ("item_code");

-- ----------------------------
-- Primary Key structure for table m_catalogue_item
-- ----------------------------
ALTER TABLE "public"."m_catalogue_item" ADD CONSTRAINT "m_catalogue_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_category
-- ----------------------------
ALTER TABLE "public"."m_category" ADD CONSTRAINT "m_category_category_code_key" UNIQUE ("category_code");

-- ----------------------------
-- Primary Key structure for table m_category
-- ----------------------------
ALTER TABLE "public"."m_category" ADD CONSTRAINT "m_category_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_city
-- ----------------------------
ALTER TABLE "public"."m_city" ADD CONSTRAINT "m_city_city_code_key" UNIQUE ("city_code");

-- ----------------------------
-- Primary Key structure for table m_city
-- ----------------------------
ALTER TABLE "public"."m_city" ADD CONSTRAINT "m_city_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_country
-- ----------------------------
ALTER TABLE "public"."m_country" ADD CONSTRAINT "m_country_country_code_key" UNIQUE ("country_code");

-- ----------------------------
-- Primary Key structure for table m_country
-- ----------------------------
ALTER TABLE "public"."m_country" ADD CONSTRAINT "m_country_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_currency
-- ----------------------------
ALTER TABLE "public"."m_currency" ADD CONSTRAINT "m_currency_currency_code_key" UNIQUE ("currency_code");

-- ----------------------------
-- Primary Key structure for table m_currency
-- ----------------------------
ALTER TABLE "public"."m_currency" ADD CONSTRAINT "m_currency_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_customer
-- ----------------------------
CREATE INDEX "idx_m_customer_code" ON "public"."m_customer" USING btree (
  "customer_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_customer_deleted" ON "public"."m_customer" USING btree (
  "deleted" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_customer_email" ON "public"."m_customer" USING btree (
  "email" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_customer_phone" ON "public"."m_customer" USING btree (
  "phone_number" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_customer_status" ON "public"."m_customer" USING btree (
  "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_customer_tax_id" ON "public"."m_customer" USING btree (
  "tax_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_customer_whitelabel" ON "public"."m_customer" USING btree (
  "whitelabel_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Triggers structure for table m_customer
-- ----------------------------
CREATE TRIGGER "trg_generate_customer_code" BEFORE INSERT ON "public"."m_customer"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_customer_code"();

-- ----------------------------
-- Uniques structure for table m_customer
-- ----------------------------
ALTER TABLE "public"."m_customer" ADD CONSTRAINT "m_customer_customer_code_key" UNIQUE ("customer_code");

-- ----------------------------
-- Primary Key structure for table m_customer
-- ----------------------------
ALTER TABLE "public"."m_customer" ADD CONSTRAINT "m_customer_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_document_template
-- ----------------------------
ALTER TABLE "public"."m_document_template" ADD CONSTRAINT "m_document_template_template_code_key" UNIQUE ("template_code");

-- ----------------------------
-- Primary Key structure for table m_document_template
-- ----------------------------
ALTER TABLE "public"."m_document_template" ADD CONSTRAINT "m_document_template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_email_template
-- ----------------------------
ALTER TABLE "public"."m_email_template" ADD CONSTRAINT "m_email_template_template_code_key" UNIQUE ("template_code");

-- ----------------------------
-- Primary Key structure for table m_email_template
-- ----------------------------
ALTER TABLE "public"."m_email_template" ADD CONSTRAINT "m_email_template_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_exchange_rate
-- ----------------------------
ALTER TABLE "public"."m_exchange_rate" ADD CONSTRAINT "m_exchange_rate_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_geofence
-- ----------------------------
ALTER TABLE "public"."m_geofence" ADD CONSTRAINT "m_geofence_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_iot_device
-- ----------------------------
ALTER TABLE "public"."m_iot_device" ADD CONSTRAINT "m_iot_device_device_id_key" UNIQUE ("device_id");

-- ----------------------------
-- Primary Key structure for table m_iot_device
-- ----------------------------
ALTER TABLE "public"."m_iot_device" ADD CONSTRAINT "m_iot_device_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_language
-- ----------------------------
ALTER TABLE "public"."m_language" ADD CONSTRAINT "m_language_language_code_key" UNIQUE ("language_code");

-- ----------------------------
-- Primary Key structure for table m_language
-- ----------------------------
ALTER TABLE "public"."m_language" ADD CONSTRAINT "m_language_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_part_master
-- ----------------------------
ALTER TABLE "public"."m_part_master" ADD CONSTRAINT "m_part_master_part_code_key" UNIQUE ("part_code");

-- ----------------------------
-- Primary Key structure for table m_part_master
-- ----------------------------
ALTER TABLE "public"."m_part_master" ADD CONSTRAINT "m_part_master_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_payment_method
-- ----------------------------
ALTER TABLE "public"."m_payment_method" ADD CONSTRAINT "m_payment_method_method_code_key" UNIQUE ("method_code");

-- ----------------------------
-- Primary Key structure for table m_payment_method
-- ----------------------------
ALTER TABLE "public"."m_payment_method" ADD CONSTRAINT "m_payment_method_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_permission
-- ----------------------------
ALTER TABLE "public"."m_permission" ADD CONSTRAINT "m_permission_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table m_permission
-- ----------------------------
ALTER TABLE "public"."m_permission" ADD CONSTRAINT "m_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_promotion
-- ----------------------------
CREATE INDEX "idx_m_promotion_active" ON "public"."m_promotion" USING btree (
  "is_active" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_promotion_code" ON "public"."m_promotion" USING btree (
  "promotion_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_promotion_date" ON "public"."m_promotion" USING btree (
  "start_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST,
  "end_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table m_promotion
-- ----------------------------
ALTER TABLE "public"."m_promotion" ADD CONSTRAINT "m_promotion_promotion_code_key" UNIQUE ("promotion_code");

-- ----------------------------
-- Primary Key structure for table m_promotion
-- ----------------------------
ALTER TABLE "public"."m_promotion" ADD CONSTRAINT "m_promotion_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_province
-- ----------------------------
ALTER TABLE "public"."m_province" ADD CONSTRAINT "m_province_province_code_key" UNIQUE ("province_code");

-- ----------------------------
-- Primary Key structure for table m_province
-- ----------------------------
ALTER TABLE "public"."m_province" ADD CONSTRAINT "m_province_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_rate_limit_log
-- ----------------------------
CREATE INDEX "idx_rate_limit_client" ON "public"."m_rate_limit_log" USING btree (
  "client_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_rate_limit_time" ON "public"."m_rate_limit_log" USING btree (
  "attempted_at" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table m_rate_limit_log
-- ----------------------------
ALTER TABLE "public"."m_rate_limit_log" ADD CONSTRAINT "m_rate_limit_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_role
-- ----------------------------
ALTER TABLE "public"."m_role" ADD CONSTRAINT "m_role_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table m_role
-- ----------------------------
ALTER TABLE "public"."m_role" ADD CONSTRAINT "m_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_role_permission
-- ----------------------------
CREATE INDEX "idx_role_permission_permission" ON "public"."m_role_permission" USING btree (
  "permission_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_role_permission_role" ON "public"."m_role_permission" USING btree (
  "role_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table m_role_permission
-- ----------------------------
ALTER TABLE "public"."m_role_permission" ADD CONSTRAINT "m_role_permission_pkey" PRIMARY KEY ("role_id", "permission_id");

-- ----------------------------
-- Indexes structure for table m_sales_price
-- ----------------------------
CREATE INDEX "idx_m_sales_price_active" ON "public"."m_sales_price" USING btree (
  "is_active" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_sales_price_item" ON "public"."m_sales_price" USING btree (
  "item_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_sales_price_tier" ON "public"."m_sales_price" USING btree (
  "price_tier" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table m_sales_price
-- ----------------------------
ALTER TABLE "public"."m_sales_price" ADD CONSTRAINT "m_sales_price_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_service
-- ----------------------------
ALTER TABLE "public"."m_service" ADD CONSTRAINT "m_service_service_code_key" UNIQUE ("service_code");

-- ----------------------------
-- Primary Key structure for table m_service
-- ----------------------------
ALTER TABLE "public"."m_service" ADD CONSTRAINT "m_service_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_shop_profile
-- ----------------------------
ALTER TABLE "public"."m_shop_profile" ADD CONSTRAINT "m_shop_profile_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_staff
-- ----------------------------
ALTER TABLE "public"."m_staff" ADD CONSTRAINT "m_staff_staff_code_key" UNIQUE ("staff_code");

-- ----------------------------
-- Primary Key structure for table m_staff
-- ----------------------------
ALTER TABLE "public"."m_staff" ADD CONSTRAINT "m_staff_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_stock_location
-- ----------------------------
ALTER TABLE "public"."m_stock_location" ADD CONSTRAINT "m_stock_location_location_code_key" UNIQUE ("location_code");

-- ----------------------------
-- Primary Key structure for table m_stock_location
-- ----------------------------
ALTER TABLE "public"."m_stock_location" ADD CONSTRAINT "m_stock_location_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_supplier
-- ----------------------------
ALTER TABLE "public"."m_supplier" ADD CONSTRAINT "m_supplier_supplier_code_key" UNIQUE ("supplier_code");

-- ----------------------------
-- Primary Key structure for table m_supplier
-- ----------------------------
ALTER TABLE "public"."m_supplier" ADD CONSTRAINT "m_supplier_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_translation
-- ----------------------------
ALTER TABLE "public"."m_translation" ADD CONSTRAINT "m_translation_message_key_language_code_context_key" UNIQUE ("message_key", "language_code", "context");
ALTER TABLE "public"."m_translation" ADD CONSTRAINT "ukisb2hgkl1u5cabd4up3s17idb" UNIQUE ("message_key", "language_code", "context");

-- ----------------------------
-- Primary Key structure for table m_translation
-- ----------------------------
ALTER TABLE "public"."m_translation" ADD CONSTRAINT "m_translation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_user
-- ----------------------------
CREATE INDEX "idx_m_user_deleted" ON "public"."m_user" USING btree (
  "deleted" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_user_email" ON "public"."m_user" USING btree (
  "email" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_m_user_whitelabel" ON "public"."m_user" USING btree (
  "whitelabel_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table m_user
-- ----------------------------
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_email_key" UNIQUE ("email");
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_username_key" UNIQUE ("username");

-- ----------------------------
-- Primary Key structure for table m_user
-- ----------------------------
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table m_user_role
-- ----------------------------
CREATE INDEX "idx_user_role_role" ON "public"."m_user_role" USING btree (
  "role_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_user_role_user" ON "public"."m_user_role" USING btree (
  "user_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table m_user_role
-- ----------------------------
ALTER TABLE "public"."m_user_role" ADD CONSTRAINT "m_user_role_pkey" PRIMARY KEY ("user_id", "role_id");

-- ----------------------------
-- Indexes structure for table m_user_token
-- ----------------------------
CREATE INDEX "idx_user_token_expiry" ON "public"."m_user_token" USING btree (
  "expiry_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "idx_user_token_token" ON "public"."m_user_token" USING btree (
  "token" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_user_token_user" ON "public"."m_user_token" USING btree (
  "user_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table m_user_token
-- ----------------------------
ALTER TABLE "public"."m_user_token" ADD CONSTRAINT "m_user_token_token_key" UNIQUE ("token");

-- ----------------------------
-- Primary Key structure for table m_user_token
-- ----------------------------
ALTER TABLE "public"."m_user_token" ADD CONSTRAINT "m_user_token_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_auto_report
-- ----------------------------
CREATE TRIGGER "trg_generate_auto_report_no" BEFORE INSERT ON "public"."t_auto_report"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_auto_report_no"();

-- ----------------------------
-- Uniques structure for table t_auto_report
-- ----------------------------
ALTER TABLE "public"."t_auto_report" ADD CONSTRAINT "t_auto_report_report_no_key" UNIQUE ("report_no");

-- ----------------------------
-- Primary Key structure for table t_auto_report
-- ----------------------------
ALTER TABLE "public"."t_auto_report" ADD CONSTRAINT "t_auto_report_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_batch_job_history
-- ----------------------------
ALTER TABLE "public"."t_batch_job_history" ADD CONSTRAINT "t_batch_job_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_device_access_log
-- ----------------------------
ALTER TABLE "public"."t_device_access_log" ADD CONSTRAINT "t_device_access_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_device_history
-- ----------------------------
ALTER TABLE "public"."t_device_history" ADD CONSTRAINT "t_device_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_document
-- ----------------------------
CREATE TRIGGER "trg_generate_document_no" BEFORE INSERT ON "public"."t_document"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_document_no"();

-- ----------------------------
-- Uniques structure for table t_document
-- ----------------------------
ALTER TABLE "public"."t_document" ADD CONSTRAINT "t_document_document_no_key" UNIQUE ("document_no");

-- ----------------------------
-- Primary Key structure for table t_document
-- ----------------------------
ALTER TABLE "public"."t_document" ADD CONSTRAINT "t_document_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_document_history
-- ----------------------------
ALTER TABLE "public"."t_document_history" ADD CONSTRAINT "t_document_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_email_history
-- ----------------------------
CREATE TRIGGER "trg_generate_email_id" BEFORE INSERT ON "public"."t_email_history"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_email_id"();

-- ----------------------------
-- Uniques structure for table t_email_history
-- ----------------------------
ALTER TABLE "public"."t_email_history" ADD CONSTRAINT "t_email_history_email_id_key" UNIQUE ("email_id");

-- ----------------------------
-- Primary Key structure for table t_email_history
-- ----------------------------
ALTER TABLE "public"."t_email_history" ADD CONSTRAINT "t_email_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_email_queue
-- ----------------------------
ALTER TABLE "public"."t_email_queue" ADD CONSTRAINT "t_email_queue_email_id_key" UNIQUE ("email_id");

-- ----------------------------
-- Primary Key structure for table t_email_queue
-- ----------------------------
ALTER TABLE "public"."t_email_queue" ADD CONSTRAINT "t_email_queue_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_geofence_alert
-- ----------------------------
ALTER TABLE "public"."t_geofence_alert" ADD CONSTRAINT "t_geofence_alert_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_gps_data
-- ----------------------------
ALTER TABLE "public"."t_gps_data" ADD CONSTRAINT "t_gps_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_inventory
-- ----------------------------
ALTER TABLE "public"."t_inventory" ADD CONSTRAINT "t_inventory_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_inventory_adjustment_detail
-- ----------------------------
ALTER TABLE "public"."t_inventory_adjustment_detail" ADD CONSTRAINT "t_inventory_adjustment_detail_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_inventory_adjustment_header
-- ----------------------------
CREATE TRIGGER "trg_generate_adjustment_no" BEFORE INSERT ON "public"."t_inventory_adjustment_header"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_adjustment_no"();

-- ----------------------------
-- Uniques structure for table t_inventory_adjustment_header
-- ----------------------------
ALTER TABLE "public"."t_inventory_adjustment_header" ADD CONSTRAINT "t_inventory_adjustment_header_adjustment_no_key" UNIQUE ("adjustment_no");

-- ----------------------------
-- Primary Key structure for table t_inventory_adjustment_header
-- ----------------------------
ALTER TABLE "public"."t_inventory_adjustment_header" ADD CONSTRAINT "t_inventory_adjustment_header_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_invoice_adjustment
-- ----------------------------
CREATE TRIGGER "trg_generate_invoice_no" BEFORE INSERT ON "public"."t_invoice_adjustment"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_invoice_no"();

-- ----------------------------
-- Uniques structure for table t_invoice_adjustment
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment" ADD CONSTRAINT "t_invoice_adjustment_invoice_no_key" UNIQUE ("invoice_no");

-- ----------------------------
-- Primary Key structure for table t_invoice_adjustment
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment" ADD CONSTRAINT "t_invoice_adjustment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_invoice_adjustment_part
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment_part" ADD CONSTRAINT "t_invoice_adjustment_part_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_invoice_adjustment_service
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment_service" ADD CONSTRAINT "t_invoice_adjustment_service_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_job
-- ----------------------------
CREATE TRIGGER "trg_generate_job_no" BEFORE INSERT ON "public"."t_job"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_job_no"();

-- ----------------------------
-- Uniques structure for table t_job
-- ----------------------------
ALTER TABLE "public"."t_job" ADD CONSTRAINT "t_job_job_no_key" UNIQUE ("job_no");

-- ----------------------------
-- Primary Key structure for table t_job
-- ----------------------------
ALTER TABLE "public"."t_job" ADD CONSTRAINT "t_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_job_diag_trouble_code
-- ----------------------------
ALTER TABLE "public"."t_job_diag_trouble_code" ADD CONSTRAINT "t_job_diag_trouble_code_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_job_part_sales
-- ----------------------------
ALTER TABLE "public"."t_job_part_sales" ADD CONSTRAINT "t_job_part_sales_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_job_service
-- ----------------------------
ALTER TABLE "public"."t_job_service" ADD CONSTRAINT "t_job_service_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_job_service_car_symptom
-- ----------------------------
ALTER TABLE "public"."t_job_service_car_symptom" ADD CONSTRAINT "t_job_service_car_symptom_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_job_status_history
-- ----------------------------
ALTER TABLE "public"."t_job_status_history" ADD CONSTRAINT "t_job_status_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_ocr_result
-- ----------------------------
ALTER TABLE "public"."t_ocr_result" ADD CONSTRAINT "t_ocr_result_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table t_outstanding_balance
-- ----------------------------
ALTER TABLE "public"."t_outstanding_balance" ADD CONSTRAINT "t_outstanding_balance_invoice_id_key" UNIQUE ("invoice_id");

-- ----------------------------
-- Primary Key structure for table t_outstanding_balance
-- ----------------------------
ALTER TABLE "public"."t_outstanding_balance" ADD CONSTRAINT "t_outstanding_balance_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_part_picking_detail
-- ----------------------------
ALTER TABLE "public"."t_part_picking_detail" ADD CONSTRAINT "t_part_picking_detail_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_part_picking_request
-- ----------------------------
CREATE TRIGGER "trg_generate_picking_no" BEFORE INSERT ON "public"."t_part_picking_request"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_picking_no"();

-- ----------------------------
-- Uniques structure for table t_part_picking_request
-- ----------------------------
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_picking_no_key" UNIQUE ("picking_no");

-- ----------------------------
-- Primary Key structure for table t_part_picking_request
-- ----------------------------
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_payment
-- ----------------------------
CREATE TRIGGER "trg_generate_payment_no" BEFORE INSERT ON "public"."t_payment"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_payment_no"();

-- ----------------------------
-- Uniques structure for table t_payment
-- ----------------------------
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_payment_no_key" UNIQUE ("payment_no");

-- ----------------------------
-- Primary Key structure for table t_payment
-- ----------------------------
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_payment_history
-- ----------------------------
ALTER TABLE "public"."t_payment_history" ADD CONSTRAINT "t_payment_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_purchase_order_detail
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_detail" ADD CONSTRAINT "t_purchase_order_detail_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_purchase_order_header
-- ----------------------------
CREATE TRIGGER "trg_generate_po_no" BEFORE INSERT ON "public"."t_purchase_order_header"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_po_no"();

-- ----------------------------
-- Uniques structure for table t_purchase_order_header
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_header" ADD CONSTRAINT "t_purchase_order_header_po_no_key" UNIQUE ("po_no");

-- ----------------------------
-- Primary Key structure for table t_purchase_order_header
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_header" ADD CONSTRAINT "t_purchase_order_header_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_purchase_order_status_history
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_status_history" ADD CONSTRAINT "t_purchase_order_status_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_quotation
-- ----------------------------
CREATE TRIGGER "trg_generate_quotation_no" BEFORE INSERT ON "public"."t_quotation"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_quotation_no"();

-- ----------------------------
-- Uniques structure for table t_quotation
-- ----------------------------
ALTER TABLE "public"."t_quotation" ADD CONSTRAINT "t_quotation_quotation_no_key" UNIQUE ("quotation_no");

-- ----------------------------
-- Primary Key structure for table t_quotation
-- ----------------------------
ALTER TABLE "public"."t_quotation" ADD CONSTRAINT "t_quotation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_quotation_part
-- ----------------------------
ALTER TABLE "public"."t_quotation_part" ADD CONSTRAINT "t_quotation_part_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_quotation_service
-- ----------------------------
ALTER TABLE "public"."t_quotation_service" ADD CONSTRAINT "t_quotation_service_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_quotation_status_history
-- ----------------------------
ALTER TABLE "public"."t_quotation_status_history" ADD CONSTRAINT "t_quotation_status_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_receipt
-- ----------------------------
CREATE TRIGGER "trg_generate_receipt_no" BEFORE INSERT ON "public"."t_receipt"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_receipt_no"();

-- ----------------------------
-- Uniques structure for table t_receipt
-- ----------------------------
ALTER TABLE "public"."t_receipt" ADD CONSTRAINT "t_receipt_receipt_no_key" UNIQUE ("receipt_no");

-- ----------------------------
-- Primary Key structure for table t_receipt
-- ----------------------------
ALTER TABLE "public"."t_receipt" ADD CONSTRAINT "t_receipt_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table t_shopping_cart
-- ----------------------------
CREATE INDEX "idx_t_shopping_cart_customer" ON "public"."t_shopping_cart" USING btree (
  "customer_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_shopping_cart_expires" ON "public"."t_shopping_cart" USING btree (
  "expires_at" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_shopping_cart_id" ON "public"."t_shopping_cart" USING btree (
  "cart_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_shopping_cart_whitelabel" ON "public"."t_shopping_cart" USING btree (
  "whitelabel_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table t_shopping_cart
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart" ADD CONSTRAINT "t_shopping_cart_cart_id_key" UNIQUE ("cart_id");

-- ----------------------------
-- Primary Key structure for table t_shopping_cart
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart" ADD CONSTRAINT "t_shopping_cart_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table t_shopping_cart_item
-- ----------------------------
CREATE INDEX "idx_t_cart_item_cart" ON "public"."t_shopping_cart_item" USING btree (
  "cart_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_cart_item_item" ON "public"."t_shopping_cart_item" USING btree (
  "item_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table t_shopping_cart_item
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart_item" ADD CONSTRAINT "t_shopping_cart_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_stocktake_detail
-- ----------------------------
ALTER TABLE "public"."t_stocktake_detail" ADD CONSTRAINT "t_stocktake_detail_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Triggers structure for table t_stocktake_header
-- ----------------------------
CREATE TRIGGER "trg_generate_stocktake_no" BEFORE INSERT ON "public"."t_stocktake_header"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_stocktake_no"();

-- ----------------------------
-- Uniques structure for table t_stocktake_header
-- ----------------------------
ALTER TABLE "public"."t_stocktake_header" ADD CONSTRAINT "t_stocktake_header_stocktake_no_key" UNIQUE ("stocktake_no");

-- ----------------------------
-- Primary Key structure for table t_stocktake_header
-- ----------------------------
ALTER TABLE "public"."t_stocktake_header" ADD CONSTRAINT "t_stocktake_header_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table t_web_order
-- ----------------------------
CREATE INDEX "idx_t_web_order_customer" ON "public"."t_web_order" USING btree (
  "customer_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_date" ON "public"."t_web_order" USING btree (
  "order_date" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_deleted" ON "public"."t_web_order" USING btree (
  "deleted" "pg_catalog"."bool_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_no" ON "public"."t_web_order" USING btree (
  "order_no" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_payment_status" ON "public"."t_web_order" USING btree (
  "payment_status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_status" ON "public"."t_web_order" USING btree (
  "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_whitelabel" ON "public"."t_web_order" USING btree (
  "whitelabel_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Triggers structure for table t_web_order
-- ----------------------------
CREATE TRIGGER "trg_generate_web_order_no" BEFORE INSERT ON "public"."t_web_order"
FOR EACH ROW
EXECUTE PROCEDURE "public"."generate_web_order_no"();

-- ----------------------------
-- Uniques structure for table t_web_order
-- ----------------------------
ALTER TABLE "public"."t_web_order" ADD CONSTRAINT "t_web_order_order_no_key" UNIQUE ("order_no");

-- ----------------------------
-- Primary Key structure for table t_web_order
-- ----------------------------
ALTER TABLE "public"."t_web_order" ADD CONSTRAINT "t_web_order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table t_web_order_item
-- ----------------------------
CREATE INDEX "idx_t_web_order_item_item" ON "public"."t_web_order_item" USING btree (
  "item_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_item_order" ON "public"."t_web_order_item" USING btree (
  "order_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table t_web_order_item
-- ----------------------------
ALTER TABLE "public"."t_web_order_item" ADD CONSTRAINT "t_web_order_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table t_web_order_status_history
-- ----------------------------
CREATE INDEX "idx_t_web_order_status_changed" ON "public"."t_web_order_status_history" USING btree (
  "changed_at" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);
CREATE INDEX "idx_t_web_order_status_order" ON "public"."t_web_order_status_history" USING btree (
  "order_id" "pg_catalog"."uuid_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table t_web_order_status_history
-- ----------------------------
ALTER TABLE "public"."t_web_order_status_history" ADD CONSTRAINT "t_web_order_status_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table m_car
-- ----------------------------
ALTER TABLE "public"."m_car" ADD CONSTRAINT "m_car_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_car_service_history
-- ----------------------------
ALTER TABLE "public"."m_car_service_history" ADD CONSTRAINT "m_car_service_history_car_id_fkey" FOREIGN KEY ("car_id") REFERENCES "public"."m_car" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_catalogue_category
-- ----------------------------
ALTER TABLE "public"."m_catalogue_category" ADD CONSTRAINT "m_catalogue_category_parent_id_fkey" FOREIGN KEY ("parent_id") REFERENCES "public"."m_catalogue_category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_catalogue_item
-- ----------------------------
ALTER TABLE "public"."m_catalogue_item" ADD CONSTRAINT "m_catalogue_item_category_id_fkey" FOREIGN KEY ("category_id") REFERENCES "public"."m_catalogue_category" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."m_catalogue_item" ADD CONSTRAINT "m_catalogue_item_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_city
-- ----------------------------
ALTER TABLE "public"."m_city" ADD CONSTRAINT "m_city_province_id_fkey" FOREIGN KEY ("province_id") REFERENCES "public"."m_province" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_province
-- ----------------------------
ALTER TABLE "public"."m_province" ADD CONSTRAINT "m_province_country_id_fkey" FOREIGN KEY ("country_id") REFERENCES "public"."m_country" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_role_permission
-- ----------------------------
ALTER TABLE "public"."m_role_permission" ADD CONSTRAINT "m_role_permission_permission_id_fkey" FOREIGN KEY ("permission_id") REFERENCES "public"."m_permission" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."m_role_permission" ADD CONSTRAINT "m_role_permission_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."m_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_sales_price
-- ----------------------------
ALTER TABLE "public"."m_sales_price" ADD CONSTRAINT "m_sales_price_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."m_catalogue_item" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_translation
-- ----------------------------
ALTER TABLE "public"."m_translation" ADD CONSTRAINT "m_translation_language_code_fkey" FOREIGN KEY ("language_code") REFERENCES "public"."m_language" ("language_code") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_user_role
-- ----------------------------
ALTER TABLE "public"."m_user_role" ADD CONSTRAINT "m_user_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."m_role" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."m_user_role" ADD CONSTRAINT "m_user_role_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."m_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table m_user_token
-- ----------------------------
ALTER TABLE "public"."m_user_token" ADD CONSTRAINT "m_user_token_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."m_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_auto_report
-- ----------------------------
ALTER TABLE "public"."t_auto_report" ADD CONSTRAINT "t_auto_report_generated_by_fkey" FOREIGN KEY ("generated_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_batch_job_history
-- ----------------------------
ALTER TABLE "public"."t_batch_job_history" ADD CONSTRAINT "t_batch_job_history_job_code_fkey" FOREIGN KEY ("job_code") REFERENCES "public"."m_batch_job" ("job_code") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_batch_job_history" ADD CONSTRAINT "t_batch_job_history_triggered_by_fkey" FOREIGN KEY ("triggered_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_device_access_log
-- ----------------------------
ALTER TABLE "public"."t_device_access_log" ADD CONSTRAINT "t_device_access_log_device_id_fkey" FOREIGN KEY ("device_id") REFERENCES "public"."m_iot_device" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_device_access_log" ADD CONSTRAINT "t_device_access_log_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."m_user" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_device_history
-- ----------------------------
ALTER TABLE "public"."t_device_history" ADD CONSTRAINT "t_device_history_device_id_fkey" FOREIGN KEY ("device_id") REFERENCES "public"."m_iot_device" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_device_history" ADD CONSTRAINT "t_device_history_triggered_by_fkey" FOREIGN KEY ("triggered_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_document
-- ----------------------------
ALTER TABLE "public"."t_document" ADD CONSTRAINT "t_document_generated_by_fkey" FOREIGN KEY ("generated_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_document" ADD CONSTRAINT "t_document_sent_by_fkey" FOREIGN KEY ("sent_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_document" ADD CONSTRAINT "t_document_template_id_fkey" FOREIGN KEY ("template_id") REFERENCES "public"."m_document_template" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_document_history
-- ----------------------------
ALTER TABLE "public"."t_document_history" ADD CONSTRAINT "t_document_history_document_id_fkey" FOREIGN KEY ("document_id") REFERENCES "public"."t_document" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_document_history" ADD CONSTRAINT "t_document_history_performed_by_fkey" FOREIGN KEY ("performed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_geofence_alert
-- ----------------------------
ALTER TABLE "public"."t_geofence_alert" ADD CONSTRAINT "t_geofence_alert_device_id_fkey" FOREIGN KEY ("device_id") REFERENCES "public"."m_iot_device" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_geofence_alert" ADD CONSTRAINT "t_geofence_alert_geofence_id_fkey" FOREIGN KEY ("geofence_id") REFERENCES "public"."m_geofence" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_geofence_alert" ADD CONSTRAINT "t_geofence_alert_resolved_by_fkey" FOREIGN KEY ("resolved_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_gps_data
-- ----------------------------
ALTER TABLE "public"."t_gps_data" ADD CONSTRAINT "t_gps_data_device_id_fkey" FOREIGN KEY ("device_id") REFERENCES "public"."m_iot_device" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_inventory
-- ----------------------------
ALTER TABLE "public"."t_inventory" ADD CONSTRAINT "t_inventory_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_inventory_adjustment_detail
-- ----------------------------
ALTER TABLE "public"."t_inventory_adjustment_detail" ADD CONSTRAINT "t_inventory_adjustment_detail_adjustment_header_id_fkey" FOREIGN KEY ("adjustment_header_id") REFERENCES "public"."t_inventory_adjustment_header" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_inventory_adjustment_detail" ADD CONSTRAINT "t_inventory_adjustment_detail_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_inventory_adjustment_header
-- ----------------------------
ALTER TABLE "public"."t_inventory_adjustment_header" ADD CONSTRAINT "t_inventory_adjustment_header_approved_by_fkey" FOREIGN KEY ("approved_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_invoice_adjustment
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment" ADD CONSTRAINT "t_invoice_adjustment_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_invoice_adjustment" ADD CONSTRAINT "t_invoice_adjustment_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_invoice_adjustment_part
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment_part" ADD CONSTRAINT "t_invoice_adjustment_part_invoice_id_fkey" FOREIGN KEY ("invoice_id") REFERENCES "public"."t_invoice_adjustment" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_invoice_adjustment_part" ADD CONSTRAINT "t_invoice_adjustment_part_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_invoice_adjustment_service
-- ----------------------------
ALTER TABLE "public"."t_invoice_adjustment_service" ADD CONSTRAINT "t_invoice_adjustment_service_invoice_id_fkey" FOREIGN KEY ("invoice_id") REFERENCES "public"."t_invoice_adjustment" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_invoice_adjustment_service" ADD CONSTRAINT "t_invoice_adjustment_service_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "public"."m_service" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_job
-- ----------------------------
ALTER TABLE "public"."t_job" ADD CONSTRAINT "t_job_car_id_fkey" FOREIGN KEY ("car_id") REFERENCES "public"."m_car" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_job" ADD CONSTRAINT "t_job_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_job" ADD CONSTRAINT "t_job_mechanic_id_fkey" FOREIGN KEY ("mechanic_id") REFERENCES "public"."m_staff" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_job_diag_trouble_code
-- ----------------------------
ALTER TABLE "public"."t_job_diag_trouble_code" ADD CONSTRAINT "t_job_diag_trouble_code_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_job_part_sales
-- ----------------------------
ALTER TABLE "public"."t_job_part_sales" ADD CONSTRAINT "t_job_part_sales_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_job_part_sales" ADD CONSTRAINT "t_job_part_sales_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_job_service
-- ----------------------------
ALTER TABLE "public"."t_job_service" ADD CONSTRAINT "t_job_service_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_job_service" ADD CONSTRAINT "t_job_service_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "public"."m_service" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_job_service_car_symptom
-- ----------------------------
ALTER TABLE "public"."t_job_service_car_symptom" ADD CONSTRAINT "t_job_service_car_symptom_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_job_status_history
-- ----------------------------
ALTER TABLE "public"."t_job_status_history" ADD CONSTRAINT "t_job_status_history_changed_by_fkey" FOREIGN KEY ("changed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_job_status_history" ADD CONSTRAINT "t_job_status_history_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_ocr_result
-- ----------------------------
ALTER TABLE "public"."t_ocr_result" ADD CONSTRAINT "t_ocr_result_document_id_fkey" FOREIGN KEY ("document_id") REFERENCES "public"."t_document" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_outstanding_balance
-- ----------------------------
ALTER TABLE "public"."t_outstanding_balance" ADD CONSTRAINT "t_outstanding_balance_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_part_picking_detail
-- ----------------------------
ALTER TABLE "public"."t_part_picking_detail" ADD CONSTRAINT "t_part_picking_detail_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_part_picking_detail" ADD CONSTRAINT "t_part_picking_detail_picking_request_id_fkey" FOREIGN KEY ("picking_request_id") REFERENCES "public"."t_part_picking_request" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_part_picking_request
-- ----------------------------
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_confirmed_by_fkey" FOREIGN KEY ("confirmed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_picked_by_fkey" FOREIGN KEY ("picked_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_quotation_id_fkey" FOREIGN KEY ("quotation_id") REFERENCES "public"."t_quotation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_part_picking_request" ADD CONSTRAINT "t_part_picking_request_requested_by_fkey" FOREIGN KEY ("requested_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_payment
-- ----------------------------
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_approved_by_fkey" FOREIGN KEY ("approved_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_payment_method_id_fkey" FOREIGN KEY ("payment_method_id") REFERENCES "public"."m_payment_method" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_payment" ADD CONSTRAINT "t_payment_received_by_fkey" FOREIGN KEY ("received_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_payment_history
-- ----------------------------
ALTER TABLE "public"."t_payment_history" ADD CONSTRAINT "t_payment_history_changed_by_fkey" FOREIGN KEY ("changed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_payment_history" ADD CONSTRAINT "t_payment_history_payment_id_fkey" FOREIGN KEY ("payment_id") REFERENCES "public"."t_payment" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_purchase_order_detail
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_detail" ADD CONSTRAINT "t_purchase_order_detail_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_purchase_order_detail" ADD CONSTRAINT "t_purchase_order_detail_po_header_id_fkey" FOREIGN KEY ("po_header_id") REFERENCES "public"."t_purchase_order_header" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_purchase_order_header
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_header" ADD CONSTRAINT "t_purchase_order_header_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_purchase_order_header" ADD CONSTRAINT "t_purchase_order_header_quotation_id_fkey" FOREIGN KEY ("quotation_id") REFERENCES "public"."t_quotation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_purchase_order_header" ADD CONSTRAINT "t_purchase_order_header_received_by_fkey" FOREIGN KEY ("received_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_purchase_order_header" ADD CONSTRAINT "t_purchase_order_header_supplier_id_fkey" FOREIGN KEY ("supplier_id") REFERENCES "public"."m_supplier" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_purchase_order_status_history
-- ----------------------------
ALTER TABLE "public"."t_purchase_order_status_history" ADD CONSTRAINT "t_purchase_order_status_history_changed_by_fkey" FOREIGN KEY ("changed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_purchase_order_status_history" ADD CONSTRAINT "t_purchase_order_status_history_po_header_id_fkey" FOREIGN KEY ("po_header_id") REFERENCES "public"."t_purchase_order_header" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_quotation
-- ----------------------------
ALTER TABLE "public"."t_quotation" ADD CONSTRAINT "t_quotation_approved_by_fkey" FOREIGN KEY ("approved_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_quotation" ADD CONSTRAINT "t_quotation_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_quotation" ADD CONSTRAINT "t_quotation_job_id_fkey" FOREIGN KEY ("job_id") REFERENCES "public"."t_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_quotation_part
-- ----------------------------
ALTER TABLE "public"."t_quotation_part" ADD CONSTRAINT "t_quotation_part_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_quotation_part" ADD CONSTRAINT "t_quotation_part_quotation_id_fkey" FOREIGN KEY ("quotation_id") REFERENCES "public"."t_quotation" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_quotation_service
-- ----------------------------
ALTER TABLE "public"."t_quotation_service" ADD CONSTRAINT "t_quotation_service_quotation_id_fkey" FOREIGN KEY ("quotation_id") REFERENCES "public"."t_quotation" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_quotation_service" ADD CONSTRAINT "t_quotation_service_service_id_fkey" FOREIGN KEY ("service_id") REFERENCES "public"."m_service" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_quotation_status_history
-- ----------------------------
ALTER TABLE "public"."t_quotation_status_history" ADD CONSTRAINT "t_quotation_status_history_changed_by_fkey" FOREIGN KEY ("changed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_quotation_status_history" ADD CONSTRAINT "t_quotation_status_history_quotation_id_fkey" FOREIGN KEY ("quotation_id") REFERENCES "public"."t_quotation" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_receipt
-- ----------------------------
ALTER TABLE "public"."t_receipt" ADD CONSTRAINT "t_receipt_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_receipt" ADD CONSTRAINT "t_receipt_issued_by_fkey" FOREIGN KEY ("issued_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_receipt" ADD CONSTRAINT "t_receipt_payment_id_fkey" FOREIGN KEY ("payment_id") REFERENCES "public"."t_payment" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_shopping_cart
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart" ADD CONSTRAINT "t_shopping_cart_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_shopping_cart" ADD CONSTRAINT "t_shopping_cart_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_shopping_cart_item
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart_item" ADD CONSTRAINT "t_shopping_cart_item_cart_id_fkey" FOREIGN KEY ("cart_id") REFERENCES "public"."t_shopping_cart" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_shopping_cart_item" ADD CONSTRAINT "t_shopping_cart_item_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."m_catalogue_item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_stocktake_detail
-- ----------------------------
ALTER TABLE "public"."t_stocktake_detail" ADD CONSTRAINT "t_stocktake_detail_counted_by_fkey" FOREIGN KEY ("counted_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_stocktake_detail" ADD CONSTRAINT "t_stocktake_detail_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_stocktake_detail" ADD CONSTRAINT "t_stocktake_detail_stocktake_header_id_fkey" FOREIGN KEY ("stocktake_header_id") REFERENCES "public"."t_stocktake_header" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_stocktake_header
-- ----------------------------
ALTER TABLE "public"."t_stocktake_header" ADD CONSTRAINT "t_stocktake_header_completed_by_fkey" FOREIGN KEY ("completed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_stocktake_header" ADD CONSTRAINT "t_stocktake_header_started_by_fkey" FOREIGN KEY ("started_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_web_order
-- ----------------------------
ALTER TABLE "public"."t_web_order" ADD CONSTRAINT "t_web_order_cart_id_fkey" FOREIGN KEY ("cart_id") REFERENCES "public"."t_shopping_cart" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_web_order" ADD CONSTRAINT "t_web_order_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."m_customer" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_web_order" ADD CONSTRAINT "t_web_order_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_web_order_item
-- ----------------------------
ALTER TABLE "public"."t_web_order_item" ADD CONSTRAINT "t_web_order_item_item_id_fkey" FOREIGN KEY ("item_id") REFERENCES "public"."m_catalogue_item" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_web_order_item" ADD CONSTRAINT "t_web_order_item_order_id_fkey" FOREIGN KEY ("order_id") REFERENCES "public"."t_web_order" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."t_web_order_item" ADD CONSTRAINT "t_web_order_item_part_id_fkey" FOREIGN KEY ("part_id") REFERENCES "public"."m_part_master" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table t_web_order_status_history
-- ----------------------------
ALTER TABLE "public"."t_web_order_status_history" ADD CONSTRAINT "t_web_order_status_history_changed_by_fkey" FOREIGN KEY ("changed_by") REFERENCES "public"."m_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."t_web_order_status_history" ADD CONSTRAINT "t_web_order_status_history_order_id_fkey" FOREIGN KEY ("order_id") REFERENCES "public"."t_web_order" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
