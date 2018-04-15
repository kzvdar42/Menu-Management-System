package com.example.kzvda.menumanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {

    private static final String MENU_TABLE = "templates";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PRICE = "price";
    public static final String KEY_BITMAP = "bitmap";


    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table "+MENU_TABLE+" ("
                + KEY_ID + "integer primary key autoincrement,"
                + KEY_NAME + "text,"
                + KEY_DESCRIPTION + "text,"
                + KEY_PRICE + "int,"
                + KEY_BITMAP + "int" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MENU_TABLE);

        // Create tables again
        onCreate(db);
    }
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */


//adding an event (NEEDS TO ADD DRAWABLE)
    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, event.get_Id()); //Event ID
        values.put(KEY_NAME, event.get_name()); // Event Title
        values.put(KEY_DESCRIPTION, event.get_description()); // Event Time
        values.put(KEY_PRICE, event.get_price()); // Event Date
        values.put(KEY_BITMAP, event.get_bitmap()); // Event Drawable

        // Inserting Row
        db.insert(MENU_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Event getEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MENU_TABLE, new String[] { KEY_ID,
                        KEY_DESCRIPTION, KEY_PRICE, KEY_BITMAP }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Event event = new Event(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3), null);
        // return contact
        return event;
    }


    // Getting All Contacts
    public List<Event> getAllContacts() {
        List<Event> eventList = new ArrayList<Event>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MENU_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.set_Id(Integer.parseInt(cursor.getString(0)));
                event.set_name(cursor.getString(1));
                event.set_description(cursor.getString(2));
                event.set_price(cursor.getInt(3));
                // Adding contact to list
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        // return contact list
        return eventList;
    }

    // Getting event Count
    public int getEventsCount() {
        String countQuery = "SELECT  * FROM " + MENU_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single contact
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, event.get_name());
        values.put(KEY_DESCRIPTION, event.get_description());
        values.put(KEY_PRICE, event.get_price());

        // updating row
        return db.update(MENU_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(event.get_Id()) });
    }

    // Deleting single contact
    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MENU_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(event.get_Id()) });
        db.close();
    }
}
