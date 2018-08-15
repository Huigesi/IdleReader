package com.example.administrator.idlereader.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.WebViewActivity;
import com.example.administrator.idlereader.bean.hupu.NbaDetailNews;
import com.example.administrator.idlereader.utils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private Unbinder mUnbinder;
    private NbaDetailNews mNbaDetailNews;

    public NbaDetailHeaderView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_nba_detail, this, true);
        mUnbinder = ButterKnife.bind(this);
    }

    public void setData(NbaDetailNews data) {
        mNbaDetailNews = data;
        refreshUI();
    }

    private void refreshUI() {
        if (mNbaDetailNews == null) {
            return;
        }
        mTvNbaDetailTitle.setText(mNbaDetailNews.getResult().getTitle());
        mTvNbaDetailTitleOrigin.setText(mNbaDetailNews.getResult().getOffline_data().
                getData().getNews().getOrigin());
        mTvNbaDetailTitleOrigin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.WEB_URL, mNbaDetailNews.getResult()
                        .getOffline_data().getData().getNews().getOrigin_url());
                intent.putExtra(WebViewActivity.WEB_TITLE, mNbaDetailNews.getResult().getTitle());
                getContext().startActivity(intent);
            }
        });
        mTvNbaDetailTitleTime.setText(mNbaDetailNews.getResult().getOffline_data().getData()
                .getNews().getAddtime());
        GlideUtils.load(getContext(),mNbaDetailNews.getResult().getOffline_data()
                .getData().getNews().getImg_m(),mImgNbaDetailContent);
        String htmls=mNbaDetailNews.getResult().getOffline_data().getData().getNews().getContent();
        mWvNbaDetailContent.loadData(htmls, "text/html;charset=UTF-8", null);
    }
}
