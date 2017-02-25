package com.example.max.jsonmaxparser.Parser;

import android.os.AsyncTask;
import android.util.Log;

import com.example.max.jsonmaxparser.Objects.User;
import com.example.max.jsonmaxparser.Utils.Constants;
import com.example.max.jsonmaxparser.Utils.MessageEvent;
import com.example.max.jsonmaxparser.Utils.Messages;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 25.02.2017.
 */

public class ParserTask extends AsyncTask<Void, Void, String> {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    List<User> userList;
    User user;

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
            EventBus.getDefault().post(new MessageEvent(Messages.SERVER_ERROR, null));
            e.printStackTrace();
        }
        return resultJson;
    }

    @Override
    protected void onPostExecute(String result) {//strJson
        super.onPostExecute(result);
        userList = new ArrayList<User>();
        try {
            //  JSONObject jObject = new JSONObject(result);
            JSONArray jArray = new JSONArray(result);
            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    // Pulling items from the array
                    user = new User();
                    user.setId(oneObject.getInt(Constants.ID));
                    user.setFirstName(oneObject.getString(Constants.FIRST_NAME));
                    user.setLastName(oneObject.getString(Constants.LAST_NAME));
                    user.setEmail(oneObject.getString(Constants.EMAIL));
                    user.setGender(oneObject.getString(Constants.GENDER));
                    user.setIpAddress(oneObject.getString(Constants.IP_ADDRESS));
                    user.setImageUrl(oneObject.getString(Constants.IMAGE_URL));
                    user.setLat(oneObject.getDouble(Constants.LAT));
                    user.setLng(oneObject.getDouble(Constants.LNG));
                    /*Integer id = oneObject.getInt(Constants.ID);
                    String oneObjectsItem = oneObject.getString(Constants.FIRST_NAME);
                    String oneObjectsItem2 = oneObject.getString(Constants.LAST_NAME);
                    String oneObjectsItem3 = oneObject.getString(Constants.IMAGE_URL);
                    Log.i("",id+" "+ oneObjectsItem+ " " +oneObjectsItem2 + " " +oneObjectsItem3);*/
                    userList.add(user);
                } catch (JSONException e) {
                    EventBus.getDefault().post(new MessageEvent(Messages.ERROR,null));
                }
            }
            EventBus.getDefault().post(new MessageEvent(Messages.RESPONSE_FROM_JSON_PARSER,userList));

        } catch (JSONException e) {
            EventBus.getDefault().post(new MessageEvent(Messages.SERVER_ERROR, null));
            e.printStackTrace();
        }
    }
}
