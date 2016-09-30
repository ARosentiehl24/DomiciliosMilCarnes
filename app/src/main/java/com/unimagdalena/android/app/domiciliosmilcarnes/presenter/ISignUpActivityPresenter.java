package com.unimagdalena.android.app.domiciliosmilcarnes.presenter;

import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.SignUpActivityPresenter;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.SignUpActivityView;

public class ISignUpActivityPresenter implements SignUpActivityPresenter {

    private SignUpActivityView signUpActivityView;

    public ISignUpActivityPresenter(SignUpActivityView signUpActivityView) {
        this.signUpActivityView = signUpActivityView;
    }

    @Override
    public void onCreate() {
        signUpActivityView.setupViews();
    }
}
