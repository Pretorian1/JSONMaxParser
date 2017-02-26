package com.example.max.jsonmaxparser.Activities;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.max.jsonmaxparser.Adapters.DataUserAdapter;
import com.example.max.jsonmaxparser.Objects.User;
import com.example.max.jsonmaxparser.Parser.ParserTask;
import com.example.max.jsonmaxparser.R;
import com.example.max.jsonmaxparser.Utils.Constants;
import com.example.max.jsonmaxparser.Utils.MessageEvent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    public ParserTask parserTask;
    public ListView listView;
    public DataUserAdapter dataUserAdapter;
    public FloatingActionButton floatingActionButton;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_of_users);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.go_to_google_maps);
        progressBar = (ProgressBar) findViewById(R.id.main_activity_load_bar);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Moving to Google Maps",Toast.LENGTH_SHORT).show();
            }
        });
        parserTask = new ParserTask(this);

    }

    @Subscribe
    public void onMessageEvent(MessageEvent event){
        switch (event.message){
            case RESPONSE_FROM_JSON_PARSER:
                dataUserAdapter = new DataUserAdapter(this,(ArrayList<User>) event.link);
                listView.setAdapter(dataUserAdapter);
                progressBar.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);
                break;
            case SERVER_ERROR:
                Toast.makeText(this,"Server Error",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                break;
            case ERROR:
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        parserTask.execute();
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






}
