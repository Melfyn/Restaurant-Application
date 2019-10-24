package no.jenkins.s326318mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import no.jenkins.s326318mappe2.adapter.RestaurantAdapter;
import no.jenkins.s326318mappe2.adapter.RestaurantSpinnerAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

import static java.lang.Long.parseLong;

public class OrderActivity extends AppCompatActivity{
    private RestaurantOrder bResOrder;
    private FriendsInOrder bFriendsInOrder;
    EditText inputDate;
    EditText inputTime;
    Spinner inputRestaurant;
    private int id;

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
            View bf = findViewById(R.id.add_friends_order);
            bf.setVisibility(View.GONE);
        } else {
            bResOrder = new RestaurantOrder();
            bFriendsInOrder = new FriendsInOrder();
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
        RestaurantSpinnerAdapter adapter = new RestaurantSpinnerAdapter(getApplicationContext(), items);
        Spinner resSpinner = findViewById(R.id.restaurant_spinner);
        resSpinner.setAdapter(adapter);

        resSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant res = (Restaurant) adapterView.getSelectedItem();

                  id = (int) (long) res.get_ID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void startKeyListeners(){
        findViewById(R.id.add_order).setOnClickListener(view -> addOrder());
        findViewById(R.id.add_friends_order).setOnClickListener(view -> addFriends());
        findViewById(R.id.delete_order).setOnClickListener(view -> deleteOrder());
    }

    public void addFriends(){
        Intent intent=new Intent(this,AddFriendsInOrder.class);
        startActivity(intent);
    }

    public void addOrder(){
        bResOrder.setDate(inputDate.getText().toString());
        bResOrder.setTime(inputTime.getText().toString());
        bResOrder.setRestaurant_id(id);

        int testTwo = 2;


        //bFriendsInOrder.setOrder_ID(1);
        bFriendsInOrder.setFriend_ID(testTwo);


        db.addRestaurantOrder(bResOrder,bFriendsInOrder);
        Log.d("Legg inn: ", "legger til bestilling");
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void deleteOrder(){
        List<FriendsInOrder> items = db.getFriendsInOrder();
        for(int i = 0; i < items.size(); i++) {
            db.deleteFriendsInOrder(bResOrder.get_ID());
        }

        db.deleteOrder(bResOrder.get_ID());
        Log.d("Delete", "Delete order");
        setResult(Activity.RESULT_OK);
        finish();
    }



}
