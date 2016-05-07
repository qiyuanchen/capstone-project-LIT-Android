package com.android.flamingo.litnyc.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.flamingo.litnyc.R;
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

import com.android.flamingo.litnyc.Data.user;
import com.android.flamingo.litnyc.db_tasks.user_tasks;

public class mapBox_activity extends AppCompatActivity implements MapboxMap.OnMarkerClickListener {

    private MapView mapView;
    private BottomSheetBehavior mBottomSheetBehavior;

    private static final String STYLEURL = "mapbox://styles/nylit/cin7s3yxo0019ajm053bzzhb1";
    public static final String USER_ID = "USER_ID";
    public static final String LNG="LNG";
    public static final String LAT="LAT";

    private Icon cLMarker;
    private double longitude;
    private double latitude;
    private user current;
    private BottomSheetDialog mBottomSheetDialog;

    public static void callMe(Activity activity, String id,double lng,double lat) {
        Intent intent = new Intent(activity, mapBox_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USER_ID, id);
        intent.putExtra(LAT,lat);
        intent.putExtra(LNG,lng);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_box_activity);
        current = user_tasks.queryDB(getApplicationContext(), getIntent().getExtras().getString(USER_ID));
        latitude=getIntent().getExtras().getDouble(LAT);
        longitude=getIntent().getExtras().getDouble(LNG);
        Log.d("user", current.toString());
        IconFactory iconFactory = IconFactory.getInstance(mapBox_activity.this);
        Drawable iconDrawable = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.current);
        cLMarker = iconFactory.fromDrawable(iconDrawable);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(STYLEURL);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.
                LatLng current= new LatLng(latitude,longitude);
                CameraPosition position = new CameraPosition.Builder()
                        .target(current) // Sets the new camera position
                        .zoom(13) // Sets the zoom// Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
                mapboxMap.addMarker(new MarkerOptions().position(current).icon(cLMarker));
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



    @Override
    public boolean onMarkerClick(Marker marker) {

        showBottomSheet(marker);

        return true;

    }

    private void showBottomSheet(Marker marker) {

        mBottomSheetDialog = new BottomSheetDialog(this);
        // inflate layout
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        //init view here


        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

}
