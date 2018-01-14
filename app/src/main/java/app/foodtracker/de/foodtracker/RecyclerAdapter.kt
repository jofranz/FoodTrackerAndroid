package app.foodtracker.de.foodtracker
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*
import java.util.*
import app.foodtracker.de.foodtracker.Model.Meal

/**
 * Created by normen on 06.11.17.
 */
class RecyclerAdapter(private val mealList: List<Meal>) : RecyclerView.Adapter<RecyclerAdapter.ItemsHolder>() {
    override fun onBindViewHolder(holder: RecyclerAdapter.ItemsHolder, position: Int) {
        holder.bind(mealList[position])
    }

    override fun getItemCount(): Int = mealList.size;

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerAdapter.ItemsHolder {
        val inflatedView = parent!!.inflate(R.layout.recyclerview_item_row, false)
        return ItemsHolder(inflatedView)
    }

    class ItemsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view = v

        fun bind(meal: Meal) = with(view) {
            if(thumbnail != null) {
                //thumbnail.setImageBitmap(meal.thumbnail as Bitmap)
            }
            foodName.text = meal.foodname
            date.text = meal.addressline.toString()
        }
        init {
            v.setOnClickListener(this)
        }

        override  fun onClick(v: View?) {
            Log.d("onClick","${v!!.foodName.text}")
        }
        companion object {
            private val key = "CITY"
        }
    }

     data class MealTableRepresentation(val thumbnail: Image?, val foodName: String, val date: GregorianCalendar, var effect: Int? )
}