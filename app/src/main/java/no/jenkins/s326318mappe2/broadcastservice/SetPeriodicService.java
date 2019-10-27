package no.jenkins.s326318mappe2.broadcastservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;

import no.jenkins.s326318mappe2.DBHandler;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

public class SetPeriodicService extends Service {

    DBHandler db;

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

        if(compareDates() == true) {
            // Set the alarm to start at approximately the time set in set HOUR_OF_DAY and MINUTE
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 37);
            AlarmManager alarm =
                    (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // method to check if there are any orders at current date
    public Boolean compareDates(){
        db = new DBHandler(this);

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1; // Month starts on zero.
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String dato = day+"."+month+"."+year;

        Log.d("DatoStreng", dato);

        ArrayList<RestaurantOrder> orders = db.getRestaurantOrder();
        for (RestaurantOrder order : orders){
            Log.d("Datoer fra db",order.getDate());
            if(order.getDate().equals(dato)){
                return true;
            }
        }
        return false;
    }
}


