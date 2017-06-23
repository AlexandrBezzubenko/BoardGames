package com.customdev.gameland;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.customdev.gameland.dialogs.ChangeEmailDialogFragment;
import com.customdev.gameland.dialogs.ChangeImageDialogFragment;
import com.customdev.gameland.interfaces.OnUserProfileImageUploadListener;
import com.customdev.gameland.models.User;
import com.customdev.gameland.utils.AuthenticateManager;
import com.customdev.gameland.utils.DatabaseManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "UserProfileEditActivity";

    public interface OnImageChosenListener {
        void onImageChosenSuccess(File newImage);
        void onImageChosenFailure();
    }

    private static Handler mHandler;

    @BindView(R.id.edit_user_image) CircleImageView mProfileImage;
    @BindView(R.id.edit_user_nickname) TextView mNicknameText;
    @BindView(R.id.edit_user_firstname) TextView mFirstNameText;
    @BindView(R.id.edit_user_lastname) TextView mLastNameText;
    @BindView(R.id.edit_user_phone) TextView mPhoneText;
    @BindView(R.id.edit_user_email) TextView mEmailText;
    @BindView(R.id.edit_user_city) TextView mCityText;
    @BindView(R.id.user_change_email_btn) Button mChangeEmail;
    @BindView(R.id.user_change_password_btn) Button mChangePassword;
    @BindView(R.id.confirm_btn) Button mConfirmChanges;
    @BindView(R.id.progress) ProgressBar mProgressBar;

    private User user;
    private User newUser;

    private Uri newImageUri;
    private String newEmail;
    private ArrayList<String> userInfoEditableFields;
    private boolean isEmailChanged;
    private boolean isPasswordChanged;
    private boolean isUserInfoChanged;
    private boolean isImageUploadingComplete;
    private boolean isEmailUpdatingComplete;
    private boolean isPasswordUpdatingComplete;
    private boolean isUserInfoUpdatingComplete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile_edit);

        ButterKnife.bind(this);
        setClickListeners();

        setAvatar();
        setUserInfoFields();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (isUploadComplete()) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_user_image:
                showImagePickDialog();
                break;
            case R.id.user_change_email_btn:
                showChangeEmailDialog();
                break;
            case R.id.user_change_password_btn:
                showChangePasswordDialog();
                break;
            case R.id.confirm_btn:
                confirmChanges();
                break;
        }
    }

    private void setClickListeners() {
        mProfileImage.setOnClickListener(this);
        mChangeEmail.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
        mConfirmChanges.setOnClickListener(this);
    }

    private void setAvatar() {
        String filePath = getCacheDir().getPath() + '/' + App.getFirebaseUser().getUid();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        mProfileImage.setImageBitmap(bitmap);

    }

    private void setUserInfoFields() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.get("USER");
            if (user != null) {
                mNicknameText.setText(user.getNickName());
                mFirstNameText.setText(user.getFirstName());
                mLastNameText.setText(user.getLastName());
                mPhoneText.setText(user.getPhone());
                mEmailText.setText(user.getEmail());
                mCityText.setText(user.getCity());
            }
        }
    }

    private void showImagePickDialog() {
        ChangeImageDialogFragment.newInstance(new OnImageChosenListener() {
            @Override
            public void onImageChosenSuccess(File newImage) {
                if (newImage != null) {
                    newImageUri = Uri.fromFile(newImage);
                    mProfileImage.setImageURI(newImageUri);
                } else {
                    Log.e(LOG_TAG, "onImageChosenSuccess: " + "returned null");
                }
            }

            @Override
            public void onImageChosenFailure() {
                Log.e(LOG_TAG, "onImageChosenFailure: " + "failure while choosing new image");
            }
        }).show(getFragmentManager(), null);
    }

    private void showChangeEmailDialog() {
        ChangeEmailDialogFragment df = new ChangeEmailDialogFragment();
        df.show(getFragmentManager(), null);
    }

    private void showChangePasswordDialog() {

    }

    private void confirmChanges() {
        String email = mEmailText.getText().toString().trim();
        if (isEmailValid(email)) {
            AuthenticateManager.changeUserEmail(email, new ChangeEmailDialogFragment.OnEmailChangeListener() {
                @Override
                public void onSuccess() {
                    if (validate())
                        updateDatabase();
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }

        if (newImageUri != null) {
            DatabaseManager.uploadUserImageToFirebase(App.getFirebaseUser().getUid(), newImageUri, new OnUserProfileImageUploadListener() {
                @Override
                public void onUploadSuccess() {
                    File tmpFile = new File(newImageUri.getPath());
                    File newImageFile = new File(getCacheDir().getPath(), App.getFirebaseUser().getUid());
                    tmpFile.renameTo(newImageFile);
                    newImageUri = null;

                    isImageUploadingComplete = true;

                    mHandler.sendEmptyMessage(0);
                }

                @Override
                public void onUploadFail(Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_unable_upload_image), Toast.LENGTH_SHORT).show();

                    isImageUploadingComplete = true;

                    mHandler.sendEmptyMessage(0);
                }
            });

        } else {
            isImageUploadingComplete = true;
        }


    }

    private boolean validate() {

        String nickname = mNicknameText.getText().toString().trim();
        String firstName = mFirstNameText.getText().toString().trim();
        String lastName = mLastNameText.getText().toString().trim();
        String phone = mPhoneText.getText().toString().trim();
        String email = mEmailText.getText().toString().trim();
        String city = mCityText.getText().toString().trim();

        boolean valid = isNicknameValid(nickname)
                && isFirstNameValid(firstName)
                && isLastnameValid(lastName)
                && isPhoneValid(phone)
                && isEmailValid(email)
                && isCityValid(city);

//        if (nickname.isEmpty() || nickname.length() < 4 || nickname.length() > 16) {
//            mNicknameText.setError(getString(R.string.user_profile_error_invalid_nickname));
//            valid = false;
//        } else {
//            mNicknameText.setError(null);
//        }

//        if (firstName.isEmpty() || firstName.length() > 16 ) {
//            mFirstNameText.setError(getString(R.string.user_profile_error_invalid_firstname));
//            valid = false;
//        } else {
//            mFirstNameText.setError(null);
//        }

//        if (lastName.isEmpty() || lastName.length() > 16 ) {
//            mLastNameText.setError(getString(R.string.user_profile_error_invalid_lastname));
//            valid = false;
//        } else {
//            mLastNameText.setError(null);
//        }

//        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
//            mPhoneText.setError(getString(R.string.user_profile_error_invalid_phone));
//            valid = false;
//        } else {
//            mPhoneText.setError(null);
//        }

//        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            mEmailText.setError(getString(R.string.user_profile_error_invalid_email));
//            valid = false;
//        } else {
//            mEmailText.setError(null);
//        }



//        if (city.isEmpty() || city.length() > 16 ) {
//            mCityText.setError(getString(R.string.user_profile_error_invalid_city));
//            valid = false;
//        } else {
//            mCityText.setError(null);
//        }

        newUser = new User();
        newUser.setNickName(nickname);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setCity(city);

        return valid;
    }

    private void updateDatabase() {
        Map<String, Object> updates = new HashMap<>();

        String currentNickname = user.getNickName();
        String currentFirstName = user.getFirstName();
        String currentLastName = user.getLastName();
        String currentPhone = user.getPhone();
        String currentCity = user.getCity();

        String newNickname = newUser.getNickName();
        String newFirstName = newUser.getFirstName();
        String newLastName = newUser.getLastName();
        String newPhone = newUser.getPhone();
        String newCity = newUser.getCity();

        if (!currentNickname.equals(newNickname)) {
            updates.put("nickName", newNickname);
        }
        if (!currentFirstName.equals(newFirstName)) {
            updates.put("firstName", newFirstName);
        }
        if (!currentLastName.equals(newLastName)) {
            updates.put("lastName", newLastName);
        }
        if (!currentPhone.equals(newPhone)) {
            updates.put("phone", newPhone);
        }
        if (!currentCity.equals(newCity)) {
            updates.put("city", newCity);
        }

        if (!updates.isEmpty()) {
            DatabaseManager.updateUserInfoInFirebase(App.getFirebaseUser().getUid(), updates);
        }

    }

    private boolean isNicknameValid(String nickname) {
        boolean valid = true;
        if (nickname.isEmpty() || nickname.length() < 4 || nickname.length() > 16) {
            mNicknameText.setError(getString(R.string.user_profile_error_invalid_nickname));
            valid = false;
        } else {
            mNicknameText.setError(null);
        }
        return valid;
    }

    private boolean isFirstNameValid(String firstName) {
        boolean valid = true;
        if (firstName.isEmpty() || firstName.length() > 16 ) {
            mFirstNameText.setError(getString(R.string.user_profile_error_invalid_firstname));
            valid = false;
        } else {
            mFirstNameText.setError(null);
        }
        return valid;
    }

    private boolean isLastnameValid(String lastName) {
        boolean valid = true;
        if (lastName.isEmpty() || lastName.length() > 16 ) {
            mLastNameText.setError(getString(R.string.user_profile_error_invalid_lastname));
            valid = false;
        } else {
            mLastNameText.setError(null);
        }
        return valid;
    }

    private boolean isPhoneValid(String phone) {
        boolean valid = true;
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            mPhoneText.setError(getString(R.string.user_profile_error_invalid_phone));
            valid = false;
        } else {
            mPhoneText.setError(null);
        }
        return valid;
    }

    private boolean isEmailValid(String email) {
        boolean valid = true;
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError(getString(R.string.user_profile_error_invalid_email));
            valid = false;
        } else {
            mEmailText.setError(null);
        }
        return valid;
    }

    private boolean isCityValid(String city) {
        boolean valid = true;
        if (city.isEmpty() || city.length() > 16 ) {
            mCityText.setError(getString(R.string.user_profile_error_invalid_city));
            valid = false;
        } else {
            mCityText.setError(null);
        }
        return valid;
    }

    private boolean isUploadComplete() {
        return (isImageUploadingComplete);
    }
}
