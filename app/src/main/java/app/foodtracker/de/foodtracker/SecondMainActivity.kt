package app.foodtracker.de.foodtracker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.main_view.*

class SecondMainActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hof : RecyclerAdapter.City = RecyclerAdapter.City(cityName = "Hof", size = 50000)
        val berlin : RecyclerAdapter.City = RecyclerAdapter.City(cityName = "Berlin", size = 3000000)
        val bonn : RecyclerAdapter.City = RecyclerAdapter.City(cityName = "Bonn", size = 700000)




        val cities  = arrayListOf<RecyclerAdapter.City>()
        cities.add(berlin)
        cities.add(hof)
        cities.add(bonn)


//        setContentView(R.layout.activity_main)
//        recycler.layoutManager = LinearLayoutManager(this,1,false)
//        recycler.adapter = RecyclerAdapter(cities)


    }
    inner class Item(val id: Long, val title: String)
}


