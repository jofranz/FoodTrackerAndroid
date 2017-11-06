package app.foodtracker.de.foodtracker
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview_item_row.view.*

/**
 * Created by normen on 06.11.17.
 */
class RecyclerAdapter(private val cities: ArrayList<City>) : RecyclerView.Adapter<RecyclerAdapter.ItemsHolder>() {
    override fun onBindViewHolder(holder: RecyclerAdapter.ItemsHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int = cities.size;

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerAdapter.ItemsHolder {
        val inflatedView = parent!!.inflate(R.layout.recyclerview_item_row, false)
        return ItemsHolder(inflatedView)
    }

    class ItemsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var view = v
        private var photo: City? = null

        fun bind(city: City) = with(view){
            cityName.text = city.cityName
            size.text = city.size.toString()
        }

        init {
            v.setOnClickListener(this)
        }

        override  fun onClick(v: View?) {
            Log.d("onClick","${v!!.cityName.text}")
        }
        companion object {
            private val key = "CITY"
        }
    }

     data class City(val cityName: String,val size: Int )
}