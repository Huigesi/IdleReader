package com.example.administrator.idlereader.movie;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseEndlessListener;
import com.example.administrator.idlereader.bean.MoviesBean;
import com.example.administrator.idlereader.movie.presenter.MoviesPresenter;
import com.example.administrator.idlereader.movie.view.IMoviesView;

import java.util.ArrayList;
import java.util.List;

public class ItemMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IMoviesView {
    private static final int TYPE_MOVIE_ON = 119;
    private static final int TYPE_MOVIE_TOP250 = 120;
    private MovieOnAdapter mMovieOnAdapter;
    private MovieTopAdapter mMovieTopAdapter;
    private BaseEndlessListener<MoviesBean.SubjectsBean> mBaseEndlessListener;

    public BaseEndlessListener<MoviesBean.SubjectsBean> getBaseEndlessListener() {
        return mBaseEndlessListener;
    }

    private MoviesPresenter moviesPresenter;
    private int startPage = 0;
    private String city = "广州";


    private List<MoviesBean.SubjectsBean> mMovieOn = new ArrayList<MoviesBean.SubjectsBean>();
    private List<MoviesBean.SubjectsBean> mMovieTop250 = new ArrayList<MoviesBean.SubjectsBean>();

    private Context context;

    public ItemMovieAdapter(Context context) {

        this.context = context;
    }

    public void setData(List<MoviesBean.SubjectsBean> mMovieOn, List<MoviesBean.SubjectsBean> mMovieTop250) {
        this.mMovieOn = mMovieOn;
        this.mMovieTop250 = mMovieTop250;
        notifyDataSetChanged();
    }

    public void addMovieOnData(List<MoviesBean.SubjectsBean> mMovieOn) {
        mMovieOn.addAll(mMovieOn);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getLayout(viewType), parent, false);
        if (viewType == TYPE_MOVIE_ON) {
            return new MovieOnViewHolder(view);
        } else {
            return new MovieTop250ViewHolder(view);
        }
    }

    public int getLayout(int viewType) {
        if (viewType == TYPE_MOVIE_ON) {
            return R.layout.item_movieon;
        } else if (viewType == TYPE_MOVIE_TOP250) {
            return R.layout.item_movie250;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_MOVIE_TOP250;
        } else if (position == 1) {
            return TYPE_MOVIE_ON;
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        moviesPresenter = new MoviesPresenter(this);
        if (holder instanceof MovieOnViewHolder) {
            mMovieOnAdapter = new MovieOnAdapter(context, mMovieOn);
            ((MovieOnViewHolder) holder).rvItemMovieOn.setLayoutManager(new
                    LinearLayoutManager(context));
            ((MovieOnViewHolder) holder).rvItemMovieOn.setAdapter(mMovieOnAdapter);
            mBaseEndlessListener = new BaseEndlessListener<>(context, mMovieOnAdapter);
            mBaseEndlessListener.setListener(new BaseEndlessListener.EndlessListener() {
                @Override
                public void onLoadData() {
                    if (mMovieOn.size() > 0) {
                        startPage += 20;
                        moviesPresenter.loadMovies("in_theaters", city,
                                startPage, 10);
                    }
                }

                @Override
                public boolean shouldLoadData() {
                    return true;
                }
            });
            ((MovieOnViewHolder) holder).rvItemMovieOn.addOnScrollListener(mBaseEndlessListener);
        } else if (holder instanceof MovieTop250ViewHolder) {
            mMovieTopAdapter = new MovieTopAdapter(context, mMovieTop250);
            ((MovieTop250ViewHolder) holder).rvItemMovieTop.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            ((MovieTop250ViewHolder) holder).rvItemMovieTop.setAdapter(mMovieTopAdapter);
            ((MovieTop250ViewHolder) holder).mTvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieTop250Activity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void showMovie(MoviesBean moviesBean) {

    }

    @Override
    public void showMoreMovie(MoviesBean moviesBean) {
        if (moviesBean.getSubjects().size() == 0) {
            mBaseEndlessListener.onNomore();
        } else {
            mMovieOn.addAll(moviesBean.getSubjects());
            notifyDataSetChanged();
        }
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {

    }

    protected class MovieOnViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvItemMovieOn;

        public MovieOnViewHolder(View view) {
            super(view);
            rvItemMovieOn = (RecyclerView) view.findViewById(R.id.rv_item_movie);
        }
    }

    protected class MovieTop250ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvItemMovieTop;
        private TextView mTvMore;


        public MovieTop250ViewHolder(View view) {
            super(view);
            rvItemMovieTop = (RecyclerView) view.findViewById(R.id.rv_item_movie);
            mTvMore = (TextView) view.findViewById(R.id.tv_more);

        }
    }
}
