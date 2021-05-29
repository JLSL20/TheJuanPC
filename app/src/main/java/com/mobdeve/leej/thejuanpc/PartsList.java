package com.mobdeve.leej.thejuanpc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mobdeve.leej.thejuanpc.adapter.CPUListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.CaseListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.GPUListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.MoboListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.PSUListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.RAMListAdapter;
import com.mobdeve.leej.thejuanpc.adapter.StorageListAdapter;
import com.mobdeve.leej.thejuanpc.model.CPU;
import com.mobdeve.leej.thejuanpc.model.Cases;
import com.mobdeve.leej.thejuanpc.model.GPU;
import com.mobdeve.leej.thejuanpc.model.Motherboard;
import com.mobdeve.leej.thejuanpc.model.PSU;
import com.mobdeve.leej.thejuanpc.model.RAM;
import com.mobdeve.leej.thejuanpc.model.Storage;

import java.util.ArrayList;

public class PartsList extends AppCompatActivity {

    private RecyclerView rv_list;
    private ArrayList<CPU> CPU_list = new ArrayList<>();
    private ArrayList<Motherboard> Mobo_list = new ArrayList<>();
    private ArrayList<GPU> GPU_list = new ArrayList<>();
    private ArrayList<RAM> RAM_list = new ArrayList<>();
    private ArrayList<Storage> Storage_list = new ArrayList<>();
    private ArrayList<Cases> Case_list = new ArrayList<>();
    private ArrayList<PSU> PSU_list = new ArrayList<>();

    private CPUListAdapter cpuListAdapter;
    private MoboListAdapter moboListAdapter;
    private GPUListAdapter gpuListAdapter;
    private RAMListAdapter ramListAdapter;
    private StorageListAdapter storageListAdapter;
    private PSUListAdapter psuListAdapter;
    private CaseListAdapter caseListAdapter;

    private ModulePrefs modulePrefs;
    private String partType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_list);

        modulePrefs = new ModulePrefs(getApplicationContext());
        init();
    }

    private void init(){
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Open the SharedPreferences of partType
        partType = modulePrefs.getPartType("partType");

        //Function that obtains the Part ArrayList and set Adapter accordingly
        setDisplay(partType);
    }

    private void setDisplay(String partType) {
        if(partType.equalsIgnoreCase("CPU")) {
            CPU_list = (ArrayList<CPU>) getIntent().getSerializableExtra("List");
            cpuListAdapter = new CPUListAdapter(getApplicationContext(), CPU_list);
            rv_list.setAdapter(cpuListAdapter);
        }
        else if (partType.equalsIgnoreCase("Mobo")) {
            Mobo_list = (ArrayList<Motherboard>) getIntent().getSerializableExtra("List");
            moboListAdapter = new MoboListAdapter(getApplicationContext(), Mobo_list);
            rv_list.setAdapter(moboListAdapter);
        }
        else if(partType.equalsIgnoreCase("GPU")) {
            GPU_list = (ArrayList<GPU>) getIntent().getSerializableExtra("List");
            gpuListAdapter = new GPUListAdapter(getApplicationContext(), GPU_list);
            rv_list.setAdapter(gpuListAdapter);
        }
        else if (partType.equalsIgnoreCase("RAM")){
            RAM_list = (ArrayList<RAM>) getIntent().getSerializableExtra("List");
            ramListAdapter = new RAMListAdapter(getApplicationContext(), RAM_list);
            rv_list.setAdapter(ramListAdapter);
        }
        else if (partType.equalsIgnoreCase("PSU")) {
            PSU_list = (ArrayList<PSU>) getIntent().getSerializableExtra("List");
            psuListAdapter = new PSUListAdapter(getApplicationContext(), PSU_list);
            rv_list.setAdapter(psuListAdapter);
        }
        else if (partType.equalsIgnoreCase("Storage")) {
            Storage_list = (ArrayList<Storage>) getIntent().getSerializableExtra("List");
            storageListAdapter = new StorageListAdapter(getApplicationContext(), Storage_list);
            rv_list.setAdapter(storageListAdapter);
        }
        else if (partType.equalsIgnoreCase("Cases")) {
            Case_list = (ArrayList<Cases>) getIntent().getSerializableExtra("List");
            caseListAdapter = new CaseListAdapter(getApplicationContext(), Case_list);
            rv_list.setAdapter(caseListAdapter);
        }
    }
}