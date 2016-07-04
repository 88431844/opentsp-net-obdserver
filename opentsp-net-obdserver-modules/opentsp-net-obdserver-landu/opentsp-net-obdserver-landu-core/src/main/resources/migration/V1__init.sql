CREATE TABLE `t_notice_base` (
  `id` varchar(32) NOT NULL COMMENT '公告ID',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `source_type` int(1) DEFAULT '0' COMMENT '来源类型(0  厂家   1 4S店 )',
  `source_id` varchar(32) DEFAULT NULL COMMENT '来源ID',
  `image_url` varchar(64) DEFAULT NULL COMMENT '图片URL',
  `content` text COMMENT '内容',
  `type` int(1) DEFAULT NULL COMMENT '类别(0 营销  1 知识  2 客服)',
  `status` int(1) DEFAULT '0' COMMENT '状态(0 草稿状态  1 待审核  2 审核中  3 已发布 4 未通过 )',
  `create_time` bigint(16) DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `audit_id` varchar(32) DEFAULT NULL COMMENT '审核人ID',
  `audit_name` varchar(32) DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` bigint(16) DEFAULT NULL COMMENT '审核时间',
  `audit_desc` varchar(512) DEFAULT NULL COMMENT '审核描述',
  `source_name` varchar(128) DEFAULT NULL COMMENT '来源名称',
  `user_name` varchar(32) DEFAULT NULL COMMENT '创建人姓名',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM ;

CREATE TABLE `t_notice_audit_log` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `notice_id` varchar(32) DEFAULT NULL COMMENT '公告ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '审核人',
  `user_name` varchar(16) DEFAULT NULL COMMENT '用户名称',
  `create_time` bigint(16) DEFAULT NULL COMMENT '审核时间',
  `status` int(1) DEFAULT NULL COMMENT '审核结果状态 (  2 审核中  3 已发布 4 未通过 )',
  `content` varchar(128) DEFAULT NULL COMMENT '审核描述',
  `type` int(1) DEFAULT NULL COMMENT '操作类型( 0 创建  1 申请  2 审核  )',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM ;

CREATE TABLE `t_notice_release_rel` (
  `notice_id` varchar(32) NOT NULL COMMENT '公告ID',
  `release_id` varchar(32) DEFAULT NULL COMMENT '发布对象ID',
  `group_name` varchar(64) DEFAULT NULL COMMENT '用户组名称',
  `create_time` bigint(16) DEFAULT NULL COMMENT '发布时间',
  `sex` int(1) DEFAULT NULL COMMENT '性别(0 男   1 女 )',
  `user_type` int(1) DEFAULT NULL COMMENT '用户类型(0 车主用户  1 普通用户)',
  `age` varchar(16) DEFAULT NULL COMMENT '年龄(从年龄，到年龄)',
  `car_age` varchar(16) DEFAULT NULL COMMENT '车龄(从年龄，到年龄)',
  `car_model` varchar(16) DEFAULT NULL COMMENT '车型',
  `last_time_maintain` varchar(32) DEFAULT NULL COMMENT '距上次保养(开始时间，结束时间 )',
  `last_time_insurance` varchar(32) DEFAULT NULL COMMENT '距上次保险(开始时间，结束时间 )',
  `audit_end_time` varchar(32) DEFAULT NULL COMMENT '年审到期(开始时间，结束时间 )',
  `name` varchar(16) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`notice_id`)
) ENGINE=MyISAM ;

CREATE TABLE `t_notice_user_base` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `notice_id` varchar(32) DEFAULT NULL COMMENT '公告ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `create_time` bigint(16) DEFAULT NULL COMMENT '创建时间',
  `count_type` int(1) DEFAULT '0' COMMENT '计数类别( 0  阅读 1 分享)',
  `read_time` int(16) DEFAULT '0' COMMENT '阅读时长(以秒为单位)',
  `read_count` bigint(16) DEFAULT '0' COMMENT '阅读次数',
  `status` int(1) DEFAULT '0' COMMENT '状态( 0 未阅读  1 已阅读)',
  `upload_time` bigint(16) DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM