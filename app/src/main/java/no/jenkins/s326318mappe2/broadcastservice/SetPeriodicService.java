package no.jenkins.s326318mappe2.broadcastservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;

public class SetPeriodicService extends Service {

    Date currentTime = Calendar.getInstance().getTime();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        java.util.Calendar cal = Calendar.getInstance();
        Intent i = new Intent(this, MyService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);

        // Set the alarm to start at approximately the time set in set HOUR_OF_DAY and MINUTE
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 20);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);




        return super.onStartCommand(intent, flags, startId);
    }
}
