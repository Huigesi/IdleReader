package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.hupu.NbaBBSComment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSLightComment;

public interface INbaBBSView {

    void showCommentData(NbaBBSComment commentData);

    void showLightCommentData(NbaBBSLightComment commentData);

    void hideDialog();

    void showDialog();

    void showErrorMsg(Throwable throwable);
}
