package com.example.administrator.idlereader.news.weibo;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.idlereader.DefaultsFooter;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.news.presenter.NewsPresenter;
import com.example.administrator.idlereader.news.view.IWeiBoSpaceView;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.SPreUtils;
import com.example.administrator.idlereader.utils.swipeBack.SwipeBackActivity;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeiBoSpaceActivity extends SwipeBackActivity implements IWeiBoSpaceView {
    private static final String TAG = "WeiBoSpaceActivity";
    @BindView(R.id.mainbackdrop)
    ImageView mMainbackdrop;
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
    @BindView(R.id.img_weibo_user_cover)
    ImageView mImgWeiboUserCover;
    @BindView(R.id.tv_weibo_user_name)
    TextView mTvWeiboUserName;
    @BindView(R.id.tv_weibo_user_intro)
    TextView mTvWeiboUserIntro;
    @BindView(R.id.tv_weibo_user_location)
    TextView mTvWeiboUserLocation;
    @BindView(R.id.tv_weibo_user_friends_count)
    TextView mTvWeiboUserFriendsCount;
    @BindView(R.id.tv_weibo_user_followers_count)
    TextView mTvWeiboUserFollowersCount;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsPresenter mNewsPresenter;
    private WeiBoNewsAdapter mWeiBoNewsAdapter;
    public static final String WEIBO_SPACE_UID = "WEIBO_SPACE_UID";
    private int page = 0;
    private String uid;
    private String mGsid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_space);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        mSrlNews.setRefreshHeader(new MaterialHeader(this).setColorSchemeColors(
                getResources().getColor(R.color.colorTheme)));
        mSrlNews.setRefreshFooter(new DefaultsFooter(this).setFinishDuration(0));
        mRvNews.setHasFixedSize(true);
        mNewsPresenter = new NewsPresenter(this);
        mGsid = SPreUtils.getWeiBoUserInfo(SPreUtils.WEIBO_GSID, this);
        mLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        uid = getIntent().getStringExtra(WEIBO_SPACE_UID);
        mWeiBoNewsAdapter = new WeiBoNewsAdapter(this);
        mSrlNews.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsPresenter.loadWeiBoUserNews(uid, mGsid, 0);
            }
        });
        mSrlNews.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mNewsPresenter.loadWeiBoUserNews(uid, mGsid, page);
            }
        });
        final int line = Resolution.dipToPx(this, 5);
        mRvNews.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, line, 0, 0);
            }
        });
        mRvNews.setLayoutManager(mLinearLayoutManager);
        mNewsPresenter.loadWeiBoUserNews(uid, mGsid, 0);
        mNewsPresenter.loadWeiBoUserHeaderNews(uid, mGsid);
        mRvNews.setAdapter(mWeiBoNewsAdapter);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void hideDialog() {
        mSrlNews.finishRefresh(0);
        mSrlNews.finishLoadMore(0);
    }

    @Override
    public void showErrorMsg(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(WeiBoNews data) {
        mWeiBoNewsAdapter.setData(data.getStatuses(), true);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showMoreData(WeiBoNews moreData) {
        mWeiBoNewsAdapter.setData(moreData.getStatuses(), false);
    }

    @Override
    public void showHeader(WeiBoSpaceUser data) {
        int weight = Resolution.dipToPx(this, 80);
        GlideUtils.load(this, data.getCovers().get(0).getCover(), mMainbackdrop, weight, weight);
        GlideUtils.loadCircle(this, data.getAvatar_large(), mImgWeiboUserCover, weight, weight);
        mTvWeiboUserName.setText(data.getName());
        mTvWeiboUserIntro.setText(data.getDescription());
        mTvWeiboUserLocation.setText(data.getLocation());
        mTvWeiboUserFriendsCount.setText(data.getFriends_count() + " 关注");
        mTvWeiboUserFollowersCount.setText(data.getFollowers_count() + " 粉丝");
        mTvBarTitle.setText(data.getName() + "的微博");
    }
}
