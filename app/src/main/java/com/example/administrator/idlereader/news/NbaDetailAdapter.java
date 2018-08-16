package com.example.administrator.idlereader.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.base.BaseRecyclerViewAdapter;
import com.example.administrator.idlereader.bean.hupu.NbaNewsComment;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.Resolution;

public class NbaDetailAdapter extends BaseRecyclerViewAdapter<NbaNewsComment.DataBean>{
    public NbaDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nba_comment, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);

    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position,
                       NbaNewsComment.DataBean data) {
        if (holder instanceof ViewHolder) {
            int weight = Resolution.dipToPx(mContext, 35);
            GlideUtils.load(mContext, data.getHeader(), ((ViewHolder) holder).imgNbaCommentUser,weight,weight);
            ((ViewHolder) holder).tvNbaCommentUser.setText(data.getUser_name());
            ((ViewHolder) holder).tvNbaCommentTime.setText(data.getFormat_time());
            String htmls=data.getContent();
            CharSequence charSequence = Html.fromHtml(htmls);
            ((ViewHolder) holder).tvNbaDetailComment.setText(charSequence);
            ((ViewHolder) holder).tvNbaCommentLight.setText("亮了("+data.getLight_count()+")");
            if (data.getQuote_data() != null) {
                ((ViewHolder) holder).llNbaCommentQuote.setVisibility(View.VISIBLE);
                String quote=data.getQuote_data().getContent();
                CharSequence sequence=Html.fromHtml(quote);
                ((ViewHolder) holder).tvNbaDetailQuoteComment.setText(sequence);
                ((ViewHolder) holder).tvNbaCommentQuoteUser.setText(data.getQuote_data().getUser_name());
            }else {
                ((ViewHolder) holder).llNbaCommentQuote.setVisibility(View.GONE);
            }
        }
    }
    protected class ViewHolder extends Holder{
        private ImageView imgNbaCommentUser;
        private TextView tvNbaCommentUser;
        private TextView tvNbaCommentTime;
        private LinearLayout llNbaCommentQuote;
        private TextView tvNbaCommentQuoteUser;
        private TextView tvNbaDetailQuoteComment;
        private TextView tvNbaDetailComment;
        private LinearLayout llNbaCommentLight;
        private TextView tvNbaCommentLight;

        public ViewHolder(View view) {
            super(view);
            imgNbaCommentUser = (ImageView) view.findViewById(R.id.img_nba_comment_user);
            tvNbaCommentUser = (TextView) view.findViewById(R.id.tv_nba_comment_user);
            tvNbaCommentTime = (TextView) view.findViewById(R.id.tv_nba_comment_time);
            llNbaCommentQuote = (LinearLayout) view.findViewById(R.id.ll_nba_comment_quote);
            tvNbaCommentQuoteUser = (TextView) view.findViewById(R.id.tv_nba_comment_quote_user);
            tvNbaDetailQuoteComment = (TextView) view.findViewById(R.id.wv_nba_detail_quote_comment);
            tvNbaDetailComment = (TextView) view.findViewById(R.id.wv_nba_detail_comment);
            llNbaCommentLight = (LinearLayout) view.findViewById(R.id.ll_nba_comment_light);
            tvNbaCommentLight = (TextView) view.findViewById(R.id.tv_nba_comment_light);
        }
    }
}
