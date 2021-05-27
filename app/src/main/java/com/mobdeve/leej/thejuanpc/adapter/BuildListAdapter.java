package com.mobdeve.leej.thejuanpc.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.leej.thejuanpc.BuildDetails;
import com.mobdeve.leej.thejuanpc.ModulePrefs;
import com.mobdeve.leej.thejuanpc.R;
import com.mobdeve.leej.thejuanpc.model.Builds;

import java.util.ArrayList;

public class BuildListAdapter extends RecyclerView.Adapter<BuildListAdapter.BuildListViewHolder> {
    private ArrayList<Builds> buildArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public BuildListAdapter(Context context, ArrayList<Builds> buildArrayList) {
        this.buildArrayList = buildArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return buildArrayList.size();
    }

    @Override
    public BuildListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_builds_list, parent, false);

        BuildListViewHolder BuildListViewHolder = new BuildListViewHolder(view);
        return BuildListViewHolder;
    }

    @Override
    public void onBindViewHolder(final BuildListAdapter.BuildListViewHolder holder, final int position) {
        modulePrefs = new ModulePrefs(context);
        holder.tv_image.setImageResource(R.drawable.pc_icon);
        holder.tv_title.setText(buildArrayList.get(position).getBuild_name());
        holder.tv_price.setText(Double.toString(buildArrayList.get(position).getTotalEstimatePrice()));

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BuildDetails.class);
                Builds temp = buildArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("build_details", temp);
                modulePrefs.saveStringPreferences("from", "UserProfile");
                context.startActivity(intent);
            }
        });
    }

    protected class BuildListViewHolder extends RecyclerView.ViewHolder {

        ImageView tv_image;
        TextView tv_title, tv_price;
        Button view_details;

        public BuildListViewHolder(View view) {
            super(view);
            tv_image = view.findViewById(R.id.tv_image);
            tv_title = view.findViewById(R.id.tv_title);
            tv_price = view.findViewById(R.id.tv_price);
            view_details = view.findViewById(R.id.view_details);
        }
    }
}
