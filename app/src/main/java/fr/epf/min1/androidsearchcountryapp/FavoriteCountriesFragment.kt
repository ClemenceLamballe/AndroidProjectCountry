package fr.epf.min1.androidsearchcountryapp


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import fr.epf.mm.gestionclient.model.Country


class FavoriteCountriesFragment : Fragment(), CountryItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private val favoriteCountries = mutableListOf<Country>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_favorite_countries, container, false)
        Log.d("MYTAG"," ON CREATE")

        recyclerView = view.findViewById(R.id.favoriteCountriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //Moi : je vais synchroniser les étoiles et déboger de favoris à détails
        updateFavoriteCountriesList()
        return view
    }

    private fun updateFavoriteCountriesList() {
        Log.d("MYTAG"," UPDATE")
        val emptyMessageTextView = view?.findViewById<TextView>(R.id.emptyMessageTextView)
        val sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        favoriteCountries.clear()

        //Moi : Rajouter ici la méthode pour récupérer les favoritescourtires

        //Toi :  tu peux creer ta liste ici ^^ (ou directement sur la page "QuizzFragment" que tu vas creer, on verra plus tard pour faire le lien





        if (favoriteCountries.isNotEmpty()) {
            Log.d("MYTAG", "NOT null")
            Log.d("MYTAG", "Size: ${favoriteCountries.size}")

            recyclerView.visibility = View.VISIBLE
            emptyMessageTextView?.visibility = View.GONE

            countryAdapter = CountryAdapter(favoriteCountries, this)
            recyclerView.adapter = countryAdapter
        } else {
            Log.d("MYTAG", "null")
            emptyMessageTextView?.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    override fun onCountryItemClicked(country : Country, newFavoriteState : Boolean) {
        val fragment = DetailCountryFragment()
        val bundle = Bundle()
        bundle.putBoolean("isFavorite", newFavoriteState)
        bundle.putParcelable("country", country) // Use putParcelable instead of putBoolean for passing country object

        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
