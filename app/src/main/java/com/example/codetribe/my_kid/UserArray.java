package com.example.codetribe.my_kid;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by CodeTribe on 9/26/2017.
 */

public class UserArray extends ArrayAdapter<UserProfile> {

    private Activity context;
    private List<UserProfile> userList;
    private FirebaseUser user;


    public UserArray(Activity context, List<UserProfile> userList) {
        super(context, R.layout.list_layout, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();


        View listViewItem = inflater.inflate(R.layout.admin_and_kids_list, null, true);
        TextView textKdName = (TextView) listViewItem.findViewById(R.id.textKidsName);
        ImageView imageProf = (ImageView) listViewItem.findViewById(R.id.image);



        UserProfile kids = userList.get(position);


        textKdName.setText(kids.getUserSurname() + " " + kids.getUserName());
        //surname.setText(kids.getUserSurname());

        return listViewItem;
    }

}
