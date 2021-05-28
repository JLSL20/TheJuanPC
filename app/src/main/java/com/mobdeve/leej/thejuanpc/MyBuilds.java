package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.leej.thejuanpc.adapter.MyBlogsSMAdapter;
import com.mobdeve.leej.thejuanpc.adapter.MyBuildsSMAdapter;
import com.mobdeve.leej.thejuanpc.model.Blogs;
import com.mobdeve.leej.thejuanpc.model.Builds;
import com.mobdeve.leej.thejuanpc.model.Cases;
import com.mobdeve.leej.thejuanpc.model.user;

import java.util.ArrayList;

public class MyBuilds extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference buildsColRef = db.collection("Builds");
    private CollectionReference blogsColRef = db.collection("Blogs");
    private DocumentReference buildRef;

    private MyBuildsSMAdapter myBuildsSMAdapter;
    private MyBlogsSMAdapter myBlogsSMAdapter;
    private ModulePrefs modulePrefs;

    private user user;
    private BottomNavigationView bottomNavigationView;
    private Builds build1;
    private ArrayList<Builds> buildArrayList = new ArrayList<Builds>();
    private String from;

    private TextView tv_header;
    private ListenerRegistration listner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_builds);
        modulePrefs = new ModulePrefs(getApplicationContext());
        setUpRecyclerView();
        from = modulePrefs.getStringPreferences("from");
        tv_header = findViewById(R.id.tv_header);

        if(from.equalsIgnoreCase("SeeMoreBuilds") || from.equalsIgnoreCase("BuildDetails") ||from.equalsIgnoreCase("MyBuilds") ){
            tv_header.setText("MY BUILDS");
        }else if (from.equalsIgnoreCase("SeeMoreBlogs")||from.equalsIgnoreCase("BlogDetails")){
            tv_header.setText("MY BLOGS");
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent = new Intent(getApplicationContext(),HomePage.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_account:
                        intent = new Intent(getApplicationContext(), UserProfile.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_build:
                        intent = new Intent(getApplicationContext(), SystemBuilder.class);
                        startActivity(intent);
                        modulePrefs.saveStringPreferences("from", "MyBuilds");
                        finish();
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

    }
    private void setUpRecyclerView(){
        user = modulePrefs.loadUser("logged_in_user");
        from = modulePrefs.getStringPreferences("from");

        if (from.equalsIgnoreCase("SeeMoreBuilds") || from.equalsIgnoreCase("BuildDetails") || from.equalsIgnoreCase("MyBuilds")){

            Query query = buildsColRef.whereEqualTo("username", user.getUsername());

            FirestoreRecyclerOptions<Builds> options = new FirestoreRecyclerOptions.Builder<Builds>()
                    .setQuery(query, Builds.class)
                    .build();

            myBuildsSMAdapter = new MyBuildsSMAdapter(options);

            RecyclerView recyclerView = findViewById(R.id.rv_list_my);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(myBuildsSMAdapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback (0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    int position = viewHolder.getAdapterPosition();

                    switch (direction){
                        case ItemTouchHelper.LEFT:
                            buildRef = myBuildsSMAdapter.createBlog(viewHolder.getAdapterPosition());
                            Toast.makeText(MyBuilds.this, buildRef.getPath(), Toast.LENGTH_SHORT).show();
                            getBuild(buildRef);

                            break;
                        case ItemTouchHelper.RIGHT:
                            myBuildsSMAdapter.deleteBuild(viewHolder.getAdapterPosition());
                            break;
                    }

                }
            }).attachToRecyclerView(recyclerView);

            myBuildsSMAdapter.setOnItemClickListener(new MyBuildsSMAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    Builds build = documentSnapshot.toObject(Builds.class);
                    Intent intent = new Intent(getApplicationContext(), BuildDetails.class);
                    intent.putExtra("build_details",build);
                    modulePrefs.saveStringPreferences("from", "SeeMore");
                    startActivity(intent);
                    finish();

                }
            });

        } else if(from.equalsIgnoreCase("SeeMoreBlogs")||from.equalsIgnoreCase("BlogDetails")){
            Query query = blogsColRef.whereEqualTo("username",user.getUsername());

            FirestoreRecyclerOptions<Blogs> options = new FirestoreRecyclerOptions.Builder<Blogs>()
                    .setQuery(query, Blogs.class)
                    .build();

            myBlogsSMAdapter = new MyBlogsSMAdapter(options);

            RecyclerView recyclerView = findViewById(R.id.rv_list_my);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(myBlogsSMAdapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback (0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                            myBlogsSMAdapter.deleteBlog(viewHolder.getAdapterPosition());

                }
            }).attachToRecyclerView(recyclerView);

            myBlogsSMAdapter.setOnItemClickListener(new MyBlogsSMAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    Blogs blog = documentSnapshot.toObject(Blogs.class);
                    Intent intent = new Intent(getApplicationContext(), BlogDetails.class);
                    intent.putExtra("blog_details",blog);
                    modulePrefs.saveStringPreferences("from", "SeeMoreBlogs");
                    startActivity(intent);
                    finish();
                }
            });

        }
    }

    private void getBuild(DocumentReference buildRef){
        listner = buildRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException error) {
                // preventing errors:

                if(error != null){
                    Toast.makeText(MyBuilds.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                //getting Data & Updating TextView:
                if (documentSnapshot.exists()){

                    // No, let's retrieve our java object
                    Builds model = documentSnapshot.toObject(Builds.class);

                    Intent intent = new Intent(getApplicationContext(),CreateBlog.class);
                    intent.putExtra("build",model);
                    startActivity(intent);
                    finish();
                }
                else{

                }

            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        if (from.equalsIgnoreCase("SeeMoreBuilds") || from.equalsIgnoreCase("BuildDetails") || from.equalsIgnoreCase("MyBuilds")){
            myBuildsSMAdapter.startListening();
        }else if(from.equalsIgnoreCase("SeeMoreBlogs") ||from.equalsIgnoreCase("BlogDetails")){
            myBlogsSMAdapter.startListening();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (from.equalsIgnoreCase("SeeMoreBuilds") || from.equalsIgnoreCase("BuildDetails") || from.equalsIgnoreCase("MyBuilds")){
            myBuildsSMAdapter.stopListening();
        } else if(from.equalsIgnoreCase("SeeMoreBlogs") ||from.equalsIgnoreCase("BlogDetails")){
            myBlogsSMAdapter.stopListening();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
        startActivity(intent);
        finish();
    }
}