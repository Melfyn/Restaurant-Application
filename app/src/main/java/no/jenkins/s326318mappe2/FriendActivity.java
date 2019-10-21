package no.jenkins.s326318mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.Restaurant;

public class FriendActivity extends AppCompatActivity {
    private Friend bfriend;
    EditText inputName;
    EditText inputPhone;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        inputName = (EditText) findViewById(R.id.r_name);
        inputPhone = (EditText) findViewById(R.id.r_ph_no);
        db = new DBHandler(this);

        if(getIntent().getExtras() != null){
            Bundle b = getIntent().getExtras();
            Object o = b.getSerializable("object");
            if(o instanceof Friend){
                bfriend = (Friend) o;
            }
        }
        if(bfriend != null){
            loadBfriend();
        }

        startKeyListeners();
    }

    public void loadBfriend(){

    }

    public void startKeyListeners(){

    }
}
