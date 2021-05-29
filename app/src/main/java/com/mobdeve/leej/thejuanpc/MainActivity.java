package com.mobdeve.leej.thejuanpc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.leej.thejuanpc.model.user;

public class MainActivity extends AppCompatActivity {

    private EditText et_username,et_password;
    private Button btn_login, btn_signup;
    private int found;
    private ModulePrefs modulePrefs;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");
    private user logged_in_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modulePrefs = new ModulePrefs(getApplicationContext());
        init();
    }

    public void init(){
        logged_in_user = modulePrefs.loadUser("logged_in_user");
        found = 0;
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        if(logged_in_user == null || logged_in_user.equals("Nothing Saved")){
            btn_login.setOnClickListener(v -> {

                if(et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill out all fields",Toast.LENGTH_SHORT).show();
                }else{
                    validate(et_username.getText().toString().replace(" ", "") ,et_password.getText().toString().replace(" ", ""));
                }
            });

            btn_signup.setOnClickListener(v -> {

                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();
            });
        }else {
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            modulePrefs.saveUser("logged_in_user",logged_in_user);
            startActivity(intent);
            finish();
        }
    }

    private void validate(String un, String pwd ){
        found = 0;
        // We need to make new technique to receive multiple data
        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    user model = documentSnapshot.toObject(user.class);

                    String username = model.getUsername();
                    String password = model.getPassword();

                    if(username.equals(un) && password.equals(pwd)){
                        found = 1;
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        modulePrefs.saveUser("logged_in_user",model);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
                if(found == 0){
                    Toast.makeText(getApplicationContext(), "USER DOES NOT EXIST", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}