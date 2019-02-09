package Database;

/**
 * Database helper. Extends SQliteOpenHelper.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.example.tamaramamedova.weatherapp.FinalValues;

import java.util.List;

public class WeatherDBHandler extends SQLiteOpenHelper {


    public WeatherDBHandler(Context context) {
        super(context, FinalValues.DATABASE_NAME, null, FinalValues.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE" + FinalValues.TABLE_NAME + "(" + FinalValues.CITYID +
                 "VARCHAR PRIMARYKEY," + FinalValues.DATA + "TEXT)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int i,int il){
        String sql = "DROP TABLE "+FinalValues.TABLE_NAME+";";
        db.execSQL(sql);
        onCreate(db);
    }



}
