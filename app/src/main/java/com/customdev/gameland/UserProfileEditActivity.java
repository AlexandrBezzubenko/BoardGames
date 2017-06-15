package com.customdev.gameland;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.customdev.gameland.dialogs.ChangeImageDialogFragment;
import com.customdev.gameland.interfaces.OnUserProfileImageUploadListener;
import com.customdev.gameland.utils.DatabaseManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    public interface OnImageChosenListener {
        void onImageChosen(Uri selectedImage);
        void onError();
    }

    @BindView(R.id.edit_user_image) CircleImageView mProfileImage;
    @BindView(R.id.edit_user_nickname) TextView mNicknameText;
    @BindView(R.id.edit_user_firstname) TextView mFirstNameText;
    @BindView(R.id.edit_user_lastname) TextView mLastNameText;
    @BindView(R.id.edit_user_phone) TextView mPhoneText;
    @BindView(R.id.edit_user_city) TextView mCityText;
    @BindView(R.id.user_change_email_btn) Button mChangeEmail;
    @BindView(R.id.user_change_password_btn) Button mChangePassword;
    @BindView(R.id.confirm_btn) Button mConfirmChanges;

    ArrayList<String> userInfoEditableFields;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile_edit);

        ButterKnife.bind(this);
        setClickListeners();

        setAvatar();
        setUserInfoFields();
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
                updateUserInfo();
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
            userInfoEditableFields = extras.getStringArrayList("USER_INFO");
            if (userInfoEditableFields != null) {
                mNicknameText.setText(userInfoEditableFields.get(0));
                mFirstNameText.setText(userInfoEditableFields.get(1));
                mLastNameText.setText(userInfoEditableFields.get(2));
                mPhoneText.setText(userInfoEditableFields.get(3));
                mCityText.setText(userInfoEditableFields.get(4));
            }
        }
    }

    private void showImagePickDialog() {
        ChangeImageDialogFragment.newInstance(new OnImageChosenListener() {
            @Override
            public void onImageChosen(Uri selectedImage) {

                Toast.makeText(getApplicationContext(), selectedImage.toString(), Toast.LENGTH_SHORT).show();

                setUserProfileImage(selectedImage);
            }

            @Override
            public void onError() {

            }
        }).show(getFragmentManager(), null);
    }

    private void showChangeEmailDialog() {

    }

    private void showChangePasswordDialog() {

    }

    private void updateUserInfo() {

    }

    private void setUserProfileImage(final Uri imageUri) {
        DatabaseManager.uploadUserImageToFirebase(App.getFirebaseUser().getUid(), imageUri, new OnUserProfileImageUploadListener() {
            @Override
            public void onUploadSuccess() {
                saveImageToCache(imageUri);
                Toast.makeText(getApplicationContext(), "User profile image updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadFail(Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImageToCache(Uri imageUri) {
        String sourceFilename = imageUri.getPath();
        String destinationFilename = getCacheDir().getPath() + File.separatorChar + App.getFirebaseUser().getUid();

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(sourceFilename));
            bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
            byte[] buf = new byte[1024];
            bis.read(buf);
            do {
                bos.write(buf);
            } while(bis.read(buf) != -1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
