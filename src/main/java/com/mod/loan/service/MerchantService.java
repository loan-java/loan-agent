package com.mod.loan.service;

/**
 * @ author liujianjian
 * @ date 2019/6/23 15:59
 */
public interface MerchantService {

    void initUser(String orderNo, String userName, String md5, int source);

    String distribute(long uid, String requestParamStr, int source, String method) throws Exception;
}
