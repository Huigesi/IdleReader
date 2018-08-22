package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.Resolution;

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
        return new ImgAdapter.ViewHolder(view);
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

    public class ViewHolder extends BaseRecyclerViewAdapter.Holder {
        public ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_rv);
        }
    }
}

