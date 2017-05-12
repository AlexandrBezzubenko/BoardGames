package com.customdev.boardgames;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.customdev.boardgames.entities.Event;
import com.customdev.boardgames.fragments.EventAddFragment;
import com.customdev.boardgames.fragments.EventListFragment;
import com.customdev.boardgames.entities.Game;
import com.customdev.boardgames.fragments.HomeScreenFragment;
import com.customdev.boardgames.fragments.UserProfileFragment;

import java.util.ArrayList;

public class MainActivity
        extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeScreenFragment.OnFragmentInteractionListener,
        EventListFragment.OnFragmentInteractionListener,
        UserProfileFragment.OnFragmentInteractionListener,
        EventAddFragment.OnFragmentInteractionListener,
        EventAddFragment.OnEventCreatedListener {

    private Fragment mCurrentFragment, mHomeScreenFragment, mEventAddFragment, mEventListFragment, mUserProfileFragment;
    private BottomNavigationView mBottomNavigationView;

    private ArrayList<Event> mEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Game> gameList = initGameList();

        mHomeScreenFragment = new HomeScreenFragment();
        mEventAddFragment = EventAddFragment.newInstance(gameList);
        mEventListFragment = new EventListFragment();
        mUserProfileFragment = new UserProfileFragment();

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        setFragment(mHomeScreenFragment);
        mBottomNavigationView.setSelectedItemId(R.id.mi_home);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        if (fragment != null) {
            fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fTransaction.replace(R.id.frame_fragment_container, fragment);
            mCurrentFragment = fragment;
        } else {
            fTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fTransaction.remove(mCurrentFragment);
        }
        fTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentCreated(Event event) {
        mEventList.add(event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_event_list_show:
                setFragment(mEventListFragment);
                break;
            case R.id.mi_event_add:
                setFragment(mEventAddFragment);
                break;
            case R.id.mi_home:
                setFragment(mHomeScreenFragment);
                break;
            case R.id.mi_event_search:
                setFragment(null);
                break;
            case R.id.mi_user_profile:
                setFragment(mUserProfileFragment);
                break;
        }
        return true;
    }

    private ArrayList<Game> initGameList() {

        ArrayList<Game> list = new ArrayList<>();

        Game game1 = new Game();
        game1.setId(1);
        game1.setName("Eclipse");
        game1.setMaxPlayers(10);
        game1.setMinPlayers(6);
        game1.setDescription("Eclipse description");
        game1.setDuration(4 * 60 * 60 * 1_000);

        Game game2 = new Game();
        game2.setId(1);
        game2.setName("Game of Thrones");
        game2.setMaxPlayers(8);
        game2.setMinPlayers(5);
        game2.setDescription("Game of Thrones description");
        game2.setDuration(6 * 60 * 60 * 1_000);

        Game game3 = new Game();
        game3.setId(1);
        game3.setName("Blood Rage");
        game3.setMaxPlayers(6);
        game3.setMinPlayers(3);
        game3.setDescription("Blood Rage description");
        game3.setDuration(4 * 60 * 60 * 1_000);

        list.add(game1);
        list.add(game2);
        list.add(game3);

        return list;
    }
}
