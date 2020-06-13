package com.kks.teza14.di.main;

import com.kks.teza14.di.main.profile.ProfileModule;
import com.kks.teza14.di.main.profile.ProfileScope;
import com.kks.teza14.di.main.profile.ProfileViewModelModule;
import com.kks.teza14.di.main.event.EventModule;
import com.kks.teza14.di.main.event.EventScope;
import com.kks.teza14.di.main.event.EventViewModelModule;
import com.kks.teza14.di.main.home.HomeModule;
import com.kks.teza14.di.main.home.HomeScope;
import com.kks.teza14.di.main.home.HomeViewModelModule;
import com.kks.teza14.ui.main.profile.ProfileFragment;
import com.kks.teza14.ui.main.event.EventFragment;
import com.kks.teza14.ui.main.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kaungkhantsoe on 23/05/2020.
 **/

@Module
public abstract class MainFragmentBuildersModule {

    @HomeScope
    @ContributesAndroidInjector(
            modules = {
                    HomeModule.class,
                    HomeViewModelModule.class
            }
    )
    abstract HomeFragment contributeHomeFragment();

    @EventScope
    @ContributesAndroidInjector(
            modules = {
                    EventModule.class,
                    EventViewModelModule.class
            }
    )
    abstract EventFragment contributeEventFragment();

    @ProfileScope
    @ContributesAndroidInjector(
            modules = {
                    ProfileModule.class,
                    ProfileViewModelModule.class
            }
    )
    abstract ProfileFragment contributeAboutFragment();

}
