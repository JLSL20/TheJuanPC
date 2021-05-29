package com.mobdeve.leej.thejuanpc;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobdeve.leej.thejuanpc.model.CPU;
import com.mobdeve.leej.thejuanpc.model.Cases;
import com.mobdeve.leej.thejuanpc.model.GPU;
import com.mobdeve.leej.thejuanpc.model.Motherboard;
import com.mobdeve.leej.thejuanpc.model.PSU;
import com.mobdeve.leej.thejuanpc.model.RAM;
import com.mobdeve.leej.thejuanpc.model.Storage;
import com.mobdeve.leej.thejuanpc.model.user;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModulePrefs {

    private SharedPreferences appPreferences;
    private final String PREFS = "appPreferences";

    public ModulePrefs(Context context){
        appPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveStringPreferences(String key, String value){
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        prefsEditor.putString(key,value);
        prefsEditor.commit();
    }

    public void clearLoggedInUserPref() {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        prefsEditor.remove("logged_in_user");
        prefsEditor.commit();
    }

    public String getStringPreferences(String key){
        return (appPreferences.getString(key,"Nothing Saved"));
    }

    public void savePartType(String key, String value) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getPartType(String key) {
        return (appPreferences.getString(key, ""));
    }

    public void clearPartsPreferences() {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        prefsEditor.remove("savedCPU");
        prefsEditor.remove("savedGPU");
        prefsEditor.remove("savedMobo");
        prefsEditor.remove("savedPSU");
        prefsEditor.remove("savedStorage");
        prefsEditor.remove("savedRAM");
        prefsEditor.remove("savedCase");
        prefsEditor.remove("savedName");

        prefsEditor.commit();
    }

    public void clearPreferences() {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public void saveCPU(String key, CPU cpu) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cpu);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public CPU loadCPU(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<CPU>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveMobo(String key, Motherboard mobo) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mobo);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public Motherboard loadMobo(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<Motherboard>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveGPU(String key, GPU gpu) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(gpu);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public GPU loadGPU(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<GPU>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveRAM(String key, RAM ram) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ram);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public RAM loadRAM(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<RAM>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void savePSU(String key, PSU psu) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(psu);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public PSU loadPSU(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<PSU>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveStorage(String key, Storage storage) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(storage);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public Storage loadStorage(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<Storage>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveCase(String key, Cases cases) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cases);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public Cases loadCases(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<Cases>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveUser(String key, user user) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public user loadUser(String key) {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = appPreferences.getString(key, null);
        Type type = new TypeToken<user>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void clearBlogDetails() {
        SharedPreferences.Editor prefsEditor = appPreferences.edit();
        prefsEditor.remove("blog_title");
        prefsEditor.remove("blog_content");

        prefsEditor.commit();
    }
}