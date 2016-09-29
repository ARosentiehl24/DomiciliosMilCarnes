package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.mmin18.widget.RealtimeBlurView;
import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.inputContainer)
    LinearLayout inputContainer;

    @BindView(R.id.blurView)
    RealtimeBlurView blurView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        blurView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, inputContainer.getHeight()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Boolean thereConnectedUser = PreferencesManager.getBoolean(getString(R.string.there_connected_user));

        if (thereConnectedUser) {
            getMenuInflater().inflate(R.menu.login_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.no_login_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
