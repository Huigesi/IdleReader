package com.example.administrator.idlereader.news.model;

import android.content.Context;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface INewsModel {
    void loadNews(String hostType,
                  int startPage,
                  String id,
                  INewsLoadListener iNewsLoadListener);

    void loadNbaNews(String nid,
                     int count,
                     INewsLoadListener iNewsLoadListener);

    void loadNbaDetails(String nid,
                        INewsLoadListener iNewsLoadListener);

    void loadNbaComment(String nid,
                        String ncid, String createTime,
                        INewsLoadListener iNewsLoadListener);

    void loadNbaZhuanTi(String nid,
                        INewsLoadListener iNewsLoadListener);

    void weiBoLogin(String user,
                    String password,
                    Context context,
                    INewsLoadListener iNewsLoadListener);

    void loadWeibo(String sinceid,
                   int page,
                   String gsId,
                   INewsLoadListener iNewsLoadListener);

    void loadWeiBoDetail(String sinceid,
                         long max_id,
                         String gsId,
                         INewsLoadListener iNewsLoadListener);

    void loadWeiBoUserNews(String uid,
                           int page,
                           String gsId,
                           INewsLoadListener iNewsLoadListener);

    void loadWeiBoUserHeaderNews(String uid,
                                 String gsId,
                                 INewsLoadListener iNewsLoadListener);

}
