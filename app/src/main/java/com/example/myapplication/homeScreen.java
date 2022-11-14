package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class homeScreen extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener {
    private TextView counterTxt;
    int coins;
    String txt = "";
    private TextView coinTxt;
    private Button setTimer;
    private EditText TimerTxt;

    long time = 25 * 60000;
    String txtValue;

    boolean clicked;
    GifImageView character;
    GifImageView background;
    private ImageView vehicle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);


        counterTxt = (TextView) findViewById(R.id.coinCounter);
        coinTxt = (TextView) findViewById(R.id.coinTxt);


        setTimer = (Button) findViewById(R.id.setTimerButton);
        TimerTxt = (EditText) findViewById(R.id.setTimerTxt);

         character = (GifImageView) findViewById(R.id.panda);
         background = (GifImageView) findViewById(R.id.panda_main_background);
         vehicle = (ImageView) findViewById(R.id.taxi_main_back);


//idk wft this shit is; update: nvm it fucking works gets value from shared preference
//ima b honest theres prolly sum redundant code here but fuck it it works
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Integer value =(mSharedPreference.getInt("COINS_VALUE", 0));
         clicked = (mSharedPreference.getBoolean("CLICK", false ));


       counterTxt.setText(txt.valueOf(value));
        Intent min = getIntent();
        coins = min.getIntExtra("t", 0);





        setTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = TimerTxt.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(homeScreen.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(homeScreen.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime();
                Intent in = new Intent(homeScreen.this, drivingScreen.class);
                in.putExtra("timer", time);
                startActivity(in);
                //TimerTxt.setText("Timer Set!");


            }
        });



    }



    protected void onStart(){
        super.onStart();
        setResources();

    }


    public void setTime(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        time = prefs.getLong("TIMER_VALUE",0);
        txtValue = TimerTxt.getText().toString();
        time = Long.parseLong(txtValue) * 60000;
        editor.putLong("TIMER_VALUE", time);
        editor.apply();
        editor.commit();
    }


    public void showMenu(View view){
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.popup_menu);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.store:
                Intent in = new Intent(homeScreen.this, storeScreen.class);
                startActivity(in);
                return true;
            case R.id.stats:
                 in = new Intent(homeScreen.this, statScreen.class);
                startActivity(in);
                return true;

            case R.id.music:
                in = new Intent(homeScreen.this, musicOptions.class);
                startActivity(in);
                return true;

            case R.id.login:
                in = new Intent(homeScreen.this, loggingIn.class);
                startActivity(in);
                return true;
        }
        return false;
    }

    private void setResources(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean panda = (mSharedPreference.getBoolean("PANDA", false ));
        Boolean night = (mSharedPreference.getBoolean("NIGHT", false ));
        Boolean fox = (mSharedPreference.getBoolean("FOX", false ));
        Boolean convert = (mSharedPreference.getBoolean("CONVERT", false));

        if(panda){
            character.setImageResource(R.drawable.panda_home_screen);

        }else if(fox){
           character.setImageResource(R.drawable.home_screen_fox);

        }if(night){
            background.setImageResource(R.drawable.night);

        }if(convert){
            vehicle.setImageResource(R.drawable.convertable_main);
        }
    }
}
