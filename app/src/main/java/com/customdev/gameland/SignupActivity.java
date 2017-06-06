package com.customdev.gameland;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.customdev.gameland.utils.DatabaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "SignUpActivity";
    private static final String USER_REFERENCE = "Users";

    private EditText mNicknameEditText, mEmailEditText, mPhoneNumberEditText, mPasswordEditText, mPasswordConfirmEditText;
    private Button mSignUpButton;

    private String mEmail, mPassword;

    private User mUser;


    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
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
            mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(LOG_TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    mUser.setId(user.getUid());
                                    DatabaseManager.addUserInfoToFirebase(mUser);
                                }
                                login();
                            } else {
                                Log.w(LOG_TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
            });
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
        mUser.setNickname(nickname);
        mUser.setEmail(email);
        mUser.setPhone(phone);

        return valid;
    }

}
