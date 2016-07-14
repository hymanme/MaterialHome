package com.hymane.materialhome.api;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/1/10
 * Description: 网络请求回调接口
 */
public interface ApiCompleteListener {
    void onComplected(Object result);

    void onFailed(String msg);
}
