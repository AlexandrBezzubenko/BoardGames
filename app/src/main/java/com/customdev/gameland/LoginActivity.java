package com.customdev.gameland;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mLoginInputLayout, mPasswordInputLayout;
    private EditText mLoginEditText, mPasswordEditText;
    private Button mLoginButton, mRegisterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginInputLayout = (TextInputLayout) findViewById(R.id.til_login);
        mPasswordInputLayout = (TextInputLayout) findViewById(R.id.til_password);
        mLoginEditText = (EditText) findViewById(R.id.et_login);
        mPasswordEditText = (EditText) findViewById(R.id.et_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mRegisterButton = (Button) findViewById(R.id.btn_register);

        mLoginEditText.addTextChangedListener(new CustomTextWatcher(mLoginEditText));
        mPasswordEditText.addTextChangedListener(new CustomTextWatcher(mPasswordEditText));
        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_register:
                Toast.makeText(this, "REGISTER", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    private void doLogin() {
        if (!validateLogin())
            return;
        if (!validatePassword())
            return;
        login();
    }

    private boolean validateLogin() {
        String login = mLoginEditText.getText().toString().trim();
        if (login.isEmpty()) {
            mLoginInputLayout.setError(getString(R.string.login_error_login_empty));
            return false;
        }
        if (login.contains("@")) {
            if (!isValidEmail(login)) {
                mLoginInputLayout.setError(getString(R.string.login_error_invalid_email));
//                requestFocus(mLoginEditText);
                return false;
            }
        }
        mLoginInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword() {
        if (mPasswordEditText.getText().toString().trim().isEmpty()) {
            mPasswordInputLayout.setError(getString(R.string.login_error_invalid_password));
//            requestFocus(mPasswordEditText);
            return false;
        } else {
            mPasswordInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private void login() {
        Toast.makeText(this, "Login: " + mLoginEditText.getText().toString() + " Password: " + mPasswordEditText.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class CustomTextWatcher implements TextWatcher {

        private View mView;

        CustomTextWatcher(View view) {
            mView = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (mView.getId()) {
                case R.id.et_login:
                    validateLogin();
                    break;
                case R.id.et_password:
                    validatePassword();
                    break;
            }
        }
    }
}
