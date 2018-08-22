package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;

public interface IWeiBoDetailView {
    void hideDialog();

    void showErrorMsg(Throwable throwable);
    void showDialog();
    void showData(WeiBoDetail data);
    void showMoreData(WeiBoDetail data);
}
