package it.univr.mb.magazza.Model;

import android.media.Image;

import java.util.Objects;

public class Item implements Comparable<Item> {
    private final String ID;
    private String name;
    private String brand;
    private String pictureUrl; //in alternativa Image(?) picture;
    private String description;
    private int year;
    private String storeLocation;

    public Item(String id, String name, String brand) {
        ID = id;
        this.name = name;
        this.brand = brand;
    }

    public Item(String id, String nome, String brand, String location) {
        ID = id;
        this.name = nome;
        this.brand = brand;
        this.storeLocation = location;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    @Override
    public int compareTo(Item i) {
        return (this.ID.compareTo(i.ID));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Item item = (Item) o;
        return Objects.equals(ID, item.ID);
    }

    @Override
    public int hashCode() {

        return this.ID.hashCode();
    }
}
