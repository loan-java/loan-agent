package com.mod.loan.service.biz;

import com.mod.loan.common.enums.MerchantStatusEnum;
import com.mod.loan.common.mapper.BaseServiceImpl;
import com.mod.loan.mapper.MerchantMapper;
import com.mod.loan.model.Merchant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ author liujianjian
 * @ date 2019/6/23 21:51
 */
@Service
public class BizMerchantService extends BaseServiceImpl<Merchant, Long> {
    @Resource
    private MerchantMapper merchantMapper;

    public List<Merchant> queryNormalAll() {
        List<Merchant> list = super.selectAll();
//        Iterator<Merchant> it = list.iterator();
//        while (it.hasNext()) {
//            Merchant m = it.next();
//            if (MerchantStatusEnum.isPause(m.getStatus())) it.remove();
//        }
//
        list.removeIf(m -> {
            return MerchantStatusEnum.isPause(m.getStatus());
        });

        return list;
    }
}
