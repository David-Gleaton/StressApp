package com.example.stressapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
/*
Team DJ
This activity handles the input from the respective layout and activate the chosen distraction
 */

public class DistractionInput extends AppCompatActivity {

    //random used to create variety in the distractions
    //canvas, context, screen, size, width, height, radius, and bubble_count
    //used in drawing the bubble distraction
    //mediaPlayer, wind_media, waves_media, and fire_media used for the ambient
    //sounds distraction
    //solution used for the math distraction
    //s used for the encouragement distraction
    //healthystring used for the exercise distraction
    private static final String PREFS = "prefs";
    Random random = new Random();
    Canvas canvas = new Canvas();
    Context context;
    Display screen;
    Point size = new Point();
    int width, height;
    int radius = 100;
    int bubble_count = 0;
    private MediaPlayer mediaPlayer;
    private MediaPlayer wind_media, waves_media, fire_media;
    int solution = 0;
    StringBuilder s = new StringBuilder(100);
    StringBuilder healthystring = new StringBuilder(200);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getBaseContext();
        screen = context.getDisplay();

        screen.getRealSize(size);
        width = size.x;
        height = size.y;

        mediaPlayer = MediaPlayer.create(this, R.raw.popping_sound);
        wind_media = MediaPlayer.create(this, R.raw.beautiful_bells);
        waves_media = MediaPlayer.create(this, R.raw.hawaii_waves);
        fire_media = MediaPlayer.create(this, R.raw.small_fireplace_crackles);

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
        //Switch case for the distraction buttons
        Intent intent = getIntent();
        String Type = intent.getExtras().getString("Type");
        switch(Type){
            case "Bubbles":
                BubblesLayout();
                break;
            case "Ambient":
                AmbientLayout();
                break;
            case "Math":
                MathLayout();
                break;
            case "Exercise":
                ExerciseLayout();
                break;
            case "Encourage":
                EncourageLayout();
                break;
        }

    }

    //pre: The user has chosen the bubbles distraction
    //post: Creates bubbles that the user can click on and "pop"
    private void BubblesLayout(){

        setContentView(R.layout.activity_bubbles);
        int num_bubbles = random.nextInt(21);
        bubble_count = num_bubbles;
        int[] dot_colors = getResources().getIntArray(R.array.circle_colors);

        //Loops through and creates new bubbles
        for (int i = 0; i < num_bubbles; i++) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new LinearLayout.LayoutParams(random.nextInt(width), random.nextInt(height)));
            ShapeDrawable circle = new ShapeDrawable(new OvalShape());
            int randx = random.nextInt(width);
            circle.setIntrinsicWidth(randx);
            circle.setIntrinsicHeight(randx);
            circle.getPaint().setColor(dot_colors[random.nextInt(dot_colors.length)]);
            image.setImageDrawable(circle);
            addContentView(image, image.getLayoutParams());
            image.setOnClickListener(new View.OnClickListener() {
                //pre: a bubble is clicked
                //post: a bubble is popped. If the number of bubbles
                //is too low, more bubbles are created
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();
                    bubble_count--;
                    if (bubble_count < 3) {
                        moreBubbles();
                    }
                    image.setVisibility(View.GONE);
                }
            });
        }
    }

    //pre: The bubbles distraction is loaded and playing
    //post: Adds more bubbles that the user can click on and "pop"
    private void moreBubbles() {
        int num_bubbles = random.nextInt(21);
        bubble_count += num_bubbles;
        int[] dot_colors = getResources().getIntArray(R.array.circle_colors);

        //Loops through and creates new bubbles
        for (int i = 0; i < num_bubbles; i++) {
            ImageView image = new ImageView(context);
            image.setLayoutParams(new LinearLayout.LayoutParams(random.nextInt(width), random.nextInt(height)));
            ShapeDrawable circle = new ShapeDrawable(new OvalShape());
            int randx = random.nextInt(width);
            circle.setIntrinsicWidth(randx);
            circle.setIntrinsicHeight(randx);
            circle.getPaint().setColor(dot_colors[random.nextInt(dot_colors.length)]);
            image.setImageDrawable(circle);
            addContentView(image, image.getLayoutParams());
            image.setOnClickListener(new View.OnClickListener() {
                //pre: a bubble is clicked
                //post: a bubble is popped. If the number of bubbles
                //is too low, more bubbles are created
                @Override
                public void onClick(View v) {
                    mediaPlayer.start();
                    bubble_count--;
                    if (bubble_count < 3) {
                        moreBubbles();
                    }
                    image.setVisibility(View.GONE);
                }
            });
        }
    }

    //pre: The user has chosen the ambient music distraction
    //post: Sets the ambient layout, where users can choose from
    //different relaxing sounds to play
    private void AmbientLayout() {
        setContentView(R.layout.activity_ambient);
    }

    //pre: Option selected
    //post: pauses any music that is currently playing and then
    //starts the new music
    public void AmbientOptionSelect(View v){
        //Switch statement for the ambient music types
        switch(v.getId()){
            case R.id.WavesButton:
                if (wind_media.isPlaying()) wind_media.pause();
                if(fire_media.isPlaying()) fire_media.pause();
                waves_media.setLooping(true);
                waves_media.start();
                break;
            case R.id.WindButton:
                if (waves_media.isPlaying()) waves_media.pause();
                if (fire_media.isPlaying()) fire_media.pause();
                wind_media.setLooping(true);
                wind_media.start();
                break;
            case R.id.FireButton:
                if (waves_media.isPlaying()) waves_media.pause();
                if (wind_media.isPlaying()) wind_media.pause();
                fire_media.setLooping(true);
                fire_media.start();
                break;
        }

    }

    //pre: The back button has been pressed
    //post: Any ambient sounds will be paused
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (waves_media.isPlaying()) waves_media.pause();
        if (wind_media.isPlaying()) wind_media.pause();
        if (fire_media.isPlaying()) fire_media.pause();
    }

    //pre: The menu back button has been pressed
    //post: Any ambient sounds will be paused
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (waves_media.isPlaying()) waves_media.pause();
        if (wind_media.isPlaying()) wind_media.pause();
        if (fire_media.isPlaying()) fire_media.pause();
        return super.onOptionsItemSelected(item);
    }

    //pre: The math distraction has been selected
    //post: Sets up the math distraction and allows the user to solve
    //random math problems
    private void MathLayout() {
        setContentView(R.layout.activity_math);
        int[] math_symbols = getResources().getIntArray(R.array.math_symbols);
        int variable_1 = random.nextInt(20);
        int variable_2 = random.nextInt(20);
        int symbol = random.nextInt(math_symbols.length);
        TextView problem = findViewById(R.id.MathProblem);
        //Switch statement to display the current math problem
        switch (symbol) {
            case 0:
                solution = variable_1 + variable_2;
                problem.setText(variable_1 + " + " + variable_2);
                break;
            case 1:
                solution = variable_1 - variable_2;
                problem.setText(variable_1 + " - " + variable_2);
                break;
            case 2:
                solution = variable_1 * variable_2;
                problem.setText(variable_1 + " * " + variable_2);
                break;
        }
    }

    //pre: The user has clicked the button to check their answer
    //post: If the user was correct, a congratulations toast is displayed
    //If they are wrong, a toast saying they were wrong and revealing the
    //correct answer is shown. Either way, a new problem is set up for
    //the user to solve.
    public void checkAnswer(View view) {
        EditText text = findViewById(R.id.MathAnswer);
        String temp = text.getText().toString();
        int value = 0;
        //Checks that the user didn't leave the answer box blank
        if (!temp.equals("")) {
            value = Integer.parseInt(temp);
        }
        else value = 0;
        //Checks the user's answer
        //If they got it right, make a congrats toast
        if (value == solution) {
            Toast.makeText(this, R.string.correct, Toast.LENGTH_LONG).show();
        }
        //If they got it wrong, make a too bad toast and say the correct answer
        else {
            s.append(getResources().getString(R.string.wrong));
            s.append(" ");
            s.append(String.valueOf(solution));
            Toast.makeText(this, s.toString(), Toast.LENGTH_LONG).show();
            s.setLength(0);
        }
        //Reset the user's text box and set up a new math problem
        text.setText("");
        int[] math_symbols = getResources().getIntArray(R.array.math_symbols);
        int variable_1 = random.nextInt(20);
        int variable_2 = random.nextInt(20);
        int symbol = random.nextInt(math_symbols.length);
        TextView problem = findViewById(R.id.MathProblem);
        //Switch statement to display the current math problem
        switch (symbol) {
            case 0:
                solution = variable_1 + variable_2;
                problem.setText(variable_1 + " + " + variable_2);
                break;
            case 1:
                solution = variable_1 - variable_2;
                problem.setText(variable_1 + " - " + variable_2);
                break;
            case 2:
                solution = variable_1 * variable_2;
                problem.setText(variable_1 + " * " + variable_2);
                break;
        }
    }

    //pre: The exercise distraction is selected
    //post: The exercise distraction is set up and allows the user to
    //choose from randomly generated exercises and healthy activities.
    private void ExerciseLayout() {
        setContentView(R.layout.activity_exercise);
        String[] exercises = getResources().getStringArray(R.array.exercise_types);
        //Choose a random number of the exercise to do
        int size1 = random.nextInt(10);
        int size2 = random.nextInt(10);
        int size3 = random.nextInt(10);
        if (size1 == 0) size1++;
        if (size2 == 0) size2++;
        if (size3 == 0) size3++;
        //Choose a random exercise to do and set up button 1
        int exercise1 = random.nextInt(exercises.length);
        int exercise2 = random.nextInt(exercises.length);
        int exercise3 = random.nextInt(exercises.length);
        Button button1 = findViewById(R.id.Exercise1);
        Button button2 = findViewById(R.id.Exercise2);
        Button button3 = findViewById(R.id.Exercise3);
        healthystring.append(String.valueOf(size1));
        healthystring.append(" ");
        healthystring.append(exercises[exercise1]);
        button1.setText(healthystring.toString());
        healthystring.setLength(0);

        //Set up button 2
        healthystring.append(String.valueOf(size2));
        healthystring.append(" ");
        healthystring.append(exercises[exercise2]);
        button2.setText(healthystring.toString());
        healthystring.setLength(0);

        //Set up button 3
        healthystring.append(String.valueOf(size3));
        healthystring.append(" ");
        healthystring.append(exercises[exercise3]);
        button3.setText(healthystring.toString());
        healthystring.setLength(0);
    }

    //pre: An exercise button is clicked
    //post: A toast with an encouraging message is displayed,
    //and then the button is reset with a new exercise or
    //healthy activity
    public void clickExercise(View view) {
        //Make an encouraging toast
        Toast.makeText(this, R.string.exercise_cheer, Toast.LENGTH_SHORT).show();
        String[] exercises = getResources().getStringArray(R.array.exercise_types);
        //Reset button 1
        if (view.getId() == R.id.Exercise1) {
            int size = random.nextInt(10);
            if (size == 0) size++;
            int exercise = random.nextInt(exercises.length);
            Button button = findViewById(R.id.Exercise1);
            healthystring.append(String.valueOf(size));
            healthystring.append(" ");
            healthystring.append(exercises[exercise]);
            button.setText(healthystring.toString());
            healthystring.setLength(0);
        }
        //Reset button 2
        else if (view.getId() == R.id.Exercise2) {
            int size = random.nextInt(10);
            if (size == 0) size++;
            int exercise = random.nextInt(exercises.length);
            Button button = findViewById(R.id.Exercise2);
            healthystring.append(String.valueOf(size));
            healthystring.append(" ");
            healthystring.append(exercises[exercise]);
            button.setText(healthystring.toString());
            healthystring.setLength(0);
        }
        //Reset button 3
        else {
            int size = random.nextInt(10);
            if (size == 0) size++;
            int exercise = random.nextInt(exercises.length);
            Button button = findViewById(R.id.Exercise3);
            healthystring.append(String.valueOf(size));
            healthystring.append(" ");
            healthystring.append(exercises[exercise]);
            button.setText(healthystring.toString());
            healthystring.setLength(0);
        }
    }

    //pre: The encouragement distraction is chosen
    //post: The encouragement distraction is begun, which
    //features random encouraging quotes and sayings
    private void EncourageLayout() {
        setContentView(R.layout.activity_encourage);
        //Choose a random encouraging text and display it
        String[] words = getResources().getStringArray(R.array.encouragements);
        int rand = random.nextInt(words.length);
        TextView text = findViewById(R.id.EncourageText);
        text.setText(words[rand]);
    }
}
