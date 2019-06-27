
INSERT INTO `tb_merchant`(`merchant_no`, `merchant_name`, `weight`, `host`, `status`) VALUES ('10000', 'huashidai', 50, 'http://hsdrzthird.mrcy888.com/rongze/dispatcherRequest', 1);
INSERT INTO  `tb_merchant`(`merchant_no`, `merchant_name`, `weight`, `host`, `status`) VALUES ('10001', 'xiaohuqianbao', 50, 'http://xhqbrzthird.shengyhwl.com/rongze/dispatcherRequest', 1);


-- 插入用户id跟md5值
select concat('insert into tb_user(id, user_name,mobile_no,source, mobile_id_md5,create_time,update_time) values(', t.id, ', "', t.user_name, '",', '"', t.user_phone, '",', '', 1, ',', '"', t.md5, '",', '"', t.create_time, '",', '"', t.update_time,'"', ');') from (select id, user_name, user_phone,(md5(CONCAT(user_phone,UPPER(user_cert_no)))) as md5,now() as create_time,now() as update_time from tb_user) t;

-- 插入order_no跟用户id关联
select concat('insert into tb_order_user(order_no, user_id,source,create_time) values(', '"', t.order_no, '"', ',', t.uid, '', ',', 1 , '', ',"',now() ,'");') from tb_user_order t;


-- 插入用户跟商户关联
select concat('insert into tb_user_merchant(merchant_no, uid) values(', '"10000"', ',', t.id, ');') from tb_user t;
