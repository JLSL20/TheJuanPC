package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobdeve.leej.thejuanpc.model.Blogs;
import com.mobdeve.leej.thejuanpc.model.Builds;
import com.mobdeve.leej.thejuanpc.model.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateBlog extends AppCompatActivity {

    private TextView build_name, build_price, build_wattage,  change_btn;
    private ImageView pc_placeholder;
    private ImageButton selectImage, back_btn;
    private Button publish_btn;
    private EditText blog_title, blog_content;

    private Builds selected_build = new Builds();
    private ModulePrefs modulePrefs;
    private Blogs blogTemp = new Blogs();
    private  static  final int GALLERY_REQUEST = 1;
    private Uri imageUri = null;
    private Date date;
    private user logged_in_user;


    private ProgressDialog progress;
    private StorageReference mStorageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference blogsColRef = db.collection("Blogs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);

        modulePrefs = new ModulePrefs(getApplicationContext());
        logged_in_user = modulePrefs.loadUser("logged_in_user");
        progress = new ProgressDialog(this);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        init();
    }

    private void init() {

        initializeVariables();

        //Load User Information
        // Get Build Object
        selected_build = (Builds) getIntent().getSerializableExtra("build");

        //Display Build Object
        build_name.setText(selected_build.getBuild_name());
        build_price.setText(Double.toString(selected_build.getTotalEstimatePrice()));
        build_wattage.setText(Integer.toString(selected_build.getTotalWattage()));
        pc_placeholder.setImageResource(R.drawable.pc_icon);


        if(modulePrefs.getPartType("blog_title") != null) {
            blog_title.setText(modulePrefs.getPartType("blog_title"));
        }

        if(modulePrefs.getPartType("blog_content") != null) {
            blog_content.setText(modulePrefs.getPartType("blog_content"));
        }

        //On Click of Change Button -> Go back to PC List, but saves Blog Title, and Blog Content, if there is
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBuilds.class);
                modulePrefs.saveStringPreferences("from", "SeeMoreBuilds");

                if (!blog_title.getText().toString().equalsIgnoreCase("")) {
                    modulePrefs.savePartType("blog_title", blog_title.getText().toString());
                }

                if (!blog_content.getText().toString().equalsIgnoreCase("")) {
                    modulePrefs.savePartType("blog_content", blog_content.getText().toString());
                }

                startActivity(intent);
                finish();
            }
        });

        //Back Button since there is no navigation bar
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),HomePage.class);
               startActivity(intent);
               finish();
            }
        });

        //Image Button to get Image from Phone Gallery
        selectImage.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,GALLERY_REQUEST);
        });

        //Actions to do when publishing the inputted blog
        publish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startUploading();
                //Code to get featured_image link here

                //Code to save blog Item to database


            }
        });
    }

    private void initializeVariables() {
        build_name = findViewById(R.id.build_name);
        build_price = findViewById(R.id.build_price);
        build_wattage = findViewById(R.id.build_wattage);

        pc_placeholder = findViewById(R.id.pc_placeholder);
        selectImage = findViewById(R.id.selectImage);
        back_btn = findViewById(R.id.back_btn);
        publish_btn = findViewById(R.id.publish_btn);
        blog_title = findViewById(R.id.blog_title);
        blog_content = findViewById(R.id.blog_content);
        change_btn = findViewById(R.id.change_btn);
    }






    private void startUploading(){

        if( TextUtils.isEmpty(blog_title.getText().toString()) || TextUtils.isEmpty(blog_content.getText().toString()) ||imageUri == null){
            Toast.makeText(getApplicationContext(),"No empty field allowed..",Toast.LENGTH_SHORT).show();
        }else {
            progress.setMessage("Posting blog...");
            progress.show();
            StorageReference filepath = mStorageReference.child("Blogs_img").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            blogTemp.setBlog_title(blog_title.getText().toString());
                            blogTemp.setContent(blog_content.getText().toString());
                            blogTemp.setUsername(logged_in_user.getUsername());

                            if(logged_in_user.getStatus().equalsIgnoreCase("regular")) {
                                blogTemp.setBlog_status("Blog Post");
                            }
                            else {
                                blogTemp.setBlog_status("Build Guides");
                            }

                            //Getting Date?
                            date = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                            blogTemp.setDate_created(df.format(date));

                            blogTemp.setBuild_name(selected_build.getBuild_name());
                            blogTemp.setUpvotes(0);
                            blogTemp.setFeatured_image(uri.toString());


                            blogsColRef
                                    .document(blogTemp.getBlog_title()+ " " + logged_in_user.getUsername())
                                    .set(blogTemp)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(getApplicationContext(), HomePage.class); //Change to HomePage
                                            modulePrefs.clearBlogDetails();
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                    });

                    progress.dismiss();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            selectImage.setImageURI(imageUri);
        }
    }

    @Override
    public void onBackPressed() {

    }
}