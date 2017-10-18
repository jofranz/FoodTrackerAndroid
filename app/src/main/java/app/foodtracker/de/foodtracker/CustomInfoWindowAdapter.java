package app.foodtracker.de.foodtracker;

import android.app.Activity;
import android.view.View;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by normen on 17.10.17.
 */

//### work in progress ###

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private View layoutInflater;

    CustomInfoWindowAdapter(Activity activity) {
        mWindow = activity.getLayoutInflater().inflate(R.layout.costum_info_window, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
