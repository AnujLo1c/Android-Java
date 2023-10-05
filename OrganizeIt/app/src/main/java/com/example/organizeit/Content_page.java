package com.example.organizeit;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
//import android.view.View;
//import android.os.Handler;
//import android.util.Log;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.ProgressBar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

//import java.io.File;
//import com.example.organizeit.databinding.VerticalItemLayoutBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.text.DateFormat;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.ParseException;
//import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Date;
//import java.util.SimpleTimeZone;

public class Content_page extends AppCompatActivity implements bottomDrawarFragment.OnDismissListener {
    // ImageView image;
    ImageFilterButton back, add;
    TextView name;
    Bundle b;
    File datefolder, dateDetails, mainf;
    SimpleDateFormat dateFormat, dateFormat2;
    FileInputStream fis;
    MyListAdapter listad;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_page);
        // adapterRv.notifyDataSetChanged();
        back = findViewById(R.id.backarrow);
        add = findViewById(R.id.add_content);
        name = findViewById(R.id.course_name);

        // bundle passing
        b = getIntent().getExtras();
        name.setText(b.getString("name"));

        // dynamic creation

        LinearLayoutCompat ll = findViewById(R.id.listVisible);
        ll.setPadding(0, 40, 0, 20);
        ll.setOrientation(LinearLayoutCompat.VERTICAL);

        // //store date
        Date date = new Date();
        date.getTime();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        //// String prevdate= dateFormat.format(date);
        mainf = new File(getFilesDir(), "Theory");
        if (!mainf.exists()) {
            mainf.mkdir();
        }
        datefolder = new File(mainf, "Dates");
        if (!datefolder.exists()) {
            datefolder.mkdir();
        }
        dateDetails = new File(datefolder, b.getString("name") + "_dates.txt");
        try {
            if (!dateDetails.exists()) {
                dateDetails.createNewFile();
                write(dateFormat2.format(date), dateDetails);
            }
            String existingDates = read(dateDetails);

            String currentDateFormatted = dateFormat2.format(date);
            if (!existingDates.contains(currentDateFormatted)) {
                // Append the current date to the existing dates
                // existingDates += currentDateFormatted + "#";

                // Write the updated dates back to the file
                write(currentDateFormatted, dateDetails);
            }

        } catch (IOException e) {
            Log.d("System.out", "onCreate: date fiel failed");
            // Toast.makeText(this, "Date file creation failed", Toast.LENGTH_SHORT).show();
        }


        // datewise dy-cre
        try {
            subframes_create(ll);
        } catch (IOException e) {
            Toast.makeText(this, "Date fetching error", Toast.LENGTH_SHORT).show();
        } catch (ParseException p) {
            Toast.makeText(this, "String parsing failed", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(v -> Content_page.super.onBackPressed());
        add.setOnClickListener(v -> {
            bottomDrawarFragment b1 = new bottomDrawarFragment();
            b1.setArguments(b);

            b1.show(getSupportFragmentManager(), b1.getTag());
        });
    }

    private void subframes_create(LinearLayoutCompat ll) throws IOException, ParseException {
        // String s = read(dateDetails);

        String s = read(dateDetails);
        String[] dates = s.split("#");

        for (String date : dates) {
            // Date Header TextView
            TextView textView = new TextView(this);
            float factor = getResources().getDisplayMetrics().density;
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(date);
            textView.setTextSize(16);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(760, 0, 0, 0);
            ll.addView(textView);

            // Divider View
            View dividerView = new View(this);
            dividerView
                    .setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) factor * 2));
            dividerView.setBackground(getDrawable(R.drawable.b_color));
            ll.addView(dividerView);

            // RecyclerView
            // RecyclerView recyclerView = new RecyclerView(this);
            // recyclerView.setLayoutParams(new
            // ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            // ViewGroup.LayoutParams.WRAP_CONTENT));
            //// LinearLayoutManager layoutManager = new LinearLayoutManager(this,
            // LinearLayoutManager.VERTICAL, false);
            // recyclerView.setLayoutManager(null);

            // Set up the adapter for the RecyclerView
            // files_Fetch(dateDetails,date);


            // imageItems.add( "anujaa.pdf");
            // imageItems.add( "anujaa.pdf");
            // imageItems.add( "anujaa.pdf");
            // imageItems.add( "anujaa.pdf");
            // imageItems.add( "anujaa.pdf");
            // imageItems.add( "anujaa.pdf");
            // pdfItems.add("nanuj lwoasnhsid.jpg");
            // pdfItems.add("n1.jpg");
            // pdfItems.add("n2.jpg");

//            listad.notifyDataSetChanged();
            // System.out.println(imageItems.size());
//            System.out.println(pdfItems.size());

            //files fetch
            File ff = new File(mainf, "Theory_" + b.getString("name"));
            File f = new File(ff, date);
            ArrayList<URI> imageItems = new ArrayList<java.net.URI>();
            ArrayList<String> pdfItems = new ArrayList<>();
            ArrayList<String> pdfItemsuris = new ArrayList<>();
            if (f.exists() && f.isDirectory()) {
                // List all files in the directory
                File[] files = f.listFiles();

                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
//         System.out.println(fileName);
                        if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg")) {
                            imageItems.add(file.toURI());
                        } else if (fileName.endsWith(".pdf")) {
                            pdfItems.add(fileName);
                            pdfItemsuris.add(String.valueOf(file.toURI()));

                        }
                    }
                }
            } else {
                System.out.println("The directory does not exist.");
            }
            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            gridLayout.setColumnCount(5);

            int rowCount = (imageItems.size() % 4 == 0) ? imageItems.size() / 4 : (imageItems.size() / 4) + 1;
            gridLayout.setRowCount(rowCount);
            gridLayout.setPadding(0, 10, 0, 10);
//            ImageView[] imageView=new ImageView[imageItems.size()];

            for (int i = 0; i < imageItems.size(); i++) {
//            if(!imageItems.isEmpty()) {
                Uri uri = Uri.parse(imageItems.get(i).toString());

                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
                imageView.setPadding(0, 0, 0, 10);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                // Set the URI as a tag for the ImageView
                imageView.setTag(uri);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Retrieve the URI from the ImageView's tag
                        Uri clickedUri = (Uri) v.getTag();
                        Intent i=new Intent(getApplicationContext(), Imagef.class);
i.putExtra("uri",clickedUri.toString());
startActivity(i);

                    }
                });

                imageView.setImageURI(uri);
                gridLayout.addView(imageView);
            }
//            }
            NonScrollListView listView = new NonScrollListView(this);
            listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            for(String str:pdfItems){
                System.out.println(str);
            }
           listad = new MyListAdapter(this, pdfItems);
            listView.setDivider(null);
            listView.setDividerHeight(0);
            listView.setScrollContainer(false);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Uri pdfUri = Uri.parse(pdfItemsuris.get(i)); // Replace with your PDF file path
                    Intent pi=new Intent(getApplicationContext(), PdfViewer.class);
                    System.out.println(pdfUri+"10");
                    pi.putExtra("puri",pdfUri.toString());
                    startActivity(pi);
                }
            });
            listView.setAdapter(listad);
            listView.setPadding(0, 0, 0, 10);
            ll.addView(gridLayout);
            ll.addView(listView);
        }
    }


    public String read(File t_f) throws IOException {
        fis = new FileInputStream(t_f);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        return br.readLine();
    }

    public void write(String s, File theorydates) throws IOException {
        String a = read(theorydates);
        // a.append(s + "#");
        // if(a.toString().isEmpty()){
        // a.append(s)
        // }
        FileOutputStream fos = new FileOutputStream(theorydates);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        Log.d("hellostring", "write: " + s + " " + a);
        if (a == null) {
            bw.write(s + "#");
        } else {
            a += (s + "#");
            bw.write(a.toString());
        }
        bw.flush();
        bw.close();
    }

    @Override
    public void onDismiss() {
        recreate();
    }


}

//
// class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
// private List<Item> imageItems;
// private List<Item> pdfItems;
// private Context context;
//
// private static final int VIEW_TYPE_IMAGE = 1;
// private static final int VIEW_TYPE_PDF = 2;
//
// public CustomAdapter(Context context, List<Item> imageItems, List<Item>
// pdfItems) {
// this.context = context;
// this.imageItems = imageItems;
// this.pdfItems = pdfItems;
// }
// @Override
// public int getItemViewType(int position) {
// // Determine the item view type based on position
// if (position < imageItems.size()) {
// return VIEW_TYPE_IMAGE;
// } else {
// return VIEW_TYPE_PDF;
// }
// }
// @NonNull
// @Override
// public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
// int viewType) {
// LayoutInflater inflater = LayoutInflater.from(context);
//
// if (viewType == VIEW_TYPE_IMAGE) {
// // Create a horizontal item view for images
// View itemView = inflater.inflate(R.layout.horizontal_item_layout, parent,
// false);
// return new ImageViewHolder(itemView);
// } else {
// // Create a vertical item view for PDFs
// View itemView = inflater.inflate(R.layout.vertical_item_layout, parent,
// false);
// return new PdfViewHolder(itemView);
// }
// }
//
// @Override
// public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int
// position) {
// if (holder instanceof ImageViewHolder) {
// // Bind image item data to the horizontal item view
// ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
// Item currentItem = imageItems.get(position);
// imageViewHolder.iconImageView.setImageResource(currentItem.getIconResource());
// imageViewHolder.textView.setText(currentItem.getText());
// } else if (holder instanceof PdfViewHolder) {
// // Bind PDF item data to the vertical item view
// PdfViewHolder pdfViewHolder = (PdfViewHolder) holder;
// Item currentItem = pdfItems.get(position - imageItems.size()); // Adjust
// position
// pdfViewHolder.iconImageView.setImageResource(currentItem.getIconResource());
// pdfViewHolder.textView.setText(currentItem.getText());
// }
// }
//
// @Override
// public int getItemCount() {
// // Total item count is the sum of image and PDF items
// return imageItems.size() + pdfItems.size();
// }
//
// public class ImageViewHolder extends RecyclerView.ViewHolder {
// ImageView iconImageView;
// TextView textView;
//
// public ImageViewHolder(@NonNull View itemView) {
// super(itemView);
// iconImageView = itemView.findViewById(R.id.iconImageView);
// textView = itemView.findViewById(R.id.textView);
// }
// }
//
// public class PdfViewHolder extends RecyclerView.ViewHolder {
// ImageView iconImageView;
// TextView textView;
//
// public PdfViewHolder(@NonNull View itemView) {
// super(itemView);
// iconImageView = itemView.findViewById(R.id.iconImageView);
// textView = itemView.findViewById(R.id.textView);
// }
// }
// }
class Item {
    private int iconResource;
    private String text;

    public Item(int iconResource, String text) {
        this.iconResource = iconResource;
        this.text = text;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getText() {
        return text;
    }
}

class MyListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> al;

    public MyListAdapter(Activity context, ArrayList<String> arrayList) {
        super(context, R.layout.pdflist, arrayList);
        this.context = context;
        this.al = arrayList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.pdflist, null, true);

        TextView titleText = rowView.findViewById(R.id.title);

        // Set the text for the title TextView
        titleText.setText(al.get(position));

        return rowView;
    }
}

class NonScrollListView extends ListView {

    public NonScrollListView(Context context) {
        super(context);
    }

    public NonScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
