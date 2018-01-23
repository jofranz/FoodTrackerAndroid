package app.foodtracker.de.foodtracker.UI

import android.app.ActionBar
import android.app.Notification
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
import app.foodtracker.de.foodtracker.Presenter.DividerItemDecoration
import app.foodtracker.de.foodtracker.Presenter.RecyclerAdapter
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import app.foodtracker.de.foodtracker.R.mipmap.ic_launcher
import android.app.PendingIntent
import android.content.Intent
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.content.Context
import app.foodtracker.de.foodtracker.R.mipmap.ic_launcher




/**
 * Created by normen on 12.11.17.
 */
class TableFragment : Fragment() {

    lateinit var adapter: RecyclerAdapter
    var recyclerView: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.table_fragment, container, false)

        recyclerView = view!!.findViewById<RecyclerView>(R.id.recycler)

        val dividerDrawable = ContextCompat.getDrawable(activity.applicationContext, R.drawable.divider)

        recyclerView!!.addItemDecoration(DividerItemDecoration(dividerDrawable))
        val mLayoutManager = LinearLayoutManager(this.activity)



        adapter = RecyclerAdapter(emptyList())
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.adapter = adapter
        val fab1 = view.findViewById<FloatingActionButton>(R.id.fab)
        fab1.setOnClickListener(View.OnClickListener {
            (activity as SecondMainActivity).changeFragment(3) //#todo 2 = add / 3 = edit
/*
            val trans = fragmentManager.beginTransaction()

            trans.replace(R.id.root_frame, AddFragment())

            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            trans.addToBackStack(null)

            trans.commit() */
        })


        fun buildNavigationMenu() {
            val daysMenu = ArrayList<String>()

            daysMenu.add(getString(R.string.dropDownToday))
            daysMenu.add(getString(R.string.dropDownYesterday))
            daysMenu.add(getString(R.string.dropDownWeek))
            daysMenu.add(getString(R.string.dropDown2Weeks))
            daysMenu.add(getString(R.string.dropDownMonth))

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_LIST)

            val arrayAdapter = ArrayAdapter(
                    actionBar?.getThemedContext(), // added ?
                    R.layout.support_simple_spinner_dropdown_item_large,
                    daysMenu)
            arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_list_item)
            actionBar?.setListNavigationCallbacks(arrayAdapter, OnDaySelectedListener()) // added ?
        }
        buildNavigationMenu()
        return view
    }


    override fun onStart() {
        super.onStart()
        (activity as SecondMainActivity).hideHome()
        var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
        showsMeals(mdb.mealModel().getAllMeal())
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


    fun showsMeals(meals: List<app.foodtracker.de.foodtracker.Model.Meal>) {
        recyclerView?.adapter = RecyclerAdapter(meals)
    }

    fun taskAddedAt(position: Int) {
        recyclerView?.adapter?.notifyItemInserted(position)
    }

    fun scrollTo(position: Int) {
        recyclerView?.smoothScrollToPosition(position)
    }
    fun refreshView(){
        var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
        showsMeals(mdb.mealModel().getAllMeal())
    }

    fun notifyUser(){
        //TODO: Notification https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
        
    }
}