package com.fiveminutes.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Objects;

import br.com.goncalves.pugnotification.notification.PugNotification;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }


    private Runnable myTask = new Runnable() {
        public void run() {

//            NotificationCompat.Builder builder =
//                    new NotificationCompat.Builder(context)
//                            .setSmallIcon(R.drawable.ic_success)
//                            .setContentTitle("5 minutes over")
//                            .setContentText("This is a Notification");
//            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            Objects.requireNonNull(manager).notify(0, builder.build());

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent showIntent = new Intent(context, Home.class);
            final PendingIntent pIntent = PendingIntent.getActivity(context
                    , 0, showIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            PugNotification.with(context)
                    .load()
                    .title("Notifier")
                    .smallIcon(R.drawable.ic_success)
                    .autoCancel(true)
                    .largeIcon(R.drawable.ic_success)
                    .flags(Notification.FLAG_AUTO_CANCEL)
                    .bigTextStyle("Click to open app")
                    .click(pIntent)
                    .tag("1")
                    .simple()
                    .build();

            // Do something here
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}
