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
import com.example.codetribe.my_kid.R;

import java.util.List;

/**
 * Created by CodeTribe on 9/15/2017.
 */

public class KidsArray extends ArrayAdapter<Kids> {
    private Activity context;
    private List<Kids> kidsList;


    public KidsArray(Activity context, List<Kids> kidsList) {
        super(context, R.layout.list_layout, kidsList);
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

        if (kids.getProfilepic() != "") {
            Glide.with(getContext()).load(kids.getProfilepic()).centerCrop().into(kidsImage);
        }
        textKdName.setText("Class : " + kids.kidsGrade + "\n" + "Name : " + kids.getName() + " " + kids.getSurname());
        // surname.setText(kids.getSurname());

        return listViewItem;
    }
}
