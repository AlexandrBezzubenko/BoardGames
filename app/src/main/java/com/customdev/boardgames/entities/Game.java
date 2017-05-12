package com.customdev.boardgames.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable{

    private int mId;
    private String mName;
    private String mDescription;
    private int mMaxPlayers;
    private int mMinPlayers;
    private long mDuration;

    public Game() {

    }

    protected Game(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mMaxPlayers = in.readInt();
        mMinPlayers = in.readInt();
        mDuration = in.readLong();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeInt(mMaxPlayers);
        dest.writeInt(mMinPlayers);
        dest.writeLong(mDuration);
    }
}
