package com.example.test.myfriends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.myfriends.BLL.FriendService;
import com.example.test.myfriends.Entity.Friend;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewFriends;
    ListAdapter listAdapter;
    private FriendService friendService;
    private ArrayList<Friend> allFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewFriends = findViewById(R.id.listViewFriends);
        createFriend();
        allFriends = friendService.getAllFriends();
        listAdapter = new ListAdapter(this, R.layout.cell_extended, allFriends);
        listViewFriends.setAdapter(listAdapter);

        openFriendActivity();

    }

    public MainActivity() {

        friendService = FriendService.getInstance();
    }

    private void createFriend()
    {
        Friend newFriend = new Friend(1, "Knud", "Storegade 23", null, 12345678,"knud@mail.dk", "knudshjemmeside.dk", "23-03-81", null);
        Friend newFriend2 = new Friend(2, "Kristian", "Lillevej 55", null, 12345678,"kristian@mail.dk", "kristianshjemmeside.dk", "23-03-81", null);

        Friend newFriend3 = new Friend(2, "Kristian", "Lillevej 55", null, 12345678,"kristian@mail.dk", "kristianshjemmeside.dk", "23-03-81", null);

        Friend newFriend4 = new Friend(2, "Kristian", "Lillevej 55", null, 12345678,"kristian@mail.dk", "kristianshjemmeside.dk", "23-03-81", null);
        friendService.createFriend(newFriend);
        friendService.createFriend(newFriend2);
        friendService.createFriend(newFriend3);
        friendService.createFriend(newFriend4);



    }

    private void openFriendActivity() {
        listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Friend entry = (Friend) parent.getItemAtPosition(position);


                Intent intent = new Intent(MainActivity.this, FriendActivity.class);
                intent.putExtra("Name", entry.getName());
                startActivity(intent);
            }

        });


    }
}

class ListAdapter extends ArrayAdapter<Friend> {

    private ArrayList<Friend> friends;

    private final int[] colors = {
            Color.parseColor("#ffffff"),
            Color.parseColor("#b3cbf2")
    };

    public ListAdapter(Context context, int textViewResourceId,
                       ArrayList<Friend> friends) {
        super(context, textViewResourceId, friends);
        this.friends = friends;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {

        String TAG = "tag";
        if (v == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            v = li.inflate(R.layout.cell_extended, parent,false);
            Log.d(TAG, "Position: " + position + " View Reused");
        }
        else
            Log.d(TAG, "Position: " + position + " View Reused");

        v.setBackgroundColor(colors[position % colors.length]);


        Friend friend = friends.get(position);


        TextView name = v.findViewById(R.id.twName);
        TextView phone = v.findViewById(R.id.twPhone);
        ImageView picture = v.findViewById(R.id.imageViewFriend);


        name.setText(friend.getName());
        phone.setText(friend.getPhone() +"");



        return v;
    }

}

