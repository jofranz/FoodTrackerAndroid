package app.foodtracker.de.foodtracker.UI

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.app.Activity.RESULT_OK
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.app.Activity
import android.graphics.Camera
import android.location.*
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import android.widget.Button
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Model.Meal
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import java.io.File
import java.io.IOException
import java.security.Provider
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by jfranz on 02.11.2017.
 */
//TODO get lat and lng
//TODO Settings screen
class AddFragment : Fragment(), LocationListener{

    private lateinit var imageView: ImageView
    private lateinit var foodName: EditText
    private lateinit var addressEdit: EditText
    private lateinit var snippet: EditText
    private lateinit var camera: FloatingActionButton
    private lateinit var inputManager : InputMethodManager
    private lateinit var submit : Button
    private var lat = 0.toDouble()
    private var lng = 0.toDouble()
    private lateinit var  locationManager: LocationManager
    private var absulutePath : Uri? = null
    private lateinit var provider: String


    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            var photoFile : File? = null
            photoFile = createImageFile();
            Log.d("PathToImage: ", photoFile.toString())
            if (photoFile != null){
                val uri = FileProvider.getUriForFile(activity.applicationContext,"com.foodtracker.android.fileprovider",photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                absulutePath = uri
                print(absulutePath)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //val extras = data.extras
            val imageBitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver,absulutePath)
            imageView?.let {  imageView.setImageBitmap(imageBitmap)}
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.add_fragment, container, false)



        imageView = rootView.findViewById(R.id.picturePreview)
        addressEdit = rootView.findViewById(R.id.addressEdit)
        foodName = rootView.findViewById(R.id.foodName)
        snippet = rootView.findViewById(R.id.snippet)
        camera = rootView.findViewById(R.id.camera)
        submit = rootView.findViewById(R.id.submit)
        camera?.setOnClickListener { dispatchTakePictureIntent() }

        submit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var currentTime = GregorianCalendar().timeInMillis
                if (absulutePath == null){
                    absulutePath = Uri.parse("android.resource://app.foodtracker.de.foodtracker/" + R.drawable.ic_add_a_photo_black_48dp)
                }
                //experimental feature for decoding coordinate to address
                val geoCoder = Geocoder(activity.baseContext)
                var matches: List<Address>? = null
                try {
                    matches = geoCoder.getFromLocation(lat, lng, 1)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val bestMatch = if (matches!!.isEmpty()) null else matches[0]
                var meal = Meal(foodName.text.toString(),snippet.text.toString(),
                        "long","ok",1,currentTime,lat,lng,bestMatch!!.getAddressLine(0).toString(),absulutePath.toString())
                var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
                mdb.mealModel().insetMeal(meal)
                Log.d("Meal: ",meal.toString())
                fragmentManager.popBackStack()

            }
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_NOT_ALWAYS)


        //### Location ###
        val crit = Criteria()
        locationManager = (activity as SecondMainActivity).getSystemService(Context.LOCATION_SERVICE) as LocationManager
        provider = locationManager!!.getBestProvider(crit, true)

        if ((activity as SecondMainActivity).checkLocationPermission()) {

            val location = locationManager!!.getLastKnownLocation(provider)

            //set current location
            if (location != null) {
                locationManager!!.requestLocationUpdates(provider, 0, 0f, this)
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)

            }
        }
    }

    companion object {

        internal val REQUEST_IMAGE_CAPTURE = 1
    }

    fun createImageFile() : File{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "JPEG_" + timeStamp + "_";
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                fileName,
                ".jpg",
                storageDir
        )
        return image
    }

    override fun onLocationChanged(location: Location?) {
        if ((activity as SecondMainActivity).checkLocationPermission()) {
            if(location?.latitude != null)
            lat = location.latitude
            lng = location!!.longitude
        }
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(this)
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        //To change body of created functions use File | Settings | File Templates.
    }

}