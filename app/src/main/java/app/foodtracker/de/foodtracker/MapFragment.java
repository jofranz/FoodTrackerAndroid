package app.foodtracker.de.foodtracker;

import android.app.Fragment;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by normen on 16.10.17.
 */

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Button mapAdd;
    private LocationManager locationManager;
    private String provider;
    private LatLng currentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        mapAdd = (Button) rootView.findViewById(R.id.addButton);
        mapAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).checkLocationPermission()) {
                    provider = locationManager.getBestProvider(new Criteria(), false);

                    Location location = locationManager.getLastKnownLocation(provider);
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    currentLocation = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You are her")
                            .snippet("This is a snippet:" + currentLocation.toString()));
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

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }
}