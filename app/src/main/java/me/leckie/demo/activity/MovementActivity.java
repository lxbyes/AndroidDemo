package me.leckie.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import me.leckie.demo.view.MovementView;

public class MovementActivity extends Activity {

    private MovementView movementView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        movementView = new MovementView(this);

        super.onCreate(savedInstanceState);
        setContentView(movementView);
    }
}
