

-- Sequence: mfs.seq_t_inbox
DROP SEQUENCE mfs.seq_t_inbox;
CREATE SEQUENCE mfs.seq_t_inbox
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999999999999
  START 1
  CACHE 1;
ALTER TABLE mfs.seq_t_inbox
  OWNER TO postgres;

  

  
-- 
-- TABLE: mfs.t_inbox
--
DROP TABLE IF EXISTS mfs.t_inbox CASCADE;
CREATE TABLE mfs.t_inbox
(
  inbox_id bigint NOT NULL,
  agent_id bigint,
  inbox_date timestamp(6) without time zone,
  trans_type character varying(25),
  inbox_desc character varying(5000),
  inbox_subject character varying(200),
  mobile_ref_id character varying(50)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mfs.t_inbox
  OWNER TO postgres;
