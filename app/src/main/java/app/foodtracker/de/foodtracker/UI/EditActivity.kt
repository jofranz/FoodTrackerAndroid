package app.foodtracker.de.foodtracker.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.foodtracker.de.foodtracker.R
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        //setSupportActionBar(toolbar)

        fab.isActivated = false
    }
}
