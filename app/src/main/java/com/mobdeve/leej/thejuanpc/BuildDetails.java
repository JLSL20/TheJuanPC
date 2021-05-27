package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.leej.thejuanpc.model.Builds;
import com.mobdeve.leej.thejuanpc.model.CPU;
import com.mobdeve.leej.thejuanpc.model.Cases;
import com.mobdeve.leej.thejuanpc.model.GPU;
import com.mobdeve.leej.thejuanpc.model.Motherboard;
import com.mobdeve.leej.thejuanpc.model.PSU;
import com.mobdeve.leej.thejuanpc.model.RAM;
import com.mobdeve.leej.thejuanpc.model.Storage;
import com.squareup.picasso.Picasso;

public class BuildDetails extends AppCompatActivity {

    private TextView cpu_name, cpu_price, cpu_wattage,
            mobo_name, mobo_price, mobo_wattage,
            gpu_name, gpu_price, gpu_wattage,
            ram_name, ram_price, ram_wattage,
            case_name, case_price, case_wattage,
            storage_name, storage_price, storage_wattage,
            psu_name, psu_price, psu_wattage,
            total_price1, total_wattage1, build_name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Builds build;
    private CollectionReference gpuColRef = db.collection("GPU");
    private CollectionReference cpuColRef = db.collection("CPU");
    private CollectionReference psuColRef = db.collection("PSU");
    private CollectionReference motherboardColRef = db.collection("Motherboard");
    private CollectionReference caseColRef = db.collection("Case");
    private CollectionReference storageColRef = db.collection("Storage");
    private CollectionReference ramColRef = db.collection("RAM");
    private  CollectionReference buildColRef = db.collection("Builds");
    private GPU gpuObj;
    private CPU cpuObj;
    private PSU psuObj;
    private Motherboard moboObj;
    private Cases caseObj;
    private Storage storageObj;
    private RAM ramObj;
    private String from;
    private  ModulePrefs modulePrefs;





    private ImageView cpu_image, gpu_image, mobo_image, ram_image, case_image, storage_image, psu_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_details);
        modulePrefs = new ModulePrefs(getApplicationContext());
        from = modulePrefs.getStringPreferences("from");

        // Get Build object here
        build = (Builds) getIntent().getSerializableExtra("build_details");
        init();
    }

    private void init() {

        initializeVariables();

        build_name.setText(build.getBuild_name());
        total_price1.setText("Price: "+String.valueOf(build.getTotalEstimatePrice()));
        total_wattage1.setText("Wattage: "+ String.valueOf(build.getTotalWattage()));

        Query gpusQuery = gpuColRef.whereEqualTo("model",build.getGpu());

        gpusQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        gpuObj = document.toObject(GPU.class);
                        Picasso.get().load(gpuObj.getImage()).into(gpu_image);
                        gpu_name.setText(gpuObj.getModel());
                        gpu_price.setText(String.valueOf(gpuObj.getPrice()));
                        gpu_wattage.setText(String.valueOf(gpuObj.getWattage()));

                    }
                }
                else {

                }
            }
        });



    Query cpuQuery = cpuColRef.whereEqualTo("model",build.getCpu());

        cpuQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        cpuObj = document.toObject(CPU.class);
                        Picasso.get().load(cpuObj.getImage()).into(cpu_image);
                        cpu_name.setText(cpuObj.getModel());
                        cpu_price.setText(String.valueOf(cpuObj.getPrice()));
                        cpu_wattage.setText(String.valueOf(cpuObj.getWattage()));

                    }
                }
                else {

                }
            }
        });


        Query psuQuery = psuColRef.whereEqualTo("model",build.getPsu());

        psuQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        psuObj = document.toObject(PSU.class);
                        Picasso.get().load(psuObj.getImage()).into(psu_image);
                        psu_name.setText(psuObj.getModel());
                        psu_price.setText(String.valueOf(psuObj.getPrice()));
                        psu_wattage.setText(String.valueOf(psuObj.getWattage()));

                    }
                }
                else {

                }
            }
        });

        Query moboQuery = motherboardColRef.whereEqualTo("model",build.getMotherboard());

        moboQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        moboObj = document.toObject(Motherboard.class);
                        Picasso.get().load(moboObj.getImage()).into(mobo_image);
                        mobo_name.setText(moboObj.getModel());
                        mobo_price.setText(String.valueOf(moboObj.getPrice()));
                        mobo_wattage.setText(String.valueOf(moboObj.getWattage()));

                    }
                }
                else {

                }
            }
        });

        Query caseQuery = caseColRef.whereEqualTo("model",build.getPc_case());

        caseQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        caseObj = document.toObject(Cases.class);
                        Picasso.get().load(caseObj.getImage()).into(case_image);
                        case_name.setText(caseObj.getModel());
                        case_price.setText(String.valueOf(caseObj.getPrice()));

                    }
                }
                else {

                }
            }
        });

        Query storageQuery = storageColRef.whereEqualTo("model",build.getStorage());

        storageQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        storageObj = document.toObject(Storage.class);
                        Picasso.get().load(storageObj.getImage()).into(storage_image);
                        storage_name.setText(storageObj.getModel());
                        storage_price.setText(String.valueOf(storageObj.getPrice()));
                        storage_wattage.setText(String.valueOf(storageObj.getWattage()));

                    }
                }
                else {

                }
            }
        });

        Query ramQuery = ramColRef.whereEqualTo("model",build.getRam());

        ramQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        ramObj = document.toObject(RAM.class);
                        Picasso.get().load(ramObj.getImage()).into(ram_image);
                        ram_name.setText(ramObj.getModel());
                        ram_price.setText(String.valueOf(ramObj.getPrice()));
                        ram_wattage.setText(String.valueOf(ramObj.getWattage()));

                    }
                }
                else {

                }
            }
        });

    }

    private void initializeVariables() {
        cpu_image = findViewById(R.id.cpu_image);
        cpu_name = findViewById(R.id.cpu_name);
        cpu_price = findViewById(R.id.cpu_price);
        cpu_wattage = findViewById(R.id.cpu_wattage);

        mobo_image = findViewById(R.id.mobo_image);
        mobo_name = findViewById(R.id.mobo_name);
        mobo_price = findViewById(R.id.mobo_price);
        mobo_wattage = findViewById(R.id.mobo_wattage);

        gpu_image = findViewById(R.id.gpu_image);
        gpu_name = findViewById(R.id.gpu_name);
        gpu_price = findViewById(R.id.gpu_price);
        gpu_wattage = findViewById(R.id.gpu_wattage);

        ram_image = findViewById(R.id.ram_image);
        ram_name = findViewById(R.id.ram_name);
        ram_price = findViewById(R.id.ram_price);
        ram_wattage = findViewById(R.id.ram_wattage);

        case_image = findViewById(R.id.case_image);
        case_name = findViewById(R.id.case_name);
        case_price = findViewById(R.id.case_price);

        psu_image = findViewById(R.id.psu_image);
        psu_name = findViewById(R.id.psu_name);
        psu_price = findViewById(R.id.psu_price);
        psu_wattage = findViewById(R.id.psu_wattage);

        storage_image = findViewById(R.id.storage_image);
        storage_name = findViewById(R.id.storage_name);
        storage_price = findViewById(R.id.storage_price);
        storage_wattage = findViewById(R.id.storage_wattage);

        total_price1 = findViewById(R.id.total_price);
        total_wattage1 = findViewById(R.id.total_wattage);
        build_name = findViewById(R.id.build_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(from.equalsIgnoreCase("UserProfile")){
            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
            startActivity(intent);
            finish();
        } else if (from.equalsIgnoreCase("SeeMore")){
            Intent intent = new Intent(getApplicationContext(),MyBuilds.class);
            modulePrefs.saveStringPreferences("from", "BuildDetails");
            startActivity(intent);
            finish();
        }

    }
}