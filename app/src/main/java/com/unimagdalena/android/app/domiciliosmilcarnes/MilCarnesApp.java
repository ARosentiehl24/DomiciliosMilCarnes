package com.unimagdalena.android.app.domiciliosmilcarnes;

import android.app.Application;
import android.content.Context;

import com.shawnlin.preferencesmanager.PreferencesManager;


public class MilCarnesApp extends Application {

    public static String PACKAGE_NAME;

    public static String SETTINGS_PREFERENCES;

    private PreferencesManager preferencesManager;

    public static MilCarnesApp milCarnesApp;

    @Override
    public void onCreate() {
        super.onCreate();

        milCarnesApp = this;

        PACKAGE_NAME = getPackageName().toUpperCase();

        SETTINGS_PREFERENCES = PACKAGE_NAME + ".SETTINGS";

        preferencesManager = new PreferencesManager(this);
        setPreferencesManager(SETTINGS_PREFERENCES);
    }

    public String getIP() {
        return PreferencesManager.getString("ip", "172.16.7.167");
    }

    public String getPORT() {
        return PreferencesManager.getString("port", "80");
    }

    public String GET_USER() {
        return "http://" + getIP() + ":" + getPORT() + "/DomiciliosMilCarnes/services/iniciarSesion.php";
    }

    public void setPreferencesManager(String name) {
        preferencesManager.setName(name);
        preferencesManager.setMode(Context.MODE_PRIVATE);
        preferencesManager.init();
    }
}

/*
* //VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

                        RequestQueue requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, MilCarnesApp.milCarnesApp.GET_USER(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e(getClass().getSimpleName(), response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if (Integer.parseInt(jsonObject.getString("estado")) == 1) {
                                        //iMainActivityPresenter.login(email, password);

                                        JSONObject usuarios = jsonObject.getJSONObject("usuarios");

                                        Log.e(getClass().getSimpleName(), usuarios.getString("cedula"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("cedula", email);
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

                        requestQueue.add(stringRequest);*/
