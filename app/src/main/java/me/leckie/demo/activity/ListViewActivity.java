package me.leckie.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.leckie.demo.R;

/**
 * ListView to show demo items
 */
public class ListViewActivity extends Activity {

    private ListView listView;

    private void init() {
        // items
        String[] items = {"Back", "Lifecycle", "GridViewActivity",
                "ListViewLoader", "Settings", "Fragment",
                "DrawBall", "Movement", "SurfaceView", "Moveable", "GreenDao", "SlidingMenu"};
        // Demo class
        final Class[] targets = {MainActivity.class, LifecycleActivity.class, GridViewActivity.class,
                ListViewLoader.class, SettingsActivity.class, FragmentActivity.class,
                DrawBallActivity.class, MovementActivity.class, SurfaceActivity.class,
                MoveableActivity.class, GreenDaoActivity.class, SlidingMenuActivity.class};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ListViewActivity.this, targets[position]));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // init the view
        init();
    }
}
