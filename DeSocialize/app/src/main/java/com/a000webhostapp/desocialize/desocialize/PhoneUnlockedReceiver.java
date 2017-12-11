package com.a000webhostapp.desocialize.desocialize;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
public class PhoneUnlockedReceiver extends BroadcastReceiver {
    Context mContext;
    public PhoneUnlockedReceiver(Context mContext){
        super();
        this.mContext = mContext;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isKeyguardSecure()) {

            //phone was unlocked, do stuff here
            Toast.makeText(mContext, "Unlocked", Toast.LENGTH_SHORT).show();
        }
    }
}
