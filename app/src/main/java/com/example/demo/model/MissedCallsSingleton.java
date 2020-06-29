package com.example.demo.model;

import java.util.ArrayList;

public class MissedCallsSingleton {
    private static MissedCallsSingleton mInstance;
    private ArrayList<MissedCallsModel> list = null;

    public static MissedCallsSingleton getInstance() {
        if(mInstance == null)
            mInstance = new MissedCallsSingleton();

        return mInstance;
    }

    private MissedCallsSingleton() {
        list = new ArrayList<MissedCallsModel>();
    }
    // retrieve array from anywhere
    public ArrayList<MissedCallsModel> getArray() {
        return this.list;
    }
    //Add element to array
    public void addToArray(MissedCallsModel missedCallsModel) {
        list.add(missedCallsModel);
    }
    public void clearArray(){
        list.clear();
    }
}
