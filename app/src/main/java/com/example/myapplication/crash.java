package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class crash extends AppCompatActivity {
private Button backHome;
    TextView accCount;
    int counter;
    MediaPlayer dSound;
    int timerCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_screen);

        backHome = (Button) findViewById(R.id.crashToHomeButton);
        accCount = (TextView) findViewById(R.id.accidentCounter);

        dSound = MediaPlayer.create(this, R.raw.ds);


        //sends user back ho on button click
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(crash.this, homeScreen.class);
                startActivity(in);

            }
        });
    }

    //executes setAcc() method when ever this screen becomes visible to the user
    protected void onStart(){
        super.onStart();
        setAcc();
        coinPenalty();
        dSound.start();
    }

    protected void coinPenalty(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        timerCoins = prefs.getInt("TIMERCOINS_VALUE",0);
        timerCoins = timerCoins* 0;
        editor.putInt("TIMERCOINS_VALUE", timerCoins);
        editor.apply();
        editor.commit();
    }

    //increments the number of accidents by one and stores it to shared prefs(phone storage)
    protected void setAcc(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        counter = prefs.getInt("ACC_VALUE",0);
        counter++;
        editor.putInt("ACC_VALUE", counter);
        editor.apply();
        editor.commit();
    }
}