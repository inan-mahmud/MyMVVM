package com.ps.mymvvm.ui.main;

import com.ps.mymvvm.ui.details.DetailsFragment;
import com.ps.mymvvm.ui.list.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
