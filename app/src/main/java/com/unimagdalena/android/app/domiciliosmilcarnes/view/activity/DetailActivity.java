package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Plate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.picture)
    ImageView picture;

    @BindView(R.id.namePlate)
    TextView namePlate;

    @BindView(R.id.typePlate)
    TextView typePlate;

    @BindView(R.id.pricePlate)
    TextView pricePlate;

    @BindView(R.id.description)
    TextView description;

    private Plate plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plate = (Plate) getIntent().getExtras().getSerializable("plate");

        String path = "http://" + MilCarnesApp.milCarnesApp.getIP() + ":" + MilCarnesApp.milCarnesApp.getPORT() + "/DomiciliosMilCarnes/img/platos/" + plate.getIdplato() + ".png";

        Glide.with(this).load(path).into(picture);

        namePlate.setText(plate.getNombre());
        typePlate.setText(plate.getTipo());
        pricePlate.setText(String.format("$%s", String.valueOf(plate.getPrecioUnitario())));
        description.setText(plate.getDetalles());
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.app_bar_shopping_cart:
                MilCarnesApp.milCarnesApp.addPlate(plate);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
