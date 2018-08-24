package com.example.administrator.idlereader.news.presenter;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface INewsPresenter {
    void loadNews(int type, int startPage);

    void loadNbaNews(String nid,
                     int count);

    void loadNbaDetail(String nid);

    void loadMoreNbaComment(String nid,String ncid,String createTime);

    void loadNbaComment(String nid);

    void loadWeibo(String sinceid,int page);

    void loadWeiBoDetail(String sinceid,long max_id);

    void loadWeiBoUserNews(String uid, int page);
    void loadWeiBoUserHeaderNews(String uid);

}
