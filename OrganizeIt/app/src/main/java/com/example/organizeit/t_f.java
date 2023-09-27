package com.example.organizeit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.graphics.drawable.Drawable;
import android.content.Intent;
//import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.Fragment;

//import android.os.Handler;
//import android.util.Log;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;


public class t_f extends Fragment {

    FloatingActionButton add_b;
    FileInputStream fis;
    ListView lv;
    ArrayList<String> t_al = new ArrayList<>();

    // text color of list item
    TextView listitem;

    // dialog box
    EditText a;
    Button create;
    // add prompt keys
    String f;

    // String s;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_t_f, container, false);
        add_b = v.findViewById(R.id.float_t);

        // Internal Storage
        File mydir = getContext().getDir("Mydir", Context.MODE_PRIVATE);
        File t_f_data = new File(mydir, "t_f_data.txt");
        boolean check = !t_f_data.exists();
        if (check) {
            internalStorage(t_f_data, getContext());
        }
        // setting up arraylist
        try {
            String st = read(t_f_data);
            String[] starr = st.split("#");
            Collections.addAll(t_al, starr);
        } catch (IOException e) {
            Toast.makeText(getContext(), "Some Error occurred", Toast.LENGTH_SHORT).show();
        }
        lv = v.findViewById(R.id.t_list);
        ArrayAdapter<String> t_adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, t_al) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v1 = super.getView(position, convertView, parent);
                listitem = v1.findViewById(R.id.text1);
                listitem.setTextColor(getResources().getColor(R.color.black, null));
                return v1;
            }
        };
        lv.setAdapter(t_adapter);

        //on Click content visible
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent content=new Intent(getContext(), Content_page.class);
                content.putExtra("position",position);
              content.putExtra("name",t_adapter.getItem(position));

//                Toast.makeText(getContext(), t_adapter.getItem(position), Toast.LENGTH_SHORT).show();
                startActivity(content);
            }
        });

        // deletion

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder ad_b = new AlertDialog.Builder(view.getContext());
                ad_b.setTitle("Delete");
                ad_b.setCancelable(false);
                ad_b.setMessage("Sure ?");

                ad_b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        t_al.remove(position);
                        t_adapter.notifyDataSetChanged();
                        StringBuilder sb = new StringBuilder();
                        t_al.forEach(element -> {
                            sb.append(element);
                            sb.append("#");
                        });
                        try {
                            writedel(sb.toString(), t_f_data);
                        } catch (IOException e) {
                            Toast.makeText(getContext(), "Item not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ad_b.setNegativeButton("No", null);
                ad_b.show();
                return true;
            }
        });

        // dialog box
        Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.add_dialog);
        add_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.setCancelable(true);
                d.show();
            }
        });
        create = d.findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = d.findViewById(R.id.abutton);
                // b= d.findViewById(R.id.bbutton);
                f = a.getText().toString();
                // s=b.getText().toString();
                // t_al.add(f);
                // write
                if (f.trim().isEmpty()) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                }
                else{
                    try {
                        write(f, t_f_data);
                        t_al.add(f);
                        t_adapter.notifyDataSetChanged();

                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Item not added", Toast.LENGTH_SHORT).show();
                    }
                    a.setText("");
                    // b.setText("");
                    d.dismiss();
                }
            }
        });

        return v;
    }

    public static void internalStorage(File t_f_data, Context cont) {
        try {
            t_f_data.createNewFile();
            FileOutputStream fos = new FileOutputStream(t_f_data);
            // Storing default items to the textfile
            fos.write("Internet Of Things".getBytes());
            fos.write("#".getBytes());
            fos.write("Introduction to Artificial Intelligence".getBytes());
            fos.write("#".getBytes());
            fos.write("Software Testing and Quality Assurance".getBytes());
            fos.write("#".getBytes());
            fos.write("Cyber and Network Security".getBytes());
            fos.write("#".getBytes());
            fos.write("Theory Of Computation".getBytes());
            fos.write("#".getBytes());
            fos.flush();
            fos.close();

        } catch (IOException e) {
            Toast.makeText(cont, "excep", Toast.LENGTH_SHORT).show();
        }
    }

    public  void write(String s, File t_f) throws IOException {
        String a =read(t_f);
//        a.append(s + "#");
        a+=(s+"#");
        FileOutputStream fos = new FileOutputStream(t_f);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(a);
        bw.flush();
        bw.close();
    }

    public static void writedel(String s, File t_f) throws IOException {
        FileOutputStream fos = new FileOutputStream(t_f);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(s);
        bw.flush();
        bw.close();
    }

    public  String read(File t_f) throws IOException {
         fis = new FileInputStream(t_f);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

}
