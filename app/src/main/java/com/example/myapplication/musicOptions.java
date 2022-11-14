package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class musicOptions extends AppCompatActivity {
    private Switch battle;
    private Switch relax;
    private Switch bit;

    boolean bc;
    boolean rc ;
    boolean b8c ;

    private Button home;
    private GifImageView background;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_options);

        battle = (Switch) findViewById(R.id.switch1);
        relax = (Switch) findViewById(R.id.switch2);
        bit = (Switch) findViewById(R.id.switch3);

        home = (Button) findViewById(R.id.musicToHome);
        background = (GifImageView) findViewById(R.id.musicBackground);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(musicOptions.this, homeScreen.class);
                startActivity(in);
            }
        });

        battle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(battle.isChecked()){
                    bc = true;
                    rc = false;
                    b8c = false;

                    relax.setChecked(false);
                    bit.setChecked(false);

                    editor.putBoolean("BATTLE", bc);
                    editor.putBoolean("RELAX", rc);
                    editor.putBoolean("8BIT", b8c);
                    editor.apply();
                    editor.commit();
                }
            }
        });

        relax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if (relax.isChecked()) {
                       rc = true;
                       bc = false;
                       b8c = false;

                       battle.setChecked(false);
                       bit.setChecked(false);

                       editor.putBoolean("BATTLE", bc);
                       editor.putBoolean("RELAX", rc);
                       editor.putBoolean("8BIT", b8c);
                       editor.apply();
                       editor.commit();
                   }
            }
        });

        bit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   if(bit.isChecked()) {
                       b8c = true;
                       rc = false;
                       bc = false;

                       battle.setChecked(false);
                       relax.setChecked(false);

                       editor.putBoolean("BATTLE", bc);
                       editor.putBoolean("RELAX", rc);
                       editor.putBoolean("8BIT", b8c);
                       editor.apply();
                       editor.commit();
                   }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       isChecked();
       setResources();
    }

    protected void isChecked(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean a = (mSharedPreference.getBoolean("BATTLE", false));
        Boolean b = (mSharedPreference.getBoolean("RELAX", false));
        Boolean c = (mSharedPreference.getBoolean("8BIT", false));

        if(a){
            battle.setChecked(true);
            relax.setChecked(false);
            bit.setChecked(false);
        }else if(b){
            battle.setChecked(false);
            relax.setChecked(true);
            bit.setChecked(false);
        }else if(c){
            battle.setChecked(false);
            relax.setChecked(false);
            bit.setChecked(true);
        }
    }

    private void setResources() {
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean panda = (mSharedPreference.getBoolean("PANDA", false));
        Boolean night = (mSharedPreference.getBoolean("NIGHT", false));
        Boolean fox = (mSharedPreference.getBoolean("FOX", false));

        if (night) {
            background.setImageResource(R.drawable.night);
        }

    }
}
