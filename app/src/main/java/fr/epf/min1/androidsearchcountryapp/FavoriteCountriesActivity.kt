package fr.epf.min1.androidsearchcountryapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.mm.gestionclient.model.Country

class FavoriteCountriesActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private val favoriteCountries = mutableListOf<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_countries)

        // Initialisation du RecyclerView
        recyclerView = findViewById(R.id.favoriteCountriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mise à jour de la liste des pays favoris
        updateFavoriteCountriesList()
    }

    private fun updateFavoriteCountriesList() {
        // Récupération des préférences partagées contenant les pays favoris
        val sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE)

        // Parcours de la liste des pays pour vérifier s'ils sont marqués comme favoris dans les préférences partagées
        for (countryName in sharedPreferences.all.keys) {
            val isFavorite = sharedPreferences.getBoolean(countryName, false)
            if (isFavorite) {
                val country = Country(Country.Name("", ""), false, "", false, emptyMap(), Country.Idd("", emptyList()), emptyList(), emptyList(), "", "", emptyMap(), emptyList(), false, emptyList(), "", Country.Maps("", ""), 0, emptyList(), Country.Flags("", "", null))

                favoriteCountries.add(country)
            }
        }
        countryAdapter = CountryAdapter(favoriteCountries)
        recyclerView.adapter = countryAdapter
    }
}
