package com.unimagdalena.android.app.domiciliosmilcarnes.interfaces;

import android.content.Context;
import android.view.View;

public interface MainActivityView {

    void setupViews();

    void singUp();

    void editProfile();

    void hideLogin();

    void showLogin();

    void showKeyboard(View view, Context context);

    void hideKeyboard(View view, Context context);

    void clearInputs();

    void showErrorMessage();

    void toast(String message);

    void updateToolbarMenu(boolean showWelcomeMessage, String name);
}
