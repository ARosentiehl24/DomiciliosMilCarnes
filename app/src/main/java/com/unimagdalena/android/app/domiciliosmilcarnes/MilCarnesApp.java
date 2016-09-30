package com.unimagdalena.android.app.domiciliosmilcarnes;

import android.app.Application;
import android.content.Context;

import com.shawnlin.preferencesmanager.PreferencesManager;


public class MilCarnesApp extends Application {

    public static String PACKAGE_NAME;

    public static String SETTINGS_PREFERENCES;

    private PreferencesManager preferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();

        PACKAGE_NAME = getPackageName().toUpperCase();

        SETTINGS_PREFERENCES = PACKAGE_NAME + ".SETTINGS";

        preferencesManager = new PreferencesManager(this);
        setPreferencesManager(SETTINGS_PREFERENCES);
    }

    public void setPreferencesManager(String name) {
        preferencesManager.setName(name);
        preferencesManager.setMode(Context.MODE_PRIVATE);
        preferencesManager.init();
    }
}
