package com.example.test.myfriends.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.test.myfriends.Entity.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob Enemark on 19-03-2018.
 */

public class DAO {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "friend_table";

    private static Context context;

    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;

    private static DAO m_instance;

    public static DAO getInstance()
    {
        if (m_instance == null) m_instance = new DAO(context);
        return m_instance;
    }

    public DAO(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    private static final String INSERT = "insert into " + TABLE_NAME
            + "(name, address, altitude, longitude, phone, mail, website, birthday, picture) values (? ,?, ?, ?, ?, ?, ?, ?, ?)";

    public long insert(Friend f) {
        this.insertStmt.bindString(1, f.getName());
        this.insertStmt.bindString(2, f.getAddress());
        this.insertStmt.bindDouble(3, f.getAltitude());
        this.insertStmt.bindDouble(4, f.getLongitude());
        this.insertStmt.bindString(5, f.getPhone() + "");
        this.insertStmt.bindString(6, f.getMail());
        this.insertStmt.bindString(7, f.getWebsite());
        this.insertStmt.bindString(8, f.getBirthday());
        this.insertStmt.bindString(9, f.getPicture() + "");


        long id = this.insertStmt.executeInsert();
        f.setId(id);
        return id;
    }

    public ArrayList<Friend> getAll() {
        ArrayList<Friend> list = new ArrayList<Friend>();
        Cursor cursor = this.db.query(TABLE_NAME,
                new String[]{"id", "name", "address", "altitude", "longitude", "phone", "mail", "website", "birthday", "picture"},
                null, null,
                null, null, "name desc");

        if (cursor.moveToFirst()) {
            do {
                list.add(new Friend(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                        ));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public void deleteById(long id)
    {
        this.db.delete(TABLE_NAME, "id = " + id, null);
    }

    public void deleteAll() {

        this.db.delete(TABLE_NAME, null, null);
    }

    public boolean updateFriend(Friend friend) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", friend.getId());
        contentValues.put("name", friend.getName());
        contentValues.put("address", friend.getAddress());
        contentValues.put("altitude", friend.getAltitude());
        contentValues.put("longitude", friend.getLongitude());
        contentValues.put("phone", friend.getPhone());
        contentValues.put("mail", friend.getMail());
        contentValues.put("website", friend.getWebsite());
        contentValues.put("birthday", friend.getBirthday());
        contentValues.put("picture", friend.getPicture() + "");

        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { friend.getId() + "" });

        return true;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + " (id INTEGER PRIMARY KEY, name TEXT, address TEXT, altitude INTEGER, longitude INTEGER, phone TEXT, mail TEXT, website TEXT, birthday TEXT, picture TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
