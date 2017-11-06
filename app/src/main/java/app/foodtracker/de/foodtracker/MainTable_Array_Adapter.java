package app.foodtracker.de.foodtracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Johannes Franz on 05.11.2017.
 */

public class MainTable_Array_Adapter extends ArrayAdapter {


    //default constructor
    public MainTable_Array_Adapter(Context aContext, ArrayList<String> stringTestArrayList) { // #todo change to model array
        super(aContext, R.layout.main_table_custom_row, stringTestArrayList);
    }


    @Override
    public View getView(int aPosition, View aThisView, ViewGroup aParent) {
        LayoutInflater mThisInflator = LayoutInflater.from(getContext());
        View mThisView = mThisInflator.inflate(R.layout.main_table_custom_row, aParent, false);

        // link layout elements to objects and provide content
        TextView mFoodText = mThisView.findViewById(R.id.foodText);
        mFoodText.setText("hardcoded string");




        TextView mEffectNr = mThisView.findViewById(R.id.effect);
        mEffectNr.setText("" + aPosition);


        // give a color to every second row
        if( aPosition % 2 == 1 ) {
            mThisView.setBackgroundColor(Color.LTGRAY);
        }

        return mThisView;

    }


}
