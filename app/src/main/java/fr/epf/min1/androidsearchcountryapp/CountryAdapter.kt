package fr.epf.min1.androidsearchcountryapp

import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository
import fr.epf.mm.gestionclient.model.Country

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

interface CountryItemClickListener {
    fun onCountryItemClicked(country : Country, newFavoriteState : Boolean)
}

class CountryAdapter(val countries: List<Country>, private val clickListener: CountryItemClickListener?=null) : RecyclerView.Adapter<CountryViewHolder>() {

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        Log.d("MYTAG","onCREATEViewHolder ")

        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        Log.d("MYTAG","onBindViewHolder ")

        val country = countries[position]
        val view = holder.itemView

        view.apply {
            findViewById<TextView>(R.id.countryNameTextView).text = country.name.common
            findViewById<TextView>(R.id.countryCapitalTextView).text = country.capital.getOrNull(0) ?: ""
            Glide.with(context)
                .load(country.flags.png)
                .error(R.drawable.error_image)
                .into(findViewById(R.id.countryFlagImageView))

            val favoriteButton = findViewById<ImageButton>(R.id.favoriteButton)
            //val isFavorite = sharedPreferences.getBoolean(country.name.common, false)
            val isFavorite = FavoriteCountriesRepository.favoriteCountries.contains(country)

            updateFavoriteButton(favoriteButton, isFavorite)

            favoriteButton.setOnClickListener {
                val newFavoriteState = !isFavorite
                updateFavoriteState(favoriteButton, country, newFavoriteState)
                updateFavoriteButton(favoriteButton, newFavoriteState)

            }

            findViewById<CardView>(R.id.country_view_cardview).setOnClickListener {
                clickListener?.onCountryItemClicked(country, isFavorite)
            }
        }
    }

    private fun updateFavoriteButton(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun updateFavoriteState(button: ImageButton, country: Country, isFavorite: Boolean) {
        if (isFavorite) {
            FavoriteCountriesRepository.favoriteCountries.add(country)
            FavoriteCountriesRepository.saveFavorites(context)

        } else {
            FavoriteCountriesRepository.favoriteCountries.remove(country)
            FavoriteCountriesRepository.saveFavorites(context)

        }
        updateFavoriteButton(button, isFavorite)
    }
}

@GlideModule//voir si supprimer ne fait rien
class MyAppGlideModule : AppGlideModule()
