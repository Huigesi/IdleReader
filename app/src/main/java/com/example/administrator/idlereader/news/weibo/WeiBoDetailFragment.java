package com.example.administrator.idlereader.news.weibo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoDetailView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeiBoDetailFragment extends Fragment implements IWeiBoDetailView {
    private static final String TAG = "WeiBoDetailFragment";
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.srl_news)
    SmartRefreshLayout mSrlNews;
    Unbinder unbinder;
    private NewsPresenter mNewsPresenter;
    public static final String WEIBO_NID = "WEIBO_NID";
    public String nid;

    public static WeiBoDetailFragment getInstance() {
        WeiBoDetailFragment fragment = new WeiBoDetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_news_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNewsPresenter = new NewsPresenter(this);
        nid = getActivity().getIntent().getStringExtra(WEIBO_NID);
        mNewsPresenter.loadWeiBoDetail(nid);
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }

    @Override
    public void showData(WeiBoDetail data) {
        Log.i(TAG, "showData: "+data.getStatus().getText());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
