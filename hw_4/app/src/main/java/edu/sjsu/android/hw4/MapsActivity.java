package edu.sjsu.android.hw4;



import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Maps Activity acts as the main activity for the Extended Maps App
 * It loads the Maps Fragment and the Loader and Classes
 * It holds 2 inner Async Classes with designated tasks
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {
    private GoogleMap mMap;
    private final LatLng LOCATION_UNIV = new LatLng(37.335371, -121.881050);
    private final LatLng LOCATION_CS = new LatLng(37.333714, -121.881860);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Invoke LoaderCallbacks to retrieve and draw already saved locations in map
        Loader<Cursor> cursor = onCreateLoader(0, null);
        Cursor data = getContentResolver().query(LocationsContentProvider.CONTENT_URI, null, null, null,
                null);
        onLoadFinished(cursor, data);

        //Setting Up On Click Listener Actions
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                drawMarker(latLng);


                //Instance of Content Values and putting in the data to be added into the database
                ContentValues locationContentValues = new ContentValues();
                locationContentValues.put(LocationDB.LATITUDE, latLng.latitude);
                locationContentValues.put(LocationDB.LONGITUDE, latLng.longitude);
                locationContentValues.put(LocationDB.MAP_ZOOM_LEVEL, mMap.getCameraPosition().zoom);

                //insert operation with the data
                getContentResolver().insert(LocationsContentProvider.CONTENT_URI, locationContentValues);
                //LocationInsertTask begins
                LocationInsertTask insertTask = new LocationInsertTask();
                insertTask.doInBackground(locationContentValues);
                Toast.makeText(getBaseContext(),
                        Html.fromHtml("<font color='#000000' ><b>" + "Marker is added to the Map" + "</b></font>"), Toast.LENGTH_SHORT).show();

            }
        });
        //OnMapClick Ends

        //OnMapLongClick
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                LocationDeleteTask deleteTask = new LocationDeleteTask();
                deleteTask.doInBackground();
                Toast.makeText(getBaseContext(),
                        Html.fromHtml("<font color='#000000' ><b>" + "All markers are removed" + "</b></font>"), Toast.LENGTH_LONG).show();
            }
        });
        //OnMapLongClick Ends
    }
    //OnMapReady() Ends

    public void onClick_CS(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18);
        mMap.animateCamera(update);
    }

    public void onClick_Univ(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14);
        mMap.animateCamera(update);
    }

    public void onClick_City(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 10);
        mMap.animateCamera(update);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id,  Bundle args) {
        Uri uri = LocationsContentProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    /**
     * Method to Load the data from the database and running on load tasks
     */
    public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
        int locationCount = 0;
        double latitude = 0;
        double longitude = 0;
        float zoom = 0;

        // Number of locations available in the SQLite database table
        locationCount = data.getCount();

        // Move the current record pointer to the first row of the table
        if(data != null){
            data.moveToFirst();
        }
        else {
            locationCount = 0;
        }

        for(int i=0;i<locationCount;i++){
            // Get the latitude
            latitude = data.getDouble(data.getColumnIndex(LocationDB.LATITUDE));
            // Get the longitude
            longitude = data.getDouble(data.getColumnIndex(LocationDB.LONGITUDE));
            // Get the zoom level
            zoom = data.getFloat(data.getColumnIndex(LocationDB.MAP_ZOOM_LEVEL));
            // Creating an instance of LatLng to plot the location in Google Maps
            LatLng location = new LatLng(latitude, longitude);
            System.out.println("location: \n" + "Lat: " + location.latitude + "Long: " + location.longitude);
            // Drawing the marker in the Google Maps
            drawMarker(location);
            // Traverse the pointer to the next row
            data.moveToNext();
        }

        if(locationCount>0){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom);
            mMap.animateCamera(update);
        }
    }


    @Override
    public void onLoaderReset( Loader<Cursor> loader) {
        Cursor data = getContentResolver().query(LocationsContentProvider.CONTENT_URI, null, null, null,
                null);
        onLoadFinished(loader, data);
    }

    /**
     * Inner Async Insert Task that adds the location values
     *                  to the SQLLite in the background task
     */
    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }


    /**
     * Inner Async Insert Task that adds the location values
     *                  to the SQLLite in the background task
     */
    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null);
            return null;
        }
    }

    /**
     * Add a marker to the existing map fragment at given location
     * @param location the location value for the marker
     */
    private void drawMarker(LatLng location) {
        //getting bitmap from a custom marker image
        BitmapDrawable bd = (BitmapDrawable)getResources().getDrawable(R.drawable.gray);
        Bitmap smallGrayMarker = Bitmap.createScaledBitmap(bd.getBitmap(), 70, 100, false);
        //Creating a custom marker option
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location)
                .title("Lat: " + location.latitude + " Long: " + location.longitude)
                .snippet("Marker")
                .icon(BitmapDescriptorFactory.fromBitmap(smallGrayMarker));
        //Adding a custom on the map
        mMap.addMarker(markerOptions);
    }

}