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

import com.customdev.gameland.models.User;
import com.customdev.gameland.utils.AuthenticateManager;
import com.customdev.gameland.utils.DatabaseManager;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "SignUpActivity";

    /**
     * The callbacks used to indicate the user signup result.
     */
    public interface OnSignupResultListener {
        /**
         * Calls in case of succeed signup.
         */
        void onSignupSuccess();

        /**
         * Calls is case of signup failure.
         * @param e An exception returned from server.
         */
        void onSignupFail(Exception e);
    }

    @BindView(R.id.et_nickname) EditText mNicknameEditText;
    @BindView(R.id.et_email) EditText mEmailEditText;
    @BindView(R.id.et_phone) EditText mPhoneNumberEditText;
    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.et_password_confirm) EditText mPasswordConfirmEditText;
    @BindView(R.id.btn_signup) Button mSignUpButton;
    @BindView(R.id.tv_login) TextView mLoginTextView;

    private String mEmail, mPassword;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        login();
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

        if (validate()) {
            mSignUpButton.setEnabled(false);
            AuthenticateManager.createUserWithEmailAndPassword(mEmail, mPassword, new OnSignupResultListener() {
                @Override
                public void onSignupSuccess() {
                    FirebaseUser fireUser = App.getFirebaseUser();
                    if (fireUser != null) {
                        mUser.setId(fireUser.getUid());
                        DatabaseManager.addUserInfoToFirebase(mUser);
                    }
                    login();
                }

                @Override
                public void onSignupFail(Exception e) {
                    mSignUpButton.setEnabled(true );
                    Toast.makeText(getApplicationContext(), "Signup failed." + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void login() {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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

        if (password.isEmpty() || password.length() < 6 || password.length() > 10 ) {
            mPasswordEditText.setError(getString(R.string.signup_error_invalid_password));
            valid = false;
        } else if (!password.equals(passwordConfirm)) {
            mPasswordEditText.setError(getString(R.string.signup_error_mismatch_password));
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }

        mEmail = email;
        mPassword = password;

        mUser = new User();
        mUser.setNickName(nickname);
        mUser.setEmail(email);
        mUser.setPhone(phone);

        return valid;
    }
}
