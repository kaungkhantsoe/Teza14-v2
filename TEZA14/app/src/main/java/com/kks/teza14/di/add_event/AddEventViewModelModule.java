package com.kks.teza14.di.add_event;

import androidx.lifecycle.ViewModel;

import com.kks.teza14.di.ViewModelKey;
import com.kks.teza14.ui.add_event.AddEventViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 04/06/2020.
 **/
@Module
public abstract class AddEventViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddEventViewModel.class)
    abstract ViewModel bindAddEventViewModel(AddEventViewModel addEventViewModel);
}
