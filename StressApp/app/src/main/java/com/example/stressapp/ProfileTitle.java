package com.example.stressapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Matrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
Team DJ
This page handles the user's profile.
A user can take a photo and set their chosen name
If they click the save button, their profile photo
and chosen name can be used for personalized greetings
when they enter the app.
 */

public class ProfileTitle extends AppCompatActivity {
    //REQUEST_IMAGE_CAPTURE and ASK_MULTIPLE_PERMISSION_REQUEST_CODE
    //are used for obtaining permissions
    //mPhotoImageView, image, and imageFilename are used for accessing
    //and displaying the image
    private static final String PREFS = "prefs";
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 103;
    ImageView mPhotoImageView;
    File image = null;
    String imageFilename = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Change appearance based on Theme
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String Theme = preferences.getString("Theme", "defaultreturn");

        //Switch case for theme switching
        switch(Theme){
            case "Dark Theme":
                setTheme(R.style.Dark);
                break;
            case "Red Theme":
                setTheme(R.style.LightPink);
                break;

        }
        setContentView(R.layout.activity_profiletitle);
        mPhotoImageView = findViewById(R.id.profilePhoto);

        //If the user has already set up their profile, it displays
        //their chosen name and profile photo
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        Boolean saved = user.getBoolean("saved", false);
        if (saved == true) {

            EditText name = findViewById(R.id.nameInsert);
            name.setText(user.getString("name", ""));

            String uriString = user.getString("uri", "");
            if (uriString != "") {

                Uri uri = Uri.parse(uriString);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Bitmap rotatedBitmap = ProfileTitle.rotateImage(bitmap, 90);
                    mPhotoImageView.setImageBitmap(rotatedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        //Requests permissions from the user to access the necessary data
        requestPermissions(new String[] {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

    }

    //pre: the user has clicked to take a photo
    //post: creates an image file and returns it
    private File createImageFile() throws IOException {

        // Create a unique filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFilename = "photo_" + timeStamp + ".jpg";

        // Create the file in the Pictures directory on external storage
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        image = new File(storageDir, imageFilename);
        return image;
    }

    public void takePhotoClick(View view) {

        // Create implicit intent
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoCaptureIntent.resolveActivity(getPackageManager()) != null) {

            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Error", "Error creating image file: " + ex);
            }

            // If the File was successfully created, start camera app
            if (photoFile != null) {

                // Create content URI to grant camera app write permission to photoFile
                Uri photoUri = FileProvider.getUriForFile(this,
                        "com.example.stressapp.fileprovider",
                        photoFile);

                // Add content URI to intent
                photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                // Start camera app
                startActivityForResult(photoCaptureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //pre: an image and an angle to be rotated have been passed in
    //post: a rotated image is returned
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    //pre: an image has been taken on the camera app
    //post: the image is rotated and displayed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //The image is accessed
            Uri uri = Uri.fromFile(image);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //The image is rotated
            Bitmap rotatedBitmap = rotateImage(bitmap, 90);

            //The image is displayed
            mPhotoImageView.setImageBitmap(rotatedBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //pre: The user clicked the save button
    //post: The user's profile picture and name are saved
    public void saveProfile(View view) {
        EditText name = findViewById(R.id.nameInsert);
        //The shared preferences are updated
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        edit.putString("name", name.getText().toString().trim());
        edit.putString("file", imageFilename);
        edit.putBoolean("saved", true);
        Uri uri = Uri.fromFile(image);
        edit.putString("uri", uri.toString());
        edit.commit();

        //A toast is printed so the user knows their data has been saved
        Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();

    }
}
