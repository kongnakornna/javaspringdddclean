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

 Date: 04/07/2026 18:11:40
*/


-- ----------------------------
-- Table structure for m_batch_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_batch_job";
CREATE TABLE "public"."m_batch_job" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "job_code" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "job_type" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
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
  "whitelabel_id" uuid
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
  "license_plate" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "province" varchar(50) COLLATE "pg_catalog"."default",
  "brand" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "model" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sub_model" varchar(100) COLLATE "pg_catalog"."default",
  "year" int4,
  "color" varchar(30) COLLATE "pg_catalog"."default",
  "engine_number" varchar(50) COLLATE "pg_catalog"."default",
  "chassis_number" varchar(50) COLLATE "pg_catalog"."default",
  "fuel_type" varchar(20) COLLATE "pg_catalog"."default",
  "transmission_type" varchar(20) COLLATE "pg_catalog"."default",
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
  "service_type" varchar(50) COLLATE "pg_catalog"."default",
  "description" text COLLATE "pg_catalog"."default",
  "total_cost" numeric(15,2),
  "mileage_at_service" int4,
  "mechanic_name" varchar(100) COLLATE "pg_catalog"."default",
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
  "customer_code" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "display_name" varchar(200) COLLATE "pg_catalog"."default",
  "customer_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'INDIVIDUAL'::character varying,
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'ACTIVE'::character varying,
  "tax_id" varchar(20) COLLATE "pg_catalog"."default",
  "email" varchar(100) COLLATE "pg_catalog"."default",
  "phone_number" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "secondary_phone" varchar(20) COLLATE "pg_catalog"."default",
  "address" text COLLATE "pg_catalog"."default",
  "province" varchar(100) COLLATE "pg_catalog"."default",
  "city" varchar(100) COLLATE "pg_catalog"."default",
  "district" varchar(100) COLLATE "pg_catalog"."default",
  "postal_code" varchar(10) COLLATE "pg_catalog"."default",
  "country" varchar(50) COLLATE "pg_catalog"."default" DEFAULT 'Thailand'::character varying,
  "contact_person" varchar(100) COLLATE "pg_catalog"."default",
  "contact_phone" varchar(20) COLLATE "pg_catalog"."default",
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "language_name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "language_name_en" varchar(50) COLLATE "pg_catalog"."default",
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
  "whitelabel_id" uuid
)
;

-- ----------------------------
-- Records of m_language
-- ----------------------------
INSERT INTO "public"."m_language" VALUES ('e4c70ae9-2861-4650-9374-8ee539bb9557', 'th', 'ภาษาไทย', 'Thai', '🇹🇭', 'f', 't', 't', 1, 'th_TH', 'dd/MM/yyyy', NULL, NULL, '฿', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_language" VALUES ('85124848-faa4-4aa7-a9ec-364f7a141fe6', 'en', 'English', 'English', '🇬🇧', 'f', 't', 'f', 2, 'en_US', 'MM/dd/yyyy', NULL, NULL, '$', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_language" VALUES ('8afd0c4f-189c-4326-a54c-b5f28fe451c1', 'zh', '中文', 'Chinese', '🇨🇳', 'f', 't', 'f', 3, 'zh_CN', 'yyyy/MM/dd', NULL, NULL, '¥', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');
INSERT INTO "public"."m_language" VALUES ('3c96128d-b62f-41f7-8203-59f89b87f289', 'ja', '日本語', 'Japanese', '🇯🇵', 'f', 't', 'f', 4, 'ja_JP', 'yyyy/MM/dd', NULL, NULL, '¥', '2026-07-04 16:44:18.482161', NULL, '00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000001');

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
  "image_url" text COLLATE "pg_catalog"."default",
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
  "whitelabel_id" uuid
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
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "description" text COLLATE "pg_catalog"."default",
  "action" varchar(50) COLLATE "pg_catalog"."default",
  "resource" varchar(50) COLLATE "pg_catalog"."default",
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
-- Table structure for m_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_role";
CREATE TABLE "public"."m_role" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "email" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "password_hash" text COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'ACTIVE'::character varying,
  "phone_number" varchar(20) COLLATE "pg_catalog"."default",
  "profile_image_url" text COLLATE "pg_catalog"."default",
  "last_login" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "updated_at" timestamp(6),
  "deleted_at" timestamp(6),
  "deleted" bool DEFAULT false,
  "user_id" uuid,
  "whitelabel_id" uuid,
  "role" varchar(50) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of m_user
-- ----------------------------
INSERT INTO "public"."m_user" VALUES ('00000000-0000-0000-0000-000000000001', 'admin', 'admin@autorepair.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'System Admin', 'ACTIVE', '081-111-1111', NULL, NULL, '2026-07-04 16:44:18.482161', NULL, NULL, 'f', NULL, NULL, NULL);
INSERT INTO "public"."m_user" VALUES ('5c8aad33-af4c-4955-be81-e2db10173b45', 'service1', 'service1@autorepair.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Service Advisor 1', 'ACTIVE', '081-222-2222', NULL, NULL, '2026-07-04 16:44:18.482161', NULL, NULL, 'f', NULL, NULL, NULL);
INSERT INTO "public"."m_user" VALUES ('b14073d4-c01d-4d85-8153-5030e98507a7', 'mechanic1', 'mechanic1@autorepair.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Mechanic 1', 'ACTIVE', '081-333-3333', NULL, NULL, '2026-07-04 16:44:18.482161', NULL, NULL, 'f', NULL, NULL, NULL);

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

-- ----------------------------
-- Table structure for m_user_token
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_user_token";
CREATE TABLE "public"."m_user_token" (
  "id" uuid NOT NULL DEFAULT gen_random_uuid(),
  "user_id" uuid NOT NULL,
  "token" text COLLATE "pg_catalog"."default" NOT NULL,
  "token_type" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "expiry_date" timestamp(6) NOT NULL,
  "revoked" bool DEFAULT false,
  "revoked_at" timestamp(6),
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "user_agent" text COLLATE "pg_catalog"."default",
  "ip_address" inet
)
;

-- ----------------------------
-- Records of m_user_token
-- ----------------------------

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
  "job_code" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "started_at" timestamp(6) NOT NULL DEFAULT now(),
  "finished_at" timestamp(6),
  "status" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "error_message" text COLLATE "pg_catalog"."default",
  "result_summary" text COLLATE "pg_catalog"."default",
  "records_processed" int4 DEFAULT 0,
  "duration_ms" int4,
  "trigger_type" varchar(20) COLLATE "pg_catalog"."default" DEFAULT 'SCHEDULED'::character varying,
  "triggered_by" uuid,
  "parameters" jsonb,
  "created_at" timestamp(6) NOT NULL DEFAULT now(),
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "job_no" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "customer_id" uuid NOT NULL,
  "car_id" uuid NOT NULL,
  "mechanic_id" uuid,
  "status" varchar(30) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'OPEN'::character varying,
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "from_status" varchar(30) COLLATE "pg_catalog"."default",
  "to_status" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "payment_terms" text COLLATE "pg_catalog"."default",
  "delivery_address" text COLLATE "pg_catalog"."default",
  "notes" text COLLATE "pg_catalog"."default",
  "terms_and_conditions" text COLLATE "pg_catalog"."default",
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
  "whitelabel_id" uuid
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
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(customer_code FROM 10) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM m_customer WHERE customer_code LIKE 'CUST-' || year_part || '-%';
    NEW.customer_code := 'CUST-' || year_part || '-' || seq_part;
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
    seq_part TEXT;
BEGIN
    year_part := TO_CHAR(NOW(), 'YYYY');
    seq_part := LPAD(CAST(COALESCE(MAX(CAST(SUBSTRING(order_no FROM 8) AS INTEGER)), 0) + 1 AS TEXT), 4, '0')
        FROM t_web_order WHERE order_no LIKE 'WO-' || year_part || '-%';
    NEW.order_no := 'WO-' || year_part || '-' || seq_part;
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
-- View structure for v_dashboard_job_status
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_dashboard_job_status";
CREATE VIEW "public"."v_dashboard_job_status" AS  SELECT status,
    count(*) AS count,
    whitelabel_id
   FROM t_job
  WHERE deleted = false
  GROUP BY status, whitelabel_id;

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
-- View structure for v_available_languages
-- ----------------------------
DROP VIEW IF EXISTS "public"."v_available_languages";
CREATE VIEW "public"."v_available_languages" AS  SELECT language_code,
    language_name,
    language_name_en,
    flag_emoji,
    is_rtl,
    is_default,
    locale,
    date_format,
    currency_symbol
   FROM m_language
  WHERE is_active = true;

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
-- Uniques structure for table m_batch_job
-- ----------------------------
ALTER TABLE "public"."m_batch_job" ADD CONSTRAINT "m_batch_job_job_code_key" UNIQUE ("job_code");

-- ----------------------------
-- Primary Key structure for table m_batch_job
-- ----------------------------
ALTER TABLE "public"."m_batch_job" ADD CONSTRAINT "m_batch_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_car
-- ----------------------------
ALTER TABLE "public"."m_car" ADD CONSTRAINT "m_car_license_plate_key" UNIQUE ("license_plate");

-- ----------------------------
-- Primary Key structure for table m_car
-- ----------------------------
ALTER TABLE "public"."m_car" ADD CONSTRAINT "m_car_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_car_service_history
-- ----------------------------
ALTER TABLE "public"."m_car_service_history" ADD CONSTRAINT "m_car_service_history_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_catalogue_category
-- ----------------------------
ALTER TABLE "public"."m_catalogue_category" ADD CONSTRAINT "m_catalogue_category_category_code_key" UNIQUE ("category_code");

-- ----------------------------
-- Primary Key structure for table m_catalogue_category
-- ----------------------------
ALTER TABLE "public"."m_catalogue_category" ADD CONSTRAINT "m_catalogue_category_pkey" PRIMARY KEY ("id");

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
-- Uniques structure for table m_role
-- ----------------------------
ALTER TABLE "public"."m_role" ADD CONSTRAINT "m_role_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table m_role
-- ----------------------------
ALTER TABLE "public"."m_role" ADD CONSTRAINT "m_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_role_permission
-- ----------------------------
ALTER TABLE "public"."m_role_permission" ADD CONSTRAINT "m_role_permission_pkey" PRIMARY KEY ("role_id", "permission_id");

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

-- ----------------------------
-- Primary Key structure for table m_translation
-- ----------------------------
ALTER TABLE "public"."m_translation" ADD CONSTRAINT "m_translation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table m_user
-- ----------------------------
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_username_key" UNIQUE ("username");
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_email_key" UNIQUE ("email");

-- ----------------------------
-- Primary Key structure for table m_user
-- ----------------------------
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_user_role
-- ----------------------------
ALTER TABLE "public"."m_user_role" ADD CONSTRAINT "m_user_role_pkey" PRIMARY KEY ("user_id", "role_id");

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
-- Uniques structure for table t_shopping_cart
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart" ADD CONSTRAINT "t_shopping_cart_cart_id_key" UNIQUE ("cart_id");

-- ----------------------------
-- Primary Key structure for table t_shopping_cart
-- ----------------------------
ALTER TABLE "public"."t_shopping_cart" ADD CONSTRAINT "t_shopping_cart_pkey" PRIMARY KEY ("id");

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
-- Primary Key structure for table t_web_order_item
-- ----------------------------
ALTER TABLE "public"."t_web_order_item" ADD CONSTRAINT "t_web_order_item_pkey" PRIMARY KEY ("id");

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
