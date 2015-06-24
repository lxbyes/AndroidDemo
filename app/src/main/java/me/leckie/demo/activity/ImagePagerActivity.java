package me.leckie.demo.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.leckie.demo.R;

public class ImagePagerActivity extends AppCompatActivity {

    private ViewPager mImagePager;

    private List<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        initViews();
    }

    private void initViews() {
        mImagePager = (ViewPager) findViewById(R.id.pager_image);
        mViews = new ArrayList<>();

        TextView v1 = new TextView(this);
        TextView v2 = new TextView(this);
        TextView v3 = new TextView(this);
        TextView v4 = new TextView(this);

        v1.setBackgroundResource(R.drawable.xianbo);
        v2.setBackgroundResource(R.drawable.qingbao);
        v3.setBackgroundResource(R.drawable.shi);
        v4.setBackgroundResource(R.drawable.xin);


        mViews.add(v1);
        mViews.add(v2);
        mViews.add(v3);
        mViews.add(v4);

        mImagePager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViews.get(position));
                return mViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }
        });

        mImagePager.setCurrentItem(1);
    }

}
