package com.mod.loan.service;

/**
 * @ author liujianjian
 * @ date 2019/6/23 15:59
 */
public interface MerchantService {

    long initUser(String orderNo, String userName, String mobileNo, String md5, int source);

    String distribute(long uid, String requestParamStr, int source, String method) throws Exception;
}
