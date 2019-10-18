package no.jenkins.s326318mappe2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.adapter.RestaurantAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.Restaurant;


public class MainActivity extends AppCompatActivity {

    DBHandler db;

    int ac_restaurant = 340;  // ac is short for activity code
    int ac_order = 211;
    int ac_friend = 666;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                }
                return true;
            }
        });

        db = new DBHandler(this);
    }

    public void showFriends(){
        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, FriendActivity.class);
            startActivityForResult(intent, ac_friend);
        });
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
        ArrayList<Restaurant> items = new ArrayList<>(); // Replace new arraylist with arraylist from database
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == ac_restaurant)
            {
                loadRestaurant();
            }
            // de andre ac greiene
        }
    }
}
