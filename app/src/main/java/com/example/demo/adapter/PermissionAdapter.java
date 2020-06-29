package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.PermissionModel;
import com.example.demo.utils.Util;

import java.util.ArrayList;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<PermissionModel> permissionModels;

    public PermissionAdapter(Context context, ArrayList<PermissionModel> permissionModels) {
        this.mContext = context;
        this.permissionModels = permissionModels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_permission_list_items, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        final PermissionModel permissionModel = permissionModels.get(i);

        myViewHolder.ivIcon.setImageResource(permissionModel.icon);
        myViewHolder.tvPermissionHeading.setText(permissionModel.title);
        myViewHolder.tvPermissionContent.setText(permissionModel.description);
        if (Util.validateString(permissionModel.description1)) {
            myViewHolder.tvPermissionContent1.setVisibility(View.VISIBLE);
            myViewHolder.tvPermissionContent1.setText(permissionModel.description1);
        } else {
            myViewHolder.tvPermissionContent1.setVisibility(View.GONE);
        }
        if (Util.validateString(permissionModel.description2)) {
            myViewHolder.tvPermissionContent2.setVisibility(View.VISIBLE);
            myViewHolder.tvPermissionContent2.setText(permissionModel.description2);
        } else {
            myViewHolder.tvPermissionContent2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return permissionModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvPermissionHeading, tvPermissionContent, tvPermissionContent1, tvPermissionContent2;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvPermissionHeading = itemView.findViewById(R.id.tvPermissionHeading);
            tvPermissionContent = itemView.findViewById(R.id.tvPermissionContent);
            tvPermissionContent1 = itemView.findViewById(R.id.tvPermissionContent1);
            tvPermissionContent2 = itemView.findViewById(R.id.tvPermissionContent2);
        }
    }
}
