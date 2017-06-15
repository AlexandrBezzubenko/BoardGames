package com.customdev.gameland;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.customdev.gameland.utils.AuthenticateManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "LoginActivity";

    /**
     * The callbacks used to indicate the user login result.
     */
    public interface OnLoginResultListener {
        /**
         * Calls in case of succeed login.
         */
        void onLoginSuccess();

        /**
         * Calls is case of login failure.
         * @param e An exception returned from server.
         */
        void onLoginFail(Exception e);
    }

    @BindView(R.id.et_email) EditText mEmailEditText;
    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.btn_login) Button mLoginButton;
    @BindView(R.id.tv_signup) TextView mSignUpTextView;
    
    private String mEmail, mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(this);
        mSignUpTextView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
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

    private void login() {
        Log.d(LOG_TAG, "Login");

        if (validate()) {
            mLoginButton.setEnabled(false);
            AuthenticateManager.signInWithEmailAndPassword(mEmail, mPassword, new OnLoginResultListener() {
                @Override
                public void onLoginSuccess() {
                    finish();
                }

                @Override
                public void onLoginFail(Exception e) {
                    mLoginButton.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Authentication failed." + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void signup() {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
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
}
