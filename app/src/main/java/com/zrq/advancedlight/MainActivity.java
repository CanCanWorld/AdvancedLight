package com.zrq.advancedlight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.zrq.advancedlight.fragment.ListFragment;
import com.zrq.advancedlight.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View rootView;
    private Context mContext;
    private List<String> mList;
    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initData();
    }

    private void initView() {
        rootView = getWindow().getDecorView();
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.table_layout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.tool_bar);
        mNavigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(mToolbar);
    }

    private void initData() {
        mList = new ArrayList<>();
        mFragments = new ArrayList<>();
        mList.add("安卓进阶之光");
        mList.add("暂无");
        mList.add("暂无");
        mList.add("暂无");
        mList.add("暂无");
        mList.add("暂无");
        mList.add("暂无");
        mList.add("暂无");

        for (String s : mList) {
            mFragments.add(new ListFragment(s));
            mTabLayout.addTab(mTabLayout.newTab().setText(s));
        }

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, mList);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}