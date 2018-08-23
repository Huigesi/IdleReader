package com.example.administrator.idlereader.movie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.administrator.idlereader.utils.GlideUtils;

import java.util.List;

public class MovieTopAdapter extends BaseRecyclerViewAdapter<MoviesBean.SubjectsBean> {

    public MovieTopAdapter(Context context) {
        super(context);
    }

    public MovieTopAdapter(Context context, @NonNull List<MoviesBean.SubjectsBean> data) {
        super(context, data);
    }

    public void setData(List<MoviesBean.SubjectsBean> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }

    public void addData(List<MoviesBean.SubjectsBean> datas){
        mList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie_top250,
                parent, false);
        return new MovieTop250ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, final MoviesBean.SubjectsBean data) {
        if (data==null){
            return;
        }
        GlideUtils.load(mContext,data.getImages().getSmall(),((MovieTop250ViewHolder)holder).ivMovieTop);
        ((MovieTop250ViewHolder)holder).tvMovieTopTitle.setText(data.getTitle());
        ((MovieTop250ViewHolder)holder).rlMovieOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_URL,data.getAlt());
                intent.putExtra(WebViewActivity.WEB_TITLE, data.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    protected class MovieTop250ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout rlMovieOn;
        private ImageView ivMovieTop;
        private TextView tvMovieTopTitle;


        public MovieTop250ViewHolder(View view) {
            super(view);
            rlMovieOn = (LinearLayout) view.findViewById(R.id.rl_movie_on);
            ivMovieTop = (ImageView) view.findViewById(R.id.iv_movie_top);
            tvMovieTopTitle = (TextView) view.findViewById(R.id.tv_movie_top_title);

        }
    }
}
