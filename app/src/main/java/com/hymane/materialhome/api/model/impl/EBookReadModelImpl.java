package com.hymane.materialhome.api.model.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.common.ServiceFactory;
import com.hymane.materialhome.api.common.service.IEBooksService;
import com.hymane.materialhome.api.model.IEBookReadModel;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.bean.http.ebook.BookChapter;
import com.hymane.materialhome.bean.http.ebook.ChapterRead;
import com.hymane.materialhome.common.URL;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/29
 * Description:
 */

public class EBookReadModelImpl implements IEBookReadModel {
    IEBooksService eBooksService;

    @Override
    public void getBookChapters(String bookId, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getBookChapters(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())//请求完成后在io线程中执行
                .doOnNext(chapter -> {//缓存章节

                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<BookChapter>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(BookChapter chapter) {
                        if (chapter.isOk()) {
                            listener.onComplected(chapter.getMixToc());
                        } else {
                            listener.onFailed(new BaseResponse(400, chapter.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getChapterContent(String url, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getChapterContent(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())//请求完成后在io线程中执行
                .doOnNext(chapter -> {//缓存章节

                })
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<ChapterRead>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(ChapterRead chapter) {
                        if (chapter.isOk()) {
                            listener.onComplected(chapter.getChapter());
                        } else {
                            listener.onFailed(new BaseResponse(400, chapter.getMsg()));
                        }
                    }
                });
    }
}
