package com.example.administrator.idlereader.news.nba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.hupu.HupuNews;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.RegularUtils;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.UIUtils;

public class NbaNewsAdapter extends BaseRecyclerViewAdapter<HupuNews.ResultBean.DataBean> {

    public NbaNewsAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nba_hupu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, final HupuNews.ResultBean.DataBean data) {
        if (holder instanceof ViewHolder) {
            if (data == null) {
                return;
            }
            int weight = Resolution.dipToPx(mContext, 100);
            int height = Resolution.dipToPx(mContext, 80);
            if (data.getThumbs() != null && data.getBadge() == null) {
                ((ViewHolder) holder).llNbaThumbs.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).llNbaDefault.setVisibility(View.GONE);
                ((ViewHolder) holder).tvNbaThumbs.setText(data.getTitle());
                GlideUtils.load(mContext, data.getThumbs().get(0), ((ViewHolder) holder).imgNbaThumbs1);
                GlideUtils.load(mContext, data.getThumbs().get(1), ((ViewHolder) holder).imgNbaThumbs2);
                GlideUtils.load(mContext, data.getThumbs().get(2), ((ViewHolder) holder).imgNbaThumbs3);
                if (data.getLights().equals("0")) {
                    ((ViewHolder) holder).llNbaLightThumbs.setVisibility(View.GONE);
                } else {
                    ((ViewHolder) holder).llNbaLightThumbs.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tvNbaLightThumbs.setText(data.getLights());
                }
                ((ViewHolder) holder).tvNbaCommentThumbs.setText(data.getReplies());
            } else {
                ((ViewHolder) holder).llNbaThumbs.setVisibility(View.GONE);
                ((ViewHolder) holder).llNbaDefault.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tvNba.setText(data.getTitle());
                GlideUtils.load(mContext, data.getImg(), ((ViewHolder) holder).imgNba, weight, height);
                if (data.getLights().equals("0")) {
                    ((ViewHolder) holder).llNbaLight.setVisibility(View.GONE);
                } else {
                    ((ViewHolder) holder).llNbaLight.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tvNbaLight.setText(data.getLights());
                }
                ((ViewHolder) holder).tvNbaComment.setText(data.getReplies());
            }
            if (data.getType() == 1) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtils.startNbaNewsFragment(mContext, data.getNid());
                    }
                });
            }else if (data.getType()==5){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIUtils.startNbaH5Fragment(mContext, data.getNid(),RegularUtils.getTid(data.getLink()));
                    }
                });
            }
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llNbaDefault;
        private ImageView imgNba;
        private TextView tvNba;
        private LinearLayout llNbaLight;
        private TextView tvNbaLight;
        private LinearLayout llNbaComment;
        private TextView tvNbaComment;
        private LinearLayout llNbaThumbs;
        private TextView tvNbaThumbs;
        private ImageView imgNbaThumbs1;
        private ImageView imgNbaThumbs2;
        private ImageView imgNbaThumbs3;
        private LinearLayout llNbaLightThumbs;
        private TextView tvNbaLightThumbs;
        private LinearLayout llNbaCommentThumbs;
        private TextView tvNbaCommentThumbs;

        public ViewHolder(View view) {
            super(view);
            llNbaDefault = (LinearLayout) view.findViewById(R.id.ll_nba_default);
            imgNba = (ImageView) view.findViewById(R.id.img_nba);
            tvNba = (TextView) view.findViewById(R.id.tv_nba);
            llNbaLight = (LinearLayout) view.findViewById(R.id.ll_nba_light);
            tvNbaLight = (TextView) view.findViewById(R.id.tv_nba_light);
            llNbaComment = (LinearLayout) view.findViewById(R.id.ll_nba_comment);
            tvNbaComment = (TextView) view.findViewById(R.id.tv_nba_comment);
            llNbaThumbs = (LinearLayout) view.findViewById(R.id.ll_nba_thumbs);
            tvNbaThumbs = (TextView) view.findViewById(R.id.tv_nba_thumbs);
            imgNbaThumbs1 = (ImageView) view.findViewById(R.id.img_nba_thumbs1);
            imgNbaThumbs2 = (ImageView) view.findViewById(R.id.img_nba_thumbs2);
            imgNbaThumbs3 = (ImageView) view.findViewById(R.id.img_nba_thumbs3);
            llNbaLightThumbs = (LinearLayout) view.findViewById(R.id.ll_nba_light_thumbs);
            tvNbaLightThumbs = (TextView) view.findViewById(R.id.tv_nba_light_thumbs);
            llNbaCommentThumbs = (LinearLayout) view.findViewById(R.id.ll_nba_comment_thumbs);
            tvNbaCommentThumbs = (TextView) view.findViewById(R.id.tv_nba_comment_thumbs);
        }
    }
}
