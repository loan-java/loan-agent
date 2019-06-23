package com.mod.loan.mapper;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.User;
import com.mod.loan.model.UserMerchant;


public interface UserMerchantMapper extends MyBaseMapper<UserMerchant> {

    UserMerchant selectByUid(long userId);
}