package no.jenkins.s326318mappe2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import no.jenkins.s326318mappe2.adapter.FriendAdapter;
import no.jenkins.s326318mappe2.adapter.FriendsInOrderAdapter;
import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.FriendsInOrder;

public class AddFriendsInOrder extends AppCompatActivity {

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_in_order);

        db = new DBHandler(this);

        loadFriends();
    }

    public void loadFriends(){
        ArrayList<Friend> items = db.getFriends();
        FriendsInOrderAdapter adapter = new FriendsInOrderAdapter(getApplicationContext(), items);
        ListView friendView = findViewById(R.id.friends_in_order_listview);
        friendView.setAdapter(adapter);
    }
}
