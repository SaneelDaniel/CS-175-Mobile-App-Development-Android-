package edu.sjsu.android.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.w("MY_DEBUG_TAG", state);

            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                telephony.listen(new PhoneStateListener(){
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        Log.w("MY_DEBUG_TAG", incomingNumber);
                    }
                },PhoneStateListener.LISTEN_CALL_STATE);
            }
        }
    }
}