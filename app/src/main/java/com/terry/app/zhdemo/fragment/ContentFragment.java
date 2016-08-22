package com.terry.app.zhdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.terry.app.zhdemo.MainActivity;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.adapter.NewsItemAdapter;
import com.terry.app.zhdemo.bean.Latest;
import com.terry.app.zhdemo.util.BannerView;
import com.terry.app.zhdemo.util.Contant;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Taozi on 2016/7/8.
 */
public class ContentFragment extends Fragment {

    private ListView lvNews;
    private BannerView bannerView;
    private Latest latest;
    private String date;
    private NewsItemAdapter mAdapter;
    private List<BannerView.ImageTitleBean> titleBeanList;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity)getActivity()).setToolbarTitle("今日热闻");
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parseLatestJson(Contant.Latest);
    }

    private void initEvent() {


        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    private void parseLatestJson(String responseString) {
        Gson gson = new Gson();
        latest = gson.fromJson(responseString, Latest.class);
        date = latest.getDate();
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
                bannerView.addImageTitleBeanList(titleBeanList);
                bannerView.start();
                List<Latest.StoriesBean> storiesEntities = latest.getStories();
                Latest.StoriesBean topic = new Latest.StoriesBean();
                topic.setType(Contant.TOPIC);
                topic.setTitle("今日热闻");
                storiesEntities.add(0, topic);
                mAdapter.addAll(storiesEntities);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bannerView.releaseResource();
    }
}
