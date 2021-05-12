package com.example.stressapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
/*
Team DJ
This activity takes in user selection and directs to the proper activity
 */

public class GraphDataTitle extends AppCompatActivity {
    //PRES and Maxbright used to correctly build the theme on screen brightness or theme pref
    private static final String PREFS = "prefs";
    private static final double MaxBright = 255.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Change appearance based on Theme
        SharedPreferences preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        String Theme = preferences.getString("Theme", "defaultreturn");

        //Switch case for theme switching
        switch (Theme) {
            case "Dark Theme":
                setTheme(R.style.Dark);
                break;
            case "Red Theme":
                setTheme(R.style.LightPink);
                break;

        }
        //Auto Switch to dark theme if screen brightness is < 30% of max brightness
        try {
            float curBrightnessValue = android.provider.Settings.System.getInt(
                    getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            if (curBrightnessValue < MaxBright * 0.3) {
                setTheme(R.style.Dark);
            }

        } catch (android.provider.Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_graphdatatitle);

    }

    //pre: option is selected
    //post: starts activity depending on which button is selected
    public void OptionSelect(View v) {
        String TypeInput = "";
        Intent intent = new Intent(this, GraphDataGraph.class);
        switch (v.getId()) {
            case R.id.EatingHabitsButton:
                TypeInput = "Eating";

                break;
            case R.id.SleepingHabitsButton:
                TypeInput = "Sleeping";

                break;
            case R.id.ExerciseButton:
                TypeInput = "Exercise";

                break;
            case R.id.SocialButton:
                TypeInput = "Social";

                break;
            case R.id.FinanceButton:
                TypeInput = "Finance";

                break;
            case R.id.MentalButton:
                TypeInput = "Mental";

                break;

        }
        intent.putExtra("Type", TypeInput);
        startActivity(intent);

    }

}
