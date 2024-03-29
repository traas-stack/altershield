CREATE TABLE `altershield_meta_platform` (
     `id` varchar(36) NOT NULL COMMENT '主键',
     `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     `platform_name` varchar(64) NOT NULL COMMENT '平台',
     `owner` varchar(512) NOT NULL COMMENT 'owner',
     `description` varchar(4096) DEFAULT NULL COMMENT '描述',
     `token` varchar(32) DEFAULT NULL COMMENT '平台token',
     `scope` varchar(32) DEFAULT NULL COMMENT '所属范围(SaaS、PaaS、laaS)',
     `tenant` varchar(64) DEFAULT NULL COMMENT '租户信息',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_platform_name` (`platform_name`)
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
     `change_effective_config_json_ref` text COMMENT '变更生效配置详情',
     `callback_config_json_ref` text COMMENT '更回调配置',
     `tags_json_ref` text COMMENT '变更场景标签',
     `status` tinyint NOT NULL COMMENT '变更场景状态 0.暂存态 1.发布状态',
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_change_scene_key` (`change_scene_key`),
     KEY `idx_platform` (`platform_name`)
) DEFAULT CHARSET=utf8mb4 COMMENT='变更场景';


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

CREATE TABLE `altershield_exe_change_order` (
        `order_id` varchar(36) NOT NULL COMMENT '主键',
        `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
        `biz_exec_order_id` varchar(128) NOT NULL COMMENT '业务工单id',
        `change_scene_key` varchar(256) NOT NULL COMMENT '场景key',
        `platform` varchar(32) NOT NULL COMMENT '变更平台名',
        `trace_id` varchar(36) NOT NULL COMMENT 'traceId',
        `uid` varchar(2) NOT NULL COMMENT '分区位;提供给node使用',
        `coord` varchar(256) NOT NULL COMMENT '变更坐标',
        `change_title` varchar(256) NOT NULL COMMENT '变更标题',
        `change_url` varchar(512) DEFAULT NULL COMMENT '变更地址',
        `param_ref` text NOT NULL COMMENT '变更参数',
        `change_scenario_code` varchar(24) NOT NULL COMMENT '变更情景码',
        `status` varchar(24) NOT NULL COMMENT '变更单状态',
        `creator` varchar(56) NOT NULL COMMENT '变更执行单创建人',
        `executor` varchar(56) DEFAULT NULL COMMENT '变更执行单执行人',
        `change_phases` varchar(128) NOT NULL COMMENT '变更阶段（环境），多个用逗号隔开',
        `msg` varchar(4048) DEFAULT NULL COMMENT '变更消息',
        `tenant_code` varchar(128) NOT NULL COMMENT '可信租户',
        `from_cloud_id` varchar(64) NOT NULL COMMENT '来源云id',
        `refer_id` varchar(36) DEFAULT NULL COMMENT '来源工单id',
        `from_tenant_code` varchar(128) DEFAULT NULL COMMENT '来源租户',
        `change_apps_ref` text DEFAULT NULL COMMENT '影响的应用',
        `plan_start_time` timestamp NULL DEFAULT NULL COMMENT '变更计划开始时间',
        `start_time` timestamp NULL DEFAULT NULL COMMENT '变更实际执行时间',
        `change_content_ref` text NOT NULL COMMENT '变更内容对象',
        `context_ref` varchar(4096) DEFAULT NULL COMMENT '上下文扩展信息',
        `finish_time` timestamp NULL DEFAULT NULL COMMENT '变更结束时间',
        `dispatch_group` varchar(128) DEFAULT NULL COMMENT '变更单调度组',
        `parent_order_info_ref` text DEFAULT NULL COMMENT '变更父工单信息。json格式',
        `change_target_ref` text DEFAULT NULL COMMENT '变更目标对象',
        `rst_ref`text DEFAULT NULL COMMENT '变更结果json',
        PRIMARY KEY (`order_id`),
        UNIQUE KEY `uk_order_id` (`platform`, `biz_exec_order_id`)   ,
        KEY `idx_change_scene_key` (`change_scene_key`)
) DEFAULT CHARSET = utf8mb4  COMMENT = '变更管控变更执行单';

CREATE TABLE `altershield_exe_counter` (
       `id` varchar(36) NOT NULL,
       `uid` varchar(2) GENERATED ALWAYS AS (substr(`id`,-12,2)) VIRTUAL,
       `count` bigint(20) unsigned NOT NULL,
       `gmt_create` datetime NOT NULL,
       `gmt_modified` datetime NOT NULL,
       PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4;

CREATE TABLE `altershield_exe_node` (
        `node_exe_id` varchar(36) NOT NULL COMMENT '主键',
        `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment 'Create time',
        `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'Last modify time',
        `trace_id` varchar(36) NOT NULL COMMENT 'traceId',
        `order_id` varchar(36) NOT NULL COMMENT '变更单id',
        `coord` varchar(256) NOT NULL COMMENT '变更坐标',
        `status` varchar(16) NOT NULL DEFAULT '' COMMENT ' 状态',
        `change_scene_key` varchar(256) NOT NULL COMMENT '变更场景key标识',
        `change_key` varchar(256) NOT NULL COMMENT '变更key标识',
        `node_type` varchar(256) NOT NULL COMMENT '变更类型',
        `change_phase` varchar(56) DEFAULT NULL COMMENT '变更环境',
        `param_ref` text DEFAULT NULL COMMENT '入参ref',
        `rst_ref` text DEFAULT NULL COMMENT '返回结果ref',
        `last_trigger_id` varchar(64) DEFAULT NULL COMMENT '记录更新的触发源id',
        `msg` text DEFAULT NULL COMMENT '节点执行备注',
        `executor` varchar(256) DEFAULT NULL COMMENT '操作人',
        `biz_ext` varchar(1024) DEFAULT NULL COMMENT '业务扩展字段',
        `search_ext_ref` varchar(4096) DEFAULT NULL COMMENT '搜索扩展字段',
        `check_id_info` varchar(1024) DEFAULT NULL COMMENT '变更前置和后置的AOP的checkId',
        `start_time` datetime DEFAULT NULL COMMENT '变更开始时间',
        `finish_time` datetime DEFAULT NULL COMMENT '变更结束时间',
        `emergency_flag` varchar(1) DEFAULT NULL COMMENT 'YN表示是否应急变更',
        `context_ref` varchar(4096) DEFAULT NULL COMMENT '上下文扩展字段',
        `tenant_code` varchar(64) DEFAULT NULL COMMENT 'TLDC架构租户信息',
        `from_cloud_id` varchar(64) NOT NULL COMMENT '来源云id',
        `refer_Id` varchar(128) DEFAULT NULL COMMENT '来源id',
        `from_tenant_code` varchar(64) DEFAULT NULL COMMENT '来源租户信息',
        `change_content_ref` text DEFAULT NULL COMMENT '变更内容',
        `change_effective_info_ref` text DEFAULT NULL COMMENT '变更生效信息',
        `dispatch_group` varchar(128) DEFAULT NULL COMMENT '调度分组',
        `change_target_ref` text DEFAULT NULL COMMENT '变更目标',
        PRIMARY KEY (`node_exe_id`),
        KEY `idx_change_key` (`change_key`)   ,
        KEY `idx_order_id` (`order_id`)   ,
        KEY `idx_trace_id` (`trace_id`, `coord`)
) DEFAULT CHARSET = utf8mb4 comment = "变更防御执行node表";

CREATE TABLE `altershield_meta_defender_rule` (
      `id` varchar(36) NOT NULL comment 'The primary key',
      `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment 'Create time',
      `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'Last modify time',
      `name` varchar(256) NOT NULL comment 'Rule name',
      `suggestion` varchar(2048) DEFAULT NULL comment 'Handling suggestions',
      `owner` varchar(1024) NOT NULL comment 'Defense rule administrator, multiple commas separated',
      `stage` varchar(4) NOT NULL comment 'Pre or post',
      `status` varchar(16) NOT NULL comment 'Defense rule status-closed/in effect/trial running',
      `exception_strategy` varchar(16) NOT NULL comment 'Rule exception handling strategy - intercept changes/release changes',
      `defense_range_type` varchar(16) NOT NULL comment 'Defense range type',
      `defense_range_key` varchar(256) NOT NULL comment 'Defense range key',
      `external_id` varchar(1024) DEFAULT NULL comment 'Associated downstream platform rule id, optional',
      `tenant` varchar(32) DEFAULT NULL comment 'Tenant to which defense rules belong',
      `delay_second` int(10) unsigned NOT NULL DEFAULT '0' comment 'Delayed execution time, unit: seconds',
      `plugin_key` varchar(256) DEFAULT NULL comment 'Plugin key used by defense rules',
      `main_class` varchar(2048) DEFAULT NULL comment 'Main class of plugin used by defense rules',
      `plugin_invoke_type` varchar(16) DEFAULT NULL comment 'Plugin call type: synchronous, asynchronous',
      `ignore_tag` varchar(32) DEFAULT NULL comment 'Ignore permissions: IGNORE directly ignores, NOT ALLOW prohibits ignoring',
      `max_detect_second` int(10) unsigned DEFAULT NULL comment 'The maximum detect time, when pre check, is the forward detect time. Unit: seconds',
      `arg_ref` text DEFAULT NULL comment 'Defense rule parameter configuration',
      `change_filter_ref` text DEFAULT NULL comment 'Defense rule filter conditions',
      PRIMARY KEY(`id`),
      KEY `idx_plugin_key`(`plugin_key`),
      KEY `idx_defense_range_key`(`defense_range_key`),
      KEY `idx_status`(`status`),
      KEY `idx_range_key_status`(`defense_range_key`, `status`)
) DEFAULT CHARSET = utf8mb4 COMMENT = 'Defender rules table';

CREATE TABLE `altershield_exe_defender_detect` (
       `detect_exe_id` varchar(36) NOT NULL comment 'The primary key',
       `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment 'Create time',
       `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'Last modify time',
       `detect_group_id` varchar(36) NOT NULL comment 'Detection group id',
       `change_order_id` varchar(36) NOT NULL comment 'Related change order id',
       `node_id` varchar(36) DEFAULT NULL comment 'Related change node id',
       `rule_id` varchar(36) NOT NULL comment 'Related defense rule id',
       `rule_name` varchar(256) NOT NULL comment 'Related defense rule name',
       `rule_owner` varchar(1024) NOT NULL comment 'Related defense rule owners',
       `plugin_key` varchar(256) DEFAULT NULL comment 'Defense rule plugin key',
       `main_class` varchar(2048) DEFAULT NULL comment 'Defense plugin main class',
       `external_id` varchar(256) DEFAULT NULL comment 'Downstream platform defense rule id',
       `stage` varchar(4) NOT NULL comment 'Pre or post',
       `status` varchar(16) NOT NULL comment 'Defense detection status',
       `blocked` varchar(8) NOT NULL comment 'This detection whether to block the execution of change',
       `block_msg` varchar(1024) DEFAULT NULL comment 'Reasons for blocking change execution',
       `msg` varchar(1024) DEFAULT NULL comment 'Brief information about the detection results',
       `result_ref` text DEFAULT NULL comment 'Details of this detection result',
       `gmt_plan` timestamp NULL DEFAULT NULL comment 'Planned start time',
       `gmt_start` timestamp NULL DEFAULT NULL comment 'Actual start time',
       `gmt_finish` timestamp NULL DEFAULT NULL comment 'Actual completed time',
       `ignored` varchar(1) DEFAULT NULL comment 'Whether the defense detection result is ignored - Y yes; N no',
       `feedback_status` varchar(16) DEFAULT NULL comment 'User feedback status',
       `feedback_reason` varchar(1024) DEFAULT NULL comment 'User feedback details reasons',
       `feedback_operator` varchar(64) DEFAULT NULL comment 'Feedback person',
       `uid` varchar(2) DEFAULT NULL comment 'Sub-table: 00~99',
       `rule_status` varchar(16) NOT NULL DEFAULT '' comment 'Defense rule status: trial run, online, etc.',
       `allow_ignore` varchar(1) NOT NULL DEFAULT '' comment 'Whether the rule allows ignored',
       PRIMARY KEY(`detect_exe_id`),
       KEY `idx_change_order_id`(`change_order_id`, `uid`) ,
       KEY `idx_node_id`(`node_id`, `uid`),
       KEY `idx_detect_group_id`(`detect_group_id`, `uid`),
       KEY `idx_rule_id`(`rule_id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = 'Defender detection record table';


CREATE TABLE `altershield_key_value` (
     `name` varchar(36) NOT NULL COMMENT '键',
     `serial_num` bigint(20) unsigned NOT NULL COMMENT '编号:从0开始,同一个key的多个编号可以append为一个大string',
     `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
     `value` text DEFAULT NULL COMMENT '值',
     PRIMARY KEY (`name`, `serial_num`)
) DEFAULT CHARSET = utf8mb4  COMMENT = '无分区keyValue表';

CREATE TABLE `altershield_sequence` (
    `name` varchar(64) NOT NULL COMMENT '主键',
    `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `value` bigint(20) unsigned NOT NULL COMMENT '1',
    `min_value` bigint(20) unsigned NOT NULL COMMENT '1',
    `max_value` bigint(20) unsigned NOT NULL COMMENT '2821109907455',
    `step` bigint(20) unsigned NOT NULL COMMENT '1000',
    PRIMARY KEY (`name`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '变更核心sequence表，用于生成各种id';

CREATE TABLE `altershield_exe_scheduler_event` (
   `event_id` varchar(36) NOT NULL COMMENT '主键',
   `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
   `uid` varchar(2) GENERATED ALWAYS AS (substr(`event_id`,-12,2)) VIRTUAL,
   `event_type` varchar(128) NOT NULL COMMENT '任务处理码。用于反序列事件',
   `info` text DEFAULT NULL COMMENT '附加信息',
   `retried` int(11) NOT NULL COMMENT '当前重试次数',
   `status` varchar(16) NOT NULL COMMENT '状态',
   `rst_msg` varchar(4096) DEFAULT NULL COMMENT '执行结果',
   `gmt_plan` timestamp NULL DEFAULT NULL COMMENT '计划执行时间',
   `source_id` varchar(64) NOT NULL COMMENT '对应事件的sourceId',
   `listener_type` varchar(64) DEFAULT NULL COMMENT '对应于处理的listener类型,创建的时候为空',
   `priority` int(10) unsigned NOT NULL COMMENT '任务优先级',
   `event_group` varchar(128) NOT NULL COMMENT '事件分组',
   `gmt_event_start` timestamp NULL DEFAULT NULL COMMENT '事件开始时间，这儿指的是事件源的时间',
   `gmt_execute_start` timestamp NULL DEFAULT NULL COMMENT '实际执行时间',
   `gmt_execute_end` timestamp NULL DEFAULT NULL COMMENT '实际执行时间结束时间',
   `gmt_event_end` timestamp NULL DEFAULT NULL COMMENT '事件结束时间',
   PRIMARY KEY (`event_id`),
   KEY `idx_group_status_plan_code` (`uid`, `event_group`, `status`, `priority`, `gmt_plan`, `event_type`),
   KEY `idx_group_status_plan` (`uid`, `event_group`, `status`, `gmt_plan`) ,
   KEY `idx_plan_group` (`uid`, `gmt_plan`, `event_group`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '变更管控调度事件表';

