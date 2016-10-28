package com.unimagdalena.android.app.domiciliosmilcarnes.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityPresenter;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityView;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class IMainActivityPresenter implements MainActivityPresenter {

    private MainActivityView mainActivityView;
    private String url;
    private User loggedUser;

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
    public void itemProfile() {
        mainActivityView.showProfile();
    }

    @Override
    public void login(final Context context, final String id, final String password) {
        loggedUser = null;

        url = MilCarnesApp.milCarnesApp.GET_USER();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("estado").equals("1")) {
                        Gson gson = new Gson();

                        loggedUser = gson.fromJson(jsonObject.getString("usuarios"), User.class);

                        //Toast.makeText(MainActivity.context, loggedUser.toString() + " ----------" + response, Toast.LENGTH_LONG).show();

                        PreferencesManager.putObject(context.getString(R.string.connected_user), loggedUser);

                        mainActivityView.updateToolbarMenu(true, loggedUser.getNombres());
                    } else {
                        mainActivityView.showErrorMessage();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getClass().getSimpleName(), "onErrorResponse: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("usuario", id);
                map.put("password", password);

                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
