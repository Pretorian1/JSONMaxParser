package com.example.max.jsonmaxparser.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.max.jsonmaxparser.Holders.UserViewHolder;
import com.example.max.jsonmaxparser.Objects.User;
import com.example.max.jsonmaxparser.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 25.02.2017.
 */

public class DataUserAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;

    public DataUserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
/*
    static class UserViewHolder {

        ImageView userImage;
        TextView userFirstName;
        TextView userLastName;
        TextView userGender;
        TextView userEmail;

        void initView(View itemView){
            userImage = (ImageView) itemView.findViewById(R.id.user_from_json_item_image);
            userFirstName = (TextView) itemView.findViewById(R.id.user_from_json_first_name);
            userLastName = (TextView) itemView.findViewById(R.id.user_from_json_last_name);
            userGender = (TextView) itemView.findViewById(R.id.user_from_json_gender);
            userEmail = (TextView) itemView.findViewById(R.id.user_from_json_email);
        }

        void setUserInformation(User user){
            if(user!=null){

            }
        }



    }*/



    @Override
    public User getItem(int i) {
        return userList.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        UserViewHolder userViewHolder;
        User user = userList.get(position);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_from_json_item, viewGroup, false);
            userViewHolder = new UserViewHolder(convertView);
          //  userViewHolder.initView(convertView);
            convertView.setTag(userViewHolder);
        } else {
            userViewHolder = (UserViewHolder) convertView.getTag();
        }

        if(user != null){
            if(user.getImageUrl()!=null)
                Glide.with(context).load(user.getImageUrl()).into(userViewHolder.userImage);
            userViewHolder.userFirstName.setText(user.getFirstName());
            userViewHolder.userLastName.setText(user.getLastName());
            userViewHolder.userGender.setText(user.getGender());
            userViewHolder.userEmail.setText(user.getEmail());
        }

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return userList.size();
    }
}
