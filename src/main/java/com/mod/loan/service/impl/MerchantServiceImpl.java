package com.mod.loan.service.impl;

import com.mod.loan.common.enums.MerchantStatusEnum;
import com.mod.loan.common.exception.BizException;

import com.mod.loan.model.Merchant;
import com.mod.loan.model.OrderUser;
import com.mod.loan.model.User;
import com.mod.loan.model.UserMerchant;
import com.mod.loan.service.MerchantService;
import com.mod.loan.service.biz.BizMerchantService;
import com.mod.loan.service.biz.BizOrderUserService;
import com.mod.loan.service.biz.BizUserMerchantService;
import com.mod.loan.service.biz.BizUserService;
import com.mod.loan.util.WeightUtil;
import com.mod.loan.util.rongze.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ author liujianjian
 * @ date 2019/6/23 16:01
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private BizUserService bizUserService;
    @Resource
    private BizUserMerchantService bizUserMerchantService;
    @Resource
    private BizOrderUserService bizOrderUserService;
    @Resource
    private BizMerchantService bizMerchantService;

    //初始化用户信息
    @Transactional(rollbackFor = Throwable.class)
    public void initUser(String orderNo, String userName, String md5, int source) {
        User user = bizUserService.queryByMd5(md5);
        if (user == null) {
            user = new User();
            user.setUserName(userName);
            user.setMobileIdMd5(md5);
            user.setSource(source);
            bizUserService.insertGetId(user);
        }
        OrderUser ou = bizOrderUserService.queryByOrderNoAndSource(orderNo, source);
        if (ou == null) {
            OrderUser orderUser = new OrderUser();
            orderUser.setCreateTime(new Date());
            orderUser.setOrderNo(orderNo);
            orderUser.setSource(source);
            orderUser.setUserId(user.getId());
            bizOrderUserService.insertSelective(orderUser);
        } else if (ou.getUserId().intValue() != user.getId().intValue()) {
            ou.setUserId(user.getId());
            bizOrderUserService.updateByPrimaryKeySelective(ou);
        }
    }

    //分发请求
    @Override
    public String distribute(long uid, String requestParamStr, int source) throws Exception {

//        Long uid = orderUserMapper.selectUidByOrderNoAndSource(orderNo, source);

        UserMerchant um = bizUserMerchantService.queryByUid(uid);
        if (um != null) {
            //如果已有商户则直接转发
            Merchant merchant = queryByMerchantNo(um.getMerchantNo());
            if (merchant != null) {
                return doPost(uid, merchant, true, requestParamStr);
            }
        }
        //如果用户还未分配商户，则按商户权重安排当前用户进哪个商户域名
        Merchant merchant = hitMerchant();
        if (merchant != null) {
            um = new UserMerchant();
            um.setUid(uid);
            um.setMerchantNo(merchant.getMerchantNo());
            bizUserMerchantService.insertSelective(um);
            return doPost(uid, merchant, false, requestParamStr);
        }

        throw new BizException("未获取到有效商户");
    }

    private Merchant hitMerchant() {

        List<Merchant> list = bizMerchantService.queryNormalAll();
        if (CollectionUtils.isEmpty(list)) return null;

        Map<String, Integer> map = new HashMap<>();
        for (Merchant m : list) {
            map.put(m.getMerchantNo(), m.getWeight());
        }

        String hitNo = WeightUtil.random(map);
        return queryByMerchantNo(hitNo);
    }

    private String doPost(long uid, Merchant merchant, boolean old, String requestParamStr) throws Exception {
        log.info("分发请求, 用户id: " + uid + ", 新用户: " + (!old) + ", 发往商户号: " + merchant.getMerchantNo() + ", 权重: " + merchant.getWeight() + ", 转向 url: " + merchant.getHost());
        return HttpClientUtils.sendPost(merchant.getHost(), requestParamStr.getBytes());
    }

    private Merchant queryByMerchantNo(String merchantNo) {
        if (StringUtils.isBlank(merchantNo)) return null;
        Merchant m = new Merchant();
        m.setMerchantNo(merchantNo);
        Merchant merchant = bizMerchantService.selectOne(m);
        return MerchantStatusEnum.isPause(merchant.getStatus()) ? null : merchant;
    }
}
