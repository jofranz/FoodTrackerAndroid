package app.foodtracker.de.foodtracker.UI

import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.R.id.container
import android.support.v4.app.Fragment
import android.support.v4.app.INotificationSideChannel
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import kotlinx.android.synthetic.main.add_fragment.*
import org.w3c.dom.Text
import kotlinx.android.synthetic.main.detail_view.*
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.util.*


/**
 * Created by normen on 17.01.18.
 */
class DetailFragment : Fragment() {

    private val key = "id"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.detail_view, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as SecondMainActivity).showHome()
        val bundle = this.arguments
        if (bundle != null) {
            val id = bundle.getInt(key)
            var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
            val meal = mdb.mealModel().findMealById(id)
            Log.d("name: ",meal.foodname)
            var imageBitmap = MediaStore.Images.Media.getBitmap(view!!.context.contentResolver, Uri.parse(meal.imagePath))
            detailImage.setImageBitmap(imageBitmap)
            detailNameValue.text = meal.foodname
            snippetValue.text = meal.shortDescription
            longValue.text = meal.longDescription
            effectValue.text = meal.effect.toString()
            effectDescription.text = meal.effectDescription
            detailAddress.text = meal.addressline
            detailTime.text = Date(meal.time).toString()

        }

    }
}

