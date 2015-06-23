package me.leckie.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.leckie.demo.R;
import me.leckie.demo.bean.NewsBean;
import me.leckie.demo.util.ImageLoader;

/**
 * @author leckie
 * @date 6/23/15
 */
public class NewsAdapter extends BaseAdapter {

    private List<NewsBean> mList;

    private LayoutInflater mInflater;

    public NewsAdapter(Context context, List<NewsBean> data) {
        mList = data;
        mInflater = LayoutInflater.from(context);
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
        //new ImageLoader().showImageByThread(viewHolder.mIcon, url);
        new ImageLoader().showImageByAsyncTask(viewHolder.mIcon, url);
        viewHolder.mTitle.setText(mList.get(position).getNewsTitle());
        viewHolder.mContent.setText(mList.get(position).getNewsContent());
        return convertView;
    }

    class ViewHolder {

        public TextView mTitle, mContent;

        public ImageView mIcon;
    }
}
