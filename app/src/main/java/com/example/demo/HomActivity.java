package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private AppCompatButton btnReadContacts,btnReadSMS,btnCallLog;
    /*Here I changed MainActivity to HomeActivity...Hello Commit*/
    /*Commit...Hello*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomActivity.this;

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
        startActivity(new Intent(HomActivity.this, CallHistoryActivity.class));
        finish();
    }

    private void showSMS() {
        startActivity(new Intent(HomActivity.this, SMSActivity.class));
        finish();
    }

    private void showContacts() {
        startActivity(new Intent(HomActivity.this, ContactsActivity.class));
        finish();
    }


}
