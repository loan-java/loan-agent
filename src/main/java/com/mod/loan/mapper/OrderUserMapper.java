package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.OrderUser;
import com.mod.loan.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface OrderUserMapper extends MyBaseMapper<OrderUser> {

    OrderUser selectByOrderNoAndSource(@Param("orderNo") String orderNo, @Param("source") Integer source);

}