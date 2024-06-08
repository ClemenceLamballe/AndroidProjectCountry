package fr.epf.min1.androidsearchcountryapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.epf.mm.gestionclient.model.Country
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository


class DetailCountryFragment : Fragment() {

    private lateinit var favoriteButtonDetail: ImageButton

    private var country: Country? = null
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getParcelable("country")
        }
        isFavorite =  FavoriteCountriesRepository.favoriteCountries.contains(country)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MYTAG","dans DETAILS : onCreateView ")

        val view = inflater.inflate(R.layout.activity_detail_coutry, container, false)

        favoriteButtonDetail = view.findViewById(R.id.favoriteButtonDetailsPage)

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




    private fun updateUI(view: View) {
        Log.d("MYTAG", "DetailCountryFragment: updateUI")
        country?.let {
            view.findViewById<TextView>(R.id.countryTitleDetailsPage).text = it.name.common?: "Unknown"
            view.findViewById<TextView>(R.id.countryCapitalDetailsPageValue).text = if (it.capital.isNotEmpty()) it.capital.joinToString(", ") else "Unknown"

            view.findViewById<TextView>(R.id.countryCommonNameDetailsPageValue).text = it.name.common
            view.findViewById<TextView>(R.id.countryOfficialNameDetailsPageValue).text = it.name.official?: "Unknown"
            //view.findViewById<TextView>(R.id.countryAbbreviationDetailsPageValue).text = it.idd.root
            //view.findViewById<TextView>(R.id.countryAbbreviationDetailsPageValue).text = it.idd.suffixes.joinToString(", ")?: "Unknown" pas interressant : fait un chiffre
            view.findViewById<TextView>(R.id.countryIndependentDetailsPageValue).text = it.independent.toString()
            view.findViewById<TextView>(R.id.countryUNMemberDetailsPageValue).text = it.unMember.toString()

            view.findViewById<TextView>(R.id.countryPopulationDetailsPageValue).text = it.population.toString()
            view.findViewById<TextView>(R.id.countryRegionDetailsPageValue).text = it.region?: "Unknown"
            view.findViewById<TextView>(R.id.countrySubregionDetailsPageValue).text = it.subregion?: "Unknown"
            view.findViewById<TextView>(R.id.countryContinentDetailsPageValue).text = it.continents.joinToString(", ")?: "Unknown"

            view.findViewById<TextView>(R.id.countryLatitudeDetailsPageValue).text = if (it.latlng.isNotEmpty()) it.latlng[0].toString() else "Unknown"
            view.findViewById<TextView>(R.id.countryLongitudeDetailsPageValue).text = if (it.latlng.size > 1) it.latlng[1].toString() else "Unknown"
            view.findViewById<TextView>(R.id.countryLandlockedDetailsPageValue).text = it.landlocked.toString()
            view.findViewById<TextView>(R.id.countryNeighborsDetailsPageValue).text = if (it.borders != null && it.borders.isNotEmpty()) {
                it.borders.joinToString(", ")
            } else {
                "Unknown"
            }

            view.findViewById<TextView>(R.id.countryMapDetailsPageValue).text = it.maps.googleMaps ?: "Unknown"
            view.findViewById<TextView>(R.id.countryCallingCodesDetailsPageValue).text = if (it.idd.suffixes.isNotEmpty()) it.idd.suffixes.joinToString(", ") else "Unknown"

            view.findViewById<TextView>(R.id.countryCurrencyDetailsPageValue).text = if (it.currencies.isNotEmpty()) it.currencies.keys.joinToString(", ") else "Unknown"


            view.findViewById<TextView>(R.id.countryLanguagesDetailsPageValue).text = if (it.languages.isNotEmpty()) it.languages.values.joinToString(", ") else "Unknown"


            Glide.with(this)
                .load(it.flags.png)
                .into(view.findViewById<ImageView>(R.id.countryFlagImageViewDetailsPage))
        }


    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        Log.d("MYTAG", "DetailCountryFragment: updateFavoriteButton - IsFavorite: $isFavorite")
        if (isFavorite) {
            favoriteButtonDetail.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            favoriteButtonDetail.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

}
