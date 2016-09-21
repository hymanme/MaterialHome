package com.hymane.materialhome.api.common.service;

import com.hymane.materialhome.bean.http.douban.BookListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/5
 * Description:
 */
public interface IBookListService {
    @GET("book/search")
    Observable<Response<BookListResponse>> getBookList(@Query("q") String q, @Query("tag") String tag, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);
}
