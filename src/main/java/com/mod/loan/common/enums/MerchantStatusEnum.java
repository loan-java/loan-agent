package com.mod.loan.common.enums;


/**
 * 商户状态，1-正常，2-停用
 */
public enum MerchantStatusEnum {

    NORMAL(1, "正常"),
    PAUSE(2, "停用");

    private Integer code;
    private String msg;

    MerchantStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isPause(int status) {
        return PAUSE.getCode() == status;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
