package app.foodtracker.de.foodtracker.Model

import android.media.Image
import java.util.*

/**
 * Created by Johannes Franz on 06.11.2017.
 */

class MarkerRepresentation(var mealImage: Image?, var time: GregorianCalendar?, var meal: String?) {

    init {
        mealImage = null
        time = GregorianCalendar()
        meal = "Burger2"
    }
}