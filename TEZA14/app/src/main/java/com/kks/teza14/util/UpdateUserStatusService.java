package com.kks.teza14.util;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;


import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kaungkhantsoe on 28/05/2020.
 **/
public class UpdateUserStatusService extends IntentService {

    public UpdateUserStatusService() {
        super("UpdateUserStatusService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

//    private static final String TAG = "UpdateUserStatusService";
//
//    @Inject
//    UpdateUserStatusApi updateUserStatusApi;
//
//    @Inject
//    SharePreferenceHelper sharePreferenceHelper;
//
//    MediatorLiveData<Resource<String>> resourceData;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        AndroidInjection.inject(this);
//    }
//
//    public UpdateUserStatusService() {
//        super("UpdateUserStatusService");
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//
////        String msg = intent.getStringExtra(PARAM_IN_MSG);
//
//        updateStatus();
//
//        Log.e(TAG, "onHandleIntent: " + updateUserStatusApi + " " + sharePreferenceHelper.getUserName());
//    }
//
//    private MediatorLiveData<Resource<String>> updateStatus() {
//        resourceData = new MediatorLiveData<>();
//        resourceData.setValue(Resource.loading(null));
//
//        LiveData<Resource<String>> source = LiveDataReactiveStreams.fromPublisher(
//                updateUserStatusApi.updateUserStatus(sharePreferenceHelper.getUserAppId())
//                        .onErrorReturn(new Function<Throwable, String>() {
//                            @Override
//                            public String apply(Throwable throwable) throws Exception {
//                                return "fail";
//                            }
//                        })
//
//                        .map(new Function<String, Resource<String>>() {
//                            @Override
//                            public Resource<String> apply(String s) throws Exception {
//                                if (s != null)
//                                    if (s.equals("fail"))
//                                        return Resource.error("Error updating status", null);
//                                return Resource.success(s);
//                            }
//                        })
//                        .subscribeOn(Schedulers.io())
//        );
//
//        resourceData.addSource(source, new Observer<Resource<String>>() {
//            @Override
//            public void onChanged(Resource<String> stringResource) {
//                resourceData.setValue(stringResource);
//                resourceData.removeSource(source);
//            }
//        });
//
//        return resourceData;
//
//    }
}
