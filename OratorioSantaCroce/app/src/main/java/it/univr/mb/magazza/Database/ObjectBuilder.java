package it.univr.mb.magazza.Database;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.ArrayList;

import it.univr.mb.magazza.Activity.LasciaActivity;
import it.univr.mb.magazza.Activity.PrendiActivity;
import it.univr.mb.magazza.Activity.MainFragments.AccessFragment;
import it.univr.mb.magazza.Activity.PrendiLasciaFragments.CausaleFragment;
import it.univr.mb.magazza.Model.Borrow;
import it.univr.mb.magazza.Model.Item;
import it.univr.mb.magazza.Model.User;

public class ObjectBuilder {
    private static final ObjectBuilder ourInstance = new ObjectBuilder();
    private DBInterface mDBInterface = new DBInterface();
    private User currentUser;
    private AccessFragment mAccessFragment;
    private Borrow mBorrow;
    private PrendiActivity mPrendiActivity;
    private LasciaActivity mLasciaActivity;
    private ArrayList<Item> mItemsToLeave;

    public static ObjectBuilder getInstance() {
        return ourInstance;
    }

    private ObjectBuilder() {
    }

    public void checkLogin(Context context, String imeiCode, AccessFragment owner) {
        mAccessFragment = owner;
        mDBInterface.isImeiRegistered(context, imeiCode);
    }


    public void loginAccepted(String nome, String cognome, String imei) {
        currentUser = new User(imei, nome, cognome);
        mAccessFragment.loginAccepted();
    }

    public void createBorrow() {
        mBorrow = new Borrow(currentUser);

    }

    public void createItemsToLeave() {
        mItemsToLeave = new ArrayList<>();
    }

    public void loginFailed() {
        mAccessFragment.loginFailed();
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setBorrowDescription(String s) {
        mBorrow.setDescription(s);
    }

    public void addItem(Item item) {
        mBorrow.addItem(item);
        //TODO-> aggiungere anche su db. Mezzo fatto ma va ridinito un tot
        //mDBInterface.addItem(item, currentUser, mBorrow.getDate(), mPrendiActivity);
    }

    public void buildBorrow(String itemId) {
        //TODO-> ko tecnico. sono finito. magari faccio un po' di grafica e vadao avanti poi.

    }

    public void getItem(String text, PrendiActivity activity) {
        mPrendiActivity = activity;
        mDBInterface.getItem(text, activity.getApplicationContext());
    }

    public void itemFound(String id, String nome, String brand) {
        Item item = new Item(id, nome, brand);
        mPrendiActivity.showFoundDialog(item);
    }

    public void itemNotFound() {
        mPrendiActivity.itemNotFound();
    }

    public boolean contains(Item item) {
        return mBorrow.contains(item);
    }

    public Borrow getBorrow() {
        return mBorrow;
    }

    public void setBorrowDate() {
        mBorrow.setDate();
    }

    public void removeItem(Item item) {
        mBorrow.removeItem(item);
    }

    public void commitBorrow(PrendiActivity activity) {
        mDBInterface.commitBorrow(activity);
    }

    public void getItemToLeave(String rawValue, LasciaActivity activity) {
        mLasciaActivity = activity;
        mDBInterface.getItemToLeave(rawValue, activity.getApplicationContext());
    }
     public void itemToLeaveFound(String id, String nome, String brand, String location) {
        //TODO-> trovato oggetto da lasciare, decidere cosa fare(+ a livello grafico che altro)
         Item item = new Item(id, nome, brand, location);
         mLasciaActivity.showToLeaveDialog(item);
     }

    public void borrowCommitted() {
        Toast.makeText(mPrendiActivity, "Prestito registrato", Toast.LENGTH_SHORT).show();
        mPrendiActivity.dispose();
    }

    public void getEvents(PrendiActivity activity) {
        mPrendiActivity = activity;
        mDBInterface.getEvents(activity);
    }

    public void eventsFound(ArrayList<String> events) {
        mPrendiActivity.setEvents(events);

    }

    public void itemAlreadyInStore() {
        Toast.makeText(mPrendiActivity, "Oggetto già in magazzino", Toast.LENGTH_SHORT).show();

    }

    public void addItemToLeave(Item item) {
        mItemsToLeave.add(item);

    }

    public void commitLeave(LasciaActivity activity) {
        mDBInterface.commitLeave(mItemsToLeave, activity);
    }

    public void leaveCommitted() {
        Toast.makeText(mLasciaActivity, "Oggetti depositati", Toast.LENGTH_SHORT).show();
        mLasciaActivity.dispose();
    }
}
