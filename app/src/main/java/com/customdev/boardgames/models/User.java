package com.customdev.boardgames.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private int mId;
    private String mNickname;
    private String mFistName;
    private String mLastName;
    private String mAvatarTag;
    private String mPassword;
    private String mEmail;
    private String mPhone;
    private int mCity;
    private int mRange;
    private int mRole;

    public User() {

    }

    private User(Parcel in) {
        mId = in.readInt();
        mNickname = in.readString();
        mFistName = in.readString();
        mLastName = in.readString();
        mAvatarTag = in.readString();
        mPassword = in.readString();
        mEmail = in.readString();
        mPhone = in.readString();
        mCity = in.readInt();
        mRange = in.readInt();
        mRole = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setNickname(String nickname) {
        mNickname = nickname;
    }

    public String getFistName() {
        return mFistName;
    }

    public void setFistName(String fistName) {
        mFistName = fistName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getAvatarTag() {
        return mAvatarTag;
    }

    public void setAvatarTag(String avatarTag) {
        mAvatarTag = avatarTag;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getCity() {
        return mCity;
    }

    public void setCity(int city) {
        mCity = city;
    }

    public int getRange() {
        return mRange;
    }

    public void setRange(int range) {
        mRange = range;
    }

    public int getRole() {
        return mRole;
    }

    public void setRole(int role) {
        mRole = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNickname);
        dest.writeString(mFistName);
        dest.writeString(mLastName);
        dest.writeString(mAvatarTag);
        dest.writeString(mPassword);
        dest.writeString(mEmail);
        dest.writeString(mPhone);
        dest.writeInt(mCity);
        dest.writeInt(mRange);
        dest.writeInt(mRole);
    }
}
