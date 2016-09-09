package com.terry.app.zhdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.bean.StoriesBean;
import com.terry.app.zhdemo.util.Contant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taozi on 2016/8/15.
 */
public class NewsItemTypeAdapter extends BaseAdapter {

    private List<StoriesBean> dataList;
    private Context mContext;

    public NewsItemTypeAdapter(Context mContext) {
        this.mContext = mContext;
        dataList = new ArrayList<>();
    }

    public void addAll(List<StoriesBean> list) {
        list.removeAll(dataList);
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoriesBean storiesBean = dataList.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (storiesBean.getType()) {
                case Contant.TOPIC:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.new_item_topic, null);
                    viewHolder.tvTopic = (TextView) convertView.findViewById(R.id.tv_topic);
                    break;
                case Contant.ITEM:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
                    viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                    viewHolder.ivTop = (ImageView) convertView.findViewById(R.id.iv_title);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (storiesBean != null) {
            switch (storiesBean.getType()){
                case Contant.TOPIC:
                    viewHolder.tvTopic.setText(storiesBean.getTitle());
                    break;
                case Contant.ITEM:
                    viewHolder.tvTitle.setText(storiesBean.getTitle());
                    Glide.with(mContext).load(storiesBean.getImages().get(0)).into(viewHolder.ivTop);
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataList.get(position).getType()) {
            case Contant.TOPIC:
                return 0;
            case Contant.ITEM:
                return 1;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEnabled(int position) {
        switch (dataList.get(position).getType()) {
            case Contant.TOPIC:
                return false;
            default:
                return true;
        }
    }

    private final class ViewHolder {
        TextView tvTopic, tvTitle;
        ImageView ivTop;
    }
}
