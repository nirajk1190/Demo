package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.model.ContactDataModel;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ItemViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<ContactDataModel> contactDataModelArrayList;
    private List<ContactDataModel> filteresContactDataModelList;
    public interface ItemClick {
        void onItemClick(int pos);
    }
    public ItemClick itemClick;

    public ContactListAdapter(Context mContext, ArrayList<ContactDataModel> contactDataModelArrayList,ItemClick itemClick) {
        this.mContext = mContext;
        this.contactDataModelArrayList = contactDataModelArrayList;
        this.filteresContactDataModelList = contactDataModelArrayList;
        this.itemClick = itemClick;
    }

    @Override
    public ContactListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_data, null);
        return new ContactListAdapter.ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ItemViewHolder holder, final int position) {
        ContactDataModel contactDataModel = filteresContactDataModelList.get(position);
        holder.icon_profile.setImageResource(R.drawable.bg_circle);
        holder.icon_profile.setColorFilter(contactDataModel.color);
        holder.icon_text.setText(contactDataModel.name.substring(0, 1));
        holder.tvName.setText(contactDataModel.name);
        holder.tvMobile.setText(contactDataModel.phone);


    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteresContactDataModelList = contactDataModelArrayList;
                } else {
                    List<ContactDataModel> filteredList = new ArrayList<>();
                    for (ContactDataModel row : contactDataModelArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.phone.contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteresContactDataModelList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteresContactDataModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteresContactDataModelList = (ArrayList<ContactDataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return filteresContactDataModelList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon_profile;
        private TextView icon_text,tvName,tvMobile;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            icon_profile = itemView.findViewById(R.id.icon_profile);
            icon_text = itemView.findViewById(R.id.icon_text);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);

        }
    }
}

