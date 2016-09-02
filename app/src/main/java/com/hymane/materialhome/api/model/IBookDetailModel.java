package com.hymane.materialhome.api.model;

import com.hymane.materialhome.api.ApiCompleteListener;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description:
 */
public interface IBookDetailModel {
    /**
     * 获取图书评论
     */
    void loadReviewsList(String bookId, int start, int count, String fields, ApiCompleteListener listener);

    /**
     * 获取推荐丛书
     */
    void loadSeriesList(String SeriesId, int start, int count, String fields, ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
