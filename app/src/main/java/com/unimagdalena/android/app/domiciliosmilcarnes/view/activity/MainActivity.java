package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mmin18.widget.RealtimeBlurView;
import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.interfaces.MainActivityView;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.VolleySingleton;
import com.unimagdalena.android.app.domiciliosmilcarnes.presenter.IMainActivityPresenter;

import org.fingerlinks.mobile.android.navigator.Navigator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.widget.RevealFrameLayout;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private Boolean isLoginVisible = false;
    private IMainActivityPresenter iMainActivityPresenter;
    private RequestQueue requestQueue;

    @BindView(R.id.etEmail)
    AppCompatEditText etEmail;

    @BindView(R.id.etPassword)
    AppCompatEditText etPassword;

    @BindView(R.id.inputContainer)
    LinearLayout inputContainer;

    @BindView(R.id.blurView)
    RealtimeBlurView blurView;

    @BindView(R.id.revealFrameLayout)
    RevealFrameLayout revealFrameLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        iMainActivityPresenter = new IMainActivityPresenter(this);
        iMainActivityPresenter.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isLoginVisible) {
            hideLogin();
        }
    }

    @Override
    public void onBackPressed() {
        if (isLoginVisible) {
            hideLogin();
        } else {
            Navigator.with(this).utils().finishWithAnimation(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Boolean thereConnectedUser = PreferencesManager.getBoolean(getString(R.string.there_connected_user));

        if (thereConnectedUser) {
            getMenuInflater().inflate(R.menu.login_menu, menu);

            MenuItem actionSearch = menu.findItem(R.id.app_bar_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(actionSearch);
            searchView.setQueryHint(getString(R.string.search));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        } else {
            getMenuInflater().inflate(R.menu.no_login_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.app_bar_log_out:
                PreferencesManager.putBoolean(getString(R.string.there_connected_user), false);

                iMainActivityPresenter.itemLogOut();
                return true;
            case R.id.app_bar_login:
                iMainActivityPresenter.itemLogIn(isLoginVisible);
                return true;
            case R.id.app_bar_search:
                iMainActivityPresenter.itemSearch();
            case R.id.app_bar_profile:
                iMainActivityPresenter.itemProfile();
                return true;
            case R.id.app_bar_sign_up:
                iMainActivityPresenter.itemSignUp();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupViews() {
        setSupportActionBar(toolbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blurView.getLayoutParams().height = inputContainer.getHeight() + 75;
                blurView.requestLayout();
            }
        }, 0);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (email.length() <= 0) {
                        etEmail.requestFocus();

                        toast(getString(R.string.field_empty_message));
                    } else if (password.length() <= 0) {
                        etPassword.requestFocus();

                        toast(getString(R.string.field_empty_message));
                    } else {
                        final String url = MilCarnesApp.milCarnesApp.GET_USER();

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(getClass().getSimpleName(), "onResponse: " + response);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    if (jsonObject.getString("estado").equals("1")) {

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
                                map.put("usuario", email);
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

                        /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                toast("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                toast("Error: " + error.toString());
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("usuario", email);
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
*/
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void singUp() {
        Navigator.with(this).build().goTo(SignUpActivity.class).animation().commit();
    }

    @Override
    public void editProfile() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("edit", true);

        Navigator.with(this).build().goTo(SignUpActivity.class, bundle).animation().commit();
    }

    @Override
    public void hideLogin() {
        int cx = (revealFrameLayout.getLeft() + revealFrameLayout.getRight());
        int cy = revealFrameLayout.getTop();

        int radius = Math.max(revealFrameLayout.getWidth(), revealFrameLayout.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(revealFrameLayout, cx, cy, radius, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isLoginVisible = false;
                revealFrameLayout.setVisibility(View.INVISIBLE);

                etEmail.getText().clear();
                etPassword.getText().clear();

                hideKeyboard(etEmail, getApplicationContext());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void showLogin() {
        revealFrameLayout.setVisibility(View.VISIBLE);

        int cx = (revealFrameLayout.getLeft() + revealFrameLayout.getRight());
        int cy = revealFrameLayout.getTop();

        int radius = Math.max(revealFrameLayout.getWidth(), revealFrameLayout.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(revealFrameLayout, cx, cy, 0, radius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isLoginVisible = true;

                etEmail.requestFocus();

                showKeyboard(etEmail, getApplicationContext());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void showKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void clearInputs() {
        etEmail.getText().clear();
        etPassword.getText().clear();

        etEmail.requestFocus();
    }

    @Override
    public void showErrorMessage() {
        toast(getString(R.string.nickname_does_not_exist_message));
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateToolbarMenu(boolean showWelcomeMessage, String name) {
        hideKeyboard(etEmail, this);

        if (showWelcomeMessage) {
            PreferencesManager.putBoolean(getString(R.string.there_connected_user), true);

            toast(String.format(getString(R.string.welcome_message), name));
        }

        /*Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);*/

        Navigator.with(this).build().goTo(MainActivity.class).animation().commit();
        finish();
    }

    @Override
    public void showProfile() {
        String connectedUser = PreferencesManager.getString(getString(R.string.connected_user));

        User user = PreferencesManager.getObject(connectedUser, User.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        Navigator.with(this).build().goTo(ProfileActivity.class, bundle).animation().commit();
    }
}
