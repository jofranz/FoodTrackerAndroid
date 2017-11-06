package app.foodtracker.de.foodtracker

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import app.foodtracker.de.foodtracker.Model.Meal
import com.google.android.gms.maps.model.Marker
import oldandBusted.MainTableFragment

class SecondMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var adapter: RecyclerAdapter

    private var mapFragment: MapFragment? = null
    private var mainTableFragment: MainTableFragment? = null
    private var addFragment: AddFragment? = null
    private val meals: Array<Meal>? = null
    private val markers: Array<Marker>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

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


    // set up navigation drawer
    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        val manager = fragmentManager

        when (id) {
        // switch case for left menu

            R.id.nav_main_table // for main table fragment
            -> if (!manager.popBackStackImmediate(MainTableFragment::class.java.name, 0)) {
                val trans = manager.beginTransaction()
                trans.addToBackStack(MainTableFragment::class.java.name)
                if (mainTableFragment == null) {
                    mainTableFragment = MainTableFragment()
                }
                trans.replace(R.id.main_content_activity, mainTableFragment)
                trans.commit()
            }

            R.id.nav_edit // for entry fragment
            -> if (!manager.popBackStackImmediate(AddFragment::class.java.name, 0)) {
                val trans = manager.beginTransaction()
                trans.addToBackStack(AddFragment::class.java.name)
                if (addFragment == null) {
                    addFragment = AddFragment()
                }
                trans.replace(R.id.main_content_activity, addFragment)
                trans.commit()
            }

            R.id.nav_map // for map fragment
            -> if (!manager.popBackStackImmediate(MapFragment::class.java.name, 0)) {
                val trans = manager.beginTransaction()
                trans.addToBackStack(MapFragment::class.java.name)
                if (mapFragment == null) {
                    mapFragment = MapFragment()
                }
                trans.replace(R.id.main_content_activity, mapFragment)
                trans.commit()
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    } // navigation drawer end


    // set up permissions
    val MY_PERMISSIONS_REQUEST_LOCATION = 99

    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asy
                AlertDialog.Builder(this)
                        .setTitle(R.string.title_permission)
                        .setMessage(R.string.text_permission)
                        .setPositiveButton(R.string.ok) { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this@SecondMainActivity,
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        }
                        .create()
                        .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    //TODO
                    // permission denied, boo! Disable the

                }
                return
            }
        }
    } // permissions end
    inner class Item(val id: Long, val title: String)
}


