package com.mobdeve.leej.thejuanpc.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.leej.thejuanpc.ModulePrefs;
import com.mobdeve.leej.thejuanpc.R;
import com.mobdeve.leej.thejuanpc.SystemBuilder;
import com.mobdeve.leej.thejuanpc.model.CPU;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CPUListAdapter extends RecyclerView.Adapter<CPUListAdapter.CPUListViewHolder> {

    private ArrayList<CPU> cpuArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public CPUListAdapter (Context context, ArrayList<CPU> cpuArrayList) {
        this.cpuArrayList = cpuArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return cpuArrayList.size();
    }

    @Override
    public CPUListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cpu_parts_list, parent, false);

        CPUListViewHolder CPUListViewHolder = new CPUListViewHolder(view);

        return CPUListViewHolder;
    }

    @Override
    public void onBindViewHolder(final CPUListAdapter.CPUListViewHolder holder, final int position) {
        holder.cpu_model.setText(cpuArrayList.get(position).getModel());
        holder.cpu_cores.setText(cpuArrayList.get(position).getNum_cores() + " Cores " + cpuArrayList.get(position).getNum_threads() + " Threads");
        holder.cpu_clockspeed.setText(cpuArrayList.get(position).getBase_clockspeed() + " Base Speed " + cpuArrayList.get(position).getMax_clockspeed() + " Max Speed");
        holder.cpu_wattageUsed.setText(Integer.toString(cpuArrayList.get(position).getWattage()) + "W TDP");
        holder.cpu_price.setText("P " + Double.toString(cpuArrayList.get(position).getPrice()));
        holder.cpu_socket.setText(cpuArrayList.get(position).getSocket());
        Picasso.get().load(cpuArrayList.get(position).getImage()).into(holder.cpu_image);

        holder.cpu_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                CPU temp = cpuArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.saveCPU("savedCPU", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class CPUListViewHolder extends RecyclerView.ViewHolder {

        TextView cpu_model, cpu_cores, cpu_clockspeed, cpu_wattageUsed, cpu_price, cpu_socket;
        ImageView cpu_image;
        ImageButton cpu_part_button;

        public CPUListViewHolder(View view) {
            super(view);

            cpu_model = view.findViewById(R.id.cpu_model);
            cpu_cores = view.findViewById(R.id.cpu_cores);
            cpu_clockspeed = view.findViewById(R.id.cpu_clockspeed);
            cpu_wattageUsed = view.findViewById(R.id.cpu_wattageUsed);
            cpu_price = view.findViewById(R.id.cpu_price);
            cpu_image = view.findViewById(R.id.cpu_image);
            cpu_socket = view.findViewById(R.id.cpu_socket);
            cpu_part_button = view.findViewById(R.id.cpu_part_button);
        }
    }
}