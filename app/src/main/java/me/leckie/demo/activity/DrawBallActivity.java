package me.leckie.demo.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import me.leckie.demo.R;
import me.leckie.demo.view.DrawBallView;

public class DrawBallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_ball);

        LinearLayout layout = (LinearLayout) findViewById(R.id.draw_ball);

        DrawBallView ball = new DrawBallView(this);
        ball.setMinimumWidth(400);
        ball.setMinimumHeight(600);

        layout.addView(ball);
    }
}
