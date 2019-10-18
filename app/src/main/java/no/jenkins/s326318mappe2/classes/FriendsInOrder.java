package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;

public class FriendsInOrder implements Serializable {

    private Long o_ID;
    private Long f_ID;

    public FriendsInOrder(Long o_ID, Long f_ID) {
        this.o_ID = o_ID;
        this.f_ID = f_ID;
    }

    public Long getO_ID() { return o_ID; }

    public void setO_ID(Long o_ID) { this.o_ID = o_ID; }

    public Long getF_ID() { return f_ID; }

    public void setF_ID(Long f_ID) { this.f_ID = f_ID; }
}
