package com.example.organizeit;

import static android.app.Activity.RESULT_OK;

//import android.app.ActivityManager;
import android.content.Context;
//import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
//import android.database.Cursor;
import android.graphics.Bitmap;
//import android.os.Build;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.documentfile.provider.DocumentFile;

//import android.os.Environment;
import android.provider.MediaStore;
//import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
//import android.view.ViewParent;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.sql.Date;
//import java.sql.Time;
import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.OutputStream;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.Date;

public class bottomDrawarFragment extends BottomSheetDialogFragment {
    ImageFilterButton camera,files;
//    File root;
Bitmap image;
Date date;
SimpleDateFormat dateFormat,dateFormat2;
public File pdir,dir,theoryfolder, destinationFile;
//    String dataToSend;

    public interface OnDismissListener {
        void onDismiss(); // Modify the parameter type as needed
    }

    private OnDismissListener onDismissListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onDismissListener = (OnDismissListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement OnDismissListener");
        }
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            // Pass data to the hosting activity
             // Modify this with the actual data
            onDismissListener.onDismiss();
        }
    }
    //File mainfile;
//    //interface for data transger
//    public interface bitmapTransfer{
//        public void bitmapfile(Bitmap b);
//    }
//    bitmapTransfer imagefile;
//    @Override
//            public void onAttach(Activity activity){
//        super.onAttach();
//    }
ActivityResultLauncher<Intent> imageCapture=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
//            Log.d("camera output", "Image file generated1" );
//        System.out.println("hii22");
        if(result.getResultCode()==RESULT_OK && result.getData()!=null){
        Intent out=result.getData();
        image= (Bitmap) out.getExtras().get("data");
            //                Log.d("asdf", "onActivityResult: 1
            saveImage(image);

//            Intent i=new Intent()
//            cp.startActivity(getActivity().getIntent());
//                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            Log.d("camera output", "Image file generated" );
        }
        else{
            Log.d("camera output", "Image not file generated" );
        }

           dismiss();

    }
});
ActivityResultLauncher<Intent> fileFetch=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
    if(result.getResultCode()==RESULT_OK && result.getData()!=null){



            String dataType = result.getData().resolveType(getContext().getContentResolver());
            Log.d("asdf", "onActivityResult: "+dataType+dataType.endsWith("wordprocessingml.document"));
            Intent data=result.getData();
            if (dataType != null) {
                if (dataType.endsWith("jpeg") || dataType.endsWith("png")||dataType.endsWith("jpg")) {
                    // It's an image
//                        System.out.println("here");

//                        image=(Bitmap) out.getExtras().get("data");
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        try {
                            // Copy the selected image to internal storage
                            saveImageToInternalStorage(selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error saving image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                 else if (dataType.endsWith("pdf")) {
//                        Log.d("fileType", "onActivityResult: pdf");
                    if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();

//                            Log.d("pdfcheck", "dksaljf"+data.getExtras().get("name"));
                        // Copy the selected image to internal storage
                        savePdfToInternalStorage(selectedImageUri);
                    }

            }
//                    cp.recreate();
                }
            else {
                    Toast.makeText(getContext(), "File format : jpeg,png,pdf,doc", Toast.LENGTH_SHORT).show();
                }

}

dismiss();
});
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_bottom_drawar, container, false);
         camera=v.findViewById(R.id.imageView);
         files=v.findViewById(R.id.files);
        date=new Date();date.getTime();
theoryfolder=new File(getActivity().getFilesDir(),"Theory");
      dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
      dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");

        if(!theoryfolder.exists()){
    theoryfolder.mkdir();
}

        pdir=new File(theoryfolder,"Theory_"+getArguments().getString("name"));
        if(!pdir.exists()){
            pdir.mkdir();
        }
         dir=new File(pdir,dateFormat2.format(date));
        if(!dir.exists()){
            dir.mkdir();
        }
        //onclick


        camera.setOnClickListener(v1 -> {

//            for(File f:dir.listFiles()) {
//                Log.d("asdfasdf", "onCreateView: " + f.getName());
//            }
            //        System.out.println(s);
//        System.out.println("hii");
            Intent cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
//                startActivity(cam);
            imageCapture.launch(cam);
        });
        files.setOnClickListener(v12 -> {
        System.out.println("hii2");
            Intent file=new Intent(Intent.ACTION_GET_CONTENT);
            file.setType("*/*"); // Set the MIME type or use "application/*" for all file types
            file.addCategory(Intent.CATEGORY_OPENABLE);
            fileFetch.launch(file);
        });
        return v;
    }

    public void saveImage(Bitmap image){

        File pictureFile;
//        dir=new File(getActivity().getFilesDir(),"Images");

         pictureFile=new File(dir,dateFormat.format(date)+".jpg");
//         dataToSend=dateFormat.format(date)+".jpg";
//        cp.editItemImg(dateFormat.format(date)+".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
//            Log.d("imagesave",dir.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Toast.makeText(getContext(), "File not Saved", Toast.LENGTH_SHORT).show();
            Log.d("imagesave", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("imagesave", "Error accessing file: " + e.getMessage());
        }
    }
    private void saveImageToInternalStorage(Uri imageUri) throws IOException {
        // Open an input stream from the selected image URI
        if(imageUri==null) Log.d("asdf", "saveImageToInternalStorage: emptu");
        InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
        Log.d("asdf", "onActivityResult: 2a");
        if (inputStream != null) {
            // Create a file in internal storage
//            File internalStorageDir = new File(dir,"Images");
//            if (!internalStorageDir.exists()) {
//                internalStorageDir.mkdir(); // Create the directory if it doesn't exist
//            }
            Date dateTime=new Date();dateTime.getTime();
             destinationFile = new File(dir, dateFormat.format(dateTime)+".jpg");

            // Open an output stream to the destination file
            OutputStream outputStream = new FileOutputStream(destinationFile);
//            Log.d("asdf", "onActivityResult: 2c");
            // Copy the image data from the input stream to the output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            // Set the saved image to the ImageView (optional)
//            imageView.setImageURI(Uri.fromFile(destinationFile));
//            Log.d("asdf", "onActivityResult: 2e");
            Toast.makeText(getContext(), "Image stored", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error opening image", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePdfToInternalStorage(Uri pdfUri) {
        try {
//            Log.d("pdfcheck", "dksaljf2"+DocumentFile.fromSingleUri(getContext(),pdfUri).getName());
            InputStream inputStream = getActivity().getContentResolver().openInputStream(pdfUri);

            File pdfFile = new File( destinationFile, DocumentFile.fromSingleUri(getContext(),pdfUri).getName());
//            File pdfFile = new File(dir, dateFormat.format(new Date().getTime())+"."+DocumentFile.fromSingleUri(getContext(),pdfUri).getName());
            OutputStream outputStream = new FileOutputStream(pdfFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            inputStream.close();
            outputStream.close();
            Toast.makeText(getContext(), "File  saved", Toast.LENGTH_SHORT).show();
            // PDF is now saved in internal storage
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error : File not saved", Toast.LENGTH_SHORT).show();

        }
    }

//    void activityCheck(Context context){
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        if (activityManager != null) {
//            // Get the list of running tasks (activities)
//            ActivityManager.RunningTaskInfo taskInfo = activityManager.getRunningTasks(1).get(0);
//
//            // Print the list of activities in the stack
//            String packageName = taskInfo.topActivity.getPackageName();
//            String className = taskInfo.topActivity.getClassName();
//
//            // You can also access other information like the base activity, task ID, etc.
//            int taskId = taskInfo.id;
//            String baseActivity = taskInfo.baseActivity.getClassName();
//
//            // Print or log the information
//            System.out.println("Package Name: " + packageName);
//            System.out.println("Top Activity: " + className);
//            System.out.println("Task ID: " + taskId);
//            System.out.println("Base Activity: " + baseActivity);
//        }
//    }

}
