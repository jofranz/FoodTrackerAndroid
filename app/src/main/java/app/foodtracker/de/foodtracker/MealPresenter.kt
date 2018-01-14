package app.foodtracker.de.foodtracker

import app.foodtracker.de.foodtracker.Model.Meal
import com.google.android.gms.tasks.Task
import javax.inject.Inject

/**
 * Created by normen on 14.01.18.
 */
class MealPresenter @Inject constructor(val mealDao: Meal.MealDao) {

    var meals = ArrayList<Meal>()

    var presentation: MealPresentation? = null

    fun onCreate(mealPresentation: MealPresentation) {
        presentation = mealPresentation
        loadMeals()
    }

    fun onDestroy() {
        presentation = null
    }

    fun loadMeals() {
        meals.clear()
        meals.addAll(mealDao.getAllMeal())
        presentation?.showsMels(meals)
    }

    fun addNewTask(foodName: String) {
        val newMeal = Meal(foodname = foodName,shortDescription = "short",longDescription = "long",effectDescription = "effectDesc",effect = 1,time = 1234567,lat = 34.324,lng = 43.1,addressline = "Test Straße")
        meals.add(newMeal)
        mealDao.insetMeal(newMeal)
        (meals.size - 1).let {
            presentation?.taskAddedAt(it)
            presentation?.scrollTo(it)
        }
    }
}