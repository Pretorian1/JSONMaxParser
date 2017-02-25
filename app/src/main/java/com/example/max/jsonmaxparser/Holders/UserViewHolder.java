package com.example.max.jsonmaxparser.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.max.jsonmaxparser.Objects.User;
import com.example.max.jsonmaxparser.R;

/**
 * Created by Max on 25.02.2017.
 */

public class UserViewHolder {
    public ImageView userImage;
    public TextView userFirstName;
    public TextView userLastName;
    public TextView userGender;
    public TextView userEmail;

    public UserViewHolder(View itemView){
        userImage = (ImageView) itemView.findViewById(R.id.user_from_json_item_image);
        userFirstName = (TextView) itemView.findViewById(R.id.user_from_json_first_name);
        userLastName = (TextView) itemView.findViewById(R.id.user_from_json_last_name);
        userGender = (TextView) itemView.findViewById(R.id.user_from_json_gender);
        userEmail = (TextView) itemView.findViewById(R.id.user_from_json_email);
    }

}
