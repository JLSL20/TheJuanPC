package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.mobdeve.leej.thejuanpc.adapter.BlogsListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.BuildListAdapter;
import com.mobdeve.leej.thejuanpc.model.Blogs;
import com.mobdeve.leej.thejuanpc.model.Builds;
import com.mobdeve.leej.thejuanpc.model.GPU;
import com.mobdeve.leej.thejuanpc.model.user;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserProfile extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ListenerRegistration listener;
    private com.mobdeve.leej.thejuanpc.model.user user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");
    private  DocumentReference ref;
    private  DocumentReference buildRef = db.collection("Builds").document("my Build 2");
    private CollectionReference buildColRef = db.collection("Builds");
    private CollectionReference blogColRef = db.collection("Blogs");
    private CollectionReference gpuColRef = db.collection("GPU");
    private ModulePrefs modulePrefs;


    private ImageView profile_pic;
    private TextView username;
    private Button status, see_more_builds,btn_logout,btn_see_blogs;
    private BuildListAdapter buildListAdapter;
    private RecyclerView rv_list_build, rv_list_blogs;
    private ArrayList<Builds> buildArrayList = new ArrayList<>();
    private ArrayList<Blogs> blogsArrayList = new ArrayList<>();
    private BlogsListAdapter blogsListAdapter;
    private Intent intent;
    private Builds build1;
    private Blogs blogs1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modulePrefs = new ModulePrefs(getApplicationContext());
        modulePrefs.saveStringPreferences("from", "UserProfile");
        setContentView(R.layout.activity_user_profile);
        user = modulePrefs.loadUser("logged_in_user");
        init();
    }

    private void init() {



        profile_pic = findViewById(R.id.profile_pic);
        username = findViewById(R.id.username);
        status = findViewById(R.id.status);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        rv_list_build = findViewById(R.id.rv_list_builds);
        see_more_builds = findViewById(R.id.see_more_builds);
        btn_logout = findViewById(R.id.btn_logout);
        btn_see_blogs = findViewById(R.id.see_more_blogs);
        rv_list_blogs = findViewById(R.id.rv_list_blogs);

        bottomNavigationView.setSelectedItemId(R.id.ic_account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.ic_build :
                        intent = new Intent(getApplicationContext(), SystemBuilder.class);
                        modulePrefs.saveStringPreferences("from", "UserProfile");
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        see_more_builds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBuilds.class);
                modulePrefs.saveStringPreferences("from", "SeeMoreBuilds");
                startActivity(intent);
                finish();

            }
        });

        profile_pic.setImageResource(R.drawable.polyfrog);
        username.setText(user.getUsername());

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStatus(user.getUsername());
            }
        });


        btn_logout.setOnClickListener(v -> {
            modulePrefs.clearLoggedInUserPref();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        });


        btn_see_blogs.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyBuilds.class);
            modulePrefs.saveStringPreferences("from", "SeeMoreBlogs");
            startActivity(intent);
            finish();
        });

    }


    private void verifyStatus(String username) {

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(username);

        Map<String, Object> map = new HashMap<>();
        map.put("status", "verified");

        docRef.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();

        user = modulePrefs.loadUser("logged_in_user");

        ref = db.collection("users").document(user.getUsername());
        status = findViewById(R.id.status);
        listener = ref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException error) {
                // preventing errors:

                if(error != null){
                    Toast.makeText(UserProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                //getting Data & Updating TextView:
                if (documentSnapshot.exists()){

                    // No, let's retrieve our java object
                    user model = documentSnapshot.toObject(user.class);

                    String status1 = model.getStatus();

                    status.setText(status1);

                }
                else{

                }

            }
        });


        if(buildArrayList.isEmpty() || buildArrayList == null){
            Query buildsQuery = buildColRef.whereEqualTo("username",user.getUsername());

            buildsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            build1 = document.toObject(Builds.class);
                            buildArrayList.add(build1);


                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        rv_list_build.setLayoutManager(layoutManager);
                        buildListAdapter = new BuildListAdapter(getApplicationContext(), buildArrayList);
                        rv_list_build.setAdapter(buildListAdapter);
                    }
                    else {

                    }
                }
            });
        }






        if(blogsArrayList.isEmpty() || blogsArrayList == null){
            Query blogsQuery = blogColRef.whereEqualTo("username",user.getUsername());

            blogsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            blogs1 = document.toObject(Blogs.class);
                            blogsArrayList.add(blogs1);
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        rv_list_blogs.setLayoutManager(layoutManager);
                        blogsListAdapter = new BlogsListAdapter(getApplicationContext(), blogsArrayList);
                        rv_list_blogs.setAdapter(blogsListAdapter);
                    }
                    else {

                    }
                }
            });
        }



    }

    @Override
    public void onBackPressed(){

    }
}


