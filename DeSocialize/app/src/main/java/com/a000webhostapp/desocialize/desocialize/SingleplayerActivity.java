package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.java.Result;
import com.a000webhostapp.desocialize.desocialize.service.PhoneUnlockedReceiver;

import java.util.Calendar;

public class SingleplayerActivity extends AppCompatActivity {

    Result lastResult = Result.LOSS;
    View resultOverlay;
    View timeRemainingView;
    TextView resultTV;
    TextView timeTV;


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
        timeTV = findViewById(R.id.timeRemainingTV);
        timeRemainingView = findViewById(R.id.timeRemainingLayout);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });

        findViewById(R.id.nav_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    void back(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    /**
     * Displays win screen when user wins
     */
    private void win(){
        if(lastResult.equals(Result.LOSS)){
            resultTV.setText(R.string.youWin);
            resultOverlay.setBackgroundColor(getResources().getColor(R.color.colorWin));
            timeRemainingView.setVisibility(View.INVISIBLE);
        }

        lastResult = Result.WIN;
        findViewById(R.id.resultOverlay).setVisibility(View.VISIBLE);
    }

    /**
     * Displays lose screen when user loses
     * @param timeRemaining how much time was remaining for user to win
     */
    private void loss(long timeRemaining){
        if(lastResult.equals(Result.WIN)){
            resultTV.setText(R.string.youLost);
            resultOverlay.setBackgroundColor(getResources().getColor(R.color.colorLoss));
            timeRemainingView.setVisibility(View.VISIBLE);
        }

        timeTV.setText(printTime(timeRemaining));

        lastResult = Result.LOSS;
        findViewById(R.id.resultOverlay).setVisibility(View.VISIBLE);
    }

    /**
     * Converts time in miliseconds into String
     * @param time time in miliseconds
     * @return String in form of Xm Ys
     */
    private String printTime(long time){
        return (((time/60000 > 0)?((time/60000)+"m "):(""))+((time/1000)%60)+"s");
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
            long timeRemaining = finalTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            if(timeRemaining > 0){
                loss(timeRemaining);
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

