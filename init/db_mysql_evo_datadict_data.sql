insert into sys_data_dict values (null, '0', '删除标志', 'DELETE_FLAG', 'DEFL0000', '正常', '1', '1', now(), '1', now());
insert into sys_data_dict values (null, '0', '删除标志', 'DELETE_FLAG', 'DEFL0001', '删除', '2', '1', now(), '1', now());

insert into sys_data_dict values (null, '0', '是否', 'YES_NO', 'YN000000', '否', '2', '1', now(), '1', now());
insert into sys_data_dict values (null, '0', '是否', 'YES_NO', 'YN000001', '是', '1', '1', now(), '1', now());

insert into sys_data_dict values (null, '0', 'Docker Swarm Node Role', 'DOCKER_SWARM_NODE_ROLE', 'worker', 'worker', '1', '1', now(), '1', now());
insert into sys_data_dict values (null, '0', 'Docker Swarm Node Role', 'DOCKER_SWARM_NODE_ROLE', 'manager', 'manager', '2', '1', now(), '1', now());

insert into sys_data_dict values (null, '0', 'Docker Swarm Node Availability', 'DOCKER_SWARM_NODE_AVAILABILITY', 'active', 'active', '1', '1', now(), '1', now());
insert into sys_data_dict values (null, '0', 'Docker Swarm Node Availability', 'DOCKER_SWARM_NODE_AVAILABILITY', 'pause', 'pause', '2', '1', now(), '1', now());
insert into sys_data_dict values (null, '0', 'Docker Swarm Node Availability', 'DOCKER_SWARM_NODE_AVAILABILITY', 'drain', 'drain', '3', '1', now(), '1', now());
