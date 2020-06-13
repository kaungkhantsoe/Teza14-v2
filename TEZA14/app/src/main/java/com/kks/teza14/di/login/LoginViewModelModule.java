package com.kks.teza14.di.login;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 22/05/2020.
 **/
@Module
public abstract class LoginViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

}
