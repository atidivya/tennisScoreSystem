package mia.cm.atidivya.tennisscorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Atidivya on 24-07-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    String TAG = "database";

    public static final String DATABASE_NAME ="tennis.db";
    public static final String TABLE_NAME ="tennis_matchDetails";


    public static final String col_1 = "ID";
    public static final String col_2 = "Player1Name";
    public static final String col_3 = "Player1Score";
    public static final String col_4 = "Player2Score";
    public static final String col_5 = "Player2Name";



    /*
    * Database Constructor Method
    * Constructor is called to create the Database Table
    * */


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);


    }

    /*
    * Database Methods Implementation
    * */

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PLAYER1_NAME TEXT NOT NULL," +
                "PLAYER1_SCORE TEXT NOT NULL, " +
                "PLAYER2_SCORE TEXT NOT NULL, " +
                "PLAYER2_NAME TEXT NOT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS" +TABLE_NAME);
        onCreate(db);
    }

    /*
    * insertData method is created to insert data
    * */

    public boolean insertData(String Player1Name, int Player1Score, int Player2Score, String Player2Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PLAYER1_NAME", Player1Name);
        contentValues.put("PLAYER1_SCORE", String.valueOf(Player1Score));
        contentValues.put("PLAYER2_SCORE", String.valueOf(Player2Score));
        contentValues.put("PLAYER2_NAME", Player2Name);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1) {
            Log.d(TAG, "Something went wrong with data Insert");
            return false;
        }
        else {
            Log.d(TAG, "Data inserted successfully");
            return true;
        }

    }

    /*
    * getAllData method is created with Cursor class
    * res is the instance to the cursor class and raw instance will query the database
    *
    * */

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        Log.d(TAG,"databses" +res);
        return res;

    }


}
