package com.kks.teza14.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kks.teza14.R;

import static com.kks.teza14.util.AppConstants.*;

/**
 * Modified by kaungkhantsoe on 12/05/2020.
 *
 * @ideaFrom https://www.programcreek.com/java-api-examples/?code=googlesamples%2Fandroid-NotificationChannels%2Fandroid-NotificationChannels-master%2FApplication%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fandroid%2Fnotificationchannels%2FNotificationHelper.java#
 **/

/**
 * Helper class to manage notification channels, and create notifications.
 */
public class NotificationHelper extends ContextWrapper {

    private static final String TAG = "NotificationHelper";

    private NotificationManagerCompat manager;

    private static boolean isCallInComing = false;

    private CountDownTimer incomingCallCountDownTimer;

    private Vibrator vibrator;

    private String message;

    private String title;

    private Intent intentToGo = null;

    private int icon = R.mipmap.ic_launcher;

    public NotificationHelper(Context base) {
        super(base);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        incomingCallCountDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {

                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(700, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(700);
                }
            }

            public void onFinish() {

                if (intentToGo != null && getLowImportanceNotification(icon, title, message, intentToGo) != null)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNotificationActive(NOTIFICATION_ID_INCOMING_CALL))
                        NotificationHelper.this.showNotification(NOTIFICATION_ID_NORMAL, getLowImportanceNotification(R.drawable.ic_phone_missed_white_24dp, title, message, intentToGo));
                    else if (isNotificationActive())
                        NotificationHelper.this.showNotification(NOTIFICATION_ID_NORMAL, getLowImportanceNotification(R.drawable.ic_phone_missed_white_24dp, title, message, intentToGo));

                removeNotification(NOTIFICATION_ID_INCOMING_CALL);
            }

        };

    }


    /**
     * Get notification with PRIORITY_DEFAULT
     *
     * @param message text to show on notification
     * */
    public NotificationCompat.Builder getLowImportanceNotification(int icon, String title, String message, Intent intent) {

        this.intentToGo = intent;
        this.icon = icon;
        this.message = message;
        this.title = title;

        if (isNotificationEnabled()) {

            return new NotificationCompat.Builder(this, NORMAL_CHANNEL_ID)
                    .setSmallIcon(icon)/* icon should be white with transparent background to show colors */
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message)
                            .setBigContentTitle(title)
                    )
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setColor(Color.RED) /* notification icon color */
                    .setLights(0xff00ff00, 300, 100)
                    .setVibrate(new long[] { 1000, 1000})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(getPendingIntent(NOTIFICATION_ID_NORMAL, intent))
                    .setChannelId(NORMAL_CHANNEL_ID)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true);
        }

        return null;
    }


    /**
     * Get notification with PRIORITY_HIGH
     *
     * @param message text to show on notification
     * */
    public NotificationCompat.Builder getHighImportanceNotification(int icon, String title, String message, Intent intent) {

        this.message = message;
        this.intentToGo = intent;
        this.icon = icon;
        this.title = title;
        this.message = message;

        if (isNotificationEnabled()) {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return new NotificationCompat.Builder(this, INCOMING_CALL_CHANNEL_ID)
                        .setSmallIcon(icon)
                       /*.setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(text)
                                .setBigContentTitle("Incoming Call...")
                        )*/
                        .setContentTitle(title)
                        .setContentText(message + " is calling...")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .addAction(getAction())
                        .setColor(Color.GREEN)
                        .setSound(getSoundUri())
                        .setLights(0xff00ff00, 300, 100)
                        .setVibrate(new long[] { 1000, 1000, 1000})
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setFullScreenIntent(getPendingIntent(NOTIFICATION_ID_INCOMING_CALL, intent), true)
                        .setChannelId(INCOMING_CALL_CHANNEL_ID)
                        .setAutoCancel(false)
                        .setOngoing(true) /* Incoming call behaviour. Cannot be removed with swipe. */
                        .setOnlyAlertOnce(true);
            } else {

                return new NotificationCompat.Builder(this, INCOMING_CALL_CHANNEL_ID)
                        .setSmallIcon(icon)
                        /*.setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(text)
                                .setBigContentTitle("Incoming Call...")
                        )*/
                        .setContentTitle("Incoming Call")
                        .setContentText(message + " is calling...")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .addAction(getAction())
                        .setColor(Color.GREEN)
                        .setSound(getSoundUri())
                        .setLights(0xff00ff00, 300, 100)
                        .setVibrate(new long[] { 1000, 1000, 1000})
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setContentIntent(getPendingIntent(NOTIFICATION_ID_INCOMING_CALL,intent))
                        .setChannelId(INCOMING_CALL_CHANNEL_ID)
                        .setAutoCancel(false)
                        .setOngoing(true)
                        .setOnlyAlertOnce(true);
            }
        }

        return null;
    }

    /**
     * Get sound uri of ringtone stored in raw
     * */
    private Uri getSoundUri() {
        int soundResId = R.raw.myringtone;
        Uri uri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + soundResId); // " android.resource://com.yammobots.caremetelemedicine/raw/myrigntone.mp3 "
        return uri;
    }

    /**
     * Remove notification by notification id
     *
     * @param notificationId notification ID
     * */
    public void removeNotification(int notificationId) {

        if (notificationId == NOTIFICATION_ID_INCOMING_CALL) {
            isCallInComing = false;
            incomingCallCountDownTimer.cancel();
        }

        getManager().cancel(notificationId);
    }

    /**
     * Remove all active notifications
     * */
    public void removeAllNotification() {
        getManager().cancelAll();
    }

    /**
     * Get Pending Intent for task to do on click notification
     */
    private PendingIntent getPendingIntent(int notificationId, Intent intent) {

//        Intent activityIntent = new Intent(this, MainActivity.class);
        removeNotification(notificationId);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    /**
     * Get Action for task to do on click call button
     */
    private NotificationCompat.Action getAction() {

        Intent broadcastIntent = new Intent(this, NotificationBroadcastReceiver.class);
//        broadcastIntent.putExtra("toastMessage", message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action(
                R.drawable.ic_phone_white_24dp,
                "Answer",
                actionIntent
        );
    }

    /**
     * 1. Check if notification is enabled
     * 2. Check if notification channel is enabled
     *
     * @return 'true' if both condition are okay, 'false' if not
     */
    private boolean isNotificationEnabled() {
        if (!getManager().areNotificationsEnabled()) {

            showNotiDisabledDialog();
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (isChannelBlocked(INCOMING_CALL_CHANNEL_ID)) {
                showChannelDisabledDialog(INCOMING_CALL_CHANNEL_ID);
                return false;
            }
            else if (isChannelBlocked(NORMAL_CHANNEL_ID)) {
                showChannelDisabledDialog(NORMAL_CHANNEL_ID);
                return false;
            }
        }
        return true;
    }

    private void showNotiDisabledDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notification is disabled")
                .setMessage("Please enable notification in setting or the application will not work properly")
                .setPositiveButton("Ok", (dialog, which) -> openNotificationSettings())
                .setNegativeButton("Cancel", null).show();
    }

    private void showChannelDisabledDialog(String channelId) {
        new AlertDialog.Builder(this)
                .setTitle("Notification of the topic is disabled")
                .setMessage("Please enable notification in setting or the application will not work properly")
                .setPositiveButton("Ok", (dialog, which) -> openChannelSettings(channelId))
                .setNegativeButton("Cancel", null).show();
    }

    /**
     * Send a notification.
     *
     * @param id           The ID of the notification
     * @param notification The notification object
     */
    public void showNotification(int id, NotificationCompat.Builder notification) {

        if (id == NOTIFICATION_ID_INCOMING_CALL) {
            isCallInComing = true;
            incomingCallCountDownTimer.start();
        }

        getManager().notify(id, notification.build());
    }

    /**
     * Open notification channel to change notification setting if the notification is disabled
     */
    private void openNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            startActivity(intent);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    /**
     * Check if notification channel is disabled
     *
     * @param chanelId The ID of the notification channel
     * @return 'true' if the channel is blocked, 'false' if not
     */
    @RequiresApi(26)
    private boolean isChannelBlocked(String chanelId) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        NotificationChannel channel = notificationManager.getNotificationChannel(chanelId);

        return channel != null &&
                channel.getImportance() == NotificationManager.IMPORTANCE_NONE;
    }

    /**
     * Open channel setting to change channel settings if channel is disabled
     *
     * @param channelId The ID of the notification channel
     */
    private void openChannelSettings(String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        startActivity(intent);
    }

    /**
     * Get the notification manager.
     * <p>
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    public NotificationManagerCompat getManager() {
        if (manager == null) {
            manager = NotificationManagerCompat.from(this);
        }
        return manager;
    }

    /**
     * Check active status of notification by ID
     *
     * @param notificationID notification ID
     * @return active status of Notification
     * */
    @RequiresApi(23)
    public boolean isNotificationActive(int notificationID) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        for (StatusBarNotification noti: notifications) {
            if(notificationID == noti.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check active status of notification
     * */
    public boolean isNotificationActive() {
        return isCallInComing;
    }

}