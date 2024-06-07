package fr.epf.min1.androidsearchcountryapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.util.Log
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
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class CountryListFragment : Fragment(), CountryItemClickListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter

    /*private val favoriteChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                val countryName = intent.getStringExtra("countryName") ?: return
                val isFavorite = intent.getBooleanExtra("isFavorite", false)
                countryAdapter.updateFavoriteStatus(countryName, isFavorite)
            }
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_list, container, false)
        recyclerView = view.findViewById(R.id.countryListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CountryListFragment", "onViewCreated: Starting data fetch")
        fetchData()
    }

    /*override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(favoriteChangedReceiver, IntentFilter("fr.epf.min1.androidsearchcountryapp.FAVORITE_CHANGED"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(favoriteChangedReceiver)
    }*/

    private fun fetchData() {
        val searchTerm = arguments?.getString("searchTerm")
        val searchByType = arguments?.getString("searchByType")

        Log.d("CountryListFragment", "searchTerm: $searchTerm")
        Log.d("CountryListFragment", "searchByType: $searchByType")


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
            try {
                val response = when (searchByType) {
                    "country" -> countryService.searchCountryByName(searchTerm ?: "")
                    "capital" -> countryService.searchCountryByCapital(searchTerm ?: "")
                    else -> null
                }

                if (response != null && response.isSuccessful) {
                    Log.d("CountryListFragment", "onResponse: Success")
                    val countries = response.body()
                    withContext(Dispatchers.Main) {
                        if (countries != null) {
                            displayCountries(countries)
                        } else {
                            Toast.makeText(requireContext(), "Aucun pays trouvé", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Log.e("MYTAG", "Échec de la récupération des pays: ${response?.code()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Échec de la récupération des pays", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("MYTAG", "Erreur lors de la récupération des données: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun displayCountries(countries: List<Country>) {
        Log.d("CountryListFragment", "displayCountries: Displaying ${countries.size} countries")
        countryAdapter = CountryAdapter(countries, this)
        recyclerView.adapter = countryAdapter
    }

    override fun onCountryItemClicked(country: Country, isFavorite: Boolean) {
        Log.d("CountryListFragment", "onCountryItemClicked: Clicked on country: ${country.name.common}, IsFavorite: $isFavorite")

        val fragment = DetailCountryFragment()
        val bundle = Bundle()
        bundle.putParcelable("country", country)

        fragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
