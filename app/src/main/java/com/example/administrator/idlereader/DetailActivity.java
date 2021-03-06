package com.example.administrator.idlereader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.example.administrator.idlereader.utils.UIUtils;
import com.example.administrator.idlereader.utils.swipeBack.SwipeBackActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends SwipeBackActivity {
    private static final String TAG = "DetailActivity";
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_bar_title)
    TextView mTvBarTitle;
    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String className = getIntent().getStringExtra(UIUtils.FRAGMENT_CLASS);
        try {
            mFragment = (Fragment) Class.forName(className).newInstance();
            if (mFragment != null) {
                mFragment.setArguments(savedInstanceState);
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, mFragment);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolBar();
    }

    private void initToolBar() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvBarTitle.setText("闲阅");
    }
}
