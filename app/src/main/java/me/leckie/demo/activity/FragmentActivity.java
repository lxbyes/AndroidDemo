package me.leckie.demo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Display;

import me.leckie.demo.R;
import me.leckie.demo.fragment.FirstFragment;
import me.leckie.demo.fragment.SecondFragment;

public class FragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Display display = getWindowManager().getDefaultDisplay();

        Fragment fragment = new FirstFragment();

        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }
}
