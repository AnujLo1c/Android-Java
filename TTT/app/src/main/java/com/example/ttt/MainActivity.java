package com.example.ttt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button b1,b2,b3,b4,b5,b6,b7,b8,b9,r;
String[] str=new String[9];

int count=0;
boolean falg=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        r=findViewById(R.id.r);


        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_game();
            }
        });
    }
        public void Game(View v1){
            Button bc=(Button) v1;

            if(bc.getText().equals("")){
            if(falg){
                bc.setText("X");
                falg=false;
            }
            else{
                bc.setText("O");
                falg=true;
            }
            count++;
            if(count>4){
                str[0]=b1.getText().toString();
                str[1]=b2.getText().toString();
                str[2]=b3.getText().toString();
                str[3]=b4.getText().toString();
                str[4]=b5.getText().toString();
                str[5]=b6.getText().toString();
                str[6]=b7.getText().toString();
                str[7]=b8.getText().toString();
                str[8]=b9.getText().toString();

                if(str[0].equals(str[1]) && str[1].equals(str[2]) && !str[0].equals("")){
                    Toast.makeText(this, str[0]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[3].equals(str[4]) && str[4].equals(str[5]) && str[3]!=""){
                    Toast.makeText(this, str[3]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[6].equals(str[7]) && str[7].equals(str[8]) && str[6]!=""){
                    Toast.makeText(this, str[6]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[0].equals(str[3]) && str[3].equals(str[6]) && str[0]!=""){
                    Toast.makeText(this, str[0]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[1].equals(str[4]) && str[4].equals(str[7]) && str[1]!=""){
                    Toast.makeText(this, str[1]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[2].equals(str[5]) && str[5].equals(str[8]) && str[2]!=""){
                    Toast.makeText(this, str[2]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[0].equals(str[4]) && str[4].equals(str[8]) && str[0]!=""){
                    Toast.makeText(this, str[0]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(str[2].equals(str[4]) && str[4].equals(str[6]) && str[2]!=""){
                    Toast.makeText(this, str[2]+" is Winner", Toast.LENGTH_LONG).show();
                    new_game();
                }
                else if(count>=9){
                    Toast.makeText(this, "The Match is Draw", Toast.LENGTH_LONG).show();
                    new_game();
                }
            }

        }}
    private void new_game(){
        b1.setText("");
        b2.setText("");
        b3.setText("");
        b4.setText("");
        b5.setText("");
        b6.setText("");
        b7.setText("");
        b8.setText("");
        b9.setText("");
        count=0;
    }


}