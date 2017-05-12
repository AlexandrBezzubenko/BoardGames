package com.customdev.boardgames.entities;

public class Location {

    private int mId;
    private int mCity;
    private int mClub;

    public Location() {

    }

    public Location(int id, int city, int club) {
        mId = id;
        mCity = city;
        mClub = club;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getCity() {
        return mCity;
    }

    public void setCity(int city) {
        mCity = city;
    }

    public int getClub() {
        return mClub;
    }

    public void setClub(int club) {
        mClub = club;
    }
}
