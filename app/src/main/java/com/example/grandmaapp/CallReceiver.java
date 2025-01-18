package com.example.grandmaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            // 전화가 오면 해당 전화번호를 가져옴
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (phoneNumber != null) {
                Log.d("CallReceiver", "전화번호: " + phoneNumber);
                sendSMS(context, phoneNumber);
            }
        }
    }

    // 문자 메시지 보내기
    private void sendSMS(Context context, String phoneNumber) {
        String message = phoneNumber + " 번호로 전화가 왔습니다. 확인해 주세요."; // 메시지 내용
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("01023953962", null, message, null, null);  // 내 번호로 수정
    }
}
