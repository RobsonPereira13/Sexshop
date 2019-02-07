package com.vendas.robso.vendas.activitys;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.vendas.robso.vendas.R;

public class SplashScreen extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayer = MediaPlayer.create(SplashScreen.this, R.raw.audiosplash);
        mediaPlayer.start();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN

        );
        setContentView(R.layout.activity_splash_screen);
        //Abrindo a Thread
        Thread timeThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3700); // Colocando a thread pra dormir
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        }; // fechando a thread
        timeThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
        finish();
    }
}


