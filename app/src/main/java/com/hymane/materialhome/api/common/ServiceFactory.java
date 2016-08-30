package com.hymane.materialhome.api.common;

import android.util.Log;

import com.hymane.materialhome.BaseApplication;
import com.hymane.materialhome.utils.NetworkUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/5
 * Description:
 */
public class ServiceFactory {
    private volatile static OkHttpClient okHttpClient;
    private static final int DEFAULT_CACHE_SIZE = 1024 * 1024 * 20;
    private static final int DEFAULT_MAX_AGE = 60 * 60;
    private static final int DEFAULT_MAX_STALE = DEFAULT_MAX_AGE * 24 * 7;

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    File cacheFile = new File(BaseApplication.getApplication().getCacheDir(), "responses");
                    Cache cache = new Cache(cacheFile, DEFAULT_CACHE_SIZE);
                    okHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(CACHED_INTERCEPTOR)
                            .addInterceptor(LoggingInterceptor)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    private static final Interceptor CACHED_INTERCEPTOR = chain -> {
        Request request = chain.request();
        if (!NetworkUtils.isConnected(BaseApplication.getApplication())) {
            int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
            CacheControl tempCacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxStale, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtils.isConnected(BaseApplication.getApplication())) {
            int maxAge = DEFAULT_MAX_AGE;
            // 有网络时 设置缓存超时时间1个小时
            response.newBuilder()
                    .removeHeader("Cache-Control")
                    .removeHeader("Expires")
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            // 无网络时，设置超时为1周
            int maxStale = DEFAULT_MAX_STALE;
            response.newBuilder()
                    .removeHeader("Cache-Control")
                    .removeHeader("Pragma")
                    .removeHeader("Expires")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    };

    private static final Interceptor LoggingInterceptor = chain -> {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.i("okhttp:", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Log.i("okhttp:", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    };

    public static <T> T createService(String baseUrl, Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }
}