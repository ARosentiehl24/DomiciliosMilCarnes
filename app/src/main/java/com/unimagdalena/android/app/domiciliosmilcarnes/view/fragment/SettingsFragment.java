package com.unimagdalena.android.app.domiciliosmilcarnes.view.fragment;


import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        String ip = MilCarnesApp.milCarnesApp.getIP();
        String port = MilCarnesApp.milCarnesApp.getPORT();

        EditTextPreference ipPreference = (EditTextPreference) findPreference("ip");
        ipPreference.setText(ip);
        ipPreference.setOnPreferenceChangeListener(this);
        ipPreference.setSummary(ip);

        EditTextPreference portPreference = (EditTextPreference) findPreference("port");
        portPreference.setText(port);
        portPreference.setOnPreferenceChangeListener(this);
        portPreference.setSummary(port);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        PreferencesManager.putString(preference.getKey(), o.toString());

        preference.setSummary(o.toString());

        return true;
    }
}
