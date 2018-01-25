package app.foodtracker.de.foodtracker.Presenter
import android.content.Context
import android.content.DialogInterface
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import app.foodtracker.de.foodtracker.Model.AppDatabase
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.util.*
import app.foodtracker.de.foodtracker.Model.Meal
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import kotlinx.android.synthetic.main.map_fragment.view.*
import android.R.string.cancel
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FilenameFilter
import java.lang.System.out
import java.net.URI


/**
 * Created by normen on 06.11.17.
 */
class RecyclerAdapter(private val mealList: List<Meal>) : RecyclerView.Adapter<RecyclerAdapter.ItemsHolder>() {
    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        holder.bind(mealList[position])
    }

    override fun getItemCount(): Int = mealList.size;

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemsHolder {
        val inflatedView = parent!!.inflate(R.layout.recyclerview_item_row, false)
        return ItemsHolder(inflatedView)
    }

    class ItemsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener, View.OnLongClickListener{

        private var view = v

        fun bind(meal: Meal) = with(view) {
            this.id = meal.id
            val imageFile = File(meal.imagePath)
            val imageBitmap = MediaStore.Images.Media.getBitmap(view.context.contentResolver, Uri.parse(meal.imagePath))
            thumbnail.setImageBitmap(imageBitmap)
            foodName.text = meal.foodname
            var time1 = GregorianCalendar()
            time1.timeInMillis = meal.time
            date.text = time1.time.toString()

            when (meal.effect) {
                0 -> {
                    effect.text = "really bad"
                    effect.setTextColor(Color.parseColor("#B71C1C"))
                }

                1 -> {
                    effect.text = "bad"
                    effect.setTextColor(Color.parseColor("#F44336"))

                }

                2 -> {
                    effect.text = "neutral"
                    effect.setTextColor(Color.parseColor("#212121"))

                }

                3 -> {
                    effect.text = "good"
                    effect.setTextColor(Color.parseColor("#76FF03"))
                }

                4 -> {
                    effect.text = "really good"
                    effect.setTextColor(Color.parseColor("#1B5E20"))
                }


            }
            //effect.text = meal.effect.toString()
        }
        init {
            v.setOnClickListener(this)
            v.setOnLongClickListener(this)
        }

        override  fun onClick(v: View?) {
            val mContext = v!!.context
            val main = mContext as SecondMainActivity
            main.changeToDetail(v.id)
        }

        override fun onLongClick(v: View?): Boolean {

            val mContext = v!!.context
            val main = mContext as SecondMainActivity
            main.showAlertDialog(v.id)
            return true
        }
        companion object {
            private val key = "CITY"
        }
    }

     data class MealTableRepresentation(val thumbnail: Image?, val foodName: String, val date: GregorianCalendar, var effect: Int? )
}