package com.customdev.gameland.fragments;

//import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.customdev.gameland.R;
import com.customdev.gameland.models.Event;
import com.customdev.gameland.models.Game;
import com.customdev.gameland.models.Location;
import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventAddFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<Game> mGameList;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private OnEventCreatedListener mOnEventCreatedListener;

    //Custom fields
    private Spinner mCitySpinner;
    private Spinner mClubSpinner;
    private Spinner mTypeSpinner;
    private Spinner mGameSpinner;
    private Spinner mPlayerMaxSpinner;
    private Spinner mPlayerNeedSpinner;
    private Button mChooseTimeBtn;
    private Button mCreateEventBtn;
    private EditText mDescriptionText;

    private Location mLocation;
    private int mType;
    private Game mGame;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private Long mDate;
    private Calendar mCalendar;

    private Event mEvent;

    public EventAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gameList Parameter 1.
     * @return A new instance of fragment EventAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventAddFragment newInstance(ArrayList<Game> gameList) {
        EventAddFragment fragment = new EventAddFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, gameList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGameList = getArguments().getParcelableArrayList(ARG_PARAM1);
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
            mCitySpinner = (Spinner) rootView.findViewById(R.id.choose_city_spinner);
            ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities, android.R.layout.simple_spinner_item);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCitySpinner.setAdapter(cityAdapter);

            mTypeSpinner = (Spinner) rootView.findViewById(R.id.choose_type_spinner);
            ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.types, android.R.layout.simple_spinner_item);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTypeSpinner.setAdapter(typeAdapter);

            mClubSpinner = (Spinner) rootView.findViewById(R.id.choose_club_spinner);
            ArrayAdapter<CharSequence> clubAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.clubs, android.R.layout.simple_spinner_item);
            clubAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mClubSpinner.setAdapter(clubAdapter);

            mGameSpinner = (Spinner) rootView.findViewById(R.id.choose_game_spinner);
            initGameSpinner();

            mPlayerMaxSpinner = (Spinner) rootView.findViewById(R.id.choose_player_max_count_spinner);
            initPlayerMaxSpinner();

            mPlayerNeedSpinner = (Spinner) rootView.findViewById(R.id.choose_player_need_spinner);
            initPlayerNeedSpinner();

            mChooseTimeBtn = (Button) rootView.findViewById(R.id.choose_date_button);
            mChooseTimeBtn.setOnClickListener(this);

            mDescriptionText = (EditText) rootView.findViewById(R.id.description_text);

            mCreateEventBtn = (Button) rootView.findViewById(R.id.create_event_button);
            mCreateEventBtn.setOnClickListener(this);
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
        if (context instanceof OnEventCreatedListener) {
            mOnEventCreatedListener = (OnEventCreatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventCreatedListener");
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
                showDateChooseDialog();
                break;
            case R.id.create_event_button:
                createEvent();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;

        showTimeChooseDialog();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mHour = hourOfDay;
        mMinute = minute;

        setDate();
    }

    private void setDate() {
        mCalendar = Calendar.getInstance();
        mCalendar.set(mYear, mMonth, mDay, mHour, mMinute, 0);
        mDate = mCalendar.getTimeInMillis();

        mChooseTimeBtn.setText(mCalendar.getTime().toString());
    }

    private void showTimeChooseDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
//        tpd.show(getFragmentManager(), "TimePickerDialog");
    }

    private void showDateChooseDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
//        dpd.show(getFragmentManager(), "DatePickerDialog");
    }

    private void initGameSpinner() {
        ArrayAdapter<CharSequence> gameAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        for (Game g : mGameList) {
            gameAdapter.add(g.getName());
        }
        gameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGameSpinner.setAdapter(gameAdapter);
        mGameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGame = mGameList.get(position);
                initPlayerMaxSpinner();
                initPlayerNeedSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPlayerMaxSpinner() {
        int gamePosition = mGameSpinner.getSelectedItemPosition();
        int gameMaxPlayers = mGameList.get(gamePosition).getMaxPlayers();
        int gameMinPlayers = mGameList.get(gamePosition).getMinPlayers();
        ArrayAdapter<Integer> playerMaxAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        for (int i = gameMaxPlayers; i >= gameMinPlayers; i--) {
            playerMaxAdapter.add(i);
        }
        playerMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlayerMaxSpinner.setAdapter(playerMaxAdapter);
        mPlayerMaxSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initPlayerNeedSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPlayerNeedSpinner() {
        int gameMaxPlayers = (int) mPlayerMaxSpinner.getSelectedItem();
        ArrayAdapter<Integer> playerNeedAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item);
        for (int i = 1; i <= gameMaxPlayers; i++) {
            playerNeedAdapter.add(i);
        }
        playerNeedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlayerNeedSpinner.setAdapter(playerNeedAdapter);
    }

    private void createEvent() {
        mEvent = new Event();
        mEvent.setId(1);
        mEvent.setLocation(new Location(1, mCitySpinner.getSelectedItemPosition(), mClubSpinner.getSelectedItemPosition()));
        mEvent.setType(mTypeSpinner.getSelectedItemPosition());
        mEvent.setGame(mGameList.get(mGameSpinner.getSelectedItemPosition()));
        mEvent.setMaxPlayersCount((Integer) mPlayerMaxSpinner.getSelectedItem());
        mEvent.setNeedPlayersCount((Integer) mPlayerNeedSpinner.getSelectedItem());
        if (mCalendar == null) {
            mCalendar = Calendar.getInstance();
        }
        mEvent.setStartTime(mCalendar.getTimeInMillis());
        mEvent.setDescription(mDescriptionText.getText().toString());
        mEvent.setCreatorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mOnEventCreatedListener.onEventCreated(mEvent);
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

    public interface OnEventCreatedListener {
        void onEventCreated(Event event);
    }
}
