package com.example.administrator.idlereader.http;

import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpModule {
    public static void setmResultListener(ResultListener mResultListener) {
        HttpModule.mResultListener = mResultListener;
    }

    public static ResultListener mResultListener;
    public static void getWeiBoUserShow(Map<String, String> params) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Integer.class, new NewsModel.IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(int.class, new NewsModel.IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(NbaDetailNews.ResultBean.ShareBean.class, new NewsModel.ShareBeanAdapter());
        Gson mgson = gsonBuilder.create();
        RetrofitHelper.getInstance(Api.WEIBO_LIST,mgson)
                .getWeiBoUserShowNews(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoSpaceUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeiBoSpaceUser weiBoSpaceUser) {
                       mResultListener.success(weiBoSpaceUser);
                    }
                });
    }
    public interface ResultListener{
        void success(WeiBoSpaceUser o);
        void fail(Throwable e);
    }
}
