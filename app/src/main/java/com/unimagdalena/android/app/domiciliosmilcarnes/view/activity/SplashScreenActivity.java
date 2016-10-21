package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Plate;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        VolleySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest(Request.Method.GET, MilCarnesApp.milCarnesApp.GET_PLATES(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                Log.e(getClass().getSimpleName(), response.toString());

                try {
                    String state = response.getString("estado");

                    switch (state) {
                        case "1":
                            ArrayList<Plate> platesArrayList = new ArrayList<>();
                            Gson gson = new Gson();
                            JSONArray message = response.getJSONArray("platos");

                            Plate[] plates = gson.fromJson(message.toString(), Plate[].class);

                            Collections.addAll(platesArrayList, plates);

                            final Bundle bundle = new Bundle();
                            bundle.putSerializable("plates", platesArrayList);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                    intent.putExtras(bundle);

                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    finish();
                                }
                            }, 2500);
                            break;
                        case "2":
                            String errorMessage = response.getString("mensaje");
                            Toast.makeText(SplashScreenActivity.this, errorMessage, Toast.LENGTH_LONG).show();

                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getClass().getSimpleName(), error.toString());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, SettingsActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }
                }, 2500);
            }
        }));
    }
}
