package com.unimagdalena.android.app.domiciliosmilcarnes.interfaces;

import android.content.Context;

import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;

public interface MainActivityPresenter {

    void onCreate();

    void itemLogOut();

    void itemLogIn(Boolean isLoginVisible);

    void itemSearch();

    void itemEdit();

    void itemSignUp();

    void login(Context context, String id, String password);

    void itemProfile();
}
