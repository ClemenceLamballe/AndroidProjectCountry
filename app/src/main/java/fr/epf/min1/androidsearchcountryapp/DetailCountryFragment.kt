package fr.epf.min1.androidsearchcountryapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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

class DetailCountryFragment : Fragment() {

    private lateinit var favoriteButtonDetail: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private var isFavorite: Boolean = false
    private lateinit var countryName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_detail_coutry, container, false)


        favoriteButtonDetail = view.findViewById(R.id.favoriteButtonDetailsPage)
        sharedPreferences = requireActivity().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        countryName = requireActivity().intent.getStringExtra("countryNameFavorite") ?: ""





        //ICI FAIRE CHANGEMENT DE VAR
        val country=requireActivity().intent.getParcelableExtra<Country>("country")
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
            //findViewById<TextView>(R.id.countryCallingCodesDetailsPageValue).text = it.callingCodes.joinToString(", ")
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







        favoriteButtonDetail = view.findViewById(R.id.favoriteButtonDetailsPage)
        sharedPreferences = requireActivity().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        countryName = requireActivity().intent.getStringExtra("countryNameFavorite") ?: ""
        isFavorite = sharedPreferences.getBoolean(countryName, false)
        updateFavoriteButton(favoriteButtonDetail, isFavorite)



        favoriteButtonDetail.setOnClickListener {
            isFavorite = !isFavorite
            sharedPreferences.edit().putBoolean(countryName, isFavorite).apply()
            updateFavoriteButton(favoriteButtonDetail, isFavorite)
            sendFavoriteChangedBroadcast()
        }

        return view
    }

    private fun updateFavoriteButton(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun sendFavoriteChangedBroadcast() {
        Log.d("MyTag","Details envoit a Liste")
        val intent = Intent("fr.epf.min1.androidsearchcountryapp.FAVORITE_CHANGED")
        intent.putExtra("countryName", countryName)
        intent.putExtra("isFavorite", isFavorite)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
}

