package com.ps.mymvvm.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
@Module
public abstract class ContextModule {

    @Binds
    abstract Context provideContext(Application application);
}

