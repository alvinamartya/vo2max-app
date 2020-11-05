package com.example.atlit.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.atlit.Model.Location2;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LocationService extends Service {
    private static final String TAG = "StopWatchGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 10f;

    private class LocationListener implements android.location.LocationListener {
        DatabaseReference locDBRef;
        private Context context;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            locDBRef = FirebaseDatabase.getInstance().getReference("locations");
            context = MySingleton.getContext();
        }

        @Override
        public void onLocationChanged(Location location) {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            if (Constants.id == null) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, location1 -> {
                        UUID uuid = UUID.randomUUID();

                        Location2 location2 = new Location2(location1.getLatitude(), location1.getLongitude());
                        com.example.atlit.Model.Location loc = new com.example.atlit.Model.Location(uuid.toString(), location1.getLatitude(), location1.getLongitude(), 0);

                        locDBRef.child(uuid.toString()).setValue(loc);
                        locDBRef.child(uuid.toString()).child("locations").child("0").setValue(location2);

                        Constants.id = uuid.toString();
                    });
                }
            } else {
                if (ActivityCompat.checkSelfPermission(MySingleton.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, location12 -> locDBRef.child(Constants.id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            LatLng dataLoc = new LatLng((double) dataSnapshot.child("lat").getValue(), (double) dataSnapshot.child("lon").getValue());
                            if (dataLoc.latitude != location12.getLatitude() || dataLoc.longitude != location12.getLongitude()) {
                                double distance = Double.parseDouble(Objects.requireNonNull(dataSnapshot.child("distance").getValue()).toString()) + calculateDistance(dataLoc.latitude, dataLoc.longitude, location12.getLatitude(), location12.getLongitude());

                                List<Location2> location2s = new ArrayList<>();
                                for (DataSnapshot data : dataSnapshot.child("locations").getChildren()) {
                                    location2s.add(new Location2((double) data.child("lat").getValue(), (double) data.child("lon").getValue()));
                                }

                                Location2 location2 = new Location2(location12.getLatitude(), location12.getLongitude());
                                com.example.atlit.Model.Location loc = new com.example.atlit.Model.Location(Constants.id, location12.getLatitude(), location12.getLongitude(), distance);
                                locDBRef.child(Constants.id).setValue(loc);
                                location2s.add(location2);

                                for (int i = 0; i < location2s.size(); i++) {
                                    Location2 item = location2s.get(i);
                                    locDBRef.child(Constants.id).child("locations").child(String.valueOf(i)).setValue(item);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }));
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    private double calculateDistance(double startLat, double startLong, double endLat, double endLong) {
        double dLat = (endLat - startLat) * Math.PI / (double) 180;
        double dLon = (endLong - startLong) * Math.PI / (double) 180;

        double lat1 = startLat * Math.PI / (double) 180;
        double lat2 = endLat * Math.PI / (double) 180;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);

        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }

}
