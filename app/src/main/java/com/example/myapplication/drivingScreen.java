package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;
import android.os.Handler;
import android.widget.Toast;
import java.util.Random;
import pl.droidsonroids.gif.GifImageView;



public class drivingScreen extends AppCompatActivity {
    private Button finish;
    private final String TAG = "main act";

    private MediaPlayer battleMusic;
    private MediaPlayer relaxMusic;
    private MediaPlayer bitMusic;

    private static  long START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    int fareCounter;
    int coins;


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 60000;

    private GifImageView background;
    private GifImageView car;
    private ImageView head;
    private ImageView passenger;
    Random random = new Random();
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewCountDown = findViewById(R.id.timerDriveScreen);
        background = (GifImageView) findViewById(R.id.driving_background);
        car = (GifImageView) findViewById(R.id.driving_car);
        head = (ImageView) findViewById(R.id.driving_heads);
        passenger = (ImageView) findViewById(R.id.passenger_head);

        finish = (Button) findViewById(R.id.finish);
        battleMusic = MediaPlayer.create(this, R.raw.onsight);
        relaxMusic = MediaPlayer.create(this, R.raw.guitar);
        bitMusic = MediaPlayer.create(this,R.raw.bit);


        Bundle bundle = getIntent().getExtras();
        mTimeLeftInMillis = bundle.getLong("timer");
        startTimer();

        setMusic();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();








       /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.coin)
                .setContentTitle("Oh no b")
                .setContentText("yo get back broadie")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        */

    //will be replaced with on finish timer code
        finish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                battleMusic.stop();
                relaxMusic.stop();
                bitMusic.stop();
                Intent in = new Intent(drivingScreen.this, finishScreen.class);
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
                mTimerRunning = false;
                battleMusic.stop();
                relaxMusic.stop();
                bitMusic.stop();
                updateFareCounter();
                Intent in = new Intent(drivingScreen.this, finishScreen.class);
                startActivity(in);
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
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        mTimeLeftInMillis = bundle.getLong("timer");
        setResources();
        setPassenger();

    }

    protected void updateFareCounter(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        fareCounter = prefs.getInt("FARE_VALUE",0);
        fareCounter++;
        editor.putInt("FARE_VALUE", fareCounter);
        editor.apply();
        editor.commit();
    }



    protected void setMusic(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
       Boolean battle = (mSharedPreference.getBoolean("BATTLE", false ));
        Boolean relax = (mSharedPreference.getBoolean("RELAX", false ));
        Boolean bit = (mSharedPreference.getBoolean("8BIT", false ));

        if(battle){
            battleMusic.start();
            battleMusic.isLooping();
        } else if(relax){
            relaxMusic.start();
            relaxMusic.isLooping();
        }else if(bit){
            bitMusic.start();
            bitMusic.isLooping();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
            handler.postDelayed(runnable = new Runnable() {
                public void run() {
                    handler.postDelayed(runnable, delay);
                    coins++;
                    editor.putInt("TIMERCOINS_VALUE",coins);
                    editor.apply();
                    editor.commit();
                   // Toast.makeText(drivingScreen.this, "This method is run every 60 seconds",
                           // Toast.LENGTH_SHORT).show();
                }
            }, delay);
            super.onResume();

        }


    /*when the user leaves and returns to the driving screen ie "restarts" this method executes
        sending the user to the crash screen upon return to the app*/
    public void onRestart(){
        super.onRestart();
        Intent in = new Intent(drivingScreen.this, crash.class);
        startActivity(in);
        mTimerRunning = false;

        Log.i(TAG, "on restart");
    }
    protected void onPause() {
        super.onPause();
        battleMusic.stop();
        relaxMusic.stop();
        bitMusic.stop();
        handler.removeCallbacks(runnable);
        mCountDownTimer.cancel();

    }


    private void setResources() {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean panda = (mSharedPreference.getBoolean("PANDA", false));
        Boolean night = (mSharedPreference.getBoolean("NIGHT", false));
        Boolean fox = (mSharedPreference.getBoolean("FOX", false));
        Boolean convert = (mSharedPreference.getBoolean("CONVERT", false));

        if (night) {
            background.setImageResource(R.drawable.driving_night);
        }
        if (fox) {
            head.setImageResource(R.drawable.fox_head);
        }if(convert){
            car.setImageResource(R.drawable.convert_drive);
        }
    }

    protected  void setPassenger(){
         x = random.nextInt(3) + 0;
        if (x == 0){
           passenger.setImageResource(R.drawable.cat_head);
        }else if(x == 1){
           passenger.setImageResource(R.drawable.penguin_head);
        }else if(x ==2){
           passenger.setImageResource(R.drawable.lion_head);
        }
    }
}











   /* private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string."channel_name");
            String description = getString(R.string."channel_description");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    */


