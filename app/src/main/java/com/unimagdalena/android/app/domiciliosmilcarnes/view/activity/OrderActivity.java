package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.view.adapter.OrderAdapter;

import org.fingerlinks.mobile.android.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @OnClick(R.id.order)
    public void OnClick(View view) {
        if (view.getId() == R.id.order) {
            long totalPrice = 0;

            for (int i = 0; i < orderAdapter.getItemCount(); i++) {
                totalPrice += orderAdapter.plate(i).getPrecioUnitario();
            }

            Bundle bundle = new Bundle();
            bundle.putLong("totalPrice", totalPrice);

            Navigator.with(this).build().goTo(DoneActivity.class, bundle).animation().commit();
        }
    }

    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderAdapter = new OrderAdapter(this, MilCarnesApp.milCarnesApp.getPlates());

        recyclerView.setAdapter(orderAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onBackPressed() {
        Navigator.with(this).utils().finishWithAnimation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
