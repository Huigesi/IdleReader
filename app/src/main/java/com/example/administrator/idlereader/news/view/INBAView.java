package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.hupu.HupuNews;

public interface INBAView{
    void showData(HupuNews hupuNews);

    void showMoreData(HupuNews hupuNews);

    void hideDialog();

    void showDialog();

    void showErrorMsg(Throwable throwable);
}
