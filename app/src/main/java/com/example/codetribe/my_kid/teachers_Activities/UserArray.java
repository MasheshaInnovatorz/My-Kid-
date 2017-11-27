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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

    ImageView imageProf;

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
       // TextView userClass = (TextView) listViewItem.findViewById(R.id.textClass);
        imageProf = (ImageView) listViewItem.findViewById(R.id.adminTeacher_Pic);



        TeacherClassAcc users = userList.get(position);

        if(users.getUserProfilePic() !=null) {
            Glide.with(context).load(users.getUserProfilePic())
                    .thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageProf);
        }
        else {
            imageProf.setImageResource(R.drawable.ic_person_black_24dp);
        }

        textKdName.setText(users.getUserSurname() + " " + users.getUserName());
        //surname.setText(kids.getUserSurname());
      //  userClass.setText(users.getTeacherClassroom());

        return listViewItem;
    }

}
