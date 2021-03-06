package com.example.administrator.idlereader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.administrator.idlereader.utils.swipeBack.SwipeBackActivity;
import com.example.administrator.idlereader.utils.swipeBack2.app.SwipeBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewActivity extends SwipeBackActivity {
    private static final String TAG = "WebViewActivity";
    @BindView(R.id.fl_web)
    FrameLayout mFlWeb;
    private WebView wbNews;
    private String loadUrl, title;
    private WebViewClient webViewClient;
    private TextView tv_bar_title;
    private ImageView iv_back;
    public static final String WEB_URL = "WEB_URL";
    public static final String WEB_TITLE = "WEB_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        loadUrl = getIntent().getStringExtra(WEB_URL);
        title = getIntent().getStringExtra(WEB_TITLE);
        initView();
        setWebViewClient();
    }

    private void setWebViewClient() {
        webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String js = getClearAdDivJs(WebViewActivity.this);
                view.loadUrl(js);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
               /* //判断是否是广告相关的资源链接
                if (!AdFilterTool.isAd(WebViewActivity.this, url)) {
                    //这里是不做处理的数据
                    return super.shouldInterceptRequest(wbNews, url);
                } else {
                    //有广告的请求数据，我们直接返回空数据，注：不能直接返回null
                    return new WebResourceResponse(null, null, null);
                }*/
                return super.shouldInterceptRequest(wbNews, url);
            }
        };
        wbNews.setWebViewClient(webViewClient);
    }

    private void initView() {
        wbNews = (WebView) findViewById(R.id.wb_news);
        wbNews.getSettings().setJavaScriptEnabled(true);
        wbNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wbNews.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wbNews.loadUrl(loadUrl);
        tv_bar_title = (TextView) findViewById(R.id.tv_bar_title);
        tv_bar_title.setText(title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wbNews.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && wbNews.canGoBack()) {
            wbNews.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static class AdFilterTool {
        public static boolean isAd(Context context, String url) {
            Resources res = context.getResources();
            String[] filterUrls = res.getStringArray(R.array.adUrls);
            for (String adUrl : filterUrls) {
                if (url.contains(adUrl)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static String getClearAdDivJs(Context context) {
        String js = "javascript:";
        Resources res = context.getResources();
        String[] adDivs = res.getStringArray(R.array.adBlockDiv);
        for (int i = 0; i < adDivs.length; i++) {
            js += "var elements = document.getElementsByClassName('" + adDivs[i] + "');\n" +
                    "while(elements.length > 0){\n" +
                    "elements[0].parentNode.removeChild(elements[0]);\n" +
                    "}";
        }
        return js;
    }
}
