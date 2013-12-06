package com.example.mwallet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;

public class Splash extends Activity {
	private Thread mSplashThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		final Splash sPlash = this;   
        
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(1500);
                    }
                }
                catch(InterruptedException ex){                    
                }

                finish();
                
                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlash, LoginActivity.class);
                startActivity(intent);
                finish();                    
            }
        };
        
        mSplashThread.start();     
	}

	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
	}

}
