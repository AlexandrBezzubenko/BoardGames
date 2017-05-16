package com.customdev.boardgames.models;

public class User {

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

    public User(String nickname) {
        this.mNickname = nickname;
    }
}
