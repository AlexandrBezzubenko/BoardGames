package com.customdev.gameland;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "LoginActivity";

    private static final int REQUEST_SIGNUP = 0;

    private ImageView mLogo;
    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButton;
    
    private String mEmail, mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogo = (ImageView) findViewById(R.id.iv_logo);
        mEmailEditText = (EditText) findViewById(R.id.et_email);
        mPasswordEditText = (EditText) findViewById(R.id.et_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        TextView signUpTextView = (TextView) findViewById(R.id.tv_signup);

        mLoginButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);

//        ViewCompat.animate(mLogo)
//                .translationY(-250)
//                .setStartDelay(500)
//                .setDuration(500)
//                .setInterpolator(new DecelerateInterpolator(1.2f))
//                .start();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.btn_login:
               login();
               break;
           case R.id.tv_signup:
               signup();
               break;
       }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void login() {
        Log.d(LOG_TAG, "Login");

        if (validate()) {

//            mLoginButton.setEnabled(false);
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Authenticating...");
//            progressDialog.show();

            App.getAuth().signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(LOG_TAG, "signInWithEmail:success");

//                                progressDialog.hide();
                                finish();
                            } else {
                                Log.e(LOG_TAG, "signInWithEmail:failure", task.getException());

                                mLoginButton.setEnabled(true);
//                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

/*        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                       onLoginSuccess();
                    }
                }, 3000
        );*/



    }

    private void signup() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private boolean validate() {
        boolean valid = true;

        mEmail = mEmailEditText.getText().toString().trim();
        mPassword = mPasswordEditText.getText().toString().trim();

        if (mEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEmailEditText.setError(getString(R.string.login_error_invalid_email));
            valid = false;
        } else {
            mEmailEditText.setError(null);
        }

        if (mPassword.isEmpty() || mPassword.length() < 4 || mPassword.length() > 10) {
            mPasswordEditText.setError(getString(R.string.login_error_invalid_password));
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }

        return valid;
    }

    private void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        finish();
    }

    private void startMainActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        public void onFocusChange(View v, boolean hasFocus){

            if(v.getId() == R.id.tv_signup && hasFocus) {

                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }
    }
}
