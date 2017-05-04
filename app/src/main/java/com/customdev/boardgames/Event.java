package com.customdev.boardgames;

import java.util.HashMap;
import java.util.List;

public class Event {

    private int mId;
    private Game mGame;
    private User mCreator;
    private long mStartTime;
    private HashMap<Integer, User> mPlayerList;
    private Location location;
    private int mMaxPlayersCount;

    public Event() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Game getGame() {
        return mGame;
    }

    public void setGame(Game game) {
        mGame = game;
    }

    public User getCreator() {
        return mCreator;
    }

    public void setCreator(User creator) {
        mCreator = creator;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long startTime) {
        mStartTime = startTime;
    }

    public HashMap<Integer, User> getPlayerList() {
        return mPlayerList;
    }

    public void setPlayerList(HashMap<Integer, User> playerList) {
        mPlayerList = playerList;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMaxPlayersCount() {
        return mMaxPlayersCount;
    }

    public void setMaxPlayersCount(int maxPlayersCount) {
        mMaxPlayersCount = maxPlayersCount;
    }
}
