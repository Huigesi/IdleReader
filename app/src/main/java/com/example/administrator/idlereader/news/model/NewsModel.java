package com.example.administrator.idlereader.news.model;


import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.idlereader.http.Api.HUPU_NBA;

/**
 * Created by Administrator on 2018/5/19.
 */

public class NewsModel implements INewsModel {

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
}
