package app.foodtracker.de.foodtracker.UI

import android.content.Context
import android.content.Intent
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
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by normen on 06.11.17.
 */
class MapFragment : Fragment(), LocationListener, GoogleMap.OnInfoWindowClickListener {
    override fun onInfoWindowClick(p0: Marker?) {
        val lat = p0!!.position!!.latitude
        val lng = p0.position!!.longitude
        var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)

        val meal = mdb.mealModel().findMealByLatLng(lat,lng)
        val intent = Intent(activity, EditActivity::class.java)
        intent.putExtra("id",meal.id.toString())
        startActivity(intent)

    }

    internal lateinit var mMapView: MapView
    private var googleMap: GoogleMap? = null
    private var mapAdd: Button? = null
    private var locationManager: LocationManager? = null
    private var provider: String? = null
    private var currentLocation: LatLng? = null
    private var lat: Double = 0.toDouble()
    private var lng: Double = 0.toDouble()
    private var markers: ArrayList<Marker> = ArrayList<Marker>()

    //TODO: Implement CostumInfoWindow 
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.map_fragment, container, false)

        mMapView = rootView.findViewById<View>(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)


        mMapView.onResume() // needed to get the map to display immediately
        val crit = Criteria()
        locationManager = (activity as SecondMainActivity).getSystemService(Context.LOCATION_SERVICE) as LocationManager
        provider = locationManager!!.getBestProvider(crit, true)

        mMapView.getMapAsync { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            if ((activity as SecondMainActivity).checkLocationPermission()) {
                googleMap!!.isMyLocationEnabled = true
            }
            googleMap!!.setOnInfoWindowClickListener(this)
            var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
            val mealList = mdb.mealModel().getAllMeal()


            for (item in mealList) {

                var imageBitmap = MediaStore.Images.Media.getBitmap(view!!.context.contentResolver, Uri.parse(item.imagePath))
                val time: GregorianCalendar = GregorianCalendar()
                time.timeInMillis = item.time
                val markerRepresentation = MarkerRepresentation(imageBitmap, time, item.foodname)

                if (googleMap != null) {
                    var markerOpt = MarkerOptions().position(LatLng(item.lat, item.lng)).title(item.foodname)
                            .snippet(item.shortDescription)
                    var marker1 = googleMap!!.addMarker(markerOpt)
                    marker1.tag = markerRepresentation

                    markers.add(marker1)
                    googleMap!!.addMarker(markerOpt)?.tag = markerRepresentation
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
        Log.d("onResume", "gets Called")
        (activity as SecondMainActivity).hideHome()
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

    fun sortDataMarker(itemPosition: Int) {
        var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
        when (itemPosition) {
            0 -> {
                val date = GregorianCalendar()

                var tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                tmp = tmp - 1
                var yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), tmp)
                Log.d("Bonobo", yesterday.time.toString())
                for (item in markers) {
                    Log.d("Knödel", item.tag.toString())
                }
            }
            1 -> {

                val date = GregorianCalendar()

                val tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                val end = tmp - 1
                val start = tmp - 2
                val yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), tmp)
                Log.d("Bonobo", yesterday.time.toString())
                val startDate = GregorianCalendar(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), start)
                val endDate = GregorianCalendar(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), end)
                val meals = mdb.mealModel().findAllMealsAfter(startDate.timeInMillis, endDate.timeInMillis)

            }
            2 -> {
                val date = GregorianCalendar()

                var tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                tmp = tmp - 7
                val yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), tmp)
                Log.d("Bonobo", yesterday.time.toString())
                val meals = mdb.mealModel().findAllMealsAfter(yesterday.timeInMillis)
            }
            3 -> {
                val date = GregorianCalendar()

                var tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                tmp = tmp - 14
                val yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR), date.get(GregorianCalendar.MONTH), tmp)
                Log.d("Bonobo", yesterday.time.toString())
                val meals = mdb.mealModel().findAllMealsAfter(yesterday.timeInMillis)
            }
            4 -> {

                val meals = mdb.mealModel().getAllMeal()
            }
        }


    }
}

