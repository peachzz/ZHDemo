package com.terry.app.zhdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.terry.app.zhdemo.MyApplication;
import com.terry.app.zhdemo.R;
import com.terry.app.zhdemo.bean.ThemesID;

import java.util.List;


/**
 * Created by Taozi on 2016/7/8.
 */
public class MenuListAdapter extends BaseAdapter {
    private Context mContxt;
//    private String[] list = new String[12];
    private List<ThemesID> dataList;

    public MenuListAdapter(Context contxt, List<ThemesID> dataList) {
        this.mContxt = contxt;
        this.dataList =dataList;
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
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContxt).inflate(R.layout.menu_item, null);
            viewHolder.mTv_Item = (TextView) convertView.findViewById(R.id.tv_menus_item);
            viewHolder.mIbMenu = (ImageButton) convertView.findViewById(R.id.ib_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTv_Item.setText(dataList.get(position).getName());
        viewHolder.mIbMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewHolder.mIbMenu.setBackgroundResource(R.drawable.background);
//                viewHolder.mIbMenu.setSelected(true);
                Toast.makeText(MyApplication.getContext(), "关注成功", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mTv_Item;
        ImageButton mIbMenu;
    }
}
