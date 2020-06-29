package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.IncomingCallsModel;
import com.example.demo.model.MissedCallsModel;

import java.util.ArrayList;

public class MissedCallsAdapter extends RecyclerView.Adapter<MissedCallsAdapter.MyItemViewHolder> {
   private Context mContext;
   private ArrayList<MissedCallsModel> missedCallsModels;

   public MissedCallsAdapter(Context mContext, ArrayList<MissedCallsModel> missedCallsModels){
       this.mContext = mContext;
       this.missedCallsModels = missedCallsModels;
   }


    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.missed_call_list_item, null);
        return new MissedCallsAdapter.MyItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {
        MissedCallsModel missedCallsModel = missedCallsModels.get(position);
        holder.tvNumber.setText(missedCallsModel.callerNumber);
        holder.tvDateAndTime.setText(missedCallsModel.callDateandTime+"");
        holder.tvDuration.setText(missedCallsModel.callDuration+"");
    }

    @Override
    public int getItemCount() {
        return missedCallsModels.size();
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder {
       private TextView tvCallerId,tvNumber,tvDateAndTime,tvDuration;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvDateAndTime = itemView.findViewById(R.id.tvDateAndTime);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }
    }
}
