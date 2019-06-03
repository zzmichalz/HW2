package com.example.hw2;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    public static List<content> items = new ArrayList<>();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                addFragment();
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                camFragment();
            }
        });

            ParseJson();
            setNames();
            initRecycylerView();
    }

    public void setNames(){

        mNames.clear();
        mImages.clear();

            for (int i = 0; i < items.size(); i++) {
                mNames.add(items.get(i).name);
                mImages.add(items.get(i).photo);
            }
    }

    public void ParseJson(){
        Type test = new TypeToken<List<content>>(){}.getType();
        Gson gson = new Gson();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("/data/user/0/com.example.hw2/files/gen.json"));
            if(br!= null){
                items = gson.fromJson(br, test);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addFragment(){
        fragmentManager = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() < 1){
            Fragment fragment;
            fragment = new fragment_add();
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.container,fragment,"frag_add");
            fragmentTransaction.commit();
        }

    }

    public void camFragment(){
        fragmentManager = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() < 1){
            Fragment fragment;
            fragment = new fragment_camera();
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.container,fragment,"frag_cam");
            fragmentTransaction.commit();
        }
    }

    public void extensionFragment(int index){
        fragmentManager = getSupportFragmentManager();
        if(getSupportFragmentManager().getBackStackEntryCount() < 1) {
            Bundle bundle = new Bundle();
            bundle.putInt("index",index);
            Fragment fragment;
            fragment = new fragment_ext();
            fragment.setArguments(bundle);
            fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.replace(R.id.container, fragment, "frag_ext");
            fragmentTransaction.commit();
        }
    }

    private void initRecycylerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames,mImages,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void saveToJson(List<content> test){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String json = gson.toJson(test);

        FileOutputStream outputStream;

        try{

            outputStream = openFileOutput("gen.json",MODE_PRIVATE);

            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(json);
            writer.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

}
