package com.example.grandmaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // 부팅 후 자동으로 PhoneService를 시작
            Intent serviceIntent = new Intent(context, PhoneService.class);
            context.startService(serviceIntent);
            Log.d("BootReceiver", "부팅 후 서비스 시작");
        }
    }
}
