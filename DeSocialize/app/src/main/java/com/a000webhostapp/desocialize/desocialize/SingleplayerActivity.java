package com.a000webhostapp.desocialize.desocialize;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class SingleplayerActivity extends AppCompatActivity {
    final int DEF_DURATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        // setup phone unlock receiver
        registerReceiver(new PhoneUnlockedReceiver(DEF_DURATION), new IntentFilter("android.intent.action.USER_PRESENT"));
    }

    private void win(){
        Toast.makeText(this, "good", Toast.LENGTH_SHORT).show();
        playAgain();
    }

    private void loss(){
        Toast.makeText(this, "unlocked too early", Toast.LENGTH_SHORT).show();
        playAgain();
    }

    private void playAgain(){

    }

    /*
     * This class listens for screen unlock event and starts methods win / loss accordingly
     */
    public class PhoneUnlockedReceiver extends BroadcastReceiver {
        final Calendar finalTime;

        public PhoneUnlockedReceiver(int duration){
            super();
            finalTime = Calendar.getInstance();
            finalTime.add(Calendar.MINUTE, duration);
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
            if (!keyguardManager.isKeyguardSecure()) {
                if(finalTime.after(Calendar.getInstance())){
                    loss();
                }
                else{
                    win();
                }
            }
        }
    }
}
