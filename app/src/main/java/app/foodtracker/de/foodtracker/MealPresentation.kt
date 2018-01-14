package app.foodtracker.de.foodtracker

import app.foodtracker.de.foodtracker.Model.Meal
import java.text.FieldPosition

/**
 * Created by normen on 14.01.18.
 */
interface MealPresentation{
    fun showsMels(meals: List<Meal>)
    fun taskAddedAt(position: Int)
    fun scrollTo(position: Int)
}