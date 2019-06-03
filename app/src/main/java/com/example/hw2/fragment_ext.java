package com.example.hw2;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class fragment_ext extends Fragment {

    private TextView bigname;
    private TextView bigphoto;
    private TextView bigloc;
    private TextView bigdesc;
    private ImageView bigimage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.extension_fragment, container, false);
            bigname = view.findViewById(R.id.big_name);
            bigphoto = view.findViewById(R.id.big_photo);
            bigloc = view.findViewById(R.id.big_loc);
            bigdesc = view.findViewById(R.id.big_desc);
            bigimage = view.findViewById(R.id.big_image);

            int id = this.getArguments().getInt("index");

            bigname.setText(MainActivity.items.get(id).name);
            bigphoto.setText(MainActivity.items.get(id).photo);
            bigloc.setText(MainActivity.items.get(id).loc);
            bigdesc.setText(MainActivity.items.get(id).text);

            String name = MainActivity.items.get(id).photo;

            File imgFile = new File("/sdcard/Android/data/com.example.hw2/files/Pictures/"+name);

            if(imgFile.exists()){

                bigimage.setImageURI(Uri.fromFile(imgFile));

            }

        return view;
    }
}
