package com.example.hw2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class fragment_camera extends Fragment {

    private Button add;
    private EditText input_name = null;
    private EditText input_loc = null;
    private EditText input_desc = null;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.camera_fragment, container, false);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(getActivity(),getString(R.string.myFileprovider),photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }

        add = view.findViewById(R.id.button_cam);
        input_name = view.findViewById(R.id.textView3_cam);
        input_loc = view.findViewById(R.id.textView2_cam);
        input_desc = view.findViewById(R.id.textView_cam);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(input_name.getEditableText().toString().equals("")  || input_loc.getEditableText().toString().equals("") || input_desc.getEditableText().toString().equals("")) {
                    Toast.makeText(getContext(),"Place all information",Toast.LENGTH_LONG).show();
                }
                else{

                    MainActivity.items.clear();
                    ((MainActivity) getActivity()).ParseJson();
                    MainActivity.items.add(new content(input_name.getEditableText().toString(), mCurrentPhoto, input_loc.getEditableText().toString(), input_desc.getEditableText().toString()));
                    ((MainActivity) getActivity()).setNames();
                    ((MainActivity) getActivity()).saveToJson(MainActivity.items);
                }

            }
        });

        return view;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "img_"+timeStamp;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(filename,".png",storageDir);

        mCurrentPhoto = image.getName();
        return image;
    }
}
