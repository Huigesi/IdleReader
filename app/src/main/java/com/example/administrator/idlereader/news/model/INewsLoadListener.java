package com.example.administrator.idlereader.news.model;


import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.news.NewsBean;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface INewsLoadListener {
    void success(NewsBean newsBean);

    void fail(Throwable throwable);

    void loadMoreSuccess(NewsBean newsBean);

    void loadNbaSuccess(HupuNews hupuNews);

    void loadMoreNbaSuccess(HupuNews hupuNews);
}
