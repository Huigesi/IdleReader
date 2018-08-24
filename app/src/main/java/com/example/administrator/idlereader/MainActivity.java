package com.example.administrator.idlereader;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.idlereader.bean.weibo.WeiBoDetail;
import com.example.administrator.idlereader.bean.weibo.WeiBoNews;
import com.example.administrator.idlereader.bean.weibo.WeiBoSpaceUser;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;
import com.example.administrator.idlereader.movie.FgMovieFragment;
import com.example.administrator.idlereader.news.FgNewsFragment;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.example.administrator.idlereader.utils.RegularUtils;
import com.example.administrator.idlereader.video.FgVideoFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";
    private View view_status;
    private ImageView iv_title_news;
    private ImageView iv_title_movie;
    private ImageView iv_title_video;
    private ViewPager vp_content;
    private Toolbar toolbars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffce3d3a"));
        }

        initView();
        initContentFragment();
        final String s = "606388e6";
        final String gsid = "_2A252cRzBDeRxGeNH61cX8yvNyT6IHXVTJxcJrDV6PUJbkdAKLUfykWpNSvDZShbJn5J7L7wv7ZqcP0d-KAnwRoKc";
        final String c = "weicoabroad";
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Integer.class, new NewsModel.IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(int.class, new NewsModel.IntegerDefault0Adapter());
        Gson mGson = gsonBuilder.create();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, mGson)
                .getWeiBoUserHeaderNews("0", s, gsid, c, "1219022557")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoSpaceUser>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(WeiBoSpaceUser weiBoNewsList) {
                        Log.i(TAG, "onNext: "+weiBoNewsList.getIdstr());
                    }
                });
    }


    private void initView() {
        view_status = (View) findViewById(R.id.view_status);
        iv_title_news = (ImageView) findViewById(R.id.iv_title_news);
        iv_title_movie = (ImageView) findViewById(R.id.iv_title_movie);
        iv_title_video = (ImageView) findViewById(R.id.iv_title_video);
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        toolbars = (Toolbar) findViewById(R.id.toolbars);

        iv_title_news.setOnClickListener(this);
        iv_title_movie.setOnClickListener(this);
        iv_title_video.setOnClickListener(this);

    }

    private void initContentFragment() {

        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(FgNewsFragment.getInstance());
        mFragmentList.add(FgMovieFragment.getInstance());
        mFragmentList.add(FgVideoFragment.getInstance());
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),
                mFragmentList);
        vp_content.setAdapter(adapter);
        vp_content.setOffscreenPageLimit(2);
        vp_content.addOnPageChangeListener(this);

        setSupportActionBar(toolbars);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        setCurrentItem(0);
    }

    private void setCurrentItem(int i) {
        vp_content.setCurrentItem(i);
        iv_title_movie.setSelected(false);
        iv_title_video.setSelected(false);
        iv_title_news.setSelected(false);
        switch (i) {
            case 0:
                iv_title_news.setSelected(true);
                break;
            case 1:
                iv_title_movie.setSelected(true);
                break;
            case 2:
                iv_title_video.setSelected(true);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_news:
                if (vp_content.getCurrentItem() != 0) {
                    setCurrentItem(0);
                }
                break;
            case R.id.iv_title_movie:
                if (vp_content.getCurrentItem() != 1) {
                    setCurrentItem(1);
                }
                break;
            case R.id.iv_title_video:
                if (vp_content.getCurrentItem() != 2) {
                    setCurrentItem(2);
                }
                break;
        }
    }
}
