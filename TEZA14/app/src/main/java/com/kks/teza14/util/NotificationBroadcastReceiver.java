package com.kks.teza14.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MediatorLiveData;

import com.kks.teza14.models.base.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.android.DaggerBroadcastReceiver;

import static com.kks.teza14.util.AppConstants.NOTIFICATION_ID_INCOMING_CALL;

/**
 * Created by kaungkhantsoe on 15/05/2020.
 **/

@Singleton
public class NotificationBroadcastReceiver extends DaggerBroadcastReceiver {

    private static final String TAG = "NotificationBroadcastRe";

    @Inject
    NotificationHelper notificationHelper;

    private MediatorLiveData<Resource<String>> resourceData;

    @Override
    public void onReceive(Context context, Intent intent) {
        /**
         * Uncomment to show toast
         * */
//        String message = intent.getStringExtra("toastMessage");
//        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        super.onReceive(context, intent);
        Toast.makeText(context, "Call answered...", Toast.LENGTH_SHORT).show();

        notificationHelper.removeNotification(NOTIFICATION_ID_INCOMING_CALL);

//        /**
//         * Uncomment to show chat
//         * */
//        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
//        if (remoteInput != null) {
//            CharSequence replyText = remoteInput.getCharSequence("key_text_reply");
//            Message answer = new Message(replyText,null);
//            MainActivity.MESSAGES.add(answer);
//
//            MainActivity.sendNotificationOnChannel1(context);
//        }
    }



}
