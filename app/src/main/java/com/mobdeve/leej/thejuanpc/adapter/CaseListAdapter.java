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
import com.mobdeve.leej.thejuanpc.model.Cases;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CaseListAdapter extends RecyclerView.Adapter<CaseListAdapter.CaseListViewHolder> {

    private ArrayList<Cases> casesArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public CaseListAdapter (Context context, ArrayList<Cases> casesArrayList) {
        this.casesArrayList = casesArrayList;
        this.context = context;
        this.modulePrefs = new ModulePrefs(context);
    }

    @Override
    public int getItemCount() {
        return casesArrayList.size();
    }

    @Override
    public CaseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.case_parts_list, parent, false);

        CaseListViewHolder CaseListViewHolder = new CaseListViewHolder(view);

        return CaseListViewHolder;
    }

    @Override
    public void onBindViewHolder(final CaseListAdapter.CaseListViewHolder holder, final int position) {
        holder.case_model.setText(casesArrayList.get(position).getModel());
        holder.case_form_factor.setText(casesArrayList.get(position).getForm_factor());
        holder.case_price.setText("P " + Double.toString(casesArrayList.get(position).getPrice()));
        Picasso.get().load(casesArrayList.get(position).getImage()).into(holder.case_image);

        holder.case_part_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SystemBuilder.class);
                Cases temp = casesArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                modulePrefs.saveCase("savedCase", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class CaseListViewHolder extends RecyclerView.ViewHolder {

        TextView case_model, case_form_factor, case_price;
        ImageView case_image;
        ImageButton case_part_button;

        public CaseListViewHolder(View view) {
            super(view);

            case_model = view.findViewById(R.id.case_model);
            case_form_factor = view.findViewById(R.id.case_form_factor);
            case_price = view.findViewById(R.id.case_price);
            case_image = view.findViewById(R.id.case_image);
            case_part_button = view.findViewById(R.id.case_part_button);
        }
    }
}