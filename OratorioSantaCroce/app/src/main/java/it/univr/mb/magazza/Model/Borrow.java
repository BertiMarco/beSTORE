package it.univr.mb.magazza.Model;

import android.os.Build;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

public class Borrow implements Serializable {
    private User mUser;
    private HashSet<Item> items;
    private String description;
    private Date mDate;

    public Borrow(User user) {
        mUser = user;
        this.items = new HashSet<>();
    }

    public boolean addItem(Item i){
        return items.add(i);
    }

    public boolean removeItem(Item i){
        return items.remove(i);
    }

    public void setDescription(String s){
        description = s;
    }

    public void setDate() {
        mDate = new Date(System.currentTimeMillis());
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }

    public User getUser() {
        return mUser;
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return mDate;
    }

}
