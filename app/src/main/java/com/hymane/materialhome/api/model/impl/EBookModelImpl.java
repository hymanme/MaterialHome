package com.hymane.materialhome.api.model.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.common.ServiceFactory;
import com.hymane.materialhome.api.common.service.IEBooksService;
import com.hymane.materialhome.api.model.IEBookModel;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.bean.http.ebook.BooksByCats;
import com.hymane.materialhome.bean.http.ebook.CategoryList;
import com.hymane.materialhome.bean.http.ebook.Rankings;
import com.hymane.materialhome.common.URL;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/21
 * Description:
 */

public class EBookModelImpl implements IEBookModel {
    IEBooksService eBooksService;

    @Override
    public void getRanking(String rankingId, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getRanking(rankingId)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Rankings>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(Rankings rankings) {
                        if (rankings.isOk()) {
                            listener.onComplected(rankings.getRanking());
                        } else {
                            listener.onFailed(new BaseResponse(400, rankings.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getCategoryList(ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getCategoryList()
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<CategoryList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(CategoryList categoryList) {
                        if (categoryList.isOk()) {
                            listener.onComplected(categoryList);
                        } else {
                            listener.onFailed(new BaseResponse(400, categoryList.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getCategoryListDetail(String gender, String type, String major, String minor, int start, int limit, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getBooksByCats(gender, type, major, minor, start, limit)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<BooksByCats>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(BooksByCats booksByCats) {
                        if (booksByCats.isOk()) {
                            listener.onComplected(booksByCats);
                        } else {
                            listener.onFailed(new BaseResponse(400, booksByCats.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
