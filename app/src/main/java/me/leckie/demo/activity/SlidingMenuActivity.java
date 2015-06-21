package me.leckie.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import me.leckie.demo.R;
import me.leckie.demo.view.SlidingMenu;

public class SlidingMenuActivity extends Activity {

    private SlidingMenu mLeftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);

        mLeftMenu = (SlidingMenu) findViewById(R.id.menu);
    }

    public void toggleMenu(View view) {
        mLeftMenu.toggleMenu();
    }
}
