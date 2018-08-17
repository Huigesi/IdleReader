package com.example.administrator.idlereader.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.idlereader.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseEndlessListener;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INBAView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HupuNBAFragment extends Fragment implements INBAView {
    private static final String TAG = "HupuNBAFragment";
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    Unbinder unbinder;
    @BindView(R.id.srl_news)
    SmartRefreshLayout mSrlNews;
    private NewsPresenter mNewsPresenter;
    private NbaNewsAdapter mNbaNewsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private BaseEndlessListener<HupuNews.ResultBean.DataBean> mBaseEndlessListener;
    private List<HupuNews.ResultBean.DataBean> mResults = new ArrayList<>();
    private int mCount;
    public String mNid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_news_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static HupuNBAFragment getInstance() {
        HupuNBAFragment fragment = new HupuNBAFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNewsPresenter = new NewsPresenter(this);
        mNbaNewsAdapter = new NbaNewsAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mSrlNews.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(
                getResources().getColor(R.color.colorTheme)));
        mSrlNews.setRefreshFooter(new DefaultsFooter(getActivity()).setFinishDuration(0));
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsPresenter.loadNbaNews("",0);
            }
        });
        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }
        });
        mNewsPresenter.loadNbaNews("", 0);
        mRvNews.setLayoutManager(mLinearLayoutManager);
        mRvNews.setHasFixedSize(true);
        mRvNews.setAdapter(mNbaNewsAdapter);
        //initListener();
    }

    /*private void initListener() {
        mBaseEndlessListener = new BaseEndlessListener<>(getContext(), mNbaNewsAdapter);
        mBaseEndlessListener.setListener(new BaseEndlessListener.EndlessListener() {
            @Override
            public void onLoadData() {
                loadMore();
            }

            @Override
            public boolean shouldLoadData() {
                return true;
            }
        });
        mRvNews.addOnScrollListener(mBaseEndlessListener);
    }*/

    private void loadMore() {
        mCount += 20;
        mNewsPresenter.loadNbaNews(mNid, mCount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(HupuNews hupuNews) {
        mResults = hupuNews.getResult().getData();
        mNid = mResults.get(mResults.size() - 1).getNid();
        mNbaNewsAdapter.setData(mResults, true);
    }

    @Override
    public void showMoreData(HupuNews hupuNews) {
        mResults = hupuNews.getResult().getData();
        mNid = mResults.get(mResults.size() - 1).getNid();
        mNbaNewsAdapter.setData(mResults, false);
        if (mResults == null || mResults.size() == 0) {
            mSrlNews.setNoMoreData(true);
        }
        mSrlNews.finishLoadMore(0);
    }

    @Override
    public void hideDialog() {
        mSrlNews.finishRefresh(0);
        mSrlNews.finishLoadMore(0);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "加载出错:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
