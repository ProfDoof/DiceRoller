package com.example.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageViewDice;
    private TextView textViewDiceValue;
    private TextView textViewCriticalValue;
    private MediaPlayer mediaPlayer;
    private Random rnJesus = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewDice = findViewById(R.id.diceImage);
        textViewDiceValue = findViewById(R.id.diceValueTextView);
        textViewCriticalValue = findViewById(R.id.criticalValueTextView);
        mediaPlayer = MediaPlayer.create(this, R.raw.dice_roll_sound_trimmed);

        imageViewDice.setSoundEffectsEnabled(false);
        imageViewDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        // Create Dice Rolling Sound player and if any sound is playing stop it.
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.dice_roll_sound_trimmed);

        // Create RotateAnimations for "rolling"
        RotateAnimation rotateDice = new RotateAnimation(0, 5760, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        RotateAnimation rotateValue = new RotateAnimation(0, 5760, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        rotateValue.setInterpolator(new LinearInterpolator());
        rotateValue.setDuration(1350);
        rotateDice.setDuration(1350);
        rotateDice.setInterpolator(new LinearInterpolator());

        // Start rolling the dice and start the sound of the rolling.
        imageViewDice.startAnimation(rotateDice);
        textViewDiceValue.startAnimation(rotateValue);
        mediaPlayer.start();

        // When the rolling sound completes get a new number and put it in the textview.
        // If it is a critical success or failure trigger a toast and a sound-bite
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                rollAndUpdateDice();
            }
        });
    }

    private void rollAndUpdateDice() {
        int randomNumberForDice = rnJesus.nextInt(20)+1;
        textViewDiceValue.setText(Integer.toString(randomNumberForDice));
        textViewCriticalValue.setText(" ");
        if (randomNumberForDice == 20) {
            mediaPlayer = MediaPlayer.create(this, R.raw.nerf_this);
            textViewCriticalValue.setText("CRITICAL SUCCESS!!!");
            mediaPlayer.start();
        }
        else if (randomNumberForDice == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sad_trombone);
            textViewCriticalValue.setText("CRITICAL FAILURE!!!!");
            mediaPlayer.start();
        }
    }
}
