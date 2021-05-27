package com.mobdeve.leej.thejuanpc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.leej.thejuanpc.ModulePrefs;
import com.mobdeve.leej.thejuanpc.R;
import com.mobdeve.leej.thejuanpc.SystemBuilder;

import com.mobdeve.leej.thejuanpc.model.Storage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StorageListAdapter extends RecyclerView.Adapter<StorageListAdapter.StorageListViewHolder> {

    private ArrayList<Storage> storageArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public StorageListAdapter (Context context, ArrayList<Storage> storageArrayList) {
        this.storageArrayList = storageArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return storageArrayList.size();
    }

    @Override
    public StorageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.storage_parts_list, parent, false);

        StorageListViewHolder StorageListViewHolder = new StorageListViewHolder(view);

        return StorageListViewHolder;
    }

    @Override
    public void onBindViewHolder(final StorageListAdapter.StorageListViewHolder holder, final int position) {
        holder.storage_model.setText(storageArrayList.get(position).getModel());
        holder.storage_capacity.setText(Double.toString(storageArrayList.get(position).getCapacity()) + "GB");
        holder.storage_readSpeed.setText(Double.toString(storageArrayList.get(position).getRead_speed()) + "MB/s");
        holder.storage_writeSpeed.setText(Double.toString(storageArrayList.get(position).getWrite_speed()) + "MB/s");
        holder.storage_wattage.setText(Integer.toString(storageArrayList.get(position).getWattage()) + "W TDP");
        holder.storage_price.setText("P " + Double.toString(storageArrayList.get(position).getPrice()));
        Picasso.get().load(storageArrayList.get(position).getImage()).into(holder.storage_image);

        holder.storage_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                Storage temp = storageArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.saveStorage("savedStorage", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class StorageListViewHolder extends RecyclerView.ViewHolder {

        TextView storage_model, storage_capacity, storage_readSpeed, storage_writeSpeed, storage_wattage, storage_price;
        ImageView storage_image;
        ImageButton storage_part_button;

        public StorageListViewHolder(View view) {
            super(view);

            storage_model = view.findViewById(R.id.storage_model);
            storage_capacity = view.findViewById(R.id.storage_capacity);
            storage_readSpeed = view.findViewById(R.id.storage_readSpeed);
            storage_writeSpeed = view.findViewById(R.id.storage_writeSpeed);
            storage_wattage = view.findViewById(R.id.storage_wattage);
            storage_price = view.findViewById(R.id.storage_price);
            storage_image = view.findViewById(R.id.storage_image);
            storage_part_button = view.findViewById(R.id.storage_part_button);
        }
    }
}