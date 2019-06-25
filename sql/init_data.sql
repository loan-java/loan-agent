
INSERT INTO `tb_merchant`(`merchant_no`, `merchant_name`, `weight`, `host`, `status`) VALUES ('10000', '及时贷', 50, 'http://localhost:10002/rongze/dispatcherRequest', 1);
INSERT INTO  `tb_merchant`(`merchant_no`, `merchant_name`, `weight`, `host`, `status`) VALUES ('10001', '华时贷', 50, 'http://localhost:10002/rongze/dispatcherRequest', 1);


-- 插入用户id跟md5值
select concat('insert into tb_user(id, mobile_id_md5) values(', t.id, ',', '"', t.md5,'"', ');') from (select id, UPPER(md5(CONCAT(user_phone,UPPER(user_cert_no)))) as md5 from tb_user) t;

-- 插入order_no跟用户id关联
select concat('insert into tb_order_user(order_no, user_id) values(', '"', t.order_no, '"', ',', t.uid, ');') from tb_user_order t;

-- 插入用户跟商户关联
select concat('insert into tb_user_merchant(merchant_no, uid) values(', '"10000"', ',', t.uid, ');') from tb_user_order t;
