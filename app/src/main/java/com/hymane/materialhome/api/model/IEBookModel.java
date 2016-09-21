package com.hymane.materialhome.api.model;

import com.hymane.materialhome.api.ApiCompleteListener;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public interface IEBookModel {
    /***
     * 获取最热排行
     * 获取留存排行
     * 获取完结排行
     * 获取潜力榜排行
     */
    void getRanking(String rankingId, ApiCompleteListener listener);

    /**
     * 获取电子书分类
     */
    void getCategoryList(ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
