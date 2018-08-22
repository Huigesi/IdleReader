package com.example.administrator.idlereader.news.weibo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.idlereader.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoDetailView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    private WeiBoDetailAdapter mWeiBoDetailAdapter;
    private WeiBoDetailHeaderView mWeiBoDetailHeaderView;

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
        mWeiBoDetailAdapter = new WeiBoDetailAdapter(getContext());
        mWeiBoDetailHeaderView = new WeiBoDetailHeaderView(getContext());
        mWeiBoDetailAdapter.setHeaderView(mWeiBoDetailHeaderView);
        mRvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvNews.setAdapter(mWeiBoDetailAdapter);
        mSrlNews.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(
                getResources().getColor(R.color.colorTheme)));
        mSrlNews.setRefreshFooter(new DefaultsFooter(getActivity()).setFinishDuration(0));
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsPresenter.loadWeiBoDetail(nid);
            }
        });
        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }
        });
        mNewsPresenter.loadWeiBoDetail(nid);
    }

    @Override
    public void hideDialog() {
       mSrlNews.finishRefresh(0);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showData(WeiBoDetail data) {
        mWeiBoDetailHeaderView.setData(data);
        if (data.getRoot_comments() != null && data.getRoot_comments().size() > 0) {
            mWeiBoDetailAdapter.setData(data.getRoot_comments(),false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}