package com.example.codetribe.my_kid.groupChat_Activities;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.codetribe.my_kid.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {

    private Activity activity;
    private List<ChatMessage> messages;
    private boolean isMine = true;
    private DatabaseReference dataRef;
    private FirebaseAuth fire;
    private String userId;

    public MessageAdapter(Activity context, int resource, List<ChatMessage> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        fire = FirebaseAuth.getInstance();
        int layoutResource = 0; // determined by view type
        ChatMessage chatMessage = getItem(position);
        int viewType = getItemViewType(position);
        String idnum = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userD = chatMessage.getUserId();




       if(chatMessage.isMine() == true) {

           layoutResource = R.layout.item_chat_right;
       }else {
           layoutResource = R.layout.item_chat_left;

       }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.msg.setText(chatMessage.getMessage());

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    private class ViewHolder {
        private TextView msg;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.msg);
        }
    }
}