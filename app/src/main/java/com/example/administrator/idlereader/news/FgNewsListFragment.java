package com.example.administrator.idlereader.news;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseEndlessListener;
import com.example.administrator.idlereader.bean.news.NewsBean;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INewsView;

import java.util.List;


public class FgNewsListFragment extends Fragment implements INewsView {

    private NewsPresenter presenter;
    private int type;
    private SwipeRefreshLayout srl_news;
    private RecyclerView rv_news;
    private ItemNewsAdapter adapter;
    private List<NewsBean.Bean> newsBeanList;
    private LinearLayoutManager layoutManager;
    private int startPage = 0;
    private BaseEndlessListener<NewsBean.Bean> mBaseEndlessListener;

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

        return inflater.inflate(R.layout.fg_news_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getInt("type");
        presenter = new NewsPresenter(this);
        rv_news = view.findViewById(R.id.rv_news);
        adapter = new ItemNewsAdapter(getActivity());
        srl_news = view.findViewById(R.id.srl_news);
        srl_news.setColorSchemeColors(Color.parseColor("#ffce3d3a"));
        srl_news.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNews(type, 0);
            }
        });
        presenter.loadNews(type, 0);
        initListener();
    }

    private void initListener() {
        mBaseEndlessListener = new BaseEndlessListener<>(getContext(),adapter);
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
        rv_news.addOnScrollListener(mBaseEndlessListener);
    }

    private void loadMore() {
        startPage += 20;
        presenter.loadNews(type, startPage);
    }

    @Override
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
        Log.i("list", "showNews: " + newsBeanList.size());
        adapter.setData(newsBeanList,true);
        layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv_news.setLayoutManager(layoutManager);
        rv_news.setHasFixedSize(true);
        rv_news.setAdapter(adapter);

    }

    @Override
    public void showMoreNews(NewsBean newsBean) {
        switch (type) {
            case FgNewsFragment.NEWS_TYPE_TOP:
                adapter.setData(newsBean.getTop(),false);
                break;
            case FgNewsFragment.NEWS_TYPE_NBA:
                adapter.setData(newsBean.getNba(),false);
                break;
            case FgNewsFragment.NEWS_TYPE_GAME:
                adapter.setData(newsBean.getGame(),false);
                break;
        }
    }

    @Override
    public void hideDialog() {
        srl_news.setRefreshing(false);
        mBaseEndlessListener.onLoadComplete();
    }

    @Override
    public void showDialog() {
        srl_news.setRefreshing(true);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(getContext(), "加载出错:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}