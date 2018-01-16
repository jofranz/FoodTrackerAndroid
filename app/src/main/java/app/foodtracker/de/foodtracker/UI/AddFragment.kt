package app.foodtracker.de.foodtracker.UI

import android.app.Fragment
import android.content.Intent
import android.graphics.Bitmap
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
import android.net.Uri
import android.os.Environment
import android.support.v4.content.FileProvider
import android.widget.Button
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Model.Meal
import app.foodtracker.de.foodtracker.R
import java.io.File
import java.io.StringReader
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by jfranz on 02.11.2017.
 */
//TODO get lat and lng
class AddFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var foodName: EditText
    private lateinit var addressEdit: EditText
    private lateinit var snippet: EditText
    private lateinit var camera: FloatingActionButton
    private lateinit var inputManager : InputMethodManager
    private lateinit var submit : Button
    private lateinit var imageLink : String
    private lateinit var absulutePath : Uri

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
            imageView!!.setImageBitmap(imageBitmap)
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
        camera!!.setOnClickListener { dispatchTakePictureIntent() }

        submit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var currentTime = GregorianCalendar().timeInMillis
                var meal = Meal(foodName.text.toString(),snippet.text.toString(),
                        "long","ok",1,currentTime,12.3,45.3245,addressEdit.text.toString(),absulutePath.toString())
                var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
                mdb.mealModel().insetMeal(meal)
                activity.fragmentManager.popBackStack()
            }
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_NOT_ALWAYS)
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




    override fun onStop() {
        super.onStop()
        Log.d("onStop","hide keyboard")
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }


}