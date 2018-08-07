package com.example.administrator.idlereader.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import com.example.administrator.idlereader.bean.NewsBean;
import com.example.administrator.idlereader.untils.Resolution;

import java.util.ArrayList;
import java.util.List;

public class ItemNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsBean.Bean> objects = new ArrayList<NewsBean.Bean>();

    private Context context;

    public ItemNewsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NewsBean.Bean> objects) {
        this.objects = objects;
    }

    public void addData(List<NewsBean.Bean> newObjects) {
        objects.addAll(newObjects);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new ItemNewsHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.footer, parent, false);
            return new FooterHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemNewsHolder) {
            final NewsBean.Bean bean = objects.get(position);
            if (bean == null) {
                return;
            }
            if (!TextUtils.isEmpty(bean.getTemplate())) {
                ((ItemNewsHolder) holder).itemNews.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemExtra.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemTemplate.setVisibility(View.VISIBLE);
                Glide.with(context.getApplicationContext())
                        .load(bean.getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgNewsTemplate);
                ((ItemNewsHolder) holder).tvTemplateTitle.setText(bean.getTitle());
            } else if (bean.getImgextra() != null) {
                ((ItemNewsHolder) holder).itemNews.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemExtra.setVisibility(View.VISIBLE);
                ((ItemNewsHolder) holder).itemTemplate.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).tvExtraTitle.setText(bean.getTitle());
                ((ItemNewsHolder) holder).tvExtraSource.setText(bean.getSource());
                ((ItemNewsHolder) holder).tvExtraVote.setText(bean.getVotecount() + "评论");
                Glide.with(context)
                        .load(bean.getImgextra().get(0).getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgExtra1);
                Glide.with(context)
                        .load(bean.getImgextra().get(1).getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgExtra2);
                Glide.with(context)
                        .load(bean.getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgExtra3);
            } else {
                ((ItemNewsHolder) holder).itemNews.setVisibility(View.VISIBLE);
                ((ItemNewsHolder) holder).itemExtra.setVisibility(View.GONE);
                ((ItemNewsHolder) holder).itemTemplate.setVisibility(View.GONE);
                Glide.with(context)
                        .load(bean.getImgsrc())
                        .asBitmap()
                        .fitCenter().placeholder(R.mipmap.loads)
                        .skipMemoryCache(false)
                        .error(R.mipmap.img_error)
                        .into(((ItemNewsHolder) holder).imgNewsCover);

                ((ItemNewsHolder) holder).tvNewsTitle.setText(bean.getTitle());
                ((ItemNewsHolder) holder).tvNewsSource.setText(bean.getSource());
                ((ItemNewsHolder) holder).tvNewsVote.setText(bean.getVotecount() + "评论");
            }

            ((ItemNewsHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ADetailActivity.class);
                    intent.putExtra("url", bean.getUrl());
                    intent.putExtra("title", bean.getSource());
                    context.startActivity(intent);
                }
            });

        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
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

    protected class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
