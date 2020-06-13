package com.kks.teza14.util;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kks.teza14.R;
import com.kks.teza14.events.AppEvents;
import com.kks.teza14.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.kks.teza14.util.AppConstants.NOTIFICATION_ID_INCOMING_CALL;
import static com.kks.teza14.util.AppConstants.NOTIFICATION_ID_NORMAL;
import static com.kks.teza14.util.AppConstants.NOTI_TYPE_CALL;
import static com.kks.teza14.util.AppConstants.NOTI_TYPE_CHECK;

/**
 * Created by kaungkhantsoe on 12/05/2020.
 **/
public class AppMessagingService extends FirebaseMessagingService {

    private static final String TAG = "AppFirebaseMessagingSer";

    @Inject
    NotificationHelper notificationHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        EventBus.getDefault().post(new AppEvents.NotificationEvent(s));
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        NotificationModel notificationModel = new NotificationModel();
//        notificationModel.setTitle(remoteMessage.getData().get("title"));
//        notificationModel.setBody(remoteMessage.getData().get("body"));
//
//        Log.e(TAG, "onMessageReceived: " + notificationModel.getTitle() + " " + notificationModel.getBody() );
//
//        switch (notificationModel.getTitle()) {
//            case NOTI_TYPE_CALL:
//                Log.e(TAG, "onMessageReceived: important" );
//                if (notificationHelper.getHighImportanceNotification(R.mipmap.ic_launcher, "", "", new Intent(this, MainActivity.class)) != null)
//                    notificationHelper.showNotification(NOTIFICATION_ID_INCOMING_CALL,
//                            notificationHelper.getHighImportanceNotification(R.drawable.ic_phone_white_24dp, "Incoming call from", notificationModel.getBody(), new Intent(this, MainActivity.class)));
//
//                break;
//
//            case NOTI_TYPE_CHECK:
//
//                Intent msgIntent = new Intent(this, UpdateUserStatusService.class);
////                msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, strInputMsg);
//                startService(msgIntent);
//                break;
//
//            default:
//                Log.e(TAG, "onMessageReceived: normal" );
//                if (notificationHelper.getLowImportanceNotification(R.mipmap.ic_launcher,"","",new Intent(this,MainActivity.class)) != null)
//                    notificationHelper.showNotification(NOTIFICATION_ID_NORMAL,
//                            notificationHelper.getLowImportanceNotification(R.mipmap.ic_launcher,
//                                    notificationModel.getTitle(),
//                                    notificationModel.getBody(),
//                                    new Intent(this,MainActivity.class)));
//                break;
//        }

    }




}
