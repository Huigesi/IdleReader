package com.example.administrator.idlereader.news.model;


import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;

/**
 * Created by Administrator on 2018/5/19.
 */

public interface INewsLoadListener {
    void success(NewsBean newsBean);

    void fail(Throwable throwable);

    void loadMoreSuccess(NewsBean newsBean);

    void loadNbaSuccess(HupuNews hupuNews);

    void loadMoreNbaSuccess(HupuNews hupuNews);

    void loadNbaDetailSuccess(NbaDetailNews nbaDetailNews);

    void loadNbaCommentSuccess(NbaNewsComment nbaNewsComment);

    void loadMoreNbaCommentSuccess(NbaNewsComment nbaNewsComment);

    void loadWeiBoSuccess(WeiBoNews weiBoNews);

    void loadMoreWeiBoSuccess(WeiBoNews weiBoNews);

    void loadWeiBoDetailSuccess(WeiBoDetail weiBoDetail);

    void loadMoreWeiBoDetailSuccess(WeiBoDetail weiBoDetail);

    void loadMoreWeiBoUserSuccess(WeiBoNews weiBoDetail);

    void loadWeiBoUserSuccess(WeiBoNews weiBoDetail);
    void loadWeiBoUserHeaderSuccess(WeiBoSpaceUser weiBoDetail);
}
