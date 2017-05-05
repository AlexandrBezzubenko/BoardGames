package com.customdev.boardgames.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.customdev.boardgames.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventAddFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    //Custom fields
    private Spinner mTypeSpinner;
    private Spinner mClubSpinner;
    private Spinner mGameSpinner;
    private Spinner mPlayerMaxSpinner;
    private Spinner mPlayerNeedSpinner;
    private Button mChooseTime;

    public EventAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventAddFragment newInstance(String param1, String param2) {
        EventAddFragment fragment = new EventAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View rootView = getView();
        if (rootView != null) {
            mTypeSpinner = (Spinner) rootView.findViewById(R.id.choose_type_spinner);
            ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.types, android.R.layout.simple_spinner_item);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTypeSpinner.setAdapter(typeAdapter);

            mClubSpinner = (Spinner) rootView.findViewById(R.id.choose_club_spinner);
            ArrayAdapter<CharSequence> clubAdapter = ArrayAdapter.createFromResource(getContext(), R.array.clubs, android.R.layout.simple_spinner_item);
            clubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mClubSpinner.setAdapter(clubAdapter);

            mGameSpinner = (Spinner) rootView.findViewById(R.id.choose_game_spinner);
            ArrayAdapter<CharSequence> gameAdapter = ArrayAdapter.createFromResource(getContext(), R.array.games, android.R.layout.simple_spinner_item);
            gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mGameSpinner.setAdapter(gameAdapter);

            List<Integer> maxCount = new ArrayList<>();
            maxCount.add(9);
            maxCount.add(8);
            maxCount.add(7);
            maxCount.add(6);
            maxCount.add(5);
            mPlayerMaxSpinner = (Spinner) rootView.findViewById(R.id.choose_player_max_count_spinner);
            ArrayAdapter<Integer> playerMaxAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, maxCount);
            playerMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mPlayerMaxSpinner.setAdapter(playerMaxAdapter);

            List<Integer> needCount = new ArrayList<>();
            needCount.add(1);
            needCount.add(2);
            needCount.add(3);
            needCount.add(4);
            needCount.add(5);
            mPlayerNeedSpinner = (Spinner) rootView.findViewById(R.id.choose_player_need_spinner);
            ArrayAdapter<Integer> playerNeedAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, needCount);
            playerNeedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mPlayerNeedSpinner.setAdapter(playerNeedAdapter);

            mChooseTime = (Button) rootView.findViewById(R.id.choose_date_button);
            mChooseTime.setOnClickListener(this);
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
            case R.id.choose_date_button:

                break;
        }
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
