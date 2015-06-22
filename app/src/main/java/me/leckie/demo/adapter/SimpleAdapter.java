package me.leckie.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.leckie.demo.R;

/**
 * @author leckie
 * @date 6/22/15
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    protected List<String> mDatas;

    public interface OnItemClickListener {
        public void onItemClick(View view, int pos);

        public void onItemLongClick(View view, int pos);
    }

    protected OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public SimpleAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_simple_textview, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int pos) {
        holder.tv.setText(mDatas.get(pos));
        setUpItemEvent(holder);
    }

    protected void setUpItemEvent(final MyViewHolder holder) {
        if (onItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, layoutPos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int pos) {
        mDatas.add(pos, "Insert One.");
        notifyItemInserted(pos);
    }

    public void deleteData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv;

    public MyViewHolder(View view) {
        super(view);

        tv = (TextView) view.findViewById(R.id.item_single_view);
    }
}
