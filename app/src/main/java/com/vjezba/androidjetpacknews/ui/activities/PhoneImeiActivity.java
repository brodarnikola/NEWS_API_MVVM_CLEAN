package com.vjezba.androidjetpacknews.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.vjezba.mvpcleanarhitecturefactorynews.R;

public class PhoneImeiActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_imei);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(com.vjezba.mvpcleanarhitecturefactorynews.presentation.activities.PhoneImeiActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String deviceId;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        deviceId = Settings.Secure.getString(
                                getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                    } else {
                        TelephonyManager mTelephony = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                        if (mTelephony.getDeviceId() != null) {
                            deviceId = mTelephony.getDeviceId();
                        } else {
                            deviceId = Settings.Secure.getString(
                                    getApplicationContext().getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                        }
                    }

                    StringBuffer deviceIdReverseOrder = new StringBuffer(deviceId);
                    Log.d("ISPIS", "IMEI phone: " + deviceIdReverseOrder ) ;
                    deviceIdReverseOrder.reverse();
                    Log.d("ISPIS", "IMEI phone reversed: " + deviceIdReverseOrder );

                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}