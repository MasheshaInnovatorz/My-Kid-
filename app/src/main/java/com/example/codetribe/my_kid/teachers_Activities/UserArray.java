package com.example.codetribe.my_kid.teachers_Activities;

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
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by CodeTribe on 9/26/2017.
 */

public class UserArray extends ArrayAdapter<TeacherClassAcc> {

    private Activity context;
    private List<TeacherClassAcc> userList;
    private FirebaseUser user;


    public UserArray(Activity context, List<TeacherClassAcc> userList) {
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
        ImageView imageProf = (ImageView) listViewItem.findViewById(R.id.adminTeacher_Pic);



        TeacherClassAcc users = userList.get(position);

        if(users.getUserProfilePic() !=null) {
            Glide.with(getContext()).load(users.getUserProfilePic()).centerCrop().into(imageProf);
        }
        textKdName.setText(users.getUserSurname() + " " + users.getUserName());
        //surname.setText(kids.getUserSurname());

        return listViewItem;
    }

}
