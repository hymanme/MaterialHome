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
     * 获取图书馆藏信息
     *
     * @param userId   用户学号
     * @param password 用户密码
     * @param isbn     图书isbn13
     */
    void loadCollectionInfo(String userId, String password, String isbn, ApiCompleteListener listener);

    /**
     * 预约图书
     * <p>
     * page       1
     *
     * @param userId     学号
     * @param password   密码
     * @param bookId     图书id
     * @param count      可预约图书总数
     * @param take_loca1 馆藏地编号
     * @param callno     索书号
     * @param location   馆藏地编号
     *                   check      1
     */
    void orderBook(String userId, String password, String bookId, String count, String take_loca1, String callno, String location, ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
