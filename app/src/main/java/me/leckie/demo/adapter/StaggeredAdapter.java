package me.leckie.demo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leckie
 * @date 6/22/15
 */
public class StaggeredAdapter extends SimpleAdapter {


    private List<Integer> mHeights;

    public StaggeredAdapter(Context context, List<String> datas) {
        super(context, datas);

        mHeights = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        lp.height = mHeights.get(pos);

        holder.itemView.setLayoutParams(lp);
        holder.tv.setText(mDatas.get(pos));

        setUpItemEvent(holder);
    }


}

