package com.customdev.boardgames.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.customdev.boardgames.R;
import com.customdev.boardgames.adapters.EventListAdapter;
import com.customdev.boardgames.models.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeScreenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeScreenFragment extends Fragment implements EventListAdapter.OnEventButtonClickListener {

    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Event> mEventList;

    private OnFragmentInteractionListener mListener;

    //Custom fields
    private RecyclerView mRecyclerView;
    private EventListAdapter mAdapter;

    public HomeScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventList Parameter 1.
     * @return A new instance of fragment HomeScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreenFragment newInstance(ArrayList<Event> eventList) {
        HomeScreenFragment fragment = new HomeScreenFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, eventList);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEventList = getArguments().getParcelableArrayList(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View rootView = getView();
        if (rootView != null) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.event_list_recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mAdapter = new EventListAdapter(getActivity(), mEventList, this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
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
    public void OnButtonClick(View view, int position) {
        switch (view.getId()) {
            case R.id.button_event_edit:
                Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_event_delete:
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();
                mEventList.remove(position);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.button_event_confirm:
                Toast.makeText(getActivity(), "Confirm", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_event_deny:
                Toast.makeText(getActivity(), "Deny", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_event_invite:
                Toast.makeText(getActivity(), "Invite", Toast.LENGTH_SHORT).show();
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
