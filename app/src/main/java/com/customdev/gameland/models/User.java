package com.customdev.gameland.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String mId;
    private String mNickname;
    private String mFistName;
    private String mLastName;
    private String mAvatarTag;
    private String mPassword;
    private String mEmail;
    private String mPhone;
    private String mCity;
    private String mRank;
    private String mRole;

    public User() {

    }

    private User(Parcel in) {
        mId = in.readString();
        mNickname = in.readString();
        mFistName = in.readString();
        mLastName = in.readString();
        mAvatarTag = in.readString();
        mPassword = in.readString();
        mEmail = in.readString();
        mPhone = in.readString();
        mCity = in.readString();
        mRank = in.readString();
        mRole = in.readString();
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
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

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getRank() {
        return mRank;
    }

    public void setRank(String rank) {
        mRank = rank;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mNickname);
        dest.writeString(mFistName);
        dest.writeString(mLastName);
        dest.writeString(mAvatarTag);
        dest.writeString(mPassword);
        dest.writeString(mEmail);
        dest.writeString(mPhone);
        dest.writeString(mCity);
        dest.writeString(mRank);
        dest.writeString(mRole);
    }
}
