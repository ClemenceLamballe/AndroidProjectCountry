package fr.epf.min1.androidsearchcountryapp


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import fr.epf.mm.gestionclient.model.Country
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository



class FavoriteCountriesFragment : Fragment(), CountryItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    //private val favoriteCountries = mutableListOf<Country>()
    private lateinit var emptyMessageTextView: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_favorite_countries, container, false)
        Log.d("FavoriteCountriesFragment", "onCreateView called")
        emptyMessageTextView = view.findViewById(R.id.errorMessageTextView)


        recyclerView = view.findViewById(R.id.favoriteCountriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        updateFavoriteCountriesList()
        return view
    }

    private fun updateFavoriteCountriesList() {
        Log.d("FavoriteCountriesFragment", "updateFavoriteCountriesList called")

        //val sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favoriteCountries = FavoriteCountriesRepository.favoriteCountries


        //Moi : Rajouter ici la méthode pour récupérer les favoritescourtires

        //Toi :  tu peux creer ta liste ici ^^ (ou directement sur la page "QuizzFragment" que tu vas creer, on verra plus tard pour faire le lien





        if (favoriteCountries.isNotEmpty()) {
            Log.d("FavoriteCountriesFragment", "Favorite countries list is not empty, size: ${favoriteCountries.size}")
            emptyMessageTextView?.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE


            countryAdapter = CountryAdapter(favoriteCountries, this)
            recyclerView.adapter = countryAdapter
        } else {
            Log.d("FavoriteCountriesFragment", "Favorite countries list is empty")
            emptyMessageTextView.text = "Favorite countries list is empty"
            emptyMessageTextView?.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    override fun onCountryItemClicked(country : Country, newFavoriteState : Boolean) {
        Log.d("FavoriteCountriesFragment", "onCountryItemClicked called with country: ${country.name.common}, newFavoriteState: $newFavoriteState")

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
