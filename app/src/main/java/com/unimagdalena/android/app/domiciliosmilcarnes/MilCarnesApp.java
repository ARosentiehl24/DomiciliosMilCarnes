package com.unimagdalena.android.app.domiciliosmilcarnes;

import android.app.Application;

import com.shawnlin.preferencesmanager.PreferencesManager;


public class MilCarnesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new PreferencesManager(this).setName(getPackageName()).init();
    }
}
