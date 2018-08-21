package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.Resolution;

import cn.jzvd.JZVideoPlayerStandard;

public class WeiboNewsAdapter extends BaseRecyclerViewAdapter<WeiBoNews.StatusesData> {
    public ImgAdapter mImgAdapter;

    public WeiboNewsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weibo, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new NewsViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, WeiBoNews.StatusesData data) {
        if (holder instanceof NewsViewHolder) {
            if (data == null) {
                return;
            }
            int weight = Resolution.dipToPx(mContext, 35);
            GlideUtils.loadCircle(mContext, data.getUser().getProfile_image_url(),
                    ((NewsViewHolder) holder).imgWeiboUser, weight, weight);
            ((NewsViewHolder) holder).tvWeiboUser.setText(data.getUser().getScreen_name());
            ((NewsViewHolder) holder).tvWeiboTime.setText(data.getCreated_at());
            ((NewsViewHolder) holder).tvWeiboContentText.setText(data.getText());
            ((NewsViewHolder) holder).tvWeiboLike.setText(String.valueOf(data.getAttitudes_count()));
            ((NewsViewHolder) holder).tvWeiboComment.setText(String.valueOf(data.getComments_count()));
            ((NewsViewHolder) holder).tvWeiboZhuan.setText(String.valueOf(data.getReposts_count()));
            if (data.getPic_ids() != null && data.getPic_ids().size() > 0) {
                ((NewsViewHolder) holder).llWeiboImg.setVisibility(View.VISIBLE);
                ((NewsViewHolder) holder).rvWeiboImgs.setLayoutManager(new GridLayoutManager(
                        mContext, 3));
                mImgAdapter = new ImgAdapter(mContext);
                mImgAdapter.setData(data.getPic_ids(), true);
                ((NewsViewHolder) holder).rvWeiboImgs.setAdapter(mImgAdapter);
            } else {
                ((NewsViewHolder) holder).llWeiboImg.setVisibility(View.GONE);
            }
            if (data.getPage_info() != null && data.getPage_info().getMedia_info() != null) {
                ((NewsViewHolder) holder).videoWeibo.setVisibility(View.VISIBLE);
                GlideUtils.loadAuto(mContext, data.getPage_info().getPage_pic(),
                        ((NewsViewHolder) holder).videoWeibo.thumbImageView);
                ((NewsViewHolder) holder).videoWeibo.setUp(
                        data.getPage_info().getMedia_info().getMp4_sd_url(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            } else {
                ((NewsViewHolder) holder).videoWeibo.setVisibility(View.GONE);
            }
            if (data.getRetweeted_status() != null) {
                ((NewsViewHolder) holder).llWeiboRetweeted.setVisibility(View.VISIBLE);
                String userName = data.getRetweeted_status()
                        .getUser().getName();
                String retWeedText = data.getRetweeted_status()
                        .getText();
                SpannableString spannableString = new SpannableString(userName+ " : " + retWeedText);
                ForegroundColorSpan span = new ForegroundColorSpan(holder.itemView.getContext()
                        .getResources().getColor(R.color.RoyalBlue));
                spannableString.setSpan(span, 0, userName.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((NewsViewHolder) holder).tvRetweetedContent.setText(spannableString);
                ((NewsViewHolder) holder).tvRetweetedReport.setText("转发 " + data.getRetweeted_status()
                        .getReposts_count());
                ((NewsViewHolder) holder).tvRetweetedComment.setText("评论 " + data.getRetweeted_status()
                        .getComments_count());
                ((NewsViewHolder) holder).tvRetweetedLike.setText("赞 " + data.getRetweeted_status()
                        .getAttitudes_count());
                if (data.getRetweeted_status().getPic_ids() != null &&
                        data.getRetweeted_status().getPic_ids().size() > 0) {
                    ((NewsViewHolder) holder).llWeiboRetweetedImg.setVisibility(View.VISIBLE);
                    mImgAdapter = new ImgAdapter(holder.itemView.getContext());
                    ((NewsViewHolder) holder).rvRetweetedImgs.setAdapter(mImgAdapter);
                    ((NewsViewHolder) holder).rvRetweetedImgs.setLayoutManager(new GridLayoutManager(
                            mContext, 3));
                    mImgAdapter.setData(data.getRetweeted_status().getPic_ids(), true);
                } else {
                    ((NewsViewHolder) holder).llWeiboRetweetedImg.setVisibility(View.GONE);
                }
                if (data.getPage_info() != null && data.getPage_info().getMedia_info() != null) {
                    ((NewsViewHolder) holder).videoRetweetedWeibo.setVisibility(View.VISIBLE);
                    ((NewsViewHolder) holder).videoWeibo.setVisibility(View.GONE);
                    GlideUtils.loadAuto(mContext, data.getPage_info().getPage_pic(),
                            ((NewsViewHolder) holder).videoRetweetedWeibo.thumbImageView);
                    ((NewsViewHolder) holder).videoRetweetedWeibo.setUp(
                            data.getPage_info().getMedia_info().getMp4_sd_url(),
                            JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
                } else {
                    ((NewsViewHolder) holder).videoRetweetedWeibo.setVisibility(View.GONE);
                    ((NewsViewHolder) holder).videoWeibo.setVisibility(View.GONE);
                }
            } else {
                ((NewsViewHolder) holder).llWeiboRetweeted.setVisibility(View.GONE);
            }
        }
    }

    public class NewsViewHolder extends Holder {
        private ImageView imgWeiboUser;
        private TextView tvWeiboUser;
        private TextView tvWeiboTime;
        private TextView tvWeiboContentText;
        private LinearLayout llWeiboImg;
        private RecyclerView rvWeiboImgs;
        private JZVideoPlayerStandard videoWeibo;
        private LinearLayout llWeiboRetweeted;
        private TextView tvRetweetedContent;
        private TextView tvRetweetedReport;
        private TextView tvRetweetedComment;
        private TextView tvRetweetedLike;
        private LinearLayout llWeiboRetweetedImg;
        private RecyclerView rvRetweetedImgs;
        private JZVideoPlayerStandard videoRetweetedWeibo;
        private LinearLayout llWeiboBtns;
        private TextView tvWeiboLike;
        private TextView tvWeiboComment;
        private TextView tvWeiboZhuan;

        public NewsViewHolder(View view) {
            super(view);
            imgWeiboUser = (ImageView) view.findViewById(R.id.img_weibo_user);
            tvWeiboUser = (TextView) view.findViewById(R.id.tv_weibo_user);
            tvWeiboTime = (TextView) view.findViewById(R.id.tv_weibo_time);
            tvWeiboContentText = (TextView) view.findViewById(R.id.tv_weibo_contentText);
            llWeiboImg = (LinearLayout) view.findViewById(R.id.ll_weibo_img);
            rvWeiboImgs = (RecyclerView) view.findViewById(R.id.rv_weibo_imgs);
            videoWeibo = (JZVideoPlayerStandard) view.findViewById(R.id.video_weibo);
            llWeiboRetweeted = (LinearLayout) view.findViewById(R.id.ll_weibo_retweeted);
            tvRetweetedContent = (TextView) view.findViewById(R.id.tv_retweeted_content);
            tvRetweetedReport = (TextView) view.findViewById(R.id.tv_retweeted_report);
            tvRetweetedComment = (TextView) view.findViewById(R.id.tv_retweeted_comment);
            tvRetweetedLike = (TextView) view.findViewById(R.id.tv_retweeted_like);
            llWeiboRetweetedImg = (LinearLayout) view.findViewById(R.id.ll_weibo_retweeted_img);
            rvRetweetedImgs = (RecyclerView) view.findViewById(R.id.rv_retweeted_imgs);
            videoRetweetedWeibo = (JZVideoPlayerStandard) view.findViewById(R.id.video_retweeted_weibo);
            llWeiboBtns = (LinearLayout) view.findViewById(R.id.ll_weibo_btns);
            tvWeiboLike = (TextView) view.findViewById(R.id.tv_weibo_like);
            tvWeiboComment = (TextView) view.findViewById(R.id.tv_weibo_comment);
            tvWeiboZhuan = (TextView) view.findViewById(R.id.tv_weibo_zhuan);
        }
    }

    public class ImgAdapter extends BaseRecyclerViewAdapter<String> {
        public ImgAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_img, null, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        }

        @Override
        public void onBind(RecyclerView.ViewHolder holder, int position, String data) {
            if (holder instanceof ViewHolder) {
                int weight = Resolution.dipToPx(this.mContext, 120);
                String imgUrl = Api.IMG_WEIBO_WAP360 + data + ".jpg";
                GlideUtils.load(mContext, imgUrl, ((ViewHolder) holder).mImageView, weight, weight);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                int margin4 = Resolution.dipToPx(mContext, 3);
                params.setMargins(0, 0, margin4, margin4);
                holder.itemView.setLayoutParams(params);
            }
        }

        public class ViewHolder extends Holder {
            public ImageView mImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.img_rv);
            }
        }
    }
}
