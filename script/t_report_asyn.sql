/*
Navicat PGSQL Data Transfer

Source Server         : Localhost
Source Server Version : 90103
Source Host           : localhost:5432
Source Database       : mfsdb
Source Schema         : mfs

Target Server Type    : PGSQL
Target Server Version : 90103
File Encoding         : 65001

Date: 2016-05-02 16:59:35
*/


-- ----------------------------
-- Table structure for t_report_asyn
-- ----------------------------
DROP TABLE IF EXISTS "mfs"."t_report_asyn";
CREATE TABLE "mfs"."t_report_asyn" (
"report_id" int8 NOT NULL,
"report_type" varchar(100) COLLATE "default",
"report_param" varchar(500) COLLATE "default",
"report_status" varchar(10) COLLATE "default",
"submited_by" int8,
"submited_date" timestamp(6),
"completed_date" timestamp(6),
"file_path" varchar(500) COLLATE "default",
"remark" varchar(500) COLLATE "default",
"file_type" varchar(10) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table t_report_asyn
-- ----------------------------
ALTER TABLE "mfs"."t_report_asyn" ADD PRIMARY KEY ("report_id");
