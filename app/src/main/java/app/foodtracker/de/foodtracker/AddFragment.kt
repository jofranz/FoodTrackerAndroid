package app.foodtracker.de.foodtracker

import android.app.Fragment
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android 

import android.app.Activity.RESULT_OK


/**
 * Created by jfranz on 02.11.2017.
 */

class AddFragment : Fragment() {

    private var imageView: ImageView? = null
    private var foodName: EditText? = null
    private var addressEdit: EditText? = null
    private var snippet: EditText? = null
    private var camera: FloatingActionButton? = null

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data.extras
            val imageBitmap = extras!!.get("data") as Bitmap
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


        camera!!.setOnClickListener { dispatchTakePictureIntent() }
        return rootView
    }

    companion object {

        internal val REQUEST_IMAGE_CAPTURE = 1
    }


}