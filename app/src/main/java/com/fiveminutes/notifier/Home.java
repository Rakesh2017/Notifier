package com.fiveminutes.notifier;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity {

    private SampleAlarmReceiver alarm;

    private ArrayList<String> times;
    private ArrayAdapter mAdapter;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            displayTime();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView start = findViewById(R.id.start);
        TextView stop = findViewById(R.id.stop);


        alarm = new SampleAlarmReceiver();
        alarm.setAlarm(this);
        times = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        String time = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        times.add(time);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, times);



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm.setAlarm(Home.this);
                Toast.makeText(Home.this, "started", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm.cancelAlarm(Home.this);
                Toast.makeText(Home.this, "stopped", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("display_time"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    public void displayTime() {
        Calendar c = Calendar.getInstance();
        String time = c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        times.add(time);
        mAdapter.notifyDataSetChanged();
    }

}