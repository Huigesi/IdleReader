package com.example.administrator.idlereader.http;


import android.util.SparseArray;

import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.TodayBean;
import com.example.administrator.idlereader.bean.VideoUrlBean;
import com.example.administrator.idlereader.bean.WeatherBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;
import com.example.administrator.idlereader.utils.klog.KLog;
import com.google.gson.Gson;

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
    private static final String c = "weicoabroad";
    private static final String s = "18909f1e";

    public RetrofitHelper(int host) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getHost(host))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
         retrofitService = retrofit.create(RetrofitService.class);
    }
    //自定义Gson适配器时用到
    public RetrofitHelper(int host,Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getHost(host))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
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

    public static RetrofitHelper getInstance(int hostType,Gson gson) {
        RetrofitHelper instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitHelper(hostType,gson);
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

    public Observable<HupuNews> getHupuNews(String nid,int count){
        return retrofitService.getHupuNews(Api.HUPU_CLIENT_ID,nid,count);
    }

    public Observable<NbaDetailNews> getNbaNewsDetail(String nid){
        return retrofitService.getNbaNewsDetail(nid, Api.HUPU_CLIENT_ID);
    }

    public Observable<NbaNewsComment> getNbaComment(String nid,String ncid,String createTime) {
        return retrofitService.getNewsComment(nid, Api.HUPU_CLIENT_ID,ncid,createTime);
    }

    public Observable<WeiBoUserInfo> login(String user, String password) {
        return retrofitService.login(c,s,user,password);
    }

    public Observable<WeiBoNews> getWeiBoNews(String sinceId, String s, String gsid, int page, String c) {
        String form = "1273095010";
        String wm = "2468_1001";
        String source = "4215535043";
        String advance_enable = "false";
        return retrofitService.getWeiBoNews(sinceId,s,gsid,page,c,form,wm,source,advance_enable);
    }

    public Observable<WeiBoNews> getWeiBoUserNews(String sinceId, String s, String gsid, int page, String c,String uid) {
        String form = "1273095010";
        String wm = "2468_1001";
        String source = "4215535043";
        return retrofitService.getWeiBoUser(sinceId,s,gsid,page,c,form,wm,source,uid);
    }
    public Observable<WeiBoSpaceUser> getWeiBoUserHeaderNews(String sinceId, String s, String gsid, String c, String uid) {
        String form = "1273095010";
        String wm = "2468_1001";
        String source = "4215535043";
        return retrofitService.getWeiBoUserHead(sinceId,s,gsid,c,form,wm,source,uid);
    }

    public Observable<WeiBoDetail> getWeiBoDetail(String s, String c, String id, String gsid,long max_id) {
        return retrofitService.getWeiBoDetail(s, c, id, gsid,max_id,2);
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
