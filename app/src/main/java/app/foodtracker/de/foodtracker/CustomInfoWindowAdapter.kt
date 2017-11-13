package app.foodtracker.de.foodtracker

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Gallery
import android.widget.TextView
import android.widget.Toast


import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

import app.foodtracker.de.foodtracker.Model.MarkerRepresentation

/**
 * Created by normen on 17.10.17.
 */

//### work in progress ###

class CustomInfoWindowAdapter internal constructor(activity: Activity) : GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private val mWindow: View
    private var v: View? = null
    private val layoutInflater: LayoutInflater

    init {
        mWindow = activity.layoutInflater.inflate(R.layout.costum_info_window, null)
        layoutInflater = activity.layoutInflater

    }

    override fun getInfoWindow(marker: Marker): View? {


        return null
    }

    override fun getInfoContents(marker: Marker): View {

        v = layoutInflater.inflate(R.layout.costum_info_window, null)

        //get the postion of the marker
        val latLng = marker.position

        val infoMeal = v!!.findViewById<TextView>(R.id.infoMeal)
        val infoTime = v!!.findViewById<TextView>(R.id.infoTime)

        val markerRe = marker.tag as MarkerRepresentation?

        infoMeal.text = markerRe!!.meal
        infoTime.text = markerRe.time?.time.toString()



        return v as View
    }

    override fun onInfoWindowClick(marker: Marker) {
        Log.d("bonobo", "bonobo")
    }
}