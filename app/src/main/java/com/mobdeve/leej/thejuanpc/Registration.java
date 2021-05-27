package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.leej.thejuanpc.model.user;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {
    private EditText et_first_name, et_last_name,et_username_reg,et_password_reg;
    private Button btn_register;
    private int found = 0;
    private ModulePrefs modulePrefs;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");
    private ArrayList<user> userArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        modulePrefs = new ModulePrefs(getApplicationContext());
        init();
    }
    private void init( ){
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_username_reg = findViewById(R.id.et_username_reg);
        et_password_reg = findViewById(R.id.et_password_reg);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(v -> {
            if(et_first_name.getText().toString().isEmpty() || et_last_name.getText().toString().isEmpty() ||et_password_reg.getText().toString().isEmpty() ||et_username_reg.getText().toString().isEmpty() ){
                Toast.makeText(getApplicationContext(),"Fill out all fields",Toast.LENGTH_SHORT).show();
            }else {
                checkDuplicate(et_first_name.getText().toString().replace(" ", "") , et_last_name.getText().toString().replace(" ", "") , et_password_reg.getText().toString().replace(" ", "") ,   et_username_reg.getText().toString().replace(" ", "") );
            }

        });

    }

    private  void checkDuplicate( String firstName, String lastName, String pass, String un){
        found = 0;

        for (user user1 : userArrayList){

            if(user1.getUsername().equals(un)){
                found = 1;
                break;
            }

        }

        if(found == 1){
            Toast.makeText(getApplicationContext(),"Username already taken",Toast.LENGTH_SHORT).show();
        }

        if (found == 0){
            SaveUser( firstName, lastName, pass, un );
        }

    }

    private void SaveUser( String firstName, String lastName, String pass, String un){


            user model = new user(firstName,lastName,pass,un);

            collectionReference
                    .document(un)
                    .set(model)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Registration.this, "Account Created",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            modulePrefs.saveUser("logged_in_user",model);
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

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }
                // Making a loop to get all data:
                for(QueryDocumentSnapshot documentSnapshot : value){

                    user model = documentSnapshot.toObject(user.class);
                    userArrayList.add(model);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}