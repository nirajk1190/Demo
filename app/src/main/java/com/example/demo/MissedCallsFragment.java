package com.example.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.adapter.IncomingCallsAdapter;
import com.example.demo.adapter.MissedCallsAdapter;
import com.example.demo.model.IncomingCallsSingleton;
import com.example.demo.model.MissedCallsSingleton;

public class MissedCallsFragment extends Fragment{
    public static final String TITLE = "Missed";
    private RecyclerView rvData;
    private View main;
    private MissedCallsAdapter missedCallsAdapter;

    //Overriden method onCreateView
    public static MissedCallsFragment newInstance() {

        return new MissedCallsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        main = inflater.inflate(R.layout.missed_calls_fragment, container, false);
        init();
        applyInit();
        return main;
    }

    private void applyInit() {
        missedCallsAdapter = new MissedCallsAdapter(getActivity(), MissedCallsSingleton.getInstance().getArray());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvData.setLayoutManager(mLayoutManager);
        rvData.setAdapter(missedCallsAdapter);

    }


    private void init() {
        rvData = main.findViewById(R.id.rvData);

    }

}
