package com.terry.app.zhdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.terry.app.zhdemo.MainActivity;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.adapter.NewsItemAdapter;
import com.terry.app.zhdemo.bean.Before;
import com.terry.app.zhdemo.bean.Latest;
import com.terry.app.zhdemo.bean.StoriesBean;
import com.terry.app.zhdemo.util.BannerView;
import com.terry.app.zhdemo.util.Contant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Taozi on 2016/8/22.
 */
public class TopNewsFragment extends BaseFragment {

    private ListView lvNews;
    private BannerView bannerView;
    private Latest latest;
    private Before before;
    private String date;
    private NewsItemAdapter mAdapter;
    private List<BannerView.ImageTitleBean> titleBeanList;
    private Handler handler = new Handler();


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_news_list, container, false);
        lvNews = (ListView) view.findViewById(R.id.lv_news);
        View header = inflater.inflate(R.layout.header, lvNews, false);
        bannerView = (BannerView) header.findViewById(R.id.header);
        bannerView.setOnItemClickListener(new BannerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        lvNews.addHeaderView(header);
        mAdapter = new NewsItemAdapter(getActivity());
        lvNews.setAdapter(mAdapter);
        initEvent();
        return view;
    }

    private void loadMore(String s) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(s).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String before = response.body().string();
                parseBeforeJson(before);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getLatest(Contant.URL_LATESET);
            }
        }).start();
    }

    private void initEvent() {
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void getLatest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            String latest = response.body().string();
            parseLatestJson(latest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLatestJson(String responseString) {
        Gson gson = new Gson();
        latest = gson.fromJson(responseString, Latest.class);
//        date = latest.getDate();
        List<Latest.TopStoriesBean> topStoriesBeanList = latest.getTop_stories();
        titleBeanList = new ArrayList<>();
        for (int i = 0; i < topStoriesBeanList.size(); i++) {
            BannerView.ImageTitleBean imageTitleBean = new BannerView.ImageTitleBean();
            imageTitleBean.setImageUrl(topStoriesBeanList.get(i).getImage());
            imageTitleBean.setTitle(topStoriesBeanList.get(i).getTitle());
            titleBeanList.add(imageTitleBean);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<StoriesBean> stories = latest.getStories();
                StoriesBean storiesBean = new StoriesBean();
                storiesBean.setTitle("今日热闻");
                storiesBean.setType(Contant.TOPIC);
                stories.add(0, storiesBean);
                mAdapter.addAll(stories);
                bannerView.addImageTitleBeanList(titleBeanList);
                bannerView.start();
                ((MainActivity) mActivity).setToolbarTitle("今日热文");
//                ((MainActivity) mActivity).setToolbarColor();
            }
        });
    }

    private void parseBeforeJson(String responseString) {
        Gson gson = new Gson();
        before = gson.fromJson(responseString, Before.class);
        date = before.getDate();
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<StoriesBean> storiesEntities = before.getStories();
                StoriesBean topic = new StoriesBean();
                topic.setType(Contant.TOPIC);
                topic.setTitle(convertDate(date));
                storiesEntities.add(0, topic);
                mAdapter.addBefore(storiesEntities);
                ((MainActivity) mActivity).setToolbarTitle(convertDate(date));
            }
        });
    }

    private String convertDate(String date) {
        String result = date.substring(0, 4);
        result += "年";
        result += date.substring(4, 6);
        result += "月";
        result += date.substring(6, 8);
        result += "日";
        return result;
    }
}