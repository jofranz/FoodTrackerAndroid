package app.foodtracker.de.foodtracker.UI

import android.app.ActionBar
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
            //(activity as SecondMainActivity).changeFragment(0)
            val trans = fragmentManager.beginTransaction()

            trans.replace(R.id.root_frame, AddFragment())

            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit()

        })


        fun buildNavigationMenu() {
            val days_menu = ArrayList<String>()

            days_menu.add("Heute")
            days_menu.add("Gestern")
            days_menu.add("Woche")
            days_menu.add("2 Wochen")
            days_menu.add("Monat")

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_LIST)

            val arrayAdapter = ArrayAdapter(
                    actionBar?.getThemedContext(), // added ?
                    R.layout.support_simple_spinner_dropdown_item_large,
                    days_menu)
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_list_item)
            actionBar?.setListNavigationCallbacks(arrayAdapter, OnDaySelectedListener()) // added ?
        }

        buildNavigationMenu()
        return view
    }



    // must be moved to kotlin style listener method ^
    private inner class OnDaySelectedListener : android.support.v7.app.ActionBar.OnNavigationListener {




        private var isSynthetic = true

        override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {

            if (runsAtLeastOnAndroidNougat() && isSynthetic) {
                isSynthetic = false
                return true
            }
            if (itemPosition < 5) {
                Log.d("jfjf", "click itemId: " + itemId + " item position:" + itemPosition)
                return true
            }
            return false
        }

        private fun runsAtLeastOnAndroidNougat(): Boolean {
            return Build.VERSION.SDK_INT > Build.VERSION_CODES.M
        }

    }


    override fun onStart() {
        super.onStart()
        (activity as SecondMainActivity).hideHome()
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