package com.example.codetribe.my_kid.kids_Activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.codetribe.my_kid.R;

import java.util.List;

/**
 * Created by CodeTribe on 9/15/2017.
 */

public class KidsArray extends ArrayAdapter<Kids> {
    private Activity context;
    private List<Kids> kidsList;


    public KidsArray(Activity context, List<Kids> kidsList) {
        super(context, R.layout.admin_and_kids_list, kidsList);
        this.context = context;
        this.kidsList = kidsList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.admin_and_kids_list, null, true);

        TextView textKdName = (TextView) listViewItem.findViewById(R.id.textKidsName);

        ImageView kidsImage = (ImageView) listViewItem.findViewById(R.id.adminTeacher_Pic);


        Kids kids = kidsList.get(position);

        if (kids.getProfilePic().equals("")) {
            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(kidsImage);
            Glide.with(getContext()).load(R.drawable.ic_person_black_24dp).into(imageViewTarget);

        } else if (!kids.getProfilePic().equals("")) {

            Glide.with(context).load(kids.getProfilePic())
                    .thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(kidsImage);

        }
        textKdName.setText(kids.getSurname() + " " + kids.getName() + "\n" + "Class " + kids.getKidsGrade());

        return listViewItem;
    }
}
