package com.example.test.myfriends.BLL;

import com.example.test.myfriends.DAL.FriendRepository;
import com.example.test.myfriends.Entity.Friend;

import java.util.ArrayList;

/**
 * Created by thomas on 12-03-2018.
 */

public class FriendService {

    private static FriendService instance = null;
    private FriendRepository repo;

    private FriendService() {
        repo = new FriendRepository();
    }

    public static FriendService getInstance( ) {
        if (instance == null)
        {
            return new FriendService();
        }
        return instance;
    }

    public ArrayList<Friend> getAllFriends()
    {
        return repo.getAllFriends();
    }
    public void createFriend(Friend friend)
    {
        repo.createFriend(friend);
    }


}
