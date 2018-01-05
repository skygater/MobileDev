package com.a000webhostapp.desocialize.desocialize;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SingleplayerActivity extends AppCompatActivity {
    Result lastResult = Result.LOSS;
    View resultOverlay;
    TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
        Resources resources = getResources();

        // setup phone unlock receiver
        PhoneUnlockedReceiver.register(this, new SPUnlock());


        // setup numberPicker
        NumberPicker numberPicker = findViewById(R.id.minutesPicker);
        numberPicker.setMinValue(resources.getInteger(R.integer.min_duration));
        numberPicker.setMaxValue(resources.getInteger(R.integer.max_duration));
        numberPicker.setValue(resources.getInteger(R.integer.def_duration));


        // setup win loss screen
        resultOverlay = findViewById(R.id.resultOverlay);
        resultTV = findViewById(R.id.resultTV);
        ImageButton imageButton = findViewById(R.id.replayBT);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
    }

    /**
     * Displays win screen when user wins
     */
    private void win(){
        //Toast.makeText(this, "good", Toast.LENGTH_SHORT).show();
        if(lastResult.equals(Result.LOSS)){
            resultTV.setText(R.string.youWin);
            resultOverlay.setBackgroundColor(getResources().getColor(R.color.colorWin));
        }

        lastResult = Result.WIN;
        findViewById(R.id.resultOverlay).setVisibility(View.VISIBLE);
    }

    /**
     * Displays lose screen when user loses
     */
    private void loss(){
        //Toast.makeText(this, "unlocked too early", Toast.LENGTH_SHORT).show();
        if(lastResult.equals(Result.WIN)){
            resultTV.setText(R.string.youLost);
            resultOverlay.setBackgroundColor(getResources().getColor(R.color.colorLoss));
        }

        lastResult = Result.LOSS;
        findViewById(R.id.resultOverlay).setVisibility(View.VISIBLE);
    }

    /**
     * Makes win / lose screen invisible again when user clicks on "play again" button
     */
    private void playAgain(){
        findViewById(R.id.resultOverlay).setVisibility(View.GONE);
    }

    /**
     * This class listens for screen unlock event and starts methods win / loss accordingly
     * depending on if there is still time left or not.
     */
    private class SPUnlock extends PhoneUnlockedReceiver{
        /** start time + duration = final time
         * tells us after what time the user won */
        Calendar finalTime;

        @Override
        public void screenUnlocked() {
            if(finalTime.after(Calendar.getInstance())){
                loss();
            }
            else{
                win();
            }
        }

        @Override
        public void screenLocked() {

            NumberPicker numberPicker = (NumberPicker)findViewById(R.id.minutesPicker);
            int duration = numberPicker.getValue();

            finalTime = Calendar.getInstance();
            finalTime.add(Calendar.MINUTE, duration);
        }
    }
}
