package com.example.administrator.idlereader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;
import com.example.administrator.idlereader.movie.FgMovieFragment;
import com.example.administrator.idlereader.news.FgNewsFragment;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.example.administrator.idlereader.news.weibo.WeiBoLoginActivity;
import com.example.administrator.idlereader.utils.GlideUtils;
import com.example.administrator.idlereader.utils.Resolution;
import com.example.administrator.idlereader.utils.SPreUtils;
import com.example.administrator.idlereader.utils.UIUtils;
import com.example.administrator.idlereader.utils.bigImgViewPager.glide.ImageLoader;
import com.example.administrator.idlereader.utils.bigImgViewPager.tool.ToastUtil;
import com.example.administrator.idlereader.video.FgVideoFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private ImageView img_menu;
    private ImageView img_person;
    private TextView mUserName;
    private ViewPager vp_content;
    private Toolbar toolbars;
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public static final String WEIBO_USER = "WEIBO_USER";
    public static final String WEIBO_PWD = "WEIBO_PWD";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#ffce3d3a"));
        }
        new SPreUtils(this);

        mTencent = Tencent.createInstance(APP_ID, MainActivity.this.getApplicationContext());
        initView();
        initContentFragment();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav);
        View headerView = navigationView.getHeaderView(0);
        img_person = (ImageView) headerView.findViewById(R.id.person);
        mUserName = (TextView) headerView.findViewById(R.id.tv_user_name);
        //开启手势滑动打开侧滑菜单栏，如果要关闭手势滑动，将后面的UNLOCKED替换成LOCKED_CLOSED 即可
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_Setting:
                        break;
                    case R.id.menu_Login_QQ:
                        mTencent.login(MainActivity.this, "all", new BaseUiListener());
                        break;
                    case R.id.menu_Login_WeiBo:
                        UIUtils.startWeiBoLoginActivity(MainActivity.this);
                        break;
                    case R.id.menu_Clear:
                        ImageLoader.cleanDiskCache(MainActivity.this);
                        ToastUtil.getInstance()._short(MainActivity.this, "磁盘缓存已成功清除");
                        break;
                    case R.id.menu_AboutUs:
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }


    private void initView() {
        view_status = (View) findViewById(R.id.view_status);
        iv_title_news = (ImageView) findViewById(R.id.iv_title_news);
        iv_title_movie = (ImageView) findViewById(R.id.iv_title_movie);
        iv_title_video = (ImageView) findViewById(R.id.iv_title_video);
        img_menu = (ImageView) findViewById(R.id.img_menu);
        img_menu.setOnClickListener(this);
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
            case R.id.img_menu:
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;
        }
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG, "登录成功" + response.toString());
                        try {
                            int weight = Resolution.dipToPx(MainActivity.this, 72);
                            GlideUtils.loadCircle(MainActivity.this,
                                    ((JSONObject) response).getString("figureurl_2"),
                                    img_person, weight, weight);
                            mUserName.setText(((JSONObject) response).getString("nickname"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_LOGIN:
                Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
