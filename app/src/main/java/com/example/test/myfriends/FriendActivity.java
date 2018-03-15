package com.example.test.myfriends;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    Button button2;

    Friend newFriend;

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

<<<<<<< HEAD
        button2 = findViewById(R.id.button2);

=======
        setFriendInfo();

    }

    private void setFriendInfo()
    {
>>>>>>> origin/Development
        Bundle extras = getIntent().getExtras();

        Friend friend = ((Friend) extras.getSerializable("FRIEND"));

        txtName.setText(friend.getName());
        txtAdress.setText(friend.getAddress());
        txtPhone.setText(friend.getPhone() + "");
        txtMail.setText(friend.getMail());
        txtWeb.setText(friend.getWebsite());
        txtBirthday.setText(friend.getBirthday());
        ivPicture.setImageDrawable(getResources().getDrawable(R.drawable.download));

        callPhone();


<<<<<<< HEAD
    }

=======
>>>>>>> origin/Development
    public FriendActivity() {
        friendService = FriendService.getInstance();
    }

<<<<<<< HEAD
    private void createFriend()
    {
        newFriend = new Friend(1, "Knud", "Storegade 23", null, 12345678,"knud@mail.dk", "knudshjemmeside.dk", "23-03-81", null);
        friendService.createFriend(newFriend);

        txtAdress.setText(newFriend.getAddress());
        txtPhone.setText(newFriend.getPhone() + "");
        txtMail.setText(newFriend.getMail());
        txtWeb.setText(newFriend.getWebsite());
        txtBirthday.setText(newFriend.getBirthday());
        ivPicture.setImageDrawable(getResources().getDrawable(R.drawable.download));
    }


    public void callPhone()
    {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode("40470903")));
                startActivity(intent);
                }
        });
    }
=======
>>>>>>> origin/Development
}

