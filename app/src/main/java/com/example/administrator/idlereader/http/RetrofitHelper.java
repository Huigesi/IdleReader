package com.example.administrator.idlereader.http;


import android.util.SparseArray;

import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.TodayBean;
import com.example.administrator.idlereader.bean.VideoUrlBean;
import com.example.administrator.idlereader.bean.WeatherBean;
import com.example.administrator.idlereader.utils.klog.KLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/19.
 */

public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";
    private static OkHttpClient okHttpClient;
    private RetrofitService retrofitService;
    private static SparseArray<RetrofitHelper> sInstanceManager = new SparseArray<>(3);

    public RetrofitHelper(int host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getHost(host))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
         retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitHelper getInstance(int hostType) {
        RetrofitHelper instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitHelper(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }
    }

    public Observable<NewsBean> getNews(String type, String id, int startPage) {
        return retrofitService.getNews(type, id, startPage);
    }
    public Observable<MoviesBean> getMovies(String total,String city,int start,int count) {
        return retrofitService.getMovie(total,city,start,count);
    }
    public Observable<TodayBean> getToday(String category){
        return retrofitService.getToday(category);
    }
    public Observable<VideoUrlBean> getVideoUrl(String api){
        return retrofitService.getVideoUrl(api);
    }
    public Observable<WeatherBean> getWeather(int cityKey){
        return retrofitService.getWeather(cityKey);
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient==null){
            synchronized (RetrofitHelper.class) {
                okHttpClient = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(mLoggingInterceptor).retryOnConnectionFailure(true)
                        .build();
            }
        }
        return okHttpClient;
    }
    // 打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            request = requestBuilder.build();

            final Response response = chain.proceed(request);

            KLog.e(TAG,"请求网址: \n" + request.url() + " \n " + "请求头部信息：\n" + request.headers() + "响应头部信息：\n" + response.headers());

            final ResponseBody responseBody = response.body();
            final long contentLength = responseBody.contentLength();

            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    KLog.e(TAG,"");
                    KLog.e(TAG,"Couldn't decode the response body; charset is likely malformed.");
                    return response;
                }
            }/*

            if (contentLength != 0) {
                KLog.v(TAG,"--------------------------------------------开始打印返回数据----------------------------------------------------");
                KLog.json(TAG,buffer.clone().readString(charset));
                KLog.v(TAG,"--------------------------------------------结束打印返回数据----------------------------------------------------");
            }*/

            return response;
        }
    };

}
