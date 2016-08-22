package com.terry.app.zhdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.bean.Latest;
import com.terry.app.zhdemo.util.Contant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taozi on 2016/8/15.
 */
public class NewsItemAdapter extends BaseAdapter {

    private List<Latest.StoriesBean> dataList;
    private Context mContext;

    public NewsItemAdapter(Context mContext) {
        this.mContext = mContext;
        dataList = new ArrayList<>();
    }

    public void addAll(List<Latest.StoriesBean> list) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_topic);
            viewHolder.ivTop = (ImageView) convertView.findViewById(R.id.iv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Latest.StoriesBean storiesBean = dataList.get(position);
        if (storiesBean.getType() == Contant.TOPIC) {
            ((FrameLayout) viewHolder.tvDate.getParent()).setBackgroundColor(Color.TRANSPARENT);
            viewHolder.tvTitle.setVisibility(View.GONE);
            viewHolder.ivTop.setVisibility(View.GONE);
            viewHolder.tvDate.setVisibility(View.VISIBLE);
//            viewHolder.tvDate.setText(storiesBean.getTitle());
        } else {
            viewHolder.tvDate.setVisibility(View.GONE);
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.ivTop.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(storiesBean.getTitle());
            Glide.with(mContext).load(storiesBean.getImages().get(0)).into(viewHolder.ivTop);
            ((FrameLayout) viewHolder.tvDate.getParent()).setBackgroundResource(R.drawable.item_background_selector_light);
        }
        return convertView;
    }

    private final class ViewHolder {
        TextView tvDate, tvTitle;
        ImageView ivTop;
    }
}
