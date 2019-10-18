package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;
import java.util.ArrayList;

import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.Restaurant;

public class RestaurantOrder implements Serializable {

    private long _ID;
    private String date;
    private String time;
    private Restaurant restaurant;
    private ArrayList<Friend> friends;
}
