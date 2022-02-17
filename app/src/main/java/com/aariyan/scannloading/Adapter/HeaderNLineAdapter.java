package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Activity.HeaderNLineActivity;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class HeaderNLineAdapter extends RecyclerView.Adapter<HeaderNLineAdapter.ViewHolder> {

    private Context context;
    private List<LinesModel> list;
    public HeaderNLineAdapter(Context context, List<LinesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_recycler_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinesModel model = list.get(position);
        holder.storeName.setText(model.getPastelDescription());
        holder.itemQuantity.setText(""+model.getQty());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName,itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
