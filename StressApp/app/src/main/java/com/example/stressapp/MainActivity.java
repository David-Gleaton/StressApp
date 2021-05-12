package com.example.stressapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;

import java.io.IOException;
/*
Team DJ
This is the main controller page for the app, where the user will decide where to go
It hosts a appbar menu that directs to settings, graphs, the profile, an additional button to go
to the distractions page, and input
The main button takes the user to the distractions page
If the user has set up their profile, it will greet them by name and display their
profile photo
 */



public class MainActivity extends AppCompatActivity {
    //PREFS and Maxbright used to correctly build the theme on screen brightness or theme pref
    //s used to greet the user if the profile is set up
    private static final String PREFS = "prefs";
    private static final double MaxBright = 255.0;
    StringBuilder s = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Change appearance based on Theme
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String Theme = preferences.getString("Theme", "defaultreturn");

        //Grab the titleimage for setting
        int ImageSrc = R.drawable.titleimagee91e63_com;

        //Switch case for theme switching
        switch(Theme){
            case "Dark Theme":
                setTheme(R.style.Dark);
                ImageSrc = R.drawable.titleimagewhite_com;
                break;
            case "Red Theme":
                setTheme(R.style.LightPink);
                ImageSrc = R.drawable.titleimagee91e63_com;
                break;

        }

        //Auto Switch to dark theme if screen brightness is < 30% of max brightness
        try {
            float curBrightnessValue=android.provider.Settings.System.getInt(
                    getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            if(curBrightnessValue < MaxBright * 0.3){
                setTheme(R.style.Dark);
                ImageSrc = R.drawable.titleimagewhite_com;
            }

        } catch (android.provider.Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        //Set the contentview
        setContentView(R.layout.activity_main);


        ImageView TitleImage = findViewById(R.id.TitleImage);
        TitleImage.setImageResource(ImageSrc);

        //If the user profile is set up, greets the user by name
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        Boolean saved = user.getBoolean("saved", false);
        if (saved == true) {
            s.append(getResources().getString(R.string.greeting));
            s.append(" ");
            s.append(user.getString("name", ""));
            s.append(getResources().getString(R.string.excite));
            TextView greeting = findViewById(R.id.HelloText);
            greeting.setText(s);
            s.setLength(0);
            greeting.setVisibility(View.VISIBLE);

            String uriString = user.getString("uri", "");
            if (uriString != "") {

                Uri uri = Uri.parse(uriString);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Bitmap rotatedBitmap = ProfileTitle.rotateImage(bitmap, 90);
                    TitleImage.setImageBitmap(rotatedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }


    //Create menu taken from zybooks
    //pre: The content view is set
    //post: inflates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }



    //pre: A menu item is selected
    //post: Starts new activity based on selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Switch statement for the menu options
            case R.id.Action_Settings:
                //Settings Selected
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;

            case R.id.Action_Input:
                // Input selected
                intent = new Intent(this, InputDataTitle.class);
                startActivity(intent);
                return true;

            case R.id.Action_Graph:
                //Graph selected
                intent = new Intent(this, GraphDataTitle.class);
                startActivity(intent);
                return true;

            case R.id.Action_Distraction:
                //Distraction selected
                intent = new Intent(this, DistractionTitle.class);
                startActivity(intent);
                return true;

            case R.id.Action_Profile:
                //Profile selected
                intent = new Intent(this, ProfileTitle.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //pre: Main button selected
    //post: Starts Distraction Title Activity
    public void OnStartClick(View view){
        Intent intent = new Intent(this, DistractionTitle.class);
        startActivity(intent);
    }
}