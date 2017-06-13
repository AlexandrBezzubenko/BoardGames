package com.customdev.gameland;

import android.app.Application;

import com.customdev.gameland.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class App extends Application {

    private static App sApp;

    private static User sUser;

    @Override
    public void onCreate() {
        super.onCreate();

        sApp = this;
    }

    public static App getInstance() {
        return sApp;
    }

    public static User getUser() {
        if (sUser != null) {
            sUser = new User();
        }
        return sUser;
    }

    public static FirebaseAuth getFifebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        return getFifebaseAuth().getCurrentUser();
    }

}
