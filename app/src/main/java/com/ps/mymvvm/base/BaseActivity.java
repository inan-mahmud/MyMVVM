package com.ps.mymvvm.base;

import android.os.Bundle;
import android.widget.TextView;

import com.ps.mymvvm.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public abstract class BaseActivity extends DaggerAppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitleTextView;

    @LayoutRes
    protected abstract int layoutRes();

    @DrawableRes
    protected abstract int toolbarIcon();

    @StringRes
    protected abstract int toolbarTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        ButterKnife.bind(this);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Display icon in the toolbar
        //activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        //activity.getSupportActionBar().setLogo(toolbarIcon();
        //activity.getSupportActionBar().setDisplayUseLogoEnabled(true);

        // If you want the status bar to be entirely transparent for KitKat and above
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        toolbarTitleTextView.setText(toolbarTitle());
    }
}