package com.example.demo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.adapter.PermissionAdapter;
import com.example.demo.model.PermissionModel;
import com.example.demo.utils.SharedPrefUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends AppCompatActivity {
    private Context mContext;
    private String TAG = PermissionActivity.class.getSimpleName();
    private RecyclerView rvData;
    private ArrayList<PermissionModel> permissionModels = new ArrayList<>();
    private PermissionAdapter permissionAdapter;
    private AppCompatButton btnAccepted;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mContext = PermissionActivity.this;

        init();
        applyInit();
    }

    private void applyInit() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.permission_act_toolbar_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        fetchPermissionList();


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvData.setLayoutManager(layoutManager);
        permissionAdapter = new PermissionAdapter(this, permissionModels);
        rvData.setAdapter(permissionAdapter);

    }

    private void requestMultiplePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR,
                        Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            SharedPrefUtil.putBoolean("isPermission", true, mContext);
                            startActivity(new Intent(PermissionActivity.this, HomActivity.class));
                            finish();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void fetchPermissionList() {
        permissionModels.clear();

        PermissionModel permissionModel = new PermissionModel();
        permissionModel.title = mContext.getResources().getString(R.string.app_history);
        permissionModel.description = mContext.getResources().getString(R.string.app_history1);
        permissionModel.description1 = mContext.getResources().getString(R.string.app_history2);
        permissionModel.icon = R.drawable.loan_history;
        permissionModels.add(permissionModel);

        PermissionModel permissionModel1 = new PermissionModel();
        permissionModel1.title = mContext.getResources().getString(R.string.identity);
        permissionModel1.description = mContext.getResources().getString(R.string.identity1);
        permissionModel1.icon = R.drawable.ic_perm_identity_black_24dp;
        permissionModels.add(permissionModel1);

        PermissionModel permissionModel2 = new PermissionModel();
        permissionModel2.title = mContext.getResources().getString(R.string.calender);
        permissionModel2.description = mContext.getResources().getString(R.string.calender1);
        permissionModel2.icon = R.drawable.ic_calender;
        permissionModels.add(permissionModel2);

        PermissionModel permissionModel3 = new PermissionModel();
        permissionModel3.title = mContext.getResources().getString(R.string.contacts);
        permissionModel3.description = mContext.getResources().getString(R.string.contacts1);
        permissionModel3.icon = R.drawable.ic_contacts_black_24dp;
        permissionModels.add(permissionModel3);

        PermissionModel permissionModel4 = new PermissionModel();
        permissionModel4.title = mContext.getResources().getString(R.string.locations);
        permissionModel4.description = mContext.getResources().getString(R.string.location1);
        permissionModel4.description1 = mContext.getResources().getString(R.string.location2);
        permissionModel4.icon = R.drawable.ic_location_on_black_24dp;
        permissionModels.add(permissionModel4);

        PermissionModel permissionModel5 = new PermissionModel();
        permissionModel5.title = mContext.getResources().getString(R.string.sms);
        permissionModel5.description = mContext.getResources().getString(R.string.sms1);
        permissionModel5.description1 = mContext.getResources().getString(R.string.sms2);
        permissionModel5.icon = R.drawable.ic_sms_black_24dp;
        permissionModels.add(permissionModel5);

        PermissionModel permissionModel6 = new PermissionModel();
        permissionModel6.title = mContext.getResources().getString(R.string.phone);
        permissionModel6.description = mContext.getResources().getString(R.string.phone1);
        permissionModel6.description1 = mContext.getResources().getString(R.string.phone2);
        permissionModel6.description2 = mContext.getResources().getString(R.string.phone3);
        permissionModel6.icon = R.drawable.ic_local_phone_black_24dp;
        permissionModels.add(permissionModel6);

        PermissionModel permissionModel7 = new PermissionModel();
        permissionModel7.title = mContext.getResources().getString(R.string.photos);
        permissionModel7.description = mContext.getResources().getString(R.string.photos1);
        permissionModel7.description2 = mContext.getResources().getString(R.string.photos2);
        permissionModel7.icon = R.drawable.ic_perm_media_black_24dp;
        permissionModels.add(permissionModel7);

        PermissionModel permissionModel8 = new PermissionModel();
        permissionModel8.title = mContext.getResources().getString(R.string.camera);
        permissionModel8.description = mContext.getResources().getString(R.string.camera1);
        permissionModel8.icon = R.drawable.ic_camera_black_24dp;
        permissionModels.add(permissionModel8);

        PermissionModel permissionModel9 = new PermissionModel();
        permissionModel9.title = mContext.getResources().getString(R.string.wifi_connection);
        permissionModel9.description = mContext.getResources().getString(R.string.wifi_connection1);
        permissionModel9.icon = R.drawable.ic_signal_wifi_4_bar_black_24dp;
        permissionModels.add(permissionModel9);

        PermissionModel permissionModel10 = new PermissionModel();
        permissionModel10.title = mContext.getResources().getString(R.string.device_id);
        permissionModel10.description = mContext.getResources().getString(R.string.device_id1);
        permissionModel10.icon = R.drawable.ic_perm_device_information_black_24dp;
        permissionModels.add(permissionModel10);

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        rvData = findViewById(R.id.rvData);
        btnAccepted = findViewById(R.id.btnAccepted);
        btnAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMultiplePermission();

            }
        });


    }
}
