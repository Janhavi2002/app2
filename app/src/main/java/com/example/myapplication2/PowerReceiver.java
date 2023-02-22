package com.example.myapplication2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PowerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent Service = new Intent(context, serv.class);

        if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())){
            Toast.makeText(context, "Action: Power Connected", Toast.LENGTH_SHORT).show();
            Log.i("Broadcast", "Power Connected");
            context.stopService(Service);

        }


    }
}


