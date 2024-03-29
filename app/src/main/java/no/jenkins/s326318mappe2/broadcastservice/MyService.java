package no.jenkins.s326318mappe2.broadcastservice;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import android.app.Service;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

import no.jenkins.s326318mappe2.DBHandler;
import no.jenkins.s326318mappe2.MainActivity;
import no.jenkins.s326318mappe2.R;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

public class MyService extends Service {
    private String notificationTitle = "Påminnelse om restaurantavtale:";
    private String notificationText = "Sted og klokkeslett";
    int MY_PERMISSIONS_REQUEST_SEND_SMS;
    DBHandler db;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
     //   Toast.makeText(getApplicationContext(), "I MinService", Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, BroadcastResult.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
        Notification notifikasjon = new NotificationCompat.Builder(this)
                .setContentTitle(notificationTitle) // custom title
                .setContentText(generateNotificationText()) // custom txt
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).build();
        notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notifikasjon);
        /*
        // test. not sure if it works
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // get system time
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); // time from shared preferences
        String smsPreference =  sharedPreferences.getString("time_sms_service", "12:30");
        String[] preference_time = smsPreference.split(":");
        int hourPref = Integer.parseInt(preference_time[0]);
        int minutePref = Integer.parseInt(preference_time[1]); // converted to ints

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourPref); //
        calendar.set(Calendar.MINUTE, minutePref);

        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        */
        SendSMS();


        //   Boolean smsSetting = sharedPreferences.getBoolean("sms_service_boolean", false);



        return super.onStartCommand(intent, flags, startId);
    }

    public String generateNotificationText(){

        db = new DBHandler(this);

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1; // Month starts on zero.
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String dato = day+"."+month+"."+year;

        Log.d("DatoStreng", dato);

        String resOrder = "";

        ArrayList<RestaurantOrder> orders = db.getRestaurantOrder();
        for (RestaurantOrder order : orders){
            if(order.getDate().equals(dato)){
                resOrder+= db.findRestaurant(order.getRestaurant_id()).getName()+ " kl: "+order.getTime();
            }
        }

        return resOrder;
    }

    public void SendSMS() {
        Toast.makeText(getApplicationContext(),"I BroadcastReceiver", Toast.LENGTH_SHORT).show();
        // get current time
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        Log.d("nåværende tidspunkt",hour+" "+minute);
        String currentTime = hour+":"+minute;

        // get time from shared preferences


        // get boolean from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean smsPreference =  sharedPreferences.getBoolean("sms_service_boolean", false);

        ArrayList<RestaurantOrder> orders = db.getRestaurantOrder();


        String message = generateNotificationText();
      //  String phoneNo= "234552";
        ArrayList<Friend> friendsphonenumbers = getFriendsInOrder_ph_no();
        for(Friend friends_pho : friendsphonenumbers){
            String phoneNo = friends_pho.getPhoneNumber();
            SmsManager smsMan = SmsManager.getDefault();smsMan.sendTextMessage(phoneNo, null, message, null, null);
        }
    }

    // fetch phone numbers from todays order. put them in array for SendSMS service
    public ArrayList<Friend> getFriendsInOrder_ph_no(){

        db = new DBHandler(this);

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1; // Month starts on zero.
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String dato = day+"."+month+"."+year;

        Log.d("DatoStreng", dato);

        ArrayList<FriendsInOrder> friendsInOrder = new ArrayList<FriendsInOrder>();
        ArrayList<Friend> friendPhoneNumbers = new ArrayList<Friend>();

        ArrayList<RestaurantOrder> orders = db.getRestaurantOrder();

        for (RestaurantOrder order : orders){
            if(order.getDate().equals(dato)){
                friendsInOrder = db.getFriendsInOrder(order.get_ID());
            }
        }

        for(FriendsInOrder friends : friendsInOrder){
            Friend friend = new Friend(db.findFriendNumber(friends.getFriend_ID()));
            friendPhoneNumbers.add(friend);
        }

        return friendPhoneNumbers;
    }

}
