package com.kks.teza14.di.main;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);
}
