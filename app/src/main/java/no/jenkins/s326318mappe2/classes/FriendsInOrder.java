package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;

public class FriendsInOrder implements Serializable {

    private Long o_ID;
    private Long f_ID;
    private int order_ID;
    private int friend_ID;

    public FriendsInOrder() {
    }
    /*
    public FriendsInOrder(Long o_ID, Long f_ID) {
        this.o_ID = o_ID;
        this.f_ID = f_ID;
    }

     */

    public FriendsInOrder(int order_ID, int friend_ID) {
        this.order_ID = order_ID;
        this.friend_ID = friend_ID;
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public int getFriend_ID() {
        return friend_ID;
    }

    public void setFriend_ID(int friend_ID) {
        this.friend_ID = friend_ID;
    }

    public Long getO_ID() { return o_ID; }

    public void setO_ID(Long o_ID) { this.o_ID = o_ID; }

    public Long getF_ID() { return f_ID; }

    public void setF_ID(Long f_ID) { this.f_ID = f_ID; }
}
