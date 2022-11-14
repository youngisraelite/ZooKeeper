package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifImageView;


public class statScreen extends AppCompatActivity {
    private TextView updateAccCounter;
    private Button statToHomeButton;
    String txt;
    private TextView updateFareCounter;
    private TextView updateBreaksTaken;
    private TextView updateBreaksSkipped;
    private GifImageView background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_screen);
        updateAccCounter = (TextView) findViewById(R.id.accidentCounter);
        updateFareCounter = (TextView) findViewById(R.id.fareCounterTxt);
        updateBreaksTaken = (TextView) findViewById(R.id.breaksTakerCounterTxt);
        updateBreaksSkipped = (TextView) findViewById(R.id.breaksSkippedCounterTxt);
        statToHomeButton = (Button) findViewById(R.id.statToHome);
        background = (GifImageView) findViewById(R.id.statsBackground);

        //sets AccValue equal to the value stored stored in shared preferences and
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Integer AccValue =(mSharedPreference.getInt("ACC_VALUE", 0));
        Integer fareValue = (mSharedPreference.getInt("FARE_VALUE", 0));
        Integer breaksTakenValue = (mSharedPreference.getInt("BREAKS_TAKEN_VALUE", 0));
        Integer breaksSkippedValue = (mSharedPreference.getInt("BREAKS_SKIPPED_VALUE", 0));

        //sets the textview equal to accValue ie updates number of accidents value
        updateAccCounter.setText(txt.valueOf(AccValue));
        updateFareCounter.setText(txt.valueOf(fareValue));
        updateBreaksTaken.setText(txt.valueOf(breaksTakenValue));
        updateBreaksSkipped.setText(txt.valueOf(breaksSkippedValue));



        //send user back home on button click
        statToHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(statScreen.this, homeScreen.class);
                startActivity(in);
            }
        });

    }

    private void setResources() {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean panda = (mSharedPreference.getBoolean("PANDA", false));
        Boolean night = (mSharedPreference.getBoolean("NIGHT", false));
        Boolean fox = (mSharedPreference.getBoolean("FOX", false));

        if (night) {
            background.setBackgroundResource(R.drawable.night);
        }
        if (panda) {

        } else if (fox) {


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setResources();
    }
}
