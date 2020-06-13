package com.kks.teza14.di.member_detail;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.member_detail.MemberDetailViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/

@Module
public abstract class MemberDetailViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MemberDetailViewModel.class)
    abstract ViewModel bindMemberDetailViewModel(MemberDetailViewModel memberDetailViewModel);
}
