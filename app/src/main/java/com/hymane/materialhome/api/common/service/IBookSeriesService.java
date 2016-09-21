package com.hymane.materialhome.api.common.service;

import com.hymane.materialhome.bean.http.douban.BookSeriesListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/2
 * Description:
 */
public interface IBookSeriesService {
    @GET("book/series/{seriesId}/books")
    Observable<Response<BookSeriesListResponse>> getBookSeries(@Path("seriesId") String seriesId, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);

}
