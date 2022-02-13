package com.aariyan.scannloading.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aariyan.scannloading.Model.HeaderLinesModel;
import com.aariyan.scannloading.R;

import java.util.List;

public class HeaderLinesAdapter extends RecyclerView.Adapter<HeaderLinesAdapter.ViewHolder> {

    private Context context;
    private List<HeaderLinesModel> list;

    public HeaderLinesAdapter(Context context, List<HeaderLinesModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_header_lines_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeaderLinesModel model = list.get(position);
        holder.storeName.setText(model.getStoreName());
        holder.orderId.setText(String.format(" # %s", model.getOrderId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storeName, orderId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeName);
            orderId = itemView.findViewById(R.id.orderId);
        }
    }
}
