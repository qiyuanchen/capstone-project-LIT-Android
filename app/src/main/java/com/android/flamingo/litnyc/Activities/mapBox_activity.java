package com.android.flamingo.litnyc.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.flamingo.litnyc.R;
import com.google.android.gms.location.LocationListener;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class mapBox_activity extends AppCompatActivity {
    private MapView mapView;
    private BottomSheetBehavior mBottomSheetBehavior;
    private static final String STYLEURL ="mapbox://styles/nylit/cin7s3yxo0019ajm053bzzhb1";
    private Icon cLMarker;
    private double longitude;
    private double latitude;
    public static void callMe(Activity activity) {
        Intent intent = new Intent(activity, mapBox_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_box_activity);
        View bottomSheet= findViewById(R.id.location_detail);
        mBottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);
        IconFactory iconFactory = IconFactory.getInstance(mapBox_activity.this);
        Drawable iconDrawable = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.current);
        cLMarker=iconFactory.fromDrawable(iconDrawable);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(STYLEURL);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {
                Log.d("latlat",latitude+"");
                Log.d("latlong",longitude+"");
                // Customize map with markers, polylines, etc.
                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(40.768678, -73.964721)) // Sets the new camera position
                                .zoom(13) // Sets the zoom// Rotate the camera
                                .tilt(30) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder
                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 7000);
                        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(40.768678, -73.964721)).icon(cLMarker));

                    }

        });

    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    private void GetMyLocation(){

    }
}
