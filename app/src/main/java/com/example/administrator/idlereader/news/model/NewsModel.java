package com.example.administrator.idlereader.news.model;


import android.content.Context;
import android.util.Log;

import com.example.administrator.idlereader.MainActivity;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;
import com.example.administrator.idlereader.utils.SPreUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.idlereader.http.Api.HUPU_NBA;

/**
 * Created by Administrator on 2018/5/19.
 */

public class NewsModel implements INewsModel {
    private static final String TAG = "NewsModel";
    private Gson mGson;
    private static final String s = "606388e6";
    private static final String c = "weicoabroad";
    private static final String form = "1273095010";
    private static final String wm = "2468_1001";
    private static final String source = "4215535043";

    @Override
    public void loadNews(final String hostType, final int startPage, final String id,
                         final INewsLoadListener iNewsLoadListener) {
        RetrofitHelper.getInstance(Api.NETEASE_NEWS)
                .getNews(hostType, id, startPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        if (startPage != 0) {
                            iNewsLoadListener.loadMoreSuccess(newsBean);
                        } else {
                            iNewsLoadListener.success(newsBean);
                        }

                    }
                });
    }

    @Override
    public void loadNbaNews(String nid,
                            final int count,
                            final INewsLoadListener iNewsLoadListener) {
        RetrofitHelper.getInstance(HUPU_NBA)
                .getHupuNews(nid, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<HupuNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HupuNews hupuNews) {
                        if (count > 0) {
                            iNewsLoadListener.loadMoreNbaSuccess(hupuNews);
                        } else {
                            iNewsLoadListener.loadNbaSuccess(hupuNews);
                        }
                    }
                });
    }

    @Override
    public void loadNbaDetails(String nid, final INewsLoadListener iNewsLoadListener) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Integer.class, new IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(int.class, new IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(NbaDetailNews.ResultBean.ShareBean.class, new ShareBeanAdapter());
        Gson mgson = gsonBuilder.create();
        RetrofitHelper.getInstance(Api.HUPU_NBA, mgson)
                .getNbaNewsDetail(nid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NbaDetailNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(NbaDetailNews nbaDetailNews) {
                        iNewsLoadListener.loadNbaDetailSuccess(nbaDetailNews);
                    }
                });
    }

    @Override
    public void loadNbaComment(String nid, final String ncid, final String createTime,
                               final INewsLoadListener iNewsLoadListener) {
        RetrofitHelper.getInstance(Api.HUPU_NBA)
                .getNbaComment(nid, ncid, createTime)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NbaNewsComment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(NbaNewsComment nbaNewsComment) {
                        if (ncid != null && createTime != null) {
                            iNewsLoadListener.loadMoreNbaCommentSuccess(nbaNewsComment);
                        } else {
                            iNewsLoadListener.loadNbaCommentSuccess(nbaNewsComment);
                        }
                    }
                });
    }

    @Override
    public void weiBoLogin(String user, String password, final Context context, final INewsLoadListener iNewsLoadListener) {
        setGsonAdapter();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, mGson)
                .login(user, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(WeiBoUserInfo weiBoUserInfo) {
                        Log.i(TAG, "onNext: " + weiBoUserInfo.getGsid());
                        SPreUtils.setWeiBoUserInfo(weiBoUserInfo, context);
                    }
                });
    }

    public void setGsonAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Integer.class, new IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(int.class, new IntegerDefault0Adapter());
        mGson = gsonBuilder.create();
    }

    @Override
    public void loadWeibo(String sinceid, final int page, String gsId, final INewsLoadListener iNewsLoadListener) {
        setGsonAdapter();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, mGson)
                .getWeiBoNews(sinceid, s, gsId, page, c)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(WeiBoNews weiBoNewsList) {
                        if (page > 1) {
                            iNewsLoadListener.loadMoreWeiBoSuccess(weiBoNewsList);
                        } else {
                            iNewsLoadListener.loadWeiBoSuccess(weiBoNewsList);
                        }
                    }
                });
    }

    @Override
    public void loadWeiBoDetail(String sinceid, final long max_id, String gsId, final INewsLoadListener iNewsLoadListener) {
        setGsonAdapter();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, mGson)
                .getWeiBoDetail(s, c, sinceid, gsId, max_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(WeiBoDetail weiBoDetail) {
                        if (max_id == 0) {
                            iNewsLoadListener.loadWeiBoDetailSuccess(weiBoDetail);
                        } else {
                            iNewsLoadListener.loadMoreWeiBoDetailSuccess(weiBoDetail);
                        }
                    }
                });
    }

    @Override
    public void loadWeiBoUserNews(String uid, final int page, String gsId, final INewsLoadListener iNewsLoadListener) {
        setGsonAdapter();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, mGson)
                .getWeiBoUserNews("0", s, gsId, 1, c, uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(WeiBoNews weiBoNewsList) {
                        if (page > 1) {
                            iNewsLoadListener.loadMoreWeiBoUserSuccess(weiBoNewsList);
                        } else {
                            iNewsLoadListener.loadWeiBoUserSuccess(weiBoNewsList);
                        }
                    }
                });
    }

    @Override
    public void loadWeiBoUserHeaderNews(String uid, String gsId, final INewsLoadListener iNewsLoadListener) {
        setGsonAdapter();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, mGson)
                .getWeiBoUserHeaderNews("0", s, gsId, c, uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoSpaceUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsLoadListener.fail(e);
                    }

                    @Override
                    public void onNext(WeiBoSpaceUser weiBoNewsList) {
                        iNewsLoadListener.loadWeiBoUserHeaderSuccess(weiBoNewsList);
                    }
                });
    }

    public static class IntegerDefault0Adapter implements JsonSerializer, JsonDeserializer {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("")) {
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(String.valueOf(src));
        }
    }

    public static class ShareBeanAdapter implements JsonSerializer, JsonDeserializer<NbaDetailNews.ResultBean.ShareBean> {
        @Override
        public NbaDetailNews.ResultBean.ShareBean deserialize(JsonElement json, Type typeOfT,
                                                              JsonDeserializationContext context)
                throws JsonParseException {
            NbaDetailNews.ResultBean.ShareBean shareBean = new NbaDetailNews.ResultBean.ShareBean();
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                shareBean.setImg(jsonObject.get("img").getAsString());
                shareBean.setUrl(jsonObject.get("url").getAsString());
                shareBean.setWechat_moments(jsonObject.get("wechat_moments").getAsString());
                shareBean.setWechat(jsonObject.get("wechat").getAsString());
                shareBean.setQzone(jsonObject.get("qzone").getAsString());
                shareBean.setQq(jsonObject.get("qq").getAsString());
                shareBean.setSummary(jsonObject.get("summary").getAsString());
            } else {
                String value = json.getAsString();
                if (value.equals("")) {
                    shareBean = null;
                }
            }
            return shareBean;
        }

        @Override
        public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(String.valueOf(src));
        }
    }

}
