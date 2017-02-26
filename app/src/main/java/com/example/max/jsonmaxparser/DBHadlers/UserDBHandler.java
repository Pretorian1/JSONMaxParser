package com.example.max.jsonmaxparser.DBHadlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.max.jsonmaxparser.Objects.User;
import com.example.max.jsonmaxparser.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 26.02.2017.
 */

public class UserDBHandler extends SQLiteOpenHelper {

    Context context;

    public UserDBHandler(Context context){
        super(context, Constants.DATA_BASE_NAME, null, Constants.DATA_BASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USERS_COORDINATES);
        String CREATE_USER_COORDINATES_TABLE = "CREATE TABLE " + Constants.TABLE_USERS_COORDINATES + "("
        + Constants.ID + " INTEGER," + Constants.LAT + " REAL,"
        + Constants.LNG + " REAL" + ")";
        db.execSQL(CREATE_USER_COORDINATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USERS_COORDINATES);
        onCreate(db);
    }

    public void addUsersCoordinates(List<User> userList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        for(User user:userList){
            values = new ContentValues();
            values.put(Constants.ID, user.getId());
            values.put(Constants.LAT, user.getLat());
            values.put(Constants.LNG, user.getLng());
            db.insert(Constants.TABLE_USERS_COORDINATES, null, values);
        }
        Toast.makeText(context, "Successful added to DATABASE", Toast.LENGTH_SHORT).show();
        db.close();
    }

    public List <User> getAllUsersCoordinates(){
        List<User> userList = new ArrayList<User>();

        String selectQuery = "SELECT " + Constants.ID + ", " + Constants.LAT + ", "
                + Constants.LNG +  " FROM " + Constants.TABLE_USERS_COORDINATES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setLat(Double.parseDouble(cursor.getString(1)));
                user.setLng(Double.parseDouble(cursor.getString(2)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        Toast.makeText(context, "Successful got from DATABASE", Toast.LENGTH_SHORT).show();
        return userList;
    }

}
