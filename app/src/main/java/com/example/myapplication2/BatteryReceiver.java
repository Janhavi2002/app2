package com.example.myapplication2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent Service = new Intent(context, serv.class);

        if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction())){
            Toast.makeText(context, "Action: Low Battery", Toast.LENGTH_SHORT).show();
            Log.i("Broadcast", "Low Battery");
            context.stopService(Service);
        }


    }
}
