CREATE TABLE `jobInfo` (
  `id` bigint(24) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
  `app_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名',
  `bean_name` varchar(32) NOT NULL DEFAULT '' COMMENT '类名',
  `method_name` varchar(32) NOT NULL DEFAULT '' COMMENT '方法名',
  `cron_param` varchar(32) NOT NULL DEFAULT '' COMMENT '参数',
  `schedule_addr` varchar(32) NOT NULL DEFAULT '' COMMENT '调度地址，为空表示随机选取',
  `param` varchar(32) NOT NULL DEFAULT '' COMMENT '执行参数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `idx_app_bean_method_name` (`app_name`,`bean_name`,`method_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='任务表';
CREATE TABLE `appInfo` (
  `id` bigint(24) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
  `app_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名',
  `address` varchar(256) NOT NULL DEFAULT '' COMMENT '应用服务器地址',
  `port` INTEGER (32) NOT NULL DEFAULT 0 COMMENT '应用开放端口号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `uniq_app_name` (`app_name`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='应用表';
CREATE TABLE `schedule_history` (
  `id` bigint(24) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
  `job_id` bigint(24) unsigned NOT NULL DEFAULT 0 COMMENT '任务编码',
  `schedule_address` varchar(256) NOT NULL DEFAULT '' COMMENT '调度地址',
  `schedule_param` varchar(256) NOT NULL DEFAULT '' COMMENT '调度参数',
  `execute_time` varchar(256) NOT NULL DEFAULT '' COMMENT '执行时间',
  `execute_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行状态：0-未执行，1-失败，2-成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX `idx_job_id` (`job_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='调度历史';


--说明：
--jobName为应用名+类名+方法名的hash值，由调度系统生成，无需系统指定，仅供调度系统用来判断唯一性
-- * * * * * ?