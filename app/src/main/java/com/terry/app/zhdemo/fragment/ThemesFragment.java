package com.terry.app.zhdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.activity.MainActivity;
import com.terry.app.zhdemo.activity.NewsDetailActivity;
import com.terry.app.zhdemo.adapter.ThemesNewsAdapter;
import com.terry.app.zhdemo.bean.ThemeItem;
import com.terry.app.zhdemo.util.Contant;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taozi on 2016/9/9.
 */
public class ThemesFragment extends BaseFragment {

    private ThemeItem themeItem;
    private List<ThemeItem.StoriesBean> storiesBeen;
    private ListView lv_themes;
    private ImageView mImageView;
    private TextView mTitle;
    private int id;
    private Handler handler = new Handler();
    private ThemesNewsAdapter themesNewsAdapter;

    public ThemesFragment(int id) {
        this.id = id;
    }


    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getThemeNewsById(id);
            }
        }).start();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.themes_item, container, false);
        lv_themes = (ListView) view.findViewById(R.id.lv_themes);
        View header = inflater.inflate(R.layout.header_theme, lv_themes, false);
        mImageView = (ImageView) header.findViewById(R.id.iv_themes);
        mTitle = (TextView) header.findViewById(R.id.tv_title);
        lv_themes.addHeaderView(header);
        themesNewsAdapter = new ThemesNewsAdapter(getActivity());
        lv_themes.setAdapter(themesNewsAdapter);
        lv_themes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsDetailActivity.startActivity(getActivity(), storiesBeen.get(position-1).getId());
            }
        });
        return view;
    }


    public void getThemeNewsById(int id) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Contant.NEWSTHEMES + id).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String backJson = response.body().string();
                Logger.d(backJson);
                parseThemeItemJson(backJson);
            }
        });
    }

    private void parseThemeItemJson(String backJson) {
        if (TextUtils.isEmpty(backJson)) {
            return;
        }
        Gson gson = new Gson();
        themeItem = gson.fromJson(backJson, ThemeItem.class);
        storiesBeen = themeItem.getStories();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity()).load(themeItem.getBackground()).into(mImageView);
                mTitle.setText(themeItem.getDescription());
                themesNewsAdapter.addData(storiesBeen);
                ((MainActivity) mActivity).setToolbarTitle(themeItem.getName());
            }
        });
    }
}
