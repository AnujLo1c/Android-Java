package com.example.organizeit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
//import android.view.View;
import com.github.barteksc.pdfviewer.PDFView;
public class PdfViewer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        ImageFilterButton ifb=findViewById(R.id.backar);
        PDFView pdfv=findViewById(R.id.pdfview);
        ifb.setOnClickListener(view -> PdfViewer.super.onBackPressed());
        Bundle b = getIntent().getExtras();
        try {
            System.out.println(b.getString("puri"));
            System.out.println(Uri.parse(b.getString("puri")));
            pdfv.fromUri(Uri.parse(b.getString("puri"))).load();
        }catch(NullPointerException n){
            Log.d("System.out", "onCreate: "+Uri.parse(b.getString("uri")));
        }
    }
}