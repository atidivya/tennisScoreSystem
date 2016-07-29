package mia.cm.atidivya.tennisscorer;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Struct;
import java.sql.Time;

/**
 * Created by Atidivya on 24-05-2016.
 */
public class TennisGame extends Activity {

      /*
    * Counter is used to keep the scores of the game
    * CounterA keeps the score for the Player 1
    * CounterB keeps the score for the Player 2
    * Sets are declared to keep the scores of the each game played between two players
    * Set1, Set2, Set 3 keeps the scores of the game played by Player 1
    * Set 4, Set5, Set6 keeps the scores of the game played by Player 2
    * */


    public int CountA=0;
    public int CountB=0;
    public int set1 = 0;
    public int set2 = 0;
    public int set3 = 0;
    public int set4 = 0;
    public int set5 = 0;
    public int set6 = 0;
    public int hours,minutes,secs;

    TextView textView1,textView2, textView3, textView4, textView5, textView6, p1, p2, matchTime;
    Button countBtn1, countBtn2, historyBtn;

    String time;







    /*
    * To keep the scores for the number of sets, game and points won by the each player,
    * the variables player1set, player1game & player1points are declared for player1
    * the variables player2set, player2game & player2points are declared for player2
    * */

    public int player1set = 0;
    public int player1game = 0;
    public int player1points = 0;

    public int player2set = 0;
    public int player2game = 0;
    public int player2points = 0;

    /*
    * seconds variable is declared below to keep time for the scorer
    * running variable is declared to keep the functionality of starting the time
    * */

    private int seconds=0;
    private boolean running;

     /*
    * onCreate method have the functions of the buttons used by the application
    * It has TextViews for the sets
    * TextViewCount for the game score
    * countBtn for the buttons used in the application
    * */


     /*
        * Database Helper class instance is declared below
        *
        * */

    DatabaseHelper matchDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tennis_game);

        /*
        * function which runs the timer on the tennis game activity
        * */

        runTimer();

        /*
        * Gets the player's name from the starting activity and sets the text in this current activity with the help of intent
        * */

        final String player1Name = getIntent().getStringExtra("Player 1 Name");
        p1 = (TextView) findViewById(R.id.player1Name);
        p1.setText(player1Name);
        final String player2Name = getIntent().getStringExtra("Player 2 Name");
        p2 = (TextView) findViewById(R.id.player2Name);
        p2.setText(player2Name);




          /*
        * Buttons are searched by ids
        * Below are the buttons declared for Player 1
        * */

        countBtn1 = (Button) findViewById(R.id.countBtn1);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

       // final TextView textViewCount1 = (TextView) findViewById(R.id.textViewCount1);


        /*
        * Buttons declared for the Player 2
        * */

        countBtn2 = (Button) findViewById(R.id.countBtn2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);

        //final TextView textViewCount2 = (TextView) findViewById(R.id.textViewCount2);




        historyBtn = (Button) findViewById(R.id.historyBtn);



     //   final TextView textViewScore = (TextView) findViewById(R.id.textViewScore);

        /*
        * Reset button is declared below
        * */

        final Button resetBtn = (Button) findViewById(R.id.resetBtn);

            /*
            * AlertDialog function is used below to display the status of the match in Message
            * */

        final AlertDialog dialog = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        /*
        * The string value received from the application layer is converted into integer by below functions for each sets
        * */

        set1 = Integer.parseInt(textView1.getText().toString());
        set2 = Integer.parseInt(textView2.getText().toString());
        set3 = Integer.parseInt(textView3.getText().toString());
        set4 = Integer.parseInt(textView4.getText().toString());
        set5 = Integer.parseInt(textView5.getText().toString());
        set6 = Integer.parseInt(textView6.getText().toString());

      //  tie1 = Integer.parseInt(textViewCount1.getText().toString());
       // tie2 = Integer.parseInt(textViewCount2.getText().toString());




        matchDb = new DatabaseHelper(this);



        /*
        * The Reset button function is created with the help of OnClickListener
        * */

        assert resetBtn !=null;
        resetBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public  void onClick(View v){
                                            CountA = 0;
                                            CountB = 0;
                                            textView1.setText("0");
                                            textView2.setText("0");
                                            textView3.setText("0");
                                            textView4.setText("0");
                                            textView5.setText("0");
                                            textView6.setText("0");
                                            countBtn1.setText("LOVE");
                                            countBtn2.setText("LOVE");
                                         //   textViewScore.setText("0");
                                            set1 = set2 = set3 = set4 = set5 = set6 = 0;

                                            running=false;
                                            seconds=0;

                                            player1set=0;
                                            player1game=0;
                                            player1points=0;

                                            player2set=0;
                                            player2game=0;
                                            player2points=0;

                                        }

                                    }
        );


        /*
        * The method for the Player1 starts below
        * */

        assert countBtn1 != null;
        countBtn1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             CountA++;

                                           //  textViewScore.setText(CountA+"--"+textViewCount1.getText()+"--"+textViewCount2.getText());

                                             if (CountA == 1) {
                                                 countBtn1.setText("15");
                                             }
                                             else if (CountA == 2 ) {
                                                 countBtn1.setText("30");
                                             } else if (CountA == 3) {
                                                 countBtn1.setText("40");
                                             }

                /*
                * After the game is reached to the level of 40-0 for player 1, it checks all the rules of the tennis before finishing the Game
                * */

                                             else if (CountA == 4) {

                                                 /*
                                                 * Function to count the number of points in the game
                                                 * 15,30,40 are the points won in the single set of a tennis match
                                                 * */


                                                 player1points  = (CountA * (15+30+40));

                                                 //String out = String.valueOf(CountA*((set1+set2)));
                                                 //textViewScore.setText(out);


                                                 if (CountB == 3 && countBtn2.getText().toString().equals("40")  && countBtn1.getText().toString().equals("40")) {
                                                     countBtn1.setText("AD");
                                                     CountA--;
                                                 }
                                                 if (CountB == 4 && countBtn2.getText().toString().equals("40") && countBtn1.getText().toString().equals("40")) {
                                                     countBtn1.setText("AD");
                                                     CountA--;
                                                 }
                                                 if (CountA == 4 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("15")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountA--;
                                                     if(set1 != 7) {
                                                         set1 = set1 + 1;
                                                         player1game = player1game + 1;
                                                         textView1.setText("" + set1);
                                                         CountA=0;
                                                     }
                                                 }
                                                 if (CountA == 4 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("30")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountA--;
                                                     if(set1 != 7) {
                                                         set1 = set1 + 1;
                                                         player1game = player1game + 1;
                                                         textView1.setText("" + set1);
                                                         CountA=0;
                                                     }
                                                 }
                                                 else if (CountA == 4 && countBtn1.getText().toString().equals("AD") && countBtn2.getText().toString().equals("40")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountA--;
                                                     if(set1 != 7) {
                                                         set1 = set1 + 1;
                                                         player1game = player1game + 1;
                                                         textView1.setText("" + set1);
                                                         CountA=0;
                                                         CountB=0;
                                                     }
                                                 }
                                                 else if (CountA == 4 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("AD")) {
                                                     countBtn1.setText("40");
                                                     countBtn2.setText("40");
                                                     CountA--;
                                                 }
                                                 else if (CountA == 4 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("LOVE")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountA--;

                        /*
                        * The below functions checks the incrementation clause of the game for the sets
                        * */

                                                     if((set1 <= 4 && set4 != 6 && set2 == 0 && set5 == 0)
                                                             || (set1 == 5 && set4 !=7 && set2 == 0 && set5 == 0)
                                                             || (set1 == 5 && set4 == 6 && set2 == 0 && set5 == 0)
                                                              || (set1 == 6 && set4 == 6 && set2 == 0 && set5 == 0)
                                                             || (set1 == 6 && set4 == 5 && set2 == 0 && set5 == 0))

                                                     {
                                                         set1 = set1 + 1;
                                                         // tie1++;
                                                         // textViewCount1.setText(""+tie1);
                                                         textView1.setText("" + set1);

                                                         /*
                                                         * Function to count the game points of a tennis match
                                                         * */

                                                         player1game = player1game + 1;



                                                         CountA=0;
                                                         if((set1 ==6 && set4 <=4 && set2 == 0 && set5 == 0)|| (set1 ==7 && set4 <= 6 && set2 ==0  && set5 == 0))
                                                         {

                                                             /*
                                                             * Function to count the number of sets won by each player
                                                             * */

                                                             player1set = player1set + 1;
                                                             builder.setMessage(player1Name+" wins the 1st set by "+textView1.getText()+"-"+textView4.getText() )
                                                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog, int id) {}
                                                                     });
                                                             AlertDialog theAlertDialog = builder.create();
                                                             theAlertDialog.show();
                                                         }

                                                     }

                       /* else if (tie1 ==0 && tie2 ==0 && set1 ==6 && set4 ==6 && set2 ==0 && set5 == 0 ) {
                            tie1++;
                            textViewCount1.setText(""+tie1);
                        }*/

                       /*else if ( set1 == 6 && set4 == 6 && set2 == 0 && set5 == 0){
                            tie1++;
                            textViewCount1.setText("" + tie1);
                            if (CountA == 1 && tie1 !=100 ){
                                tie1++;
                                CountA = 0;
                            }








                        }
                        */

                                                     else if((set2 <= 5 && set5 != 6 && set3 == 0 && set6 == 0)
                                                             || (set2 == 5 && set5 != 7 && set3 == 0 && set6 == 0)
                                                             || (set2 == 6 && set5 == 5 && set3 == 0 && set6 == 0)
                                                             || (set2 == 6 && set5 == 6 && set3 == 0 && set6 == 0))
                                                     {
                                                         set2 = set2 + 1;
                                                         textView2.setText("" + set2);
                                                         CountA=0;
                                                         player1game = player1game + 1;

                                                         if((set1 == 6 && set4 <=4 && set2 ==6 && set5 <=4 && set3 == 0 && set6 == 0) || (set2 ==7 && set5 <= 6 && set3 ==0  && set6 == 0))
                                                         {
                                                             player1set = player1set + 1;
                                                             //builder.setTitle("Game Over");
                                                             builder.setMessage(player1Name+" won the Match by winning both the Sets by "+textView1.getText()+"-"+textView4.getText()+" , "+textView2.getText()+"-"+textView5.getText())
                                                                     .setNeutralButton("Save",new DialogInterface.OnClickListener() {
                                                                         @Override
                                                                         public void onClick(DialogInterface dialog, int id) {
                                                                              boolean isInserted = matchDb.insertData(

                                                                                      /*
                                                                                      * argument values for the insert data on save alert button
                                                                                      * */

                                                                                     p1.getText().toString(),
                                                                                     player1set,
                                                                                     player2set,
                                                                                     p2.getText().toString());

                                                                             if(isInserted = true)
                                                                                 Toast.makeText(TennisGame.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                                                             else
                                                                                 Toast.makeText(TennisGame.this, "Data Not Inserted", Toast.LENGTH_LONG).show();


                                                                         }
                                                                     })
                                                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog, int id) {
                                                                             CountA = 0;
                                                                             CountB = 0;
                                                                             textView1.setText("0");
                                                                             textView2.setText("0");
                                                                             textView3.setText("0");
                                                                             textView4.setText("0");
                                                                             textView5.setText("0");
                                                                             textView6.setText("0");
                                                                             countBtn1.setText("LOVE");
                                                                             countBtn2.setText("LOVE");
                                                                             set1 = set2 = set3 = set4 = set5 = set6 = 0;

                                                                         }
                                                                     });
                                                             AlertDialog theAlertDialog = builder.create();
                                                             theAlertDialog.show();
                                                         }
                                                         else if((set4 ==6 && set1 <=4 && set2 ==6 && set5 <=4 && set3 == 0 && set6 == 0)|| (set2 ==7 && set5 <= 6 && set3 ==0  && set6 == 0))
                                                         {
                                                             player1set = player1set + 1;
                                                             builder.setMessage(player1Name+" wins the 2nd Set by "+textView2.getText()+"-"+textView5.getText() )
                                                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog, int id) {




                                                                         }
                                                                     });
                                                             AlertDialog theAlertDialog = builder.create();
                                                             theAlertDialog.show();

                                                         }

                                                     }

                                                     else if((set3 <= 4 && set6 != 6)
                                                             || (set3 == 5 && set6 !=7 )
                                                             || (set3 == 5 && set6 == 6 )
                                                             || (set3 == 6 && set6 == 6 )
                                                             || (set3 == 6 && set6 == 5 ))

                                                     {
                                                         set3 = set3 + 1;
                                                         player1game = player1game + 1;
                                                         textView3.setText("" + set3);
                                                         CountA=0;
                                                         if((set3 ==6 && set6 <=4) || (set3 ==7 && set6 <= 6 ))
                                                         {
                                                             //builder.setTitle("Game Over");
                                                             player1set = player1set + 1;
                                                             builder.setMessage(player1Name+" won the Match by "+textView1.getText()+"-"+textView4.getText()+" , "+textView2.getText()+"-"+textView5.getText()+" , "+textView3.getText()+"-"+textView6.getText())
                                                                     .setNeutralButton("Save",new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog, int id) {

                                                                             boolean isInserted = matchDb.insertData(

                                                                                      /*
                                                                                      * argument values for the insert data on save alert button
                                                                                      * */

                                                                                     p1.getText().toString(),
                                                                                     player1set,
                                                                                     player2set,
                                                                                     p2.getText().toString());

                                                                             if(isInserted = true)
                                                                                 Toast.makeText(TennisGame.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                                                             else
                                                                                 Toast.makeText(TennisGame.this, "Data Not Inserted", Toast.LENGTH_LONG).show();


                                                                         }
                                                                     })

                                                                     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                         public void onClick(DialogInterface dialog, int id) {


                                                                             CountA = 0;
                                                                             CountB = 0;
                                                                             textView1.setText("0");
                                                                             textView2.setText("0");
                                                                             textView3.setText("0");
                                                                             textView4.setText("0");
                                                                             textView5.setText("0");
                                                                             textView6.setText("0");
                                                                             countBtn1.setText("LOVE");
                                                                             countBtn2.setText("LOVE");
                                                                             set1 = set2 = set3 = set4 = set5 = set6 = 0;

                                                                         }
                                                                     });
                                                             AlertDialog theAlertDialog = builder.create();
                                                             theAlertDialog.show();
                                                         }

                                                     }
                                                     else{
                                                         CountA = 0;
                                                     }

                                                 }
                                                 else if (CountA == 5 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("40")) {
                                                     countBtn1.setText("AD");
                                                     countBtn2.setText("40");

                                                 }
                                             }
                                         }
                                     }
        );


       /*
        * The method for the Player1 starts below
        * */


        assert countBtn2 != null;
        countBtn2.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             CountB++;

                                            // textViewScore.setText(CountB+"--"+textViewCount1.getText()+"--"+textViewCount2.getText());

                                             if (CountB == 1){
                                                 countBtn2.setText("15");
                                             }
                                             else if (CountB == 2) {
                                                 countBtn2.setText("30");
                                             }
                                             else if (CountB == 3) {
                                                 countBtn2.setText("40");
                                             }

                /*
                * After the game is reached to the level of 40-0 for player 2, it checks all the rules of the tennis before finishing the Game
                * */

                                             else if (CountB == 4 && countBtn1.getText().toString().equals("LOVE") && countBtn2.getText().toString().equals("40")) {
                                                 player2points  = (CountB * (15+30+40));
                                                 countBtn1.setText("LOVE");
                                                 countBtn2.setText("LOVE");
                                                 //CountB--;
                                                 //CountB = 0;

                    /*
                        * The below functions checks the incrementation clause of the game for the sets
                        * */
                                                 if((set4 <= 4 && set1 != 6 && set2 == 0 && set5 == 0)
                                                         || (set4 == 5 && set1 !=7 && set2 == 0 && set5 == 0)
                                                         || (set4 == 5 && set1 == 6 && set2 == 0 && set5 == 0)
                                                          || (set4 == 6 && set1 == 6 && set2 == 0 && set5 == 0)
                                                         || (set4 == 6 && set1 == 5 && set2 == 0 && set5 == 0)
                                                         )
                                                 {
                                                     set4 = set4 + 1;
                                                     player2game = player2game + 1;
                                                     // textViewCount2.setText(""+tie2);
                                                     textView4.setText("" + set4);
                                                     CountB=0;
                                                     if((set4 ==6 && set1 <=4 && set2 == 0 && set5 == 0)|| (set4 ==7 && set1 <= 6 && set2 ==0  && set5 == 0))

                                                     {
                                                         player2set = player2set + 1;
                                                         builder.setMessage(player2Name+" wins the 1st set by "+textView4.getText()+" - "+textView1.getText())
                                                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {


                                                                     }
                                                                 });
                                                         AlertDialog theAlertDialog = builder.create();
                                                         theAlertDialog.show();
                                                     }
                                                 }
                   /* else if ( set1 == 6 && set4 == 6 && set2 == 0 && set5 == 0){

                        tie2++;
                        textViewCount2.setText(""+tie2);

                    }
                    */

                                                 else if((set5 <= 5 && set2 != 6 && set3 == 0 && set6 == 0)
                                                         || (set5 == 5 && set2 != 7 && set3 == 0 && set6 == 0)
                                                         || (set5 == 6 && set2 == 5 && set3 == 0 && set6 == 0)
                                                         || (set5 == 6 && set2 == 6 && set3 == 0 && set6 == 0))
                                                 {
                                                     set5 = set5 + 1;
                                                     player2game = player2game + 1;
                                                     textView5.setText("" + set5);
                                                     CountB=0;
                                                     if((set4 ==6 && set1 <=4 &&set5 ==6 && set2 <=4 && set3 == 0 && set6 == 0)|| (set5 ==7 && set2 <= 6 && set3 ==0  && set6 == 0))

                                                     {
                                                         //builder.setTitle("Game Over");
                                                         player2set = player2set + 1;
                                                         builder.setMessage(player2Name+"won the Match by winning both the Sets"+textView4.getText()+" - "+textView1.getText()+" , "+textView5.getText()+" - "+textView2.getText())
                                                                 .setNeutralButton("Save",new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {

                                                                         boolean isInserted = matchDb.insertData(

                                                                                      /*
                                                                                      * argument values for the insert data on save alert button
                                                                                      * */

                                                                                 p1.getText().toString(),
                                                                                 player1set,
                                                                                 player2set,
                                                                                 p2.getText().toString());

                                                                         if(isInserted = true)
                                                                             Toast.makeText(TennisGame.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                                                         else
                                                                             Toast.makeText(TennisGame.this, "Data Not Inserted", Toast.LENGTH_LONG).show();


                                                                     }
                                                                 })

                                                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {



                                                                         CountA = 0;
                                                                         CountB = 0;
                                                                         textView1.setText("0");
                                                                         textView2.setText("0");
                                                                         textView3.setText("0");
                                                                         textView4.setText("0");
                                                                         textView5.setText("0");
                                                                         textView6.setText("0");
                                                                         countBtn1.setText("LOVE");
                                                                         countBtn2.setText("LOVE");
                                                                         set1 = set2 = set3 = set4 = set5 = set6 = 0;

                                                                     }
                                                                 });
                                                         AlertDialog theAlertDialog = builder.create();
                                                         theAlertDialog.show();
                                                     }
                                                     else if ((set1 ==6 && set4 <=4 && set5 ==6 && set2 <=4 && set3 == 0 && set6 == 0)|| (set5 ==7 && set2 <= 6 && set3 ==0  && set6 == 0))
                                                     {
                                                         player2set = player2set + 1;
                                                         builder.setMessage(player2Name+" wins the 2nd Set by "+textView5.getText()+" - "+textView2.getText())
                                                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {



                                                                     }
                                                                 });
                                                         AlertDialog theAlertDialog = builder.create();
                                                         theAlertDialog.show();

                                                     }
                                                 }
                                                 else if((set6 <= 4 && set3 != 6)
                                                         || (set6 == 5 && set3 !=7 )
                                                         || (set6 == 5 && set3 == 6 )
                                                         || (set6 == 6 && set3 == 6 )
                                                         || (set6 == 6 && set3 == 5 )
                                                         ) {
                                                     set6 = set6 + 1;
                                                     player2game = player2game + 1;
                                                     textView6.setText("" + set6);
                                                     CountB=0;
                                                     if((set6 ==6 && set3 <=4) || (set6 ==7 && set3 <= 6 ))
                                                     {
                                                         // builder.setTitle("Game Over");
                                                         player2set = player2set + 1;
                                                         builder.setMessage(player2Name+" won the Match by "+textView4.getText()+" - "+textView1.getText()+" , "+textView5.getText()+" - "+textView2.getText()+" , "+textView6.getText()+" - "+textView3.getText())
                                                                 .setNeutralButton("Save",new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {

                                                                         boolean isInserted = matchDb.insertData(

                                                                                      /*
                                                                                      * argument values for the insert data on save alert button
                                                                                      * */

                                                                                 p1.getText().toString(),
                                                                                 player1set,
                                                                                 player2set,
                                                                                 p2.getText().toString());

                                                                         if(isInserted = true)
                                                                             Toast.makeText(TennisGame.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                                                         else
                                                                             Toast.makeText(TennisGame.this, "Data Not Inserted", Toast.LENGTH_LONG).show();


                                                                     }
                                                                 })

                                                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {


                                                                         CountA = 0;
                                                                         CountB = 0;
                                                                         textView1.setText("0");
                                                                         textView2.setText("0");
                                                                         textView3.setText("0");
                                                                         textView4.setText("0");
                                                                         textView5.setText("0");
                                                                         textView6.setText("0");
                                                                         countBtn1.setText("LOVE");
                                                                         countBtn2.setText("LOVE");
                                                                         set1 = set2 = set3 = set4 = set5 = set6 = 0;
                                                                     }
                                                                 });
                                                         AlertDialog theAlertDialog = builder.create();
                                                         theAlertDialog.show();
                                                     }
                                                 }
                                                 else{
                                                     CountB = 0;
                                                 }
                                             }
                                             if (CountB == 4) {
                                                 player2points  = (CountB * (15+30+40));
                                                 if (CountA == 3 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("40")) {
                                                     countBtn2.setText("AD");
                                                     CountB--;
                                                 }
                                                 if (CountA == 4 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("40")) {
                                                     countBtn2.setText("AD");
                                                     CountB--;
                                                 }
                                                 if (CountB == 4 && countBtn2.getText().toString().equals("40") && countBtn1.getText().toString().equals("15")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountB--;
                                                     if((set4 <= 4 && set1 != 6) || (set2 == 0 && set5 == 0)) {
                                                         set4 = set4 + 1;
                                                         player2game = player2game + 1;
                                                         textView4.setText("" + set4);
                                                         CountB=0;
                                                     }
                                                 }
                                                 if (CountB == 4 && countBtn2.getText().toString().equals("40") && countBtn1.getText().toString().equals("30")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountB--;
                                                     if(set4 != 7) {
                                                         set4 = set4 + 1;
                                                         player2game = player2game + 1;
                                                         textView4.setText("" + set4);
                                                         CountB=0;
                                                     }
                                                 }
                                                 else if (CountB == 4 && countBtn2.getText().toString().equals("AD") && countBtn1.getText().toString().equals("40")) {
                                                     countBtn1.setText("LOVE");
                                                     countBtn2.setText("LOVE");
                                                     CountB--;
                                                     if(set4 != 7) {
                                                         set4 = set4 + 1;
                                                         player2game = player2game + 1;
                                                         textView4.setText("" + set4);
                                                         CountB=0;
                                                         CountA=0;
                                                     }
                                                 }
                                                 else if (CountB == 4 && countBtn2.getText().toString().equals("40") && countBtn1.getText().toString().equals("AD")) {
                                                     countBtn1.setText("40");
                                                     countBtn2.setText("40");
                                                     CountB--;
                                                 }
                                                 else if (CountB == 5 && countBtn1.getText().toString().equals("40") && countBtn2.getText().toString().equals("40")) {
                                                     countBtn1.setText("40");
                                                     countBtn2.setText("AD");

                                                 }
                                             }
                                         }
                                     }
        );

    }

    /*
    * Begin method is created to start the time
    * true value indicates the clock is running
    * Pause method is created to pause the time
    * false value indicates the time has paused
    * */

    public void onClickBegin(View v){

        running=true;


    }

    public void onClickPause(View v){

        running=false;
    }

    private void runTimer() {

        matchTime = (TextView) findViewById(R.id.matchTime);

        /*
        * Handler is introduced to use the funtion of delay
        * */

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

               time = String.format("%02d:%02d:%02d", hours, minutes, secs);

                matchTime.setText(time);

                if (running) {

                    seconds++;

                }
                handler.postDelayed(this, 800);

            }



        });

    }

    /*
    *  resultGame method is created to pass the values of player's name and their scores to another acitivity
    * */

    public void resultsGame(View v){

        if(v.getId() == R.id.resultBtn);{


            Intent i = new Intent(TennisGame.this, ResultsGame.class);

            i.putExtra("Player1", getIntent().getStringExtra("Player 1 Name"));
            i.putExtra("Player2", getIntent().getStringExtra("Player 2 Name"));



            i.putExtra("set1", String.valueOf(player1set));
            i.putExtra("game1", String.valueOf(player1game));
            i.putExtra("point1", String.valueOf(player1points));

            i.putExtra("set2", String.valueOf(player2set));
            i.putExtra("game2", String.valueOf(player2game));
            i.putExtra("point2", String.valueOf(player2points));




            startActivity(i);
        }


    }


    /*
    * historyBtn Object is set to call onClicklistner
    *
    * */

    public void historyGame(View v){
        if(v.getId() == R.id.historyBtn); {

            Intent i = new Intent(TennisGame.this, HistoryGame.class);
            startActivity(i);

            matchDb.getAllData();
            Cursor res = matchDb.getAllData();

            /*
            * if count is 0, the table is empty otherwise table have stored data
            * */

            if(res.getCount() == 0){
                Toast.makeText(TennisGame.this, "Table is Empty", Toast.LENGTH_LONG).show();
                return;
            }

            /*
            * String buffer is created to display the data from the database.
            * */

            StringBuffer buffer = new StringBuffer();
            Toast.makeText(TennisGame.this, "Total Number of Data Stored : "+res.getCount(), Toast.LENGTH_LONG).show();
            while(res.moveToNext()){
                buffer.append("ID:"+ res.getString(0)+ "\n");
                buffer.append("Player1Name:"+ res.getString(1)+"\n");
                buffer.append("Player1Score:"+ res.getString(2)+"\n");
                buffer.append("Player2Score:"+ res.getString(3)+"\n");
                buffer.append("Player2Name:"+ res.getString(4)+"\n\n");
            }

        }
    }

    /*
    *called when the activity is going to be stopped
    * called before onStop (before or after onPause)
    *not called when the activity is closed by the user (back button)
    * */



    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        /*outState.putInt("SET",set1);
        outState.putInt("set2",set2);
        outState.putInt("set3",set3);
        outState.putInt("set4",set4);
        outState.putInt("set5",set6);
        outState.putInt("set6",set6);*/

        outState.putString("SET1", textView1.getText().toString());
        outState.putString("SET2", textView2.getText().toString());
        outState.putString("SET3", textView3.getText().toString());
        outState.putString("SET4", textView4.getText().toString());
        outState.putString("SET5", textView5.getText().toString());
        outState.putString("SET6", textView6.getText().toString());
        outState.putString("COUNT1", countBtn1.getText().toString());
        outState.putString("COUNT2", countBtn2.getText().toString());
     //   outState.putInt("time", Integer.parseInt(String.format("%02d:%02d:%02d", hours, minutes, secs)));
        //outState.putInt("min", Integer.parseInt(String.format("%02d", minutes)));
       // outState.putInt("hr", Integer.parseInt(String.format("%02d", hours)));
       // outState.putString(time, matchTime.getText().toString());
       // outState.putInt("sec", Integer.parseInt(String.format("%02d", secs)));

//       outState.putSerializable("time", (Serializable) matchTime);



    }


    /*
     *called when the activity is recreated after being destroyed by the system super.onRestoreInstanceState(outState);
     *called after onStart
     * */



    protected void onRestoreInstanceState(Bundle savedInstanceState){

        super.onRestoreInstanceState(savedInstanceState);


   //     matchTime.setText((CharSequence) savedInstanceState.getSerializable("time"));

//      matchTime.setText(savedInstanceState.getString(time));
      //  hours =savedInstanceState.getInt("time");
       // minutes =savedInstanceState.getInt("time");
      //  secs =savedInstanceState.getInt("time");
       // minutes =savedInstanceState.getInt("min");
       // hours = savedInstanceState.getInt("hr");
        textView1.setText(savedInstanceState.getString("SET1"));
        textView2.setText(savedInstanceState.getString("SET2"));
        textView3.setText(savedInstanceState.getString("SET3"));
        textView4.setText(savedInstanceState.getString("SET4"));
        textView5.setText(savedInstanceState.getString("SET5"));
        textView6.setText(savedInstanceState.getString("SET6"));

        countBtn1.setText(savedInstanceState.getString("COUNT1"));
        countBtn2.setText(savedInstanceState.getString("COUNT2"));


        /*set1 = savedInstanceState.getInt("SET");
        set2 = savedInstanceState.getInt("set2");
        set3 = savedInstanceState.getInt("set3");
        set4 = savedInstanceState.getInt("set4");
        set5 = savedInstanceState.getInt("set5");
        set6 = savedInstanceState.getInt("set6");*/




    }




    }



