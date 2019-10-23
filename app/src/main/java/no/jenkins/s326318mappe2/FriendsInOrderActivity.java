package no.jenkins.s326318mappe2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import no.jenkins.s326318mappe2.classes.FriendsInOrder;

public class FriendsInOrderActivity extends AppCompatActivity {
    TextView utskrift;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsinorder);
        db = new DBHandler(this);
        utskrift = (TextView) findViewById(R.id.friends_in_order_view);
        utskrift.setText(visAlle());
    }

    public String visAlle() {
        String tekst = "";
        List<FriendsInOrder> friendsInOrders = db.getFriendsInOrder();
        for (FriendsInOrder friend : friendsInOrders) {
            tekst = tekst + "Order ID: " + friend.getOrder_ID()+ ", Friend ID: " +
                    friend.getFriend_ID() +"\n";
            Log.d("Navn: ", tekst);
        }
        return tekst;
    }

}