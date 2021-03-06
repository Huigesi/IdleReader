package com.example.administrator.idlereader.news.weibo;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoDetailView;
import com.example.administrator.idlereader.utils.SPreUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class WeiBoDetailFragment extends BaseRecyclerFragment implements IWeiBoDetailView {
    private static final String TAG = "WeiBoDetailFragment";
    private NewsPresenter mNewsPresenter;
    public static final String WEIBO_NID = "WEIBO_NID";
    public String nid;
    private WeiBoDetailAdapter mWeiBoDetailAdapter;
    private WeiBoDetailHeaderView mWeiBoDetailHeaderView;
    private long mMaxId;
    private String mGsid;

    public static WeiBoDetailFragment getInstance() {
        WeiBoDetailFragment fragment = new WeiBoDetailFragment();
        return fragment;
    }

    @Override
    public void init() {
        mNewsPresenter = new NewsPresenter(this);
        mGsid = SPreUtils.getWeiBoUserInfo(SPreUtils.WEIBO_GSID, getActivity());
        nid = getActivity().getIntent().getStringExtra(WEIBO_NID);
        mWeiBoDetailAdapter = new WeiBoDetailAdapter(getActivity());
        mWeiBoDetailHeaderView = new WeiBoDetailHeaderView(getActivity());
        mRvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvNews.setAdapter(mWeiBoDetailAdapter);
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsPresenter.loadWeiBoDetail(nid, mGsid, 0);
            }
        });
        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreComment();
            }
        });
        mNewsPresenter.loadWeiBoDetail(nid, mGsid, 0);
    }

    @Override
    public void hideDialog() {
        mSrlNews.finishRefresh(0);
        mSrlNews.finishLoadMore(0);
    }

    public void loadMoreComment() {
        mNewsPresenter.loadWeiBoDetail(nid, mGsid, mMaxId);
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
        if (data.getStatus().getMblogtypename() != null) {
            mWeiBoDetailHeaderView.setData(data);
            mWeiBoDetailAdapter.setHeaderView(mWeiBoDetailHeaderView);
        }else {
        }
        if (data.getRoot_comments() != null && data.getRoot_comments().size() > 0) {
            mWeiBoDetailAdapter.setData(data.getRoot_comments(), true);
        }
        if (data.getMax_id() == 0) {
            mSrlNews.finishLoadMore(0);
            mSrlNews.setNoMoreData(true);
        } else {
            mMaxId = data.getMax_id();
        }
    }

    @Override
    public void showMoreData(WeiBoDetail data) {
        if (data.getRoot_comments() != null && data.getRoot_comments().size() > 0) {
            mWeiBoDetailAdapter.setData(data.getRoot_comments(), false);
        }
        if (data.getMax_id() == 0) {
            mSrlNews.finishLoadMore(0);
            mSrlNews.setNoMoreData(true);
        } else {
            mMaxId = data.getMax_id();
        }
    }
}
