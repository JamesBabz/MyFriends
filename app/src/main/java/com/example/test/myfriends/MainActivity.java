package com.example.test.myfriends;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
        friendService.setContext(this);

        listViewFriends = findViewById(R.id.listViewFriends);
     // createFriend();
        allFriends = friendService.getAllFriends();
        listAdapter = new ListAdapter(this, R.layout.cell_extended, allFriends);
        listViewFriends.setAdapter(listAdapter);

        addListenerOnList();

    }

    public MainActivity() {

        friendService = FriendService.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.optAddFriend:
                    openDetailActivity();
                    return true;
                case R.id.optClose:
                    closeApp();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }

    private void openDetailActivity(){
        Intent intent = new Intent();
        intent.setClass(this, CreateFriendActivity.class);
        startActivity(intent);

    }



    private void closeApp() {
        finish();
    }

    //Listens on witch item is clicked and
    private void addListenerOnList() {
        listViewFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend entry = (Friend) parent.getItemAtPosition(position);
                openFriendView(entry);
            }
        });
    }

    //Opens FriendActivity with all information about the selected friend
    private void openFriendView(Friend entry){

        Intent intent = new Intent(MainActivity.this, FriendActivity.class);
        intent.putExtra("FRIEND", entry);
        startActivity(intent);
    }
}


class ListAdapter extends ArrayAdapter<Friend> {

    private ArrayList<Friend> friends;
    Context context;
    private FriendService friendService;

    // Array of colors to set in listView
    private final int[] colors = {
            Color.parseColor("#ffffff"),
            Color.parseColor("#b3cbf2")
    };

    public ListAdapter(Context context, int textViewResourceId,
                       ArrayList<Friend> friends) {
        super(context, textViewResourceId, friends);
        this.friends = friends;
        this.context = context;
        friendService = FriendService.getInstance();
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
        ImageView birthday = v.findViewById(R.id.imageViewBirthday);


        name.setText(friend.getName());
        phone.setText(friend.getPhone() + "");
        picture.setImageURI(Uri.parse(friend.getPicture()));

        //Sets the image of Dannebrog if the friend has birthday
       /* if(friendService.isItBirthday(friend.getBirthday()))
        {
            birthday.setImageDrawable(context.getResources().getDrawable(R.drawable.dannebrog));
        }*/

        return v;
    }


}

