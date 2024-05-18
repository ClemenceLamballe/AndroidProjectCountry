package fr.epf.min1.androidsearchcountryapp

import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class CountryListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.item_country)
        Toast.makeText(this, "Hello, World!", Toast.LENGTH_SHORT).show()
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "CountryListActivity is loaded", Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, "Hello, World!", Toast.LENGTH_SHORT).show()


    }




}
