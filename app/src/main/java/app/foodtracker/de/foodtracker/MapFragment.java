package app.foodtracker.de.foodtracker;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.security.Provider;

import app.foodtracker.de.foodtracker.Model.MarkerRepresentation;

/**
 * Created by normen on 16.10.17.
 */

public class MapFragment extends Fragment implements LocationListener{

    MapView mMapView;
    private GoogleMap googleMap;
    private Button mapAdd;
    private LocationManager locationManager;
    private String provider;
    private LatLng currentLocation;
    private double lat;
    private double lng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately
        mapAdd = (Button) rootView.findViewById(R.id.addButton);
        Criteria crit = new Criteria();
        locationManager = (LocationManager)((MainActivity)getActivity()).getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(crit,true);






        mapAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).checkLocationPermission()) {






                    MarkerRepresentation markerRepresentation = new MarkerRepresentation();

                    currentLocation = new LatLng(lat, lng);

                    googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are her")
                            .snippet("This is a snippet:" + currentLocation.toString())).setTag(markerRepresentation);
                    googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                }
            }
        });

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mapAdd.setEnabled(true);
                googleMap = mMap;

                // For showing a move to my location button
                if(((MainActivity)getActivity()).checkLocationPermission()){
                    googleMap.setMyLocationEnabled(true);
                }


                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Toast.makeText(getActivity(), "Info window clicked",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(((MainActivity)getActivity()).checkLocationPermission()) {

            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null) {
                locationManager.requestLocationUpdates(provider, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            }
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged","bonobo");
        if(((MainActivity)getActivity()).checkLocationPermission()){
             lat = location.getLatitude();
             lng = location.getLongitude();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }




}