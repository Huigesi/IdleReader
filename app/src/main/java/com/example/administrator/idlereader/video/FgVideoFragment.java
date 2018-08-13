package com.example.administrator.idlereader.video;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseEndlessListener;
import com.example.administrator.idlereader.bean.TodayContentBean;
import com.example.administrator.idlereader.video.presenter.IVideoPresenter;
import com.example.administrator.idlereader.video.presenter.VideoPresenter;
import com.example.administrator.idlereader.video.view.IVideoView;

import java.util.List;


public class FgVideoFragment extends Fragment  implements IVideoView {

    private IVideoPresenter iVideoPresenter;
    private RecyclerView rv_video;
    private ItemVideoAdapter itemVideoAdapter;
    private SwipeRefreshLayout srl_video;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_video,container,false);
    }

    public static FgVideoFragment getInstance() {
        FgVideoFragment fragment = new FgVideoFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iVideoPresenter = new VideoPresenter(this);
        rv_video = view.findViewById(R.id.rv_video);
        srl_video = view.findViewById(R.id.srl_video);
        srl_video.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        iVideoPresenter.loadVideo(true);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        srl_video.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iVideoPresenter.loadVideo(true);
            }
        });
        itemVideoAdapter = new ItemVideoAdapter(getActivity());
        rv_video.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&
                        (layoutManager.findLastVisibleItemPosition()+1)==layoutManager.getItemCount()) {
                    iVideoPresenter.loadVideo(false);
                }
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
        itemVideoAdapter.addData(todayContentBeans,videoList);
    }

    @Override
    public void hideDialog() {
        srl_video.setRefreshing(false);
    }

    @Override
    public void showDialog() {
        srl_video.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "加载出错:"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
