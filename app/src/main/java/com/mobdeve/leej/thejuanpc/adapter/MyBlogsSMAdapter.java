package com.mobdeve.leej.thejuanpc.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mobdeve.leej.thejuanpc.R;
import com.mobdeve.leej.thejuanpc.model.Blogs;

public class MyBlogsSMAdapter extends FirestoreRecyclerAdapter<Blogs, MyBlogsSMAdapter.BlogHolder> {

private OnItemClickListener listener;

public MyBlogsSMAdapter(@NonNull FirestoreRecyclerOptions<Blogs> options) {
        super(options);
        }

@Override
protected void onBindViewHolder(@NonNull BlogHolder holder, int position, @NonNull Blogs model) {
        holder.tv_blog_name.setText(model.getBlog_title());
        holder.tv_created_date.setText(model.getDate_created());
        holder.tv_up_vote.setText(String.valueOf(model.getUpvotes()));
        }

@NonNull
@Override
public BlogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_blogs_sm_items,parent,false);

        return new BlogHolder(v);
        }

public void deleteBlog(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
        }



class BlogHolder extends RecyclerView.ViewHolder{
    TextView tv_blog_name;
    TextView tv_created_date;
    TextView tv_up_vote;

    public BlogHolder (View itemView){
        super(itemView);
        tv_blog_name = itemView.findViewById(R.id.tv_blog_name);
        tv_created_date = itemView.findViewById(R.id.tv_created_date);
        tv_up_vote = itemView.findViewById(R.id.tv_up_vote);

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

    public void  setOnItemClickListener(MyBlogsSMAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}