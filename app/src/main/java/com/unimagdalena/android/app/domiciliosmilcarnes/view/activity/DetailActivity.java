package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shawnlin.preferencesmanager.PreferencesManager;
import com.unimagdalena.android.app.domiciliosmilcarnes.MilCarnesApp;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Comentario;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.Plate;
import com.unimagdalena.android.app.domiciliosmilcarnes.model.entity.User;
import com.unimagdalena.android.app.domiciliosmilcarnes.view.adapter.ComentarioAdapter;

import java.util.ArrayList;

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

    @BindView(R.id.btnEnviar)
    Button btnEnviar;

    @BindView(R.id.campoComentario)
    TextInputEditText campoComentario;

    @BindView(R.id.comentarios)
    RecyclerView rvComentarios;

    private int cantidadComentarios;

    private ArrayList<Comentario> comentarios;
    private ComentarioAdapter comentarioAdapter;
    private Plate plate;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (PreferencesManager.getBoolean(getString(R.string.there_connected_user))) {
            user = PreferencesManager.getObject(getString(R.string.connected_user), User.class);
        }

        plate = (Plate) getIntent().getExtras().getSerializable("plate");

        String path = "http://" + MilCarnesApp.milCarnesApp.getIP() + ":" + MilCarnesApp.milCarnesApp.getPORT() + "/DomiciliosMilCarnes/img/platos/" + plate.getIdplato() + ".png";

        Glide.with(this).load(path).into(picture);

        namePlate.setText(plate.getNombre());
        typePlate.setText(plate.getTipo());
        pricePlate.setText(String.format("$%s", String.valueOf(plate.getPrecioUnitario())));
        description.setText(plate.getDetalles());

        comentarios = new ArrayList<>();

        cantidadComentarios = PreferencesManager.getInt(plate.getNombre(), 0);

        for (int i = 1; i <= cantidadComentarios; i++) {
            comentarios.add(new Comentario(PreferencesManager.getString(plate.getNombre() + "_" + i)));
        }

        btnEnviar.setEnabled(PreferencesManager.getBoolean(getString(R.string.there_connected_user)));
        campoComentario.setEnabled(PreferencesManager.getBoolean(getString(R.string.there_connected_user)));

        comentarioAdapter = new ComentarioAdapter(comentarios, this);
        rvComentarios.setAdapter(comentarioAdapter);
        rvComentarios.setHasFixedSize(true);
        rvComentarios.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidadComentarios++;

                PreferencesManager.putInt(plate.getNombre(), cantidadComentarios);
                PreferencesManager.putString((plate.getNombre() + "_" + cantidadComentarios), user.getIdUsuario() + ":" + user.getNombres() + ":" + campoComentario.getText().toString());

                comentarioAdapter.addComentario(new Comentario(user.getIdUsuario() + ":" + user.getNombres() + ":" + campoComentario.getText().toString()));
            }
        });
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
