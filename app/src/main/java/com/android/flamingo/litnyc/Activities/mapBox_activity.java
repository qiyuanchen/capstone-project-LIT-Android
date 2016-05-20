package com.android.flamingo.litnyc.Activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.flamingo.litnyc.Network.EndPoints;
import com.android.flamingo.litnyc.Network.NetworkHelper;
import com.android.flamingo.litnyc.Network.result_response;
import com.android.flamingo.litnyc.Network.results_request;
import com.android.flamingo.litnyc.Network.updateresponse;
import com.android.flamingo.litnyc.R;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
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

public class mapBox_activity extends Activity implements MapboxMap.OnMarkerClickListener,BottomSheetListener {

    private MapView mapView;
    private BottomSheetBehavior mBottomSheetBehavior;

    private static final String STYLEURL = "mapbox://styles/nylit/cio081a1k0021agkl7hlt6ub3";
    private static final String UGLYMAPSTYLEURL = "mapbox://styles/nylit/cinz2ilxs0006a4np8tq7p3nm";
    public static final String USER_ID = "USER_ID";
    public static final LatLng TESTCOORDS=new LatLng(40.768274, -73.963814);

    private Icon cLMarker;
    private Icon lv1;
    private Icon lv2;
    private Icon lv3;
    private Icon lv4;
    private double longitude;
    private double latitude;
    private user current;
    private HashMap<String, result_response.network_response> hashmap;
    private List<result_response.network_response> results;
    private Switch theme;
    private View mProgressView;
    private LocationManager lm;
    private Location loc;

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
        mapView = (MapView) findViewById(R.id.mapview);
        theme=(Switch)findViewById(R.id.theme);
        mProgressView = findViewById(R.id.login_progress);
        current = user_tasks.queryDB(getApplicationContext(), getIntent().getExtras().getString(USER_ID));
        results=new ArrayList<>();
        //init hm
        hashmap = new HashMap<>();
        //current location
        getResultTask task=new getResultTask();
        task.execute();


        /*
        latitude = getIntent().getExtras().getDouble(LAT);
        longitude = getIntent().getExtras().getDouble(LNG);*/

        // get results
       // results = (ArrayList<result_response.network_response>) getIntent().getSerializableExtra("RESULTS");

        //store data into hm
        initMap(savedInstanceState);


    }
    public class getResultTask extends AsyncTask<Void, Void, Boolean> {

        getResultTask(){
            showProgress(true);

        }
        @Override
        protected Boolean doInBackground(Void... params) {
            //if(getCurrentLocation()){
                getResults();
                return true;
           // }else{
             //   return false;
            //}

        }
        @Override
        protected void onPostExecute(final Boolean success) {
            storeToHM();
            showProgress(false);

        }
    }
    public void getResults() {
        results_request request = new results_request(current.id, mapBox_activity.TESTCOORDS.getLatitude(), mapBox_activity.TESTCOORDS.getLongitude());
        try {
            result_response response = NetworkHelper.makeRequestAdapter(getApplicationContext())
                    .create(EndPoints.class).result(request);
            results.addAll(response.result);
        } catch (Exception e) {
            e.printStackTrace(System.out);

        }
    }
    private boolean getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
            loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = loc.getLongitude();
            latitude = loc.getLatitude();
            return true;
        }else{
            return false;
        }

    }
    private void initMap(Bundle savedInstanceState){
        //switch
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
        Drawable iconDrawable4 = ContextCompat.getDrawable(mapBox_activity.this, R.drawable.pin4);
        lv4 = iconFactory.fromDrawable(iconDrawable4);

        //init map
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
                    }else if(itt.level==4){
                        mapboxMap.addMarker(new MarkerOptions().position(mLatlng).icon(lv4).title(itt.id));
                    }else {
                        mapboxMap.addMarker(new MarkerOptions().position(mLatlng).icon(lv3).title(itt.id));
                    }

                }
                mapboxMap.setOnMarkerClickListener(mapBox_activity.this);


            }

        });
    }
    private boolean updatePref(){
        try {
            updateresponse response = NetworkHelper.makeRequestAdapter(getApplicationContext())
                    .create(EndPoints.class).uresponse(current);
            return(response.result);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return false;
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
    public void onBackPressed()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if(!updatePref()){
            Toast.makeText(this,"Failed to Update to server",Toast.LENGTH_LONG).show();
            thread.start();
        }
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(!marker.getTitle().equals("current"))
            showBottomSheet(marker);

        return true;

    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            theme.setVisibility(show ? View.GONE : View.VISIBLE);
            mapView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mapView.setVisibility(show ? View.GONE : View.VISIBLE);
            theme.setVisibility(show ? View.GONE : View.VISIBLE);

        }
    }

    private void showBottomSheet(Marker marker) {

        result_response.network_response r = hashmap.get(marker.getTitle());
        for(String key:r.attributes.keySet()){
            incrementCate(key, 0.05);
        }
        user_tasks.updateDB(this,current);
        // inflate layout
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        //init view here
        TextView name=(TextView)view.findViewById(R.id.location_name);
        TextView address =(TextView)view.findViewById(R.id.location_address);
        TextView cate=(TextView)view.findViewById(R.id.category);
        cate.setText(r.category);
        name.setText(r.name);
        String addres=r.location.get("formattedAddress").replace("[","");
        addres=addres.replace("\"","");
        addres=addres.replace("]","");
        addres=addres.replace(",","\n");
        address.setText(addres);
        new BottomSheet.Builder(this,R.style.BottomSheet_Custom)
                .setView(view)
                .show();

    }
    private void incrementCate(String key,double val){
        current.incrementAtt(key,val);
    }


    private void storeToHM() {
        for (result_response.network_response itt : results) {

            hashmap.put(itt.id, itt);
        }
    }
    @Override
    public void onSheetDismissed(int which) {

        switch (which) {
            case BottomSheet.BUTTON_POSITIVE:
                break;

            case BottomSheet.BUTTON_NEGATIVE:
                break;
        }
    }
    @Override
    public void onSheetItemSelected(MenuItem item) {
    }
    @Override
    public void onSheetShown() {
    }



}
