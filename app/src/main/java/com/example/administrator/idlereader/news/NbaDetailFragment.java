package com.example.administrator.idlereader.news;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INbaDetailView;
import com.example.administrator.idlereader.utils.Resolution;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NbaDetailFragment extends Fragment implements INbaDetailView {
    private static final String TAG = "NbaDetailFragment";
    @BindView(R.id.rv_nba_detail)
    RecyclerView mRvNbaDetail;
    Unbinder unbinder;
    public static final String NBA_NID = "NBA_NID";
    @BindView(R.id.srl_nba_detail)
    SwipeRefreshLayout mSrlNbaDetail;
    private NewsPresenter mNewsPresenter;
    private NbaDetailAdapter mNbaDetailAdapter;
    private NbaDetailHeaderView mNbaDetailHeaderView;
    private NbaDetailLightView mNbaDetailLightView;

    public static NbaDetailFragment getInstance() {
        NbaDetailFragment fragment = new NbaDetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nba_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.i(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNbaDetailAdapter = new NbaDetailAdapter(getActivity());
        mNewsPresenter = new NewsPresenter(this);
        mRvNbaDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvNbaDetail.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int line = Resolution.dipToPx(getContext(), 1);
                outRect.set(0, 0, 0, line);
            }
        });

        mNbaDetailHeaderView = new NbaDetailHeaderView(getActivity());
        mNbaDetailLightView = new NbaDetailLightView(getActivity());
        final String nid = getActivity().getIntent().getStringExtra(NBA_NID);
        mNewsPresenter.loadNbaDetail(nid);
        mRvNbaDetail.setAdapter(mNbaDetailAdapter);
        mSrlNbaDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsPresenter.loadNbaDetail(nid);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(NbaDetailNews data) {
        Log.i(TAG, "showData: " + data.getResult().getTitle());
        //mNbaDetailAdapter.setData(, false);
        mNbaDetailHeaderView.setData(data);
        mNbaDetailAdapter.setHeaderView(mNbaDetailHeaderView);
    }

    @Override
    public void showCommentData(NbaNewsComment commentData) {
        mNbaDetailAdapter.setData(commentData.getData(), false);
        if (commentData.getLight_comments()!= null&&commentData.getLight_comments().size()>0) {
            mNbaDetailLightView.setData(commentData);
            mNbaDetailAdapter.setLightCommentView(mNbaDetailLightView);
        }
        if (commentData.getData() == null) {
            mNbaDetailHeaderView.setNomore(true);
        } else {
            mNbaDetailHeaderView.setNomore(false);
        }
    }

    @Override
    public void hideDialog() {
        mSrlNbaDetail.setRefreshing(false);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }
}