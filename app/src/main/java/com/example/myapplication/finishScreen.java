package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import pl.droidsonroids.gif.GifImageView;

public class finishScreen extends AppCompatActivity {
    private Button takeBreakButton;
    private Button skipBreakButton;
    private Button finishToHomeButton;
    private GifImageView dance;
    private ImageView collectOnClickCoin;
    int counter = 0 ;
    int coins ;
    private TextView counterTxt;
    String txt="";
    private TextView cliclToCollectTxt;
    Boolean clicked = false;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private MediaPlayer coinSound;

    int timerCoins =0;

    private GifImageView character;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
        takeBreakButton = (Button) findViewById(R.id.coffeBreak);
        skipBreakButton = (Button) findViewById(R.id.KeepDriving);
        finishToHomeButton = (Button) findViewById(R.id.finishToHome);
        collectOnClickCoin = (ImageView) findViewById(R.id.spiningCoin);
        counterTxt = (TextView) findViewById(R.id.coinCount);
        cliclToCollectTxt = (TextView) findViewById(R.id.clickToCollectTxt);
        coinSound = MediaPlayer.create(this, R.raw.coinsound);
        character = (GifImageView) findViewById(R.id.pandaDance);

        //deadass some redundant code here but it works so don't fucking touch it cause it fucking sucks and is very annoying
         prefs = PreferenceManager.getDefaultSharedPreferences(this);
         editor = prefs.edit();

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Integer value =(mSharedPreference.getInt("COINS_VALUE", 0));
        Long timerValue =(mSharedPreference.getLong("TIMER_VALUE", 0));
        timerCoins =(mSharedPreference.getInt("TIMERCOINS_VALUE",0));
        counterTxt.setText(txt.valueOf(value));

        setResources();



        //updates coin count by 10 when coin is clicked
        collectOnClickCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ;
                increment();
                collectOnClickCoin.setVisibility(View.GONE);
                cliclToCollectTxt.setVisibility(View.GONE);
                clicked = true;
                coinSound.start();

            }
        });


        //sends user to home on button click
        finishToHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clickyOrNoClicky();
            }
        });

    //sends user to driving screen
        skipBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked) {
                    increment();
                    Intent in = new Intent(finishScreen.this, drivingScreen.class);
                    in.putExtra("timer", timerValue);
                    startActivity(in);
                }   else {
                    Intent in = new Intent(finishScreen.this, drivingScreen.class);
                    in.putExtra("timer", timerValue);
                    startActivity(in);
                }
            }
        });

    //sends user to break screen
        takeBreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked) {
                    increment();
                    Intent in = new Intent(finishScreen.this, breakScreen.class);
                    startActivity(in);
                }   else {
                    Intent in = new Intent(finishScreen.this, breakScreen.class);
                    startActivity(in);
                }
            }
        });
    }

    //updates and stores coin value by ten
    public void increment(){
        coins = prefs.getInt("COINS_VALUE",0);
        coins = coins + timerCoins;
        editor.putInt("COINS_VALUE", coins);
        counterTxt.setText(txt.valueOf(coins));
        timerCoins = timerCoins*0;
        editor.putInt("TIMERCOINS_VALUE", timerCoins);
        editor.apply();
        editor.commit();
    }

    //honesty redundant and useless but it works and im too lazy to change it
    public void clickyOrNoClicky(){
        if (!clicked) {
            increment();
            Intent in = new Intent(finishScreen.this, homeScreen.class);
            startActivity(in);
        }   else {
                Intent in = new Intent(finishScreen.this, homeScreen.class);
                startActivity(in);
            }
    }

    private void setResources() {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean panda = (mSharedPreference.getBoolean("PANDA", false));
        //Boolean cat = (mSharedPreference.getBoolean("CAT", false));
        Boolean fox = (mSharedPreference.getBoolean("FOX", false));

        if (fox) {
            character.setImageResource(R.drawable.complete_fox);
        }
    }

    // *shrugs* *cries*
    /*public void tohome(){
        Intent in = new Intent(finishScreen.this, homeScreen.class);
        in.putExtra("t", counter);
        startActivity(in);
    } */
}