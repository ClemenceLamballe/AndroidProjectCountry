package fr.epf.min1.androidsearchcountryapp

import CountryRepository
import CountryService
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.min1.androidsearchcountryapp.ui.theme.AndroidSearchCountryAppTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: CountryRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CountryService::class.java)
        repository = CountryRepository(service)

        val searchButtonbyCountry = findViewById<Button>(R.id.searchByCountryButton)
        val searchField = findViewById<EditText>(R.id.searchField)

        searchButtonbyCountry.setOnClickListener {
            val query = searchField.text.toString()
            searchCountries(query)
        }
    }
    private fun searchCountries(query: String) {
        lifecycleScope.launch {
            val countries = repository.searchCountries(query)
            //recyclerView.adapter = CountryAdapter(countries)
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidSearchCountryAppTheme {
        Greeting("Android")
    }
}