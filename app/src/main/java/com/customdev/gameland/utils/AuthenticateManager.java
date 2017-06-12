package com.customdev.gameland.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticateManager {

    Context mContext;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public AuthenticateManager(Context context) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    public void createUser(String email, String password) {

    }

}
