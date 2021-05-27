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
import com.mobdeve.leej.thejuanpc.model.RAM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RAMListAdapter extends RecyclerView.Adapter<RAMListAdapter.RAMListViewHolder> {

    private ArrayList<RAM> ramArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public RAMListAdapter (Context context, ArrayList<RAM> ramArrayList) {
        this.ramArrayList = ramArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return ramArrayList.size();
    }

    @Override
    public RAMListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ram_parts_list, parent, false);

        RAMListViewHolder RAMListViewHolder = new RAMListViewHolder(view);

        return RAMListViewHolder;
    }

    @Override
    public void onBindViewHolder(final RAMListAdapter.RAMListViewHolder holder, final int position) {
        holder.ram_model.setText(ramArrayList.get(position).getModel());
        holder.ram_speed.setText(Integer.toString(ramArrayList.get(position).getSpeed()) + "mHz");
        holder.ram_capacity.setText(Integer.toString(ramArrayList.get(position).getCapacity()) + "GB");
        holder.ram_sticks.setText(Integer.toString(ramArrayList.get(position).getNum_sticks()) + " Sticks");
        holder.ram_wattage.setText(Integer.toString(ramArrayList.get(position).getWattage()) + "W TDP");
        holder.ram_price.setText("P " + Double.toString(ramArrayList.get(position).getPrice()));
        Picasso.get().load(ramArrayList.get(position).getImage()).into(holder.ram_image);

        holder.ram_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                RAM temp = ramArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.saveRAM("savedRAM", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class RAMListViewHolder extends RecyclerView.ViewHolder {

        TextView ram_model, ram_speed, ram_capacity, ram_sticks, ram_wattage, ram_price;
        ImageView ram_image;
        ImageButton ram_part_button;

        public RAMListViewHolder(View view) {
            super(view);

            ram_model = view.findViewById(R.id.ram_model);
            ram_speed = view.findViewById(R.id.ram_speed);
            ram_capacity = view.findViewById(R.id.ram_capacity);
            ram_sticks = view.findViewById(R.id.ram_sticks);
            ram_wattage = view.findViewById(R.id.ram_wattage);
            ram_price = view.findViewById(R.id.ram_price);
            ram_image = view.findViewById(R.id.ram_image);
            ram_part_button = view.findViewById(R.id.ram_part_button);
        }
    }
}