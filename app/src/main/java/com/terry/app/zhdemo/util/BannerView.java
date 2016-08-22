package com.terry.app.zhdemo.util;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.terry.app.zhdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taozi on 2016/8/15.
 */
public class BannerView extends FrameLayout {

    private static final String TAG = "BannerView";

    private Context mContext;
    private View contentView;
    private ViewPager vpImageTitle;
    private LinearLayout llDot;//指示器
    private int count;
    private List<View> viewList;//显示的图片和标题
    private boolean isAutoPlay;
    private Handler handler;
    private int currentItem;
    private SparseBooleanArray isLarge;
    private List<ImageTitleBean> imageTitleBeanList;
    private int dotSize = 12;//指示器大小
    private int dotSpace = 12;//指示器间距
    private int delay = 3000;//图片切换时间

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //初始化View
        initView();
        //初始化Animator

        //初始化数据
        initData();
    }

    /**
     * 初始化Data
     */
    private void initData() {
        imageTitleBeanList = new ArrayList<>();
    }

    /**
     * 初始化View
     */
    private void initView() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.banner_vp, this, true);//布局与父布局建立联系
        vpImageTitle = (ViewPager) findViewById(R.id.adv_pager);
        llDot = (LinearLayout) findViewById(R.id.circles);
    }

    //添加图片
    public void addImageUrl(String imageUrl) {
        ImageTitleBean imageTitleBean = new ImageTitleBean();
        imageTitleBean.setImageUrl(imageUrl);
        imageTitleBeanList.add(imageTitleBean);
    }

    //添加图片和标题
    public void addImageTitle(String imageUrl, String title) {
        ImageTitleBean imageTitleBean = new ImageTitleBean();
        imageTitleBean.setImageUrl(imageUrl);
        imageTitleBean.setTitle(title);
        imageTitleBeanList.add(imageTitleBean);
    }

    //添加图片和标题的ImageTitleBean对象
    public void addImageTitleBean(ImageTitleBean imageTitleBean) {
        imageTitleBeanList.add(imageTitleBean);
    }

    //添加图片和标题的ImageTitleBean对象的数据列表
    public void addImageTitleBeanList(List<ImageTitleBean> imageTitleBeanList) {
        this.imageTitleBeanList = imageTitleBeanList;
    }

    //开始轮播
    public void start() {
        if (imageTitleBeanList != null) {
            count = imageTitleBeanList.size();
            //设置ViewPager
            setViewPager(imageTitleBeanList);
            // 设置指示器
            setIndicator();
            // 开始播放
            starPlay();
        } else {
            Log.d(TAG, "数据为空");
        }
    }

    /**
     * 开始自动播放图片
     */
    private void starPlay() {
        // 如果少于2张就不用自动播放了
        if (count < 2) {
            isAutoPlay = false;
        } else {
            isAutoPlay = true;
            handler = new Handler();
            handler.postDelayed(task, delay);
        }
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if (isAutoPlay) {
                // 位置循环
                currentItem = currentItem % (count + 1) + 1;
                // 正常每隔3秒播放一张图片
                vpImageTitle.setCurrentItem(currentItem);
                handler.postDelayed(task, delay);
            } else {
                // 如果处于拖拽状态停止自动播放，会每隔5秒检查一次是否可以正常自动播放。
                handler.postDelayed(task, 5000);
            }
        }
    };

    /**
     * 设置指示器
     */
    private void setIndicator() {
        isLarge = new SparseBooleanArray();
        // 记得创建前先清空数据，否则会受遗留数据的影响。
        llDot.removeAllViews();
        for (int i = 0; i < count; i++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.banner_dot_normal);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            llDot.addView(view, layoutParams);
            isLarge.put(i, false);
        }
        llDot.getChildAt(0).setBackgroundResource(R.drawable.banner_dot_focus);
//        animatorToLarge.setTarget(llDot.getChildAt(0));
//        animatorToLarge.start();
        isLarge.put(0, true);
    }

    /**
     * 设置ViewPager
     *
     * @param imageTitleBeanList
     */
    private void setViewPager(List<ImageTitleBean> imageTitleBeanList) {
        // 设置View列表
        setViewList(imageTitleBeanList);
        vpImageTitle.setAdapter(new ImageTitlePagerAdapter());
        // 从第1张图片开始（位置刚好也是1，注意：0位置现在是最后一张图片）
        currentItem = 1;
        vpImageTitle.setCurrentItem(1);
        vpImageTitle.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 遍历一遍子View，设置相应的背景。
                for (int i = 0; i < llDot.getChildCount(); i++) {
                    if (i == position - 1) {// 被选中
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.banner_dot_focus);
                        if (!isLarge.get(i)) {
                            isLarge.put(i, true);
                        }
                    } else {// 未被选中
                        llDot.getChildAt(i).setBackgroundResource(R.drawable.banner_dot_normal);
                        if (isLarge.get(i)) {
                            isLarge.put(i, false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    // 闲置中
                    case ViewPager.SCROLL_STATE_IDLE:
                        // “偷梁换柱”
                        if (vpImageTitle.getCurrentItem() == 0) {
                            vpImageTitle.setCurrentItem(count, false);
                        } else if (vpImageTitle.getCurrentItem() == count + 1) {
                            vpImageTitle.setCurrentItem(1, false);
                        }
                        currentItem = vpImageTitle.getCurrentItem();
                        isAutoPlay = true;
                        break;
                    // 拖动中
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isAutoPlay = false;
                        break;
                    // 设置中
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isAutoPlay = true;
                        break;
                }
            }
        });
    }

    /**
     * 根据传入的数据设置View列表
     *
     * @param imageTitleBeanList
     */
    private void setViewList(List<ImageTitleBean> imageTitleBeanList) {
        viewList = new ArrayList<>();
        for (int i = 0; i < count + 2; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.banner_content, null);
            ImageView ivImage = (ImageView) view.findViewById(R.id.iv_title);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            if (i == 0) {//将第一页设置为最后一张图片
                Glide.with(mContext).load(imageTitleBeanList.get(count - 1).getImageUrl()).into(ivImage);
                tvTitle.setText(imageTitleBeanList.get(count - 1).getTitle());
            } else if (i == count + 1) {//将最后一页设置为第一张图片
                Glide.with(mContext).
                        load(imageTitleBeanList.get(0).getImageUrl()).into(ivImage);
                tvTitle.setText(imageTitleBeanList.get(0).getTitle());
            } else {
                Glide.with(mContext).
                        load(imageTitleBeanList.get(i - 1).getImageUrl()).into(ivImage);
                tvTitle.setText(imageTitleBeanList.get(i - 1).getTitle());
            }
            //将设置好的View添加到View列表中
            viewList.add(view);
        }
    }


    public void setDotSpace(int dotSpace) {
        this.dotSpace = dotSpace;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setDotSize(int dotSize) {
        this.dotSize = dotSize;
    }

    public static class ImageTitleBean {
        private String imageUrl;
        private String title;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    // 创建监听器接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 声明监听器
    private OnItemClickListener onItemClickListener;

    // 提供设置监听器的公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * PagerAdapter
     */
    class ImageTitlePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = viewList.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position - 1);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }

    /**
     * 释放资源
     */
    public void releaseResource() {
        handler.removeCallbacksAndMessages(null);
        mContext = null;
    }
}
