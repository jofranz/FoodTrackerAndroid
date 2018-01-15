package app.foodtracker.de.foodtracker.UI

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.foodtracker.de.foodtracker.*
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Model.Meal


/**
 * Created by normen on 12.11.17.
 */
class TableFragment : Fragment() {


    private lateinit var adapter: RecyclerAdapter
    var recyclerView: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.table_fragment, container, false)

        recyclerView = view!!.findViewById<RecyclerView>(R.id.recycler)
        val mLayoutManager = LinearLayoutManager(this.activity)


        adapter = RecyclerAdapter(emptyList())
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter
        val fab1 = view.findViewById<FloatingActionButton>(R.id.fab)
        fab1.setOnClickListener(View.OnClickListener {
            var mdb = AppDatabase.getInMemoryDatabase(this.context)
            val meal1 = Meal("Burger","short","long","effect",1,124356,12.1,45.7,"Schloßalle 3")
            mdb.mealModel().insetMeal(meal1)
            showsMeals(mdb.mealModel().getAllMeal())
            //(activity as SecondMainActivity).changeFragment(0)
        })
        return view
    }

    fun showsMeals(meals: List<app.foodtracker.de.foodtracker.Model.Meal>) {
        recyclerView?.adapter = RecyclerAdapter(meals)
    }

    fun taskAddedAt(position: Int) {
        recyclerView?.adapter?.notifyItemInserted(position)
    }

    fun scrollTo(position: Int) {
        recyclerView?.smoothScrollToPosition(position)
    }
}