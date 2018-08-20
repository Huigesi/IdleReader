package com.example.administrator.idlereader.news.weibo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.idlereader.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoView;
import com.example.administrator.idlereader.utils.Resolution;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeiboFragment extends Fragment implements IWeiBoView {
    private static final String TAG = "WeiboFragment";
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.srl_news)
    SmartRefreshLayout mSrlNews;
    Unbinder unbinder;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsPresenter mNewsPresenter;
    private WeiboNewsAdapter mWeiboNewsAdapter;

    public static WeiboFragment getInstance() {
        WeiboFragment fragment = new WeiboFragment();
        return fragment;
    }
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
        mLinearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mWeiboNewsAdapter = new WeiboNewsAdapter(getActivity());
        mSrlNews.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(
                getResources().getColor(R.color.colorTheme)));
        mSrlNews.setRefreshFooter(new DefaultsFooter(getActivity()).setFinishDuration(0));
        mRvNews.setLayoutManager(mLinearLayoutManager);
        final int line = Resolution.dipToPx(getActivity(), 5);
        mRvNews.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0,0,0,line);
            }
        });
        mRvNews.setHasFixedSize(true);
        mNewsPresenter.loadWeibo(String.valueOf(0));
        mRvNews.setAdapter(mWeiboNewsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Log.i(TAG, "showErrorMsg: "+throwable.getMessage());
    }

    @Override
    public void showData(WeiBoNews data) {
        Log.i(TAG, "showData: "+data.getStatuses().get(0).getText());
        mWeiboNewsAdapter.setData(data.getStatuses(), true);
    }

    @Override
    public void showMoreData(WeiBoNews moreData) {

    }
}
