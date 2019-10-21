package no.jenkins.s326318mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
        inputName = (EditText) findViewById(R.id.f_name);
        inputPhone = (EditText) findViewById(R.id.f_ph_no);
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
        inputName.setText(bfriend.getName());
        inputPhone.setText(bfriend.getPhoneNumber());
    }

    public void startKeyListeners(){
        findViewById(R.id.add_friend).setOnClickListener(view -> addFriend());
        findViewById(R.id.update_friend).setOnClickListener(view -> updateFriend());
        findViewById(R.id.delete_friend).setOnClickListener(view -> deleteFriend());
    }

    public void addFriend(){
        Friend friend = new Friend(inputName.getText().toString(), inputPhone.getText().toString());
        db.addFriend(friend);
        Log.d("Legg inn: ", "legger til kontakter");
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void updateFriend(){
        Friend friend = new Friend(bfriend.get_ID(), inputName.getText().toString(), inputPhone.getText().toString());
        db.updateFriend(friend);
        Log.d("Update: ", "Update friend");
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void deleteFriend(){
        db.deleteFriend(bfriend.get_ID());
        Log.d("Delete", "Delete friend");
        setResult(Activity.RESULT_OK);
        finish();
    }
}
