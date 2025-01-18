package com.example.grandmaapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private TelephonyManager telephonyManager;
    private String incomingPhoneNumber = "";
    private static final int REQUEST_CODE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 전화 권한 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS}, REQUEST_CODE_PERMISSIONS);
        } else {
            // 권한이 이미 있으면 전화 상태 리스너를 등록
            initializePhoneStateListener();
        }
    }

    // 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인되었으면 전화 상태 리스너 등록
                initializePhoneStateListener();
            } else {
                // 권한이 거부되었을 때 처리
                Toast.makeText(this, "권한이 거부되었습니다. 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 전화 상태 리스너 등록
    private void initializePhoneStateListener() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);

                // 전화가 올 때
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    incomingPhoneNumber = incomingNumber;
                    sendSmsToMyNumber(incomingPhoneNumber);  // 전화번호를 내 번호로 문자로 전송
                }
            }
        };

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    // 내 번호로 전화번호 문자 전송
    private void sendSmsToMyNumber(String phoneNumber) {
        String myPhoneNumber = "01023953962";  // 내 번호로 수정
        SmsManager smsManager = SmsManager.getDefault();

        try {
            smsManager.sendTextMessage(myPhoneNumber, null, "모르는 전화가 왔습니다: " + phoneNumber, null, null);
            Toast.makeText(this, "전화번호: " + phoneNumber + " 를 문자로 전송했습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "문자 전송 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
