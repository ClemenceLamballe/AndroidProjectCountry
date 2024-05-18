package fr.epf.min1.androidsearchcountryapp
import fr.epf.min1.androidsearchcountryapp.CountryListActivity
import CountryRepository

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.activity.ComponentActivity

import androidx.recyclerview.widget.RecyclerView


class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: CountryRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val ButtonsearchByCountry = findViewById<Button>(R.id.searchByCountryButton)
        val ButtonsearchByCapital = findViewById<Button>(R.id.searchByCapitalButton)

        ButtonsearchByCountry.setOnClickListener(){
            with(it.context){
                val intent = Intent(this, CountryListActivity::class.java)
                startActivity(intent)
            }

        }

        ButtonsearchByCapital.setOnClickListener(){
            val intent = Intent(this, CountryListActivity::class.java)
            startActivity(intent)
        }



    }



}

