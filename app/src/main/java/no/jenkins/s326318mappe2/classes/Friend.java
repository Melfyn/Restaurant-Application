package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;

public class Friend implements Serializable {

    private Long _ID;
    private String name;
    private String phoneNumber;

    public Friend() {
    }

    public Friend(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Friend(Long _ID, String name, String phoneNumber) {
        this._ID = _ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
