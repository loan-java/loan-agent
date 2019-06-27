package com.mod.loan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.enums.UserOriginEnum;
import com.mod.loan.common.exception.BizException;

import com.mod.loan.model.OrderUser;
import com.mod.loan.service.MerchantService;
import com.mod.loan.service.biz.BizOrderUserService;
import com.mod.loan.service.biz.BizUserService;
import com.mod.loan.util.rongze.BizDataUtil;
import com.mod.loan.util.rongze.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ author liujianjian
 * @ date 2019/5/15 18:01
 */
@Slf4j
@RestController
@RequestMapping("/rongze")
public class RongZeRequestController {

    @Resource
    private MerchantService merchantService;
    @Resource
    private BizOrderUserService bizOrderUserService;
    @Resource
    private BizUserService bizUserService;

    private static final String logPre = "agent-融泽入口请求, ";

    @RequestMapping("/dispatcherRequest")
    public Object dispatcherRequest(@RequestBody JSONObject param) {

        long s = System.currentTimeMillis();

        Object result = null;
        String method = param.getString("method");
        log.info(logPre + "开始, method: " + method);

        try {

            if (StringUtils.isBlank(method)) throw new BizException(ResponseEnum.M5000);

            String sign = param.getString("sign");
            boolean check = SignUtil.checkSign(param.toJSONString(), sign);
            if (!check) throw new BizException(ResponseEnum.M4006);

            String oriParamStr = param.toJSONString();

            //解密 bizData
            if ("1".equals(param.getString("biz_enc"))) {
                String bizDataStr = param.getString("biz_data");
                String bizData = BizDataUtil.decryptBizData(bizDataStr, param.getString("des_key"));
                param.put("biz_data", bizData);
//                log.warn(logPre + "method: " + method + ", 解密后的数据：" + param.toJSONString());
            }

            JSONObject bizData = JSONObject.parseObject(param.getString("biz_data"));

            //设置随机数
//            String n="";
//            if("dev".equals(Constant.ENVIROMENT)){
//                n=String.valueOf((1+Math.random()*(1000-1+1)));
//            }

            String orderNo = bizData.getString("order_no"); //默认的orderNo获取方式

            if ("fund.cert.auth".equals(method)) {
                //调的第一个接口
                String md5 = bizData.getString("md5");
                String userName = bizData.getString("user_name");
                merchantService.initUser(orderNo, userName, md5, UserOriginEnum.RZ.getCodeInt());
            }

            Long uid = null;
            if (StringUtils.isNotBlank(orderNo)) {
                OrderUser ou = bizOrderUserService.queryRongZeOU(orderNo);
                if (ou == null) throw new BizException("会话已失效，请从主页重新进入");
                uid = ou.getUserId();
            }

            if ("fund.userinfo.base".equals(method)) {
                //用户基本信息
                JSONObject orderInfo = bizData.getJSONObject("orderInfo");//订单基本信息
                String userMobile = orderInfo.getString("user_mobile");
                orderNo = orderInfo.getString("order_no");
                if (uid == null) {
                    OrderUser ou = bizOrderUserService.queryRongZeOU(orderNo);
                    if (ou == null) throw new BizException("会话已失效，请从主页重新进入");
                    uid = ou.getUserId();
                }
                bizUserService.updateUserMobile(uid, userMobile);
            }

            if (uid == null) throw new BizException("根据orderNo未获取到用户id");

            result = merchantService.distribute(uid, oriParamStr, UserOriginEnum.RZ.getCodeInt(), method);

        } catch (Exception e) {
            logFail(e, "【" + method + "】方法出错：" + param.toJSONString());
            result = e instanceof BizException ? ResponseBean.fail(((BizException) e)) : ResponseBean.fail(e.getMessage());
        }
        log.info(logPre + "结束, result: " + JSON.toJSONString(result) + ", method: " + method + ", costTime: " + (System.currentTimeMillis() - s) + " ms");
        return result;
    }

    private void logFail(Exception e, String info) {
        if (e instanceof BizException)
            log.info(getPreLog() + e.getMessage() + ",相关数据：" + info);
        else
            log.error("融泽入口请求系统异常, " + getPreLog() + e.getMessage() + ",相关数据：" + info, e);
    }

    private String getPreLog() {
        return "";
    }


    static class ResponseBean<T> {

        private int code;
        private String msg;
        private T data;

        public ResponseBean() {
        }

        public ResponseBean(int code, String msg, T data) {
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public static <T> ResponseBean<T> success() {
            return new ResponseBean<>(200, "成功", null);
        }

        public static <T> ResponseBean<T> success(T data) {
            return new ResponseBean<>(200, "成功", data);
        }


        public static <T> ResponseBean<T> fail(String msg) {
            return new ResponseBean<>(ResponseEnum.M4000.getCodeInt(), msg, null);
        }

        public static <T> ResponseBean<T> fail(int code, String msg) {
            return new ResponseBean<>(code, msg, null);
        }

        public static <T> ResponseBean<T> fail(BizException e) {
            return new ResponseBean<>(e.getCodeInt(), e.getMessage(), null);
        }

        public static <T> ResponseBean<T> fail(ResponseEnum r) {
            return new ResponseBean<>(r.getCodeInt(), r.getMessage(), null);
        }

        public static <T> ResponseBean<T> fail(int code, String msg, T data) {
            return new ResponseBean<>(code, msg, data);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}

