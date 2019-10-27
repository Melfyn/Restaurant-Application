package no.jenkins.s326318mappe2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import no.jenkins.s326318mappe2.adapter.FriendAdapter;
import no.jenkins.s326318mappe2.adapter.RestaurantAdapter;
import no.jenkins.s326318mappe2.adapter.RestaurantOrderAdapter;
import no.jenkins.s326318mappe2.broadcastservice.MyService;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;


public class MainActivity extends AppCompatActivity {

    DBHandler db;

    int ac_restaurant = 340;  // ac is short for activity code
    int ac_order = 211;
    int ac_friend = 666;

    int MY_PERMISSIONS_REQUEST_SEND_SMS;
    int ac_permission = 342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(); // start broadcast service

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_orders:
                        showOrders();
                        break;
                    case R.id.menu_friends:
                        showFriends();
                        break;
                    case R.id.menu_restaurant:
                        showRestaurants();
                        break;
                    case R.id.menu_settings:
                        showSettings();
                        break;
               //     case R.id.menu_friendsinorder:
               //         showFriendsInOrder();

                }
                return true;
            }
        });

        db = new DBHandler(this);

        showOrders(); // show orders when app starts


        String smsPermission = android.Manifest.permission.SEND_SMS;
        MY_PERMISSIONS_REQUEST_SEND_SMS = ContextCompat.checkSelfPermission(this, smsPermission);
        if (MY_PERMISSIONS_REQUEST_SEND_SMS != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{smsPermission}, ac_permission);
        }

    //    SendSMS();
    }

    public void showFriends(){
        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, FriendActivity.class);
            startActivityForResult(intent, ac_friend);
        });
        loadFriends();
        ListView friendView = findViewById(R.id.main_listview);
        friendView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getAdapter() instanceof FriendAdapter){
                    Friend friend = (Friend) ((FriendAdapter) adapterView.getAdapter()).getItem(i);
                    Bundle b = new Bundle();
                    b.putSerializable("object",friend);
                    Intent intent = new Intent(MainActivity.this, FriendActivity.class);
                    intent.putExtras(b);
                    startActivityForResult(intent, ac_friend);
                }
            }
        });
    }

    public void loadFriends(){
        ArrayList<Friend> items = db.getFriends();
        FriendAdapter adapter = new FriendAdapter(getApplicationContext(), items);
        ListView friendView = findViewById(R.id.main_listview);
        friendView.setAdapter(adapter);
    }

    public void showRestaurants(){
        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, RestaurantActivity.class);
            startActivityForResult(intent, ac_restaurant);
        });
        loadRestaurant();
        ListView resView = findViewById(R.id.main_listview);
        resView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getAdapter() instanceof RestaurantAdapter){
                    Restaurant res = (Restaurant) ((RestaurantAdapter) adapterView.getAdapter()).getItem(i);
                    Bundle b = new Bundle();
                    b.putSerializable("object",res);
                    Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                    intent.putExtras(b);
                    startActivityForResult(intent, ac_restaurant);
                }
            }
        });
    }

    private void loadRestaurant(){
        ArrayList<Restaurant> items = db.getRestaurants();
        RestaurantAdapter adapter = new RestaurantAdapter(getApplicationContext(), items);
        ListView resView = findViewById(R.id.main_listview);
        resView.setAdapter(adapter);
    }

    public void showOrders() {
        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivityForResult(intent, ac_order);
        });
        loadOrder();
        ListView resView = findViewById(R.id.main_listview);
        resView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getAdapter() instanceof RestaurantOrderAdapter){
                    RestaurantOrder resOrder = (RestaurantOrder) ((RestaurantOrderAdapter) adapterView.getAdapter()).getItem(i);
                    Log.d("ordre:",resOrder.get_ID()+"");
                    Bundle b = new Bundle();
                    b.putSerializable("object",resOrder);
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtras(b);
                    startActivityForResult(intent, ac_order);
                }
            }
        });
    }

    public void loadOrder(){
        ArrayList<RestaurantOrder> items = db.getRestaurantOrder();
        for(RestaurantOrder order : items) {
            Restaurant res = db.findRestaurant(order.getRestaurant_id());
            order.setRestaurant(res);
        }
        RestaurantOrderAdapter adapter = new RestaurantOrderAdapter(getApplicationContext(), items);
        ListView resView = findViewById(R.id.main_listview);
        resView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == ac_restaurant)
            {
                loadRestaurant();
            }
            if(requestCode == ac_friend){
                loadFriends();
            }
            if(requestCode == ac_order){
                loadOrder();
            }
        }
    }

    public void showSettings(){
        Intent intent=new Intent(this,SetPreferencesActivity.class);
        startActivity(intent);
    }
    //test method to display that friends in a order is inserted correctly
    public void showFriendsInOrder(){
        Intent intent=new Intent(this,FriendsInOrderActivity.class);
        startActivity(intent);
    }

    // start broadcast service. might need to be placed in setpreferenceactivity
    public void startService() {
        Intent intent = new Intent();
        intent.setAction("no.jenkins.s326318mappe2.broadcast");
        sendBroadcast(intent);
    }

    public void stopPeriodic(View v) {
        Intent i = new Intent(this, MyService.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarm!= null) {
            alarm.cancel(pintent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ac_permission)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, getString(R.string.sms_permitted), Toast.LENGTH_LONG).show();
            }
            else
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(getString(R.string.sms_permission_title));
                alertDialogBuilder.setMessage(getString(R.string.sms_permission_txt));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setNeutralButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss());
                alertDialogBuilder.create().show();
            }
        }
    }

}
