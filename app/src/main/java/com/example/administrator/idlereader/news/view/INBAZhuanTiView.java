package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.hupu.NbaZhuanti;

public interface INBAZhuanTiView {
    void showData(NbaZhuanti data);
    void hideDialog();

    void showDialog();

    void showErrorMsg(Throwable throwable);
}
