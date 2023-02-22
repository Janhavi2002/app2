package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.util.Log;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    int h1,h2,m1,m2;

    BatteryReceiver battery;
    PowerReceiver connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);

        Fragment1 fragmentDemo = new Fragment1();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainerView, fragmentDemo);
        ft.commit();



        battery = new BatteryReceiver();
        registerReceiver(battery,new IntentFilter(Intent.ACTION_BATTERY_LOW));

        connected = new PowerReceiver();
        registerReceiver(connected,new IntentFilter(Intent.ACTION_POWER_CONNECTED));


        Intent Service = new Intent(MainActivity.this, serv.class);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                h1 = fragmentDemo.getHour1();
                m1 = fragmentDemo.getMin1();

                h2 = fragmentDemo.getHour2();
                m2 = fragmentDemo.getMin2();


                Service.putExtra("h1",String.valueOf(h1));
                Service.putExtra("h2",String.valueOf(h2));
                Service.putExtra("m1",String.valueOf(m1));
                Service.putExtra("m2",String.valueOf(m2));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.i("MainActivity","Service started");
                    startForegroundService(Service);
                }
                else{
                    Log.i("MainActivity","Incompatible Version");
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopService(Service);

            }
        });

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("hour1", h1);
        outState.putInt("hour2", h2);
        outState.putInt("min1", m1);
        outState.putInt("min2", m2);

    }


    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(battery);
        unregisterReceiver(connected);
    }


    }