--CLEAR DATA
truncate table mfs.t_mapping_userole CASCADE;
truncate table mfs.m_role_dtl CASCADE;
truncate table mfs.m_role CASCADE;
truncate table mfs.m_user CASCADE;
truncate table mfs.m_user_institution CASCADE;
truncate table mfs.m_user_branch CASCADE;
truncate table mfs.m_user_unit CASCADE;
truncate table mfs.m_user_center CASCADE;
truncate table mfs.m_component CASCADE;
truncate table mfs.m_menu CASCADE;



-- INSERT MASTER MENU -- START --
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (1, 'ADMINISTRATION', 'Administration', null, 'cogs', null, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (11, 'USER_MANAGEMENT', 'User Management', '/administration/user/', 'user', 1, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (12, 'ROLE_MANAGEMENT', 'Roles Management', '/administration/role/', 'users', 1, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (13, 'HIERARCHY', 'Hierarchy', '/administration/hierarchy/', 'sitemap', 1, 1, 3);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (2, 'REGISTRATION', 'Registration', null, 'registered', null, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (21, 'UPLOAD_CSV', 'Upload CSV & History', '/registration/upload/', 'upload', 2, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (22, 'USER_ACTIVATION', 'User List for Activation', '/registration/activation/', 'list-ul', 2, 1, 2);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (3, 'MONITORING', 'Monitoring', null, 'dashboard', null, 1, 3);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (31, 'ACTIVE_USERS', 'Active Users', '/monitoring/user/', 'user-secret', 3, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (32, 'TRANSACTION_CONFIRMATION', 'Transaction Confirmation', '/monitoring/confirmation/', 'mobile', 3, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (33, 'SMS_LOGS', 'SMS Logs', '/monitoring/sms/', 'envelope', 3, 1, 3);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (34, 'PENDING_TASK', 'Pending Task', '/monitoring/task/', 'list-alt', 3, 1, 4);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (35, 'SLF_REQUEST', 'SLF Request', '/monitoring/slf/', 'archive', 3, 1, 5);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (4, 'UTILITIES', 'Utilities', null, 'cog', null, 1, 4);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (41, 'FEE_STRUCTURE', 'Fee Structure', '/utilities/fee/', 'money', 4, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (42, 'PARAMETER_CONFIG', 'Parameter Configuration', '/utilities/config/', 'wrench', 4, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (43, 'ATM_LOCATION', 'ATM Location', '/utilities/atm/', 'map-marker', 4, 1, 3);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (44, 'BANK_NEWS', 'Bank News', '/utilities/news/', 'envelope', 4, 1, 4);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (45, 'SERVICE_DOWNTIME', 'Service Downtime', '/utilities/service/', 'server', 4, 1, 5);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (46, 'INSTITUTION', 'Institution', '/utilities/institution/', 'sitemap', 4, 1, 6);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (47, 'BRANCH', 'Branch', '/utilities/branch/', 'sitemap', 4, 1, 7);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (48, 'UNIT', 'Unit', '/utilities/unit/', 'sitemap', 4, 1, 8);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (49, 'CENTER', 'Center', '/utilities/center/', 'sitemap', 4, 1, 9);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (5, 'CUSTOMER_SERVICE', 'Customer Service', null , 'phone', null, 1, 5);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (51, 'DASHBOARD', 'Dashboard', '/cs/dashboard/', 'phone', 5, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (52, 'BROADCAST_MESSAGE', 'Broadcast Message', '/cs/message/', 'envelope', 5, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (53, 'TYPE_CONCERN', 'Type Of Concern', '/cs/concern/', 'laptop', 5, 1, 3);

INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (6, 'REPORT', 'Report', null, 'clone', null, 1, 6);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (61, 'WEB_USER', 'Web Tool User Listing', '/report/web-user/', 'clone', 6, 1, 1);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (62, 'TRANSACTION', 'Transaction Logs', '/report/transaction/', 'clone', 6, 1, 2);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (63, 'ACTIVITY_HISTORY', 'Activity History', '/report/activity-history/', 'clone', 6, 1, 3);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (64, 'PINPASSWORD_CHANGE', 'MPIN - Password Change', '/report/pinpsswd-change/', 'clone', 6, 1, 4);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (65, 'WEB_SMS_RESEND', 'Resend SMS Activation', '/report/sms-resend/', 'clone', 6, 1, 5);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (66, 'WEB_ACTIVITY', 'User Activity Logs', '/report/web-activity/', 'clone', 6, 1, 6);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (67, 'LOGIN_LOGOUT', 'Login - Logout', '/report/login-logout/', 'clone', 6, 1, 5);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (68, 'JOURNALS', 'Journals', '/report/journals/', 'clone', 6, 1, 8);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (69, 'STATEMENT_ACCOUNT', 'Statement of Account', '/report/statement/', 'clone', 6, 1, 9);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (610, 'TRANSACTION_SUSPICIOUS', 'Suspicious Transaction', '/report/suspicious/', 'clone', 6, 1, 10);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (611, 'WEB_MOBILE_CHANGE', 'Change Mobile No', '/report/mobile-change/', 'clone', 6, 1, 11);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (612, 'RPT_USER_ACTIVATION', 'User Activation', '/report/activation/', 'clone', 6, 1, 12);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (613, 'RPT_ACTIVE_USERS', 'Active Users', '/report/active-user/', 'clone', 6, 1, 13);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (614, 'TRANSACTION_VALID', 'Valid Transaction', '/report/valid/', 'clone', 6, 1, 14);
INSERT INTO mfs.m_menu(menu_id, menu_name, menu_desc, menu_url, menu_icon, menu_parent_id,menu_enabled, menu_seq_no)
VALUES (615, 'CS_DASHBOARD', 'CS Dashboard', '/report/cs/', 'clone', 6, 1, 15);
-- INSERT MASTER MENU -- END --


-- SET COMPONENT TO MENU AS ADMIN -- START --
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (1, 1, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (2, 2, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (3, 3, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (4, 4, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (5, 5, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (6, 6, 'VIEW', 'VIEW');

INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (111, 11, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (112, 11, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (113, 11, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (121, 12, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (122, 12, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (123, 12, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (131, 13, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (132, 13, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (133, 13, 'EDIT', 'EDIT');

INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (211, 21, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (212, 21, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (221, 22, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (222, 22, 'EDIT', 'EDIT');

INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (311, 31, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (312, 31, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (313, 31, 'CHANGE MOBILE', 'CHANGE MOBILE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (314, 31, 'CHANGE TYPE', 'CHANGE TYPE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (315, 31, 'SMS RESEND', 'SMS RESEND');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (316, 31, 'RESET PASSWORD', 'RESET PASSWORD');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (317, 31, 'RESET PIN', 'RESET PIN');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (318, 31, 'DEACTIVATE', 'DEACTIVATE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (319, 31, 'USERNAME', 'USERNAME');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (321, 32, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (322, 32, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (331, 33, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (341, 34, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (342, 34, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (351, 35, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (352, 35, 'EDIT', 'EDIT');

INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (411, 41, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (412, 41, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (413, 41, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (421, 42, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (422, 42, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (423, 42, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (431, 43, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (432, 43, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (433, 43, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (434, 43, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (441, 44, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (442, 44, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (443, 44, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (444, 44, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (451, 45, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (452, 45, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (453, 45, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (454, 45, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (461, 46, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (462, 46, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (463, 46, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (464, 46, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (471, 47, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (472, 47, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (473, 47, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (474, 47, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (481, 48, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (482, 48, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (483, 48, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (484, 48, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (491, 49, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (492, 49, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (493, 49, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (494, 49, 'DELETE', 'DELETE');

INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (511, 51, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (512, 51, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (513, 51, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (521, 52, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (522, 52, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (523, 52, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (524, 52, 'DELETE', 'DELETE');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (531, 53, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (532, 53, 'NEW', 'NEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (533, 53, 'EDIT', 'EDIT');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (534, 53, 'DELETE', 'DELETE');

INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (61, 61, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (62, 62, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (63, 63, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (64, 64, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (65, 65, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (66, 66, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (67, 67, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (68, 68, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (89, 69, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (610, 610, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (611, 611, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (612, 612, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (613, 613, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (614, 614, 'VIEW', 'VIEW');
INSERT INTO mfs.m_component(menu_comp_id, menu_id, menu_comp_name, menu_comp_desc) VALUES (615, 615, 'VIEW', 'VIEW');
-- SET COMPONENT TO MENU AS ADMIN -- END --


-- GENERATE USER AS ADMIN -- START --
INSERT INTO mfs.m_user(user_id, user_login, user_passwd, given_name, middle_name, last_name, user_email, user_type, user_enabled, user_status, user_position, passwd_default)
VALUES (1, 'admins', '5f4dcc3b5aa765d61d8327deb882cf99', 'Administrator', 'M', 'L', 'admin@mfs.com', 'WEB', TRUE, 'ACTIVE', 'SUPER_ADMIN', false);
-- GENERATE USER AS ADMIN -- END --