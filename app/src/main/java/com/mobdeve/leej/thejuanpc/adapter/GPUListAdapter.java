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
import com.mobdeve.leej.thejuanpc.model.GPU;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GPUListAdapter extends RecyclerView.Adapter<GPUListAdapter.GPUListViewHolder> {

    private ArrayList<GPU> gpuArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public GPUListAdapter (Context context, ArrayList<GPU> gpuArrayList) {
        this.gpuArrayList = gpuArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return gpuArrayList.size();
    }

    @Override
    public GPUListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gpu_parts_list, parent, false);

        GPUListViewHolder GPUListViewHolder = new GPUListViewHolder(view);

        return GPUListViewHolder;
    }

    @Override
    public void onBindViewHolder(final GPUListAdapter.GPUListViewHolder holder, final int position) {
        holder.gpu_model.setText(gpuArrayList.get(position).getModel());
        holder.gpu_brand.setText(gpuArrayList.get(position).getBrand());
        holder.gpu_clockrate.setText(Double.toString(gpuArrayList.get(position).getClock_rate()) + "mHz");
        holder.gpu_memorySize.setText(Integer.toString(gpuArrayList.get(position).getMemory_size()) + "GB");
        holder.gpu_wattage.setText(Integer.toString(gpuArrayList.get(position).getWattage()) + "W TDP");
        holder.gpu_price.setText("P " + Double.toString(gpuArrayList.get(position).getPrice()));
        Picasso.get().load(gpuArrayList.get(position).getImage()).into(holder.gpu_image);

        holder.gpu_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                GPU temp = gpuArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.saveGPU("savedGPU", temp);
                context.startActivity(intent);

            }
        });
    }

    protected class GPUListViewHolder extends RecyclerView.ViewHolder {

        TextView gpu_model, gpu_brand, gpu_clockrate, gpu_memorySize, gpu_wattage, gpu_price;
        ImageView gpu_image;
        ImageButton gpu_part_button;

        public GPUListViewHolder(View view) {
            super(view);

            gpu_model = view.findViewById(R.id.gpu_model);
            gpu_brand = view.findViewById(R.id.gpu_brand);
            gpu_clockrate = view.findViewById(R.id.gpu_clockrate);
            gpu_memorySize = view.findViewById(R.id.gpu_memorySize);
            gpu_wattage = view.findViewById(R.id.gpu_wattage);
            gpu_price = view.findViewById(R.id.gpu_price);
            gpu_image = view.findViewById(R.id.gpu_image);
            gpu_part_button = view.findViewById(R.id.gpu_part_button);
        }
    }
}