package com.example.adresboek;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.location.Geocoder;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
{

    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();


        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10*1000).setFastestInterval(1*1000);

    }

    @Override
    protected void onResume() {

        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();

        Address adres = null;
        Intent intent = getIntent();
        String location = new String();
        List<Address> addresses = null;
        Bundle extras = getIntent().getExtras();
        if (extras == null){}
        else {
            location = extras.getString("location");
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
           addresses =  geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
           String errorMessage = "Service not available";
            Log.e(TAG, errorMessage, e);
        }

        if (addresses != null && addresses.size() > 0){
            adres = addresses.get(0);
        }

        //LatLng latLng = new LatLng(51.096366,5.530642);
        LatLng latLng = new LatLng(adres.getLatitude(),adres.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(extras.getString("name")));
        float zoom = 11;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {

        LatLng latLng = new LatLng(50.928901,5.395001);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
        float zoom = 11;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void  handleNewLocation(Location location){
        Log.d(TAG ,location.toString());

        double currentLong = location.getLongitude();
        double currentLat = location.getLatitude();

        LatLng latLng = new LatLng(currentLat,currentLong);

        MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");
        mMap.addMarker(options);
        float zoom = 11;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


    }

    private void addMarker(Location location){


        Intent intent = getIntent();
        String tagname = intent.getStringExtra("name");
        String location1 = intent.getStringExtra("location");

        Geocoder geocoder = new Geocoder(this,Locale.getDefault());


        double adresLong = location.getLongitude();
        double adresLat = location.getLatitude();

        LatLng latLng = new LatLng(adresLat,adresLong);
        MarkerOptions options = new MarkerOptions().position(latLng).title(tagname);
        mMap.addMarker(options);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null){
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()){
            try {
                connectionResult.startResolutionForResult(this,CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
        Log.i(TAG,"Location service connection failed: " + connectionResult.getErrorMessage());
        }
    }
}
