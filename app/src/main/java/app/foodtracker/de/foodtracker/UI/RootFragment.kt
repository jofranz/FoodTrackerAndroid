package app.foodtracker.de.foodtracker.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.foodtracker.de.foodtracker.R


/**
 * Created by normen on 20.01.18.
 */
class RootFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /* Inflate the layout for this fragment */
        val view = inflater?.inflate(R.layout.root_layout, container, false)

        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.root_frame, TableFragment())

        transaction.commit()

        return view
    }

}