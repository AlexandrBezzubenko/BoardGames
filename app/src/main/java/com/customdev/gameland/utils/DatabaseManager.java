package com.customdev.gameland.utils;

import com.customdev.gameland.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {

    private static final String LOG_TAG = "DatabaseManager";

    private static final String USERS = "Users";

    private static User sUser;

    public DatabaseManager() {

    }

    public static void addUserInfoToFirebase(User user) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(USERS).child(user.getId());
        userReference.setValue(user);
    }

}
