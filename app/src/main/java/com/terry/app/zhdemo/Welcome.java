package com.terry.app.zhdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by wwjun.wang on 2015/8/11.
 */
public class Welcome extends Activity {

    public static final String WELCOME_URL = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

    private ImageView mIvStart;
    private TextView mTvStart;
    private RelativeLayout anim;
    private ImageView ivBot;
    private LinearLayout mLinear;
    private TranslateAnimation tAnimation;
    private RelativeLayout mAnimTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        mIvStart = (ImageView) findViewById(R.id.iv_start);
        mTvStart = (TextView) findViewById(R.id.tv_start);

        mLinear = (LinearLayout) findViewById(R.id.linear);
        mAnimTop = (RelativeLayout) findViewById(R.id.anim_top);

        mLinear.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        anim = (RelativeLayout) findViewById(R.id.anim);
        ivBot = (ImageView) findViewById(R.id.iv_bot);
        getImage();
    }

    private void startActivity() {
        Intent intent = new Intent(Welcome.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
        finish();
    }

    public void getImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(WELCOME_URL).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        String[] str = parseJson(json);
                        final String url = str[0];
                        final String text = str[1];

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                anim.setVisibility(View.VISIBLE);

                                int moveY = anim.getMeasuredHeight();
                                tAnimation = new TranslateAnimation(0, 0, moveY, 0);
                                tAnimation.setDuration(1100);
                                tAnimation.setFillAfter(true);

                                tAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        ivBot.setImageResource(R.mipmap.dark_image_small_default_white);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
                                                alphaAnimation.setDuration(900);
                                                alphaAnimation.setFillAfter(true);
                                                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {

                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                startActivity();
                                                            }
                                                        },2000);

                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {

                                                    }
                                                });
                                                mAnimTop.startAnimation(alphaAnimation);
                                                Glide.with(Welcome.this).load(url).centerCrop().crossFade().into(mIvStart);
                                                mTvStart.setText(text);
                                            }
                                        }, 1500);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                anim.startAnimation(tAnimation);
                            }
                        });
                    } else {
                        Toast.makeText(Welcome.this, "图片获取异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public String[] parseJson(String jsonString) {
        String[] welcome = new String[2];
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String imageUrl = jsonObject.getString("img");
            String text = jsonObject.getString("text");
            welcome[0] = imageUrl;
            welcome[1] = text;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return welcome;
    }
}