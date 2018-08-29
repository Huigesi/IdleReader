package com.example.administrator.idlereader.news.nba;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.idlereader.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseEndlessListener;
import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INbaDetailView;
import com.example.administrator.idlereader.utils.Resolution;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NbaDetailFragment extends BaseRecyclerFragment implements INbaDetailView {
    private static final String TAG = "NbaDetailFragment";
    public static final String NBA_NID = "NBA_NID";
    private NewsPresenter mNewsPresenter;
    private NbaDetailAdapter mNbaDetailAdapter;
    private NbaDetailHeaderView mNbaDetailHeaderView;
    private NbaDetailLightView mNbaDetailLightView;
    private BaseEndlessListener<NbaNewsComment.DataBean> mEndlessListener;
    public String mNcid, mCreateTime;
    public String nid;

    public static NbaDetailFragment getInstance() {
        NbaDetailFragment fragment = new NbaDetailFragment();
        return fragment;
    }


    @Override
    public void init() {
        mNbaDetailAdapter = new NbaDetailAdapter(getActivity());
        mNewsPresenter = new NewsPresenter(this);
        mRvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvNews.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int line = Resolution.dipToPx(getContext(), 1);
                outRect.set(0, 0, 0, line);
            }
        });
        mNbaDetailHeaderView = new NbaDetailHeaderView(getActivity());
        mNbaDetailLightView = new NbaDetailLightView(getActivity());
        nid = getActivity().getIntent().getStringExtra(NBA_NID);
        mNewsPresenter.loadNbaComment(nid);
        mNewsPresenter.loadNbaDetail(nid);
        mRvNews.setAdapter(mNbaDetailAdapter);
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsPresenter.loadNbaDetail(nid);
                mNewsPresenter.loadNbaComment(nid);
            }
        });

        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }
        });
    }

    private void loadMoreData() {
        mNewsPresenter.loadMoreNbaComment(nid, mNcid, mCreateTime);
    }

    @Override
    public void showData(NbaDetailNews data) {
        mNbaDetailHeaderView.setData(data);
        mNbaDetailAdapter.setHeaderView(mNbaDetailHeaderView);
    }

    @Override
    public void showCommentData(NbaNewsComment commentData) {
        if (commentData.getData() != null && commentData.getData().size() > 0) {
            mNbaDetailAdapter.setData(commentData.getData(), true);
            if (commentData.getLight_comments() != null && commentData.getLight_comments().size() > 0) {
                mNbaDetailLightView.setData(commentData);
                mNbaDetailAdapter.setLightCommentView(mNbaDetailLightView);
            } else {
                mNbaDetailAdapter.removeLightCommentView();
            }
            mNcid = commentData.getData().get(commentData.getData().size() - 1).getNcid();
            mCreateTime = commentData.getData().get(commentData.getData().size() - 1).getCreate_time();
        }
    }

    @Override
    public void showMoreCommentData(NbaNewsComment commentData) {
        if (commentData.getData() != null && commentData.getData().size() > 0) {
            mNbaDetailAdapter.setData(commentData.getData(), false);
            mNcid = commentData.getData().get(commentData.getData().size() - 1).getNcid();
            mCreateTime = commentData.getData().get(commentData.getData().size() - 1).getCreate_time();
        } else {
            mSrlNews.finishLoadMore(0);
            mSrlNews.setNoMoreData(true);
        }
        mSrlNews.finishRefresh();
        mSrlNews.finishLoadMore(0);
    }

    @Override
    public void hideDialog() {
        mSrlNews.finishRefresh();
        mSrlNews.finishLoadMore(0);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
