package com.example.max.jsonmaxparser.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.max.jsonmaxparser.DBHadlers.UserDBHandler;
import com.example.max.jsonmaxparser.Objects.User;
import com.example.max.jsonmaxparser.R;
import com.example.max.jsonmaxparser.Utils.MessageEvent;
import com.example.max.jsonmaxparser.Utils.Messages;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UserDBHandler db = new UserDBHandler(this);
        List <User>userList =  db.getAllUsersCoordinates();
        EventBus.getDefault().post(new MessageEvent(Messages.RESPONSE_DATA_FROM_DATA_BASE,userList));
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        switch (event.message) {
            case RESPONSE_DATA_FROM_DATA_BASE:
                setUsersCoordinates((List<User>)event.link);
                break;
        }
    }

    public void setUsersCoordinates(List<User> userList){
        LatLng sydney;
        for(User user :userList){
            sydney = new LatLng(user.getLat(), user.getLng());
        mMap.addMarker(new MarkerOptions().position(sydney).title(""+user.getId()));}
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
