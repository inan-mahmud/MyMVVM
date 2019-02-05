package com.ps.mymvvm.di.module;

import com.ps.mymvvm.di.util.ViewModelKey;
import com.ps.mymvvm.ui.details.DetailsViewModel;
import com.ps.mymvvm.ui.list.ListViewModel;
import com.ps.mymvvm.util.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Prokash Sarkar on 2/5/19.
 */
//@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(DetailsViewModel detailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}