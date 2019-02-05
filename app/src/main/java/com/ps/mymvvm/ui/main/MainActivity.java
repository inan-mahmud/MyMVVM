package com.ps.mymvvm.ui.main;

import android.os.Bundle;

import com.ps.mymvvm.R;
import com.ps.mymvvm.base.BaseActivity;
import com.ps.mymvvm.ui.list.ListFragment;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class MainActivity extends BaseActivity {

    public static final String INTENT_KEY_USERNAME = "username";

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected int toolbarIcon() {
        return R.mipmap.ic_launcher;
    }

    @Override
    protected int toolbarTitle() {
        return R.string.search_result;
    }

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(INTENT_KEY_USERNAME)) {
            username = getIntent().getStringExtra(INTENT_KEY_USERNAME);
        }

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ListFragment.newInstance(username)).commit();
    }
}
