package com.example.administrator.idlereader.news;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.utils.swipeBack.SwipeBackActivity;
import com.example.administrator.idlereader.utils.swipeBack.SwipeBackLayout;


public class GameActivity extends SwipeBackActivity {
    private String mLoadUrl,mTitle;
    private ImageView iv_back;
    private TextView tv_bar_title;
    private WebView wb_news;
    private WebViewClient webViewClient;
    private SwipeBackLayout swipeBackLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_notframe);
        mLoadUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        swipeBackLayout = getSwipeBackLayout();
        initView();
        setWebViewClient();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_bar_title = (TextView) findViewById(R.id.tv_bar_title);
        wb_news = (WebView) findViewById(R.id.wb_news);
        wb_news = (WebView) findViewById(R.id.wb_news);
        wb_news.getSettings().setJavaScriptEnabled(true);
        wb_news.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb_news.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wb_news.canGoBack();
        wb_news.canGoForward();
        wb_news.loadUrl(mLoadUrl);
        tv_bar_title = (TextView) findViewById(R.id.tv_bar_title);
        tv_bar_title.setText(mTitle);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setWebViewClient() {
        webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(wb_news, url);
            }
        };
        wb_news.setWebViewClient(webViewClient);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        wb_news.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && wb_news.canGoBack()) {
            wb_news.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
