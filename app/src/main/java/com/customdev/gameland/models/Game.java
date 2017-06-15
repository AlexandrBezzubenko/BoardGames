package com.customdev.gameland.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable{

    private String mId;
    private String mName;
    private String mLanguage;
    private String mDescribe;
    private String mMaxPlayers;
    private String mMinPlayers;
    private String mDuration;

    public Game() {

    }

    protected Game(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mLanguage = in.readString();
        mDescribe = in.readString();
        mMaxPlayers = in.readString();
        mMinPlayers = in.readString();
        mDuration = in.readString();
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public String getMaxPlayers() {
        return mMaxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        mMaxPlayers = maxPlayers;
    }

    public String getMinPlayers() {
        return mMinPlayers;
    }

    public void setMinPlayers(String minPlayers) {
        mMinPlayers = minPlayers;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mLanguage);
        dest.writeString(mDescribe);
        dest.writeString(mMaxPlayers);
        dest.writeString(mMinPlayers);
        dest.writeString(mDuration);
    }
}
