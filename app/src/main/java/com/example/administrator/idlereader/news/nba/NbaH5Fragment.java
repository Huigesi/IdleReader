package com.example.administrator.idlereader.news.nba;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSComment;
import com.example.administrator.idlereader.bean.hupu.NbaBBSLightComment;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INbaBBSView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NbaH5Fragment extends BaseRecyclerFragment implements INbaBBSView {

    private static final String TAG = "NbaH5Fragment";
    public String nid, tid;
    public static final String NBA_H5_NID = "NBA_H5_NID";
    public static final String NBA_H5_TID = "NBA_H5_TID";
    private NbaBBSHeaderView mNbaBBSHeaderView;
    private NbaBBSDetailLightView mNbaDetailLightView;
    private NbaBBSDetailAdapter mNbaBBSDetailAdapter;
    private NewsPresenter mNewsPresenter;

    public static NbaH5Fragment getInstance() {
        NbaH5Fragment fragment = new NbaH5Fragment();
        return fragment;
    }

    @Override
    public void init() {
        nid = getActivity().getIntent().getStringExtra(NBA_H5_NID);
        tid = getActivity().getIntent().getStringExtra(NBA_H5_TID);
        mNewsPresenter = new NewsPresenter(this);
        Map<String, String> parmes = new HashMap<>();
        parmes.put("page","1");
        parmes.put("tid", tid);
        parmes.put("client", Api.HUPU_CLIENT_ID);
        mNewsPresenter.loadNbaBBSComment(parmes);
        mNewsPresenter.loadNbaLightBBSComment(parmes);
        mNbaBBSDetailAdapter=new NbaBBSDetailAdapter(getActivity());
        mNbaBBSHeaderView = new NbaBBSHeaderView(getActivity());
        mNbaDetailLightView = new NbaBBSDetailLightView(getActivity());
        mNbaBBSHeaderView.setData(tid);
        mNbaBBSDetailAdapter.setHeaderView(mNbaBBSHeaderView);
        mRvNews.setAdapter(mNbaBBSDetailAdapter);
        mRvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showCommentData(NbaBBSComment commentData) {
        mNbaBBSDetailAdapter.setData(commentData.getResult().getList(),true);
    }

    @Override
    public void showLightCommentData(NbaBBSLightComment commentData) {
        if (commentData.getList() != null&&commentData.getList().size()>0) {
            mNbaDetailLightView.setData(commentData.getList());
            mNbaBBSDetailAdapter.setLightCommentView(mNbaDetailLightView);
        }else {
            mNbaBBSDetailAdapter.removeLightCommentView();
        }
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
