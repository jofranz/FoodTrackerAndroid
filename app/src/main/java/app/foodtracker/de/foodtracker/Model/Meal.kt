package app.foodtracker.de.foodtracker.Model

import android.media.Image
import java.util.*

/**
 * Created by Johannes Franz on 06.11.2017.
 */


class Meal(var food: Food?, var time: GregorianCalendar?, var foodImage: Image?) {

    init {
        food = Food("Burger", "Delicious Burger", "Burger with meat and a great sauce", "no effects", 1)
        time = GregorianCalendar()
        foodImage = null
    }
}