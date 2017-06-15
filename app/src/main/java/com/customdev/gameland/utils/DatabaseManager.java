package com.customdev.gameland.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.customdev.gameland.App;
import com.customdev.gameland.interfaces.OnUserProfileImageDownloadListener;
import com.customdev.gameland.interfaces.OnUserProfileImageUploadListener;
import com.customdev.gameland.models.Game;
import com.customdev.gameland.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Map;

public class DatabaseManager {

    private static final String LOG_TAG = "DatabaseManager";

    public static void addUserInfoToFirebase(User user) {
        Log.d(LOG_TAG, "Add user info to firebase");

        App.getDatabaseUsersReference().child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "addUserInfoToFirebase: success");
                } else {
                    Log.e(LOG_TAG, "addUserInfoToFirebase: failure", task.getException());
                }
            }
        });
    }

    public static void updateUserInfoInFirebase(String userId, Map<String, Object> updates) {
        Log.d(LOG_TAG, "Update user info in firebase");

        App.getDatabaseUsersReference().child(userId).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "updateUserInfoInFirebase: success");
                } else {
                    Log.e(LOG_TAG, "updateUserInfoInFirebase: failure", task.getException());
                }
            }
        });
    }

    public static void downloadUserImageFromFirebase(Context context, String userId, final OnUserProfileImageDownloadListener listener) {
        Log.d(LOG_TAG, "Get user profile image from firebase");

        StorageReference imageRef = App.getStorageUserImagesReference().child(userId);
        File image = new File(context.getCacheDir(), userId);
        imageRef.getFile(image).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "downloadUserImageFromFirebase: success");

                    listener.onDownloadSuccess();
                } else {
                    Log.e(LOG_TAG, "downloadUserImageFromFirebase: failure", task.getException());

                    listener.onDownloadFail(task.getException());
                }
            }
        });
    }

    public static void uploadUserImageToFirebase(String userId, Uri imageUri, final OnUserProfileImageUploadListener listener) {
        Log.d(LOG_TAG, "Add user profile image to firebase");

        StorageReference imageRef = App.getStorageUserImagesReference().child(userId);
        imageRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(LOG_TAG, "uploadUserImageFromFirebase: success");

                    listener.onUploadSuccess();
                } else {
                    Log.d(LOG_TAG, "uploadUserImageFromFirebase: success");

                    listener.onUploadFail(task.getException());
                }
            }
        });
    }

    public static void getGameListFromFirebase() {
        Log.d(LOG_TAG, "Get list of games from firebase");

        App.getDatabaseGamesReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(LOG_TAG, "onChildAdded: " + dataSnapshot.getKey());

                try {
                    Game game = dataSnapshot.getValue(Game.class);
                    App.getGameList().add(game);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(LOG_TAG, "onChildChanged: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "onChildRemoved: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(LOG_TAG, "onChildMoved: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(LOG_TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
    }

    public static void getCityListFromFirebase() {
        Log.d(LOG_TAG, "Get list of cities from firebase.");

    }

    public static void getClubListFromFirebase(String city) {
        Log.d(LOG_TAG, "Get list of clubs from firebase for " + city);
    }

    public static void getEventListForDate(String date) {
        Log.d(LOG_TAG, "Get list of events from firebase for " + date);


    }
}
