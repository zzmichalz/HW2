package com.example.hw2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class fragment_add extends Fragment {

    private Button add;
    private EditText input_name = null;
    private EditText input_loc = null;
    private EditText input_desc = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_fragment, container, false);

        add = view.findViewById(R.id.button);
        input_name = view.findViewById(R.id.textView3);
        input_loc = view.findViewById(R.id.textView2);
        input_desc = view.findViewById(R.id.textView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(input_name.getEditableText().toString().equals("")  || input_loc.getEditableText().toString().equals("") || input_desc.getEditableText().toString().equals("")) {
                    Toast.makeText(getContext(),"Place all information",Toast.LENGTH_LONG).show();
                }
                else{

                    String[] images = {"img1","img2","img3","img4","img5"};
                    Random rand = new Random();
                    int index = rand.nextInt(5);

                    MainActivity.items.clear();
                    ((MainActivity) getActivity()).ParseJson();
                    MainActivity.items.add(new content(input_name.getEditableText().toString(), images[index]+".png", input_loc.getEditableText().toString(), input_desc.getEditableText().toString()));
                    ((MainActivity) getActivity()).setNames();
                    ((MainActivity) getActivity()).saveToJson(MainActivity.items);
                }

            }
        });

        return view;
    }
}
