package me.leckie.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.leckie.demo.R;
import me.leckie.demo.adapter.SimpleAdapter;
import me.leckie.demo.adapter.StaggeredAdapter;

public class StaggeredGridLayoutActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<String> mDatas;

    private StaggeredAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initDatas();

        initViews();

        mAdapter = new StaggeredAdapter(this, mDatas);
        mAdapter.setOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(StaggeredGridLayoutActivity.this, "onItemClick:" + pos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int pos) {
                mAdapter.deleteData(pos);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    public void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.action_hor_gridview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.action_staggered:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
