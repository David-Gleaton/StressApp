
package com.example.stressapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
Team DJ
This activity handles the input from the respective layout and activate the DB functions
 */


public class InputDataInput extends AppCompatActivity {
    //PREFS and Maxbright used to correctly build the theme on screen brightness or theme pref
    private static final String PREFS = "prefs";
    private static final double MaxBright = 255.0;
    //Database object
    Database db;


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
        //Auto Switch to dark theme if screen brightness is < 30% of max brightness
        try {
            float curBrightnessValue=android.provider.Settings.System.getInt(
                    getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            if(curBrightnessValue < MaxBright * 0.3){
                setTheme(R.style.Dark);
            }

        } catch (android.provider.Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        //Create Instance of db
        db = new Database(this);


        //Switch on what type of input
        Intent intent = getIntent();
        String Type = intent.getExtras().getString("Type");
        switch(Type){
            case "Eating":
                setContentView(R.layout.activity_eatinginput);
                break;
            case "Sleeping":
                setContentView(R.layout.activity_sleepinginput);
                break;
            case "Exercise":
                setContentView(R.layout.activity_excerciseinput);
                break;
            case "Social":
                setContentView(R.layout.activity_socialinput);
                break;
            case "Finance":
                setContentView(R.layout.activity_financesinput);
                break;
            case "Mental":
                setContentView(R.layout.activity_mentalinput);
                break;
        }



    }


    //pre: The user has pressed the confirm button
    //post: Inputs into database and generates a toast on success or failure
    public void EatConfirm(View v){

        //Grab the editexts
        EditText BreakfastText = findViewById(R.id.EditBreakfast);
        String Breakfast = BreakfastText.getText().toString();
        EditText LunchText = findViewById(R.id.EditLunch);
        String Lunch = LunchText.getText().toString();
        EditText DinnerText = findViewById(R.id.EditDinner);
        String Dinner = DinnerText.getText().toString();

        //Parse strings to an int and sum
        int Total = Integer.parseInt("0"+Breakfast) + Integer.parseInt("0"+Lunch) + Integer.parseInt("0"+Dinner);

        //Insert into db
        long success = db.addEat(Total);

        if(success != -1){
            Context context = getApplicationContext();
            CharSequence text = "Input Recorded!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Error in Input!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }


    //pre: User Pressed confirm
    //post: inputs into database and generates toast based on success or failure
    public void SleepConfirm(View v){
            //Grab picker times
            TimePicker WakePicker = findViewById(R.id.WakePicker);
            TimePicker SleepPicker = findViewById(R.id.SleepPicker);
            int wakeHour, wakeMin, sleepHour, sleepMin;

            wakeHour = WakePicker.getHour();
            sleepHour = SleepPicker.getHour();
            wakeMin = WakePicker.getMinute();
            sleepMin = SleepPicker.getMinute();

            //Change time to % of hours for display later
            double adjustedWake = wakeHour + (wakeMin/60.0);
            double adjustedSleep = sleepHour + (sleepMin/60.0);
            double TotalSleep = adjustedSleep - adjustedWake;
            //if negative, flip sign. Can't sleep negative times, even though it feels like that sometimes
            if(TotalSleep <0){
                TotalSleep *= -1;
            }

            //Insert into db
            long success = db.addSleep(TotalSleep);

            if(success != -1){
                Context context = getApplicationContext();
                CharSequence text = "Input Recorded!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }else{
                Context context = getApplicationContext();
                CharSequence text = "Error in Input!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }




    }


    //pre: user press exercise confirm
    //post: write to db nd generate toast on success or failure
    public void ExerciseConfirm(View v){
        //Grab edittexts
        EditText EditHours = findViewById(R.id.EditHours);
        EditText EditMins = findViewById(R.id.EditMins);
        String Hours = EditHours.getText().toString();
        String Mins = EditMins.getText().toString();
        int hours, mins;
        //Parse into ints
        hours = Integer.parseInt("0"+Hours);
        mins = Integer.parseInt("0"+Mins);
        //If user enters >24 or > 59 put down to actual maximums
        if(hours >24){
            hours = 24;
        }
        if(mins > 59){
            mins = 59;
        }

        //Adjust to % of an hour for display
        double adjustedExcercise = hours + (mins/60.0);


        //Insert into db
        long success = db.addExcercise(adjustedExcercise);

        if(success != -1){
            Context context = getApplicationContext();
            CharSequence text = "Input Recorded!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Error in Input!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }



    //pre: user selects confirm
    //post: write to database and generate toast
    public void SocialConfirm(View v){
        //Grab edittextss
        EditText EditHours = findViewById(R.id.EditHours);
        EditText EditMins = findViewById(R.id.EditMins);
        EditText EditNumPpl = findViewById(R.id.EditNumPpl);
        String Hours = EditHours.getText().toString();
        String Mins = EditMins.getText().toString();
        String NumPpl = EditNumPpl.getText().toString();
        int hours, mins, numPpl;
        //Parse into ints
        hours = Integer.parseInt("0"+Hours);
        mins = Integer.parseInt("0"+Mins);
        numPpl = Integer.parseInt("0"+NumPpl);
        //Round numbers so they make sense
        if(hours >24){
            hours = 24;
        }
        if(mins > 59){
            mins = 59;
        }

        //Adjust for display
        double adjustedSocial = hours + (mins/60.0);

        //Insert into db
        long success = db.addSocial(adjustedSocial, numPpl);

        if(success != -1){
            Context context = getApplicationContext();
            CharSequence text = "Input Recorded!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Error in Input!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }




    }


    //pre: user pressed confirm
    //post: write to db and generate toast
    public void FinanceConfirm(View v){
        //grab editexts
        EditText EditMoney = findViewById(R.id.EditMoney);
        String Money = EditMoney.getText().toString();
        double money;
        //Parse and round
        money = Double.parseDouble("0"+Money);
        money = Math.round(money * 100.0)/ 100.0;


        //insert into db
        long success = db.addFinance(money);

        if(success != -1){
            Context context = getApplicationContext();
            CharSequence text = "Input Recorded!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Error in Input!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    //Pre: user presses a face
    //post: write to db and generate toast
    public void MentalConfirm(View v){

        //Get what face was pressed
        String FaceType = v.getTag().toString();
        long success = -1;
        //Switch on what face and write to db
        switch (FaceType){
            case "Sad":
                success = db.addMood(0);
                break;
            case "Neutral":
                success = db.addMood(1);
                break;
            case "Happy":
                success = db.addMood(2);
                break;
        }

        if(success != -1){
            Context context = getApplicationContext();
            CharSequence text = "Input Recorded!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Error in Input!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }






}