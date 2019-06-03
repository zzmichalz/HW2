package com.example.hw2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mImageName = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mImageName, ArrayList<String> mImages, Context mContext) {
        this.mImageName = mImageName;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_items,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        viewHolder.imageName.setText(mImageName.get(position));

        String name = mImages.get(position);

        File imgFile = new File("/sdcard/Android/data/com.example.hw2/files/Pictures/"+name);

        if(imgFile.exists()){

            viewHolder.image.setImageURI(Uri.fromFile(imgFile));

        }



        viewHolder.del.setOnClickListener(new View.OnClickListener() {
        @Override
                public void onClick(View view){
                /*Intent intent = new Intent(mContext,MainActivity.class);
                intent.putExtra("pos",position);
                ((MainActivity)mContext).finish();
                mContext.startActivity(intent);*/
            MainActivity.items.clear();
            ((MainActivity)mContext).ParseJson();
            MainActivity.items.remove(position);
            ((MainActivity)mContext).setNames();
            ((MainActivity)mContext).saveToJson(MainActivity.items);
            notifyDataSetChanged();

            }
        });

        viewHolder.itemslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).extensionFragment(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        RelativeLayout itemslayout;
        Button del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            del = itemView.findViewById(R.id.del);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            itemslayout = itemView.findViewById(R.id.items_layout);

        }

    }
}
