--
-- ER/Studio 8.0 SQL Code Generation
-- Company :      xyz
-- Project :      mfs.dm1
-- Author :       zuhron
--
-- Date Created : Thursday, January 21, 2016 15:12:03
-- Target DBMS : PostgreSQL 8.0
--


-- Sequence: seq_m_activation_code
DROP SEQUENCE IF EXISTS mfs.seq_m_activation_code CASCADE;
CREATE SEQUENCE mfs.seq_m_activation_code
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_activation_code OWNER TO postgres;
SELECT setval('mfs.seq_m_activation_code', 1, true);


-- Sequence: seq_log_igate
DROP SEQUENCE IF EXISTS mfs.seq_log_igate CASCADE;
CREATE SEQUENCE mfs.seq_log_igate
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_log_igate OWNER TO postgres;


-- Sequence: seq_m_agent
DROP SEQUENCE IF EXISTS mfs.seq_m_agent CASCADE;
CREATE SEQUENCE mfs.seq_m_agent
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_agent OWNER TO postgres;


-- Sequence: seq_m_cust
DROP SEQUENCE IF EXISTS mfs.seq_m_cust CASCADE;
CREATE SEQUENCE mfs.seq_m_cust
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_cust OWNER TO postgres;


-- Sequence: seq_m_fee_structure
DROP SEQUENCE IF EXISTS mfs.seq_m_fee_structure CASCADE;
CREATE SEQUENCE mfs.seq_m_fee_structure
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 99999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_fee_structure OWNER TO postgres;


-- Sequence: seq_m_menu_detail
DROP SEQUENCE IF EXISTS mfs.seq_m_component CASCADE;
CREATE SEQUENCE mfs.seq_m_component
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_component OWNER TO postgres;


-- Sequence: seq_m_role
DROP SEQUENCE IF EXISTS mfs.seq_m_role CASCADE;
CREATE SEQUENCE mfs.seq_m_role
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_role OWNER TO postgres;


-- Sequence: seq_t_broadcast_sms
DROP SEQUENCE IF EXISTS mfs.seq_t_broadcast_sms CASCADE;
CREATE SEQUENCE mfs.seq_t_broadcast_sms
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_t_broadcast_sms OWNER TO postgres;


-- Sequence: seq_t_trans_finance
DROP SEQUENCE IF EXISTS mfs.seq_t_trans_finance CASCADE;
CREATE SEQUENCE mfs.seq_t_trans_finance
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_t_trans_finance OWNER TO postgres;


-- Sequence: seq_m_role_detail
DROP SEQUENCE IF EXISTS mfs.seq_m_role_detail CASCADE;
CREATE SEQUENCE mfs.seq_m_role_detail
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_role_detail OWNER TO postgres;


-- Sequence: seq_t_mapping_hierarchy
DROP SEQUENCE IF EXISTS mfs.seq_t_mapping_hierarchy CASCADE;
CREATE SEQUENCE mfs.seq_t_mapping_hierarchy
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_t_mapping_hierarchy OWNER TO postgres;


-- Sequence: seq_m_user
DROP SEQUENCE IF EXISTS mfs.seq_m_user CASCADE;	
CREATE SEQUENCE mfs.seq_m_user
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_m_user OWNER TO postgres;


-- Sequence: seq_t_mapping_userole
DROP SEQUENCE IF EXISTS mfs.seq_t_mapping_userole CASCADE;
CREATE SEQUENCE mfs.seq_t_mapping_userole
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_t_mapping_userole OWNER TO postgres;


-- Sequence: seq_t_upload_file
DROP SEQUENCE IF EXISTS mfs.seq_t_upload_file CASCADE;
CREATE SEQUENCE mfs.seq_t_upload_file
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_t_upload_file OWNER TO postgres;


-- Sequence: seq_log_audit_trail
DROP SEQUENCE IF EXISTS mfs.seq_log_audit_trail CASCADE;
CREATE SEQUENCE mfs.seq_log_audit_trail
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 999999999999999999
	START 1
	CACHE 1;
ALTER TABLE mfs.seq_log_audit_trail OWNER TO postgres;


-- Sequence: seq_m_param_config
DROP SEQUENCE IF EXISTS mfs.seq_m_param_config CASCADE;
CREATE SEQUENCE mfs.seq_m_param_config
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999999999999
  START 1
  CACHE 1;
ALTER TABLE mfs.seq_m_param_config OWNER TO postgres;
  

-- 
-- TABLE: m_module 
--
DROP TABLE IF EXISTS mfs.m_module CASCADE;
CREATE TABLE mfs.m_module(
    module_id		    int8 NOT NULL,
    module_name           varchar(100),
    CONSTRAINT "Pkey_m_module" PRIMARY KEY ("module_id") USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: log_audit_trail 
--
DROP TABLE IF EXISTS mfs.log_audit_trail CASCADE;
CREATE TABLE mfs.log_audit_trail
(
  log_audit_id bigint NOT NULL,
  module_id bigint,
  action_name character varying(50),
  cid character varying(30),
  data_before character varying(1024),
  data_after character varying(1024),
  app_type character varying(10),
  created_by bigint,
  created_date timestamp without time zone,
  last_updated_by bigint,
  last_updated_date timestamp without time zone,
  menu_name character varying(50),
  CONSTRAINT log_audit_trail_pkey PRIMARY KEY (log_audit_id )
)
;


-- 
-- TABLE: log_file_upload 
--
DROP TABLE IF EXISTS mfs.log_file_upload CASCADE;
CREATE TABLE mfs.log_file_upload(
    log_upload_id              int8            NOT NULL,
    upload_filename_orig       varchar(120),
    upload_filename_invalid    varchar(120),
    upload_status              char(10),
    upload_date                timestamp,
    upload_flag_proses         int4,
    total_rows                 int4,
    total_valid                int4,
    total_invalid              int4,
    created_by                 int8,
    created_date               timestamp,
    last_updated_by            int8,
    last_updated_date          timestamp,
    CONSTRAINT "PK29" PRIMARY KEY (log_upload_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: log_igate 
--
DROP TABLE IF EXISTS mfs.log_igate CASCADE;
CREATE TABLE mfs.log_igate(
    log_igate_id          int8             NOT NULL,
    trans_id              int8,
    "LOG_ISOMSG_REQUEST"  varchar(1024),
    log_isomsg_resp       varchar(1024),
    source_cid            varchar(30),
    target_cid            varchar(30),
    post_date             timestamp,
    capture_date          timestamp,
    trans_type            varchar(10),
    created_by            int8,
    created_date          timestamp,
    last_updated_by       int8,
    last_updated_date     timestamp,
    CONSTRAINT "PK21" PRIMARY KEY (log_igate_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: log_mobile_error 
--
DROP TABLE IF EXISTS mfs.log_mobile_error CASCADE;
CREATE TABLE mfs.log_mobile_error(
    log_id                     int8             NOT NULL,
    log_date                   timestamp,
    log_message                varchar(1024),
    app_version                varchar(10),
    os_version                 varchar(100),
    locale                     varchar(100),
    device_model               varchar(100),
    device_brand               varchar(100),
    device_id                  varchar(100),
    device_board               varchar(100),
    device_total_memory        int8,
    device_available_memory    int8,
    log_upload_date            timestamp,
    CONSTRAINT "PK28" PRIMARY KEY (log_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_activation_code 
--
DROP TABLE IF EXISTS mfs.m_activation_code CASCADE;
CREATE TABLE mfs.m_activation_code(
    act_id               int8           NOT NULL,
    act_code             varchar(10)    NOT NULL,
    created_date         timestamp,
    cid                  varchar(30),
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK18" PRIMARY KEY (act_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_agent 
--
DROP TABLE IF EXISTS mfs.m_agent CASCADE;
CREATE TABLE mfs.m_agent(
    agent_id                     int8            NOT NULL,
    approver_status              varchar(10),
    agent_status              	 varchar(10),
    agent_expired_actcode        timestamp,
    cid                          varchar(30)     NOT NULL,
    agent_name                   varchar(100),
    agent_mobile_no              varchar(25),
    agent_firstname              varchar(50),
    agent_middlename             varchar(50),
    agent_lastname               varchar(50),
    agent_email                  varchar(50),
    agent_gender                 varchar(10),
    agent_login                  varchar(10),
    agent_imei                   varchar(25),
    agent_passwd                 varchar(100),
    upload_type                  bytea,
    agent_dob                    date,
    agent_pin                    varchar(6)      NOT NULL,
    agent_broadcast_status       varchar(10),
    agent_latitude               float8,
    agent_longitude              float8,
    agent_expired_date           date,
    agent_enabled                int4,
    agent_reg_date               timestamp,
    agent_apps_version           varchar(10),
    agent_login_status           varchar(10),
    agent_rst_passwd_flag        int2            NOT NULL,
    agent_rst_pin_flag           int2            NOT NULL,
    agent_activation_req_flag    int4,
    agent_login_retry            int2            NOT NULL,
    act_code                     varchar(10),
    inst_code                    varchar(15),
    branch_code                  varchar(15),
    unit_code                    varchar(15),
    center_code                  varchar(15),
    created_date                 timestamp,
    created_by                   int8,
    last_updated_date            timestamp,
    last_updated_by              int8,
    act_id                       int8,
    CONSTRAINT "PK9" PRIMARY KEY (agent_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_branch 
--
DROP TABLE IF EXISTS mfs.m_branch CASCADE;
CREATE TABLE mfs.m_branch(
    branch_code          varchar(15)    NOT NULL,
    branch_desc          varchar(50),
    created_date         timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK5" PRIMARY KEY (branch_code) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_center 
--
DROP TABLE IF EXISTS mfs.m_center CASCADE;
CREATE TABLE mfs.m_center(
    center_code          varchar(15)    NOT NULL,
    center_desc          varchar(50),
    created_date         timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      timestamp,
    CONSTRAINT "PK7" PRIMARY KEY (center_code) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_component 
--
DROP TABLE IF EXISTS mfs.m_component CASCADE;
CREATE TABLE mfs.m_component(
    menu_comp_id       int4            NOT NULL,
    menu_id           int4,
    menu_comp_name    varchar(20),
    menu_comp_desc    varchar(255),
    menu_comp_icon    varchar(50),
    CONSTRAINT "PK11" PRIMARY KEY (menu_comp_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_cust 
--
DROP TABLE IF EXISTS mfs.m_cust CASCADE;
CREATE TABLE mfs.m_cust(
    cust_id                  int8            NOT NULL,
    approver_status          varchar(10),
    cust_status          varchar(10),
    cust_login_retry         int2            NOT NULL,
    cust_expired_actcode     timestamp,
    cid                      varchar(30)     NOT NULL,
    act_code                 varchar(10),
    cust_name                varchar(120),
    cust_mobile_phone        char(10),
    cust_firstname           varchar(10),
    cust_middlename          varchar(50),
    cust_lastname            varchar(50),
    cust_home_phone          char(10),
    cust_gender              varchar(10),
    cust_login               varchar(10),
    cust_passwd              varchar(255),
    cust_pin                 varchar(6),
    cust_expire_date         date,
    cust_enabled             interval,
    cust_reg_date            timestamp,
    upload_type              varchar(10),
    cust_dob                 date,
    cust_apps_version        varchar(10),
    cust_login_status        varchar(10),
    cust_rst_passwd_flag     int2            NOT NULL,
    cust_rst_pin_flag        int2            NOT NULL,
    inst_code                varchar(15),
    cust_broadcast_status    varchar(10),
    branch_code              varchar(15),
    unit_code                varchar(15),
    center_code              varchar(15),
    created_date             timestamp,
    created_by               int8,
    last_updated_date        timestamp,
    last_updated_by          int8,
    act_id                   int8,
    CONSTRAINT "PK16" PRIMARY KEY (cust_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_disclamer 
--
DROP TABLE IF EXISTS mfs.m_disclamer CASCADE;
CREATE TABLE mfs.m_disclamer(
    discl_id       char(10)         NOT NULL,
    discl_name     varchar(50),
    discl_value    varchar(5000),
    discl_type     varchar(10),
    CONSTRAINT "PK26" PRIMARY KEY (discl_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_fee_structure 
--
DROP TABLE IF EXISTS mfs.m_fee_structure CASCADE;
CREATE TABLE mfs.m_fee_structure(
    fee_id               int8         DEFAULT nextval('mfs.seq_m_fee_structure'::regclass) NOT NULL,
    trans_type		 varchar(10) COLLATE "default",
    start_range          int4,
    end_range            int4,
    total_charge         float8,
    agent_income         float8,
    fds_fee              float8,
    cmit_fee             float8,
    bank_income          float8,
    bank_income_flag     boolean,
    telco_fee		 float4,
    created_by          int8,
    created_date         timestamp,
    last_updated_by      int8,
    last_updated_date    timestamp,
    CONSTRAINT "PK24" PRIMARY KEY (fee_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_institution 
--
DROP TABLE IF EXISTS mfs.m_institution CASCADE;
CREATE TABLE mfs.m_institution(
    inst_code            varchar(15)    NOT NULL,
    inst_desc            varchar(50),
    created_date        timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK6" PRIMARY KEY (inst_code) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_lookup 
--
DROP TABLE IF EXISTS mfs.m_lookup CASCADE;
CREATE TABLE mfs.m_lookup(
    lookup_id         int8            NOT NULL,
    lookup_type       varchar(30),
    lookup_value      varchar(50),
    lookup_desc       varchar(100),
    lookup_display    varchar(1),
    CONSTRAINT "PK15" PRIMARY KEY (lookup_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_menu 
--
DROP TABLE IF EXISTS mfs.m_menu CASCADE;
CREATE TABLE mfs.m_menu(
    menu_id           int4            NOT NULL,
    menu_name         varchar(50),
    menu_desc         varchar(50),
    menu_url          varchar(255),
    menu_icon         varchar(50),
    menu_parent_id    int4,
    menu_enabled      int4,
    menu_seq_no       int4,
    CONSTRAINT "PK10" PRIMARY KEY (menu_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_msg_format 
--
DROP TABLE IF EXISTS mfs.m_msg_format CASCADE;
CREATE TABLE mfs.m_msg_format(
    msg_format_id         int8             NOT NULL,
    msg_format_name       varchar(20),
    msg_format_type       varchar(20),
    msg_format_content    varchar(1000),
    CONSTRAINT "PK35" PRIMARY KEY (msg_format_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_param_config 
--
DROP TABLE IF EXISTS mfs.m_param_config CASCADE;
CREATE TABLE mfs.m_param_config(
    param_id             int8            NOT NULL,
    param_name           varchar(50),
    param_value          varchar(50),
    param_desc           varchar(255),
    app_type             varchar(10),
    created_by           int8,
    created_date         timestamp,
    last_updated_by      int8,
    last_updated_date    timestamp,
    CONSTRAINT "PK20" PRIMARY KEY (param_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_role 
--
DROP TABLE IF EXISTS mfs.m_role CASCADE;
CREATE TABLE mfs.m_role(
    role_id              int8            NOT NULL,
    role_name            varchar(120),
    role_desc            varchar(255),
    created_date         timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK12" PRIMARY KEY (role_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_role_dtl 
--
DROP TABLE IF EXISTS mfs.m_role_dtl CASCADE;
CREATE TABLE mfs.m_role_dtl(
    role_dtl_id          int8         NOT NULL,
    role_id              int8         NOT NULL,
    menu_comp_id          int4,
    created_date         timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK13" PRIMARY KEY (role_dtl_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_unit 
--
DROP TABLE IF EXISTS mfs.m_unit CASCADE;
CREATE TABLE mfs.m_unit(
    unit_code            varchar(15)    NOT NULL,
    unit_desc            varchar(50),
    created_date         timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK8" PRIMARY KEY (unit_code) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: m_user 
--
DROP TABLE IF EXISTS mfs.m_user CASCADE;
CREATE TABLE mfs.m_user(
    user_id                int8            NOT NULL,
    user_login             varchar(10),
    user_passwd            varchar(255),
    user_name              varchar(120),
    user_email             varchar(50),
    user_phone             varchar(25),
    user_status            varchar(10),
    user_position          varchar(50),
    user_expired_passwd    date,
    user_type              varchar(10),
    login_attempts 	   int8,
    inst_code 		   character varying(15),
    branch_code 	   character varying(15),
    unit_code 		   character varying(15),
    center_code 	   character varying(15),
    user_parent            int8,
    user_enabled           boolean,
    created_date           timestamp,
    created_by             int8,
    last_updated_date      timestamp,
    last_updated_by        int8,
    CONSTRAINT "PK1" PRIMARY KEY (user_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_broadcast_sms 
--
DROP TABLE IF EXISTS mfs.t_broadcast_sms CASCADE;
CREATE TABLE mfs.t_broadcast_sms(
    msg_id                int8            NOT NULL,
    trans_id              int8,
    msg_desc              varchar(180),
    msg_status            varchar(10),
    msg_command           varchar(10),
    msg_sent_date         timestamp,
    trans_mobile_refno    varchar(50),
    trans_code_refno      varchar(50),
    cid                   varchar(30),
    CONSTRAINT "PK19" PRIMARY KEY (msg_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_cs_ticket 
--
DROP TABLE IF EXISTS mfs.t_cs_ticket CASCADE;
CREATE TABLE mfs.t_cs_ticket(
    ticket_id       int8             NOT NULL,
    trans_type      varchar(25),
    concern_type    varchar(15),
    message         varchar(1000),
    created_by      int8,
    created_date    timestamp,
    CONSTRAINT "PK32" PRIMARY KEY (ticket_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_inbox 
--
DROP TABLE IF EXISTS mfs.t_inbox CASCADE;
CREATE TABLE mfs.t_inbox(
    inbox_id         int8            NOT NULL,
    agent_id         int8,
    inbox_date       timestamp,
    trans_type       varchar(25),
    inbox_desc       varchar(255),
    mobile_ref_id    varchar(18),
    CONSTRAINT "PK31" PRIMARY KEY (inbox_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_job_process 
--
DROP TABLE IF EXISTS mfs.t_job_process CASCADE;
CREATE TABLE mfs.t_job_process(
    job_id             int8            NOT NULL,
    activity_name      varchar(25),
    activity_ref_id    int8,
    activity_status    varchar(10),
    activity_flag      int4,
    activity_date      timestamp,
    activity_desc      varchar(120),
    CONSTRAINT "PK30" PRIMARY KEY (job_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_mapping_hierarchy 
--
DROP TABLE IF EXISTS mfs.t_mapping_hierarchy CASCADE;
CREATE TABLE mfs.t_mapping_hierarchy(
    hierarchy_id         int8           NOT NULL,
    inst_code            varchar(15),
    branch_code          varchar(15),
    unit_code            varchar(15),
    center_code          varchar(15),
    created_by           int8,
    created_date         timestamp,
    last_updated_by      int8,
    last_updated_date    timestamp,
    CONSTRAINT "PK33" PRIMARY KEY (hierarchy_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_mapping_userole 
--
DROP TABLE IF EXISTS mfs.t_mapping_userole CASCADE;
CREATE TABLE mfs.t_mapping_userole(
    userrole_id          int8         NOT NULL,
    user_id              int8         NOT NULL,
    role_id              int8         NOT NULL,
    created_date         timestamp,
    created_by           int8,
    last_updated_date    timestamp,
    last_updated_by      int8,
    CONSTRAINT "PK14" PRIMARY KEY (userrole_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_trans_finance 
--
DROP TABLE IF EXISTS mfs.t_trans_finance CASCADE;
CREATE TABLE mfs.t_trans_finance(
    trans_id                   int8            DEFAULT nextval('mfs.seq_t_trans_finance'::regclass) NOT NULL,
    cust_id                    int8,
    agent_id                   int8,
    trans_date                 timestamp,
    trans_status               varchar(10),
    trans_type                 varchar(20),
    trans_amount_credit        float8,
    trans_amount_debet         float8,
    trans_amount               float8,
    trans_curr_matapat_bal     float8,
    trans_avail_matapat_bal    float8,
    trans_loan_outstd_bal      float8,
    trans_slf_bal              float8,
    trans_slf_req_status       varchar(20),
    trans_post_date            timestamp,
    trans_amount_fee           float8,
    trans_mobile_refno         varchar(50),
    trans_core_refno           varchar(50),
    trans_remark               varchar(255),
    trans_igate_status         varchar(10)     NOT NULL,
    trans_mobile_token         varchar(10),
    agent_cid                  varchar(30),
    cust_cid                   varchar(10),
    log_igate_id               int8,
    created_date               timestamp,
    created_by                 int8,
    last_updated_date          timestamp,
    last_updated_by            int8,
    CONSTRAINT "PK17" PRIMARY KEY (trans_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;


-- 
-- TABLE: t_upload_file 
--
DROP TABLE IF EXISTS mfs.t_upload_file CASCADE;
CREATE TABLE mfs.t_upload_file(
    UPLOAD_ID          	int8 NOT NULL,
    ORIGINAL_FILENAME	varchar(255),
    DISPLAY_FILENAME	varchar(255),
    INVALID_FILENAME	varchar(255),
    STATUS		varchar(50),
    BRANCH_CODE		varchar(15),
    CENTER_CODE		varchar(15),
    UPLOAD_DATE      	timestamp,
    PROCESS_DATE      	timestamp,
    UPLOAD_BY           int8,
    TOTAL_CLIENT        int4,
    VALID_CLIENT	int4,
    INVALID_CLIENT	int4,
    CONSTRAINT "PK_t_upload_file" PRIMARY KEY (upload_id) USING INDEX TABLESPACE cardbank_idx_tblspac 
)
;



-- 
-- TABLE: m_agent 
--
ALTER TABLE mfs.m_agent ADD CONSTRAINT "Refm_activation_code24" 
    FOREIGN KEY (act_id)
    REFERENCES mfs.m_activation_code(act_id)
;

ALTER TABLE mfs.m_agent ADD CONSTRAINT "Refm_branch30" 
    FOREIGN KEY (branch_code)
    REFERENCES mfs.m_branch(branch_code)
;

ALTER TABLE mfs.m_agent ADD CONSTRAINT "Refm_unit31" 
    FOREIGN KEY (unit_code)
    REFERENCES mfs.m_unit(unit_code)
;

ALTER TABLE mfs.m_agent ADD CONSTRAINT "Refm_institution32" 
    FOREIGN KEY (inst_code)
    REFERENCES mfs.m_institution(inst_code)
;

ALTER TABLE mfs.m_agent ADD CONSTRAINT "Refm_center33" 
    FOREIGN KEY (center_code)
    REFERENCES mfs.m_center(center_code)
;


-- 
-- TABLE: m_component 
--
ALTER TABLE mfs.m_component ADD CONSTRAINT "Refm_menu28" 
    FOREIGN KEY (menu_id)
    REFERENCES mfs.m_menu(menu_id)
;


-- 
-- TABLE: m_cust 
--
ALTER TABLE mfs.m_cust ADD CONSTRAINT "Refm_activation_code23" 
    FOREIGN KEY (act_id)
    REFERENCES mfs.m_activation_code(act_id)
;


-- 
-- TABLE: m_role_dtl 
--
ALTER TABLE mfs.m_role_dtl ADD CONSTRAINT "Refm_role27" 
    FOREIGN KEY (role_id)
    REFERENCES mfs.m_role(role_id)
;

ALTER TABLE mfs.m_role_dtl ADD CONSTRAINT "Refm_component34" 
    FOREIGN KEY (menu_comp_id)
    REFERENCES mfs.m_component(menu_comp_id)
;


-- 
-- TABLE: t_mapping_userole 
--
ALTER TABLE mfs.t_mapping_userole ADD CONSTRAINT "Refm_role25" 
    FOREIGN KEY (role_id)
    REFERENCES mfs.m_role(role_id)
;

ALTER TABLE mfs.t_mapping_userole ADD CONSTRAINT "Refm_user26" 
    FOREIGN KEY (user_id)
    REFERENCES mfs.m_user(user_id)
;


-- 
-- TABLE: t_trans_finance 
--
ALTER TABLE mfs.t_trans_finance ADD CONSTRAINT "Refm_agent21" 
    FOREIGN KEY (agent_id)
    REFERENCES mfs.m_agent(agent_id)
;

ALTER TABLE mfs.t_trans_finance ADD CONSTRAINT "Refm_cust22" 
    FOREIGN KEY (cust_id)
    REFERENCES mfs.m_cust(cust_id)
;


