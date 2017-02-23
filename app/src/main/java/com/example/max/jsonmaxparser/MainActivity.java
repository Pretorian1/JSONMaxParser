package com.example.max.jsonmaxparser;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.max.jsonmaxparser.Utils.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume(){
        super.onResume();
       // getStringJSON(Constants.URL);
        new ParseTask().execute();
   // parseJSON(getStringJSON(Constants.URL));
    }

    public String getStringJSON(String url){
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(url);
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {

            Toast.makeText(getBaseContext(),"Error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        finally {
            try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
        }
        return result;
    }

    public void parseJSON(String result){
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray jArray = jObject.getJSONArray("");
            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    Integer id = oneObject.getInt(Constants.ID);
                    String oneObjectsItem = oneObject.getString(Constants.FIRST_NAME);
                    String oneObjectsItem2 = oneObject.getString(Constants.LAST_NAME);
                    String oneObjectsItem3 = oneObject.getString(Constants.IMAGE_URL);
                    Log.i("",id+" "+ oneObject+ " " +oneObjectsItem2 + " " +oneObjectsItem3);
                } catch (JSONException e) {
                    // Oops
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL(Constants.URL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String result) {//strJson
            super.onPostExecute(result);
            try {
              //  JSONObject jObject = new JSONObject(result);
                JSONArray jArray = new JSONArray(result);
                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        Integer id = oneObject.getInt(Constants.ID);
                        String oneObjectsItem = oneObject.getString(Constants.FIRST_NAME);
                        String oneObjectsItem2 = oneObject.getString(Constants.LAST_NAME);
                        String oneObjectsItem3 = oneObject.getString(Constants.IMAGE_URL);
                        Log.i("",id+" "+ oneObjectsItem+ " " +oneObjectsItem2 + " " +oneObjectsItem3);
                    } catch (JSONException e) {
                        // Oops
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

          /*  // выводим целиком полученную json-строку
            Log.d(LOG_TAG, strJson);

            JSONObject dataJsonObj = null;
            String secondName = "";

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray friends = dataJsonObj.getJSONArray("friends");

                // 1. достаем инфо о втором друге - индекс 1
                JSONObject secondFriend = friends.getJSONObject(1);
                secondName = secondFriend.getString("name");
                Log.d(LOG_TAG, "Второе имя: " + secondName);

                // 2. перебираем и выводим контакты каждого друга
                for (int i = 0; i < friends.length(); i++) {
                    JSONObject friend = friends.getJSONObject(i);

                    JSONObject contacts = friend.getJSONObject("contacts");

                    String phone = contacts.getString("mobile");
                    String email = contacts.getString("email");
                    String skype = contacts.getString("skype");

                    Log.d(LOG_TAG, "phone: " + phone);
                    Log.d(LOG_TAG, "email: " + email);
                    Log.d(LOG_TAG, "skype: " + skype);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }
}
