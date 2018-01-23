package app.foodtracker.de.foodtracker.UI

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import app.foodtracker.de.foodtracker.*
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Presenter.DividerItemDecoration
import app.foodtracker.de.foodtracker.Presenter.RecyclerAdapter
import android.support.v4.content.ContextCompat
import java.time.Year
import java.util.*


/**
 * Created by normen on 12.11.17.
 */
class TableFragment : Fragment() {

    lateinit var adapter: RecyclerAdapter
    var recyclerView: RecyclerView? = null
    var currentStat = 0


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
            (activity as SecondMainActivity).changeView(2) //#todo 2 = add / 3 = edit
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
            //TODO Make Asnyc
            if (runsAtLeastOnAndroidNougat() && isSynthetic) {
                isSynthetic = false
                return true
            }
            if (itemPosition < 5) {
                var mdb = AppDatabase.getInMemoryDatabase(activity.applicationContext)
                when(itemPosition){
                    0 ->{
                        val date = GregorianCalendar()

                        var tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                        tmp = tmp - 1
                        var yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),tmp)
                        Log.d("Bonobo",yesterday.time.toString())
                        var meals = mdb.mealModel().findAllMealsAfter(yesterday.timeInMillis)
                        showsMeals(meals)
                    }
                    1 -> {

                        val date = GregorianCalendar()

                        val tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                        val end = tmp - 1
                        val start = tmp - 2
                        val yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),tmp)
                        Log.d("Bonobo",yesterday.time.toString())
                        val startDate = GregorianCalendar(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),start)
                        val endDate = GregorianCalendar(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),end)
                        val meals = mdb.mealModel().findAllMealsAfter(startDate.timeInMillis,endDate.timeInMillis)
                        showsMeals(meals)

                    }
                    2 ->{
                        val date = GregorianCalendar()

                        var tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                        tmp = tmp - 7
                        val yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),tmp)
                        Log.d("Bonobo",yesterday.time.toString())
                        val meals = mdb.mealModel().findAllMealsAfter(yesterday.timeInMillis)
                        showsMeals(meals)
                    }
                    3->{
                        val date = GregorianCalendar()

                        var tmp = date.get(GregorianCalendar.DAY_OF_MONTH)
                        tmp = tmp - 14
                        val yesterday = GregorianCalendar(date.get(GregorianCalendar.YEAR),date.get(GregorianCalendar.MONTH),tmp)
                        Log.d("Bonobo",yesterday.time.toString())
                        val meals = mdb.mealModel().findAllMealsAfter(yesterday.timeInMillis)
                        showsMeals(meals)
                    }
                    4->{

                        val meals = mdb.mealModel().getAllMeal()
                        showsMeals(meals)
                    }
                }
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