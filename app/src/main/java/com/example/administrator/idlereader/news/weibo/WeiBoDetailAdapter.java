package com.example.administrator.idlereader.news.weibo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.RegularUtils;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.TimeUtils;
import com.example.administrator.idlereader.utils.UIUtils;

import java.text.ParseException;

public class WeiBoDetailAdapter extends BaseRecyclerViewAdapter<WeiBoDetail.RootCommentsData> {

    public WeiBoDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weibo_comment, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new WeiBoViewHolder(view);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position, final WeiBoDetail.RootCommentsData data) {
        if (holder instanceof WeiBoViewHolder) {
            if (data == null) {
                return;
            }
            int weight = Resolution.dipToPx(mContext, 35);
            GlideUtils.loadCircle(mContext, data.getUser().getProfile_image_url(),
                    ((WeiBoViewHolder) holder).imgWeiboUser, weight, weight);
            ((WeiBoViewHolder) holder).imgWeiboUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.startSpaceFragment(mContext,data.getUser().getIdstr());
                }
            });
            ((WeiBoViewHolder) holder).tvWeiboUser.setText(data.getUser().getScreen_name());
            try {
                ((WeiBoViewHolder) holder).tvWeiboTime.setText(
                        TimeUtils.prettyTime4(TimeUtils.prettyDate1(data.getCreated_at())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SpannableString commentText = UIUtils.setTextHighLight(mContext, data.getText(),
                    null);
            ((WeiBoViewHolder) holder).tvWeiboCommenttext.setText(commentText);
            if (data.getLike_counts() == 0) {
                ((WeiBoViewHolder) holder).tvWeiboCommentLike.setText("");
            } else {
                ((WeiBoViewHolder) holder).tvWeiboCommentLike.setText(data.getLike_counts() + "");
            }
            if (data.getComments() != null && data.getComments().size() > 0) {
                ((WeiBoViewHolder) holder).llWeiboCommentReply.setVisibility(View.VISIBLE);
                SpannableString reply1 = UIUtils.setTextHighLight(mContext,
                        data.getComments().get(0).getUser().getName() + " "
                                + data.getComments().get(0).getText(),
                        data.getComments().get(0).getUser().getName());
                ((WeiBoViewHolder) holder).tvWeiboCommentReply1.setText(reply1);
                if (data.getComments().size() > 1) {
                    ((WeiBoViewHolder) holder).tvWeiboCommentReply2.setVisibility(View.VISIBLE);
                    SpannableString reply2 = UIUtils.setTextHighLight(mContext,
                            data.getComments().get(1).getUser().getName() + " "
                                    + data.getComments().get(1).getText(),
                            data.getComments().get(1).getUser().getName());
                    ((WeiBoViewHolder) holder).tvWeiboCommentReply2.setText(reply2);
                    if (data.getMore_info() != null) {
                        ((WeiBoViewHolder) holder).tvWeiboCommentReplycount.setVisibility(View.VISIBLE);
                        ((WeiBoViewHolder) holder).tvWeiboCommentReplycount.setText(
                                data.getMore_info().getText());
                    } else {
                        ((WeiBoViewHolder) holder).tvWeiboCommentReplycount.setVisibility(View.GONE);
                    }
                } else {
                    ((WeiBoViewHolder) holder).tvWeiboCommentReply2.setVisibility(View.GONE);
                }
            } else {
                ((WeiBoViewHolder) holder).llWeiboCommentReply.setVisibility(View.GONE);
            }
        }
    }

    public class WeiBoViewHolder extends Holder {
        private ImageView imgWeiboUser;
        private TextView tvWeiboUser;
        private TextView tvWeiboTime;
        private TextView tvWeiboSource;
        private TextView tvWeiboCommentLike;
        private TextView tvWeiboCommenttext;
        private LinearLayout llWeiboCommentReply;
        private TextView tvWeiboCommentReply1;
        private TextView tvWeiboCommentReply2;
        private TextView tvWeiboCommentReplycount;

        public WeiBoViewHolder(View view) {
            super(view);
            imgWeiboUser = (ImageView) view.findViewById(R.id.img_weibo_user);
            tvWeiboUser = (TextView) view.findViewById(R.id.tv_weibo_user);
            tvWeiboTime = (TextView) view.findViewById(R.id.tv_weibo_time);
            tvWeiboSource = (TextView) view.findViewById(R.id.tv_weibo_source);
            tvWeiboCommentLike = (TextView) view.findViewById(R.id.tv_weibo_comment_like);
            tvWeiboCommenttext = (TextView) view.findViewById(R.id.tv_weibo_commenttext);
            llWeiboCommentReply = (LinearLayout) view.findViewById(R.id.ll_weibo_comment_reply);
            tvWeiboCommentReply1 = (TextView) view.findViewById(R.id.tv_weibo_comment_reply1);
            tvWeiboCommentReply2 = (TextView) view.findViewById(R.id.tv_weibo_comment_reply2);
            tvWeiboCommentReplycount = (TextView) view.findViewById(R.id.tv_weibo_comment_replycount);
        }
    }
}

