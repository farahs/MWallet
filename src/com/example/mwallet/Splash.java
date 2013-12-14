package com.example.mwallet;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class Splash extends Activity {
	ImageView img;
    public void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        img = (ImageView)findViewById(R.id.home);
        img.setBackgroundResource(R.drawable.splash_gif);
        img.post(new Runnable() {
            public void run() {
                AnimationDrawable progressAnimation = (AnimationDrawable) img.getBackground();
                progressAnimation.start();
            }
        });
        new Handler().postDelayed(new Runnable() {
                public void run() {
                    
                        startActivity(new Intent(Splash.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                        finish();
                        
                }
        }, secondsDelayed * 2100);
    }
}
