package com.example.test.myfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        setFriendInfo();

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

    }

    public FriendActivity() {
        friendService = FriendService.getInstance();
    }

}
