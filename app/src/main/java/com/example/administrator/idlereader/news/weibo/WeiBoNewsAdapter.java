package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannedString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.RegularUtils;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.TimeUtils;
import com.example.administrator.idlereader.utils.UIUtils;

import cn.jzvd.JZVideoPlayerStandard;

public class WeiBoNewsAdapter extends BaseRecyclerViewAdapter<WeiBoNews.StatusesData> {
    public ImgAdapter mImgAdapter;

    public WeiBoNewsAdapter(Context context) {
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
    public void onBind(RecyclerView.ViewHolder holder, int position, final WeiBoNews.StatusesData data) {
        if (holder instanceof NewsViewHolder) {
            if (data == null) {
                return;
            }
            int weight = Resolution.dipToPx(mContext, 35);
            GlideUtils.loadCircle(mContext, data.getUser().getProfile_image_url(),
                    ((NewsViewHolder) holder).imgWeiboUser, weight, weight);
            ((NewsViewHolder) holder).tvWeiboUser.setText(data.getUser().getScreen_name());
            ((NewsViewHolder) holder).tvWeiboTime.setText(
                    TimeUtils.prettyTime4(TimeUtils.prettyDate1(data.getCreated_at())));
            ((NewsViewHolder) holder).tvWeiboSource.setText(RegularUtils.getA(data.getSource()));
            SpannableString content = UIUtils.setTextHighLight(mContext, data.getText(), null);
            ((NewsViewHolder) holder).tvWeiboContentText.setText(content);
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
            if (data.getRetweeted_status() != null && data.getRetweeted_status().getUser() != null) {
                ((NewsViewHolder) holder).llWeiboRetweeted.setVisibility(View.VISIBLE);
                String userName = data.getRetweeted_status()
                        .getUser().getName();
                String retWeedText = data.getRetweeted_status()
                        .getText();
                SpannableString retweeted = UIUtils.setTextHighLight(mContext, userName + " : " + retWeedText, userName);
                ((NewsViewHolder) holder).tvRetweetedContent.setText(
                        retweeted);
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
            } else if (data.getRetweeted_status() != null &&
                    data.getRetweeted_status().getUser() == null) {
                ((NewsViewHolder) holder).llWeiboRetweeted.setVisibility(View.VISIBLE);
                ((NewsViewHolder) holder).tvRetweetedContent.setText("抱歉，这条微博已被删除");
            } else {
                ((NewsViewHolder) holder).llWeiboRetweeted.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.startWeiBoDetailFragment(mContext, data.getIdstr());
                }
            });
        }
    }

    public class NewsViewHolder extends Holder {
        private ImageView imgWeiboUser;
        private TextView tvWeiboUser;
        private TextView tvWeiboTime;
        private TextView tvWeiboSource;
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
            tvWeiboSource = (TextView) view.findViewById(R.id.tv_weibo_source);
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
}
