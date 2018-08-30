package com.example.administrator.idlereader.news.nba;

import android.os.Binder;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.hupu.NbaZhuanti;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.INBAZhuanTiView;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.swipeBack.SwipeBackActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NBAZhuanTiActivity extends SwipeBackActivity implements INBAZhuanTiView {

    @BindView(R.id.mainbackdrop)
    ImageView mMainbackdrop;
    @BindView(R.id.tv_nba_zhuanti)
    TextView mTvNbaZhuanti;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_bar_title)
    TextView mTvBarTitle;
    @BindView(R.id.maincollapsing)
    CollapsingToolbarLayout mMaincollapsing;
    @BindView(R.id.mainappbar)
    AppBarLayout mMainappbar;
    @BindView(R.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R.id.srl_news)
    SmartRefreshLayout mSrlNews;
    public static final String NBA_NID = "NBA_NID";
    @BindView(R.id.tv_nba_zhuanti_sum)
    TextView mTvNbaZhuantiSum;
    private String mNid;
    private NbaZhuanTiAdapter mNbaZhuanTiAdapter;
    private NewsPresenter mNewsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbazhuan_ti);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mNewsPresenter = new NewsPresenter(this);
        mNid = getIntent().getStringExtra(NBA_NID);
        mNbaZhuanTiAdapter = new NbaZhuanTiAdapter(this);
        mNewsPresenter.loadNbaZhuanTi(mNid);
        mRvNews.setAdapter(mNbaZhuanTiAdapter);
        mRvNews.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showData(NbaZhuanti data) {
        GlideUtils.load(this, data.getResult().getImg_m(), mMainbackdrop);
        mTvNbaZhuanti.setText(data.getResult().getTitle());
        mTvNbaZhuantiSum.setText(data.getResult().getSummary());
        mNbaZhuanTiAdapter.setData(data.getResult().getGroups(), true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
