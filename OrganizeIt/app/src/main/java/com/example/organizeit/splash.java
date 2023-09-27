package com.example.organizeit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Splash Screen
        LottieAnimationView l=findViewById(R.id.lotti);
        Random random=new Random();
        int a=random.nextInt(4);
        if((a)==0){
            l.setAnimation(R.raw.lotti1);
        }else if((a)==1)
            l.setAnimation(R.raw.lotti2);
        else{
            l.setAnimation(R.raw.lotti3);
        }


        Intent i=new Intent(splash.this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        },2000);

    }
}