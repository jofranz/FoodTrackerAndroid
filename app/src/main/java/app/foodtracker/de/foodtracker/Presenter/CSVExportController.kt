package app.foodtracker.de.foodtracker.Presenter

import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import app.foodtracker.de.foodtracker.Model.AppDatabase
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.text.DateFormat
import java.util.*


/**
 * Created by normen on 24.01.18.
 */
class CSVExportController: AsyncTask<Context, Void, Unit>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }
    override fun doInBackground(vararg params: Context?): Unit {
        val mdb = AppDatabase.getInMemoryDatabase(params.get(0)!!)
        val meals = mdb.mealModel().getAllMeal()
        val state = Environment.getExternalStorageState()
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return Unit
        }
        val exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val file: File
        val printWr: PrintWriter
        try {
            val df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())

            file = File(exportDir, "FoodtrackerData" +
                    ".csv")
            file.createNewFile()
            printWr = PrintWriter(PrintWriter(file))
            printWr.println("Foodname,shortDescription,longDescription,effectDescription,effect,time,lat,lng,addressline,imagePath")
            for (item in meals){
                val foodname = item.foodname
                val shortDescription = item.shortDescription
                val longDescription = item.longDescription
                val effectDescription = item.effectDescription
                val effect = item.effect
                val time = item.time
                val lat = item.lat
                val lng = item.lng
                val addresLine = item.addressline

                val row = (foodname + ";" + shortDescription + ";" + longDescription + ";" + effectDescription + ";" + effect + ";"
                        + time  + ";" + lat + ';' + lng + ";" + addresLine)
                printWr.println(row)
            }
            printWr.close()
        }catch (exc: Exception){
            Log.d("CSVExport", exc.toString())
        }

    }


}