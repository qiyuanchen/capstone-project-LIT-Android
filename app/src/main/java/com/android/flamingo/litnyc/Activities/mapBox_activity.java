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
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.flamingo.litnyc.Network.result_response;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class mapBox_activity extends AppCompatActivity implements MapboxMap.OnMarkerClickListener {

    private MapView mapView;
    private BottomSheetBehavior mBottomSheetBehavior;

    private static final String STYLEURL = "mapbox://styles/nylit/cio081a1k0021agkl7hlt6ub3";
    private static final String UGLYMAPSTYLEURL = "mapbox://styles/nylit/cinz2ilxs0006a4np8tq7p3nm";
    public static final String RESULTS = "RESULTS";
    public static final String USER_ID = "USER_ID";
    public static final String LNG = "LNG";
    public static final String LAT = "LAT";
    public static final LatLng TESTCOORDS=new LatLng(40.768274, -73.963814);

    private Icon cLMarker;
    private Icon lv1;
    private Icon lv2;
    private Icon lv3;
    private double longitude;
    private double latitude;
    private user current;
    private BottomSheetDialog mBottomSheetDialog;
    private HashMap<String, result_response.network_response> hashmap;
    private List<result_response.network_response> results;
    private Switch theme;

    public static void callMe(Activity activity, String id, double lng, double lat, ArrayList<result_response.network_response> result) {
        Intent intent = new Intent(activity, mapBox_activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USER_ID, id);
        intent.putExtra(LAT, lat);
        intent.putExtra(LNG, lng);
        intent.putExtra(RESULTS, result);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_box_activity);
        //current location
        current = user_tasks.queryDB(getApplicationContext(), getIntent().getExtras().getString(USER_ID));
        latitude = getIntent().getExtras().getDouble(LAT);
        longitude = getIntent().getExtras().getDouble(LNG);
        // get results
        results = (ArrayList<result_response.network_response>) getIntent().getSerializableExtra("RESULTS");
        //init hm
        hashmap = new HashMap<>();
        //store data into hm
        storeToHM();
        //switch
        theme=(Switch)findViewById(R.id.theme);
        theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    mapView.setStyleUrl(STYLEURL);

                }else{
                    mapView.setStyleUrl(UGLYMAPSTYLEURL);


                }
            }
        });
        //init map icon
        IconFactory iconFactory = IconFactory.getInstance(mapBox_activity.this);
        Drawable iconDrawable = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.current2);
        cLMarker = iconFactory.fromDrawable(iconDrawable);
        Drawable iconDrawable1 = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.pin1);
        lv1 = iconFactory.fromDrawable(iconDrawable1);
        Drawable iconDrawable2 = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.pin2);
        lv2 = iconFactory.fromDrawable(iconDrawable2);
        Drawable iconDrawable3 = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.pin3);
        lv3 = iconFactory.fromDrawable(iconDrawable3);
        //init map
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setStyleUrl(STYLEURL);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.
                LatLng current = new LatLng(latitude, longitude);
                CameraPosition position = new CameraPosition.Builder()
                        .target(TESTCOORDS) // Sets the new camera position
                        .zoom(13) // Sets the zoom// Rotate the camera
                        .tilt(30) // Set the camera tilt
                        .build(); // Creates a CameraPosition from the builder
                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 7000);
                mapboxMap.addMarker(new MarkerOptions().position(TESTCOORDS).icon(cLMarker).title("current"));
                for (result_response.network_response itt : results) {
                    double lat = Double.parseDouble(itt.location.get("lat"));
                    double lng = Double.parseDouble(itt.location.get("lng"));
                    LatLng mLatlng = new LatLng(lat, lng);
                    if (itt.level == 0 || itt.level == 3) {
                        mapboxMap.addMarker(new MarkerOptions().position(mLatlng).icon(lv1).title(itt.id));
                    } else if (itt.level == 2) {
                        mapboxMap.addMarker(new MarkerOptions().position(mLatlng).icon(lv2).title(itt.id));
                    } else {
                        mapboxMap.addMarker(new MarkerOptions().position(mLatlng).icon(lv3).title(itt.id));
                    }

                }
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
        if(!marker.getTitle().equals("current"))
            showBottomSheet(marker);

        return true;

    }

    private void showBottomSheet(Marker marker) {

        result_response.network_response r = hashmap.get(marker.getTitle());
        mBottomSheetDialog = new BottomSheetDialog(this);
        // inflate layout
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        //init view here

        TextView name=(TextView)view.findViewById(R.id.location_name);
        TextView address =(TextView)view.findViewById(R.id.location_address);
        name.setText(r.name+"("+r.category+")");
        String addres=r.location.get("formattedAddress").replace("[","");
        addres=addres.replace("\"","");
        addres=addres.replace("]","");
        addres=addres.replace(",","\n");
        address.setText(addres);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }


    private void storeToHM() {
        for (result_response.network_response itt : results) {

            hashmap.put(itt.id, itt);
        }
    }


}
