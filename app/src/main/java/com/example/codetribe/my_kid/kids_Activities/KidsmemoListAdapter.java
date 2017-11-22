package com.example.codetribe.my_kid.kids_Activities;

/**
 * Created by CodeTribe on 9/2/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.codetribe.my_kid.R;

import java.util.List;

/**
 * Created by Belal on 2/23/2017.
 */

public class KidsmemoListAdapter extends RecyclerView.Adapter<KidsmemoListAdapter.ViewHolder> {

    private Context context;
    private List<MemokidsUpload_class> uploads;

    public KidsmemoListAdapter(Context context, List<MemokidsUpload_class> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kids_memo_lists_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MemokidsUpload_class upload = uploads.get(position);

        holder.textviewSenderid.setText("Uploaded by: " + upload.getPersonUploaded());
        holder.textViewTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                upload.getTimeUploaded()));

        holder.textViewName.setText(upload.getName());
        Glide.with(context).load(upload.getUri())
                                    .thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textviewSenderid, textViewName,textViewTime;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

           textViewName = (TextView) itemView.findViewById(R.id.tvImageName);
           textviewSenderid = (TextView) itemView.findViewById(R.id.senderid);
           textViewTime = (TextView) itemView.findViewById(R.id.timeupload);


            imageView = (ImageView) itemView.findViewById(R.id.imgView);



        }
    }
}