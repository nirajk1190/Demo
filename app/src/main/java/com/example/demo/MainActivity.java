package com.example.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.demo.utils.SharedPrefUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private AppCompatButton btnReadContacts,btnReadSMS,btnCallLog;
    /**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        init();
    }

    private void init() {
        btnReadContacts = findViewById(R.id.btnReadContacts);
        btnReadSMS = findViewById(R.id.btnReadSMS);
        btnCallLog = findViewById(R.id.btnCallLog);

        btnReadContacts.setOnClickListener(this);
        btnReadSMS.setOnClickListener(this);
        btnCallLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnReadContacts){
            showContacts();
        }if (v.getId()==R.id.btnReadSMS){
            showSMS();
        }if (v.getId()==R.id.btnCallLog){
            showCallLogs();
        }
    }

    private void showCallLogs() {
        startActivity(new Intent(MainActivity.this, CallHistoryActivity.class));
        finish();
    }

    private void showSMS() {
        startActivity(new Intent(MainActivity.this, SMSActivity.class));
        finish();
    }

    private void showContacts() {
        startActivity(new Intent(MainActivity.this, ContactsActivity.class));
        finish();
    }


}
