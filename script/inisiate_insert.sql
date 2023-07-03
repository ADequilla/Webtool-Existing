--CLEAR DATA
truncate table mfs.t_mapping_userole CASCADE;
truncate table mfs.m_role_dtl CASCADE;
truncate table mfs.m_role CASCADE;
truncate table mfs.m_user CASCADE;
truncate table mfs.m_component CASCADE;
truncate table mfs.m_menu CASCADE;



-- INSERT MASTER MENU -- START --
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (1, 'ADMINISTRATION', 'Administration', '/administration/', 'cogs', null, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (11, 'USER_MANAGEMENT', 'User Management', '/administration/user/', 'users', 1, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (12, 'ROLE_MANAGEMENT', 'Roles Management', '/administration/role/', 'sitemap', 1, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (13, 'HIERARCHY', 'Hierarchy', '/administration/hierarchy/', 'sitemap', 1, 1, 3);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (2, 'REGISTRATION', 'Registration', '/registration', 'registered', null, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (21, 'UPLOAD_CSV', 'Upload CSV & History', '/registration/uploadCsv', 'upload', 2, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (22, 'USER_ACTIVATION', 'User List for Activation', '/registration/userActivation', 'reorder', 2, 1, 2);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (3, 'MONITORING', 'Monitoring', '/monitoring', 'dashboard', null, 1, 3);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (31, 'ACTIVE_AGENTS', 'Active Agents', '/monitoring/activeAgent', 'user-secret', 3, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (32, 'MOBILE_TRANSACTION', 'Mobile Transaction Log', '/monitoring/mobileTransaction', 'mobile', 3, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (33, 'SMS_LOGS', 'SMS Logs', '/monitoring/smsLogs', 'envelope', 3, 1, 3);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (34, 'PENDING_TASK', 'Pending Task', '/monitoring/pendingTask', 'tasks', 3, 1, 4);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (35, 'SLF_REQUEST', 'SLF Request', '/monitoring/slfRequest', 'archive', 3, 1, 5);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (4, 'UTILITIES', 'Utilities', '/utilities', 'cog', null, 1, 4);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (41, 'FEE_STRUCTURE', 'Fee Structure', '/utilities/feeStructure', 'credit-card', 4, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (42, 'PARAMETER_CONFIG', 'Parameter Configuration', '/utilities/paramConfig', 'wrench', 4, 1, 2);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (5, 'CUSTOMER_SERVICE', 'Customer Service', '/customerService', 'phone', null, 1, 5);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (6, 'AUDIT_TRAIL', 'Audit Trail', '/auditTrail', 'desktop', null, 1, 6);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (61, 'ACTIVITIES_USER', 'Activities of Users', '/auditTrail/activitiesUsers', 'laptop', 6, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (62, 'LOGIN_MONITORING', 'Login Monitoring', '/auditTrail/loginMonitoring', 'keyboard-o', 6, 1, 2);
-- INSERT MASTER MENU -- END --


-- SET COMPONENT TO MENU AS ADMIN -- START --
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (1, 1, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (2, 1, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (3, 1, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (4, 1, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (5, 2, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (6, 2, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (7, 2, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (8, 2, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (9, 3, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (10, 3, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (11, 3, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (12, 3, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (13, 4, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (14, 4, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (15, 4, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (16, 4, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (17, 5, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (18, 5, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (19, 5, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (20, 5, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (21, 6, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (22, 6, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (23, 6, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (24, 6, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (25, 11, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (26, 11, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (27, 11, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (28, 11, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (29, 12, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (30, 12, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (31, 12, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (32, 12, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (33, 13, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (34, 13, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (35, 13, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (36, 13, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (37, 21, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (38, 21, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (39, 21, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (40, 21, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (41, 22, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (42, 22, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (43, 22, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (44, 22, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (45, 31, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (46, 31, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (47, 31, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (48, 31, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (49, 32, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (50, 32, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (51, 32, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (52, 32, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (53, 33, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (54, 33, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (55, 33, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (56, 33, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (57, 34, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (58, 34, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (59, 34, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (60, 34, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (61, 35, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (62, 35, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (63, 35, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (64, 35, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (65, 41, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (66, 41, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (67, 41, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (68, 41, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (69, 42, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (70, 42, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (71, 42, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (72, 42, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (73, 61, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (74, 61, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (75, 61, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (76, 61, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (77, 62, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (78, 62, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (79, 62, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (80, 62, 'DELETE', 'DELETE');
-- SET COMPONENT TO MENU AS ADMIN -- END --


-- GENERATE USER AS ADMIN -- START --
INSERT INTO mfs.m_user(user_id, user_login, user_passwd, user_name, user_email, user_type, user_enabled, user_status, user_position)
VALUES (1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'Administrator', 'admin@cardbank.com', 'WEB', TRUE, 'ACTIVE', 'SUPER_ADMIN');
-- GENERATE USER AS ADMIN -- END --