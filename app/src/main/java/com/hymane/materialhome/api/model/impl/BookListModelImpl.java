package com.hymane.materialhome.api.model.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.common.ServiceFactory;
import com.hymane.materialhome.api.common.service.IBookService;
import com.hymane.materialhome.api.model.IBookListModel;
import com.hymane.materialhome.bean.http.BaseResponse;
import com.hymane.materialhome.bean.http.BookListResponse;
import com.hymane.materialhome.common.URL;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/5
 * Description:
 */
public class BookListModelImpl implements IBookListModel {

    /**
     * 获取图书列表
     */
    @Override
    public void loadBookList(String q, final String tag, int start, int count, String fields, final ApiCompleteListener listener) {
        IBookService iBookService = ServiceFactory.createService(URL.HOST_URL_DOUBAN, IBookService.class);
        iBookService.getBookList(q, tag, start, count, fields)
                .subscribeOn(Schedulers.newThread())    //请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<BookListResponse> bookListResponse) {
                        if (bookListResponse.isSuccessful()) {
                            listener.onComplected(bookListResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(bookListResponse.code(), bookListResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
