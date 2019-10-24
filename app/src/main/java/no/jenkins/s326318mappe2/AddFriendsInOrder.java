package no.jenkins.s326318mappe2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.adapter.FriendAdapter;
import no.jenkins.s326318mappe2.adapter.FriendsInOrderAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;

public class AddFriendsInOrder extends AppCompatActivity {
    private ArrayList<Friend> friends;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_in_order);

        db = new DBHandler(this);

        loadFriends();
        Log.d("Friends array", logFriends());
    }

    public void loadFriends(){
        friends = db.getFriends();
        // initialiserer attendingvariablene

        for(Friend friend : friends){
           if(friend.getAttending() == null){
               friend.setAttending(false);
           }
        }

        FriendsInOrderAdapter adapter = new FriendsInOrderAdapter(getApplicationContext(), friends);
        ListView friendView = findViewById(R.id.friends_in_order_listview);
        friendView.setAdapter(adapter);

    }

    public String logFriends(){
        String array = "";

        for(Friend friend : friends){
            array+=" "+friend.get_ID();
            array+=" "+friend.getName();
            array+=" "+friend.getPhoneNumber();
            array+=" "+friend.getAttending();
        }

        return array;
    }


}
