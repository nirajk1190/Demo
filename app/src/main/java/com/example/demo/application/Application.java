package com.example.demo.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Application extends MultiDexApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        MultiDex.install(this);


    }

    public static Context getContext() {
        return mContext;
    }
}
