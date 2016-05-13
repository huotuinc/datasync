package com.huobanplus.goodsync.common;

import lombok.Data;

/**
 * Created by liual on 2015-09-07.
 */
@Data
public class ApiResult {
    private int resultCode;
    private String desc;

    public static ApiResult resultWith(int resultCode, String desc) {
        ApiResult apiResult = new ApiResult();
        apiResult.setResultCode(resultCode);
        apiResult.setDesc(desc);
        return apiResult;
    }
    public static ApiResult resultWith(int resultCode) {
        ApiResult apiResult = new ApiResult();
        apiResult.setResultCode(resultCode);
        return apiResult;
    }
}
