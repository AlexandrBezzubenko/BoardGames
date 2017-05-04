package com.customdev.boardgames;

public class Game {

    private int mId;
    private String mName;
    private String mDescription;
    private int mMaxPlayers;
    private int mMinPlayers;
    private long mDuration;
    private boolean mIsAvaliable;

    public Game() {

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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getMaxPlayers() {
        return mMaxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        mMaxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return mMinPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        mMinPlayers = minPlayers;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public boolean isAvaliable() {
        return mIsAvaliable;
    }

    public void setAvaliable(boolean avaliable) {
        mIsAvaliable = avaliable;
    }
}
