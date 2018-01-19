package app.foodtracker.de.foodtracker.UI

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.foodtracker.de.foodtracker.*
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Presenter.RecyclerAdapter


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
            //showsMeals(mdb.mealModel().getAllMeal())
            (activity as SecondMainActivity).changeFragment(0)
        })
        return view
    }

    override fun onStart() {
        super.onStart()
        var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
        showsMeals(mdb.mealModel().getAllMeal())
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