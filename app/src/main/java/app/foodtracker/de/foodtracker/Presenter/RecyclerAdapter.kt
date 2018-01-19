package app.foodtracker.de.foodtracker.Presenter
import android.content.Context
import android.media.Image
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.util.*
import app.foodtracker.de.foodtracker.Model.Meal
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import kotlinx.android.synthetic.main.map_fragment.view.*

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

    class ItemsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view = v

        fun bind(meal: Meal) = with(view) {
            this.id = meal.id
            var imageBitmap = MediaStore.Images.Media.getBitmap(view.context.contentResolver,Uri.parse(meal.imagePath))
            thumbnail.setImageBitmap(imageBitmap)
            foodName.text = meal.foodname
            date.text = meal.addressline
        }
        init {
            v.setOnClickListener(this)
        }

        override  fun onClick(v: View?) {
            var mContext = v!!.context
            var main = mContext as SecondMainActivity
            main.changeFragment(1, v!!.id)
        }
        companion object {
            private val key = "CITY"
        }
    }

     data class MealTableRepresentation(val thumbnail: Image?, val foodName: String, val date: GregorianCalendar, var effect: Int? )
}