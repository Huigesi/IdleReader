package com.example.administrator.idlereader.news.presenter;

import android.content.Context;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface INewsPresenter {
    void loadNews(int type, int startPage);

    void loadNbaNews(String nid,
                     int count);

    void weiBoLogin(String user, String password, Context context);

    void loadNbaDetail(String nid);

    void loadMoreNbaComment(String nid, String ncid, String createTime);

    void loadNbaComment(String nid);

    void loadWeibo(String sinceid, String gsid,int page);

    void loadWeiBoDetail(String sinceid, String gsid,long max_id);

    void loadWeiBoUserNews(String uid,String gsid, int page);

    void loadWeiBoUserHeaderNews(String uid,String gsid);

}
