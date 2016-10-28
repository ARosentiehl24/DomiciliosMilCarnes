package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.VolleySingleton;

import org.fingerlinks.mobile.android.navigator.Navigator;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private User editedUser;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvId)
    TextView tvId;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvLastName)
    TextView tvLastName;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;

    @BindView(R.id.tvAddress)
    TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        User user = (User) getIntent().getSerializableExtra(getString(R.string.connected_user));

        String path = MilCarnesApp.milCarnesApp.GET_EDITED_USER() + "?id=" + user.getIdUsuario();

        VolleySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(getClass().getSimpleName(), response.toString());

                try {
                    if (response.getString("estado").equals("1")) {
                        Gson gson = new Gson();

                        editedUser = gson.fromJson(response.getString("usuarios"), User.class);

                        setTitle(String.format("Perfil de %s %s", editedUser.getNombres(), editedUser.getApellidos()));

                        tvId.setText(editedUser.getCedula());
                        tvName.setText(editedUser.getNombres());
                        tvLastName.setText(editedUser.getApellidos());
                        tvEmail.setText(editedUser.getCorreo());
                        tvPhoneNumber.setText(editedUser.getTelefono());
                        tvAddress.setText(editedUser.getDireccion());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }));

        setTitle(String.format("Perfil de %s %s", user.getNombres(), user.getApellidos()));
    }

    @Override
    public void onBackPressed() {
        Navigator.with(this).utils().finishWithAnimation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.app_bar_edit:
                User user = editedUser;

                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.connected_user), user);
                bundle.putBoolean("edit", true);

                Navigator.with(this).build().goTo(SignUpActivity.class, bundle).animation().commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
