package com.mobdeve.leej.thejuanpc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mobdeve.leej.thejuanpc.R;
import com.mobdeve.leej.thejuanpc.model.Builds;

public class MyBuildsSMAdapter extends FirestoreRecyclerAdapter<Builds, MyBuildsSMAdapter.BuildHolder> {

    private OnItemClickListener listener;

    public MyBuildsSMAdapter(@NonNull FirestoreRecyclerOptions<Builds> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BuildHolder holder, int position, @NonNull Builds model) {
        holder.tv_build_name.setText(model.getBuild_name());
        holder.tv_build_price.setText("Price: " + String.valueOf(model.getTotalEstimatePrice()));
        holder.tv_build_parts.setText(model.getGpu()+ ", " + model.getCpu()+ ", " +model.getMotherboard()+ ", " +model.getPsu()+ ", " +model.getRam()+ ", " +model.getStorage()+ ", " +model.getPc_case());
    }

    @NonNull
    @Override
    public BuildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_builds_sm_items,parent,false);


        return new BuildHolder(v);
    }

    public void deleteBuild(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public DocumentReference createBlog(int position){
        DocumentReference buildName;
        buildName = getSnapshots().getSnapshot(position).getReference();
        return buildName;
    }

    class BuildHolder extends RecyclerView.ViewHolder{
        TextView tv_build_name;
        TextView tv_build_price;
        TextView tv_build_parts;

        public BuildHolder (View itemView){
            super(itemView);
            tv_build_name = itemView.findViewById(R.id.tv_build_name);
            tv_build_price = itemView.findViewById(R.id.tv_build_price);
            tv_build_parts = itemView.findViewById(R.id.tv_build_parts);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null){
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
