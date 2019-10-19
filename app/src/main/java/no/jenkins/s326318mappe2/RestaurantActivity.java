package no.jenkins.s326318mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import no.jenkins.s326318mappe2.classes.Restaurant;

public class RestaurantActivity extends AppCompatActivity {
    private Restaurant bres;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        if(getIntent().getExtras() != null){
            Bundle b = getIntent().getExtras();
            Object o = b.getSerializable("object");
            if(o instanceof Restaurant){
                bres = (Restaurant) o;
            }
        }
        if(bres != null){
            loadBres();
        }
    }

    private void loadBres(){
        // populere bres i textview
    }

    private void saveAndReturn(){
        setResult(Activity.RESULT_OK);
        finish();
    }

}
