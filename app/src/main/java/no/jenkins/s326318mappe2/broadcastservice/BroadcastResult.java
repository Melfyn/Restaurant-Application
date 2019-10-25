package no.jenkins.s326318mappe2.broadcastservice;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import no.jenkins.s326318mappe2.R;

public class BroadcastResult extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // burde enten sendes direkte til ordren eller til hovedmeyen i appen. denne funker ikke enda
    }
}
