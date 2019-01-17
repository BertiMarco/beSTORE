package it.univr.mb.magazza.Model;

public class User {
    private String imeiCode;
    private String name;
    private String surname;

    public User(String imeiCode, String name, String surname) {
        this.imeiCode = imeiCode;
        this.name = name;
        this.surname = surname;
    }

    public String getImeiCode() {
        return imeiCode;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
