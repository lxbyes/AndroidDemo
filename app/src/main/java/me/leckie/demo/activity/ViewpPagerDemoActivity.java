package me.leckie.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.leckie.demo.R;

public class ViewpPagerDemoActivity extends AppCompatActivity {

    private ViewPager mPager;

    private PagerTabStrip mTabStrip;

    private List<View> mViews;

    private List<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewp_pager_demo);

        initViews();
    }

    private void initViews() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabStrip = (PagerTabStrip) findViewById(R.id.tab_strip);
        mViews = new ArrayList<>();
        mTitles = new ArrayList<>();

        mTitles.add("News");
        mTitles.add("Friends");
        mTitles.add("Sports");
        mTitles.add("Weibo");

        LayoutInflater inflater = LayoutInflater.from(this);
        mViews.add(inflater.inflate(R.layout.tab_news, null));
        mViews.add(inflater.inflate(R.layout.tab_friends, null));
        mViews.add(inflater.inflate(R.layout.tab_sports, null));
        mViews.add(inflater.inflate(R.layout.tab_weibo, null));

        mTabStrip.setDrawFullUnderline(false);
        mTabStrip.setTabIndicatorColor(Color.LTGRAY);
        mTabStrip.setTextSpacing(20);
        mTabStrip.setPadding(0, 0, 0, 0);

        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("Leckie", position + ":onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Leckie", position + ":onPageSelected");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("Leckie", state + ":onPageScrollStateChanged");

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewp_pager_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
