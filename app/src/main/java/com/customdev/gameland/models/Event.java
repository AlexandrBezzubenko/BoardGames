package com.customdev.gameland.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Event implements Parcelable {

    private static final int TYPE_GAME = 0;
    private static final int TYPE_CHAMPIONSHIP = 1;
    private static final int TYPE_DND = 2;

    private String mId;
    private Location location;
    private int mType;
    private Game mGame;
    private long mMaxPlayersCount;
    private long mNeedPlayersCount;
    private long mStartTime;
    private String mDescription;
    private HashMap<Integer, User> mPlayerList;
    private String mCreatorId;

    public Event() {

    }

    protected Event(Parcel in) {
        mId = in.readString();
        mType = in.readInt();
        mGame = in.readParcelable(Game.class.getClassLoader());
        mMaxPlayersCount = in.readInt();
        mNeedPlayersCount = in.readInt();
        mStartTime = in.readLong();
        mDescription = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
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

    public long getMaxPlayersCount() {
        return mMaxPlayersCount;
    }

    public void setMaxPlayersCount(long maxPlayersCount) {
        mMaxPlayersCount = maxPlayersCount;
    }

    public long getNeedPlayersCount() {
        return mNeedPlayersCount;
    }

    public void setNeedPlayersCount(long needPlayersCount) {
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

    public String getCreatorId() {
        return mCreatorId;
    }

    public void setCreatorId(String creatorId) {
        mCreatorId = creatorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeInt(mType);
        dest.writeParcelable(mGame, flags);
        dest.writeLong(mMaxPlayersCount);
        dest.writeLong(mNeedPlayersCount);
        dest.writeLong(mStartTime);
        dest.writeString(mDescription);
    }
}
