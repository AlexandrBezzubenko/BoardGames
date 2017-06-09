package com.customdev.gameland;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class App extends Application {

    private static FirebaseAuth mAuth;
    private static FirebaseUser mUser;

    public static FirebaseAuth getAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    public static FirebaseUser getUser() {
        if (mUser == null) {
            mUser = getAuth().getCurrentUser();
        }
        return mUser;
    }
}
