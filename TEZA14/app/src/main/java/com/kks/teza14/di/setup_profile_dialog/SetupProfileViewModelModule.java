package com.kks.teza14.di.setup_profile_dialog;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.setup_profile_dialog.SetupProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 03/06/2020.
 **/

@Module
public abstract class SetupProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SetupProfileViewModel.class)
    abstract ViewModel bindSetupProfileViewModel(SetupProfileViewModel setupProfileViewModel);
}
