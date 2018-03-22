package com.example.test.myfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test.myfriends.BLL.FriendService;
import com.example.test.myfriends.Entity.Friend;

/**
 * Created by thomas on 22-03-2018.
 */

public class CreateEditFriendActivity extends AppCompatActivity {

    Button btnSave;
    Button btnDiscard;
    EditText txtName;
    EditText txtMail;
    EditText txtPhone;
    EditText txtAddress;
    EditText txtBirthday;
    EditText txtWebsite;

    FriendService friendService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_friend);
        btnSave = findViewById(R.id.btnSave);
        btnDiscard = findViewById(R.id.btnDiscard);
        txtName = findViewById(R.id.ctxtName);
        txtMail = findViewById(R.id.ctxtMail);
        txtPhone = findViewById(R.id.ctxtPhone);
        txtAddress = findViewById(R.id.ctxtAddress);
        txtBirthday = findViewById(R.id.ctxtBirthday);
        txtWebsite = findViewById(R.id.ctxtWebsite);

    }

    public CreateEditFriendActivity() {
        friendService = FriendService.getInstance();
    }

    public void CreateFriend(View v){

        Friend newfriend = new Friend(txtName.getText().toString(), txtAddress.getText().toString(), 00.00, 00.00, txtPhone.getText().toString(), txtMail.getText().toString(), txtWebsite.getText().toString(), txtBirthday.getText().toString(), null);
        friendService.createFriend(newfriend);

        openMain();

    }

    public void onclickDiscard(View v)
    {
       openMain();
    }

    private void openMain()
    {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }
}