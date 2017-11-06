package oldandBusted;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import app.foodtracker.de.foodtracker.Model.MarkerRepresentation;
import app.foodtracker.de.foodtracker.R;

/**
 * Created by normen on 17.10.17.
 */

//### work in progress ###

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private final View mWindow;
    private View v;
    private LayoutInflater layoutInflater;

    CustomInfoWindowAdapter(Activity activity) {
        mWindow = activity.getLayoutInflater().inflate(R.layout.costum_info_window, null);
        layoutInflater = activity.getLayoutInflater();

    }
    @Override
    public View getInfoWindow(Marker marker) {



        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        v = layoutInflater.inflate(R.layout.costum_info_window,null);

        //get the postion of the marker
        LatLng latLng = marker.getPosition();

        TextView infoMeal = v.findViewById(R.id.infoMeal);
        TextView infoTime = v.findViewById(R.id.infoTime);

        MarkerRepresentation markerRe = (MarkerRepresentation) marker.getTag();

        infoMeal.setText(markerRe.getMeal());
        infoTime.setText(markerRe.getTime().getTime().toString());



        return v;
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d("bonobo","bonobo");
    }
}
