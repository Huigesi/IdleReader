package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;

public interface IWeiBoSpaceView {
    void hideDialog();

    void showErrorMsg(Throwable throwable);

    void showData(WeiBoNews data);

    void showDialog();

    void showMoreData(WeiBoNews moreData);

    void showHeader(WeiBoSpaceUser data);
}
