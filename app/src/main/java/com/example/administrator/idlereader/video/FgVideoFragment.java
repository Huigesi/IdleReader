package com.example.administrator.idlereader.video;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.administrator.idlereader.bean.TodayContentBean;
import com.example.administrator.idlereader.video.presenter.IVideoPresenter;
import com.example.administrator.idlereader.video.presenter.VideoPresenter;
import com.example.administrator.idlereader.video.view.IVideoView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FgVideoFragment extends Fragment implements IVideoView {

    @BindView(R.id.rv_video)
    RecyclerView rv_video;
    @BindView(R.id.srl_video)
    SmartRefreshLayout srl_video;
    Unbinder unbinder;
    private IVideoPresenter iVideoPresenter;
    private ItemVideoAdapter itemVideoAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static FgVideoFragment getInstance() {
        FgVideoFragment fragment = new FgVideoFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iVideoPresenter = new VideoPresenter(this);

        iVideoPresenter.loadVideo(true);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        srl_video.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(
                getResources().getColor(R.color.colorTheme)));
        srl_video.setRefreshFooter(new DefaultsFooter(getActivity()).setFinishDuration(0));
        srl_video.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                iVideoPresenter.loadVideo(true);
            }
        });
        itemVideoAdapter = new ItemVideoAdapter(getActivity());
        srl_video.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                iVideoPresenter.loadVideo(false);
            }
        });
    }

    @Override
    public void showVideo(List<TodayContentBean> todayContentBeans, List<String> videoList) {
        itemVideoAdapter.setData(todayContentBeans, videoList);
        rv_video.setLayoutManager(layoutManager);
        rv_video.setAdapter(itemVideoAdapter);
    }

    @Override
    public void showMoreData(List<TodayContentBean> todayContentBeans, List<String> videoList) {
        itemVideoAdapter.addData(todayContentBeans, videoList);
        if (todayContentBeans==null||todayContentBeans.size() == 0) {
            srl_video.setNoMoreData(true);
        }
        srl_video.finishLoadMore(0);
    }

    @Override
    public void hideDialog() {
        srl_video.finishRefresh(0);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "加载出错:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
