package com.a000webhostapp.desocialize.desocialize;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SingleplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        // setup phone unlock receiver
        registerReceiver(new PhoneUnlockedReceiver(this), new IntentFilter("android.intent.action.USER_PRESENT"));
    }
}
