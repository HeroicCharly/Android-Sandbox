package heroiccharly.helloworld;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import models.GpsPoint;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Global variables
    private int ToastDuration = Toast.LENGTH_SHORT;
    private FusedLocationProviderClient locationClient;
    private GoogleMap _map;
    private GpsPoint currentUserLocation= new GpsPoint();
    private static final int LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button acceptBtn = findViewById(R.id.btn_accept);

        locationClient = LocationServices.getFusedLocationProviderClient(this);

        //Set the behavior for the map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Set the behavior for the toast
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = ((EditText) findViewById(R.id.toastMessage)).getText().toString();

                if (text.length() > 0) {
                    displayToast(text);
                }
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //If we don't have the permission, we ask for them
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION);
        }

        locateUser();

        _map = googleMap;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case LOCATION_PERMISSION:{
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    //If we get the permission for the first time, we set the marker on our position
                    locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                _map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Here I am"));
                            }
                        }
                    });
                }
            }
        }
    }

    private void setMarker(GpsPoint gpsPoint, String markerName){
        if(_map != null){
            _map.addMarker(new MarkerOptions().position(new LatLng(gpsPoint.get_latitude(),gpsPoint.get_longitude())).title(markerName));
        }
    }
    private void locateUser(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentUserLocation.set_longitude(location.getLongitude());
                        currentUserLocation.set_latitude(location.getLatitude());

                        setMarker(currentUserLocation,"User's current location");
                        _map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentUserLocation.get_latitude(),currentUserLocation.get_longitude()),15));
                    }
                }
            });
        }else{
            displayToast("Woops! no permissions");
        }
    }
    private void displayToast(String message){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context,message,ToastDuration);
        toast.show();
        CharSequence text = ((EditText) findViewById(R.id.toastMessage)).getText();
    }
}
