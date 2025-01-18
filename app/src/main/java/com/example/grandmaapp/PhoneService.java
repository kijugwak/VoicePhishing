package com.example.grandmaapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PhoneService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.d("PhoneService", "서비스 시작됨");
            // 서비스가 시작되면 CallReceiver를 통해 전화 상태를 감지하도록 브로드캐스트 발송
            Intent receiverIntent = new Intent(this, CallReceiver.class);
            sendBroadcast(receiverIntent);
        } catch (Exception e) {
            Log.e("PhoneService", "서비스 실행 중 오류 발생: " + e.getMessage());
        }
        return START_STICKY; // 서비스가 종료되지 않도록 설정
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("PhoneService", "서비스 종료됨");
    }
}
