package com.example.test.myfriends;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

public class FriendActivity extends AppCompatActivity {

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

    }




