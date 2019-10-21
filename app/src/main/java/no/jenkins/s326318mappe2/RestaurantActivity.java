package no.jenkins.s326318mappe2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import no.jenkins.s326318mappe2.classes.Restaurant;

public class RestaurantActivity extends AppCompatActivity {
    private Restaurant bres;
    EditText inputName;
    EditText inputPhone;
    EditText inputAdress;
    EditText inputType;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        inputName = (EditText) findViewById(R.id.r_name);
        inputPhone = (EditText) findViewById(R.id.r_ph_no);
        inputAdress = (EditText) findViewById(R.id.r_adress);
        inputType = (EditText) findViewById(R.id.r_type);
        db = new DBHandler(this);

        if(getIntent().getExtras() != null){
            Bundle b = getIntent().getExtras();
            Object o = b.getSerializable("object");
            if(o instanceof Restaurant){
                bres = (Restaurant) o;
            }
        }
        if(bres != null){
            loadBres();
            View b = findViewById(R.id.add_restaurant);
            b.setVisibility(View.GONE);
        }

        startKeyListeners();
    }

    private void loadBres(){
        // populere bres i textview
        inputName.setText(bres.getName());
        inputAdress.setText(bres.getAdress());
        inputPhone.setText(bres.getPhoneNumber());
        inputType.setText(bres.getType());
    }


    public void startKeyListeners() {
        findViewById(R.id.add_restaurant).setOnClickListener(view -> addOneRestaurant());
    }

    public void addOneRestaurant() {
        setResult(Activity.RESULT_OK);
        Restaurant res = new Restaurant(inputName.getText().toString(), inputAdress.getText().toString(), inputPhone.getText().toString(),inputType.getText().toString());
        db.addRestaurant(res);
        Log.d("Legg inn: ", "legger til kontakter");
        finish();
    }


}
