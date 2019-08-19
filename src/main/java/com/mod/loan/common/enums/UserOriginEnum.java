package com.mod.loan.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 用户注册渠道
 *
 * @author kk
 */
public enum UserOriginEnum {

    RZ("1", "融泽"),
    BB("2", "嘣嘣");

    private String code;
    private String msg;

    UserOriginEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCodeInt() {
        return StringUtils.isNumeric(code) ? Integer.parseInt(code) : -1;
    }
}
