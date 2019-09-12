insert into sys_micro values ('1', '数据字典服务', 'evo-datadict', '/datadict', '1', now(), '1', now());
insert into sys_micro values ('2', '基础服务', 'evo-sys', '/sys', '1', now(), '1', now());
insert into sys_micro values ('3', '示例服务', 'evo-sample', '/sample', '1', now(), '1', now());
insert into sys_micro values ('4', '管控服务', 'evo-controller', '/controller', '1', now(), '1', now());

insert into sys_func values ('1', '1', null, null, 'GET', '/**/*', '1', now(), '1', now());
insert into sys_func values ('2', '1', null, null, 'POST', '/**/*', '1', now(), '1', now());
insert into sys_func values ('3', '1', null, null, 'PUT', '/**/*', '1', now(), '1', now());
insert into sys_func values ('4', '1', null, null, 'DELETE', '/**/*', '1', now(), '1', now());
insert into sys_func values ('5', '2', null, null, 'GET', '/**/*', '1', now(), '1', now());
insert into sys_func values ('6', '2', null, null, 'POST', '/**/*', '1', now(), '1', now());
insert into sys_func values ('7', '2', null, null, 'PUT', '/**/*', '1', now(), '1', now());
insert into sys_func values ('8', '2', null, null, 'DELETE', '/**/*', '1', now(), '1', now());
insert into sys_func values ('9', '3', null, null, 'GET', '/**/*', '1', now(), '1', now());
insert into sys_func values ('10', '3', null, null, 'POST', '/**/*', '1', now(), '1', now());
insert into sys_func values ('11', '3', null, null, 'PUT', '/**/*', '1', now(), '1', now());
insert into sys_func values ('12', '3', null, null, 'DELETE', '/**/*', '1', now(), '1', now());
insert into sys_func values ('13', '4', null, null, 'GET', '/**/*', '1', now(), '1', now());
insert into sys_func values ('14', '4', null, null, 'POST', '/**/*', '1', now(), '1', now());
insert into sys_func values ('15', '4', null, null, 'PUT', '/**/*', '1', now(), '1', now());
insert into sys_func values ('16', '4', null, null, 'DELETE', '/**/*', '1', now(), '1', now());

insert into sys_role values ('1', '管理员', 'EVO_ADMIN', '1', now(), '1', now());

insert into sys_role_func values (1, 1);
insert into sys_role_func values (1, 2);
insert into sys_role_func values (1, 3);
insert into sys_role_func values (1, 4);
insert into sys_role_func values (1, 5);
insert into sys_role_func values (1, 6);
insert into sys_role_func values (1, 7);
insert into sys_role_func values (1, 8);
insert into sys_role_func values (1, 9);
insert into sys_role_func values (1, 10);
insert into sys_role_func values (1, 11);
insert into sys_role_func values (1, 12);
insert into sys_role_func values (1, 13);
insert into sys_role_func values (1, 14);
insert into sys_role_func values (1, 15);
insert into sys_role_func values (1, 16);

insert into sys_user values ('1', 'admin', '$2a$08$pdWJYgAsN6/3GT/I5zeSbeKChDXrNH/DrM5QkB1nSopsS8K0F1xIS', '管理员', 'EVO_ADMIN', 'admin@evo.com', '18643100000', 'YN000000', null, null, null, null, '1', now(), '1', now());

insert into sys_user_role values (1, 1);
