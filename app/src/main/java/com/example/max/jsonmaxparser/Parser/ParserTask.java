package com.example.max.jsonmaxparser.Parser;

import android.os.AsyncTask;
import android.util.Log;

import com.example.max.jsonmaxparser.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Max on 25.02.2017.
 */

public class ParserTask extends AsyncTask<Void, Void, String> {
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
    }
}
