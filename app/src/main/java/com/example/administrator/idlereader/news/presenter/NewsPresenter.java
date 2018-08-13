package com.example.administrator.idlereader.news.presenter;


import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.news.FgNewsFragment;
import com.example.administrator.idlereader.news.HupuNBAFragment;
import com.example.administrator.idlereader.news.model.INewsLoadListener;
import com.example.administrator.idlereader.news.model.INewsModel;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.example.administrator.idlereader.news.view.INBAView;
import com.example.administrator.idlereader.news.view.INewsView;

/**
 * Created by Administrator on 2018/5/19.
 */

public class NewsPresenter implements INewsPresenter, INewsLoadListener {

    private INewsModel iNewsModel;
    private INewsView iNewsView;
    private INBAView mINBAView;
    private onNidChangeListener mOnNidChangeListener;

    public String getNid() {
        return mNid;
    }

    public void setNid(String nid) {
        mNid = nid;
    }

    private String mNid;

    public NewsPresenter(INewsView iNewsView) {
        this.iNewsView = iNewsView;
        this.iNewsModel = new NewsModel();
    }

    public NewsPresenter(INBAView INBAView) {
        this.iNewsModel = new NewsModel();
        mINBAView = INBAView;
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
        mNid = hupuNews.getResult().getData().get(hupuNews.getResult().getData().size()).getNid();
        setNid(mNid);
    }

    @Override
    public void loadMoreNbaSuccess(HupuNews hupuNews) {
        mINBAView.hideDialog();
        mINBAView.showMoreData(hupuNews);
        mNid = hupuNews.getResult().getData().get(hupuNews.getResult().getData().size()).getNid();
        setNid(mNid);
    }

    public interface onNidChangeListener {
        void nidChange(String nid);
    }

    public void setOnNidChangeListener(onNidChangeListener onNidChangeListener) {
        this.mOnNidChangeListener = onNidChangeListener;
    }
}
