package com.kks.teza14.di.main.profile;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/

@Module
public abstract class ProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindAboutViewMode(ProfileViewModel profileViewModel);
}
