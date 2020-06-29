package com.example.demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.model.IncomingCallsModel;
import com.example.demo.model.IncomingCallsSingleton;
import com.example.demo.model.MissedCallsModel;
import com.example.demo.model.MissedCallsSingleton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallHistoryActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {
    private Context mContext;
    private String TAG = CallHistoryActivity.class.getSimpleName();
    private RecyclerView rvData;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String[] projection = new String[]{
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        mContext = CallHistoryActivity.this;

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
        toolbar.setTitle(getResources().getString(R.string.call_history_toolbar_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        fetchhHistory();

        setupViewPager();

    }

    private void fetchhHistory() {
        IncomingCallsSingleton.getInstance().clearArray();
        MissedCallsSingleton.getInstance().clearArray();

        //Fetches the complete call log in descending order. i.e recent calls appears first.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor c = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null,
                null, CallLog.Calls.DATE + " DESC");

        assert c != null;
        if (c.getCount() > 0)
        {
            c.moveToFirst();
            do{
                String callerID = c.getString(c.getColumnIndex(CallLog.Calls._ID));
                String callerNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                long callDateandTime = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
                long callDuration = c.getLong(c.getColumnIndex(CallLog.Calls.DURATION));
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM HH:mm a");
                String dateString = formatter.format(new Date(callDateandTime));


//                Date callDayTime = new Date(Long.valueOf(callDateandTime));
                int callType = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE));
                if(callType == CallLog.Calls.INCOMING_TYPE)
                {
                    //incoming call
                    IncomingCallsModel incomingCallsModel = new IncomingCallsModel();
                    incomingCallsModel.callerID = callerID;
                    incomingCallsModel.callerNumber = callerNumber;
                    incomingCallsModel.callDateandTime = dateString;
                    incomingCallsModel.callDuration = callDuration;
                    IncomingCallsSingleton.getInstance().addToArray(incomingCallsModel);
                }
                else if(callType == CallLog.Calls.OUTGOING_TYPE)
                {
                    //outgoing call
                }
                else if(callType == CallLog.Calls.MISSED_TYPE)
                {
                    //missed call
                    //incoming call
                    MissedCallsModel missedCallsModel = new MissedCallsModel();
                    missedCallsModel.callerID = callerID;
                    missedCallsModel.callerNumber = callerNumber;
                    missedCallsModel.callDateandTime = dateString;
                    missedCallsModel.callDuration = callDuration;
                    MissedCallsSingleton.getInstance().addToArray(missedCallsModel);
                }
            }while(c.moveToNext());

        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);

        tabLayout  = findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        Pager adapter = new Pager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
