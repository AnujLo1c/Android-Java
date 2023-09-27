package com.example.organizeit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class p_f extends Fragment {
    // textcolor
    TextView listitem;
    FloatingActionButton add_b;
    ListView lv;
    ArrayList<String> p_al = new ArrayList<>();

    // dialog box
    EditText a, b;
    Button create;
    String f, s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_p_f, container, false);
        add_b = v.findViewById(R.id.float_p);

        // data internal storage
        File mydir = getContext().getDir("Mydir", Context.MODE_PRIVATE);
        File p_f_data = new File(mydir, "p_f_data.txt");
        boolean check = !p_f_data.exists();
        if (check) {
            internalStorage(p_f_data, getContext());
        }

        // setting up arraylist
        try {
            String st = read(p_f_data, getContext());
            // System.out.println(st);
            String[] starr = st.split("#");
            for (String i : starr) {
                // System.out.println(i);
                p_al.add(i);
            }
            // System.out.println(t_al);
            // Log.e("AL", String.valueOf(t_al));
        } catch (IOException e) {
            Toast.makeText(getContext(), "Some Error occurred", Toast.LENGTH_SHORT).show();
        }

        // List start's from here
        lv = v.findViewById(R.id.p_list);

        // p_al.add("Internet Of Things");
        // p_al.add("Introduction to Artificial Intelligence");
        // p_al.add("Software Testing and Quality Assurance");
        // p_al.add("Cyber and Network Security ");
        // p_al.add("Programming with Python ");
        ArrayAdapter<String> t_adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, p_al) {
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

        // deletion

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder ad_b = new AlertDialog.Builder(view.getContext());
                ad_b.setTitle("Delete");
                ad_b.setCancelable(false);
                ad_b.setMessage("Sure?");

                ad_b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        p_al.remove(position);
                        t_adapter.notifyDataSetChanged();
                        StringBuilder sb = new StringBuilder();
                        p_al.forEach(element -> {
                            sb.append(element);
                            sb.append("#");
                        });
                        try {
                            writedel(sb.toString(), p_f_data, getContext());
                        } catch (IOException e) {
                            Toast.makeText(getContext(), "Item not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ad_b.setNegativeButton("No", null);
                ad_b.show();
                return false;
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
                        write(f, p_f_data, getContext());
                        p_al.add(f);
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

        // Inflate the layout for this fragment
        return v;
    }

    public static void internalStorage(File p_f_data, Context cont) {
        try {
            p_f_data.createNewFile();
            FileOutputStream fos = new FileOutputStream(p_f_data);
            // Storing default items to the textfile
            fos.write("Internet Of Things".getBytes());
            fos.write("#".getBytes());
            fos.write("Introduction to Artificial Intelligence".getBytes());
            fos.write("#".getBytes());
            fos.write("Software Testing and Quality Assurance".getBytes());
            fos.write("#".getBytes());
            fos.write("Cyber and Network Security".getBytes());
            fos.write("#".getBytes());
            fos.write("Programming with Python".getBytes());
            fos.write("#".getBytes());
            fos.flush();
            fos.close();
            // FileInputStream fis=new FileInputStream("t_f_data.txt");
            // Toast.makeText(cont, "Data stored for f time", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(cont, "excep", Toast.LENGTH_SHORT).show();
        }
    }

    public static void write(String s, File p_f, Context context) throws IOException {
        StringBuilder a = new StringBuilder(read(p_f, context));
        a.append(s + "#");
        FileOutputStream fos = new FileOutputStream(p_f);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        osw.write(String.valueOf(a.toString()));
        osw.flush();
        osw.close();
    }

    public static void writedel(String s, File p_f, Context context) throws IOException {
        FileOutputStream fos = new FileOutputStream(p_f);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        osw.write(s);
        osw.flush();
        osw.close();
    }

    public static String read(File p_f, Context context) throws IOException {
        FileInputStream fis = new FileInputStream(p_f);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

}