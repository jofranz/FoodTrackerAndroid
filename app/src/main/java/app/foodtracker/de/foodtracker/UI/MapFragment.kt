package app.foodtracker.de.foodtracker.UI

import android.content.Context
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Model.MarkerRepresentation
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.io.IOException
import java.util.*

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
                googleMap!!.addMarker(MarkerOptions().position(currentLocation!!).title("You are here")
                        .snippet("This is a snippet:" + currentLocation!!.toString())).tag = markerRepresentation
                //set own CustomInfoWindow
                //googleMap!!.setInfoWindowAdapter(CustomInfoWindowAdapter(activity))
                googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))

            }
        }
        mMapView.getMapAsync { mMap ->
            mapAdd!!.isEnabled = true
            googleMap = mMap

            // For showing a move to my location button
            if ((activity as SecondMainActivity).checkLocationPermission()) {
                googleMap!!.isMyLocationEnabled = true
            }
            googleMap!!.setOnInfoWindowClickListener {
                Toast.makeText(activity, "Info window clicked",
                        Toast.LENGTH_SHORT).show()
            }
            var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
            val mealList = mdb.mealModel().getAllMeal()

            for (item in mealList){

                var imageBitmap = MediaStore.Images.Media.getBitmap(view!!.context.contentResolver, Uri.parse(item.imagePath))
                val time: GregorianCalendar = GregorianCalendar()
                time.timeInMillis = item.time
                val markerRepresentation = MarkerRepresentation(imageBitmap,time,item.foodname)

                if(googleMap != null) {
                    googleMap!!.addMarker(MarkerOptions().position(LatLng(item.lat,item.lng)).title(item.foodname)
                            .snippet(item.shortDescription))?.tag = markerRepresentation
                }
            }
            // For zooming automatically to the location of the marker
            // val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
            // googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }

        try {
            MapsInitializer.initialize(activity.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
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

