package oldandBusted;


import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

import app.foodtracker.de.foodtracker.R;
import app.foodtracker.de.foodtracker.SecondMainActivity;

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
        locationManager = (LocationManager)((SecondMainActivity)getActivity()).getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(crit,true);




        mapAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((SecondMainActivity)getActivity()).checkLocationPermission()) {





                    //create a payload for the marker
                    MarkerRepresentation markerRepresentation = new MarkerRepresentation();

                    currentLocation = new LatLng(lat, lng);

                    googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are her")
                            .snippet("This is a snippet:" + currentLocation.toString())).setTag(markerRepresentation);

                    //set own CustomInfoWindow
                    googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));


                    //experimental feature for decoding coordinate to address
                    Geocoder geoCoder = new Geocoder(getActivity().getBaseContext());
                    List<Address> matches = null;
                    try {
                        matches = geoCoder.getFromLocation(lat, lng, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                    Log.d("debug", bestMatch.toString());
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
                if(((SecondMainActivity)getActivity()).checkLocationPermission()){
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
        if(((SecondMainActivity)getActivity()).checkLocationPermission()) {

            Location location = locationManager.getLastKnownLocation(provider);

            //set current location
            if (location == null) {
                locationManager.requestLocationUpdates(provider, 0, 0, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            }
        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged","bonobo");
        if(((SecondMainActivity)getActivity()).checkLocationPermission()){
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
        //disable postion update when the Fragment get changed
        locationManager.removeUpdates(this);
    }




}