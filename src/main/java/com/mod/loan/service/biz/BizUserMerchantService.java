package com.mod.loan.service.biz;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.UserMerchantMapper;
import com.mod.loan.model.UserMerchant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ author liujianjian
 * @ date 2019/6/23 21:51
 */
@Service
public class BizUserMerchantService extends BaseServiceImpl<UserMerchant, Long> {
    @Resource
    private UserMerchantMapper userMerchantMapper;

    public UserMerchant queryByUid(long uid) {
        return userMerchantMapper.selectByUid(uid);
    }
}
