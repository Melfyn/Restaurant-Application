package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;
import java.util.ArrayList;

import no.jenkins.s326318mappe2.classes.Friend;
import no.jenkins.s326318mappe2.classes.Restaurant;

public class RestaurantOrder implements Serializable {

    private Long _ID;
    private String date;
    private String time;
    private Restaurant restaurant;
    private int restaurant_id;
    private ArrayList<Friend> friends;

    public RestaurantOrder() {
    }

    public RestaurantOrder(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    /*
    public RestaurantOrder(String date, String time, Restaurant restaurant) {
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
    }
    */
    public RestaurantOrder(String date, String time, int restaurant_id) {
        this.date = date;
        this.time = time;
        this.restaurant_id = restaurant_id;
    }

    public RestaurantOrder(String date, String time, Restaurant restaurant, ArrayList<Friend> friends) {
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
        this.friends = friends;
    }

    public RestaurantOrder(Long _ID, String date, String time, Restaurant restaurant, ArrayList<Friend> friends) {
        this._ID = _ID;
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
        this.friends = friends;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Long get_ID() { return _ID; }

    public void set_ID(Long _ID) { this._ID = _ID; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public Restaurant getRestaurant() { return restaurant; }

    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public ArrayList<Friend> getFriends() { return friends; }

    public void setFriends(ArrayList<Friend> friends) { this.friends = friends; }
}
