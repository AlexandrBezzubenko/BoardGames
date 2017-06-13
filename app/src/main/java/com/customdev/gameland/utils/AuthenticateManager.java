package com.customdev.gameland.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.customdev.gameland.App;
import com.customdev.gameland.interfaces.OnLoginResultListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthenticateManager {

    private static final String LOG_TAG = "AuthenticateManager";

    public static void signInWithEmailAndPassword(final Activity activity, String email, String password) {
        if (activity instanceof OnLoginResultListener) {
            final OnLoginResultListener listener = (OnLoginResultListener) activity;

            App.getFifebaseAuth().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(LOG_TAG, "signInWithEmail: success");

                                    listener.OnLoginSuccess();
                                } else {
                                    Log.e(LOG_TAG, "signInWithEmail: failure", task.getException());

                                    listener.OnLoginFail(task.getException());
                                }
                            }
                        });
        } else {
            throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }
}
