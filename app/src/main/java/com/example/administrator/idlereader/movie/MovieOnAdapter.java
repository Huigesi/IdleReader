package com.example.administrator.idlereader.movie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.idlereader.WebViewActivity;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.MoviesBean;

import java.util.List;

public class MovieOnAdapter extends BaseRecyclerViewAdapter<MoviesBean.SubjectsBean> {
    private static final String TAG = "MovieOnAdapter";

    public MovieOnAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie_on, parent, false);
        return new MovieOnViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, final MoviesBean.SubjectsBean data) {
        if (data == null) {
            return;
        }
        Glide.with(mContext)
                .load(data.getImages().getSmall())
                .into(((MovieOnViewHolder) holder).ivMovieOn);
        ((MovieOnViewHolder) holder).tvMovieOnTitle.setText(data.getTitle());
        String directors = "";
        for (int i = 0; i < data.getDirectors().size(); i++) {
            if (i == data.getDirectors().size() - 1) {
                directors += data.getDirectors().get(i).getName();
            } else {
                directors += data.getDirectors().get(i).getName() + "/";
            }
        }
        ((MovieOnViewHolder) holder).tvMovieOnDirectors.setText("导演：" + directors);
        String casts = "";

        if (data.getCasts().size() != 0) {
            for (int i = 0; i < data.getCasts().size(); i++) {
                if (i == data.getCasts().size() - 1) {
                    casts += data.getCasts().get(i).getName();
                } else {
                    casts += data.getCasts().get(i).getName() + "/";
                }
            }
            ((MovieOnViewHolder) holder).tvMovieOnCasts.setText(casts);
        } else {
            ((MovieOnViewHolder) holder).tvMovieOnCasts.setText("主演：佚名");
        }

        String gen = "";
        for (int i = 0; i < data.getGenres().size(); i++) {
            if (i == data.getGenres().size() - 1) {
                gen += data.getGenres().get(i);
            } else {
                gen += data.getGenres().get(i) + "/";
            }
        }
        ((MovieOnViewHolder) holder).tvMovieOnGenres.setText("类型：" + gen);
        if (data.getRating().getAverage()==0.0) {
            ((MovieOnViewHolder) holder).tvMovieOnRating.setText("评分：" + "暂无评分");
        }else {
            ((MovieOnViewHolder) holder).tvMovieOnRating.setText("评分：" + data.getRating().getAverage());

        }
        ((MovieOnViewHolder) holder).rvMovieOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", data.getAlt());
                intent.putExtra("title", data.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class MovieOnViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMovieOn;
        private TextView tvMovieOnTitle;
        private TextView tvMovieOnDirectors;
        private TextView tvMovieOnCasts;
        private TextView tvMovieOnGenres;
        private TextView tvMovieOnRating;
        private LinearLayout rvMovieOn;

        public MovieOnViewHolder(View view) {
            super(view);
            ivMovieOn = (ImageView) view.findViewById(R.id.iv_movie_on);
            tvMovieOnTitle = (TextView) view.findViewById(R.id.tv_movie_on_title);
            tvMovieOnDirectors = (TextView) view.findViewById(R.id.tv_movie_on_directors);
            tvMovieOnCasts = (TextView) view.findViewById(R.id.tv_movie_on_casts);
            tvMovieOnGenres = (TextView) view.findViewById(R.id.tv_movie_on_genres);
            tvMovieOnRating = (TextView) view.findViewById(R.id.tv_movie_on_rating);
            rvMovieOn = (LinearLayout) view.findViewById(R.id.rl_movie_on);
        }
    }
}
