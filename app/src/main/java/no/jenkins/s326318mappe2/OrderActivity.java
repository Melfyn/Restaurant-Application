package no.jenkins.s326318mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.adapter.RestaurantAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

import static java.lang.Long.parseLong;

public class OrderActivity extends AppCompatActivity{
    private RestaurantOrder bResOrder;
    private RestaurantOrder bRes;
    EditText inputDate;
    EditText inputTime;
    Spinner inputRestaurant;
    private Restaurant storedRestaurant;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        inputDate = (EditText) findViewById(R.id.o_date);
        inputTime = (EditText) findViewById(R.id.o_time);
        inputRestaurant = (Spinner) findViewById(R.id.restaurant_spinner);
        db = new DBHandler(this);

        if(getIntent().getExtras() != null){
            Bundle b = getIntent().getExtras();
            Object o = b.getSerializable("object");
            if(o instanceof RestaurantOrder){
                bResOrder = (RestaurantOrder) o;
            }
        }

        if(bResOrder != null){
            loadBResOrder();
            View b = findViewById(R.id.add_order);
            b.setVisibility(View.GONE);
        } else {
            View b = findViewById(R.id.delete_order);
            b.setVisibility(View.GONE);
        }
        startKeyListeners();
        loadRestaurant();
    }

    public void loadBResOrder(){
        inputDate.setText(bResOrder.getTime());
        inputTime.setText(bResOrder.getDate());
    }

    private void loadRestaurant(){
        ArrayList<Restaurant> items = db.getRestaurants();
        RestaurantAdapter adapter = new RestaurantAdapter(getApplicationContext(), items);
        Spinner resSpinner = findViewById(R.id.restaurant_spinner);
        resSpinner.setAdapter(adapter);

        resSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant res = (Restaurant) adapterView.getSelectedItem();
                bResOrder = new RestaurantOrder();
                int id = (int) (long) res.get_ID();
                bResOrder.setRestaurant_id(id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void startKeyListeners(){
        findViewById(R.id.add_order).setOnClickListener(view -> addOrder());
        findViewById(R.id.delete_order).setOnClickListener(view -> deleteOrder());
    }

    public void addOrder(){
        bResOrder.setDate(inputDate.getText().toString());
        bResOrder.setTime(inputTime.getText().toString());
        int testRestaurant = 1;

        int testOne = 1;
        int testTwo = 2;
        Log.d("testrestaurant", testRestaurant+" ");
     //   RestaurantOrder resOrder = new RestaurantOrder(inputDate.getText().toString(), inputTime.getText().toString(), testRestaurant);
        FriendsInOrder friendsOrder = new FriendsInOrder(testOne,testTwo);
        db.addRestaurantOrder(bResOrder,friendsOrder);
        Log.d("Legg inn: ", "legger til bestilling");
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void deleteOrder(){
        db.deleteFriend(bResOrder.get_ID());
        Log.d("Delete", "Delete friend");
        setResult(Activity.RESULT_OK);
        finish();
    }
}
