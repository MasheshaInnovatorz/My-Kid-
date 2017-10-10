package com.example.codetribe.my_kid;

/**
 * Created by CodeTribe on 9/2/2017.
 */

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class KidsmemoListAdapter extends ArrayAdapter<MemokidsUpload_class> {
    private Activity context;
    private int resource;
    private List<MemokidsUpload_class> listImage;


    public KidsmemoListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<MemokidsUpload_class> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource =resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View v=inflater.inflate(resource,null);

        TextView kidsName = (TextView)v.findViewById(R.id.kidsNameId);
        TextView tvName=(TextView)v.findViewById(R.id.tvImageName);
        TextView uploadedName = (TextView)v.findViewById(R.id.senderid);
        ImageView img=(ImageView)v.findViewById(R.id.imgView);

        img.setScaleType(ImageView.ScaleType.CENTER_CROP);


        tvName.setText(listImage.get(position).getName());


        uploadedName.setText(listImage.get(position).getPersonUploaded());

        Glide.with(context).load(listImage.get(position).getUri()).into(img);

        return  v;
    }
}
