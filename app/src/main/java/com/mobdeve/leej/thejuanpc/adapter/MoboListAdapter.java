package com.mobdeve.leej.thejuanpc.adapter;

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
import com.mobdeve.leej.thejuanpc.model.Motherboard;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoboListAdapter extends RecyclerView.Adapter<MoboListAdapter.MoboListViewHolder> {
    private ArrayList<Motherboard> motherboardArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public MoboListAdapter(Context context, ArrayList<Motherboard> motherboardArrayList) {
        this.motherboardArrayList = motherboardArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return motherboardArrayList.size();
    }

    @Override
    public MoboListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mobo_parts_list, parent, false);

        MoboListViewHolder MoboListViewHolder  = new MoboListViewHolder(view);

        return MoboListViewHolder;
    }

    @Override
    public void onBindViewHolder(final MoboListAdapter.MoboListViewHolder holder, final int position) {
        holder.mobo_model.setText(motherboardArrayList.get(position).getModel());
        holder.mobo_form_factor.setText(motherboardArrayList.get(position).getForm_factor());
        holder.mobo_chipset.setText(motherboardArrayList.get(position).getChipset());
        holder.mobo_ram_speed.setText("Recommended RAM Speed: " + Integer.toString(motherboardArrayList.get(position).getReco_ram_speed()));
        holder.mobo_wattage.setText(Integer.toString(motherboardArrayList.get(position).getWattage()) + "W TDP");
        holder.mobo_price.setText("P " + Double.toString(motherboardArrayList.get(position).getPrice()));
        holder.mobo_socket.setText(motherboardArrayList.get(position).getSocket());
        Picasso.get().load(motherboardArrayList.get(position).getImage()).into(holder.mobo_image);

        holder.mobo_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                Motherboard temp = motherboardArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.saveMobo("savedMobo", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class MoboListViewHolder extends RecyclerView.ViewHolder{

        TextView mobo_model, mobo_form_factor, mobo_chipset, mobo_ram_speed, mobo_wattage, mobo_price, mobo_socket;
        ImageView mobo_image;
        ImageButton mobo_part_button;

        public MoboListViewHolder(View view) {
            super(view);
            mobo_model = view.findViewById(R.id.mobo_model);
            mobo_form_factor = view.findViewById(R.id.mobo_form_factor);
            mobo_chipset = view.findViewById(R.id.mobo_chipset);
            mobo_ram_speed = view.findViewById(R.id.mobo_ram_speed);
            mobo_wattage = view.findViewById(R.id.mobo_wattage);
            mobo_price = view.findViewById(R.id.mobo_price);
            mobo_socket = view.findViewById(R.id.mobo_socket);
            mobo_image = view.findViewById(R.id.mobo_image);
            mobo_part_button = view.findViewById(R.id.mobo_part_button);
        }
    }
}