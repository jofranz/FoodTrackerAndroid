package app.foodtracker.de.foodtracker.Model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by normen on 15.01.18.
 */
@Database(entities = arrayOf(Meal::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealModel(): Meal.MealDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        @JvmStatic fun getInMemoryDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}

