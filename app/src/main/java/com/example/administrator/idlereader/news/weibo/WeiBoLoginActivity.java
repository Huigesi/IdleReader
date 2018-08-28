package com.example.administrator.idlereader.news.weibo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.idlereader.MainActivity;
import com.example.administrator.idlereader.R;
import com.example.administrator.idlereader.bean.weibo.WeiBoUserInfo;
import com.example.administrator.idlereader.http.Api;
import com.example.administrator.idlereader.http.RetrofitHelper;
import com.example.administrator.idlereader.news.model.NewsModel;
import com.example.administrator.idlereader.utils.SPreUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A login screen that offers login via email/password.
 */
public class WeiBoLoginActivity extends AppCompatActivity {

    private static final String TAG = "WeiBoLoginActivity";
    @BindView(R.id.weibo_email)
    EditText mWeiboEmail;
    @BindView(R.id.weibo_password)
    EditText mWeiboPassword;
    @BindView(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @BindView(R.id.login_form)
    LinearLayout mLoginForm;
    public static final int REQUEST_WEIBO_LOGIN = 895;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_login);
        ButterKnife.bind(this);
        mWeiboEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mWeiboEmail.getText())){
                    Toast.makeText(WeiBoLoginActivity.this, "请输入邮箱或手机号",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mWeiboPassword.getText())) {
                    Toast.makeText(WeiBoLoginActivity.this, "请输入密码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                loginWeiBo(mWeiboEmail.getText().toString().trim(),
                        mWeiboPassword.getText().toString().trim());
            }
        });

    }

    private void loginWeiBo(String user,String password) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Integer.class, new NewsModel.IntegerDefault0Adapter());
        gsonBuilder.registerTypeAdapter(int.class, new NewsModel.IntegerDefault0Adapter());
        Gson gson = gsonBuilder.create();
        RetrofitHelper.getInstance(Api.WEIBO_LIST, gson)
                .login(user, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<WeiBoUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(WeiBoLoginActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeiBoUserInfo weiBoUserInfo) {
                        if (weiBoUserInfo != null) {
                            Toast.makeText(WeiBoLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            SPreUtils.setWeiBoUserInfo(weiBoUserInfo, WeiBoLoginActivity.this);
                            finish();
                        }else {
                            Toast.makeText(WeiBoLoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

