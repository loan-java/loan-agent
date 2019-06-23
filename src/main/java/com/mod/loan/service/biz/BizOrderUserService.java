package com.mod.loan.service.biz;

import com.mod.loan.common.enums.UserOriginEnum;
import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.OrderUserMapper;
import com.mod.loan.model.OrderUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ author liujianjian
 * @ date 2019/6/23 21:51
 */
@Service
public class BizOrderUserService extends BaseServiceImpl<OrderUser, Long> {
    @Resource
    private OrderUserMapper orderUserMapper;

    public OrderUser queryRongZeOU(String orderNo) {
        return queryByOrderNoAndSource(orderNo, UserOriginEnum.RZ.getCodeInt());
    }

    public OrderUser queryByOrderNoAndSource(String orderNo, int source) {
        return orderUserMapper.selectByOrderNoAndSource(orderNo, source);
    }
}
