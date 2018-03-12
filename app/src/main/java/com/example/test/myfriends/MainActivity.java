package com.example.test.myfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.test.myfriends.BLL.FriendService;
import com.example.test.myfriends.Entity.Friend;

public class MainActivity extends AppCompatActivity {

    private FriendService friendService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public MainActivity() {
        friendService = FriendService.getInstance();
    }

    private void createFriend()
    {
        Friend newFriend = new Friend(1, "Knud", "Storegade 23", null, 12345678,"knud@mail.dk", "knudshjemmeside.dk", "23-03-81", null);
        friendService.createFriend(newFriend);
    }
}
