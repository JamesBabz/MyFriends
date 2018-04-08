package com.example.test.myfriends;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.test.myfriends.BLL.FriendService;
import com.example.test.myfriends.Entity.Friend;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FriendActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    File mFile;
    Uri uriSavedImage;

    TextView txtName;
    TextView txtAdress;
    TextView txtPhone;
    TextView txtMail;
    TextView txtBirthday;
    TextView txtWeb;
    ImageView ivPicture;
    Geocoder geocoder;
    Friend friend;

    Button btnShow;
    ImageButton btnDelete;
    ImageButton btnSms;
    ImageButton btnCall;

    private FriendService friendService;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        txtName = findViewById(R.id.txtName);
        txtAdress = findViewById(R.id.txtAdress);
        txtPhone = findViewById(R.id.txtPhone);
        txtMail = findViewById(R.id.txtMail);
        txtBirthday = findViewById(R.id.txtBirthday);
        txtWeb = findViewById(R.id.txtWeb);
        ivPicture = findViewById(R.id.ivPicture);


        btnShow = findViewById(R.id.btnShow);
        btnSms = findViewById(R.id.btnSms);
        btnCall = findViewById(R.id.btnCall);
        btnDelete = findViewById(R.id.menuDelete);

        Bundle extras = getIntent().getExtras();
        friend = ((Friend) extras.getSerializable("FRIEND"));

        smsPhone();
        callPhone();
        setFriendInfo();
        sendMail();
        openWebsite();
        takePicture();
        openMap(friend);

    }

    public FriendActivity() {
        friendService = FriendService.getInstance();

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public void setHomeAddress(View v) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(!checkLocationPermission())
            {
                return;
            }
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            geocoder = new Geocoder(getBaseContext());
                            List<Address> address = new ArrayList<>();
                            try {
                                address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            friend.setAddress(address.get(0).getAddressLine(0));
                            friendService.updateFriend(friend);
                            setFriendInfo();

                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menuDelete) {
            deleteAlertBox();
        } else if (id == R.id.menuEdit) {
            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));


            Intent intent = new Intent();
            intent.setClass(FriendActivity.this, EditFriendActivity.class);
            intent.putExtra("FRIEND", friend);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void setFriendInfo() {

        txtName.setText(friend.getName());
        txtAdress.setText(friend.getAddress());
        txtPhone.setText(friend.getPhone() + "");
        txtMail.setText(friend.getMail());
        txtWeb.setText(friend.getWebsite());
        txtBirthday.setText(friend.getBirthday());

        if (friend.getPicture().equals(""))
        {
            ivPicture.setImageDrawable(getResources().getDrawable(R.drawable.placeholder));
        }
        else
        {
            ivPicture.setImageURI(Uri.parse(friend.getPicture()));
        }
    }

    public void smsPhone() {
        btnSms.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode(friend.getPhone() + "")));
                startActivity(intent);
            }
        });
    }

    public void callPhone() {
        btnCall.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + friend.getPhone() + ""));
                startActivity(intent);
            }
        });
    }

    public void sendMail() {
        txtMail.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{friend.getMail()});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(intent, "Send"));
            }
        });
    }

    public void openWebsite() {
        txtWeb.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));
            String url = friend.getWebsite();

            @Override
            public void onClick(View view) {
                if (!url.startsWith("https://") && !url.startsWith("http://")) {
                    url = "http://" + url;
                }
                Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(openUrlIntent);
            }
        });
    }

    public void openMap(final Friend entry) {
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, Map_details.class);
                intent.putExtra("FRIEND", entry);
                startActivity(intent);
            }
        });
    }

    public void deleteFriend() {
        Bundle extras = getIntent().getExtras();
        Friend friend = ((Friend) extras.getSerializable("FRIEND"));

        friendService.deleteFriend(friend);

        openMain();

        Toast.makeText(FriendActivity.this, "You deleted " + friend.getName(),
                Toast.LENGTH_LONG).show();

    }

    public void deleteAlertBox() {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you really want to delete " + friend.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteFriend();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();


    }


            private void takePicture()
            {
                ivPicture.setOnClickListener(new View.OnClickListener() {

                    Bundle extras = getIntent().getExtras();
                    Friend friend = ((Friend) extras.getSerializable("FRIEND"));

                    @Override
                    public void onClick(View view) {
                        onClickTakePics();
                    }
                });
            }

    private String appFolderCheckandCreate(){

        String appFolderPath="";
        File externalStorage = Environment.getExternalStorageDirectory();

        if (externalStorage.canWrite())
        {
            appFolderPath = externalStorage.getAbsolutePath() + "/MyApp";
            File dir = new File(appFolderPath);

            if (!dir.exists())
            {
                dir.mkdirs();
            }

        }
        else
        {
            //showToast("  Storage media not found or is full ! ");
        }

        return appFolderPath;
    }



    private String getTimeStamp() {

        final long timestamp = new Date().getTime();

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        final String timeString = new SimpleDateFormat("HH_mm_ss_SSS").format(cal.getTime());


        return timeString;}

    private void onClickTakePics()
    {
        mFile = new File(appFolderCheckandCreate(), "img" + getTimeStamp() + ".jpg");
        uriSavedImage = Uri.fromFile(mFile);

        // create Intent to take a picture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        intent.putExtra("return-data", true);

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bundle extras = getIntent().getExtras();
                Friend friend = ((Friend) extras.getSerializable("FRIEND"));

                friend = new Friend(friend.getId(), friend.getName(), friend.getAddress(), 00.00, 00.00, friend.getPhone(), friend.getMail(), friend.getWebsite(), friend.getBirthday(), this.uriSavedImage + "");
                friendService.updateFriend(friend);
                ivPicture.setImageURI(Uri.parse(friend.getPicture()));

            } else
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show();
                return;

            } else
                Toast.makeText(this, "Picture NOT taken - unknown error...", Toast.LENGTH_LONG).show();
        }
    }

    private void openMain()
    {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

}






