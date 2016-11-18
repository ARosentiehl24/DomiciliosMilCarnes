package com.unimagdalena.android.app.domiciliosmilcarnes.view.activity;

import android.location.Address;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.entire.sammalik.samlocationandgeocoding.SamLocationRequestService;
import com.google.android.gms.maps.model.LatLng;
import com.unimagdalena.android.app.domiciliosmilcarnes.R;

import org.fingerlinks.mobile.android.navigator.Navigator;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoneActivity extends AppCompatActivity {

    @BindView(R.id.message)
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();

        new SamLocationRequestService(DoneActivity.this).executeService(new SamLocationRequestService.SamLocationListener() {
            @Override
            public void onLocationUpdate(Location location, Address address) {
                LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng restaurante = new LatLng(11.23805, -74.2046887);

                double tiempo = (calculationByDistance(me, restaurante) * 1000) / 833;

                message.setText("El total del pedido es " + bundle.getLong("totalPrice") + " y se va a demorar " + (Math.round(tiempo) + 30) + " minutos.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Navigator.with(this).utils().finishWithAnimation();
    }

    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    public double roundToDecimals(double d, int c) {
        int temp = (int) (d * Math.pow(10, c));
        return ((double) temp) / Math.pow(10, c);
    }

}
