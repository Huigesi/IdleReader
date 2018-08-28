package com.example.administrator.idlereader.news.nba;

import android.util.Log;

import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.hupu.NbaZhuanti;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INBAZhuanTiView;


public class NbaZhuanTiFragment extends BaseRecyclerFragment implements INBAZhuanTiView{
    private static final String TAG = "NbaZhuanTiFragment";
    public static final String NBA_NID = "NBA_NID";
    private NewsPresenter mNewsPresenter;
    private String mNid;

    @Override
    public void init() {
        mNid = getActivity().getIntent().getStringExtra(NBA_NID);
        mNewsPresenter = new NewsPresenter(this);
        mNewsPresenter.loadNbaZhuanTi(mNid);
    }

    @Override
    public void showData(NbaZhuanti data) {
        Log.i(TAG, "showData: "+data.getResult().getTitle());
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }
}
