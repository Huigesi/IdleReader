package com.example.administrator.idlereader.movie;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseEndlessListener;
import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.movie.presenter.MoviesPresenter;
import com.example.administrator.idlereader.movie.view.IMoviesView;
import com.example.administrator.idlereader.utils.swipeBack.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;


public class MovieTop250Activity extends SwipeBackActivity implements IMoviesView {

    private RecyclerView mActivityTop250;
    private MovieTopAdapter mMovieTopAdapter;
    private MoviesPresenter moviesPresenter;
    private BaseEndlessListener<MoviesBean.SubjectsBean> mBaseEndlessListener;
    private List<MoviesBean.SubjectsBean> mMovieTop250 = new ArrayList<MoviesBean.SubjectsBean>();
    private SwipeRefreshLayout srlMovie;
    private int startPage = 0;
    private boolean isNoMore = false;
    private ImageView iv_back;
    private TextView tv_bar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_top250);
        moviesPresenter = new MoviesPresenter(this);
        initView();
        moviesPresenter.loadMovies("top250", null, 0, 30);
        srlMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesPresenter.loadMovies("top250", null, 0, 30);
            }
        });
    }

    private void initView() {
        mActivityTop250 = (RecyclerView) findViewById(R.id.activity_top250);
        srlMovie = (SwipeRefreshLayout) findViewById(R.id.srl_movie);
        srlMovie.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        mMovieTopAdapter = new MovieTopAdapter(this);
        mActivityTop250.setLayoutManager(new GridLayoutManager(this, 3));
        mActivityTop250.setAdapter(mMovieTopAdapter);
        mActivityTop250.setHasFixedSize(true);
        srlMovie = (SwipeRefreshLayout) findViewById(R.id.srl_movie);
        mBaseEndlessListener = new BaseEndlessListener<>(this, mMovieTopAdapter);
        mBaseEndlessListener.setListener(new BaseEndlessListener.EndlessListener() {
            @Override
            public void onLoadData() {
                startPage += 30;
                moviesPresenter.loadMovies("top250", null, startPage, 30);
            }

            @Override
            public boolean shouldLoadData() {
                if (isNoMore) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        mActivityTop250.addOnScrollListener(mBaseEndlessListener);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_bar_title = (TextView) findViewById(R.id.tv_bar_title);
        tv_bar_title.setText("豆瓣TOP250");
    }

    @Override
    public void showMovie(MoviesBean moviesBean) {
        mMovieTop250.clear();
        if (moviesBean.getTotal() == 250) {
            mMovieTop250.addAll(moviesBean.getSubjects());
        }
        mMovieTopAdapter.setData(mMovieTop250, true);
    }

    @Override
    public void showMoreMovie(MoviesBean moviesBean) {
        if (moviesBean.getSubjects().size() == 0) {
            isNoMore = true;
        } else {
            mMovieTopAdapter.setData(moviesBean.getSubjects(), false);
        }

    }

    @Override
    public void hideDialog() {
        srlMovie.setRefreshing(false);
        mBaseEndlessListener.onLoadComplete();
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }
}
