package com.example.test.myfriends;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.test.myfriends.Entity.Friend;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Map_details extends FragmentActivity implements OnMapReadyCallback {

    Friend friend;
    private GoogleMap mMap;
    Geocoder geocoder;


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

        try {
            address = geocoder.getFromLocationName(friend.getAddress(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(address.size() > 0) {
            LatLng location = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            MarkerOptions options = new MarkerOptions();
            options.position(location);
            options.title(friend.getName());
//            options.icon(BitmapDescriptorFactory.);
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }




}
