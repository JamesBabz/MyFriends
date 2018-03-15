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

        Bundle extras = getIntent().getExtras();

         String name = ((String) extras.getSerializable("Name"));

        txtName.setText(name);
        createFriend();

    }


    public FriendActivity() {
        friendService = FriendService.getInstance();
    }

    private void createFriend()
    {
        Friend newFriend = new Friend(1, "Knud", "Storegade 23", null, 12345678,"knud@mail.dk", "knudshjemmeside.dk", "23-03-81", null);
        friendService.createFriend(newFriend);



        txtAdress.setText(newFriend.getAddress());
        txtPhone.setText(newFriend.getPhone() + "");
        txtMail.setText(newFriend.getMail());
        txtWeb.setText(newFriend.getWebsite());
        txtBirthday.setText(newFriend.getBirthday());
        ivPicture.setImageDrawable(getResources().getDrawable(R.drawable.download));

    }
}
