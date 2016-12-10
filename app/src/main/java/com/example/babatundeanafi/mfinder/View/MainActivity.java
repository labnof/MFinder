package com.example.babatundeanafi.mfinder.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.babatundeanafi.mfinder.Model.adapters.MsqCursorAdapter;
import com.example.babatundeanafi.mfinder.Model.adapters.MySQLiteHelper;
import com.example.babatundeanafi.mfinder.Model.control.LoadDataFromJsonToDB;
import com.example.babatundeanafi.mfinder.Model.model.Mosque;
import com.example.babatundeanafi.mfinder.R;
import com.example.babatundeanafi.mfinder.View.Messages.ShowMessageAsync;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener

         {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;


    // MySQLiteHelper is a SQLiteOpenHelper class connecting to SQLite
    private MySQLiteHelper db;
    private ListView msqListView;

             private AlertDialog.Builder builder; // for Dialog

             @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

                 // for Dialog
                 builder = new AlertDialog.Builder(
                         MainActivity.this);



                 // FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





        // Database
        db = new MySQLiteHelper(this);//Initialize the DatabaseHelper

        // Loading Data in to DB
        File database = getApplicationContext().getDatabasePath(db.DATABASE_NAME);
        if (!database.exists()) {

            Log.d("Insert: ", "Inserting ..");
            AddJsonMosquesDB(); // Adds the Mosques to DB
            populateListview();// Displace the Mosques on the ListView

        } else {
            populateListview();


        }

        /*   MAP
        * ATTENTION: This was auto-generated to implement the App Indexing API.
        *See https://g.co/AppIndexing/AndroidStudio for more information.
        */

        //checks if device version is higher than or equal to Marshmallow

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }


    // Function to populate ListView
    private void populateListview() {

        Cursor c = db.getAllMosques1();
        MsqCursorAdapter msqcursorAdt;
        msqcursorAdt = new MsqCursorAdapter(getBaseContext(), c);

        // Find the ListView resource.
        msqListView = (ListView) findViewById(R.id.msqListView);
        msqListView.setAdapter(msqcursorAdt);
    }

    //Fuction to add Mosq to Db
    private void AddJsonMosquesDB() {

        LoadDataFromJsonToDB ld = new LoadDataFromJsonToDB(getApplicationContext());
        List<Mosque> mosquesList = ld.loadFromJsonToDB(ld.getMsqStringFromJson(R.raw.mosques));
        ld.addMosquetoDBfromJSON(db, mosquesList);

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }








    // Map Functions Starts here/ Google API

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     * Reference:http://stackoverflow.com/questions/34582370/how-can-i-show-current-location-on-a-google-map-on-android-marshmallow/34582595#34582595
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        String title = "Marker in Sydney";
        mAddMarker(sydney, title);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

             //Method to Add Marker
             public void mAddMarker(LatLng latlng, String title) {

                 // Add a marker in Sydney
                 LatLng sydney = latlng;
                 String discription = title;
                 mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(discription));

             }



             //Function to Initialize Google Play Services
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();


    }



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //TODO:
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ShowMessageAsync m = new ShowMessageAsync();
                m.onPostExecute(builder);
                m.execute();

                //Prompt the user once explanation has been shown
                //(just doing it here for now, note that with this code, no explanation is shown)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

             //Function to show AlertDialog
             public void ShowAlertDialog( ) {

                 AlertDialog.Builder alertDialog;

                 alertDialog = new AlertDialog.Builder(getApplication());
                 alertDialog.setTitle("title");
                 alertDialog.setMessage("msg");

                 alertDialog.show();
             }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

             //LocationListener Ends
             @Override
             public void onLocationChanged(Location location) {

             }

             @Override
             public void onStatusChanged(String s, int i, Bundle bundle) {

             }

             @Override
             public void onProviderEnabled(String s) {

             }

             @Override
             public void onProviderDisabled(String s) {

             }

             @Override
             public void onConnected(@Nullable Bundle bundle) {

             }

             @Override
             public void onConnectionSuspended(int i) {

             }

             @Override
             public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

             }

             /**
              * ATTENTION: This was auto-generated to implement the App Indexing API.
              * See https://g.co/AppIndexing/AndroidStudio for more information.
              */
             public Action getIndexApiAction() {
                 Thing object = new Thing.Builder()
                         .setName("Main Page") // TODO: Define a title for the content shown.
                         // TODO: Make sure this auto-generated URL is correct.
                         .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                         .build();
                 return new Action.Builder(Action.TYPE_VIEW)
                         .setObject(object)
                         .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                         .build();
             }

             @Override
             public void onStart() {
                 super.onStart();

                 // ATTENTION: This was auto-generated to implement the App Indexing API.
                 // See https://g.co/AppIndexing/AndroidStudio for more information.
                 mGoogleApiClient.connect();
                 AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
             }

             @Override
             public void onStop() {
                 super.onStop();

                 // ATTENTION: This was auto-generated to implement the App Indexing API.
                 // See https://g.co/AppIndexing/AndroidStudio for more information.
                 AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
                 mGoogleApiClient.disconnect();
             }


         }




