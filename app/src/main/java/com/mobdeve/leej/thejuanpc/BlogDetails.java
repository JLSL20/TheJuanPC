package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.mobdeve.leej.thejuanpc.model.Blogs;
import com.mobdeve.leej.thejuanpc.model.user;
import com.mobdeve.leej.thejuanpc.model.Builds;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlogDetails extends AppCompatActivity {

    private TextView blog_category, blog_title, blog_username, blog_date, blog_content, build_name, build_price, build_wattage;
    private ImageButton back_btn, like_btn;
    private ImageView pc_placeholder, featured_image;
    private ModulePrefs modulePrefs;
    private user user = new user();
    private CardView featured_build;
    private int isLike = 0;
    private Blogs blogs = new Blogs();
    private Builds Builds = new Builds();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference blogRef;
    private DocumentReference buildRef ;
    private ListenerRegistration listner;
    private ArrayList<String> liked_users;
    private int upvotes;
    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        modulePrefs = new ModulePrefs(getApplicationContext());
        from = modulePrefs.getStringPreferences("from");
        init();
    }

    private void init() {

        initializeVariables();

        //Get Blog Object from RecyclerView
        blogs = (Blogs) getIntent().getSerializableExtra("blog_details");
        //Get Build Object from Database using yung blog build_name

        buildRef = db.collection("Builds").document(blogs.getBuild_name() + " " + blogs.getUsername());
        listner = buildRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException error) {
                // preventing errors:

                if(error != null){
                    Toast.makeText(BlogDetails.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                //getting Data & Updating TextView:
                if (documentSnapshot.exists()){

                    // No, let's retrieve our java object
                    Builds = documentSnapshot.toObject(Builds.class);

                    build_name.setText(Builds.getBuild_name());
                    build_price.setText(String.valueOf(Builds.getTotalEstimatePrice()));
                    build_wattage.setText(String.valueOf(Builds.getTotalWattage()));
                    pc_placeholder.setImageResource(R.drawable.pc_icon);
                }
                else{

                }
            }
        });


        user = modulePrefs.loadUser("logged_in_user");



        isLike = checkLike(blogs.getLiked_users(), user);

        //Set blog details
        blog_category.setText(blogs.getBlog_status());
        blog_title.setText(blogs.getBlog_title());
        blog_username.setText("By " + blogs.getUsername());
        blog_date.setText(blogs.getDate_created());
        blog_content.setText(blogs.getContent());
        //Picasso to display Blog Featured Image
        Picasso.get().load(blogs.getFeatured_image()).into(featured_image);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.equalsIgnoreCase("SeeMoreBlogs")){
                    Intent intent = new Intent(getApplicationContext(),MyBuilds.class);
                    modulePrefs.saveStringPreferences("from", "BlogDetails");
                    startActivity(intent);
                    finish();
                }else if(from.equalsIgnoreCase("HomePage")){
                    Intent intent = new Intent(getApplicationContext(),HomePage.class);
                    startActivity(intent);
                    finish();
                }else if(from.equalsIgnoreCase("UserProfile")){
                    Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        featured_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuildDetails.class);
                intent.putExtra("build_details", Builds);
                startActivity(intent);
            }
        });

        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liked_users = blogs.getLiked_users();
                if(isLike ==0){


                        like_btn.setBackgroundResource(R.drawable.ic_like1); // This means liking
                        liked_users.add(user.getUsername());
                        upvotes = blogs.getUpvotes();
                        upvotes++;
                        blogs.setUpvotes(upvotes);
                        blogRef = db.collection("Blogs").document(blogs.getBlog_title()+" "+blogs.getUsername());
                        Map<String, Object> map = new HashMap<>();
                        map.put("liked_users", liked_users);
                        map.put("upvotes", upvotes);
                        blogRef.update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        isLike = 1;
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });



                }else{

                    if(isLike == 1) { //This means disliking
                        like_btn.setBackgroundResource(R.drawable.ic_like);
                        int counter =0;
                        int index = 0;
                        for (String user1 : liked_users ){
                            if(user1.equalsIgnoreCase(user.getUsername())){

                                index = counter;
                                break;
                            }
                            counter++;
                        }

                        liked_users.remove(index);
                        upvotes = blogs.getUpvotes();
                        upvotes--;
                        blogs.setUpvotes(upvotes);
                        blogRef = db.collection("Blogs").document(blogs.getBlog_title()+" "+blogs.getUsername());
                        Map<String, Object> map = new HashMap<>();
                        map.put("liked_users", liked_users);
                        map.put("upvotes", upvotes);
                        blogRef.update(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        isLike = 0;
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                    }
                }
            }
        });
    }

    private int checkLike(ArrayList<String> usersLiked, user user) {

        for(String users : usersLiked) {

            if(users.equalsIgnoreCase(user.getUsername())) {
                like_btn.setBackgroundResource(R.drawable.ic_like1);
                return 1;
            }
        }
        return 0;
    }

    private void initializeVariables() {

        blog_category = findViewById(R.id.blog_category);
        blog_title = findViewById(R.id.blog_title);
        blog_username = findViewById(R.id.blog_username);
        blog_date = findViewById(R.id.blog_date);
        blog_content = findViewById(R.id.blog_content);

        build_name = findViewById(R.id.build_name);
        build_price = findViewById(R.id.build_price);
        build_wattage = findViewById(R.id.build_wattage);

        pc_placeholder = findViewById(R.id.pc_placeholder);
        featured_image = findViewById(R.id.featured_image);
        back_btn = findViewById(R.id.back_btn);
        like_btn = findViewById(R.id.like_btn);
        featured_build = findViewById(R.id.featured_build);
    }

    @Override
    public void onBackPressed() {

    }
}