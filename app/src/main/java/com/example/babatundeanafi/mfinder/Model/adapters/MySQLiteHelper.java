package com.example.babatundeanafi.mfinder.Model.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.babatundeanafi.mfinder.Model.model.Mosque;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference: http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * http://www.101apps.co.za/index.php/articles/using-a-sqlite-database-in-android.html
 * Created by babatundeanafi on 30/09/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MOSQUES = "mosques";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOSQUE_NAME = "name";
    public static final String COLUMN_MOSQUE_ADDRESS = "address";
    public static final String COLUMN_MOSQUE_PHONE = "phone";
    public static final String COLUMN_MOSQUE_EMAIL = "email";
    public static final String COLUMN_MOSQUE_LANG = "language";

    public static final String DATABASE_NAME = "mosques.db";
    private static final int DATABASE_VERSION = 2;



    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MOSQUES + "( " + COLUMN_ID
            + " integer primary key autoincrement, " +
            COLUMN_MOSQUE_NAME + " text not null,  " +
            COLUMN_MOSQUE_ADDRESS + " text not null," +
            COLUMN_MOSQUE_PHONE + " text not null," +
            COLUMN_MOSQUE_EMAIL + " text not null," +
            COLUMN_MOSQUE_LANG + " text not null);";




    private static final String DATABASE_INSERT = "insert into"
            + TABLE_MOSQUES + "( " + COLUMN_ID
            + " , " +
            COLUMN_MOSQUE_NAME + " ,  " +
            COLUMN_MOSQUE_ADDRESS + " ," +
            COLUMN_MOSQUE_PHONE + " ," +
            COLUMN_MOSQUE_EMAIL + " ," +
            COLUMN_MOSQUE_LANG +");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);


    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOSQUES);
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */





    // Adding new contact
    public void addMosque(Mosque mosque) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOSQUE_NAME, mosque.getName()); // Contact Name
        values.put(COLUMN_MOSQUE_ADDRESS, mosque.getAddress()); // Contact Address
        values.put(COLUMN_MOSQUE_PHONE, mosque.getPhone()); // Contact Address
        values.put(COLUMN_MOSQUE_EMAIL, mosque.getEmail()); // Contact Address
        values.put(COLUMN_MOSQUE_LANG, mosque.getLanguage()); // Contact Address

        // Inserting Row
        db.insert(TABLE_MOSQUES, null, values);
        db.close(); // Closing database connection

    }


    // Getting single contact
    Mosque getMosque(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOSQUES, new String[]{COLUMN_ID,
                        COLUMN_MOSQUE_NAME, COLUMN_MOSQUE_ADDRESS, COLUMN_MOSQUE_PHONE,
                        COLUMN_MOSQUE_EMAIL, COLUMN_MOSQUE_LANG}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Mosque mosque = new Mosque(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5));
        // return mosque
        return mosque;
    }

    // Getting All Contacts
    public List<Mosque> getAllMosques() {
        List<Mosque> contactList = new ArrayList<Mosque>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOSQUES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Mosque mosque = new Mosque();
                mosque.setID(Integer.parseInt(cursor.getString(0)));
                mosque.setName(cursor.getString(1));
                mosque.setAddress(cursor.getString(2));
                mosque.setPhone(cursor.getString(3));
                mosque.setEmail(cursor.getString(4));
                mosque.setLanguage(cursor.getString(5));
                // Adding contact to list
                contactList.add(mosque);
            } while (cursor.moveToNext());}

            // return contact list
            return contactList;
        }

    /** Returns all the customers in the table */
    public Cursor getAllMosques1(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_MOSQUES, new String[] {  COLUMN_ID,COLUMN_MOSQUE_NAME,COLUMN_MOSQUE_ADDRESS,COLUMN_MOSQUE_PHONE, COLUMN_MOSQUE_EMAIL, COLUMN_MOSQUE_LANG } ,
                null, null, null, null,
                COLUMN_MOSQUE_NAME + " asc ");

        if (c != null){
            c.moveToFirst();
        }

        return c;
    }




    // Updating single contact
    public int updateMosque(Mosque mosque) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOSQUE_NAME, mosque.getName()); // Contact Name
        values.put(COLUMN_MOSQUE_ADDRESS, mosque.getAddress()); // Contact Address
        values.put(COLUMN_MOSQUE_PHONE, mosque.getPhone()); // Contact Address
        values.put(COLUMN_MOSQUE_EMAIL, mosque.getEmail()); // Contact Address
        values.put(COLUMN_MOSQUE_LANG, mosque.getLanguage()); // Contact Address

        // updating row
        return db.update(TABLE_MOSQUES, values, COLUMN_ID+ " = ?",
                new String[] { String.valueOf(mosque.getID()) });
    }

    // Deleting single contact
    public void deleteMosque(Mosque mosque) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOSQUES, COLUMN_ID + " = ?",
                new String[] { String.valueOf(mosque.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getMosquesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOSQUES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }



}




