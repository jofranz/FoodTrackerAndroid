package oldandBusted;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import app.foodtracker.de.foodtracker.R;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("jf table pos: ", "click " + position);
            }
        });

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