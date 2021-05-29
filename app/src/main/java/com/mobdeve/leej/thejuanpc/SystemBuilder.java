package com.mobdeve.leej.thejuanpc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
import com.mobdeve.leej.thejuanpc.model.user;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SystemBuilder extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference gpuRef, cpuRef, mobaRef,psuRef,ramRef,storageRef,pc_caseRef;
    private CollectionReference gpuColRef = db.collection("GPU");
    private CollectionReference cpuColRef = db.collection("CPU");
    private CollectionReference psuColRef = db.collection("PSU");
    private CollectionReference motherboardColRef = db.collection("Motherboard");
    private CollectionReference caseColRef = db.collection("Case");
    private CollectionReference storageColRef = db.collection("Storage");
    private CollectionReference ramColRef = db.collection("RAM");
    private  CollectionReference buildColRef = db.collection("Builds");
    private DocumentSnapshot mLastQuerieDocument;

    private ModulePrefs modulePrefs;
    private double total_price = 0;
    private int total_wattage = 0;
    private Intent intent;
    private int part_validation = 0;
    private String temp;
    private String from;

    private ArrayList<CPU> CPU_list = new ArrayList<>();
    private ArrayList<Motherboard> Mobo_list = new ArrayList<>();
    private ArrayList<GPU> GPU_list = new ArrayList<>();
    private ArrayList<RAM> RAM_list = new ArrayList<>();
    private ArrayList<Storage> Storage_list = new ArrayList<>();
    private ArrayList<Cases> Case_list = new ArrayList<>();
    private ArrayList<PSU> PSU_list = new ArrayList<>();

    private CPU cpu = new CPU();
    private Motherboard mobo = new Motherboard();
    private GPU gpu = new GPU();
    private RAM ram = new RAM();
    private Storage storage = new Storage();
    private PSU psu = new PSU();
    private Cases cases = new Cases();
    private Builds system_build = new Builds();
    private  user user = new user();

    private TextView cpu_name, cpu_price, cpu_wattage,
            mobo_name, mobo_price, mobo_wattage,
            gpu_name, gpu_price, gpu_wattage,
            ram_name, ram_price, ram_wattage,
            case_name, case_price, case_wattage,
            storage_name, storage_price, storage_wattage,
            psu_name, psu_price, psu_wattage,
            total_price1, total_wattage1;

    private ImageView cpu_image, gpu_image, mobo_image, ram_image, case_image, storage_image, psu_image;
    private EditText build_name;
    private Button save_button;
    private ImageButton cpu_button, gpu_button, mobo_button, ram_button, case_button, storage_button, psu_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_builder);
        modulePrefs = new ModulePrefs(getApplicationContext());
        populate();
        init();
    }

    private void init() {
        initializeVariables();
        from = modulePrefs.getStringPreferences("from");

        //Open SharedPreferences for each part, set if meron, if wala placeholder
        OpenSharedPreferences();
        total_price1.setText("Total Price: " + Double.toString(total_price));
        total_wattage1.setText("Total Wattage: " + Integer.toString(total_wattage));
        user = modulePrefs.loadUser("logged_in_user");

        //For each button onClick, move to next Activity passing ArrayList, save SharedPreference of what type
        cpu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", CPU_list);
                modulePrefs.savePartType("partType", "CPU");
                startActivity(intent);
            }
        });

        mobo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", Mobo_list);
                modulePrefs.savePartType("partType", "Mobo");
                startActivity(intent);
            }
        });

        gpu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", GPU_list);
                modulePrefs.savePartType("partType", "GPU");
                startActivity(intent);
            }
        });

        ram_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", RAM_list);
                modulePrefs.savePartType("partType", "RAM");
                startActivity(intent);
            }
        });

        psu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", PSU_list);
                modulePrefs.savePartType("partType", "PSU");
                startActivity(intent);
            }
        });

        storage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", Storage_list);
                modulePrefs.savePartType("partType", "Storage");
                startActivity(intent);
            }
        });

        case_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PartsList.class);
                intent.putExtra("List", Case_list);
                modulePrefs.savePartType("partType", "Cases");
                startActivity(intent);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(build_name.getText().toString().equalsIgnoreCase("") || build_name.getText().toString().equalsIgnoreCase(" ")){
                    Toast.makeText(getApplicationContext(),"Please set build name",Toast.LENGTH_SHORT).show();
                } else {
                    part_validation = validateParts();

                    if(part_validation == 1) { // Save build properties
                        system_build.setBuild_name(build_name.getText().toString());
                        system_build.setPc_case(cases.getModel());
                        system_build.setCpu(cpu.getModel());
                        system_build.setGpu(gpu.getModel());
                        system_build.setMotherboard(mobo.getModel());
                        system_build.setPsu(psu.getModel());
                        system_build.setRam(ram.getModel());
                        system_build.setStorage(storage.getModel());
                        system_build.setUsername(user.getUsername());
                        system_build.setTotalEstimatePrice(total_price);
                        system_build.setTotalWattage(total_wattage);

                        buildColRef
                                .document(system_build.getBuild_name()+" "+system_build.getUsername())
                                .set(system_build)
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

                        modulePrefs.clearPartsPreferences();
                        intent = new Intent(getApplicationContext(), UserProfile.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (part_validation == 0)  {
            modulePrefs.savePartType("savedName", build_name.getText().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        temp = modulePrefs.getPartType("savedName");

        if(temp != null) {
            build_name.setText(temp);
        }
    }

    private int validateParts() {
        CPU temp = cpu;
        GPU temp1 = gpu;
        PSU temp2 = psu;
        Storage temp3 = storage;
        RAM temp4 = ram;
        Motherboard temp5 = mobo;
        Cases temp6 = cases;

        if (temp == null || temp1 == null || temp2 == null || temp3 == null || temp4 == null || temp5 == null || temp6 == null){
            Toast.makeText(getApplicationContext(), "Please fill out all parts!", Toast.LENGTH_LONG).show();
            return 0;
        }
        else {
            if(!temp.getSocket().equalsIgnoreCase(temp5.getSocket())) {
                Toast.makeText(getApplicationContext(), "CPU and Motherboard Socket do not match!", Toast.LENGTH_LONG).show();
                return 0;
            }
            else {
                if (temp4.getSpeed() < temp5.getReco_ram_speed()) {
                    Toast.makeText(getApplicationContext(), "Please select RAM with higher clock speed", Toast.LENGTH_LONG).show();
                    return 0;
                }
                else {
                    if (!temp5.getForm_factor().equalsIgnoreCase(temp6.getForm_factor())) {
                        Toast.makeText(getApplicationContext(), "Motherboard Form Factor does not fit case", Toast.LENGTH_LONG).show();
                        return 0;
                    }
                    else {
                        if (!(temp2.getWattage() >= total_wattage)) {
                            Toast.makeText(getApplicationContext(), "PSU Wattage cannot accomodate the parts", Toast.LENGTH_LONG).show();
                            return 0;
                        }
                        else {
                            return 1;
                        }
                    }
                }
            }
        }
    }

    private void OpenSharedPreferences() {
        cpu = modulePrefs.loadCPU("savedCPU");
        mobo = modulePrefs.loadMobo("savedMobo");
        gpu = modulePrefs.loadGPU("savedGPU");
        storage = modulePrefs.loadStorage("savedStorage");
        psu = modulePrefs.loadPSU("savedPSU");
        ram = modulePrefs.loadRAM("savedRAM");
        cases = modulePrefs.loadCases("savedCase");

        if(cpu != null) {
            //   cpu_image.setImageResource(cpu.getImgID());
            Picasso.get().load(cpu.getImage()).into(cpu_image);
            cpu_name.setText(cpu.getModel());
            cpu_wattage.setText(Integer.toString(cpu.getWattage()));
            cpu_price.setText(Double.toString(cpu.getPrice()));
            total_price = total_price + cpu.getPrice();
            total_wattage = total_wattage + cpu.getWattage();
        }
        else {
            cpu_name.setText("No CPU Chosen yet!");
        }

        if(mobo != null) {
            Picasso.get().load(mobo.getImage()).into(mobo_image);
            mobo_name.setText(mobo.getModel());
            mobo_wattage.setText(Integer.toString(mobo.getWattage()));
            mobo_price.setText(Double.toString(mobo.getPrice()));
            total_price = total_price + mobo.getPrice();
            total_wattage = total_wattage + mobo.getWattage();
        }
        else {
            mobo_name.setText("No Motherboard Chosen yet!");
        }

        if(gpu != null) {
            Picasso.get().load(gpu.getImage()).into(gpu_image);
            gpu_name.setText(gpu.getModel());
            gpu_wattage.setText(Integer.toString(gpu.getWattage()));
            gpu_price.setText(Double.toString(gpu.getPrice()));
            total_price = total_price + gpu.getPrice();
            total_wattage = total_wattage + gpu.getWattage();
        }
        else {
            gpu_name.setText("No GPU Chosen yet!");
        }

        if(ram != null) {
            Picasso.get().load(ram.getImage()).into(ram_image);
            ram_name.setText(ram.getModel());
            ram_wattage.setText(Integer.toString(ram.getWattage()));
            ram_price.setText(Double.toString(ram.getPrice()));
            total_price = total_price + ram.getPrice();
            total_wattage = total_wattage + ram.getWattage();
        }
        else {
            ram_name.setText("No RAM Chosen yet!");
        }

        if(psu != null) {
            Picasso.get().load(psu.getImage()).into(psu_image);
            psu_name.setText(psu.getModel());
            psu_wattage.setText(Integer.toString(psu.getWattage()));
            psu_price.setText(Double.toString(psu.getPrice()));
            total_price = total_price + psu.getPrice();
        }
        else {
            psu_name.setText("No PSU Chosen yet!");
        }

        if(storage != null) {
            Picasso.get().load(storage.getImage()).into(storage_image);
            storage_name.setText(storage.getModel());
            storage_wattage.setText(Integer.toString(storage.getWattage()));
            storage_price.setText(Double.toString(storage.getPrice()));
            total_price = total_price + storage.getPrice();
            total_wattage = total_wattage + storage.getWattage();
        }
        else {
            storage_name.setText("No Storage Chosen yet!");
        }

        if(cases != null) {
            Picasso.get().load(cases.getImage()).into(case_image);
            case_name.setText(cases.getModel());
            case_wattage.setText("0");
            case_price.setText(Double.toString(cases.getPrice()));
            total_price = total_price + cases.getPrice();
        }
        else {
            case_name.setText("No Case Chosen yet!");
        }
    }

    private void initializeVariables(){
        cpu_image = findViewById(R.id.cpu_image);
        cpu_name = findViewById(R.id.cpu_name);
        cpu_price = findViewById(R.id.cpu_price);
        cpu_wattage = findViewById(R.id.cpu_wattage);
        cpu_button = findViewById(R.id.cpu_button);

        mobo_image = findViewById(R.id.mobo_image);
        mobo_name = findViewById(R.id.mobo_name);
        mobo_price = findViewById(R.id.mobo_price);
        mobo_wattage = findViewById(R.id.mobo_wattage);
        mobo_button = findViewById(R.id.mobo_button);

        gpu_image = findViewById(R.id.gpu_image);
        gpu_name = findViewById(R.id.gpu_name);
        gpu_price = findViewById(R.id.gpu_price);
        gpu_wattage = findViewById(R.id.gpu_wattage);
        gpu_button = findViewById(R.id.gpu_button);

        ram_image = findViewById(R.id.ram_image);
        ram_name = findViewById(R.id.ram_name);
        ram_price = findViewById(R.id.ram_price);
        ram_wattage = findViewById(R.id.ram_wattage);
        ram_button = findViewById(R.id.ram_button);

        case_image = findViewById(R.id.case_image);
        case_name = findViewById(R.id.case_name);
        case_price = findViewById(R.id.case_price);
        case_wattage = findViewById(R.id.case_wattage);
        case_button = findViewById(R.id.case_button);

        psu_image = findViewById(R.id.psu_image);
        psu_name = findViewById(R.id.psu_name);
        psu_price = findViewById(R.id.psu_price);
        psu_wattage = findViewById(R.id.psu_wattage);
        psu_button = findViewById(R.id.psu_button);

        storage_image = findViewById(R.id.storage_image);
        storage_name = findViewById(R.id.storage_name);
        storage_price = findViewById(R.id.storage_price);
        storage_wattage = findViewById(R.id.storage_wattage);
        storage_button = findViewById(R.id.storage_button);

        total_price1 = findViewById(R.id.total_price);
        total_wattage1 = findViewById(R.id.total_wattage);
        build_name = findViewById(R.id.build_name);
        save_button = findViewById(R.id.save_build);
    }

    private void populate() {
        gpuColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    GPU model = documentSnapshot.toObject(GPU.class);
                    GPU_list.add(model);
                }
            }
        });

        cpuColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    CPU model = documentSnapshot.toObject(CPU.class);
                    CPU_list.add(model);
                }
            }
        });

        psuColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    PSU model = documentSnapshot.toObject(PSU.class);
                    PSU_list.add(model);
                }
            }
        });

        storageColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    Storage model = documentSnapshot.toObject(Storage.class);
                    Storage_list.add(model);
                }
            }
        });

        caseColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    Cases model = documentSnapshot.toObject(Cases.class);
                    Case_list.add(model);
                }
            }
        });

        ramColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    RAM model = documentSnapshot.toObject(RAM.class);
                    RAM_list.add(model);
                }
            }
        });

        motherboardColRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // Preventing Errors
                if(error != null){
                    return;
                }

                for(QueryDocumentSnapshot documentSnapshot : value){

                    Motherboard model = documentSnapshot.toObject(Motherboard.class);
                    Mobo_list.add(model);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(from.equalsIgnoreCase("UserProfile")){
            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
            startActivity(intent);
            finish();
        }else  if(from.equalsIgnoreCase("HomePage")){
            Intent intent = new Intent(getApplicationContext(),HomePage.class);
            startActivity(intent);
            finish();
        }
        else  if(from.equalsIgnoreCase("MyBuilds")){
            Intent intent = new Intent(getApplicationContext(),MyBuilds.class);
            startActivity(intent);
            finish();
        }
    }
}