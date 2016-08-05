package com.hymane.materialhome.bean.http;

import com.hymane.materialhome.bean.http.BaseResponse;

/**
 * Created by hyman on 2016/8/2.
 */
public class UserInfo extends BaseResponse {
    private static String token;

    public UserInfo(int code, String msg) {
        super(code, msg);
    }

    public static String getToken() {
        return token;
    }

}
