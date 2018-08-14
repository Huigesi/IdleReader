package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;

public interface INbaDetailView {
    void showData(NbaDetailNews data);

    void hideDialog();

    void showDialog();

    void showErrorMsg(Throwable throwable);
}
