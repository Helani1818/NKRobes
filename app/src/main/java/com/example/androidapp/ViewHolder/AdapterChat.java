package com.example.androidapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class AdapterChat extends BaseAdapter {

    private Context context;
    private List<Message> messages;

    public AdapterChat() {
    }

    public AdapterChat(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(List<Message> message) {
        this.messages = message;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        }

        TextView messageContent = convertView.findViewById(R.id.message_content);
        TextView timeSend = convertView.findViewById(R.id.timesend);
        ImageView imageSent = convertView.findViewById(R.id.image_sent);

        TextView fromMTextView = convertView.findViewById(R.id.from_message_content);
        TextView timeFrom = convertView.findViewById(R.id.timefrom);
        ImageView fromImage = convertView.findViewById(R.id.from_image);



        View layoutView = convertView.findViewById(R.id.view_layout);

        Message message = messages.get(position);

        System.out.println(message.toString());


        if(message.from){
            if (message.messageType.equalsIgnoreCase("IMAGE")) {
                fromMTextView.setVisibility(View.GONE);
                fromImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(message.image).into(fromImage);
//                layoutView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

            }else {
                fromImage.setVisibility(View.GONE);
                fromMTextView.setVisibility(View.VISIBLE);
                timeFrom.setVisibility(View.VISIBLE);
                fromMTextView.setText(message.message);

                timeFrom.setText(message.messageTime);
            }

            messageContent.setVisibility(View.GONE);
            imageSent.setVisibility(View.GONE);
            timeSend.setVisibility(View.GONE);
        }else {
//            layoutView.setBackgroundColor(ContextCompat.getColor(context, R.color.sentColor));
            if (message.messageType.equalsIgnoreCase("IMAGE")) {
                messageContent.setVisibility(View.GONE);
                imageSent.setVisibility(View.VISIBLE);
                Glide.with(context).load(message.image).into(imageSent);
//                layoutView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

            } else {
                imageSent.setVisibility(View.GONE);
                messageContent.setVisibility(View.VISIBLE);
                timeSend.setVisibility(View.VISIBLE);
                messageContent.setText(message.message);
                timeSend.setText(message.messageTime);
            }

            fromMTextView.setVisibility(View.GONE);
            fromImage.setVisibility(View.GONE);
            timeFrom.setVisibility(View.GONE);

        }



        return convertView;
    }

}