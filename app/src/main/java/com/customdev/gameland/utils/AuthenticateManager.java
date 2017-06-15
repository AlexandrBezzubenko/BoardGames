package com.customdev.gameland.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.customdev.gameland.App;
import com.customdev.gameland.LoginActivity.OnLoginResultListener;
import com.customdev.gameland.SignupActivity.OnSignupResultListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthenticateManager {

    private static final String LOG_TAG = "AuthenticateManager";

    /**
     * Login in with specified email and password.
     *
     * @param email User email to login.
     * @param password User password to login.
     * @param listener The instance of {@link OnLoginResultListener} interface to notify caller with the login result.
     */
    public static void signInWithEmailAndPassword(String email, String password, final OnLoginResultListener listener) {
        App.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(LOG_TAG, "signInWithEmail: success");

                                listener.onLoginSuccess();
                            } else {
                                Log.e(LOG_TAG, "signInWithEmail: failure", task.getException());

                                listener.onLoginFail(task.getException());
                            }
                        }
                    });
    }

    /**
     * Signup Firebase user with specified email and password.
     *
     * @param email Email for new user.
     * @param password Password for new user.
     * @param listener The instance of {@link OnSignupResultListener} interface to notify caller with the signup result.
     */
    public static void createUserWithEmailAndPassword(String email, String password, final OnSignupResultListener listener) {
        App.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(LOG_TAG, "createUserWithEmail: success");

                            listener.onSignupSuccess();
                        } else {
                            Log.w(LOG_TAG, "createUserWithEmail: failure", task.getException());

                            listener.onSignupFail(task.getException());
                        }
                    }
                });
    }
}
