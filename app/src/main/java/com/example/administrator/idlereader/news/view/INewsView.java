package com.example.administrator.idlereader.news.view;


import com.example.administrator.idlereader.bean.news.News163;
import com.example.administrator.idlereader.bean.news.NewsBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface INewsView {
    /*void showNews(NewsBean newsBean);

    void showMoreNews(NewsBean newsBean);*/

    void showNews(List<News163> data);

    void showMoreNews(List<News163> data);

    void hideDialog();

    void showDialog();

    void showErrorMsg(Throwable throwable);
}
