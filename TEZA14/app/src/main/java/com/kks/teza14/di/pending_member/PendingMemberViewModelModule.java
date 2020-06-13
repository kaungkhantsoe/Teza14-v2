package com.kks.teza14.di.pending_member;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.pending_member.PendingMemberViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 01/06/2020.
 **/
@Module
public abstract class PendingMemberViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PendingMemberViewModel.class)
    abstract ViewModel bindPendingMemberViewModel(PendingMemberViewModel pendingMemberViewModel);
}
