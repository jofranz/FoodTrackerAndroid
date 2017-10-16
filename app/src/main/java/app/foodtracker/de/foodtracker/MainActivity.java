package app.foodtracker.de.foodtracker;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ioController = new IOController();
        //init all UI elements
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mNavView = (NavigationView) findViewById(R.id.navView);

        mNavView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.openDrawer,R.string.closeDrawer);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default fragment
        setTitle("blaa");
        Tablefragment tableFragment = new Tablefragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.linear, tableFragment,"table");
        fragmentTransaction.commit();
    }


    @Override
    //dispalys the drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //handle the navigation
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("Test",String.valueOf(item.getItemId()));
        int id = item.getItemId();

        switch (id){
            case R.id.table:
                setTitle("Bundesliga Tabelle");
//                Tablefragment tablefragment = new Tablefragment();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.linear,tablefragment,"table").addToBackStack("table");
//                fragmentTransaction.commit();
                break;
            case R.id.results:
                setTitle("Bundesliga Ergebnisse");
//                ResultsFragment resultsFragment = new ResultsFragment();
//                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction2.replace(R.id.linear,resultsFragment,"result").addToBackStack("result");
//                fragmentTransaction2.commit();
                break;
            case R.id.settings:
                setTitle("Einstellungen");
//                SettingsFragment settingsFragment = new SettingsFragment();
//                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction3.replace(R.id.linear,settingsFragment,"setting").addToBackStack("setting");
//                fragmentTransaction3.commit();
                break;
            case R.id.info:
                setTitle("Hilfe");
//                InfoFragment infoFragment = new InfoFragment();
//                FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction4.replace(R.id.linear,infoFragment,"info").addToBackStack("info");
//                fragmentTransaction4.commit();
                break;
            default:

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    //pop one fragment from the stack
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }



    ////////////////////////////////////////////////////////////////////////


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asy
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_permission)
                        .setMessage(R.string.text_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    //TODO
                    // permission denied, boo! Disable the


                }
                return;
            }

        }
    }
}
