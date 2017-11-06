package app.foodtracker.de.foodtracker.Model

/**
 * Created by Johannes Franz on 06.11.2017.
 */

class Food(var foodName : String?, var shortDescription : String?, var longDescription : String?, var effectDescription : String?, var effect : Int) {
    init {
        foodName = "Burger";
        shortDescription = "very delicious food"
        longDescription = "Burger with potatoes"
        effectDescription = "no bad effects"
        effect = 1 //0-2  0 bad, 1 neutral, 2 good
    }
}