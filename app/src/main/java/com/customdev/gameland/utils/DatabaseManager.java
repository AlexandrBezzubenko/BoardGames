package com.customdev.gameland.utils;

import android.util.Log;

import com.customdev.gameland.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
