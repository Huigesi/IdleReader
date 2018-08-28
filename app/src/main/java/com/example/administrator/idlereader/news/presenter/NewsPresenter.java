package com.example.administrator.idlereader.news.presenter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.hupu.NbaBBSComment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSLightComment;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.bean.hupu.NbaZhuanti;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.news.FgNewsFragment;
import com.example.administrator.idlereader.news.model.INewsLoadListener;
import com.example.administrator.idlereader.news.model.INewsModel;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.example.administrator.idlereader.news.view.INBAView;
import com.example.administrator.idlereader.news.view.INBAZhuanTiView;
import com.example.administrator.idlereader.news.view.INbaBBSView;
import com.example.administrator.idlereader.news.view.INbaDetailView;
import com.example.administrator.idlereader.news.view.INewsView;
import com.example.administrator.idlereader.news.view.IWeiBoDetailView;
import com.example.administrator.idlereader.news.view.IWeiBoSpaceView;
import com.example.administrator.idlereader.news.view.IWeiBoView;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/19.
 */

public class NewsPresenter implements INewsPresenter, INewsLoadListener {

    private INewsModel iNewsModel;
    private INewsView iNewsView;
    private INBAView mINBAView;
    private INbaDetailView mINbaDetailView;
    private INBAZhuanTiView mINBAZhuanTiView;
    private IWeiBoView mIWeiBoView;
    private INbaBBSView mINbaBBSView;
    private IWeiBoSpaceView mIWeiBoSpaceView;
    private IWeiBoDetailView mIWeiBoDetailView;

    public NewsPresenter(INbaBBSView view) {
        this.mINbaBBSView = view;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(IWeiBoSpaceView iNewsView) {
        this.mIWeiBoSpaceView = iNewsView;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(INewsView iNewsView) {
        this.iNewsView = iNewsView;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(INBAView inbaView) {
        this.iNewsModel = new NewsModel();
        mINBAView = inbaView;
    }

    public NewsPresenter(INbaDetailView iNbaDetailView) {
        mINbaDetailView = iNbaDetailView;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(IWeiBoView iWeiBoView) {
        mIWeiBoView = iWeiBoView;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(IWeiBoDetailView iWeiBoDetailView) {
        mIWeiBoDetailView = iWeiBoDetailView;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(INBAZhuanTiView inbaZhuanTiView) {
        mINBAZhuanTiView=inbaZhuanTiView;
        this.iNewsModel = new NewsModel();
    }

    @Override
    public void loadNews(int type, int startPage) {
        if (startPage == 0) {
            iNewsView.showDialog();
        }

        switch (type) {
            case FgNewsFragment.NEWS_TYPE_TOP:
                iNewsModel.loadNews("headline", startPage, Api.HEADLINE_ID,
                        this);
                break;
            case FgNewsFragment.NEWS_TYPE_NBA:
                iNewsModel.loadNews("list", startPage, Api.NBA_ID,
                        this);
                break;
            case FgNewsFragment.NEWS_TYPE_GAME:
                iNewsModel.loadNews("list", startPage, Api.GAME_ID,
                        this);
                break;
        }

    }

    @Override
    public void loadNbaNews(String nid, int count) {
        if (count == 0) {
            mINBAView.showDialog();
        }
        iNewsModel.loadNbaNews(nid, count, this);
    }

    @Override
    public void weiBoLogin(String user, String password, Context context) {
        iNewsModel.weiBoLogin(user, password, context, this);
    }

    @Override
    public void loadNbaDetail(String nid) {
        mINbaDetailView.showDialog();
        iNewsModel.loadNbaDetails(nid, this);
    }

    @Override
    public void loadMoreNbaComment(String nid, String ncid, String createTime) {
        mINbaDetailView.showDialog();
        iNewsModel.loadNbaComment(nid, ncid, createTime, this);
    }

    @Override
    public void loadNbaComment(String nid) {
        mINbaDetailView.showDialog();
        iNewsModel.loadNbaComment(nid, null, null, this);
    }

    @Override
    public void loadNbaZhuanTi(String nid) {
        iNewsModel.loadNbaZhuanTi(nid, this);
    }

    @Override
    public void loadNbaBBSComment(Map<String, String> parms) {
        iNewsModel.loadNbaBBSComment(parms,this);
    }

    @Override
    public void loadNbaLightBBSComment(Map<String, String> parms) {
        iNewsModel.loadLightNbaBBSComment(parms,this);
    }

    @Override
    public void loadWeibo(String sinceid, String gsid, int page) {
        if (page == 1) {
            mIWeiBoView.showDialog();
        }
        iNewsModel.loadWeibo(sinceid, page, gsid, this);
    }

    @Override
    public void loadWeiBoDetail(String sinceid, String gsid, long max_id) {
        iNewsModel.loadWeiBoDetail(sinceid, max_id, gsid, this);
    }

    @Override
    public void loadWeiBoUserNews(String uid, String gsid, int page) {
        iNewsModel.loadWeiBoUserNews(uid, page, gsid, this);
    }

    @Override
    public void loadWeiBoUserHeaderNews(String uid, String gsid) {
        iNewsModel.loadWeiBoUserHeaderNews(uid, gsid, this);
    }

    @Override
    public void success(NewsBean newsBean) {
        iNewsView.hideDialog();
        if (newsBean != null) {
            iNewsView.showNews(newsBean);
        }
    }

    @Override
    public void fail(Throwable throwable) {
        iNewsView.hideDialog();
        iNewsView.showErrorMsg(throwable);
    }

    @Override
    public void loadMoreSuccess(NewsBean newsBean) {
        iNewsView.hideDialog();
        iNewsView.showMoreNews(newsBean);
    }

    @Override
    public void loadNbaSuccess(HupuNews hupuNews) {
        mINBAView.hideDialog();
        mINBAView.showData(hupuNews);
    }

    @Override
    public void loadMoreNbaSuccess(HupuNews hupuNews) {
        mINBAView.hideDialog();
        mINBAView.showMoreData(hupuNews);
    }

    @Override
    public void loadNbaDetailSuccess(NbaDetailNews nbaDetailNews) {
        mINbaDetailView.showData(nbaDetailNews);
        mINbaDetailView.hideDialog();
    }

    @Override
    public void loadNbaCommentSuccess(NbaNewsComment nbaNewsComment) {
        mINbaDetailView.showCommentData(nbaNewsComment);
        mINbaDetailView.hideDialog();
    }

    @Override
    public void loadNbaZhuanTiSuccess(NbaZhuanti data) {
        mINBAZhuanTiView.showData(data);
        mINBAZhuanTiView.hideDialog();
    }

    @Override
    public void loadMoreNbaCommentSuccess(NbaNewsComment nbaNewsComment) {
        mINbaDetailView.showMoreCommentData(nbaNewsComment);
        mINbaDetailView.hideDialog();
    }

    @Override
    public void loadNbaBBSCommentSuccess(NbaBBSComment data) {
        mINbaBBSView.showCommentData(data);
    }

    @Override
    public void loadNbaBBSLightCommentSuccess(NbaBBSLightComment data) {
        mINbaBBSView.showLightCommentData(data);
    }

    @Override
    public void loadWeiBoSuccess(WeiBoNews weiBoNews) {
        mIWeiBoView.showData(weiBoNews);
        mIWeiBoView.hideDialog();
    }

    @Override
    public void loadMoreWeiBoSuccess(WeiBoNews weiBoNews) {
        mIWeiBoView.showMoreData(weiBoNews);
        mIWeiBoView.hideDialog();
    }

    @Override
    public void loadWeiBoDetailSuccess(WeiBoDetail weiBoDetail) {
        mIWeiBoDetailView.showData(weiBoDetail);
        mIWeiBoDetailView.hideDialog();
    }

    @Override
    public void loadMoreWeiBoDetailSuccess(WeiBoDetail weiBoDetail) {
        mIWeiBoDetailView.showMoreData(weiBoDetail);
        mIWeiBoDetailView.hideDialog();
    }

    @Override
    public void loadMoreWeiBoUserSuccess(WeiBoNews weiBoDetail) {
        mIWeiBoSpaceView.showMoreData(weiBoDetail);
        mIWeiBoSpaceView.hideDialog();
    }

    @Override
    public void loadWeiBoUserSuccess(WeiBoNews weiBoDetail) {
        mIWeiBoSpaceView.hideDialog();
        mIWeiBoSpaceView.showData(weiBoDetail);
    }

    @Override
    public void loadWeiBoUserHeaderSuccess(WeiBoSpaceUser weiBoDetail) {
        mIWeiBoSpaceView.hideDialog();
        mIWeiBoSpaceView.showHeader(weiBoDetail);
    }
}
