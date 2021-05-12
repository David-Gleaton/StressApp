package com.example.stressapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
/*
Team DJ
This activity merely is a controller that directs the user to the selected distraction page
 */

public class DistractionTitle extends AppCompatActivity {
    private static final String PREFS = "prefs";


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
        setContentView(R.layout.activity_distractiondatatitle);

    }

    //pre: Option selected
    //post: Starts DistractionInput Class and passes in what option was selected
    public void OptionSelect(View v){
        String TypeInput = "";
        //Switch statement for which distraction is chosen
        Intent intent = new Intent(this, DistractionInput.class);
        switch(v.getId()){
            case R.id.BubblesButton:
                TypeInput = "Bubbles";
                break;
            case R.id.AmbientButton:
                TypeInput = "Ambient";
                break;
            case R.id.MathButton:
                TypeInput = "Math";
                break;
            case R.id.ExerciseButton:
                TypeInput = "Exercise";
                break;
            case R.id.EncouragementButton:
                TypeInput = "Encourage";
                break;
        }
        intent.putExtra("Type",TypeInput);
        startActivity(intent);

    }

}
