package com.example.myapplication2;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class serv extends Service {
    boolean checkTime ;
    private boolean isRunning  = false;
    int h1,h2,m1,m2,hour,minute;

    Calendar now;

    MediaPlayer ring;

    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {

            try {
                timer();
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            stopForeground(true);
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        Looper mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        isRunning = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.i("Service", "Service Started");

        final String CHANNEL_ID = "Foreground Service";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID).
                setContentText("Foreground service is running")
                .setContentTitle("Foreground")
                .setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(101, builder.build());

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        h1 = Integer.parseInt(intent.getStringExtra("h1"));
        h2 = Integer.parseInt(intent.getStringExtra("h2"));
        m1 = Integer.parseInt(intent.getStringExtra("m1"));
        m2 = Integer.parseInt(intent.getStringExtra("m2"));

        return START_STICKY;
    }

    public void timer() throws InterruptedException {
        checkTime = true;




        ring= MediaPlayer.create(this,R.raw.ring);
        boolean done1 = false;
        boolean done2 = false;

        while(checkTime) {
            now = Calendar.getInstance();
            hour = now.get(Calendar.HOUR_OF_DAY);
            minute = now.get(Calendar.MINUTE);



            if (h1 == hour && m1 == minute && !done1) {
                done1 = true;
                Log.i("Service", "Alarm 1 Ringing");
                Toast.makeText(this, "Alarm 1 Ringing", Toast.LENGTH_LONG).show();
                ring.start();



            } else if (h2 == hour && m2 == minute && !done2) {
                done2 = true;
                Log.i("Service", "Alarm 2 Ringing");
                Toast.makeText(this, "Alarm 2 Ringing", Toast.LENGTH_LONG).show();
                ring.start();



            }
            else if(!isRunning){
                break;
            }

            else{
                Thread.sleep(10000);

            }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        ring.stop();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();


    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
