package com.terry.app.zhdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.bean.NewsDetail;
import com.terry.app.zhdemo.util.Contant;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taozi on 2016/8/25.
 */
public class NewsDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private WebView mWebView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.webview);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        setWebView(mWebView);

        final int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doNetWork(id);
                }
            }).start();
        }
    }

    public void doNetWork(int id) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Contant.NEWSDETAIL + id).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    parseData(response.body().string());
                }
            });
    }


    private void parseData(String string) {
        Gson gson = new Gson();
        final NewsDetail mNewsDetail = gson.fromJson(string, NewsDetail.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                String headerImage;
                if (mNewsDetail.getImage() == null || mNewsDetail.getImage() == "") {
                    headerImage = "file:///android_asset/news_detail_header_image.jpg";
                } else {
                    headerImage = mNewsDetail.getImage();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("<div class=\"img-wrap\">")
                        .append("<h1 class=\"headline-title\">")
                        .append(mNewsDetail.getTitle()).append("</h1>")
                        .append("<span class=\"img-source\">")
                        .append(mNewsDetail.getImage_source()).append("</span>")
                        .append("<img src=\"").append(headerImage)
                        .append("\" alt=\"\">")
                        .append("<div class=\"img-mask\"></div>");
                String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                        + mNewsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
                mWebView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
                mToolbar.setTitle(mNewsDetail.getTitle());
            }
        });
    }

    private void setWebView(WebView mWebView) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
    }

    public static void startActivity(Context context, int id) {
        Intent i = new Intent(context, NewsDetailActivity.class);
        i.putExtra("id", id);
        context.startActivity(i);
    }
}