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
import com.mobdeve.leej.thejuanpc.BlogDetails;
import com.mobdeve.leej.thejuanpc.ModulePrefs;
import com.mobdeve.leej.thejuanpc.R;
import com.mobdeve.leej.thejuanpc.model.Blogs;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class BlogsListAdapter extends RecyclerView.Adapter<BlogsListAdapter.BlogsListViewHolder> {
    private ArrayList<Blogs> blogsArrayList;
    private Context context;
    private ModulePrefs modulePrefs;

    public BlogsListAdapter(Context context, ArrayList<Blogs> blogsArrayList) {
        this.blogsArrayList = blogsArrayList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return blogsArrayList.size();
    }

    @Override
    public BlogsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blogs_posted_list, parent, false);

        BlogsListViewHolder BlogsListViewHolder = new BlogsListViewHolder(view);
        return BlogsListViewHolder;
    }

    @Override
    public void onBindViewHolder(final BlogsListAdapter.BlogsListViewHolder holder, final int position) {
        modulePrefs = new ModulePrefs(context);
        Picasso.get().load(blogsArrayList.get(position).getFeatured_image()).into(holder.tv_image);
        holder.tv_title.setText(blogsArrayList.get(position).getBlog_title());

        holder.view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BlogDetails.class);
                Blogs temp = blogsArrayList.get(position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("blog_details", temp);
                context.startActivity(intent);
            }
        });
    }

    protected class BlogsListViewHolder extends RecyclerView.ViewHolder {

        ImageView tv_image;
        TextView tv_title;
        Button view_details;

        public BlogsListViewHolder(View view) {
            super(view);
            tv_image = view.findViewById(R.id.tv_image);
            tv_title = view.findViewById(R.id.tv_title);
            view_details = view.findViewById(R.id.view_details);
        }
    }
}
