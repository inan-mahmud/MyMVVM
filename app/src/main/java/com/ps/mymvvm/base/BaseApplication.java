package com.ps.mymvvm.base;

import com.ps.mymvvm.di.component.ApplicationComponent;
import com.ps.mymvvm.di.component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder()
                .application(this)
                .build();
        component.inject(this);

        return component;
    }
}
