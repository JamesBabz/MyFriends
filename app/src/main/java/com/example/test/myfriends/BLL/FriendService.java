package com.example.test.myfriends.BLL;

import android.content.Context;

import com.example.test.myfriends.DAL.DAO;
import com.example.test.myfriends.Entity.Friend;

import java.util.ArrayList;

/**
 * Created by thomas on 12-03-2018.
 */

public class FriendService {

    private static FriendService instance = null;


    private static DAO dao;

    public static void setContext(Context c)
    {
        dao = new DAO(c);
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
        return dao.getAll();
    }

    public void createFriend(Friend friend)
    {
        dao.insert(friend);
    }


}
