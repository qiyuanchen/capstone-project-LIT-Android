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
import android.widget.Toast;

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

import Data.user;
import db_tasks.user_tasks;

public class mapBox_activity extends AppCompatActivity implements MapboxMap.OnMarkerClickListener {

    private MapView mapView;
    private BottomSheetBehavior mBottomSheetBehavior;

    private static final String STYLEURL ="mapbox://styles/nylit/cin7s3yxo0019ajm053bzzhb1";
    public static final String USER_ID ="USER_ID";

    private Icon cLMarker;
    private double longitude;
    private double latitude;
    private user current;
    public static void callMe(Activity activity, String id) {
        Intent intent = new Intent(activity, mapBox_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USER_ID, id);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_box_activity);
        current= user_tasks.queryDB(getApplicationContext(),getIntent().getExtras().getString(USER_ID));
        Log.d("user",current.toString());
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

                // Customize map with markers, polylines, etc.
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(40.768678, -73.964721)) // Sets the new camera position
                        .zoom(13) // Sets the zoom// Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(40.768678, -73.964721)).icon(cLMarker));
                mapboxMap.setOnMarkerClickListener(mapBox_activity.this);
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
    @Override
    public boolean onMarkerClick(Marker marker){


        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        Toast.makeText(this,"Marker",Toast.LENGTH_LONG).show();


        return true;

    }

}
