package app.foodtracker.de.foodtracker;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Johannes Franz on 02.11.2017.
 */

public class MainTableFragment extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.main_table, container, false);
        listView = mView.findViewById(R.id.mainTableListView);


        ListAdapter mListAdapter = new MainTable_Array_Adapter(getActivity(), getDummy(14));
        listView.setAdapter(mListAdapter);

        return mView;
    }


    // temporary function to get dummy entries
    private ArrayList<String> getDummy(int times) {

        ArrayList<String> dummyList = new ArrayList<>();


        for (int i = times; i > 0; i--) {
            dummyList.add("blaaa: " + i);
        }

        return dummyList;
    }
}