package app.foodtracker.de.foodtracker.Model

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by normen on 22.11.17.
 */
@Module
class AppModule(private val context: Context){
    //TODO fix annotation
    @Provides
    fun providesAppContext() = context

    @Provides fun providesAppDatabase(context: Context): Meal.AppDatabase = Room.databaseBuilder(context,Meal.AppDatabase::class.java,"my-db").allowMainThreadQueries().build()

    @Provides fun proidesToDoDa(database: Meal.AppDatabase) = database.mealDao()
    annotation class Module
}