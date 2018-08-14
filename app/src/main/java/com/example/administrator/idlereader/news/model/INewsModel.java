package com.example.administrator.idlereader.news.model;

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
}
