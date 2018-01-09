package com.a000webhostapp.desocialize.desocialize.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by djordjekalezic on 09/01/2018.
 */

public abstract class PhoneUnlockedReceiver extends BroadcastReceiver {

    /**
     * Registers the receiver for all important events - Necessary for this class to work!
     * @param context current context
     * @param phoneUnlockedReceiver instance of receiver to be registered
     */
    public static void register(Context context, PhoneUnlockedReceiver phoneUnlockedReceiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        context.registerReceiver(phoneUnlockedReceiver, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            screenUnlocked();
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            screenLocked();
        }
    }

    /**
     * Started when user unlocks screen
     */
    public abstract void screenUnlocked();

    /**
     * Started when user locks screen
     */
    public abstract void screenLocked();
}
