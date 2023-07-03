/*
Navicat PGSQL Data Transfer

Source Server         : Mobile Banking Server
Source Server Version : 90304
Source Host           : 172.18.0.68:5432
Source Database       : MFSDB
Source Schema         : mfs

Target Server Type    : PGSQL
Target Server Version : 90304
File Encoding         : 65001

Date: 2016-04-27 09:50:20
*/


-- ----------------------------
-- Table structure for t_service_downtime
-- ----------------------------
DROP TABLE IF EXISTS "mfs"."t_service_downtime";
CREATE TABLE "mfs"."t_service_downtime" (
"downtime_id" int8 NOT NULL,
"downtime_start" timestamp(6) NOT NULL,
"downtime_end" timestamp(6) NOT NULL,
"downtime_desc" varchar(500) COLLATE "default",
"created_dt" timestamp(6),
"created_by" int8,
"last_updated_by" int8,
"last_updated_dt" timestamp(6)
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table t_service_downtime
-- ----------------------------
ALTER TABLE "mfs"."t_service_downtime" ADD PRIMARY KEY ("downtime_id");
