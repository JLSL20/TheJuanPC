package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mobdeve.leej.thejuanpc.model.user;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobdeve.leej.thejuanpc.adapter.BlogsListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.BuildListAdapter;
import com.mobdeve.leej.thejuanpc.model.Blogs;
import com.mobdeve.leej.thejuanpc.model.user;


public class HomePage extends AppCompatActivity {
    private user loggedin_user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private BottomNavigationView bottomNavigationView;
    private StorageReference mStorageReference;
    private ListenerRegistration listener;
    private CollectionReference blogsColRef = db.collection("Blogs");
    private ModulePrefs modulePrefs;

    private ImageView featured_image;
    private TextView featured_title, featured_username;
    private RecyclerView homePage_guides, homePage_posts;
    private ArrayList<Blogs> build_guides = new ArrayList<>();
    private ArrayList<Blogs> blog_posts = new ArrayList<>();
    private Blogs featured_guide = new Blogs();
    private BlogsListAdapter blogsListAdapter;
    private Intent intent;
    private Blogs blogs1,blogs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        modulePrefs = new ModulePrefs(getApplicationContext());
        modulePrefs.saveStringPreferences("from", "HomePage");
        init();

    }

    private void init() {

        initializeVariables();
        loggedin_user = modulePrefs.loadUser("logged_in_user");

        //featured_guide = Blog with highest upvote


        //Picasso to get image to put to featured_image





        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_account:
                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_build :
                        intent = new Intent(getApplicationContext(), SystemBuilder.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }


    private void initializeVariables() {
        featured_image = findViewById(R.id.featured_image);
        featured_title = findViewById(R.id.featured_title);
        featured_username = findViewById(R.id.featured_username);
        homePage_guides = findViewById(R.id.homePage_guides);
        homePage_posts = findViewById(R.id.homePage_posts);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }



    @Override
    protected void onStart() {
        super.onStart();




        Query blogsQuery = blogsColRef.whereEqualTo("blog_status","Build Guides");

        blogsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        blogs1 = document.toObject(Blogs.class);
                        build_guides.add(blogs1);
                    }

                    featured_guide =  getFeatured(build_guides);
                    featured_title.setText(featured_guide.getBlog_title());
                    featured_username.setText(featured_guide.getUsername());
                    Picasso.get().load(featured_guide.getFeatured_image()).into(featured_image);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    homePage_guides.setLayoutManager(layoutManager);
                    blogsListAdapter = new BlogsListAdapter(getApplicationContext(), build_guides);
                    homePage_guides.setAdapter(blogsListAdapter);
                }
                else {

                }
            }
        });


        blogsQuery = blogsColRef.whereEqualTo("blog_status","Blog Post");

        blogsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        blogs2 = document.toObject(Blogs.class);
                        blog_posts.add(blogs2);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    homePage_posts.setLayoutManager(layoutManager);
                    blogsListAdapter = new BlogsListAdapter(getApplicationContext(), blog_posts);
                    homePage_posts.setAdapter(blogsListAdapter);
                }
                else {

                }
            }
        });





    }

    private Blogs getFeatured(ArrayList<Blogs> build_guides){
        int counter = 0;
        int index =0;
        int minimum = build_guides.get(0).getUpvotes();
        for(Blogs blogs1: build_guides) {

            if(blogs1.getUpvotes() > minimum) {
                index = counter;
            }

            counter++;
        }
        return build_guides.get(index);
    }

    @Override
    public void onBackPressed() {

    }
}