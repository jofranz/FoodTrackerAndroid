package app.foodtracker.de.foodtracker.UI

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Model.Meal
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.add_constraint.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity(), View.OnClickListener, LocationListener {

    private var lat = 0.toDouble()
    private var lng = 0.toDouble()
    private lateinit var  locationManager: LocationManager
    private var absulutePath : Uri? = null
    private lateinit var provider: String
    private lateinit var inputManager : InputMethodManager
    private var currentTime: Long = 0


    override fun onClick(v: View?) {
        val name = foodName.text.toString()
        val snippet = snippet.text.toString()
        val addres = addressEdit.text.toString()
        if (absulutePath == null){
            absulutePath = Uri.parse("android.resource://app.foodtracker.de.foodtracker/" + R.drawable.ic_add_a_photo_black_48dp)
        }
        val mdb = AppDatabase.getInMemoryDatabase(applicationContext)
        val meal = Meal(name,snippet,"","",2,currentTime,lat,lng,addres,absulutePath!!.toString())
        mdb.mealModel().insetMeal(meal)
        val intent = Intent(this, SecondMainActivity::class.java).apply {
            putExtra("extraAdd", "valueAddt")
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        //setSupportActionBar(toolbar)
        val currentDate = GregorianCalendar()
        val currentTime  = currentDate.timeInMillis
        infoTime.setText(currentDate.time.toString())
        fab.setOnClickListener { view -> dispatchTakePictureIntent() }
        submit.setOnClickListener(this)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile : File? = null
            photoFile = createImageFile();
            Log.d("PathToImage: ", photoFile.toString())
            if (photoFile != null){
                val uri = FileProvider.getUriForFile(applicationContext,"com.foodtracker.android.fileprovider",photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                absulutePath = uri
                print(absulutePath)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    companion object {

        internal val REQUEST_IMAGE_CAPTURE = 1
    }

    fun createImageFile() : File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "JPEG_" + timeStamp + "_";
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                fileName,
                ".jpg",
                storageDir
        )
        return image
    }

    override fun onResume() {
        super.onResume()
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_NOT_ALWAYS)


        val crit = Criteria()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        provider = locationManager!!.getBestProvider(crit, true)

        if (checkLocationPermission()) {

            val location = locationManager!!.getLastKnownLocation(provider)

            //set current location
            if (location != null) {
                locationManager!!.requestLocationUpdates(provider, 0, 0f, this)
                locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)

            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        if (checkLocationPermission()) {
            if(location?.latitude != null)
                lat = location.latitude
            lng = location!!.longitude
        }
        //experimental feature for decoding coordinate to address
        val geoCoder = Geocoder(baseContext)
        var matches: List<Address>? = null
        try {
            matches = geoCoder.getFromLocation(lat, lng, 1)

        } catch (e: IOException) {
            e.printStackTrace()
        }

        val bestMatch = if (matches!!.isEmpty()) null else matches[0]
        addressEdit.text = bestMatch?.getAddressLine(0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //val extras = data.extras
            val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver,absulutePath)
            mealImageView?.let {  mealImageView.setImageBitmap(imageBitmap)}
        }
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(this)
        val imm = applicationContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)

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


    //Permission
    // set up permissions
    val MY_PERMISSIONS_REQUEST_LOCATION = 99

    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asy
                AlertDialog.Builder(this)
                        .setTitle(R.string.title_permission)
                        .setMessage(R.string.text_permission)
                        .setPositiveButton(R.string.ok) { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this@AddActivity,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        }
                        .create()
                        .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    // permission denied, boo! Disable the
                }
                return
            }
        }
    } // permissions end
}
