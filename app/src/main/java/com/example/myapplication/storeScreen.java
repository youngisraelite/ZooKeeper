package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.view.animation.Animation;
import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;

public class storeScreen extends AppCompatActivity {
    private Button toHome;


    private TextView counterTxt2;
    int coins;
    int intCost;
    int balance;
    int value =0;
    String txt = "";


    private Switch equipFoxS;
    private Switch equipPandaS;
    private Switch equipNightS;
    private Switch equipDayS;
    private Switch equipTaxiSwitch;
    private Switch equipConvertSwitch;

    private Button unlockFox;
    private Button unlockNight;
    private Button unlockConvert;

    boolean item1 = true;
    boolean itemNight =false;
    boolean item3 = false;
    boolean itemDay = true;
    boolean itemTaxi = true;
    boolean itemConvert =false;

    TextView unlockTxt;
    String currentCost;
    String text;
    SharedPreferences sharedPreferences;

    boolean foxB = false;
    boolean NightB = false;
    Boolean convertB= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.store_screen);

        toHome = findViewById(R.id.storeToHomeButton);

        counterTxt2 = findViewById(R.id.coinCounter2);

        equipFoxS = (Switch) findViewById(R.id.equipFox);
        equipPandaS = (Switch) findViewById(R.id.equipPandaSwitch);
        equipNightS = (Switch) findViewById(R.id.equipNightSwitch);
        equipDayS = (Switch) findViewById(R.id.equipDaySwitch);
        equipTaxiSwitch = (Switch) findViewById(R.id.equipTaxiSwitch);
        equipConvertSwitch = (Switch) findViewById(R.id.equipConvertSwitch);
        unlockNight = (Button) findViewById(R.id.unlockNightBtn);
        unlockFox = (Button) findViewById(R.id.unlockFoxBtn);
        unlockConvert = (Button) findViewById(R.id.unlockConvertBtn);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Integer value =(mSharedPreference.getInt("COINS_VALUE", 0));

        counterTxt2.setText(txt.valueOf(value));
        Intent min = getIntent();
        coins = min.getIntExtra("t", 0);

       //editor.putBoolean("NIGHTBOOL",false );
       //editor.putBoolean("FOXBOOL",false );
       //editor.putBoolean("CONVERTBOOL", false);




        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(storeScreen.this, homeScreen.class);
                startActivity(in);
            }
        });

        unlockConvert.setOnClickListener(new View.OnClickListener() {
            int userBalance = (mSharedPreference.getInt("COINS_VALUE", 0));
            int c = 40;
            public void onClick(View view) {
                if(userBalance >= c) {

                    purchase(prefs, editor, c);

                    unlockConvert.setVisibility(View.GONE);
                    convertB = true;
                    editor.putBoolean("CONVERTBOOL",true );
                    editor.commit();
                    TextView unlockTxt = findViewById(R.id.purchaseComplete);

                    CountDownTimer textTimer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long l) {
                        }
                        @Override
                        public void onFinish() {
                            unlockTxt.setText("");
                        }
                    }.start();
                }
                else{
                    noPurchase(prefs, editor);
                }

            }
        });


        unlockNight.setOnClickListener(new View.OnClickListener() {
            int userBalance = (mSharedPreference.getInt("COINS_VALUE", 0));
            int c = 30;
            public void onClick(View view) {
                if(userBalance >= c) {

                    purchase(prefs, editor, c);

                    unlockNight.setVisibility(View.GONE);
                    NightB = true;
                    editor.putBoolean("NIGHTBOOL",true );
                    editor.commit();
                    TextView unlockTxt = findViewById(R.id.purchaseComplete);

                    CountDownTimer textTimer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long l) {
                        }
                        @Override
                        public void onFinish() {
                            unlockTxt.setText("");
                        }
                    }.start();
                }
                    else{
                        noPurchase(prefs, editor);
                    }
                }
        });


        unlockFox.setOnClickListener(new View.OnClickListener() {
            int userBalance = (mSharedPreference.getInt("COINS_VALUE", 0));
            int c = 20;
            public void onClick(View view) {
                if(userBalance >= intCost) {

                    purchase(prefs, editor, c);

                    unlockFox.setVisibility(View.GONE);
                     foxB = true;
                     editor.putBoolean("FOXBOOL", foxB);
                     editor.commit();
                    TextView unlockTxt = findViewById(R.id.purchaseComplete);

                    CountDownTimer textTimer = new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            unlockTxt.setText("");
                        }
                    }.start();
                }

                else{
                    noPurchase(prefs, editor);
                }

            }
        });

        equipPandaS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(equipPandaS.isChecked()){
                   item1 = true;
                  // item2 = false;
                   item3 = false;

                  // equipNightS.setChecked(false);
                   equipFoxS.setChecked(false);

                   editor.putBoolean("PANDA", item1);
                   editor.putBoolean("NIGHT", itemNight);
                   editor.putBoolean("FOX", item3);
                   editor.apply();
                   editor.commit();
               }
            }
        });

        equipNightS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(equipNightS.isChecked()){
                    //item1 = false;
                    itemNight = true;
                    itemDay = false;
                   // item3 = false;

                   // equipPandaS.setChecked(false);
                   // equipFoxS.setChecked(false);
                    equipDayS.setChecked(false);
                    //editor.putBoolean("PANDA", item1);
                    editor.putBoolean("NIGHT", itemNight);
                    editor.putBoolean("DAY", itemDay);
                    //editor.putBoolean("FOX", item3);
                    editor.apply();
                    editor.commit();
                }
            }
        });

        equipDayS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(equipDayS.isChecked()){
                    itemDay = true;
                    itemNight = false;

                    equipNightS.setChecked(false);

                    editor.putBoolean("DAY", itemDay);
                    editor.putBoolean("NIGHT", itemNight);

                    editor.apply();
                    editor.commit();
                }
            }
        });

        equipTaxiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(equipTaxiSwitch.isChecked()) {
                    itemTaxi = true;
                    itemConvert = false;

                    equipConvertSwitch.setChecked(false);

                    editor.putBoolean("TAXI", itemTaxi);
                    editor.putBoolean("CONVERT", itemConvert);
                    editor.apply();
                    editor.commit();
                }
            }
        });

        equipConvertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(equipConvertSwitch.isChecked()) {
                    itemTaxi = false;
                    itemConvert = true;

                    equipTaxiSwitch.setChecked(false);

                    editor.putBoolean("TAXI", itemTaxi);
                    editor.putBoolean("CONVERT", itemConvert);
                    editor.apply();
                    editor.commit();
                }
            }
        });

        equipFoxS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(equipFoxS.isChecked()){
                    item1 = false;
                    //item2 = false;
                    item3 = true;

                   // equipNightS.setChecked(false);
                    equipPandaS.setChecked(false);

                    editor.putBoolean("PANDA", item1);
                   // editor.putBoolean("NIGHT", item2);
                    editor.putBoolean("FOX", item3);
                    editor.apply();
                    editor.commit();
                }
            }
        });
    }
    public void purchase (SharedPreferences prefs, SharedPreferences.Editor editor, int c){

        TextView unlockTxt = findViewById(R.id.purchaseComplete);

        String text = "Purchase Completed";
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        unlockTxt.setText(ss);


        //currentCost = currentCost.replaceAll("[^\\d]", " ");
        //currentCost = currentCost.trim();


        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int userBalance = (mSharedPreference.getInt("COINS_VALUE", 0));

        userBalance -= c;
        editor.putInt("COINS_VALUE", userBalance);
        counterTxt2.setText(txt.valueOf(userBalance));
        editor.apply();
        editor.commit();
    }
    public void noPurchase (SharedPreferences prefs, SharedPreferences.Editor editor){
        TextView unlockTxt = findViewById(R.id.purchaseComplete);

        String text = "Not Enough Coins";
        SpannableString ss2 = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss2.setSpan(boldSpan, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        unlockTxt.setText(ss2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isChecked();
        isGone();

    }

    protected void isChecked(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean a = (mSharedPreference.getBoolean("PANDA", true));
        Boolean b = (mSharedPreference.getBoolean("NIGHT", false));
        Boolean c = (mSharedPreference.getBoolean("FOX", false));
        Boolean d = (mSharedPreference.getBoolean("DAY", false));
        Boolean e = (mSharedPreference.getBoolean("TAXI", false));
        Boolean f = (mSharedPreference.getBoolean("CONVERT", false));

        if(a){
            equipPandaS.setChecked(true);

            equipFoxS.setChecked(false);
        }else if(c){
            equipPandaS.setChecked(false);
            // equipNightS.setChecked(false);
            equipFoxS.setChecked(true);
        }if(b){
            //equipPandaS.setChecked(false);
            equipNightS.setChecked(true);
            equipDayS.setChecked(false);
            //equipFoxS.setChecked(false);
        }else if(d){
            equipDayS.setChecked(true);
            equipNightS.setChecked(false);
        }if (e){
            equipTaxiSwitch.setChecked(true);
            equipConvertSwitch.setChecked(false);
        }else if(f){
            equipTaxiSwitch.setChecked(false);
            equipConvertSwitch.setChecked(true);
        }
    }

    protected void isGone(){
        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean a = (mSharedPreference.getBoolean("FOXBOOL", false));
        Boolean b = (mSharedPreference.getBoolean("NIGHTBOOL", false));
        Boolean c = (mSharedPreference.getBoolean("CONVERTBOOL", false));
        if (b){
            unlockNight.setVisibility(View.GONE);
        }
        if(a){
            unlockFox.setVisibility(View.GONE);
        }if(c){
            unlockConvert.setVisibility(View.GONE);
        }

    }
}
