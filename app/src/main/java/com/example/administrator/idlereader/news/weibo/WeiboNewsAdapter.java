package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class WeiboNewsAdapter extends BaseRecyclerViewAdapter<WeiBoNews.StatusesData>{
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
            GlideUtils.loadCircle(mContext,data.getUser().getProfile_image_url(),
                    ((NewsViewHolder) holder).imgWeiboUser,weight,weight);
            ((NewsViewHolder) holder).tvWeiboUser.setText(data.getUser().getScreen_name());
            ((NewsViewHolder) holder).tvWeiboTime.setText(data.getCreated_at());
            ((NewsViewHolder) holder).tvWeiboContentText.setText(data.getText());
            ((NewsViewHolder) holder).tvWeiboLike.setText(String.valueOf(data.getAttitudes_count()));
            ((NewsViewHolder) holder).tvWeiboComment.setText(String.valueOf(data.getComments_count()));
            ((NewsViewHolder) holder).tvWeiboZhuan.setText(String.valueOf(data.getReposts_count()));
            if (data.getPic_ids() != null && data.getPic_ids().size() > 0) {
                ((NewsViewHolder) holder).llWeiboImg.setVisibility(View.VISIBLE);
                ((NewsViewHolder) holder).rvWeiboImgs.setLayoutManager(new GridLayoutManager(
                        mContext,3));
                mImgAdapter = new ImgAdapter(mContext);
                mImgAdapter.setData(data.getPic_ids(), true);
                ((NewsViewHolder) holder).rvWeiboImgs.setAdapter(mImgAdapter);
            }else {
                ((NewsViewHolder) holder).llWeiboImg.setVisibility(View.GONE);
            }
            if (data.getPage_info()!=null&&data.getPage_info().getMedia_info() != null) {
                ((NewsViewHolder) holder).mVideo.setVisibility(View.VISIBLE);
                GlideUtils.loadAuto(mContext,data.getPage_info().getPage_pic(),
                        ((NewsViewHolder) holder).mVideo.thumbImageView);
                ((NewsViewHolder) holder).mVideo.setUp(
                        data.getPage_info().getMedia_info().getMp4_hd_url(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            }else {
                ((NewsViewHolder) holder).mVideo.setVisibility(View.GONE);
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
        private LinearLayout llWeiboBtns;
        private TextView tvWeiboLike;
        private TextView tvWeiboComment;
        private TextView tvWeiboZhuan;
        private JZVideoPlayerStandard mVideo;

        public NewsViewHolder(View view) {
            super(view);
            imgWeiboUser = (ImageView) view.findViewById(R.id.img_weibo_user);
            tvWeiboUser = (TextView) view.findViewById(R.id.tv_weibo_user);
            tvWeiboTime = (TextView) view.findViewById(R.id.tv_weibo_time);
            tvWeiboContentText = (TextView) view.findViewById(R.id.tv_weibo_contentText);
            llWeiboImg = (LinearLayout) view.findViewById(R.id.ll_weibo_img);
            rvWeiboImgs = (RecyclerView) view.findViewById(R.id.rv_weibo_imgs);
            llWeiboBtns = (LinearLayout) view.findViewById(R.id.ll_weibo_btns);
            tvWeiboLike = (TextView) view.findViewById(R.id.tv_weibo_like);
            tvWeiboComment = (TextView) view.findViewById(R.id.tv_weibo_comment);
            tvWeiboZhuan = (TextView) view.findViewById(R.id.tv_weibo_zhuan);
            mVideo = (JZVideoPlayerStandard) view.findViewById(R.id.video_weibo);
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
                GlideUtils.load(mContext,imgUrl,((ViewHolder) holder).mImageView,weight,weight);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
                int margin4 = Resolution.dipToPx(mContext, 3);
                params.setMargins(0, 0, margin4, margin4);
                holder.itemView.setLayoutParams(params);
            }
        }
        public class ViewHolder extends Holder{
            public ImageView mImageView;
            public ViewHolder(View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.img_rv);
            }
        }
    }
}
