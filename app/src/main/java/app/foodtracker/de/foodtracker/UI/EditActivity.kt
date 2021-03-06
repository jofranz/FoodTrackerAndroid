package app.foodtracker.de.foodtracker.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import app.foodtracker.de.foodtracker.Model.AppDatabase
import app.foodtracker.de.foodtracker.Model.Meal
import app.foodtracker.de.foodtracker.R
import app.foodtracker.de.foodtracker.SecondMainActivity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.edit_constraint.*
import java.util.*

class EditActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var oldMeal: Meal

    override fun onClick(v: View?) {
        val name = foodName.text.toString()
        val snippet = longDescription.text.toString()
        val effectNumber = seekBar.progress
        val effectDesc = effect.text.toString()

        val meal = Meal(name,snippet,"",effectDesc,effectNumber,oldMeal.time,oldMeal.lat,oldMeal.lng,oldMeal.addressline,oldMeal.imagePath)
        val mdb = AppDatabase.getInMemoryDatabase(applicationContext)
        meal.id  = oldMeal.id
        mdb.mealModel().updateMeal(meal)

        val intent = Intent(this, SecondMainActivity::class.java)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit)
        submit.setOnClickListener(this)

        val id = intent.getStringExtra("id").toInt()
        val mdb = AppDatabase.getInMemoryDatabase(applicationContext)
        val meal =  mdb.mealModel().findMealById(id)
        oldMeal = meal
        foodName.setText(meal.foodname)
        longDescription.setText(meal.shortDescription)
        val time = GregorianCalendar()

        val imageBitmap = MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, Uri.parse(meal.imagePath))
        mealImageView.setImageBitmap(imageBitmap)
        if (!oldMeal.effectDescription.equals("")){
            effect.setText(oldMeal.effectDescription)
        }
        seekBar.progress = oldMeal.effect
        time.timeInMillis = meal.time
        infoTime.setText(time.time.toString())
        addressEdit.setText(meal.addressline)
        setSupportActionBar(toolbar)
    }
}
