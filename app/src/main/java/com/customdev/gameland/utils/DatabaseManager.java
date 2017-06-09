package com.customdev.gameland.utils;

import com.customdev.gameland.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {

    private static final String USERS = "Users";
    private static final String USER_NICK_NAME = "kUserNickName";
    private static final String USER_FIRST_NAME = "kUserFirstName";
    private static final String USER_LAST_NAME = "kUserLastName";
    private static final String USER_PHONE = "kUserPhone";
    private static final String USER_CITY = "kUserCity";
    private static final String USER_RANK = "kUserRank";
    private static final String USER_ROLE = "kUserRole";

    private User user;

    public DatabaseManager() {

    }

    public static void addUserInfoToFirebase(User user) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(USERS).child(user.getId());
        userReference.child(USER_NICK_NAME).setValue(user.getNickname());
        userReference.child(USER_FIRST_NAME).setValue(user.getFistName());
        userReference.child(USER_LAST_NAME).setValue(user.getLastName());
        userReference.child(USER_PHONE).setValue(user.getPhone());
        userReference.child(USER_CITY).setValue(user.getCity());
        userReference.child(USER_RANK).setValue(user.getRank());
        userReference.child(USER_ROLE).setValue(user.getRole());
    }

    public static User getUserInfoFromFirebase(FirebaseUser fireUser) {
        User user = new User();


        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(fireUser.getUid());
        user.setId(fireUser.getUid());
        user.setEmail(fireUser.getEmail());
//        user.setAvatarTag(fireUser.getPhotoUrl().toString());
//        user.setNickname();
//        user.setFistName();
//        user.setLastName();
//        user.setPhone();
//        user.setCity();
//        user.setRank();
//        user.setRole();

        return user;
    }
}
