package fr.epf.min1.androidsearchcountryapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min1.androidsearchcountryapp.api.CountryService
import fr.epf.mm.gestionclient.model.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


class CountryListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchTerm = intent.getStringExtra("searchTerm")
        val searchByType = intent.getStringExtra("searchByType")
        if (searchTerm!=null){
            Log.e("TEST_SEARCH", searchTerm)
        }

        setContentView(R.layout.activity_country_list)

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val countryService = retrofit.create(CountryService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<List<Country>>? = null

            if (!searchTerm.isNullOrEmpty()) {
                response = if (searchByType == "country") {
                    countryService.searchCountryByName(searchTerm)
                } else if (searchByType == "capital") {
                    countryService.searchCountryByCapital(searchTerm)
                } else {
                    // Gérer les autres types de recherche ici
                    null
                }
            }

            if (response != null) {
                if (response.isSuccessful) {
                    Log.d("TEST_API", "Success")
                    val countries = response.body()

                    withContext(Dispatchers.Main) {
                        if (countries != null) {
                            displayCountries(countries)
                        }
                    }
                } else {
                    Log.e("TEST_API", "Échec de la récupération des pays: ${response.code()}")
                }
            }
        }





       /* CoroutineScope(Dispatchers.IO).launch {
            try {
                val countryList = service.getAllCountries()
                // TODO(FAIRE MARCHER API ET LOGIQUE RE RECHERCHE ICI)
                *//*val countryList = listOf(
                    Country("France", "Paris", "French Republic", "FR", "Euro", "€", "+33", listOf("2"), listOf("AND", "BEL", "DEU", "ITA", "LUX", "MCO", "ESP", "CHE"), "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg", "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg", 67391582, "Europe", true, true, mapOf("fra" to "French"), "Europe", "Western Europe", 48.8566, 2.3522, false),
                    Country("USA", "Washington, D.C.", "United States of America", "US", "United States Dollar", "$", "+1", listOf("2"), listOf("CAN", "MEX"), "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg", "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg", 331002651, "North America", true, true, mapOf("eng" to "English"), "Americas", "North America", 38.9072, -77.0369, false),
                    Country("Germany", "Berlin", "Federal Republic of Germany", "DE", "Euro", "€", "+49", listOf("2"), listOf("AUT", "BEL", "CZE", "DNK", "FRA", "LUX", "NLD", "POL", "CHE"), "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg", "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg", 83166711, "Europe", true, true, mapOf("deu" to "German"), "Europe", "Western Europe", 52.5200, 13.4050, false),
                    Country("Japan", "Tokyo", "Japan", "JP", "Japanese Yen", "¥", "+81", listOf(), listOf(), "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg", "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg", 126476461, "Asia", true, true, mapOf(), "Asia", "Eastern Asia", 35.6895, 139.6917, false),
                    Country("Canada", "Ottawa", "Canada", "CA", "Canadian Dollar", "$", "+1", listOf("2"), listOf("USA"), "https://upload.wikimedia.org/wikipedia/commons/c/cf/Flag_of_Canada.svg", "https://upload.wikimedia.org/wikipedia/commons/c/cf/Flag_of_Canada.svg", 37742154, "North America", true, true, mapOf("eng" to "English", "fra" to "French"), "Americas", "Northern America", 45.4215, -75.6972, false),
                    Country("Brazil", "Brasília", "Federative Republic of Brazil", "BR", "Brazilian Real", "R$", "+55", listOf("2"), listOf("ARG", "BOL", "COL", "GUF", "GUY", "PRY", "PER", "SUR", "URY", "VEN"), "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg", "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg", 212559417, "South America", true, true, mapOf("por" to "Portuguese"), "Americas", "South America", -15.8267, -47.9218, false),
                    Country("Australia", "Canberra", "Commonwealth of Australia", "AU", "Australian Dollar", "$", "+61", listOf("2"), listOf(), "https://upload.wikimedia.org/wikipedia/commons/b/b9/Flag_of_Australia.svg", "https://upload.wikimedia.org/wikipedia/commons/b/b9/Flag_of_Australia.svg", 25687041, "Oceania", true, true, mapOf("eng" to "English"), "Oceania", "Australia and New Zealand", -35.3081, 149.1244, false),
                    Country("India", "New Delhi", "Republic of India", "IN", "Indian Rupee", "₹", "+91", listOf("2"), listOf("BGD", "BTN", "MMR", "CHN", "NPL", "PAK", "LKA"), "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg", "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg", 1380004385, "Asia", true, true, mapOf("eng" to "English", "hin" to "Hindi"), "Asia", "Southern Asia", 28.6139, 77.2090, false),
                    Country("Mexico", "Mexico City", "United Mexican States", "MX", "Mexican Peso", "$", "+52", listOf("2"), listOf("BLZ", "GTM", "USA"), "https://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg", "https://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg", 128932753, "North America", true, true, mapOf("spa" to "Spanish"), "Americas", "Central America", 19.4326, -99.1332, false),
                    Country("Russia", "Moscow", "Russian Federation", "RU", "Russian Ruble", "₽", "+7", listOf("2"), listOf("AZE", "BLR", "CHN", "EST", "FIN", "GEO", "KAZ", "PRK", "LVA", "LTU", "MNG", "NOR", "POL", "UKR"), "https://upload.wikimedia.org/wikipedia/en/f/f3/Flag_of_Russia.svg", "https://upload.wikimedia.org/wikipedia/en/f/f3/Flag_of_Russia.svg", 145912025, "Europe", true, true, mapOf("rus" to "Russian"), "Europe", "Eastern Europe", 55.7558, 37.6176, false),
                    )*//*

                withContext(Dispatchers.Main) {
                    displayCountries(countryList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CountryListActivity, "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show()
                }
            }
        }*/


    }

    private fun displayCountries(countries: List<Country>) {
        recyclerView = findViewById(R.id.countryListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        countryAdapter = CountryAdapter(countries)
        recyclerView.adapter = countryAdapter
    }




}