package Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tamaramamedova.weatherapp.FinalValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapters.Weather;

/**
 * Class to create database. Uses database helper to create database.
 *
 */

public class Database {
    private static WeatherDBHandler dbHelper;
    private SQLiteDatabase db;

    public Database(Context context) {
        dbHelper = new WeatherDBHandler(context);
        dbHelper.getWritableDatabase();
        this.db = dbHelper.getReadableDatabase();
    }

    public void InsertWeather(WeatherDB w){
       String first =  w.getLatlon();
       String second = w.getJSON();

        ContentValues values = new ContentValues();
        values.put(FinalValues.CITYID, first);
        values.put(FinalValues.DATA, second);
        db.insert(FinalValues.TABLE_NAME, null, values);
    }

    public int countAllStoredLocations(){
        int total = 0;
        String query = "SELECT * FROM"+FinalValues.TABLE_NAME+";";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            total = cursor.getCount();
        }
        cursor.close();
        return total;
    }
    public String getJSONfromDB(String latlon){
        String query = "SELECT DATA FROM"+FinalValues.TABLE_NAME+" WHERE CITYID="+latlon+";";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        String results;
        if(cursor.moveToFirst()){
            do{



                    int id = cursor.getInt(0);
                    System.out.println("Response number " + id);
                    String storedData = cursor.getString(cursor.getColumnIndexOrThrow(FinalValues.DATA));
                    System.out.println("Response number " + storedData);
                    results=storedData;
            }while (cursor.moveToNext());
            return results;
        }else{
            return null;
        }

    }
    public SQLiteDatabase getDbConnection(){
        return this.db;
    }
    public void closeDbConnection(){
        if(this.db != null){
            this.db.close();
        }
    }


}
