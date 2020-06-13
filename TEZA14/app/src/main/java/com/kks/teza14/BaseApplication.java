package com.kks.teza14;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import com.kks.teza14.custom_control.AndroidCommonSetup;
import com.kks.teza14.di.DaggerAppComponent;

import java.util.ArrayList;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

import static com.kks.teza14.util.AppConstants.*;

public class BaseApplication extends DaggerApplication {

    private static final String TAG = "BaseApplication";
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        /*
        * Initialize Firebase sdk for analytic
        * */
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        /*
        * Initialize Facebook sdk for login
        * */
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        /*
        * Initialize Common setup for MM text
        * */
        AndroidCommonSetup.getInstance().init(getApplicationContext());

        /*
        * Create notification channels
        * */
        createNotificationChannels();

        initMemberPhotos();

        initFamilyPhotos();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /*
            Chats and calls channel. Show as heads-up notification
             */
            NotificationChannel incomingCallChannel = new NotificationChannel(
                    INCOMING_CALL_CHANNEL_ID,
                    "Chats and Calls",
                    NotificationManager.IMPORTANCE_HIGH
            );

            /*
            Chats and calls notification
             */
            incomingCallChannel.setDescription("Chats and Calls notification channel");
            incomingCallChannel.enableLights(true);
            incomingCallChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            incomingCallChannel.enableVibration(true);
            incomingCallChannel.setVibrationPattern(new long[]{1000, 1000});

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build();
            incomingCallChannel.setSound(getCustomRingtoneUri(),att);
//            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE),att); // Default ring tone


            /*
            Other notifications channel
             */
            NotificationChannel normalChannel = new NotificationChannel(
                    NORMAL_CHANNEL_ID,
                    "Others",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            /*
            Other notification
             */
            normalChannel.setDescription("Other notifications");
            normalChannel.enableLights(true);
            normalChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            normalChannel.enableVibration(true);
            normalChannel.setVibrationPattern(new long[]{1000, 1000});
            normalChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),att); // Default ring tone


            NotificationManager manager = getSystemService(NotificationManager.class);

//            NotificationManager.Policy policy = new NotificationManager.Policy(
//
//            )
//            manager.setNotificationPolicy();
            if (manager != null) {
                manager.createNotificationChannel(normalChannel);
                manager.createNotificationChannel(incomingCallChannel);

            }
        }
    }

    public String isActivityVisible() {
        return ProcessLifecycleOwner.get().getLifecycle().getCurrentState().name();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        //App in background

        Log.e(TAG, "************* backgrounded");
        Log.e(TAG, "************* ${isActivityVisible()}");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        Log.e(TAG, "************* foregrounded");
        Log.e(TAG, "************* ${isActivityVisible()}");
        // App in foreground
    }

    private Uri getCustomRingtoneUri() {
        int soundResId = R.raw.myringtone;
        return Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + soundResId);
    }

    private void initFamilyPhotos() {
        familyPhotoArray = new ArrayList<>();
        familyPhotoArray.add(R.drawable.f1);
        familyPhotoArray.add(R.drawable.f2);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f4);
        familyPhotoArray.add(R.drawable.f5);
        familyPhotoArray.add(R.drawable.f6);
        familyPhotoArray.add(R.drawable.f7);
        familyPhotoArray.add(R.drawable.f8);
        familyPhotoArray.add(R.drawable.f9);
        familyPhotoArray.add(R.drawable.f10);
        familyPhotoArray.add(R.drawable.f11);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f13);
        familyPhotoArray.add(R.drawable.f14);
        familyPhotoArray.add(R.drawable.f15);
        familyPhotoArray.add(R.drawable.f16);
        familyPhotoArray.add(R.drawable.f17);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f19);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f21);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f23);
        familyPhotoArray.add(R.drawable.f24);
        familyPhotoArray.add(R.drawable.f25);
        familyPhotoArray.add(R.drawable.f26);
        familyPhotoArray.add(R.drawable.f27);
        familyPhotoArray.add(R.drawable.f28);
        familyPhotoArray.add(R.drawable.f29);
        familyPhotoArray.add(R.drawable.f30);
        familyPhotoArray.add(R.drawable.f31);
        familyPhotoArray.add(R.drawable.f32);
        familyPhotoArray.add(R.drawable.f33);
        familyPhotoArray.add(R.drawable.f34);
        familyPhotoArray.add(R.drawable.f35);
        familyPhotoArray.add(R.drawable.f36);
        familyPhotoArray.add(R.drawable.f37);
        familyPhotoArray.add(R.drawable.f38);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f40);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f44);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f46);
        familyPhotoArray.add(R.drawable.f47);
        familyPhotoArray.add(R.drawable.f48);
        familyPhotoArray.add(R.drawable.f49);
        familyPhotoArray.add(R.drawable.f50);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f54);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f57);
        familyPhotoArray.add(R.drawable.f58);
        familyPhotoArray.add(R.drawable.f59);
        familyPhotoArray.add(R.drawable.f60);
        familyPhotoArray.add(R.drawable.f61);
        familyPhotoArray.add(R.drawable.f62);
        familyPhotoArray.add(R.drawable.f63);
        familyPhotoArray.add(R.drawable.f64);
        familyPhotoArray.add(R.drawable.f65);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f68);
        familyPhotoArray.add(R.drawable.f69);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f72);
        familyPhotoArray.add(R.drawable.f73);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f75);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f77);
        familyPhotoArray.add(R.drawable.f78);
        familyPhotoArray.add(R.drawable.f79);
        familyPhotoArray.add(R.drawable.f80);
        familyPhotoArray.add(R.drawable.f81);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f84);
        familyPhotoArray.add(R.drawable.f85);
        familyPhotoArray.add(R.drawable.f86);
        familyPhotoArray.add(R.drawable.f87);
        familyPhotoArray.add(R.drawable.f88);
        familyPhotoArray.add(R.drawable.f89);
        familyPhotoArray.add(R.drawable.f90);
        familyPhotoArray.add(R.drawable.f91);
        familyPhotoArray.add(R.drawable.f92);
        familyPhotoArray.add(R.drawable.f93);
        familyPhotoArray.add(R.drawable.f94);
        familyPhotoArray.add(R.drawable.f95);
        familyPhotoArray.add(R.drawable.f96);
        familyPhotoArray.add(R.drawable.f97);
        familyPhotoArray.add(R.drawable.f98);
        familyPhotoArray.add(R.drawable.f99);
        familyPhotoArray.add(R.drawable.f100);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f103);
        familyPhotoArray.add(R.drawable.f104);
        familyPhotoArray.add(R.drawable.f105);
        familyPhotoArray.add(R.drawable.f106);
        familyPhotoArray.add(R.drawable.f107);
        familyPhotoArray.add(R.drawable.f108);
        familyPhotoArray.add(R.drawable.f109);
        familyPhotoArray.add(R.drawable.f110);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f112);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f114);
        familyPhotoArray.add(R.drawable.f115);
        familyPhotoArray.add(R.drawable.f116);
        familyPhotoArray.add(R.drawable.f117);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f119);
        familyPhotoArray.add(R.drawable.f120);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f122);
        familyPhotoArray.add(R.drawable.f123);
        familyPhotoArray.add(R.drawable.f124);
        familyPhotoArray.add(R.drawable.f125);
        familyPhotoArray.add(R.drawable.f126);
        familyPhotoArray.add(R.drawable.f127);
        familyPhotoArray.add(R.drawable.f128);
        familyPhotoArray.add(R.drawable.f129);
        familyPhotoArray.add(R.drawable.f130);
        familyPhotoArray.add(R.drawable.f131);
        familyPhotoArray.add(R.drawable.f132);
        familyPhotoArray.add(R.drawable.f133);
        familyPhotoArray.add(R.drawable.f134);
        familyPhotoArray.add(R.drawable.f135);
        familyPhotoArray.add(R.drawable.f136);
        familyPhotoArray.add(R.drawable.f137);
        familyPhotoArray.add(R.drawable.f138);
        familyPhotoArray.add(null);
        familyPhotoArray.add(null);
        familyPhotoArray.add(R.drawable.f141);
    }

    private void initMemberPhotos() {
        memberPhotoArray = new ArrayList<>();
        memberPhotoArray.add(R.drawable.m1);
        memberPhotoArray.add(R.drawable.m2);
        memberPhotoArray.add(R.drawable.m3);
        memberPhotoArray.add(R.drawable.m4);
        memberPhotoArray.add(R.drawable.m5);
        memberPhotoArray.add(R.drawable.m6);
        memberPhotoArray.add(R.drawable.m7);
        memberPhotoArray.add(R.drawable.m8);
        memberPhotoArray.add(R.drawable.m9);
        memberPhotoArray.add(R.drawable.m10);
        memberPhotoArray.add(R.drawable.m11);
        memberPhotoArray.add(R.drawable.m12);
        memberPhotoArray.add(R.drawable.m13);
        memberPhotoArray.add(R.drawable.m14);
        memberPhotoArray.add(R.drawable.m15);
        memberPhotoArray.add(R.drawable.m16);
        memberPhotoArray.add(R.drawable.m17);
        memberPhotoArray.add(R.drawable.m18);
        memberPhotoArray.add(R.drawable.m19);
        memberPhotoArray.add(R.drawable.m20);
        memberPhotoArray.add(R.drawable.m21);
        memberPhotoArray.add(R.drawable.m22);
        memberPhotoArray.add(R.drawable.m23);
        memberPhotoArray.add(R.drawable.m24);
        memberPhotoArray.add(R.drawable.m25);
        memberPhotoArray.add(R.drawable.m26);
        memberPhotoArray.add(R.drawable.m27);
        memberPhotoArray.add(R.drawable.m28);
        memberPhotoArray.add(R.drawable.m29);
        memberPhotoArray.add(R.drawable.m30);
        memberPhotoArray.add(R.drawable.m31);
        memberPhotoArray.add(R.drawable.m32);
        memberPhotoArray.add(R.drawable.m33);
        memberPhotoArray.add(R.drawable.m34);
        memberPhotoArray.add(R.drawable.m35);
        memberPhotoArray.add(R.drawable.m36);
        memberPhotoArray.add(R.drawable.m37);
        memberPhotoArray.add(R.drawable.m38);
        memberPhotoArray.add(R.drawable.m39);
        memberPhotoArray.add(R.drawable.m40);
        memberPhotoArray.add(null);
        memberPhotoArray.add(R.drawable.m42);
        memberPhotoArray.add(R.drawable.m43);
        memberPhotoArray.add(R.drawable.m44);
        memberPhotoArray.add(R.drawable.m45);
        memberPhotoArray.add(R.drawable.m46);
        memberPhotoArray.add(R.drawable.m47);
        memberPhotoArray.add(R.drawable.m48);
        memberPhotoArray.add(R.drawable.m49);
        memberPhotoArray.add(R.drawable.m50);
        memberPhotoArray.add(R.drawable.m51);
        memberPhotoArray.add(R.drawable.m52);
        memberPhotoArray.add(R.drawable.m53);
        memberPhotoArray.add(R.drawable.m54);
        memberPhotoArray.add(R.drawable.m55);
        memberPhotoArray.add(R.drawable.m56);
        memberPhotoArray.add(R.drawable.m57);
        memberPhotoArray.add(R.drawable.m58);
        memberPhotoArray.add(R.drawable.m59);
        memberPhotoArray.add(R.drawable.m60);
        memberPhotoArray.add(R.drawable.m61);
        memberPhotoArray.add(R.drawable.m62);
        memberPhotoArray.add(R.drawable.m63);
        memberPhotoArray.add(R.drawable.m64);
        memberPhotoArray.add(R.drawable.m65);
        memberPhotoArray.add(R.drawable.m66);
        memberPhotoArray.add(R.drawable.m67);
        memberPhotoArray.add(R.drawable.m68);
        memberPhotoArray.add(R.drawable.m69);
        memberPhotoArray.add(R.drawable.m70);
        memberPhotoArray.add(R.drawable.m71);
        memberPhotoArray.add(R.drawable.m72);
        memberPhotoArray.add(R.drawable.m73);
        memberPhotoArray.add(R.drawable.m74);
        memberPhotoArray.add(R.drawable.m75);
        memberPhotoArray.add(R.drawable.m76);
        memberPhotoArray.add(R.drawable.m77);
        memberPhotoArray.add(R.drawable.m78);
        memberPhotoArray.add(R.drawable.m79);
        memberPhotoArray.add(R.drawable.m80);
        memberPhotoArray.add(R.drawable.m81);
        memberPhotoArray.add(R.drawable.m82);
        memberPhotoArray.add(R.drawable.m83);
        memberPhotoArray.add(R.drawable.m84);
        memberPhotoArray.add(R.drawable.m85);
        memberPhotoArray.add(R.drawable.m86);
        memberPhotoArray.add(R.drawable.m87);
        memberPhotoArray.add(R.drawable.m88);
        memberPhotoArray.add(R.drawable.m89);
        memberPhotoArray.add(R.drawable.m90);
        memberPhotoArray.add(R.drawable.m91);
        memberPhotoArray.add(R.drawable.m92);
        memberPhotoArray.add(R.drawable.m93);
        memberPhotoArray.add(R.drawable.m94);
        memberPhotoArray.add(R.drawable.m95);
        memberPhotoArray.add(R.drawable.m96);
        memberPhotoArray.add(R.drawable.m97);
        memberPhotoArray.add(R.drawable.m98);
        memberPhotoArray.add(R.drawable.m99);
        memberPhotoArray.add(R.drawable.m100);
        memberPhotoArray.add(R.drawable.m101);
        memberPhotoArray.add(R.drawable.m102);
        memberPhotoArray.add(R.drawable.m103);
        memberPhotoArray.add(R.drawable.m104);
        memberPhotoArray.add(R.drawable.m105);
        memberPhotoArray.add(R.drawable.m106);
        memberPhotoArray.add(R.drawable.m107);
        memberPhotoArray.add(R.drawable.m108);
        memberPhotoArray.add(R.drawable.m109);
        memberPhotoArray.add(R.drawable.m110);
        memberPhotoArray.add(R.drawable.m111);
        memberPhotoArray.add(R.drawable.m112);
        memberPhotoArray.add(R.drawable.m113);
        memberPhotoArray.add(R.drawable.m114);
        memberPhotoArray.add(R.drawable.m115);
        memberPhotoArray.add(R.drawable.m116);
        memberPhotoArray.add(R.drawable.m117);
        memberPhotoArray.add(R.drawable.m118);
        memberPhotoArray.add(R.drawable.m119);
        memberPhotoArray.add(R.drawable.m120);
        memberPhotoArray.add(R.drawable.m121);
        memberPhotoArray.add(R.drawable.m122);
        memberPhotoArray.add(R.drawable.m123);
        memberPhotoArray.add(R.drawable.m124);
        memberPhotoArray.add(R.drawable.m125);
        memberPhotoArray.add(R.drawable.m126);
        memberPhotoArray.add(R.drawable.m127);
        memberPhotoArray.add(R.drawable.m128);
        memberPhotoArray.add(R.drawable.m129);
        memberPhotoArray.add(R.drawable.m130);
        memberPhotoArray.add(R.drawable.m131);
        memberPhotoArray.add(R.drawable.m132);
        memberPhotoArray.add(R.drawable.m133);
        memberPhotoArray.add(R.drawable.m134);
        memberPhotoArray.add(R.drawable.m135);
        memberPhotoArray.add(R.drawable.m136);
        memberPhotoArray.add(R.drawable.m137);
        memberPhotoArray.add(R.drawable.m138);
        memberPhotoArray.add(R.drawable.m139);
        memberPhotoArray.add(R.drawable.m140);
        memberPhotoArray.add(R.drawable.m141);

    }
}
