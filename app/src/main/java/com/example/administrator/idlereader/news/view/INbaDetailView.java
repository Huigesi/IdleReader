package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;

public interface INbaDetailView {
    void showData(NbaDetailNews data);

    void showCommentData(NbaNewsComment commentData);

    void showMoreCommentData(NbaNewsComment commentData);

    void hideDialog();

    void showDialog();

    void showErrorMsg(Throwable throwable);
}
