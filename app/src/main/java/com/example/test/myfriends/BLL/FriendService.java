package com.example.test.myfriends.BLL;

import android.content.Context;

import com.example.test.myfriends.DAL.DAO;
import com.example.test.myfriends.Entity.Friend;
import com.example.test.myfriends.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

    //Singleton
    public static FriendService getInstance( ) {
        if (instance == null)
        {
            return new FriendService();
        }
        return instance;
    }

    // Returns all friends from repository
    public ArrayList<Friend> getAllFriends()
    {
        return dao.getAll();
    }
  
    //Sends the created friend from UI to the DAO
    public void createFriend(Friend friend)
    {
        dao.insert(friend);
    }

    //Checks if a friend has birthday
    public boolean isItBirthday(String birthday)
    {
        //substrings the birthday from the friend. Removes yyyy
        String Main_String = birthday;
        int last = Main_String.lastIndexOf("-");
        String substringBirthday = Main_String.substring(0,last);

        //create a new dateString with only day and month
        String today = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());

        if(today.equals(substringBirthday))
        {
            return true;
        } else
            return false;
    }

}
