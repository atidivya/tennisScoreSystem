package mia.cm.atidivya.tennisscorer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Atidivya on 20-06-2016.
 */
public class ResultsGame extends Activity {


    TextView player1Set, player2Set, player1Points, player2Points, player1Game, player2Game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_game);


        /*
        * Below, ids are set for the player's name, sets, points and games
        * */

        final TextView player1 = (TextView) findViewById(R.id.player1Name);
        final TextView player2 = (TextView) findViewById(R.id.player2Name);

         player1Set = (TextView) findViewById(R.id.player1Set);
        player1Points = (TextView) findViewById(R.id.player1Points);
        player1Game = (TextView) findViewById(R.id.player1Game);

        player2Set = (TextView) findViewById(R.id.player2Set);
        player2Points = (TextView) findViewById(R.id.player2Points);
        player2Game = (TextView) findViewById(R.id.player2Game);

        final TextView resetStats = (TextView) findViewById(R.id.resetStats);

        /*
        *  Below functions receives the values from TennisGame activity via Intent
        *  Sets the values for the player's name, sets , game, points won by the each player
        * */


        player1.setText( getIntent().getStringExtra("Player1"));
        player2.setText( getIntent().getStringExtra("Player2"));

        player1Set.setText( getIntent().getStringExtra("set1"));
        player1Points.setText( getIntent().getStringExtra("point1"));
        player1Game.setText( getIntent().getStringExtra("game1"));

        player2Set.setText( getIntent().getStringExtra("set2"));
        player2Points.setText( getIntent().getStringExtra("point2"));
        player2Game.setText( getIntent().getStringExtra("game2"));


        /*
        * Reset button is set reset the statistics of the match
        * */

        assert resetStats !=null;
        resetStats.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public  void onClick(View v){
                                            player1Set.setText("0");
                                            player1Points.setText("0");
                                            player1Game.setText("0");
                                            player2Set.setText("0");
                                            player2Points.setText("0");
                                            player2Game.setText("0");

                                        }

                                    }
        );



    }

    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);



        outState.putString("P1SET", player1Set.getText().toString());
        outState.putString("P1POINT", player1Points.getText().toString());
        outState.putString("P1GAME", player1Game.getText().toString());

        outState.putString("P2SET", player2Set.getText().toString());
        outState.putString("P2POINT", player2Points.getText().toString());
        outState.putString("P2GAME", player2Game.getText().toString());





    }

    protected void onRestoreInstanceState(Bundle savedInstanceState){

        super.onRestoreInstanceState(savedInstanceState);

        player1Set.setText(savedInstanceState.getString("P1SET"));
        player1Points.setText(savedInstanceState.getString("P1POINT"));
        player1Game.setText(savedInstanceState.getString("P1GAME"));

        player2Set.setText(savedInstanceState.getString("P2SET"));
        player2Points.setText(savedInstanceState.getString("P2POINT"));
        player2Game.setText(savedInstanceState.getString("P2GAME"));





    }



}
