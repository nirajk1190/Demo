package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.SMSModel;

import java.util.ArrayList;

public class SMSListAdapter extends RecyclerView.Adapter<SMSListAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<SMSModel> smsModels;

    public interface ItemClick {
        void onItemClick(int pos);
    }

    public ItemClick itemClick;

    public SMSListAdapter(Context mContext, ArrayList<SMSModel> smsModels) {
        this.mContext = mContext;
        this.smsModels = smsModels;
    }

    @Override
    public SMSListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sms_data, null);
        return new SMSListAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SMSListAdapter.ItemViewHolder holder, final int position) {
        SMSModel smsModel = smsModels.get(position);
        holder.tvAddress.setText(smsModel.address);
        holder.tvBody.setText(smsModel.body);

    }

    @Override
    public int getItemCount() {
        return smsModels.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAddress, tvBody;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvBody = itemView.findViewById(R.id.tvBody);

        }
    }
}

