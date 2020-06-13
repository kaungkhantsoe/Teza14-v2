package com.kks.teza14.di.edit_member;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.edit_member.EditMemberViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 31/05/2020.
 **/

@Module
public abstract class EditMemberViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditMemberViewModel.class)
    abstract ViewModel bindEditMemberViewModel(EditMemberViewModel editMemberViewModel);
}
