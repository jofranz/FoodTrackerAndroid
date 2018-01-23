package app.foodtracker.de.foodtracker

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.model.Marker
import android.content.Intent
import android.content.DialogInterface
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Presenter.PageAdapter
import app.foodtracker.de.foodtracker.UI.*


class SecondMainActivity : AppCompatActivity(){

    private var mapFragment: MapFragment? = null
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> supportFragmentManager.popBackStack()

        }
        return true
    }



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
                    // permission denied, boo! Disable the
                }
                return
            }
        }
    } // permissions end


    // identifer: AddFragemnt -> 0,   add more
    public fun changeView(identifer: Int, id: Int = 0) {

        when (identifer) {
            0
            -> {}
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

            2
            -> if (true) {
                val intent = Intent(this, AddActivity::class.java).apply {
                    putExtra("extraAdd", "valueAddt")
                }
                startActivity(intent)
            }


            3
            -> if (true) {
                val intent = Intent(this, EditActivity::class.java).apply {
                    putExtra("extraEdit", "valueEdit")
                }
                startActivity(intent)
            }

        }

    }
    fun showHome(){
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
    }


    fun hideHome(){
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(false);
    }

    fun changeToDetail(id: Int){
        val trans = supportFragmentManager.beginTransaction()
        val intent = Intent(this, EditActivity::class.java).apply {
            putExtra("id", id)
        }
        startActivity(intent)
    }
    fun deleteItem(id: Int) : Boolean{
        val mdb = AppDatabase.getInMemoryDatabase(applicationContext)
        val meal = mdb.mealModel().findMealById(id)
        mdb.mealModel().deleteMeal(meal)

        val tableFragment: TableFragment = supportFragmentManager.findFragmentByTag("table") as TableFragment
        tableFragment.refreshView()
        return true
    }
    fun showAlertDialog(itemId: Int){
        val alertDialogBuilder = android.app.AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.deleteEntry)
        alertDialogBuilder.setMessage(R.string.deleteMessage)
        alertDialogBuilder.setPositiveButton(R.string.ok, DialogInterface.OnClickListener{
            dialog, id -> deleteItem(itemId)
        })
        alertDialogBuilder.setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, id ->
            // cancel
        })
        alertDialogBuilder.show()

    }

}

