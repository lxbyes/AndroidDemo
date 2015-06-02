package me.leckie.demo.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import me.leckie.demo.R;

public class ImageViewActivity extends Activity {

    private static final String TAG = "ImageViewActivity";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = (ImageView) findViewById(R.id.imageview);

        Bundle bundle = this.getIntent().getExtras();
        int imageId = bundle.getInt("imageId");

        Log.d(TAG, "" + imageId);

        imageView.setImageResource(imageId);
    }

}
