package com.mod.loan.service.biz;

import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.MerchantMapper;
import com.mod.loan.model.Merchant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ author liujianjian
 * @ date 2019/6/23 21:51
 */
@Service
public class BizMerchantService extends BaseServiceImpl<Merchant, Long> {
    @Resource
    private MerchantMapper merchantMapper;

}
