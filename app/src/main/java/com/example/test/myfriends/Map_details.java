package com.example.test.myfriends;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.test.myfriends.Entity.Friend;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

public class Map_details extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    Friend friend;
    private GoogleMap mMap;
    Geocoder geocoder;
    FusedLocationProviderClient mFusedLocationClient;
    boolean locationGranted = false;
    Task<Location> mLastKnownLocation;
    Location loc;


    public Map_details() {
        Location location = new Location("");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_details);
        Bundle extras = getIntent().getExtras();
        friend = ((Friend) extras.getSerializable("FRIEND"));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        geocoder = new Geocoder(this);
        mMap = googleMap;
        List<Address> address = null;
        LatLng location = null;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try { // Try to get LatLng from address
            address = geocoder.getFromLocationName(friend.getAddress(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address.size() > 0) { //If the try is succesfull and a LatLng pair is found

            //If permission to get location is granted
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationGranted = true;
                Toast.makeText(this, "NICE", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "FEJL", Toast.LENGTH_LONG).show();
            }


            location = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());

            //Creates different options for the marker
            MarkerOptions options = new MarkerOptions();
            options.position(location);
            options.title(friend.getName());
//            options.icon(BitmapDescriptorFactory.);


            //Add the marker and set the view to focus on it
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


        }
        if (locationGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
        }

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}





