package com.ps.mymvvm.di.module;

import com.ps.mymvvm.ui.lookup.LookupActivity;
import com.ps.mymvvm.ui.main.MainActivity;
import com.ps.mymvvm.ui.main.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    abstract LookupActivity bindLookupActivity();

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
