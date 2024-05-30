package fr.epf.min1.androidsearchcountryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.activity.ComponentActivity

import androidx.recyclerview.widget.RecyclerView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchField = findViewById<TextView>(R.id.searchField)
        val buttonsearchByCountry = findViewById<Button>(R.id.searchByCountryButton)
        val buttonsearchByCapital = findViewById<Button>(R.id.searchByCapitalButton)


        buttonsearchByCountry.setOnClickListener {
            val searchTerm = searchField.text.toString()
            if (searchTerm.isNotEmpty()) {
                val intent = Intent(this, CountryListActivity::class.java)
                intent.putExtra("searchTerm", searchTerm)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Veuillez saisir un terme de recherche", Toast.LENGTH_SHORT).show()
            }
        }

        buttonsearchByCapital.setOnClickListener(){
            val searchTerm = searchField.text.toString()
            if (searchTerm.isNotEmpty()) {
                val intent = Intent(this, CountryListActivity::class.java)
                intent.putExtra("searchTerm", searchTerm)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Veuillez saisir un terme de recherche", Toast.LENGTH_SHORT).show()
            }
        }



    }



}

