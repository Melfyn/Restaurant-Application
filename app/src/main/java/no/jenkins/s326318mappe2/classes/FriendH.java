package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendH implements Serializable {
    private ArrayList<Friend> items;

    public FriendH(ArrayList<Friend> items) {
        this.items = items;
    }

    public ArrayList<Friend> getItems() {
        return items;
    }
}
