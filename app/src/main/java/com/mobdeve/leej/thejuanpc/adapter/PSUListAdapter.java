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
import com.mobdeve.leej.thejuanpc.model.PSU;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PSUListAdapter extends RecyclerView.Adapter<PSUListAdapter.PSUListViewHolder> {

    private ArrayList<PSU> psuArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public PSUListAdapter (Context context, ArrayList<PSU> psuArrayList) {
        this.psuArrayList = psuArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return psuArrayList.size();
    }

    @Override
    public PSUListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.psu_parts_list, parent, false);

        PSUListViewHolder PSUListViewHolder = new PSUListViewHolder(view);

        return PSUListViewHolder;
    }

    @Override
    public void onBindViewHolder(final PSUListAdapter.PSUListViewHolder holder, final int position) {
        holder.psu_model.setText(psuArrayList.get(position).getModel());
        holder.psu_rating.setText("80+ " + psuArrayList.get(position).getRating() + " Rating");
        holder.psu_modularity.setText(psuArrayList.get(position).getModularity() + " Modular");
        holder.psu_wattage.setText(Integer.toString(psuArrayList.get(position).getWattage()) + "W TDP");
        holder.psu_price.setText("P " + Double.toString(psuArrayList.get(position).getPrice()));
        Picasso.get().load(psuArrayList.get(position).getImage()).into(holder.psu_image);

        holder.psu_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                PSU temp = psuArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.savePSU("savedPSU", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class PSUListViewHolder extends RecyclerView.ViewHolder {

        TextView psu_model, psu_rating, psu_modularity, psu_wattage, psu_price;
        ImageView psu_image;
        ImageButton psu_part_button;

        public PSUListViewHolder(View view) {
            super(view);

            psu_model = view.findViewById(R.id.psu_model);
            psu_rating = view.findViewById(R.id.psu_rating);
            psu_modularity = view.findViewById(R.id.psu_modularity);
            psu_wattage = view.findViewById(R.id.psu_wattage);
            psu_price = view.findViewById(R.id.psu_price);
            psu_image = view.findViewById(R.id.psu_image);
            psu_part_button = view.findViewById(R.id.psu_part_button);
        }
    }
}