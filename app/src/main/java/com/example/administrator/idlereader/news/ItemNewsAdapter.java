package com.example.administrator.idlereader.news;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.idlereader.ADetailActivity;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.news.NewsBean;

import java.util.ArrayList;
import java.util.List;

public class ItemNewsAdapter extends BaseRecyclerViewAdapter<NewsBean.Bean> {
    private static final String TAG = "ItemNewsAdapter";
    private List<NewsBean.Bean> datas = new ArrayList<NewsBean.Bean>();

    public ItemNewsAdapter(Context context, @NonNull List<NewsBean.Bean> data, List<NewsBean.Bean> datas) {
        super(context, data);
    }

    public ItemNewsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ItemNewsHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, final NewsBean.Bean data) {
        if (holder instanceof ItemNewsHolder) {
            if (data == null) {
                return;
            }
            if (!TextUtils.isEmpty(data.getTemplate())) {
                ((ItemNewsHolder) holder).itemNews.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemExtra.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemTemplate.setVisibility(View.VISIBLE);
                Glide.with(mContext.getApplicationContext())
                        .load(data.getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgNewsTemplate);
                ((ItemNewsHolder) holder).tvTemplateTitle.setText(data.getTitle());
            } else if (data.getImgextra() != null) {
                ((ItemNewsHolder) holder).itemNews.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemExtra.setVisibility(View.VISIBLE);
                ((ItemNewsHolder) holder).itemTemplate.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).tvExtraTitle.setText(data.getTitle());
                ((ItemNewsHolder) holder).tvExtraSource.setText(data.getSource());
                ((ItemNewsHolder) holder).tvExtraVote.setText(data.getVotecount() + "评论");
                Glide.with(mContext)
                        .load(data.getImgextra().get(0).getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgExtra1);
                Glide.with(mContext)
                        .load(data.getImgextra().get(1).getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgExtra2);
                Glide.with(mContext)
                        .load(data.getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgExtra3);
            } else {
                ((ItemNewsHolder) holder).itemNews.setVisibility(View.VISIBLE);
                ((ItemNewsHolder) holder).itemExtra.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemTemplate.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(data.getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgNewsCover);

                ((ItemNewsHolder) holder).tvNewsTitle.setText(data.getTitle());
                ((ItemNewsHolder) holder).tvNewsSource.setText(data.getSource());
                ((ItemNewsHolder) holder).tvNewsVote.setText(data.getVotecount() + "评论");
            }

            ((ItemNewsHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ADetailActivity.class);
                    intent.putExtra("url", data.getUrl());
                    intent.putExtra("title", data.getSource());
                    mContext.startActivity(intent);
                }
            });

        }
    }

    protected class ItemNewsHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemNews;
        private TextView tvNewsTitle;
        private ImageView imgNewsCover;
        private TextView tvNewsSource;
        private TextView tvNewsVote;
        private LinearLayout itemExtra;
        private TextView tvExtraTitle;
        private ImageView imgExtra1;
        private ImageView imgExtra2;
        private ImageView imgExtra3;
        private TextView tvExtraSource;
        private TextView tvExtraVote;
        private RelativeLayout itemTemplate;
        private ImageView imgNewsTemplate;
        private TextView tvTemplateTitle;

        public ItemNewsHolder(View view) {
            super(view);
            itemNews = (LinearLayout) view.findViewById(R.id.item_news);
            tvNewsTitle = (TextView) view.findViewById(R.id.tv_news_title);
            imgNewsCover = (ImageView) view.findViewById(R.id.img_news_cover);
            tvNewsSource = (TextView) view.findViewById(R.id.tv_news_source);
            tvNewsVote = (TextView) view.findViewById(R.id.tv_news_vote);
            itemExtra = (LinearLayout) view.findViewById(R.id.item_extra);
            tvExtraTitle = (TextView) view.findViewById(R.id.tv_extra_title);
            imgExtra1 = (ImageView) view.findViewById(R.id.img_extra1);
            imgExtra2 = (ImageView) view.findViewById(R.id.img_extra2);
            imgExtra3 = (ImageView) view.findViewById(R.id.img_extra3);
            tvExtraSource = (TextView) view.findViewById(R.id.tv_extra_source);
            tvExtraVote = (TextView) view.findViewById(R.id.tv_extra_vote);
            itemTemplate = (RelativeLayout) view.findViewById(R.id.item_template);
            imgNewsTemplate = (ImageView) view.findViewById(R.id.img_news_template);
            tvTemplateTitle = (TextView) view.findViewById(R.id.tv_template_title);
        }
    }
}
