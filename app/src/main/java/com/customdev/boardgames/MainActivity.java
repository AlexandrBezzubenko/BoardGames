package com.customdev.boardgames;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.customdev.boardgames.fragments.EventAddFragment;
import com.customdev.boardgames.fragments.EventListFragment;
import com.customdev.boardgames.fragments.HomeScreenFragment;
import com.customdev.boardgames.fragments.UserProfileFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        HomeScreenFragment.OnFragmentInteractionListener,
        EventListFragment.OnFragmentInteractionListener,
        UserProfileFragment.OnFragmentInteractionListener,
        EventAddFragment.OnFragmentInteractionListener {

    private Event mEvent;

    private Fragment mCurrentFragment,mHomeScreen, mEventAdd, mEventList, mUserProfile;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHomeScreen = new HomeScreenFragment();
        mEventAdd = new EventAddFragment();
        mEventList = new EventListFragment();
        mUserProfile = new UserProfileFragment();

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        setFragment(mHomeScreen);
        mBottomNavigationView.setSelectedItemId(R.id.mi_home);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
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



    private void init() {
        User creator = new User("Michael");

        HashMap<Integer, User> playerList = new HashMap<>();
        playerList.put(creator.getId(), creator);

        Game game = new Game();
        game.setId(1);
        game.setName("Game of Thrones");
        game.setDescription("Bla-Bla-Bla");
        game.setMaxPlayers(10);
        game.setMinPlayers(4);
        game.setDuration((long) (3 * 60 * 60 * 1000));
        game.setAvaliable(true);

        Location location = new Location();

        mEvent = new Event();
        mEvent.setId(1);
        mEvent.setCreator(creator);
        mEvent.setGame(game);
        mEvent.setLocation(location);
        mEvent.setMaxPlayersCount(8);
        mEvent.setPlayerList(playerList);
        mEvent.setStartTime(Calendar.getInstance().getTimeInMillis());

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_event_list_show:
                setFragment(mEventList);
                break;
            case R.id.mi_event_add:
                setFragment(mEventAdd);
                break;
            case R.id.mi_home:
                setFragment(mHomeScreen);
                break;
            case R.id.mi_event_search:
                setFragment(null);
                break;
            case R.id.mi_user_profile:
                setFragment(mUserProfile);
                break;
        }
        return true;
    }
}
