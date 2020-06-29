package com.example.demo.model;

import java.util.ArrayList;

public class IncomingCallsSingleton {
    private static IncomingCallsSingleton mInstance;
    private ArrayList<IncomingCallsModel> list = null;

    public static IncomingCallsSingleton getInstance() {
        if(mInstance == null)
            mInstance = new IncomingCallsSingleton();

        return mInstance;
    }

    private IncomingCallsSingleton() {
        list = new ArrayList<IncomingCallsModel>();
    }
    // retrieve array from anywhere
    public ArrayList<IncomingCallsModel> getArray() {
        return this.list;
    }
    //Add element to array
    public void addToArray(IncomingCallsModel incomingCallsModel) {
        list.add(incomingCallsModel);
    }
    public void clearArray(){
        list.clear();
    }
}
