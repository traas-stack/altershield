CREATE TABLE `altershield_meta_platform` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `platform_name` varchar(64) NOT NULL COMMENT '平台',
  `config_json_ref` text DEFAULT NULL COMMENT '配置信息,以json存储',
  `owner` varchar(512) NOT NULL COMMENT 'owner',
  `description` varchar(4096) DEFAULT NULL COMMENT '描述',
  `token` varchar(32) DEFAULT NULL COMMENT '平台token',
  `scope` varchar(32) DEFAULT NULL COMMENT '所属范围(SaaS、PaaS、laaS)',
  `tenant` varchar(64) DEFAULT NULL COMMENT '租户信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_platform` (`platform_name`) ,
  UNIQUE KEY `uk_name` (`platform_name`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '平台元数据';


CREATE TABLE `altershield_meta_change_scene` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `tenant_code` varchar(64) DEFAULT NULL COMMENT '租户',
  `server_tenant_code` varchar(4096) DEFAULT NULL COMMENT '变更场景服务的租户 多个用逗号隔开',
  `name` varchar(256) NOT NULL COMMENT '变更场景名字',
  `change_scene_key` varchar(256) NOT NULL COMMENT '场景key,一个场景的唯一标识',
  `owner` varchar(512) DEFAULT NULL COMMENT '场景负责人，多个用逗号隔开',
  `generation` varchar(12) DEFAULT NULL COMMENT '场景代G；G0,G1,G2,G3,G4',
  `risk_info` varchar(128) DEFAULT NULL COMMENT '风险等级',
  `platform_name` varchar(128) NOT NULL COMMENT '平台名字，altershield_meta_platform',
  `scope` varchar(128) DEFAULT NULL COMMENT '变更场景范围。SaaS,PaaS,IaaS',
  `description` varchar(4096) DEFAULT NULL COMMENT '变更场景描述',
  `effective_target_type` varchar(4096) DEFAULT NULL COMMENT '变更生效载体类型;多个用逗号隔开',
  `change_content_type` varchar(56) DEFAULT NULL COMMENT '变更内容类型',
  `change_target_type` varchar(56) NOT NULL COMMENT '变更对象类型',
  `change_effective_config_json_ref` text DEFAULT NULL COMMENT '变更生效配置详情',
  `callback_config_json_ref` text DEFAULT NULL COMMENT '更回调配置',
  `tags_json_ref` text DEFAULT NULL COMMENT '变更场景标签',
  `status` tinyint(4) NOT NULL COMMENT '变更场景状态 0.暂存态 1.发布状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_change_scene_key` (`change_scene_key`) ,
  KEY `idx_platform` (`platform_name`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '变更场景';

CREATE TABLE `altershield_meta_change_step` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `name` varchar(256) NOT NULL COMMENT '变更步骤名称',
  `change_scene_key` varchar(256) NOT NULL COMMENT '变更场景key',
  `change_key` varchar(280) NOT NULL COMMENT '变更key',
  `step_type` varchar(56) NOT NULL COMMENT '变更步骤类型。change_batch,change_action,change_action_prefix,change_action_suffix',
  `defence_config_json_ref` text DEFAULT NULL COMMENT '防御配置信息',
  `change_content_type` varchar(128) DEFAULT NULL COMMENT '变更内容类型',
  `effective_target_type` varchar(4096) DEFAULT NULL COMMENT '生效载体类型',
  `change_target_type` varchar(128) DEFAULT NULL COMMENT '变更对象类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_key` (`change_key`),
  KEY `idx_change_scene_key` (`change_scene_key`, `step_type`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '变更步骤';

CREATE TABLE `altershield_meta_change_type` (
  `id` varchar(36) NOT NULL COMMENT '主鍵',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `type` varchar(128) NOT NULL COMMENT '变更内容类型。唯一标识',
  `name` varchar(128) NOT NULL COMMENT '变更内容类型名字',
  `type_desc` varchar(1024) NOT NULL COMMENT '详情描述',
  `category` varchar(20) NOT NULL COMMENT '分类',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type` (`category`, `type`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '变更类型';

CREATE TABLE `altershield_config` (
  `name` varchar(256) NOT NULL COMMENT '名称',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `value` varchar(10240) DEFAULT NULL COMMENT '值',
  `cfg_desc` varchar(4096) NOT NULL COMMENT '描述',
  PRIMARY KEY (`name`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '后台配置表';