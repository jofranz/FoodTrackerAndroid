package app.foodtracker.de.foodtracker.Presenter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import app.foodtracker.de.foodtracker.UI.MapFragment
import app.foodtracker.de.foodtracker.UI.RootFragment
import app.foodtracker.de.foodtracker.UI.TableFragment

class PageAdapter(fragmentManager: FragmentManager,numberOfTabs: Int): FragmentStatePagerAdapter(fragmentManager){

     private var mNumOfTabs = 0


    init {
        mNumOfTabs = numberOfTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return RootFragment()
            }
            1 -> {
                return MapFragment()
            }
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }

}

