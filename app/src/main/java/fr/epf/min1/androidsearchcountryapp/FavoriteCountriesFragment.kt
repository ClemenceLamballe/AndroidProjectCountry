package fr.epf.min1.androidsearchcountryapp

import Country
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment


class FavoriteCountriesFragment : Fragment(), CountryItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private val favoriteCountries = mutableListOf<Country>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_favorite_countries, container, false)

        // Initialisation du RecyclerView
        recyclerView = view.findViewById(R.id.favoriteCountriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Mise à jour de la liste des pays favoris
        updateFavoriteCountriesList()

        return view
    }

    private fun updateFavoriteCountriesList() {
        // Récupération des préférences partagées contenant les pays favoris
        val sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)

        // Parcours de la liste des pays pour vérifier s'ils sont marqués comme favoris dans les préférences partagées
        for (countryName in sharedPreferences.all.keys) {
            val isFavorite = sharedPreferences.getBoolean(countryName, false)
            if (isFavorite) {
                val country = Country(countryName, "", "") // Remplacer ces champs par les vraies informations du pays
                favoriteCountries.add(country)
            }
        }
        countryAdapter = CountryAdapter(favoriteCountries,  this)
        recyclerView.adapter = countryAdapter
    }

    override fun onCountryItemClicked(countryName: String) {
        val fragment = DetailCountryFragment()
        val bundle = Bundle()
        bundle.putString("countryNameFavorite", countryName)
        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
