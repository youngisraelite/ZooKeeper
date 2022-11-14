package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;


public class breakScreen extends AppCompatActivity {
private Button breakToDriving;
    private static  long START_TIME_IN_MILLIS = 300000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    Long timerValue;
    int breaksTaken;
    int breaksSkipped;
    private Button breaksOverLetsDriveButton;
    private TextView breaksOverLetsDriveTxt;
    private GifImageView breakScreenGif;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.break_screen);

        breakToDriving = (Button) findViewById(R.id.skipKeep);
        breaksOverLetsDriveButton = (Button) findViewById(R.id.breakOverDriveNowButton);
        breaksOverLetsDriveTxt = (TextView) findViewById(R.id.breakOverTxt);
        mTextViewCountDown = findViewById(R.id.timerBreakScreen);

        breaksOverLetsDriveButton.setVisibility(View.INVISIBLE);
        breaksOverLetsDriveTxt.setVisibility(View.INVISIBLE);

      breakScreenGif = (GifImageView) findViewById(R.id.break_screen);

        startTimer();

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
         timerValue =(mSharedPreference.getLong("TIMER_VALUE", 0));

        //sends user back to driving screen on button click
        breakToDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBreaksSkippedCounter();
                Intent in = new Intent(breakScreen.this, drivingScreen.class);
                in.putExtra("timer", timerValue);
                startActivity(in);
            }
        });

        breaksOverLetsDriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBreaksTakenCounter();
                Intent in = new Intent(breakScreen.this, drivingScreen.class);
                in.putExtra("timer", timerValue);
                startActivity(in);
            }
        });

    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                breakToDriving.setVisibility(View.INVISIBLE);
                mTextViewCountDown.setVisibility(View.INVISIBLE);
                breaksOverLetsDriveButton.setVisibility(View.VISIBLE);
                breaksOverLetsDriveTxt.setVisibility(View.VISIBLE);
                mTimerRunning = false;
            }
        }.start();

        mTimerRunning = true;
    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    protected void updateBreaksTakenCounter(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        breaksTaken = prefs.getInt("BREAKS_TAKEN_VALUE",0);
        breaksTaken++;
        editor.putInt("BREAKS_TAKEN_VALUE", breaksTaken);
        editor.apply();
        editor.commit();
    }

    protected void updateBreaksSkippedCounter(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        breaksSkipped = prefs.getInt("BREAKS_SKIPPED_VALUE",0);
        breaksSkipped++;
        editor.putInt("BREAKS_SKIPPED_VALUE", breaksSkipped);
        editor.apply();
        editor.commit();
    }

    private void setResources() {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean panda = (mSharedPreference.getBoolean("PANDA", false));
        Boolean night = (mSharedPreference.getBoolean("NIGHT", false));
        Boolean fox = (mSharedPreference.getBoolean("FOX", false));

        if (panda) {
            breakScreenGif.setImageResource(R.drawable.breakscreen);
        } else if (fox) {
            breakScreenGif.setImageResource(R.drawable.fox_break);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setResources();
    }
}