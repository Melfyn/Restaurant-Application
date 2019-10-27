package no.jenkins.s326318mappe2.broadcastservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.app.Service;

import androidx.core.app.NotificationCompat;

import no.jenkins.s326318mappe2.R;

public class MyService extends Service {
    private String notificationTitle = "Påminnelse om restaurantavtale:";
    private String notificationText = "Sted og klokkeslett";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    /*
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
    */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, BroadcastResult.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this)
                .setContentTitle(notificationTitle) // custom title
                .setContentText(notificationText) // custom txt
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();
        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notifikasjon);
        return super.onStartCommand(intent, flags, startId);
    }

    public String generateNotificationText(){

        return "0";
    }
}
