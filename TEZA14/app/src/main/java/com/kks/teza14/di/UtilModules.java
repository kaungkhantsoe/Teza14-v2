package com.kks.teza14.di;

import com.kks.teza14.util.AppMessagingService;
import com.kks.teza14.util.NotificationBroadcastReceiver;
import com.kks.teza14.util.UpdateUserStatusService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kaungkhantsoe on 27/05/2020.
 **/
@Module
public abstract class UtilModules {

    @ContributesAndroidInjector
    abstract NotificationBroadcastReceiver contributeNotificationBroadcastReceiver();

    @ContributesAndroidInjector
    abstract AppMessagingService contributeAppMessagingService();

    @ContributesAndroidInjector
    abstract UpdateUserStatusService contributeUpdateUserStatusService();
}
