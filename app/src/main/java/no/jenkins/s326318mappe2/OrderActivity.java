package no.jenkins.s326318mappe2;

import androidx.annotation.Nullable;
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
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import no.jenkins.s326318mappe2.adapter.RestaurantAdapter;
import no.jenkins.s326318mappe2.adapter.RestaurantSpinnerAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendH;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;
import no.jenkins.s326318mappe2.classes.Restaurant;
import no.jenkins.s326318mappe2.classes.RestaurantOrder;

import static java.lang.Long.parseLong;

public class OrderActivity extends AppCompatActivity {
    private RestaurantOrder bResOrder;
    private FriendsInOrder bFriendsInOrder;
    private ArrayList<Friend> attendingFriendsList;
    EditText inputDate;
    EditText inputTime;
    TextView attendingFriendsView;
    Spinner inputRestaurant;
    private int id;

    int ac_friends = 266;

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
        showAttendingFriends();
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
                  // store restaurant id from spinner
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
        startActivityForResult(intent,ac_friends);
    }

    public void addOrder(){
        bResOrder.setDate(inputDate.getText().toString());
        bResOrder.setTime(inputTime.getText().toString());
        bResOrder.setRestaurant_id(id);

        // note to self: add restaurant order before inserting friends in order to retrieve correct order id from getLastId method
        db.addRestaurantOrder(bResOrder);

        int order_id = db.getLastID();

        ArrayList<FriendsInOrder> friendsInOrderList = new ArrayList<FriendsInOrder>();
        for(Friend friend : attendingFriendsList){
            if(friend.getAttending() != false){
                FriendsInOrder oneFriend = new FriendsInOrder();
                int friend_id = (int) (long) friend.get_ID();
                oneFriend.setOrder_ID(order_id);
                oneFriend.setFriend_ID(friend_id);
                friendsInOrderList.add(oneFriend);
            }
        }


        for(FriendsInOrder friendInOrder : friendsInOrderList){
            db.addFriendsInOrder(friendInOrder);
        }

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

    // Method for populating Attending friends textfield
    public void showAttendingFriends(){
        attendingFriendsView = findViewById(R.id.attending_friends_txtview);

        String attendingFriends = " ";
        if(attendingFriendsList != null){
            for(Friend friend : attendingFriendsList){
                if(friend.getAttending() != false){
                    attendingFriends+= friend.getName()+" ";
                    attendingFriends+= friend.getPhoneNumber()+" ";
                    attendingFriends+= friend.getAttending()+". ";
                }
            }
        }

        attendingFriendsView.setText(attendingFriends);
        Log.d("Venner som deltar array", attendingFriends);
    }

    // Method for logging friends that will be inserted in help table FriendsInOrder
    public String selectedFriendsInOrderArray(){
        ArrayList<FriendsInOrder> friendsInOrderList = new ArrayList<FriendsInOrder>();
        for(Friend friend : attendingFriendsList){
            if(friend.getAttending() != false){
                FriendsInOrder oneFriend = new FriendsInOrder();
                int friend_id = (int) (long) friend.get_ID();
                oneFriend.setOrder_ID(id);
                oneFriend.setFriend_ID(friend_id);
                friendsInOrderList.add(oneFriend);
            }
        }

        String out = " ";
        for(FriendsInOrder friendsInOrder : friendsInOrderList){
            out+="(( ORDER ID"+friendsInOrder.getOrder_ID()+" ";
            out+=" FRIEND ID"+friendsInOrder.getFriend_ID()+" ))";
        }
        return out;
    }

    // retrieve bundled arraylist from AddfriendsInOrder
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ac_friends && data != null) {
                FriendH fh = (FriendH) data.getSerializableExtra("AttendingFriends");
                if(fh != null && fh.getItems() != null)
                    attendingFriendsList = fh.getItems();
                    showAttendingFriends();
                    Log.d("Friends in order array",selectedFriendsInOrderArray());
                    Log.d("onActivityResult", "du er i activity result");
            }
        }
    }
}
