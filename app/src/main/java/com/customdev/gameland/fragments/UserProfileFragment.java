package com.customdev.gameland.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.customdev.gameland.LoginActivity;
import com.customdev.gameland.R;
import com.customdev.gameland.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = "UserProfileFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private User mUser;

    private OnFragmentInteractionListener mListener;

    // Custom fields
//    private UserProfileImage mProfileImage;
    private CircleImageView mProfileImage;
    private TextView mRangeText;
    private TextView mNicknameText;
    private TextView mFirstNameText;
    private TextView mLastNameText;
    private TextView mPhoneText;
    private TextView mEmailText;
    private TextView mCityText;

    private FloatingActionButton mEditFloatingActionButton;
    private Button mConfirmButton;
    private Button mLogout;

    private boolean isEditable = false;

    private DatabaseReference mUserReference;
    private ValueEventListener mUserListener;

    private User currentUser;
    private User newUser;

    private File file;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(User user) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

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

    @Override
    public void onStop() {
        super.onStop();

        if (mUserReference != null) {
            mUserReference.removeEventListener(mUserListener);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View rootView = getView();
        if (rootView != null) {
            mProfileImage = (CircleImageView) rootView.findViewById(R.id.user_avatar_image);
            mRangeText = (TextView) rootView.findViewById(R.id.user_range_text);
            mNicknameText = (TextView) rootView.findViewById(R.id.user_nickname_text);
            mFirstNameText = (TextView) rootView.findViewById(R.id.user_firstname_text);
            mLastNameText = (TextView) rootView.findViewById(R.id.user_lastname_text);
            mPhoneText = (TextView) rootView.findViewById(R.id.user_phone_text);
            mEmailText = (EditText) rootView.findViewById(R.id.user_email_text);
            mCityText = (TextView) rootView.findViewById(R.id.user_city_text);

            mEditFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.edit_fab);
            mEditFloatingActionButton.setOnClickListener(this);

            mConfirmButton = (Button) rootView.findViewById(R.id.confirm_btn);
            mConfirmButton.setOnClickListener(this);

            mLogout = (Button) rootView.findViewById(R.id.logout_btn);
            mLogout.setOnClickListener(this);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_fab:
                allowEdit();
                break;
            case R.id.confirm_btn:
                confirmEdit();
                break;
            case R.id.logout_btn:
                logout();
                break;
        }
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

//        final int resId = getActivity().getResources().getIdentifier("avatar", "drawable", getActivity().getPackageName());
//        mProfileImage.setImageResource(resId);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("avatar.png");
        String path = imageRef.getPath();
        String str = imageRef.toString();
        Uri mUri = Uri.parse(str);
        file = null;
        try {
            file = File.createTempFile("avatar", "png");
            imageRef.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                            setAvatar();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (localFile != null) {
//            String filePath = localFile.getPath();
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//
//            mProfileImage.setImageBitmap(bitmap);
//        }

        mRangeText.setText(String.valueOf(user.getRank()));
        mNicknameText.setText(user.getNickname());
        mFirstNameText.setText(user.getFirstName());
        mLastNameText.setText(user.getLastName());
        mPhoneText.setText(user.getPhone());
        mEmailText.setText(user.getEmail());
        mCityText.setText(user.getCity());
    }

    private void setAvatar() {
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        mProfileImage.setImageBitmap(bitmap);
    }

    private void updateDatabase() {
        Map<String, Object> updates = new HashMap<>();

        FirebaseAuth fireAuth = FirebaseAuth.getInstance();
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users").child(fireUser.getUid());

        String currentNickname = currentUser.getNickname();
        String currentFirstName = currentUser.getFirstName();
        String currentLastName = currentUser.getLastName();
        String currentPhone = currentUser.getPhone();
        String currentCity = currentUser.getCity();
//        String currentEmail = currentUser.getEmail();

        String newNickname = newUser.getNickname();
        String newFirstName = newUser.getFirstName();
        String newLastName = newUser.getLastName();
        String newPhone = newUser.getPhone();
        String newCity = newUser.getCity();
//        String newEmail = newUser.getEmail();

        if (!currentNickname.equals(newNickname)) {
            updates.put("nickname", newNickname);
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
//        if (!currentEmail.equals(newEmail)) {
//            updates.put("email", newEmail);
//
//            fireUser.updateEmail(newEmail)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(LOG_TAG, "User email address updated.");
//                            }
//                        }
//                    });
//        }

        if (!updates.isEmpty()) {
            userReference.updateChildren(updates);
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
        newUser.setNickname(nickname);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setCity(city);

        return valid;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
