package com.terry.app.zhdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.terry.app.zhdemo.activity.MainActivity;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.activity.NewsDetailActivity;
import com.terry.app.zhdemo.adapter.NewsItemAdapter;
import com.terry.app.zhdemo.adapter.NewsItemTypeAdapter;
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
public class TopNewsFragment extends BaseFragment implements AbsListView.OnScrollListener {

    private ListView lvNews;
    private BannerView bannerView;
    private Before before;
    private Latest latest;
    //日期时间,20160823
    private String date;
    //    private NewsItemAdapter mAdapter;
    private NewsItemTypeAdapter mAdapter;
    //轮播数据集合加载
    private List<BannerView.ImageTitleBean> titleBeanList;
    private List<Latest.TopStoriesBean> topStoriesBeanList;
    private List<StoriesBean> stories;
    private Handler handler = new Handler();
    private boolean isLoading = false;
//    private int index = 1;
//    private SwipeRefreshLayout mSwipe;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_news_list, container, false);
//        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        lvNews = (ListView) view.findViewById(R.id.lv_news);
        View header = inflater.inflate(R.layout.header, lvNews, false);
        bannerView = (BannerView) header.findViewById(R.id.header);
        bannerView.setOnItemClickListener(new BannerView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsDetailActivity.startActivity(getContext(), topStoriesBeanList.get(position).getId());
//                Toast.makeText(getContext(),""+position,Toast.LENGTH_SHORT).show();
            }
        });
        lvNews.addHeaderView(header);
        mAdapter = new NewsItemTypeAdapter(getActivity());
        lvNews.setAdapter(mAdapter);
        lvNews.setOnScrollListener(this);
//        mSwipe.setColorSchemeResources(R.color.bule,R.color.yellow,R.color.red,R.color.green);
//        mSwipe.setOnRefreshListener(this);
        initEvent();
        return view;
    }

    private void loadMore(String s) {
        isLoading = true;
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
                StoriesBean storiesBean = (StoriesBean) mAdapter.getItem(position - 1);
                NewsDetailActivity.startActivity(getContext(), storiesBean.getId());
//                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLatest(String url) {
        isLoading = true;
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
        Logger.d("LATEST", latest.getTop_stories(), latest.getStories());
        date = latest.getDate();
        topStoriesBeanList = latest.getTop_stories();
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
                stories = latest.getStories();
                StoriesBean storiesBean = new StoriesBean();
                storiesBean.setTitle("今日热闻");
                storiesBean.setType(Contant.TOPIC);
                stories.add(0, storiesBean);
                mAdapter.addAll(stories);
                bannerView.addImageTitleBeanList(titleBeanList);
                bannerView.start();
                ((MainActivity) mActivity).setToolbarTitle("今日热闻");
                isLoading = false;
//                ((MainActivity) mActivity).setToolbarColor(0.01f);
            }
        });
    }

    private void parseBeforeJson(String responseString) {
        Gson gson = new Gson();
        before = gson.fromJson(responseString, Before.class);
        if (before == null) {
            isLoading = false;
            return;
        }
        date = before.getDate();
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<StoriesBean> storiesEntities = before.getStories();
                StoriesBean topic = new StoriesBean();
                topic.setType(Contant.TOPIC);
                topic.setTitle(convertDate(date));
                storiesEntities.add(0, topic);
                mAdapter.addAll(storiesEntities);
                ((MainActivity) mActivity).setToolbarTitle(convertDate(date));
                isLoading = false;
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (lvNews != null && lvNews.getChildCount() > 0) {
            //下拉加载昨日新闻
            if (firstVisibleItem + visibleItemCount == totalItemCount && !isLoading) {
                loadMore(Contant.URL_BEFORE + date);
            }
        }
    }
}