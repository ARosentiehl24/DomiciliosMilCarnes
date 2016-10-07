package com.unimagdalena.android.app.domiciliosmilcarnes.presenter;

import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityPresenter;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityView;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;


public class IMainActivityPresenter implements MainActivityPresenter {

    private MainActivityView mainActivityView;

    public IMainActivityPresenter(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void onCreate() {
        mainActivityView.setupViews();
    }

    @Override
    public void itemLogOut() {
        mainActivityView.updateToolbarMenu(false, null);
    }

    @Override
    public void itemLogIn(Boolean isLoginVisible) {
        if (isLoginVisible) {
            mainActivityView.hideLogin();
        } else {
            mainActivityView.showLogin();
        }
    }

    @Override
    public void itemSearch() {

    }

    @Override
    public void itemEdit() {
        mainActivityView.editProfile();
    }

    @Override
    public void itemSignUp() {
        mainActivityView.singUp();
    }

    @Override
    public void login(String email, String password) {
        User storedUser = PreferencesManager.getObject(email, User.class);

        if (storedUser == null) {
            mainActivityView.clearInputs();
            mainActivityView.showErrorMessage();
        } else {
            if (password.equals(storedUser.getPassword())) {
                PreferencesManager.putString("connected_user", email);

                mainActivityView.updateToolbarMenu(true, storedUser.getName());
            } else {
                mainActivityView.clearInputs();
                mainActivityView.showErrorMessage();
            }
        }
    }

    @Override
    public void itemProfile() {
        mainActivityView.showProfile();
    }
}
