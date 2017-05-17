package com.customdev.boardgames.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customdev.boardgames.R;
import com.customdev.boardgames.customViews.UserProfileImage;
import com.customdev.boardgames.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private User mUser;

    private OnFragmentInteractionListener mListener;

    // Custom fields
    private UserProfileImage mProfileImage;
    private TextView mRangeText;
    private TextView mNicknameText;
    private TextView mFirstnameText;
    private TextView mLastnameText;
    private TextView mPhoneText;
    private TextView mEmailText;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View rootView = getView();
        if (rootView != null) {
            mProfileImage = (UserProfileImage) rootView.findViewById(R.id.user_avatar_image);
            final String str = mUser.getAvatarTag();
            final int resId = getActivity().getResources().getIdentifier(str, "drawable", getActivity().getPackageName());
            mProfileImage.setImageResource(resId);

            mRangeText = (TextView) rootView.findViewById(R.id.user_range_text);
            mRangeText.setText(String.valueOf(mUser.getRange()));

            mNicknameText = (TextView) rootView.findViewById(R.id.user_nickname_text);
            mNicknameText.setText(mUser.getNickname());

            mFirstnameText = (TextView) rootView.findViewById(R.id.user_firstname_text);
            mFirstnameText.setText(mUser.getFistName());

            mLastnameText = (TextView) rootView.findViewById(R.id.user_lastname_text);
            mLastnameText.setText(mUser.getLastName());

            mPhoneText = (TextView) rootView.findViewById(R.id.user_phone_text);
            mPhoneText.setText(mUser.getPhone());

            mEmailText = (TextView) rootView.findViewById(R.id.user_email_text);
            mEmailText.setText(mUser.getEmail());
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
