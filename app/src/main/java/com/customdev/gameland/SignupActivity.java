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


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "SignUpActivity";

    private EditText mNicknameEditText, mEmailEditText, mPhoneNumberEditText, mPasswordEditText, mPasswordConfirmEditText;
    private Button mSignUpButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mNicknameEditText = (EditText) findViewById(R.id.et_nickname);
        mEmailEditText = (EditText) findViewById(R.id.et_email);
        mPhoneNumberEditText = (EditText) findViewById(R.id.et_phone);
        mPasswordEditText = (EditText) findViewById(R.id.et_password);
        mPasswordConfirmEditText = (EditText) findViewById(R.id.et_password_confirm);
        mSignUpButton = (Button) findViewById(R.id.btn_signup);
        TextView loginTextView = (TextView) findViewById(R.id.tv_login);

        mSignUpButton.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                signup();
                break;

            case R.id.tv_login:
                login();
                break;
        }
    }

    private void signup() {
        Log.d(LOG_TAG, "Signup");

        if (!validate()) {
            onSignupFail();
            return;
        }
    }

    private void login() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private boolean validate() {
        boolean valid = true;

        String nickname = mNicknameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String phone = mPhoneNumberEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String passwordConfirm = mPasswordConfirmEditText.getText().toString().trim();

        if (nickname.isEmpty() || nickname.length() < 4 || nickname.length() > 16) {
            mNicknameEditText.setError(getString(R.string.signup_error_invalid_nickname));
            valid = false;
        } else {
            mNicknameEditText.setError(null);
        }


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError(getString(R.string.signup_error_invalid_email));
            valid = false;
        } else {
            mEmailEditText.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            mPhoneNumberEditText.setError(getString(R.string.signup_error_invalid_phone));
            valid = false;
        } else {
            mPhoneNumberEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10 ) {
            mPasswordEditText.setError(getString(R.string.signup_error_invalid_password));
            valid = false;
        } else if (!password.equals(passwordConfirm)) {
            mPasswordEditText.setError(getString(R.string.signup_error_mismatch_password));
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }


        return valid;
    }

    private void onSignupFail() {

    }

}
