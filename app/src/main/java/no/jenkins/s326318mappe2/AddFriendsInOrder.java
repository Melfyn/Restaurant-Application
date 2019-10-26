package no.jenkins.s326318mappe2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

import no.jenkins.s326318mappe2.adapter.FriendAdapter;
import no.jenkins.s326318mappe2.adapter.FriendsInOrderAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendH;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;

public class AddFriendsInOrder extends AppCompatActivity implements Serializable{
    private ArrayList<Friend> friends;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_in_order);

        db = new DBHandler(this);

        startKeyListeners();

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

        findViewById(R.id.friend_checkbox);
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

    public void startKeyListeners() {
        findViewById(R.id.add_friends_action_btn).setOnClickListener(view -> addCheckedFriends());
    }

    // Bundle arraylist
    public void addCheckedFriends() {
        Bundle b = new Bundle();
        b.putSerializable("AttendingFriends", new FriendH(friends));
        Intent intent = new Intent();
        intent.putExtras(b);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

}
