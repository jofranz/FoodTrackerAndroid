package app.foodtracker.de.foodtracker

import android.app.Fragment
import android.content.Context
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import app.foodtracker.de.foodtracker.Model.MarkerRepresentation
import app.foodtracker.de.foodtracker.RecyclerAdapter
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

/**
 * Created by normen on 06.11.17.
 */
class MapFragment : Fragment(), LocationListener {

    internal lateinit var mMapView: MapView
    private var googleMap: GoogleMap? = null
    private var mapAdd: Button? = null
    private var locationManager: LocationManager? = null
    private var provider: String? = null
    private var currentLocation: LatLng? = null
    private var lat: Double = 0.toDouble()
    private var lng: Double = 0.toDouble()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.map_fragment, container, false)

        mMapView = rootView.findViewById<View>(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)


        mMapView.onResume() // needed to get the map to display immediately
        mapAdd = rootView.findViewById<View>(R.id.addButton) as Button
        val crit = Criteria()
        locationManager = (activity as SecondMainActivity).getSystemService(Context.LOCATION_SERVICE) as LocationManager
        provider = locationManager!!.getBestProvider(crit, true)




        mapAdd!!.setOnClickListener {
            if ((activity as SecondMainActivity).checkLocationPermission()) {


                //create a payload for the marker
                val markerRepresentation = MarkerRepresentation()

                currentLocation = LatLng(lat, lng)

                googleMap!!.addMarker(MarkerOptions().position(currentLocation!!).title("You are here")
                        .snippet("This is a snippet:" + currentLocation!!.toString())).tag = markerRepresentation

                //set own CustomInfoWindow
                googleMap!!.setInfoWindowAdapter(CustomInfoWindowAdapter(activity))
                googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))


                //experimental feature for decoding coordinate to address
                val geoCoder = Geocoder(activity.baseContext)
                var matches: List<Address>? = null
                try {
                    matches = geoCoder.getFromLocation(lat, lng, 1)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val bestMatch = if (matches!!.isEmpty()) null else matches[0]
                Log.d("debug", bestMatch!!.toString())
            }
        }

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync { mMap ->
            mapAdd!!.isEnabled = true
            googleMap = mMap

            // For showing a move to my location button
            if ((activity as SecondMainActivity).checkLocationPermission()) {
                googleMap!!.isMyLocationEnabled = true
            }


            // For dropping a marker at a point on the Map
            val sydney = LatLng(-34.0, 151.0)
            googleMap!!.addMarker(MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"))
            googleMap!!.setOnInfoWindowClickListener {
                Toast.makeText(activity, "Info window clicked",
                        Toast.LENGTH_SHORT).show()
            }
            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        if ((activity as SecondMainActivity).checkLocationPermission()) {

            val location = locationManager!!.getLastKnownLocation(provider)

            //set current location
            if (location == null) {
                locationManager!!.requestLocationUpdates(provider, 0, 0f, this)
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)

            }
        }
    }


    override fun onLocationChanged(location: Location) {
        Log.d("onLocationChanged", "bonobo")
        if ((activity as SecondMainActivity).checkLocationPermission()) {
            lat = location.latitude
            lng = location.longitude

        }

    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    override fun onStop() {
        super.onStop()
        //disable postion update when the Fragment get changed
        locationManager!!.removeUpdates(this)
    }


}

