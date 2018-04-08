package com.example.test.myfriends.BLL;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.ArrayAdapter;

import com.example.test.myfriends.DAL.DAO;
import com.example.test.myfriends.Entity.Friend;
import com.example.test.myfriends.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by thomas on 12-03-2018.
 */

public class FriendService {

    private static FriendService instance = null;


    private static DAO dao;

    public static void setContext(Context c)
    {
        dao = new DAO(c);
    }

    //Singleton
    public static FriendService getInstance( ) {
        if (instance == null)
        {
            instance = new FriendService();
        }
        return instance;
    }

    // Returns all friends from repository
    public ArrayList<Friend> getAllFriends() {
        return dao.getAll();
    }
  
    //Sends the created friend from UI to the DAO
    public void createFriend(Friend friend)
    {
        dao.insert(friend);
    }

    //Sends the created friend from UI to the DAO
    public void deleteFriend(Friend friend)
    {
        dao.deleteById(friend.getId());
    }

    //Updates the chosen friend
    public void updateFriend(Friend friend)
    {
        dao.updateFriend(friend);
    }

    //Checks if a friend has birthday
    public boolean isItBirthday(String birthday)
    {
        //substrings the birthday from the friend. Removes yyyy
        String Main_String = birthday;
        int last = Main_String.lastIndexOf("-");
        String substringBirthday = Main_String.substring(0,last);

        //create a new dateString with only day and month
        String today = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());

        if(today.equals(substringBirthday))
        {
            return true;
        } else
            return false;
    }

    public ArrayList<Friend> sortListByName(){
        //Generate a new list each time from the DB list
        ArrayList<Friend> returnList = new ArrayList<>(dao.getAll());
       Collections.sort(returnList, new FriendComparator());
       return returnList;
    }

/*public void test(Context c) {
    Geocoder geocoder = new Geocoder(c);
    List<Address> address = null;
    ArrayList<Friend> friends = new ArrayList<>(dao.getAll());
    LatLng location = null;

    for (Friend friend : friends) {

        try { // Try to get LatLng from address
            address = geocoder.getFromLocationName(friend.getAddress(), 1);
            System.out.println(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address.size() > 0) { //If the try is succesfull and a LatLng pair is found

            location = findLongAndAlti(address);
            friend.setLongitude(location.longitude);
            friend.setAltitude(location.latitude);
            System.out.println(getDistanceFromLatLonInKm(55.488431, 8.446710, friend.getAltitude(), friend.getLongitude()));

        }
    }
}
    private LatLng findLongAndAlti(List<Address> address) {
        LatLng location;
        location = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
        return location;

    }

    private double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    private double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }*/

}



class FriendComparator implements Comparator<Friend> {
    @Override
    public int compare(Friend first, Friend second) {
        return first.getName().compareTo(second.getName());
    }
}



