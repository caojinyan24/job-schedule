CREATE TABLE `job` (
  `id` bigint(24) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `job_name` varchar(32) NOT NULL DEFAULT '' COMMENT '任务名',
  `application_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名',
  `bean_name` varchar(32) NOT NULL DEFAULT '' COMMENT '类名',
  `method_name` varchar(32) NOT NULL DEFAULT '' COMMENT '方法名',
  `cron_param` varchar(32) NOT NULL DEFAULT '' COMMENT '参数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_idx_job_name` (`job_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='任务表';
CREATE TABLE `application` (
  `id` bigint(24) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `application_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名',
  `address` varchar(256) NOT NULL DEFAULT '' COMMENT '服务器地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_idx_application_name` (`application_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='应用表';
CREATE TABLE `schedule_history` (
  `id` bigint(24) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `job_name` varchar(32) NOT NULL DEFAULT '' COMMENT '任务名',
  `schedule_address` varchar(256) NOT NULL DEFAULT '' COMMENT '调度地址',
  `schedule_param` varchar(256) NOT NULL DEFAULT '' COMMENT '调度参数',
  `execute_time` varchar(256) NOT NULL DEFAULT '' COMMENT '执行时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_idx_application_name` (`application_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='调度历史';