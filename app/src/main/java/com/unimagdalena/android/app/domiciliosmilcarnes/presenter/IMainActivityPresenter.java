package com.unimagdalena.android.app.domiciliosmilcarnes.presenter;

import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityPresenter;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityView;


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
    public void itemSignUp() {
        mainActivityView.singUp();
    }

    @Override
    public void login(String nickName, String password) {

    }
}
