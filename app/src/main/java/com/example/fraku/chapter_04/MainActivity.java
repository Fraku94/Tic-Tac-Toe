package com.example.fraku.chapter_04;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    boolean GameIsActive = true;
    // 0-Kolko, 1-Krzyzyk
    int ActivePlayer;

    // 2 oznacza nie zagrany ruch
    int[] GameState = {2,2,2,2,2,2,2,2,2};

    // Mozłiwe wygrane
    int[][] WinningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{2,4,6},{0,4,8}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  void dropIn(View view){

        ImageView counter = (ImageView) view;

        int TappedCounter = Integer.parseInt(counter.getTag().toString());

        TextView PlayerTv = (TextView)findViewById(R.id.Player);

        if (GameState[TappedCounter] ==2) {

            GameState[TappedCounter] = ActivePlayer;

            counter.setTranslationZ(-1000f);

            if (ActivePlayer == 0) {
                counter.setImageResource(R.drawable.kolko);
                ActivePlayer = 1;
                PlayerTv.setText("Krzyżyk");
            } else {
                counter.setImageResource(R.drawable.krzyzyk);
                ActivePlayer = 0;
                PlayerTv.setText("Kółko");
            }

            counter.animate().translationZBy(1000f).rotation(360).setDuration(100);

            for (int[] WinPosition : WinningPositions)
            {
                if (GameState[WinPosition[0]] == GameState[WinPosition[1]] &&
                        GameState[WinPosition[1]] == GameState[WinPosition[2]] &&
                        GameState[WinPosition[0]] !=2)
                {
                    GameIsActive = false;

                        String Winner= " Player 2 !!";

                    if (GameState[WinPosition[0]] == 0)
                     {
                        Winner= " Player 1 !!";
                     }


                    TextView WinnerPlayer = (TextView)findViewById(R.id.WinnerPlayer);
                    WinnerPlayer.setText(Winner);
                    LinearLayout WinnerLayout = (LinearLayout)findViewById(R.id.WinnerLayout);

                    WinnerLayout.setVisibility(View.VISIBLE);



                }else
                    {

                    boolean GameIsOver = true;

                    for(int CounterState : GameState)
                    {
                        if (CounterState == 2)
                        {
                            GameIsOver = false;
                        }
                    }
                    if (GameIsOver)
                    {
                        LinearLayout MatchLayout = (LinearLayout)findViewById(R.id.MatchLayout);

                        MatchLayout.setVisibility(View.VISIBLE);
                    }
                    }
            }
        }
    }
    public  void PlayAgain(View view) {

        LinearLayout WinnerLayout = (LinearLayout)findViewById(R.id.WinnerLayout);
        WinnerLayout.setVisibility(View.INVISIBLE);

        LinearLayout MatchLayout = (LinearLayout)findViewById(R.id.MatchLayout);
        MatchLayout.setVisibility(View.INVISIBLE);

        GameIsActive = true;
        ActivePlayer = 0;

        for (int i=0; i < GameState.length; i++){
            GameState[i] = 2;
        }
        GridLayout GridPlansza = (GridLayout)findViewById(R.id.GridPlansza);

        for(int i =0;i<GridPlansza.getChildCount();i++)
        {
            ((ImageView) GridPlansza.getChildAt(i)).setImageResource(0);
        }
    }
    public  void EndAplication(View view) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
