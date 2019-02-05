package com.ps.mymvvm.di.component;

import android.app.Application;

import com.ps.mymvvm.base.BaseApplication;
import com.ps.mymvvm.di.module.ActivityBindingModule;
import com.ps.mymvvm.di.module.ContextModule;
import com.ps.mymvvm.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
@Singleton
@Component(modules = {ContextModule.class, NetworkModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }
}
