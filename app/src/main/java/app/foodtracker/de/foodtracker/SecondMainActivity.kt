package app.foodtracker.de.foodtracker

import android.Manifest
import android.app.Activity
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
import app.foodtracker.de.foodtracker.UI.AddFragment
import app.foodtracker.de.foodtracker.UI.MapFragment
import app.foodtracker.de.foodtracker.UI.TableFragment
import com.google.android.gms.maps.model.Marker
import android.arch.persistence.room.Room
import android.location.LocationManager
import app.foodtracker.de.foodtracker.UI.DetailFragment
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.ViewParent
import app.foodtracker.de.foodtracker.Presenter.PageAdapter
import kotlinx.android.synthetic.main.activity_main.*


class SecondMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mapFragment: MapFragment? = null
    private var addFragment: AddFragment? = null
    private var tableFragment: TableFragment? = null
    private lateinit var detailFragment: DetailFragment
    private val markers: Array<Marker>? = null
    private val manager = supportFragmentManager
    private val key = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        //navigationView.setNavigationItemSelectedListener(this)
        val tabLayout = findViewById<View>(R.id.tab_layout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("LIST"))
        tabLayout.addTab(tabLayout.newTab().setText("MAP"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val viewPager = findViewById<ViewPager>(R.id.pager)
        val pageViewAdapter = PageAdapter(supportFragmentManager, 2)

        viewPager.adapter = pageViewAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


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

        /*
        when (id) {
        // when(id) for left menu

            R.id.nav_main_table // for main table fragment
            -> if (!manager.popBackStackImmediate(TableFragment::class.java.name, 0)) {
                val trans = manager.beginTransaction()
                trans.addToBackStack(TableFragment::class.java.name)
                if (tableFragment == null) {
                    tableFragment = TableFragment()
                }
                trans.replace(R.id.main_content_activity, tableFragment)
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
                mapFragment = MapFragment()
                trans.replace(R.id.main_content_activity, mapFragment)
                trans.commit()
            }
            */


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


    // identifer: AddFragemnt -> 0,   add more
    public fun changeFragment(identifer: Int, id: Int = 0) {

        when (identifer) {
            0
            -> if (!manager.popBackStackImmediate(AddFragment::class.java.name, 0)) {
                val trans = manager.beginTransaction()
                trans.addToBackStack(AddFragment::class.java.name)
                addFragment = AddFragment()
                trans.replace(R.id.main_layout, addFragment)
                trans.commit()
                getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = "ADD A MEAL"
            }
            1
            -> if (!manager.popBackStackImmediate(DetailFragment::class.java.name, 0)) {
                val trans = manager.beginTransaction()
                trans.addToBackStack(DetailFragment::class.java.name)
                detailFragment = DetailFragment()
                val bundle = Bundle()
                bundle.putInt("id", id)
                detailFragment.arguments = bundle
                trans.replace(R.id.main_layout, detailFragment)
                trans.commit()
                getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = "Detail's"
            }

        }

    }


}

