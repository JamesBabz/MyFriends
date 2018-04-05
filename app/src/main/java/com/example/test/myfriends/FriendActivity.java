package com.example.test.myfriends;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FriendActivity extends AppCompatActivity {

    private final static String LOGTAG = "Camera01";
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    File mFile;

    TextView txtName;
    TextView txtAdress;
    TextView txtPhone;
    TextView txtMail;
    TextView txtBirthday;
    TextView txtWeb;
    ImageView ivPicture;

    Button btnShow;
    ImageButton btnSms;
    ImageButton btnCall;

    private FriendService friendService;

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

        smsPhone();
        callPhone();
        setFriendInfo();
        sendMail();
        openWebsite();
        takePicture();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();


        if(id == R.id.menuDelete)
        {
            deleteAlertBox();
        }
        else if (id == R.id.menuEdit)
        {
            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));


            Intent intent = new Intent();
            intent.setClass(FriendActivity.this, EditFriendActivity.class);
            intent.putExtra("FRIEND", friend);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void setFriendInfo()
    {
        Bundle extras = getIntent().getExtras();
        Friend friend = ((Friend) extras.getSerializable("FRIEND"));

        txtName.setText(friend.getName());
        txtAdress.setText(friend.getAddress());
        txtPhone.setText(friend.getPhone() + "");
        txtMail.setText(friend.getMail());
        txtWeb.setText(friend.getWebsite());
        txtBirthday.setText(friend.getBirthday());
        ivPicture.setImageDrawable(getResources().getDrawable(R.drawable.download));

        openMap(friend);
    }


    public FriendActivity() {
        friendService = FriendService.getInstance();
    }


    public void smsPhone()
    {
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

    public void callPhone()
    {
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

    public void sendMail()
    {
        txtMail.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {friend.getMail()});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");

                /* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(intent,"Send"));
            }
        });
    }

    public void openWebsite()
    {
        txtWeb.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));
            String url = friend.getWebsite();

            @Override
            public void onClick(View view) {
                if (!url.startsWith("https://") && !url.startsWith("http://")){
                    url = "http://" + url;
                }
                Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(openUrlIntent);
            }
        });
    }

    public void openMap(final Friend entry)
    {
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendActivity.this, Map_details.class);
                intent.putExtra("FRIEND", entry);
                startActivity(intent);
            }
        });
    }

    public void deleteFriend()
    {
            Bundle extras = getIntent().getExtras();
            Friend friend = ((Friend) extras.getSerializable("FRIEND"));


                friendService.deleteFriend(friend);


                Toast.makeText(FriendActivity.this, "You deleted " + friend.getName(),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent);
            }

            public void deleteAlertBox()
            {
                Bundle extras = getIntent().getExtras();
                Friend friend = ((Friend) extras.getSerializable("FRIEND"));


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

    private void onClickTakePics()
    {

        mFile = getOutputMediaFile(); // create a file to save the image
        if (mFile == null)
        {
            Toast.makeText(this, "Could not create file...", Toast.LENGTH_LONG).show();
            return;
        }
        // create Intent to take a picture
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }


    /** Create a File for saving an image */
    private File getOutputMediaFile(){


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Camera01");

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String postfix = "jpg";
        String prefix = "IMG";

        File mediaFile = new File(mediaStorageDir.getPath() +
                File.separator + prefix +
                "_"+ timeStamp + "." + postfix);

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showPictureTaken(mFile);

            } else
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show();
                return;

            } else
                Toast.makeText(this, "Picture NOT taken - unknown error...", Toast.LENGTH_LONG).show();
        }
    }

    private void showPictureTaken(File f) {
        Picasso.with(this).load(Uri.fromFile(f)).into(ivPicture);
    }
}




