package com.fiveminutes.notifier;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.ArrayAdapter;

import java.util.Objects;

public class SampleBootReceiver extends BroadcastReceiver {
    SampleAlarmReceiver alarm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED"))
        {
            alarm  = new SampleAlarmReceiver();
            alarm.setAlarm(context);

            Intent background = new Intent(context, BackgroundService.class);
            context.startService(background);

        }
    }
}