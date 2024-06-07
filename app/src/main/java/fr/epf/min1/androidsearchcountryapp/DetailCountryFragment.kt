package fr.epf.min1.androidsearchcountryapp

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import fr.epf.mm.gestionclient.model.Country
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository


class DetailCountryFragment : Fragment() {

    private lateinit var favoriteButtonDetail: ImageButton
    //private lateinit var sharedPreferences: SharedPreferences
    //private var isFavorite: Boolean = false
    private var country: Country? = null
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getParcelable("country")
        }
        isFavorite =  FavoriteCountriesRepository.favoriteCountries.contains(country)

    }

    /*private val favoriteChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("MYTAG", "BroadcastReceiver - onReceive")
            if (intent != null) {
                country = intent.getParcelableExtra("Country : ") ?: return
                val isFavorite = intent.getBooleanExtra("isFavorite", false)
                updateFavoriteButton(isFavorite)
                Log.d("MYTAG", "BroadcastReceiver - Country: ${country?.name?.common}, IsFavorite: $isFavorite")

            }
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MYTAG","dans DETAILS : onCreateView ")

        val view = inflater.inflate(R.layout.activity_detail_coutry, container, false)

        favoriteButtonDetail = view.findViewById(R.id.favoriteButtonDetailsPage)
        //sharedPreferences = requireActivity().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        /*country = arguments?.getParcelable("country")
        country?.let {
            isFavorite = sharedPreferences.getBoolean(it.name.common, false)
        }*/

        /*country = arguments?.getParcelable("country")
        val isFavorite = FavoriteCountriesRepository.favoriteCountries.contains(country)
        updateFavoriteButton(isFavorite)
        Log.d("MYTAG", "onCreateView - Country: ${country?.name?.common}, IsFavorite: $isFavorite")
        */
        updateUI(view)
        return view
       }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MYTAG", "DetailCountryFragment: onViewCreated")
        country = arguments?.getParcelable("country")
        Log.d("MYTAG", "DetailCountryFragment: country ${country?.name?.common}")

        val isFavorite = FavoriteCountriesRepository.favoriteCountries.contains(country)
        updateFavoriteButton(isFavorite)
        favoriteButtonDetail.setOnClickListener {
            country?.let {
                val newFavoriteState = !isFavorite
                if (newFavoriteState) {
                    FavoriteCountriesRepository.favoriteCountries.add(it)
                } else {
                    FavoriteCountriesRepository.favoriteCountries.remove(it)
                }
                updateFavoriteButton(newFavoriteState)
            }
        }
    }


    /*override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            favoriteChangedReceiver,
            IntentFilter("fr.epf.min1.androidsearchcountryapp.FAVORITE_CHANGED")
        )
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(favoriteChangedReceiver)
    }*/

    private fun updateUI(view: View) {
        Log.d("MYTAG", "DetailCountryFragment: updateUI")
        country?.let {
            view.findViewById<TextView>(R.id.countryTitleDetailsPage).text = it.name.common
            view.findViewById<TextView>(R.id.countryCapitalDetailsPageValue).text = it.capital.joinToString(", ")
            view.findViewById<TextView>(R.id.countryOfficialNameDetailsPageValue).text = it.name.official
            view.findViewById<TextView>(R.id.countryAbbreviationDetailsPageValue).text = it.flag
            view.findViewById<TextView>(R.id.countryCurrencyDetailsPageValue).text = it.currencies.keys.joinToString(", ")
            view.findViewById<TextView>(R.id.countryPopulationDetailsPageValue).text = it.population.toString()
            view.findViewById<TextView>(R.id.countryNeighboringCountriesDetailsPageValue).text = it.borders.joinToString(", ")
            view.findViewById<TextView>(R.id.countryIndependentDetailsPageValue).text = it.independent.toString()
            view.findViewById<TextView>(R.id.countryUNMemberDetailsPageValue).text = it.unMember.toString()
            view.findViewById<TextView>(R.id.countryLanguagesDetailsPageValue).text = it.languages.values.joinToString(", ")
            view.findViewById<TextView>(R.id.countryRegionDetailsPageValue).text = it.region
            view.findViewById<TextView>(R.id.countrySubregionDetailsPageValue).text = it.subregion
            view.findViewById<TextView>(R.id.countryContinentDetailsPageValue).text = it.continents.joinToString(", ")
            view.findViewById<TextView>(R.id.countryLatitudeDetailsPageValue).text = it.latlng[0].toString()
            view.findViewById<TextView>(R.id.countryLongitudeDetailsPageValue).text = it.latlng[1].toString()
            view.findViewById<TextView>(R.id.countryLandlockedDetailsPageValue).text = it.landlocked.toString()

            Glide.with(this)
                .load(it.flags.png)
                .into(view.findViewById<ImageView>(R.id.countryFlagImageViewDetailsPage))
        }

        /*updateFavoriteButton(isFavorite)
        favoriteButtonDetail.setOnClickListener {
            isFavorite = !isFavorite
            country?.let {
                sharedPreferences.edit().putBoolean(it.name.common, isFavorite).apply()
                updateFavoriteButton(isFavorite)
                sendFavoriteChangedBroadcast()
            }
        }*/
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        Log.d("MYTAG", "DetailCountryFragment: updateFavoriteButton - IsFavorite: $isFavorite")
        if (isFavorite) {
            favoriteButtonDetail.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            favoriteButtonDetail.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    /*private fun sendFavoriteChangedBroadcast() {
        Log.d("MYTAG", "DetailCountryFragment: sendFavoriteChangedBroadcast")
        val intent = Intent("fr.epf.min1.androidsearchcountryapp.FAVORITE_CHANGED")
        intent.putExtra("country", country)
        intent.putExtra("isFavorite", isFavorite)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }*/

}
