package com.huobanplus.goodsync.common;

/**
 * Created by liual on 2015-09-07.
 */
public enum ResultCodeEnum {
    SUCCESS(200, "SUCCESS"),
    SYSTEM_BAD_REQUEST(500, "系统请求失败"),
    AUTHORITY_FAILED(300, "授权失败"),
    SYNC_SAME_ACCOUNT(301, "无法同步相同账户");
    private int resultCode;
    private String msg;

    ResultCodeEnum(int resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
