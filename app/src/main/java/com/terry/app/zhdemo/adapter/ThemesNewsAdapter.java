package com.terry.app.zhdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.bean.ThemeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taozi on 2016/9/9.
 */
public class ThemesNewsAdapter extends BaseAdapter {

    private List<ThemeItem.StoriesBean> storiesBeen;
    private Context mContext;

    public ThemesNewsAdapter(Context mContext) {
        this.mContext = mContext;
        storiesBeen = new ArrayList<>();
    }

    public void addData(List<ThemeItem.StoriesBean> beanList) {
        beanList.removeAll(storiesBeen);
        storiesBeen.addAll(beanList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return storiesBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return storiesBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.ivTop = (ImageView) convertView.findViewById(R.id.iv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ThemeItem.StoriesBean bean = storiesBeen.get(position);
        viewHolder.tvTitle.setText(bean.getTitle());
        if (bean.getImages() != null){
            viewHolder.ivTop.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(bean.getImages().get(0)).into(viewHolder.ivTop);
        }else {
            viewHolder.ivTop.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        ImageView ivTop;
    }
}

