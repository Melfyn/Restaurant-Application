package no.jenkins.s326318mappe2.classes;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private Long _ID;
    private String name;
    private String adress;
    private String phoneNumber;
    private String type;

    public Restaurant() {
    }

    public Restaurant(String name, String adress, String phoneNumber, String type) {
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public Restaurant(Long _ID, String name, String adress, String phoneNumber, String type) {
        this._ID = _ID;
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.type = type;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
