package com.example.test.myfriends.DAL;

import com.example.test.myfriends.Entity.Friend;

import java.util.ArrayList;

/**
 * Created by thomas on 12-03-2018.
 */

public class FriendRepository {

    private ArrayList<Friend> allFriends;

    public FriendRepository() {
        allFriends = new ArrayList<>();
    }

    public ArrayList<Friend> getAllFriends(){
        return allFriends;
    }

    public void createFriend(Friend friend) {
        allFriends.add(friend);
    }
}
