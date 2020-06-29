package com.example.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.adapter.IncomingCallsAdapter;
import com.example.demo.model.IncomingCallsSingleton;

public class IncomingCallsFragment extends Fragment {
    public static final String TITLE = "Incoming";
    private RecyclerView rvData;
    private View main;
    private IncomingCallsAdapter incomingCallsAdapter;

    //Overriden method onCreateView
    public static IncomingCallsFragment newInstance() {

        return new IncomingCallsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        main = inflater.inflate(R.layout.incoming_calls_fragment, container, false);
        init();
        applyInit();
        return main;
    }

    private void applyInit() {
        incomingCallsAdapter = new IncomingCallsAdapter(getActivity(), IncomingCallsSingleton.getInstance().getArray());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvData.setLayoutManager(mLayoutManager);
        rvData.setAdapter(incomingCallsAdapter);

    }


    private void init() {
        rvData = main.findViewById(R.id.rvData);

    }

}
