package com.easycore.digestio.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jakub Begera (jakub@easycoreapps.com) on 09.01.17.
 */
public class Preferences {

    private final static String KEY_SAVED_SESSION = "saved_session";

    private final SharedPreferences preferences;
    private final Context context;

    public Preferences(SharedPreferences preferences, Context context) {
        this.preferences = preferences;
        this.context = context;
    }


    public void saveSessionID(String sessionID) {
        preferences.edit()
                .putString(KEY_SAVED_SESSION, sessionID)
                .apply();
    }

    public void removeSessionID() {
        preferences.edit()
                .remove(KEY_SAVED_SESSION)
                .apply();
    }

    public String getSessionID() {
        return preferences.getString(KEY_SAVED_SESSION, null);
    }
}
