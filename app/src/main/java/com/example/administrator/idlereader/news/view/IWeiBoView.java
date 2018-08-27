package com.example.administrator.idlereader.news.view;

import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;

public interface IWeiBoView {
    void hideDialog();

    void showErrorMsg(Throwable throwable);

    void showData(WeiBoNews data);
    void showDialog();
    void showMoreData(WeiBoNews moreData);
}
