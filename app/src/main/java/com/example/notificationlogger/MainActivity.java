package com.example.notificationlogger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "NotifyLogger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText tokenInput = findViewById(R.id.tokenInput);
        EditText chatIdInput = findViewById(R.id.chatIdInput);
        Button saveBtn = findViewById(R.id.saveConfigBtn);
        Button permBtn = findViewById(R.id.checkPermissionBtn);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        
        tokenInput.setText(prefs.getString("bot_token", "8265963835:AAEIknEuysIsTWDwx1_bCFKOF1YIpLCoRT8"));
        chatIdInput.setText(prefs.getString("chat_id", "1786564127"));

        saveBtn.setOnClickListener(v -> {
            String token = tokenInput.getText().toString().trim();
            String chatId = chatIdInput.getText().toString().trim();
            
            prefs.edit().putString("bot_token", token)
                        .putString("chat_id", chatId).apply();
            
            // Force re-registering the service as a nudge
            toggleService(this);
            
            // Test telegram connection
            new TelegramBotManager(token, chatId).sendStatusMessage("🚀 RE-REGISTERED: Bot is listening.");
            
            Toast.makeText(this, "Config Saved! Re-starting Listener Service.", Toast.LENGTH_SHORT).show();
        });

        permBtn.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        });
    }

    // This trick disables and then re-enables the component to force Android to bind it
    private void toggleService(android.content.Context context) {
        ComponentName cn = new ComponentName(context, NotificationCaptureService.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        
        Log.d(TAG, "Toggled Service to Force Rebind");
    }
}