package com.kks.teza14.di.main.home;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.main.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/

@Module
public abstract class HomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

}
