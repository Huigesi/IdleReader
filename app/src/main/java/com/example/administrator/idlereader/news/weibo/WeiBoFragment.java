package com.example.administrator.idlereader.news.weibo;

import android.graphics.Rect;
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

import com.example.administrator.idlereader.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoView;
import com.example.administrator.idlereader.utils.Resolution;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeiBoFragment extends BaseRecyclerFragment implements IWeiBoView {
    private static final String TAG = "WeiBoFragment";
    Unbinder unbinder;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsPresenter mNewsPresenter;
    private WeiBoNewsAdapter mWeiBoNewsAdapter;
    private int page=1;

    public static WeiBoFragment getInstance() {
        WeiBoFragment fragment = new WeiBoFragment();
        return fragment;
    }

    @Override
    public void hideDialog() {
        mSrlNews.finishRefresh(0);
        mSrlNews.finishLoadMore(0);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Log.i(TAG, "showErrorMsg: "+throwable.getMessage());
    }

    @Override
    public void showData(WeiBoNews data) {
        mWeiBoNewsAdapter.setData(data.getStatuses(), true);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void showMoreData(WeiBoNews moreData) {
        mWeiBoNewsAdapter.setData(moreData.getStatuses(), false);
    }

    @Override
    public void init() {
        mNewsPresenter = new NewsPresenter(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mWeiBoNewsAdapter = new WeiBoNewsAdapter(getActivity());
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsPresenter.loadWeibo(String.valueOf(0),1);
            }
        });
        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mNewsPresenter.loadWeibo(String.valueOf(0),page);
            }
        });
        final int line = Resolution.dipToPx(getActivity(), 5);
        mRvNews.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0,0,0,line);
            }
        });
        mRvNews.setLayoutManager(mLinearLayoutManager);
        mNewsPresenter.loadWeibo(String.valueOf(0),1);
        mRvNews.setAdapter(mWeiBoNewsAdapter);
    }
}
