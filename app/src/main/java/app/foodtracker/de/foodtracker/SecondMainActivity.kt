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
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Presenter.CSVExportController
import app.foodtracker.de.foodtracker.Presenter.PageAdapter
import app.foodtracker.de.foodtracker.UI.*
import android.app.Activity




class SecondMainActivity : AppCompatActivity(){

    private var mapFragment: MapFragment? = null
    private var tableFragment: TableFragment? = null
    private lateinit var detailFragment: DetailFragment
    private val markers: Array<Marker>? = null
    private val manager = supportFragmentManager
    private val key = "id"
    var statOfTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        verifyStoragePermissions(this)
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
                statOfTab = tab.position
                if (tab.position == 1){
                    val myFragment: MapFragment = supportFragmentManager.fragments.get(1) as MapFragment

                    if (myFragment!!.isVisible()) {
                        myFragment.onResume()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true);

    }




        private fun runsAtLeastOnAndroidNougat(): Boolean {
            return Build.VERSION.SDK_INT > Build.VERSION_CODES.M
        }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> supportFragmentManager.popBackStack()
            R.id.action_csvexport -> {verifyStoragePermissions(this)
                exportCSV()}

        }
        return true
    }

    fun exportCSV(){
        val csvExport = CSVExportController()
        csvExport.execute(applicationContext)
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
            -> {

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
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("id",id.toString())
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

    //Permission Write
    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }

}

