package mia.cm.atidivya.tennisscorer;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Atidivya on 24-05-2016.
 */
public class StartGame  extends AppCompatActivity {


    EditText player1Name, player2Name;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String player1 = "name1";
    public static final String player2 = "name2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        player1Name = (EditText) findViewById(R.id.player1Name);
        player2Name = (EditText) findViewById(R.id.player2Name);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(player1)) {
            player1Name.setText(sharedpreferences.getString(player1, ""));
        }

        if (sharedpreferences.contains(player2)) {
            player2Name.setText(sharedpreferences.getString(player2, ""));
        }


    }


    /*
    *  startGame method ot created to get the player's name and pass it to another activity via Intent
    * */

    public void startGame(View v){
        if(v.getId() == R.id.startGame);{

            String str1 = player1Name.getText().toString();

            String str2 = player2Name.getText().toString();
            Intent i = new Intent(StartGame.this, TennisGame.class);
            i.putExtra("Player 1 Name", str1);
            i.putExtra("Player 2 Name", str2);
            startActivity(i);









            }
        }




    public void onResume(){  // called just after onStart (state = Resumed - activity visible and active )
        super.onResume();

    }


    public void onPause(){

        super.onPause();

        String p1 = player1Name.getText().toString();
        String p2 = player2Name.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(player1, p1);
        editor.putString(player2, p2);
        editor.commit();


    }


    public void onStop(){ // called when the activity is completely hidden (state = Stop)
        super.onStop();

    }

    public void onRestart(){  // called when the activity is about to return (stated = Started - transient state)
        super.onRestart();

    }

    public void onDestroy(){ // called when the activity is destroyed (state = Destroyed)
        super.onDestroy();

    }

    public void onClear(View view) {

        player1Name = (EditText) findViewById(R.id.player1Name);
        player2Name = (EditText) findViewById(R.id.player2Name);
        player1Name.setText("");
        player2Name.setText("");

    }

    public void Get(View view) {
        player1Name = (EditText) findViewById(R.id.player1Name);
        player2Name = (EditText) findViewById(R.id.player2Name);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(player1)) {
            player1Name.setText(sharedpreferences.getString(player1, ""));
        }

        if (sharedpreferences.contains(player2)) {
            player2Name.setText(sharedpreferences.getString(player2, ""));
        }
    }

    public void onSaveInstanceState(Bundle outState){ // called when the activity is going to be stopped
        // called before onStop (before or after onPause)
        // not called when the activity is closed by the user (back button)
        super.onSaveInstanceState(outState);

    }

    public void onRestoreInstanceState(Bundle outState){ // called when the activity is recreated after being destroyed by the system
        super.onRestoreInstanceState(outState);          // called after onStart

    }


    public void aboutUs(View v) {
        if(v.getId() == R.id.aboutUsBtn);{

            Intent i = new Intent(StartGame.this, AboutUs.class);

            startActivity(i);

        }


    }

}


