package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.bigImgViewPager.ImagePreview;
import com.example.administrator.idlereader.utils.bigImgViewPager.bean.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ImgAdapter extends BaseRecyclerViewAdapter<String> {
    private List<ImageInfo> imageInfoList;
    private List<String> gifIds;

    public List<String> getGifIds() {
        return gifIds;
    }

    public void setGifIds(List<String> gifIds) {
        this.gifIds = gifIds;
    }

    public ImgAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ImgAdapter.ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, final int position, String data) {
        if (holder instanceof ViewHolder) {
            int weight = Resolution.dipToPx(this.mContext, 120);
            initPictureData(data);
            String url;
            if (gifIds.contains(data)) {
                url = Api.IMG_WEIBO_ORIGINAL_GIF + data + ".gif";
                GlideUtils.loadGif(mContext, url, ((ViewHolder) holder).mImageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePreview
                                .getInstance()
                                .setContext(mContext)
                                .setIndex(position)
                                .setImageInfoList(imageInfoList)
                                .setShowDownButton(true)
                                .setGif(true)
                                .setLoadStrategy(ImagePreview.LoadStrategy.NetworkAuto)
                                .setFolderName("IdleReader")
                                .setScaleLevel(1, 3, 8)
                                .setZoomTransitionDuration(300)
                                .start();
                    }
                });
            } else {
                url = Api.IMG_WEIBO_WAP360 + data + ".jpg";
                GlideUtils.load(mContext, url, ((ViewHolder) holder).mImageView, weight, weight);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImagePreview
                                .getInstance()
                                .setContext(mContext)
                                .setIndex(position)
                                .setImageInfoList(imageInfoList)
                                .setShowDownButton(true)
                                .setGif(false)
                                .setLoadStrategy(ImagePreview.LoadStrategy.NetworkAuto)
                                .setFolderName("IdleReader")
                                .setScaleLevel(1, 3, 8)
                                .setZoomTransitionDuration(300)
                                .start();
                    }
                });
            }

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            int margin4 = Resolution.dipToPx(mContext, 3);
            params.setMargins(0, 0, margin4, margin4);
            holder.itemView.setLayoutParams(params);
        }
    }

    public void initPictureData(String data) {
        imageInfoList = new ArrayList<>();
        ImageInfo imageInfo;
        for (String image : mList) {
            String OrlimgUrl;
            String thumbnail;
            if (gifIds.contains(data)) {
                OrlimgUrl = Api.IMG_WEIBO_ORIGINAL_GIF + image + ".gif";
                thumbnail = Api.IMG_WEIBO_ORIGINAL_GIF + image + ".gif";
            } else {
                OrlimgUrl = Api.IMG_WEIBO_ORIGINAL + image + ".jpg";
                thumbnail = Api.IMG_WEIBO_WAP360 + image + ".jpg";
            }
            imageInfo = new ImageInfo();
            imageInfo.setOriginUrl(OrlimgUrl);// 原图
            imageInfo.setThumbnailUrl(
                    thumbnail);// 缩略图，实际使用中，根据需求传入缩略图路径。如果没有缩略图url，可以将两项设置为一样，并隐藏查看原图按钮即可。
            imageInfoList.add(imageInfo);
            imageInfo = null;
        }
    }

    public class ViewHolder extends BaseRecyclerViewAdapter.Holder {
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_rv);
        }
    }
}

