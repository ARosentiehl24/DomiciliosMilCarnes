package com.unimagdalena.android.app.domiciliosmilcarnes.interfaces;

public interface MainActivityPresenter {

    void onCreate();

    void itemLogOut();

    void itemLogIn(Boolean isLoginVisible);

    void itemSearch();

    void itemSignUp();

    void login(String nickName, String password);
}
