package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.SignUpActivityView;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.VolleySingleton;
import com.unimagdalena.android.app.domiciliosmilcarnes.presenter.ISignUpActivityPresenter;

import org.fingerlinks.mobile.android.navigator.Navigator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class SignUpActivity extends AppCompatActivity implements SignUpActivityView, VerticalStepperForm {

    private AppCompatEditText etId;
    private AppCompatEditText etName;
    private AppCompatEditText etLastName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etRepeatPassword;
    private AppCompatEditText etPhoneNumber;
    private AppCompatEditText etAddress;
    private Boolean isInEditMode = false;
    private User user;

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

        String[] titles = {getString(R.string.identification),
                getString(R.string.name),
                getString(R.string.last_name),
                getString(R.string.email),
                getString(R.string.pass),
                getString(R.string.repeat_password),
                getString(R.string.phone_number),
                getString(R.string.address)};

        String[] subTitles = {getString(R.string.request_identification),
                getString(R.string.request_name),
                getString(R.string.request_last_name),
                getString(R.string.request_email),
                getString(R.string.request_password),
                getString(R.string.request_repeat_password),
                getString(R.string.request_phone_number),
                getString(R.string.request_address)};

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        int colorAccent = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

        VerticalStepperFormLayout.Builder builder = VerticalStepperFormLayout.Builder.newInstance(verticalStepperFormLayout, titles, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)
                .stepsSubtitles(subTitles)
                .buttonTextColor(colorAccent)
                .buttonPressedBackgroundColor(colorPrimaryDark)
                .buttonPressedTextColor(colorAccent)
                .stepNumberTextColor(colorAccent)
                .stepTitleTextColor(colorPrimary)
                .stepSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor))
                .showVerticalLineWhenStepsAreCollapsed(false);
        builder.init();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            isInEditMode = bundle.getBoolean("edit", false);
        }

        if (isInEditMode) {
            setTitle("Modificar Perfil");

            user = (User) getIntent().getSerializableExtra(getString(R.string.connected_user));

            etId.setText(String.valueOf(user.getCedula()));
            etName.setText(user.getNombres());
            etLastName.setText(user.getApellidos());
            etEmail.setText(user.getCorreo());
            etPhoneNumber.setText(user.getTelefono());
            etAddress.setText(user.getDireccion());

            etId.setEnabled(false);
            etEmail.setEnabled(false);

            etPassword.setHint("Contraseña anterior");
            etPassword.setEnabled(false);

            etRepeatPassword.setHint("Nueva contraseña");
            etRepeatPassword.setEnabled(false);

            verticalStepperFormLayout.setStepTitle(4, "Contraseña anterior");
            verticalStepperFormLayout.setStepSubtitle(4, "Ingrese la contraseña anterior");

            verticalStepperFormLayout.setStepTitle(5, "Nueva contraseña");
            verticalStepperFormLayout.setStepSubtitle(5, "Ingrese la nueva contraseña");
        }
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
                etId.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etId.setInputType(InputType.TYPE_CLASS_NUMBER);
                etId.setMaxLines(1);
                etId.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etId.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etId;

                break;
            case 1:
                etName = new AppCompatEditText(this);
                etName.setHint(R.string.name);
                etName.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etName.setInputType(InputType.TYPE_CLASS_TEXT);
                etName.setMaxLines(1);
                etName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etName.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etName;

                break;
            case 2:
                etLastName = new AppCompatEditText(this);
                etLastName.setHint(R.string.last_name);
                etLastName.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etLastName.setInputType(InputType.TYPE_CLASS_TEXT);
                etLastName.setMaxLines(1);
                etLastName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etLastName.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etLastName;

                break;
            case 3:
                etEmail = new AppCompatEditText(this);
                etEmail.setHint(R.string.email);
                etEmail.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                etEmail.setMaxLines(1);
                etEmail.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etEmail.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etEmail;

                break;
            case 4:
                etPassword = new AppCompatEditText(this);
                etPassword.setHint(R.string.pass);
                etPassword.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPassword.setMaxLines(1);
                etPassword.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etPassword.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etPassword;

                break;
            case 5:
                etRepeatPassword = new AppCompatEditText(this);
                etRepeatPassword.setHint(R.string.repeat_password);
                etRepeatPassword.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etRepeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etRepeatPassword.setMaxLines(1);
                etRepeatPassword.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etRepeatPassword.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etRepeatPassword;

                break;
            case 6:
                etPhoneNumber = new AppCompatEditText(this);
                etPhoneNumber.setHint(R.string.phone_number);
                etPhoneNumber.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                etPhoneNumber.setMaxLines(1);
                etPhoneNumber.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etPhoneNumber.setTextColor(ContextCompat.getColor(this, R.color.textColor));

                view = etPhoneNumber;

                break;
            case 7:
                etAddress = new AppCompatEditText(this);
                etAddress.setHint(R.string.address);
                etAddress.setHintTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
                etAddress.setInputType(InputType.TYPE_CLASS_TEXT);
                etAddress.setMaxLines(1);
                etAddress.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                etAddress.setTextColor(ContextCompat.getColor(this, R.color.textColor));

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
        if (allFieldsAreValid()) {
            StringRequest stringRequest;

            if (isInEditMode) {
                stringRequest = new StringRequest(Request.Method.POST, MilCarnesApp.milCarnesApp.UPDATE_USER(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            processCreateUpdateResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();

                        map.put("idUsuario", user.getIdUsuario());
                        map.put("cedula", getTextFrom(etId));
                        map.put("nombres", getTextFrom(etName));
                        map.put("apellidos", getTextFrom(etLastName));
                        map.put("telefono", getTextFrom(etPhoneNumber));
                        map.put("correo", getTextFrom(etEmail));
                        map.put("direccion", getTextFrom(etAddress));

                        return map;
                    }
                };
            } else {
                stringRequest = new StringRequest(Request.Method.POST, MilCarnesApp.milCarnesApp.INSERT(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            processCreateUpdateResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();

                        map.put("cedula", getTextFrom(etId));
                        map.put("nombres", getTextFrom(etName));
                        map.put("apellidos", getTextFrom(etLastName));
                        map.put("telefono", getTextFrom(etPhoneNumber));
                        map.put("correo", getTextFrom(etEmail));
                        map.put("pass", getTextFrom(etPassword));
                        map.put("direccion", getTextFrom(etAddress));
                        map.put("Rol", String.valueOf(1));

                        return map;
                    }
                };
            }

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    private void processCreateUpdateResponse(String response) throws JSONException {
        Log.d(getClass().getSimpleName(), response);

        JSONObject jsonObject = new JSONObject(response);

        try {
            String state = jsonObject.getString("estado");
            String message = jsonObject.getString("mensaje");

            switch (state) {
                case "1":
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case "2":
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean allFieldsAreValid() {
        if (etId.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(0, false);

            etId.requestFocus();
            showKeyboard(etId, this);

            toast(getString(R.string.field_empty_message));

            return false;
        } else if (etName.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(1, false);

            etName.requestFocus();
            showKeyboard(etName, this);

            toast(getString(R.string.field_empty_message));

            return false;
        } else if (etLastName.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(2, false);

            etLastName.requestFocus();
            showKeyboard(etLastName, this);

            toast(getString(R.string.field_empty_message));

            return false;
        } else if (etEmail.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(3, false);

            etEmail.requestFocus();
            showKeyboard(etEmail, this);

            toast(getString(R.string.field_empty_message));

            return false;
        } else if (etPassword.getText().length() <= 0) {
            if (!isInEditMode) {
                verticalStepperFormLayout.goToStep(4, false);

                etPassword.requestFocus();
                showKeyboard(etPassword, this);

                toast(getString(R.string.field_empty_message));
            }

            return isInEditMode;
        } else if (etRepeatPassword.getText().length() <= 0) {
            if (!isInEditMode) {
                verticalStepperFormLayout.goToStep(5, false);

                etRepeatPassword.requestFocus();
                showKeyboard(etRepeatPassword, this);

                toast(getString(R.string.field_empty_message));
            }

            return isInEditMode;
        } else if (etPhoneNumber.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(6, false);

            etPhoneNumber.requestFocus();
            showKeyboard(etPhoneNumber, this);

            toast(getString(R.string.field_empty_message));

            return false;
        } else if (etAddress.getText().length() <= 0) {
            verticalStepperFormLayout.goToStep(7, false);

            etAddress.requestFocus();
            showKeyboard(etAddress, this);

            toast(getString(R.string.field_empty_message));

            return false;
        } else if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            verticalStepperFormLayout.goToStep(5, false);

            etRepeatPassword.requestFocus();
            showKeyboard(etRepeatPassword, this);

            toast(getString(R.string.passwords_do_not_match_message));

            return false;
        } else {
            return true;
        }
    }

    public String getTextFrom(AppCompatEditText textInputEditText) {
        return textInputEditText.getText().toString();
    }
}