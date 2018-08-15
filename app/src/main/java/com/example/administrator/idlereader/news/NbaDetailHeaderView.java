package com.example.administrator.idlereader.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.utils.GlideUtils;

import butterknife.BindView;

public class NbaDetailHeaderView extends LinearLayout {

    @BindView(R.id.tv_nba_detail_title)
    TextView mTvNbaDetailTitle;
    @BindView(R.id.tv_nba_detail_title_time)
    TextView mTvNbaDetailTitleTime;
    @BindView(R.id.tv_nba_detail_title_origin)
    TextView mTvNbaDetailTitleOrigin;
    @BindView(R.id.rl_detail_title)
    LinearLayout mRlDetailTitle;
    @BindView(R.id.wv_nba_detail_content)
    WebView mWvNbaDetailContent;
    @BindView(R.id.ll_nba_detail_content)
    LinearLayout mLlNbaDetailContent;
    @BindView(R.id.img_nba_detail_content)
    ImageView mImgNbaDetailContent;

    private NbaDetailNews mNbaDetailNews;

    public NbaDetailHeaderView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_nba_detail, this, false);

    }

    public void setData(NbaDetailNews data) {
        mNbaDetailNews = data;
        refreshUI();
    }

    private void refreshUI() {
        if (mNbaDetailNews == null) {
            return;
        }
        GlideUtils.load(getContext(),mNbaDetailNews.getResult().getImg(),mImgNbaDetailContent);
        String htmls=mNbaDetailNews.getResult().getOffline_data().getData().getNews().getContent();
        mWvNbaDetailContent.loadData(htmls, "text/html;charset=UTF-8", null);
    }
}
