-- Sequence: mfs.seq_m_type_concern
DROP SEQUENCE mfs.seq_m_type_concern;
CREATE SEQUENCE mfs.seq_m_type_concern
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999999999999
  START 13
  CACHE 1;
ALTER TABLE mfs.seq_m_type_concern
  OWNER TO postgres;
  
-- 
-- TABLE:  mfs.m_type_concern
--
DROP TABLE IF EXISTS mfs.m_type_concern CASCADE;
CREATE TABLE mfs.m_type_concern
(
  concern_code bigint NOT NULL,
  concern_name character varying(50),
  concern_desc character varying(50),
  concern_level character varying(50),
  concern_time character varying(5),
  created_date timestamp(6) without time zone,
  created_by bigint,
  last_updated_date timestamp(6) without time zone,
  last_updated_by bigint
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mfs.m_type_concern
  OWNER TO postgres;