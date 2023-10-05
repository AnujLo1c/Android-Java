package com.example.organizeit;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.Random;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaRouter;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatButton theory,practical,assignment;
        theory=findViewById(R.id.theory);
        practical=findViewById(R.id.practical);
        assignment=findViewById(R.id.assignment);
        {
            //setting-up default fragment or start-up fragment
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fl, new t_f());
            theory.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
            theory.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
            ft.commit();

            //button click
            //theory
            theory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    theory.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    practical.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    assignment.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
            theory.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
            practical.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
            assignment.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));

                    frag_load(new t_f());
                }
            });

            //Practical
            practical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Practical", Toast.LENGTH_SHORT).show();
                practical.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    theory.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    assignment.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    theory.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    practical.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    assignment.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    frag_load(new p_f());
                }
            });

            //Assignment
            assignment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assignment.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    practical.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    theory.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    theory.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    practical.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white,null)));
                    assignment.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black,null)));
                    frag_load(new a_f());
//                Toast.makeText(MainActivity.this, "Assignemt", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    void frag_load(Fragment f){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fl,f);
        ft.commit();
    }

}