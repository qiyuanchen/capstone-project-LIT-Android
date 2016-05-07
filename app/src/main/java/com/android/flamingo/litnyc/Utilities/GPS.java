package com.android.flamingo.litnyc.Utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Qiyuan on 5/7/2016.
 */
public class GPS implements android.location.LocationListener {
    private LocationManager lm;
    private boolean GPSenabled;
    private boolean networkEnabled;
    private Context context;
    private Location location;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 20; // 20 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 5; // 5 minute

    public GPS(Context context) {
        this.context = context;
        lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        //checks if gps is enabled;
        GPSenabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    public boolean getGPSStatus() {
        return GPSenabled;
    }

    public boolean getNetworkStatus() {
        return networkEnabled;
    }

    //gets current location
    public Location fetchLocation() {
        //returns null if both provider is off.
        try {
            if (!getGPSStatus() && !getNetworkStatus()) {
                Toast.makeText(context, "Please enable GPS or WIFI", Toast.LENGTH_LONG).show();
                return null;
            }
            if (getNetworkStatus()) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

            } else if (getGPSStatus()) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }


            }
        }catch(Exception e){
            e.printStackTrace(System.out);
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}