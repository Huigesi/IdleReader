package com.example.administrator.idlereader.news.weibo;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerFragment;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoView;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.SPreUtils;
import com.example.administrator.idlereader.utils.UIUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.Unbinder;

public class WeiBoFragment extends BaseRecyclerFragment implements IWeiBoView {
    private static final String TAG = "WeiBoFragment";
    Unbinder unbinder;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsPresenter mNewsPresenter;
    private WeiBoNewsAdapter mWeiBoNewsAdapter;
    private int page=1;
    private String mGsid;

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
        if (data != null) {
            if (data.getStatuses() != null && data.getStatuses().size() >0) {
                mWeiBoNewsAdapter.setData(data.getStatuses(), true);
                mWeiBoNewsAdapter.removeHeaderView();
            }else {
                View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.view_nomore, null, false);
                Button btn = (Button) headerView.findViewById(R.id.btn_login);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtils.startWeiBoLoginActivity(getActivity());
                    }
                });
                mWeiBoNewsAdapter.setHeaderView(headerView);
            }
        }
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
        mGsid = SPreUtils.getWeiBoUserInfo(SPreUtils.WEIBO_GSID, getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        mWeiBoNewsAdapter = new WeiBoNewsAdapter(getActivity());
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mGsid = SPreUtils.getWeiBoUserInfo(SPreUtils.WEIBO_GSID, getActivity());
                mNewsPresenter.loadWeibo(String.valueOf(0),mGsid,1);
            }
        });
        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mNewsPresenter.loadWeibo(String.valueOf(0),mGsid,page);
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
        mNewsPresenter.loadWeibo(String.valueOf(0),mGsid,1);
        mRvNews.setAdapter(mWeiBoNewsAdapter);
    }
}
