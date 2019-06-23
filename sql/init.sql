CREATE TABLE `tb_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户姓名',
  `mobile_no` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `source` int(11) DEFAULT '1' COMMENT '用户来源，1-融泽',
  `mobile_id_md5` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号身份证号md5值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mobile_s` (`mobile_no`,`source`),
  UNIQUE KEY `uk_md5` (`mobile_id_md5`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `tb_merchant` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `merchant_no` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '商户号，10000-及时贷',
  `merchant_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `weight` int(11) DEFAULT NULL COMMENT '商户权重配置',
  `host` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商户域名',
  `status` int(11) DEFAULT '1' COMMENT '商户状态，1-正常，2-停用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_no` (`merchant_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商户信息';

CREATE TABLE `tb_order_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '流量端订单号',
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `source` int(11) DEFAULT NULL COMMENT '来源，1-融泽',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_oid_s` (`order_no`,`source`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='流量端订单号跟用户关系';

CREATE TABLE `tb_user_merchant` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `merchant_no` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商户号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uid_merchant` (`uid`,`merchant_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户跟商户关系';