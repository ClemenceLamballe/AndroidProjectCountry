package fr.epf.min1.androidsearchcountryapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import fr.epf.mm.gestionclient.model.Country

class DetailCountryActivity : AppCompatActivity() {

    private lateinit var favoriteButtonDetail: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private var isFavorite: Boolean = false
    private lateinit var countryName: String
    private lateinit var countrynom : String
    //private lateinit var country : Country

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_coutry)






        //ICI FAIRE CHANGEMENT DE VAR
        //val countryTitleTextView = findViewById<TextView>(R.id.countryTitleTextViewDetailsPage)
        val country=intent.getParcelableExtra<Country>("country")
        //country?.let { findViewById<TextView>(R.id.countryTitleTextViewDetailsPage).text = country.name.common }
        country?.let {
            findViewById<TextView>(R.id.countryTitleDetailsPage).text = it.name.common
            findViewById<TextView>(R.id.countryCapitalDetailsPageValue).text = it.capital.joinToString(", ")
            findViewById<TextView>(R.id.countryOfficialNameDetailsPageValue).text = it.name.official
            findViewById<TextView>(R.id.countryAbbreviationDetailsPageValue).text = it.flag
            findViewById<TextView>(R.id.countryCurrencyDetailsPageValue).text = it.currencies.keys.joinToString(", ")
            findViewById<TextView>(R.id.countryPopulationDetailsPageValue).text = it.population.toString()
            findViewById<TextView>(R.id.countryNeighboringCountriesDetailsPageValue).text = it.borders.joinToString(", ")
            findViewById<TextView>(R.id.countryIndependentDetailsPageValue).text = it.independent.toString()
            findViewById<TextView>(R.id.countryUNMemberDetailsPageValue).text = it.unMember.toString()
            findViewById<TextView>(R.id.countryLanguagesDetailsPageValue).text = it.languages.values.joinToString(", ")
            //findViewById<TextView>(R.id.countryCallingCodesDetailsPageValue).text = it.callingCodes.joinToString(", ")
            findViewById<TextView>(R.id.countryRegionDetailsPageValue).text = it.region
            findViewById<TextView>(R.id.countrySubregionDetailsPageValue).text = it.subregion
            findViewById<TextView>(R.id.countryContinentDetailsPageValue).text = it.continents.joinToString(", ")
            findViewById<TextView>(R.id.countryLatitudeDetailsPageValue).text = it.latlng[0].toString()
            findViewById<TextView>(R.id.countryLongitudeDetailsPageValue).text = it.latlng[1].toString()
            findViewById<TextView>(R.id.countryLandlockedDetailsPageValue).text = it.landlocked.toString()
            Glide.with(this)
                .load(it.flags.png)
                .into(findViewById<ImageView>(R.id.countryFlagImageViewDetailsPage))
        }







        favoriteButtonDetail = findViewById(R.id.favoriteButtonDetailsPage)
        sharedPreferences = getSharedPreferences("favorites", Context.MODE_PRIVATE)
        countryName = intent.getStringExtra("countryNameFavorite") ?: ""
        isFavorite = sharedPreferences.getBoolean(countryName, false)
        updateFavoriteButton(favoriteButtonDetail, isFavorite)

        

        favoriteButtonDetail.setOnClickListener {
            isFavorite = !isFavorite
            sharedPreferences.edit().putBoolean(countryName, isFavorite).apply()
            updateFavoriteButton(favoriteButtonDetail, isFavorite)
            sendFavoriteChangedBroadcast()
        }
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
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
