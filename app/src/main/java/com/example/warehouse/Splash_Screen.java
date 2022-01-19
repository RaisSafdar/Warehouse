package com.example.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /** Duration of wait **/
        int SPLASH_DISPLAY_LENGTH = 1000;

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash_Screen.this, Login.class);
                Splash_Screen.this.startActivity(mainIntent);
                Splash_Screen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }}