package com.customdev.gameland;

import android.app.Application;

import com.customdev.gameland.models.Game;
import com.customdev.gameland.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class App extends Application {

    private static final String DB_REF_GAMES = "Games";
    private static final String DB_REF_USERS = "Users";
    private static final String STR_REF_USER_IMGS = "UserProfileImages";

    private static ArrayList<Game> sGameList;

//    private static App sApp;

//    private static User sUser;

    @Override
    public void onCreate() {
        super.onCreate();

//        sApp = this;
    }

//    public static App getInstance() {
//        return sApp;
//    }

//    public static User getUser() {
//        if (sUser != null) {
//            sUser = new User();
//        }
//        return sUser;
//    }

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    public static DatabaseReference getDatabaseGamesReference() {
        return FirebaseDatabase.getInstance().getReference(DB_REF_GAMES);
    }

    public static DatabaseReference getDatabaseUsersReference() {
        return FirebaseDatabase.getInstance().getReference(DB_REF_USERS);
    }

    public static StorageReference getStorageUserImagesReference() {
        return FirebaseStorage.getInstance().getReference(STR_REF_USER_IMGS);
    }


    public static ArrayList<Game> getGameList() {
        if (sGameList == null) {
            sGameList = new ArrayList<>();
        }
        return sGameList;
    }
}
