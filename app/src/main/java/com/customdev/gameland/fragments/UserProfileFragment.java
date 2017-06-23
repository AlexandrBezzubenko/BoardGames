package com.customdev.gameland.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.customdev.gameland.App;
import com.customdev.gameland.LoginActivity;
import com.customdev.gameland.R;
import com.customdev.gameland.UserProfileEditActivity;
import com.customdev.gameland.interfaces.OnUserProfileImageDownloadListener;
import com.customdev.gameland.models.User;
import com.customdev.gameland.utils.DatabaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileFragment extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = "UserProfileFragment";

    @BindView(R.id.user_avatar_image) CircleImageView mProfileImage;
    @BindView(R.id.user_range_text) TextView mRangeText;
    @BindView(R.id.user_nickname_text) TextView mNicknameText;
    @BindView(R.id.user_firstname_text) TextView mFirstNameText;
    @BindView(R.id.user_lastname_text) TextView mLastNameText;
    @BindView(R.id.user_phone_text) TextView mPhoneText;
    @BindView(R.id.user_email_text) TextView mEmailText;
    @BindView(R.id.user_city_text) TextView mCityText;
    @BindView(R.id.edit_fab) FloatingActionButton mEditFloatingActionButton;
    @BindView(R.id.confirm_btn) Button mConfirmButton;
    @BindView(R.id.logout_btn) Button mLogout;
    private Unbinder unbinder;

    private boolean isEditable = false;

    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    private User currentUser;
    private User newUser;

    private File file;

    ProgressDialog progress;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        showProgressDialog();
//        updateUserInfo();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setOnClickListeners();
    }

    @Override
    public void onStart() {
        super.onStart();

        updateUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mUserReference != null) {
            mUserReference.removeEventListener(mUserListener);
        }

        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_fab:
//                allowEdit();
                startEditActivity();
                break;
            case R.id.confirm_btn:
                confirmEdit();
                break;
            case R.id.logout_btn:
                logout();
                break;
        }
    }

    private void setOnClickListeners() {
        mEditFloatingActionButton.setOnClickListener(this);
        mConfirmButton.setOnClickListener(this);
        mLogout.setOnClickListener(this);
    }

    private void allowEdit() {
        isEditable = true;
        mNicknameText.setEnabled(isEditable);
        mFirstNameText.setEnabled(isEditable);
        mLastNameText.setEnabled(isEditable);
        mPhoneText.setEnabled(isEditable);
//        mEmailText.setEnabled(isEditable);
        mCityText.setEnabled(isEditable);
        mConfirmButton.setVisibility(View.VISIBLE);
    }

    private void confirmEdit() {
        if (validate()) {
            isEditable = false;
            mNicknameText.setEnabled(isEditable);
            mFirstNameText.setEnabled(isEditable);
            mLastNameText.setEnabled(isEditable);
            mPhoneText.setEnabled(isEditable);
//            mEmailText.setEnabled(isEditable);
            mCityText.setEnabled(isEditable);
            mConfirmButton.setVisibility(View.GONE);
            updateDatabase();
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void updateUI(User user) {
        getImage(user.getId());
        updateTextFields(user);
    }

    private void setAvatar() {
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        mProfileImage.setImageBitmap(bitmap);

//        mProfileImage.setImageURI(Uri.fromFile(file));
    }

    private void updateDatabase() {
        Map<String, Object> updates = new HashMap<>();

        String currentNickname = currentUser.getNickName();
        String currentFirstName = currentUser.getFirstName();
        String currentLastName = currentUser.getLastName();
        String currentPhone = currentUser.getPhone();
        String currentCity = currentUser.getCity();

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

    private boolean validate() {
        boolean valid = true;

        String nickname = mNicknameText.getText().toString().trim();
        String lastName = mFirstNameText.getText().toString().trim();
        String firstName = mLastNameText.getText().toString().trim();
        String phone = mPhoneText.getText().toString().trim();
        String email = mEmailText.getText().toString().trim();
        String city = mCityText.getText().toString().trim();

        if (nickname.isEmpty() || nickname.length() < 4 || nickname.length() > 16) {
            mNicknameText.setError(getString(R.string.user_profile_error_invalid_nickname));
            valid = false;
        } else {
            mNicknameText.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError(getString(R.string.user_profile_error_invalid_email));
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            mPhoneText.setError(getString(R.string.user_profile_error_invalid_phone));
            valid = false;
        } else {
            mPhoneText.setError(null);
        }

        if (firstName.isEmpty() || firstName.length() > 16 ) {
            mFirstNameText.setError(getString(R.string.user_profile_error_invalid_firstname));
            valid = false;
        } else {
            mFirstNameText.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() > 16 ) {
            mLastNameText.setError(getString(R.string.user_profile_error_invalid_lastname));
            valid = false;
        } else {
            mLastNameText.setError(null);
        }

        if (city.isEmpty() || city.length() > 16 ) {
            mCityText.setError(getString(R.string.user_profile_error_invalid_city));
            valid = false;
        } else {
            mCityText.setError(null);
        }

        newUser = new User();
        newUser.setNickName(nickname);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setCity(city);

        return valid;
    }

    private void getImage(String userId) {
        String filePath = getActivity().getCacheDir().getPath() + '/' + userId;
        file = new File(filePath);

        if (file.exists()) {
            setAvatar();
        } else {
            DatabaseManager.downloadUserImageFromFirebase(getActivity(), userId, new OnUserProfileImageDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    setAvatar();
                }

                @Override
                public void onDownloadFail(Exception e) {
                    Toast.makeText(getActivity(), "onUploadFail" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (progress != null) {
            progress.dismiss();
        }
    }

    private void updateTextFields(User user) {
        mRangeText.setText(String.valueOf(user.getRank()));
        mNicknameText.setText(user.getNickName());
        mFirstNameText.setText(user.getFirstName());
        mLastNameText.setText(user.getLastName());
        mPhoneText.setText(user.getPhone());
        mEmailText.setText(user.getEmail());
        mCityText.setText(user.getCity());
    }

    private void showProgressDialog() {
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading user info");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    private void updateUserInfo() {
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                updateUI(currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(LOG_TAG, "loadUserInfo:onCancelled ", databaseError.toException());
            }
        };
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(fireUser.getUid());
        userReference.addValueEventListener(userListener);

        mUserReference = userReference;
        mUserListener = userListener;
    }

    private void startEditActivity() {
        Intent i = new Intent(getActivity(), UserProfileEditActivity.class);
        i.putExtra("USER", currentUser);
        startActivity(i);
    }
}
