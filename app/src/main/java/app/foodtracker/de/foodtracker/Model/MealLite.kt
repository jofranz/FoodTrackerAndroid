package app.foodtracker.de.foodtracker.Model

import android.arch.persistence.room.*
import java.nio.charset.CodingErrorAction.REPLACE
import java.util.*
import java.lang.annotation.Annotation
import android.arch.persistence.room.OnConflictStrategy

@Entity(tableName = "meal")
data class Meal(@ColumnInfo(name = "foodname") var foodname: String,
                    @ColumnInfo(name = "shortDescription") var shortDescription: String,
                    @ColumnInfo(name= "longDescription") var longDescription: String,
                    @ColumnInfo(name= "effectDescription") var effectDescription: String,
                    @ColumnInfo(name = "effect")var effect: Int,
                    @ColumnInfo(name = "time")var time: Long,
                    @ColumnInfo(name = "lat") var lat: Double,
                    @ColumnInfo(name = "lng") var lng: Double,
                    @ColumnInfo(name = "addressline") var addressline: String){

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    @Dao interface MealDao{
        @Query("select * from meal")
        fun getAllMeal(): List<Meal>

        @Query("select * from meal where name = :p0")
        fun findMealByName(foodname: String): Meal

        @Query("select * from meal where id = :p0")
        fun findMealById(id: Int)

        @Query("select * from meal where time >= :p0")
        fun findAllMealsAfter(time: Long) : List<Meal>

        @Query("select * from meal where addressLine == :p0")
        fun findAllMealsWithAddress(addressline: String)

        @Insert(onConflict = 1)
        fun insetMeal(meal: Meal)

        @Delete
        fun deleteMeal(meal: Meal)

    }
    @Database(entities = arrayOf(Meal::class), version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase(){

        abstract fun mealDao(): MealDao


    }
}



/**
 * Created by normen on 22.11.17.
 */
