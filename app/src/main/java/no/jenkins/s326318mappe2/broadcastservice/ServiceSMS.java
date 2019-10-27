package no.jenkins.s326318mappe2.broadcastservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import no.jenkins.s326318mappe2.DBHandler;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

public class ServiceSMS extends Service {
    DBHandler db;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        SendSMS();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 37);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
       // alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);


        return super.onStartCommand(intent, flags, startId);
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
}
