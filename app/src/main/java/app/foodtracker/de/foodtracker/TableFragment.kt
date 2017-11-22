package app.foodtracker.de.foodtracker

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*
import kotlinx.android.synthetic.main.table_fragment.*

/**
 * Created by normen on 12.11.17.
 */
class TableFragment : Fragment() {

    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.table_fragment, container, false)

        val recycleView = view!!.findViewById<RecyclerView>(R.id.recycler)
        val mLayoutManager = LinearLayoutManager(this.activity)



        val burger : RecyclerAdapter.MealTableRepresentation = RecyclerAdapter.MealTableRepresentation(null,"Burger", GregorianCalendar(),null)
        val fish : RecyclerAdapter.MealTableRepresentation = RecyclerAdapter.MealTableRepresentation(null,"Fish", GregorianCalendar(),null)
        val soda: RecyclerAdapter.MealTableRepresentation = RecyclerAdapter.MealTableRepresentation(null,"Soda", GregorianCalendar(),null)



        val mealList  = arrayListOf<RecyclerAdapter.MealTableRepresentation>()
        mealList.add(burger)
        mealList.add(fish)
        mealList.add(soda)

        adapter = RecyclerAdapter(mealList)
        recycleView.layoutManager = mLayoutManager
        recycleView.adapter = adapter
        val fab1 = view!!.findViewById<FloatingActionButton>(R.id.fab)
        fab1.setOnClickListener(View.OnClickListener {
            (activity as SecondMainActivity).changeFragment(0)
        })

        return view
    }



}