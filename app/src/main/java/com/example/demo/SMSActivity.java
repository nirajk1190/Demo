package com.example.demo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.adapter.ContactListAdapter;
import com.example.demo.adapter.SMSListAdapter;
import com.example.demo.model.SMSModel;
import com.example.demo.utils.MyDividerItemDecoration;

import java.util.ArrayList;

public class SMSActivity extends AppCompatActivity {
    private Context mContext;
    private String TAG = SMSActivity.class.getSimpleName();
    private ArrayList<SMSModel> smsModelArrayList = new ArrayList<>();
    private Toolbar toolbar;
    private static final int PERMISSIONS_REQUEST_READ_SMS = 100;
    private SMSListAdapter smsListAdapter;
    private RecyclerView rvData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        mContext = SMSActivity.this;

        init();
        applyInit();
    }

    private void applyInit() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.contacts_toolbar_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        showSMS();
        if (showSMS()!=null){
            smsListAdapter = new SMSListAdapter(mContext, smsModelArrayList);
            rvData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvData.setItemAnimator(new DefaultItemAnimator());
            rvData.addItemDecoration(new MyDividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            rvData.setAdapter(smsListAdapter);
        }

    }

    private ArrayList<SMSModel> showSMS() {
        smsModelArrayList.clear();
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);

            System.out.println("======&gt; Mobile number =&gt; "+address);
            System.out.println("=====&gt; SMS Text =&gt; "+body);
            SMSModel smsModel = new SMSModel();
            smsModel.address= address;
            smsModel.body = body;
            smsModelArrayList.add(smsModel);
        }
        return smsModelArrayList;
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rvData = findViewById(R.id.rvData);
    }
}
