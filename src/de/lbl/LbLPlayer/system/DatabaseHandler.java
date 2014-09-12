//package de.lbl.LbLPlayer.system;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.DatabaseErrorHandler;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import de.lbl.LbLPlayer.model.*;
//public class DatabaseHandler extends SQLiteOpenHelper {
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 1;
//    // Database Name
//    private static final String DATABASE_NAME = "bookmarkManager";
//    // Contacts table name
//    private static final String TABLE_BOOKMARKS = "bookmarks";
//    // Contacts Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_IMAGE = "IMAGE";
//    private static final String KEY_H1 = "h1";
//    private static final String KEY_H2 = "h2";
//    private static final String KEY_H3 = "h3";
//    private static final String KEY_LINK = "link";
//    public DatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//    public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//    public DatabaseHandler(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_BOOKMARKS_TABLE = "CREATE TABLE " + TABLE_BOOKMARKS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMAGE + " TEXT," + KEY_H1 + " TEXT," + KEY_H2 + " TEXT, " + KEY_H3 + " TEXT, "
//			+ KEY_LINK + " TEXT" + ")";
//        db.execSQL(CREATE_BOOKMARKS_TABLE);
//    }
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
//        // Create tables again
//        onCreate(db);
//    }
//    public void addEntry(Entry book) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_IMAGE, getImageByte(book.screenshot));
//        values.put(KEY_H1, book.date);
//        values.put(KEY_H2, book.name);
//        values.put(KEY_H3, book.label);
//        values.put(KEY_LINK, book.link);
//        // Inserting Row
//        db.insert(TABLE_BOOKMARKS, null, values);
//        db.close(); // Closing database connection
//    }
//    public Bookmark getBookmark(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_BOOKMARKS, new String[] { KEY_ID, KEY_IMAGE, KEY_H1, KEY_H2, KEY_H3, KEY_LINK }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//        Bookmark book = new Bookmark();
//        book.id = Integer.parseInt(cursor.getString(0));
//        book.screenshot = getImageFromByte(cursor.getBlob(1));
//        book.date = cursor.getString(2);
//        book.name = cursor.getString(3);
//        book.label = cursor.getString(4);
//        book.link = cursor.getString(5);
//        return book;
//    }
//    public List<Bookmark> getAllBookmarks() {
//        List<Bookmark> bookmarkList = new ArrayList<Bookmark>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_BOOKMARKS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Bookmark book = new Bookmark();
//                book.id = Integer.parseInt(cursor.getString(0));
//                book.screenshot = getImageFromByte(cursor.getBlob(1));
//                book.date = cursor.getString(2);
//                book.name = cursor.getString(3);
//                book.label = cursor.getString(4);
//                book.link = cursor.getString(5);
//                // Adding Bookmark to list
//                bookmarkList.add(book);
//            } while (cursor.moveToNext());
//        }
//
//        // return Bookmark list
//        return bookmarkList;
//    }
//
//    public int getBookmarksCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_BOOKMARKS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
//
//    public int updateBookmark(Bookmark book) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_IMAGE, getImageByte(book.screenshot));
//        values.put(KEY_H1, book.date);
//        values.put(KEY_H2, book.name);
//        values.put(KEY_H3, book.label);
//        values.put(KEY_LINK, book.link);
//
//        // updating row
//        return db.update(TABLE_BOOKMARKS, values, KEY_ID + " = ?",
//						 new String[] { String.valueOf(book.id) });
//    }
//
//
//    public void deleteBookmark(Bookmark book) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_BOOKMARKS, KEY_ID + " = ?",
//				  new String[] { String.valueOf(book.id) });
//        db.close();
//    }
//
//    public byte[] getImageByte(Bitmap screenshot) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        screenshot.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }
//    public Bitmap getImageFromByte(byte[] byteArray) {
//        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//    }
//}
