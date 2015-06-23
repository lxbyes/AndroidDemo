package me.leckie.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import me.leckie.demo.R;
import me.leckie.demo.bean.NewsBean;
import me.leckie.demo.util.ImageLoader;

/**
 * @author leckie
 * @date 6/23/15
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private ListView mListView;

    private ImageLoader mImageLoader;

    private List<NewsBean> mList;

    private LayoutInflater mInflater;

    private int mStart;

    private int mEnd;

    public static String[] URLS;

    private boolean mFirst = true;

    public NewsAdapter(Context context, List<NewsBean> data, ListView listView) {
        mListView = listView;
        mList = data;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(listView);
        URLS = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            URLS[i] = data.get(i).getNewsIconUrl();
        }

        mFirst = true;
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_simple_async, null);
            viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).getNewsIconUrl();
        viewHolder.mIcon.setTag(url);
        // async load image
        //mImageLoader.showImageByThread(viewHolder.mIcon, url);
        //mImageLoader.showImageByAsyncTask(viewHolder.mIcon, url);
        viewHolder.mTitle.setText(mList.get(position).getNewsTitle());
        viewHolder.mContent.setText(mList.get(position).getNewsContent());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // 加载可见项
            mImageLoader.loadImages(mStart, mEnd);

        } else {
            // 停止任务
            mImageLoader.cancelAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        // 首次加载
        if (mFirst && visibleItemCount > 0) {
            mImageLoader.loadImages(mStart, mEnd);
            mFirst = false;
        }
    }

    class ViewHolder {
        public TextView mTitle;
        public TextView mContent;
        public ImageView mIcon;
    }
}
