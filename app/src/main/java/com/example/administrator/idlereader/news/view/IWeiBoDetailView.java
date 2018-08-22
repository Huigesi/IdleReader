package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;

public interface IWeiBoDetailView {
    void hideDialog();

    void showErrorMsg(Throwable throwable);

    void showData(WeiBoDetail data);
}
