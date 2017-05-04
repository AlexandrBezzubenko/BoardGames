package com.customdev.boardgames;

public class Location {

    private int mId;
    private String mName;
    private int mTable;

    public Location() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getTable() {
        return mTable;
    }

    public void setTable(int table) {
        mTable = table;
    }
}
