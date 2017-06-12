package com.customdev.gameland;

import android.app.Application;

import com.customdev.gameland.models.User;
import com.customdev.gameland.utils.DatabaseManager;

public class App extends Application {

    private static App mInstance;

    private User mUser;

    private App() {
        mInstance = this;
    }

    public static App getInstance() {
        if (mInstance == null) {
            new App();
        }
        return mInstance;
    }

//

}
