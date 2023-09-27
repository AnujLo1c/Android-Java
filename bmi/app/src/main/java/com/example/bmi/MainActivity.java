package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText w,hf,hi;
        TextView output;
        Button b;
        LinearLayout ll;

        w=findViewById(R.id.weigth);
        hf=findViewById(R.id.height);
        hi=findViewById(R.id.heighti);
        b=findViewById(R.id.button);
        output=findViewById(R.id.output);
        ll=findViewById(R.id.ll);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             int we=Integer.parseInt(w.getText().toString()) ;
             int f=Integer.parseInt(hf.getText().toString()) ;
             int i=Integer.parseInt(hi.getText().toString()) ;
             int inches=f*12+i;
             double cm=inches*2.53;
             cm/=100;
             cm=we/(cm*cm);
             if(cm>25){
                 ll.setBackgroundResource(R.color.ared);
                 output.setText("You are Overweight");
             }
             else if(cm<18){
                 ll.setBackgroundResource(R.color.ablue);
                 output.setText("You are Underweight");
//                 ll.setBackgroundColor(Color.BLUE);
                }
             else{ output.setText("You are Fit");
                 ll.setBackgroundResource(R.color.agreen);}
//             ll.setBackgroundColor(Color.GREEN);
            }
        });
}}