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

import java.util.ArrayList;

public class IncomingCallsAdapter extends RecyclerView.Adapter<IncomingCallsAdapter.MyItemViewHolder> {
   private Context mContext;
   private ArrayList<IncomingCallsModel> incomingCallsModels;

   public IncomingCallsAdapter(Context mContext, ArrayList<IncomingCallsModel> incomingCallsModels){
       this.mContext = mContext;
       this.incomingCallsModels = incomingCallsModels;
   }


    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.incoming_call_list_item, null);
        return new IncomingCallsAdapter.MyItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {
        IncomingCallsModel incomingCallsModel = incomingCallsModels.get(position);
        holder.tvNumber.setText(incomingCallsModel.callerNumber);
        holder.tvDateAndTime.setText(incomingCallsModel.callDateandTime+"");
        holder.tvDuration.setText(incomingCallsModel.callDuration+"");
    }

    @Override
    public int getItemCount() {
        return incomingCallsModels.size();
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
