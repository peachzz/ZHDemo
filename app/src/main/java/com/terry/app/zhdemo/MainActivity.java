package com.terry.app.zhdemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.terry.app.zhdemo.adapter.MenuListAdapter;
import com.terry.app.zhdemo.fragment.TopNewsFragment;
import com.terry.app.zhdemo.fragment.ContentFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ContentFragment mContainer;
    private ActionBarDrawerToggle toggle;
    private Toolbar mToolbar;
    private ImageView mIvLogin;
    private TextView mLogins;
    private LinearLayout mCollect;
    private LinearLayout mDown;
    private TextView mTvDown;
    private LinearLayout mHomeItem;
    private ListView mLvMenu;
    private MenuListAdapter menuListAdapter;
    private String[] MENUS = {"日常心理学", "用户推荐日报", "电影日报", "不许无聊", "设计日报", "大公司日报", "财经日报", "互联网安全", "开始游戏", "音乐日报", "动漫日报", "体育日报"};
    private LinearLayout mLoginLay;
    private ScrollView mLeftMenu;
    private TopNewsFragment topNewsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvents();

//        if (mContainer == null) {
//            mContainer = new ContentFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(R.id.container, mContainer).commitAllowingStateLoss();
//        }
        if (topNewsFragment == null) {
            topNewsFragment = new TopNewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.container, topNewsFragment).commitAllowingStateLoss();
        }
    }

    private void initEvents() {

        //toolbar
        mToolbar.setTitle("知乎日报");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //scrollView 设置到顶部
        mLeftMenu.smoothScrollTo(0, 20);
        toggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        mCollect.setOnClickListener(this);
        mDown.setOnClickListener(this);
        mHomeItem.setOnClickListener(this);
        mLoginLay.setOnClickListener(this);
        //listView
        menuListAdapter = new MenuListAdapter(this, MENUS);
        mLvMenu.setFocusable(false);
        mLvMenu.setAdapter(menuListAdapter);

        mLvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + MENUS[position], Toast.LENGTH_SHORT).show();
                view.setSelected(true);
                mDrawerLayout.closeDrawers();
            }
        });

    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mIvLogin = (ImageView) findViewById(R.id.iv_login);
        mLogins = (TextView) findViewById(R.id.logins);

        mCollect = (LinearLayout) findViewById(R.id.collect);
        mDown = (LinearLayout) findViewById(R.id.down);
        mTvDown = (TextView) findViewById(R.id.tv_down);

        mHomeItem = (LinearLayout) findViewById(R.id.home_item);
        mLvMenu = (ListView) findViewById(R.id.lv_menu);

        mLeftMenu = (ScrollView) findViewById(R.id.left_menu);
        mLoginLay = (LinearLayout) findViewById(R.id.login_lay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect:
                Toast.makeText(MainActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.down:
                Toast.makeText(MainActivity.this, "下载", Toast.LENGTH_SHORT).show();
                break;
            case R.id.home_item:
                Toast.makeText(MainActivity.this, "首页", Toast.LENGTH_SHORT).show();
                mHomeItem.setSelected(true);
                break;
            case R.id.login_lay:
                Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    public void setToolbarTitle(String text) {
        mToolbar.setTitle(text);
    }

    public void setToolbarColor(float a) {
        mToolbar.setAlpha(a);
    }
}
