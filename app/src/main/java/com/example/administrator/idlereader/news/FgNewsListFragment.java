package com.example.administrator.idlereader.news;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.idlereader.bean.news.News163;
import com.example.administrator.idlereader.utils.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INewsView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


public class FgNewsListFragment extends Fragment implements INewsView {

    private NewsPresenter presenter;
    private int type;
    private SmartRefreshLayout srl_news;
    private RecyclerView rv_news;
    private ItemNewsAdapter adapter;
    private List<NewsBean.Bean> newsBeanList;
    private List<News163> mNews163List;
    private LinearLayoutManager layoutManager;
    private int startPage = 0;

    public static FgNewsListFragment newInstance(int type) {
        Bundle args = new Bundle();
        FgNewsListFragment fragment = new FgNewsListFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fg_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        presenter = new NewsPresenter(this);
        rv_news = view.findViewById(R.id.rv_news);
        adapter = new ItemNewsAdapter(getActivity());
        srl_news = view.findViewById(R.id.srl_news);
        srl_news.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(
                getResources().getColor(R.color.colorTheme)));
        srl_news.setRefreshFooter(new DefaultsFooter(getActivity()).setFinishDuration(0));
        srl_news.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.loadNews(type, 0);
            }
        });
        srl_news.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }
        });
        presenter.loadNews(type, 0);
    }

    private void loadMore() {
        startPage += 20;
        presenter.loadNews(type, startPage);
    }

   /* @Override
    public void showNews(final NewsBean newsBean) {
        switch (type) {
            case FgNewsFragment.NEWS_TYPE_TOP:
                newsBeanList = newsBean.getTop();
                break;
            case FgNewsFragment.NEWS_TYPE_NBA:
                newsBeanList = newsBean.getNba();
                break;
            case FgNewsFragment.NEWS_TYPE_GAME:
                newsBeanList = newsBean.getGame();
                break;
        }
        adapter.setData(newsBeanList, true);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv_news.setLayoutManager(layoutManager);
        rv_news.setHasFixedSize(true);
        rv_news.setAdapter(adapter);

    }*/

    /*@Override
    public void showMoreNews(NewsBean newsBean) {
        switch (type) {
            case FgNewsFragment.NEWS_TYPE_TOP:
                adapter.setData(newsBean.getTop(), false);
                break;
            case FgNewsFragment.NEWS_TYPE_NBA:
                adapter.setData(newsBean.getNba(), false);
                break;
            case FgNewsFragment.NEWS_TYPE_GAME:
                adapter.setData(newsBean.getGame(), false);
                break;
        }
        srl_news.finishLoadMore(0);
        if (newsBean == null) {
            srl_news.setNoMoreData(true);
        }
    }*/

    @Override
    public void showNews(List<News163> data) {
        adapter.setData(data, true);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv_news.setLayoutManager(layoutManager);
        rv_news.setHasFixedSize(true);
        rv_news.setAdapter(adapter);
    }

    @Override
    public void showMoreNews(List<News163> data) {
        adapter.setData(data, false);
        srl_news.finishLoadMore(0);
        if (data == null) {
            srl_news.setNoMoreData(true);
        }
    }

    @Override
    public void hideDialog() {
        srl_news.finishRefresh(0);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "加载出错:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}