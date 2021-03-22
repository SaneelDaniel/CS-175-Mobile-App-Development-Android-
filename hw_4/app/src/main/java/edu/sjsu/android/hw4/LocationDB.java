package edu.sjsu.android.hw4;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 *
   Database Specific helper class
   Handles all the query execution state and controls
 */
public class LocationDB extends SQLiteOpenHelper {

    /**
     * Database specific constant declarations */
    static final String DATABASE_NAME = "MapsLocationDB";
    static final String TABLE_NAME = "location";
    static final int DATABASE_VERSION = 1;

    static final String _ID = "_id";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";
    static final String MAP_ZOOM_LEVEL = "zoom";

    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LATITUDE + " DOUBLE NOT NULL, " +
                    LONGITUDE +" DOUBLE NOT NULL, " +
                    MAP_ZOOM_LEVEL + " INTEGER NOT NULL);";
    private SQLiteDatabase db;

    /**
     * LocationDB Constructor
     * @param context
     */
    public LocationDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }

    @Override
    /**
     * If the database needs to be updated
     */
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
    }

    /**
     * Method that executes the insert location record into the location database
     * @param values location values
     * @return
     */
    public Uri insert(Context context, Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert( TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0)
        {
            LocationsContentProvider provider = new LocationsContentProvider();
            Uri _uri = ContentUris.withAppendedId(provider.CONTENT_URI, rowID);
            context.getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    /**
     * Method to execute delete all location data from the database
     * @param uri the content location path
     * @return execution result 1:0 True:False
     */
    public int delete(Uri uri) {
        int value = 0;
        value = db.delete(TABLE_NAME, null, null);
        //onCreate(db);
        if (value == 0){
            throw new IllegalArgumentException("Exception in 'Delete' (LocationDB). Unknown URI " + uri );
        }
        return value;
    }

    /**
     * Method to query the contents of the location database and fetch all saved data
     * @return Cursor to the data
     */
    public Cursor getAllLocations(){
        return db.query(TABLE_NAME, new String[] { _ID,  LATITUDE , LONGITUDE, MAP_ZOOM_LEVEL } , null, null, null, null, null);
    }

}
