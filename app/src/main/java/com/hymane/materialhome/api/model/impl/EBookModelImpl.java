package com.hymane.materialhome.api.model.impl;

import com.hymane.materialhome.api.ApiCompleteListener;
import com.hymane.materialhome.api.common.ServiceFactory;
import com.hymane.materialhome.api.common.service.IEBooksService;
import com.hymane.materialhome.api.model.IEBookModel;
import com.hymane.materialhome.bean.http.douban.BaseResponse;
import com.hymane.materialhome.bean.http.ebook.BookDetail;
import com.hymane.materialhome.bean.http.ebook.BookZoneBean;
import com.hymane.materialhome.bean.http.ebook.BooksByCats;
import com.hymane.materialhome.bean.http.ebook.BooksByTag;
import com.hymane.materialhome.bean.http.ebook.CategoryList;
import com.hymane.materialhome.bean.http.ebook.HotReview;
import com.hymane.materialhome.bean.http.ebook.HotWords;
import com.hymane.materialhome.bean.http.ebook.LikedBookList;
import com.hymane.materialhome.bean.http.ebook.Rankings;
import com.hymane.materialhome.common.URL;

import retrofit2.Response;
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
    public void getBookDetail(String bookId, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getBookDetail(bookId)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookDetail>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(Response<BookDetail> bookDetail) {
                        if (bookDetail.isSuccessful()) {
                            BookDetail body = bookDetail.body();
                            listener.onComplected(bookDetail.body());
                        } else {
                            listener.onFailed(new BaseResponse(400, bookDetail.errorBody().toString()));
                        }
                    }
                });
    }

    @Override
    public void getBookReviewList(String bookId, String sort, int start, int limit, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getBookReviewList(bookId, sort, start, limit)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<HotReview>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(HotReview review) {
                        if (review.isOk()) {
                            listener.onComplected(review);
                        } else {
                            listener.onFailed(new BaseResponse(400, review.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getHotReview(String bookId, int limit, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getHotReview(bookId, limit)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<HotReview>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(HotReview review) {
                        if (review.isOk()) {
                            listener.onComplected(review);
                        } else {
                            listener.onFailed(new BaseResponse(400, review.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getBooksByTag(String tags, int start, int limit, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getBooksByTag(tags, start, limit)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<BooksByTag>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(BooksByTag booksByTag) {
                        if (booksByTag.isOk()) {
                            listener.onComplected(booksByTag);
                        } else {
                            listener.onFailed(new BaseResponse(400, booksByTag.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getRecommendBookList(String bookId, int limit, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getRecommendBookList(bookId, limit)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LikedBookList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(LikedBookList bookList) {
                        if (bookList.isOk()) {
                            listener.onComplected(bookList);
                        } else {
                            listener.onFailed(new BaseResponse(400, bookList.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getBookZoneDetail(String bookListId, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getBookZoneDetail(bookListId)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<BookZoneBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(BookZoneBean bookZone) {
                        if (bookZone.isOk()) {
                            listener.onComplected(bookZone);
                        } else {
                            listener.onFailed(new BaseResponse(400, bookZone.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void getHotWord(ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.getHotWord()
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<HotWords>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(new BaseResponse(400, e.toString()));
                    }

                    @Override
                    public void onNext(HotWords response) {
                        if (response.isOk()) {
                            listener.onComplected(response);
                        } else {
                            listener.onFailed(new BaseResponse(400, response.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void searchBooks(String query, int start, int limit, ApiCompleteListener listener) {
        if (eBooksService == null) {
            eBooksService = ServiceFactory.createService(URL.HOST_URL_ZSSQ, IEBooksService.class);
        }
        eBooksService.searchBooks(query,  start,  limit)
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
                    public void onNext(BooksByCats response) {
                        if (response.isOk()) {
                            listener.onComplected(response);
                        } else {
                            listener.onFailed(new BaseResponse(400, response.getMsg()));
                        }
                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
