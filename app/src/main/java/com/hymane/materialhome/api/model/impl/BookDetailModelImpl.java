package com.hymane.materialhome.api.model.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.model.IBookDetailModel;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/2/23 0023
 * Description: 图书详情页数据模型
 */
public class BookDetailModelImpl implements IBookDetailModel {

    @Override
    public void loadReviewsList(String bookId, int start, int count, String fields, final ApiCompleteListener listener) {

    }

    @Override
    public void loadSeriesList(String SeriesId, int start, int count, String fields, final ApiCompleteListener listener) {

    }

    @Override
    public void loadCollectionInfo(String userId, String password, String isbn, final ApiCompleteListener listener) {

    }

    @Override
    public void orderBook(String userId, String password, String bookId, String count, String take_loca1, String callno, String location, final ApiCompleteListener listener) {

    }

    @Override
    public void cancelLoading() {

    }
}
