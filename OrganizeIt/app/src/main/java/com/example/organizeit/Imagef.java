package com.example.organizeit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Imagef extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagef);
        ImageView ifv=findViewById(R.id.imagef);
        ImageFilterButton ifb=findViewById(R.id.backarrow);

        ifb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imagef.super.onBackPressed();
            }
        });
        Bundle b = getIntent().getExtras();
try {
    ifv.setImageURI(Uri.parse(b.getString("uri")));
//        ImageFilterButton ifb=findViewById(R.id.backarrow);
}catch(NullPointerException n){
    Log.d("System.out", "onCreate: "+Uri.parse(b.getString("uri")));
}
    }
}