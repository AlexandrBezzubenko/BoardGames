package com.customdev.gameland;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Random;

import com.customdev.gameland.models.Event;
import com.customdev.gameland.fragments.EventAddFragment;
import com.customdev.gameland.fragments.EventListFragment;
import com.customdev.gameland.models.Game;
import com.customdev.gameland.fragments.HomeScreenFragment;
import com.customdev.gameland.fragments.UserProfileFragment;
import com.customdev.gameland.models.Location;
import com.customdev.gameland.models.User;
import com.customdev.gameland.utils.DatabaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    private ArrayList<Event> mEventList = new ArrayList<>();

    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            ArrayList<Game> gameList = initGameList();
            mEventList = initEventList(gameList);

            mHomeScreenFragment = HomeScreenFragment.newInstance(mEventList);
            mEventAddFragment = EventAddFragment.newInstance(gameList);
            mEventListFragment = new EventListFragment();
            mUserProfileFragment = new UserProfileFragment();

            setFragment(mHomeScreenFragment);
            mBottomNavigationView.setSelectedItemId(R.id.mi_home);
        } else {
            startLoginActivity();
        }
    }

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
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
    public void onEventCreated(Event event) {
        mEventList.add(event);
        int menuItemId = mBottomNavigationView.getMenu().findItem(R.id.mi_home).getItemId();
        mBottomNavigationView.setSelectedItemId(menuItemId);
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

    private void startLoginActivity() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

    private ArrayList<Game> initGameList() {

        ArrayList<Game> list = new ArrayList<>();

        Game game1 = new Game();
        game1.setId(1);
        game1.setName("Eclipse");
        game1.setLogoTag("eclipse");
        game1.setMaxPlayers(10);
        game1.setMinPlayers(6);
        game1.setDescription("Eclipse description");
        game1.setDuration(4 * 60 * 60 * 1_000);

        Game game2 = new Game();
        game2.setId(2);
        game2.setName("Game of Thrones");
        game2.setLogoTag("thrones");
        game2.setMaxPlayers(8);
        game2.setMinPlayers(5);
        game2.setDescription("Game of Thrones description");
        game2.setDuration(6 * 60 * 60 * 1_000);

        Game game3 = new Game();
        game3.setId(3);
        game3.setName("Blood Rage");
        game3.setLogoTag("blood_rage");
        game3.setMaxPlayers(6);
        game3.setMinPlayers(3);
        game3.setDescription("Blood Rage description");
        game3.setDuration(4 * 60 * 60 * 1_000);

        Game game4 = new Game();
        game4.setId(4);
        game4.setName("Agricola");
        game4.setLogoTag("agricola");
        game4.setMaxPlayers(4);
        game4.setMinPlayers(2);
        game4.setDescription("Agricola description");
        game4.setDuration(2 * 60 * 60 * 1_000);

        list.add(game1);
        list.add(game2);
        list.add(game3);
        list.add(game4);

        return list;
    }

    private ArrayList<Event> initEventList(ArrayList<Game> games) {

        ArrayList<Event> list = new ArrayList<>();
        Location location = new Location();
        Game game = games.get(randInt(0, games.size() - 1));

        location.setId(1);
        location.setCity(0);
        location.setClub(0);

        Event event1 = new Event();
        event1.setId(1);
        event1.setLocation(location);
        event1.setType(0);
        event1.setGame(game);
        event1.setMaxPlayersCount(game.getMinPlayers());
        event1.setNeedPlayersCount(game.getMinPlayers());
        event1.setStartTime(System.currentTimeMillis());
        event1.setDescription("Description");
        event1.setCreatorId(mUser.getUid());

        game = games.get(randInt(0, games.size() - 1));

        location.setId(2);
        location.setCity(1);
        location.setClub(1);

        Event event2 = new Event();
        event2.setId(2);
        event2.setLocation(location);
        event2.setType(0);
        event2.setGame(game);
        event2.setMaxPlayersCount(game.getMinPlayers());
        event2.setNeedPlayersCount(game.getMinPlayers());
        event2.setStartTime(System.currentTimeMillis());
        event2.setDescription("Description");
        event2.setCreatorId(mUser.getUid());

        game = games.get(randInt(0, games.size() - 1));

        location.setId(3);
        location.setCity(2);
        location.setClub(2);

        Event event3 = new Event();
        event3.setId(3);
        event3.setLocation(location);
        event3.setType(0);
        event3.setGame(game);
        event3.setMaxPlayersCount(game.getMinPlayers());
        event3.setNeedPlayersCount(game.getMinPlayers());
        event3.setStartTime(System.currentTimeMillis());
        event3.setDescription("Description");
        event3.setCreatorId(mUser.getUid());

        game = games.get(randInt(0, games.size() - 1));

        location.setId(4);
        location.setCity(3);
        location.setClub(0);

        Event event4 = new Event();
        event4.setId(4);
        event4.setLocation(location);
        event4.setType(0);
        event4.setGame(game);
        event4.setMaxPlayersCount(game.getMinPlayers());
        event4.setNeedPlayersCount(game.getMinPlayers());
        event4.setStartTime(System.currentTimeMillis());
        event4.setDescription("Description");
        event4.setCreatorId(mUser.getUid());

        game = games.get(randInt(0, games.size() - 1));

        location.setId(5);
        location.setCity(4);
        location.setClub(0);

        Event event5 = new Event();
        event5.setId(5);
        event5.setLocation(location);
        event5.setType(0);
        event5.setGame(game);
        event5.setMaxPlayersCount(game.getMinPlayers());
        event5.setNeedPlayersCount(game.getMinPlayers());
        event5.setStartTime(System.currentTimeMillis());
        event5.setDescription("Description");
        event5.setCreatorId(mUser.getUid());

        game = games.get(randInt(0, games.size() - 1));

        location.setId(6);
        location.setCity(0);
        location.setClub(0);

        Event event6 = new Event();
        event6.setId(6);
        event6.setLocation(location);
        event6.setType(0);
        event6.setGame(game);
        event6.setMaxPlayersCount(game.getMinPlayers());
        event6.setNeedPlayersCount(game.getMinPlayers());
        event6.setStartTime(System.currentTimeMillis());
        event6.setDescription("Description");
        event6.setCreatorId(mUser.getUid());

        list.add(event1);
        list.add(event2);
        list.add(event3);
        list.add(event4);
        list.add(event5);
        list.add(event6);

        return list;
    }

    private int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
