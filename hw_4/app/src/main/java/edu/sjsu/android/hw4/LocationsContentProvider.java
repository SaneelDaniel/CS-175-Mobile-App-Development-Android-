package edu.sjsu.android.hw4;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 *
 * Content Provider Class for the location data
 * Acts as a controller for the main activity to start database command execution
 */
public class LocationsContentProvider extends ContentProvider {

    /**
     * constant declarations  */
    static final String PROVIDER_NAME = "edu.sjsu.android.hw4";
    static final String URL = "content://" + PROVIDER_NAME + "/location";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int VERSION = 1;
    private SQLiteDatabase db;
    LocationDB dbHelper;
    Context context;
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "location", 1);
    }


    @Override
    public boolean onCreate() {
        context = getContext();
        dbHelper = new LocationDB(getContext());
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        if(uriMatcher.match(uri)==VERSION){
            return dbHelper.getAllLocations();
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        dbHelper.insert(this.context, uri, contentValues);
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int value = dbHelper.delete(uri);
        return value;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
