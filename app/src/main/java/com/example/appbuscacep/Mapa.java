package com.example.appbuscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private Double lati, longi;
    private MapView mapview;
    private GoogleMap gmap;
    private MarkerOptions markerOptions = new MarkerOptions();
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        AcessaWsTask task = new AcessaWsTask();
        Bundle mapViewBundle = null;

        try {

            String js = task.execute("http://maps.googleapis.com/maps/api/geocode/json?address=" + message).get();
            JSONObject jobj = new JSONObject(js);

            lati = jobj.getDouble("lat");
            longi  = jobj.getDouble("lng");
        } catch (ExecutionException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }

        mapview.findViewById(R.id.mapView);
        mapview.setClickable(true);

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapview.onCreate(mapViewBundle);
        mapview.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        gmap.setIndoorEnabled(true);

        UiSettings ponto = gmap.getUiSettings();
        ponto.setIndoorLevelPickerEnabled(true);

        ponto.setMyLocationButtonEnabled(true);
        ponto.setMapToolbarEnabled(true);
        ponto.setCompassEnabled(true);
        ponto.setZoomControlsEnabled(true);

        LatLng latLong = new LatLng(lati, longi);
        markerOptions.position(latLong);
        gmap.addMarker(markerOptions);

        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLong));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }
}