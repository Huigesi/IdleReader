package com.example.administrator.idlereader.news.model;


import android.util.Log;

import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.idlereader.http.Api.HUPU_NBA;

/**
 * Created by Administrator on 2018/5/19.
 */

public class NewsModel implements INewsModel {
    private static final String TAG = "NewsModel";
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
                        if (count >0) {
                            iNewsLoadListener.loadMoreNbaSuccess(hupuNews);
                        } else {
                            iNewsLoadListener.loadNbaSuccess(hupuNews);
                        }
                    }
                });
    }

    @Override
    public void loadNbaDetails(String nid, final INewsLoadListener iNewsLoadListener) {
        RetrofitHelper.getInstance(Api.HUPU_NBA)
                .getNbaNewsDetail(nid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<NbaDetailNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
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
                .getNbaComment(nid,ncid,createTime)
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
                        }else {
                            iNewsLoadListener.loadNbaCommentSuccess(nbaNewsComment);
                        }
                    }
                });
    }

    @Override
    public void loadWeibo(String sinceid, final INewsLoadListener iNewsLoadListener) {
        String s = "606388e6";
        String gsid = "_2A252cRzBDeRxGeNH61cX8yvNyT6IHXVTJxcJrDV6PUJbkdAKLUfykWpNSvDZShbJn5J7L7wv7ZqcP0d-KAnwRoKc";
        int page = 1;
        String c = "weicoabroad";
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Integer.class, new IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(int.class, new IntegerDefault0Adapter());
        Gson gson = gsonBuilder.create();

        RetrofitHelper.getInstance(Api.WEIBO_LIST,gson)
                .getWeiBoNews(sinceid, s, gsid, page, c)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoNews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ");
                        iNewsLoadListener.fail(e);
                        //java.lang.IllegalStateException: Expected a double but was END_ARRAY at line 1 column 5863 path $.statuses[0].retweeted_status.biz_ids[1]
                    }

                    @Override
                    public void onNext(WeiBoNews weiBoNewsList) {
                        iNewsLoadListener.loadWeiBoSuccess(weiBoNewsList);
                    }
                });
    }

    public class IntegerDefault0Adapter implements JsonSerializer, JsonDeserializer {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("")){
                    return 0;
                }
            } catch (Exception ignore){
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


    public class IntegerTypeAdapter extends TypeAdapter<Number> {
        @Override
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.value(number);
        }

        @Override
        public Number read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                String value = jsonReader.nextString();
                if ("".equals(value)) {
                    return 0;
                }
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    }

}
