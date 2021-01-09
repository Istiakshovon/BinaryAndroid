package com.binary;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    int index;
    CharSequence charSequence;
    long delay = 200;
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if(index <= charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        //Fetching id from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getUserLogin = sharedPreferences.getString(constant.HOME_PAGE, "");



        animatText("Binary");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getUserLogin.equals("1")){
                    Intent intent = new Intent(getApplicationContext(),TextToBinaryActivity.class);
                    startActivity(intent);
                    finish();
                }else if(getUserLogin.equals("0")){
                    Intent intent = new Intent(getApplicationContext(),BinaryToTextActivity.class);
                    startActivity(intent);
                    finish();
                }else if(getUserLogin.equals("2")){
                    Intent intent = new Intent(getApplicationContext(),DecimalToBinaryActivity.class);
                    startActivity(intent);
                    finish();
                } else if (getUserLogin.equals("3")) {
                    Intent intent = new Intent(getApplicationContext(),BinaryToDecimalActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (getUserLogin.equals("")) {
                        //Creating a shared preference

                        SharedPreferences sp = getSharedPreferences(constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sp.edit();
                        //Adding values to editor
                        editor.putString(constant.HOME_PAGE, "1");

                        //Saving values to editor
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), TextToBinaryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), TextToBinaryActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        },4000);
    }

    public void animatText(CharSequence cs){
        charSequence = cs;
        index = 0;
        textView.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }

}