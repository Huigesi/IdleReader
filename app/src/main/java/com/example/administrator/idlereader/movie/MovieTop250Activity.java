package com.example.administrator.idlereader.movie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.movie.presenter.MoviesPresenter;
import com.example.administrator.idlereader.movie.view.IMoviesView;

import java.util.ArrayList;
import java.util.List;

public class MovieTop250Activity extends AppCompatActivity implements IMoviesView {

    private RecyclerView mActivityTop250;
    private MovieTopAdapter mMovieTopAdapter;
    private MoviesPresenter moviesPresenter;
    private List<MoviesBean.SubjectsBean> mMovieTop250 = new ArrayList<MoviesBean.SubjectsBean>();
    private SwipeRefreshLayout srlMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_top250);
        moviesPresenter = new MoviesPresenter(this);
        initView();
        moviesPresenter.loadMovies("top250", null, 30);
        srlMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesPresenter.loadMovies("top250", null, 30);
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
        srlMovie = (SwipeRefreshLayout) findViewById(R.id.srl_movie);
    }

    @Override
    public void showNews(MoviesBean moviesBean) {
        mMovieTop250.clear();
        if (moviesBean.getTotal() == 250) {
            mMovieTop250.addAll(moviesBean.getSubjects());
        }
        mMovieTopAdapter.setData(mMovieTop250);
    }

    @Override
    public void hideDialog() {
        srlMovie.setRefreshing(false);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }
}
