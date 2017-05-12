package com.customdev.boardgames.entities;

import java.util.HashMap;

public class Event {

    private static final int TYPE_GAME = 0;
    private static final int TYPE_DND = 1;
    private static final int TYPE_CHAMPIONSHIP = 2;

    private int mId;
    private Location location;
    private int mType;
    private Game mGame;
    private int mMaxPlayersCount;
    private int mNeedPlayersCount;
    private long mStartTime;
    private String mDescription;
    private HashMap<Integer, User> mPlayerList;
    private User mCreator;

    public Event() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public Game getGame() {
        return mGame;
    }

    public void setGame(Game game) {
        mGame = game;
    }

    public int getMaxPlayersCount() {
        return mMaxPlayersCount;
    }

    public void setMaxPlayersCount(int maxPlayersCount) {
        mMaxPlayersCount = maxPlayersCount;
    }

    public int getNeedPlayersCount() {
        return mNeedPlayersCount;
    }

    public void setNeedPlayersCount(int needPlayersCount) {
        mNeedPlayersCount = needPlayersCount;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public HashMap<Integer, User> getPlayerList() {
        return mPlayerList;
    }

    public void setPlayerList(HashMap<Integer, User> playerList) {
        mPlayerList = playerList;
    }

    public User getCreator() {
        return mCreator;
    }

    public void setCreator(User creator) {
        mCreator = creator;
    }
}
