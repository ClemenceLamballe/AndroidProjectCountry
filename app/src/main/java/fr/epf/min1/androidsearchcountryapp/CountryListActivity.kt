package fr.epf.min1.androidsearchcountryapp

import Country
import RestCountriesService
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CountryListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_country_list)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RestCountriesService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                //val countryList = service.getAllCountries()
                val countryList = listOf(
                    Country("France", "Paris", "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg"),
                    Country("USA", "Washington, D.C.", "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"),
                    Country("Germany", "Berlin", "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"),
                    Country("Japan", "Tokyo", "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg"),
                    Country("Canada", "Ottawa", "https://upload.wikimedia.org/wikipedia/commons/c/cf/Flag_of_Canada.svg"),
                    Country("Brazil", "Brasília", "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg"),
                    Country("Australia", "Canberra", "https://upload.wikimedia.org/wikipedia/commons/b/b9/Flag_of_Australia.svg"),
                    Country("India", "New Delhi", "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg"),
                    Country("Mexico", "Mexico City", "https://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg"),
                    Country("Russia", "Moscow", "https://upload.wikimedia.org/wikipedia/en/f/f3/Flag_of_Russia.svg")
                )
                withContext(Dispatchers.Main) {
                    displayCountries(countryList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CountryListActivity, "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun displayCountries(countries: List<Country>) {
        recyclerView = findViewById(R.id.countryListRecyclerView)
        //glide pour image??
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(countries)

        recyclerView.adapter = countryAdapter
    }




}
