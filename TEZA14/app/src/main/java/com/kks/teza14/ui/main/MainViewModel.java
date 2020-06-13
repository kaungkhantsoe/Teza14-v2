package com.kks.teza14.ui.main;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

/**
 * Created by kaungkhantsoe on 30/05/2020.
 **/
public class MainViewModel extends ViewModel {

    private MainDelegate mainDelegate;

    public void setHomeFragDelegate(MainDelegate mainDelegate) {
        this.mainDelegate = mainDelegate;
    }

    public MainDelegate getMainDelegate() {
        return this.mainDelegate;
    }

    @Inject
    public MainViewModel() {

    }
}
