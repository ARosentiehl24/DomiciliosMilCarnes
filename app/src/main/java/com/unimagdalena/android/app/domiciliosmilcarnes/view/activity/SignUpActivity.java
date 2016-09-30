package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.SignUpActivityView;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;
import com.unimagdalena.android.app.domiciliosmilcarnes.presenter.ISignUpActivityPresenter;

import org.fingerlinks.mobile.android.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityView, VerticalStepperForm {

    private AppCompatEditText etId;
    private AppCompatEditText etName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etNickName;
    private AppCompatEditText etPassword;
    private AppCompatEditText etRepeatPassword;
    private AppCompatEditText etPhoneNumber;
    private AppCompatEditText etAddress;

    private ISignUpActivityPresenter iSignUpActivityPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.verticalStepperFormLayout)
    VerticalStepperFormLayout verticalStepperFormLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        iSignUpActivityPresenter = new ISignUpActivityPresenter(this);
        iSignUpActivityPresenter.onCreate();

    }

    @Override
    public void onBackPressed() {
        Navigator.with(this).utils().finishWithAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] titles = {getString(R.string.id),
                getString(R.string.name),
                getString(R.string.last_name),
                getString(R.string.nickname),
                getString(R.string.password),
                getString(R.string.repeat_password),
                getString(R.string.phone_number),
                getString(R.string.address)};

        String[] subTitles = {getString(R.string.request_identification),
                getString(R.string.request_name),
                getString(R.string.request_last_name),
                getString(R.string.request_nickname),
                getString(R.string.request_password),
                getString(R.string.request_repeat_password),
                getString(R.string.request_phone_number),
                getString(R.string.request_address)};

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        VerticalStepperFormLayout.Builder.newInstance(verticalStepperFormLayout, titles, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)
                .stepsSubtitles(subTitles)
                .init();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;

        switch (stepNumber) {
            case 0:
                etId = new AppCompatEditText(this);
                etId.setHint(R.string.identification);
                etId.setInputType(InputType.TYPE_CLASS_NUMBER);
                etId.setMaxLines(1);
                etId.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etId;

                break;
            case 1:
                etName = new AppCompatEditText(this);
                etName.setHint(R.string.name);
                etName.setInputType(InputType.TYPE_CLASS_TEXT);
                etName.setMaxLines(1);
                etName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etName;

                break;
            case 2:
                etLastName = new AppCompatEditText(this);
                etLastName.setHint(R.string.last_name);
                etLastName.setInputType(InputType.TYPE_CLASS_TEXT);
                etLastName.setMaxLines(1);
                etLastName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etLastName;

                break;
            case 3:
                etNickName = new AppCompatEditText(this);
                etNickName.setHint(R.string.nickname);
                etNickName.setInputType(InputType.TYPE_CLASS_TEXT);
                etNickName.setMaxLines(1);
                etNickName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etNickName;

                break;
            case 4:
                etPassword = new AppCompatEditText(this);
                etPassword.setHint(R.string.password);
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setMaxLines(1);
                etPassword.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etPassword;

                break;
            case 5:
                etRepeatPassword = new AppCompatEditText(this);
                etRepeatPassword.setHint(R.string.repeat_password);
                etRepeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etRepeatPassword.setMaxLines(1);
                etRepeatPassword.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etRepeatPassword;

                break;
            case 6:
                etPhoneNumber = new AppCompatEditText(this);
                etPhoneNumber.setHint(R.string.phone_number);
                etPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                etPhoneNumber.setMaxLines(1);
                etPhoneNumber.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etPhoneNumber;

                break;
            case 7:
                etAddress = new AppCompatEditText(this);
                etAddress.setHint(R.string.address);
                etAddress.setInputType(InputType.TYPE_CLASS_TEXT);
                etAddress.setMaxLines(1);
                etAddress.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                view = etAddress;

                break;
        }

        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                verticalStepperFormLayout.setStepAsCompleted(0);
                break;
            case 1:
                verticalStepperFormLayout.setStepAsCompleted(1);
                break;
            case 2:
                verticalStepperFormLayout.setStepAsCompleted(2);
                break;
            case 3:
                verticalStepperFormLayout.setStepAsCompleted(3);
                break;
            case 4:
                verticalStepperFormLayout.setStepAsCompleted(4);
                break;
            case 5:
                verticalStepperFormLayout.setStepAsCompleted(5);
                break;
            case 6:
                verticalStepperFormLayout.setStepAsCompleted(6);
                break;
            case 7:
                verticalStepperFormLayout.setStepAsCompleted(7);
                break;
        }
    }

    public void showKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void sendData() {
        if (etId.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(0, false);

            etId.requestFocus();
            showKeyboard(etId, this);
            
            toast(getString(R.string.field_empty_message));
        } else if (etName.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(1, false);

            etName.requestFocus();
            showKeyboard(etName, this);

            toast(getString(R.string.field_empty_message));
        } else if (etLastName.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(2, false);

            etLastName.requestFocus();
            showKeyboard(etLastName, this);

            toast(getString(R.string.field_empty_message));
        } else if (etNickName.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(3, false);

            etNickName.requestFocus();
            showKeyboard(etNickName, this);

            toast(getString(R.string.field_empty_message));
        } else if (etPassword.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(4, false);

            etPassword.requestFocus();
            showKeyboard(etPassword, this);

            toast(getString(R.string.field_empty_message));
        } else if (etRepeatPassword.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(5, false);

            etRepeatPassword.requestFocus();
            showKeyboard(etRepeatPassword, this);

            toast(getString(R.string.field_empty_message));
        } else if (etPhoneNumber.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(6, false);

            etPhoneNumber.requestFocus();
            showKeyboard(etPhoneNumber, this);

            toast(getString(R.string.field_empty_message));
        } else if (etAddress.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(7, false);

            etAddress.requestFocus();
            showKeyboard(etAddress, this);

            toast(getString(R.string.field_empty_message));
        } else {
            User storedUser = PreferencesManager.getObject(etNickName.getText().toString(), User.class);

            if (storedUser == null) {
                if (etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
                    User newUser = new User(Integer.valueOf(etId.getText().toString()),
                            etName.getText().toString(),
                            etLastName.getText().toString(),
                            etNickName.getText().toString(),
                            etPassword.getText().toString(),
                            etPhoneNumber.getText().toString(),
                            etAddress.getText().toString());

                    PreferencesManager.putObject(etNickName.getText().toString(), newUser);

                    toast(getString(R.string.successful_registration_message));

                    onBackPressed();
                } else {
                    verticalStepperFormLayout.goToStep(5, false);

                    etRepeatPassword.requestFocus();
                    showKeyboard(etRepeatPassword, this);

                    toast(getString(R.string.passwords_do_not_match_message));
                }
            } else {
                if (etId.getText().toString().equals(String.valueOf(storedUser.getId()))) {
                    verticalStepperFormLayout.goToStep(0, false);

                    etId.requestFocus();
                    showKeyboard(etId, this);

                    toast(getString(R.string.id_already_in_use_message));
                } else if (etNickName.getText().toString().equals(storedUser.getNickName())) {
                    verticalStepperFormLayout.goToStep(3, false);

                    etNickName.requestFocus();
                    showKeyboard(etNickName, this);

                    toast(getString(R.string.nickname_already_in_use_message));
                }
            }
        }
    }
}
