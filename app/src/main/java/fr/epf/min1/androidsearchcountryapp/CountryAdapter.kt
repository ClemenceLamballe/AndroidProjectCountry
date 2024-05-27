package fr.epf.min1.androidsearchcountryapp
import Country
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

class CountryAdapter(val countries: List<Country>) : RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        val view = holder.itemView
        val countryNameTextView = view.findViewById<TextView>(R.id.countryNameTextView)
        countryNameTextView.text = country.name

        val countryCapitalTextView = view.findViewById<TextView>(R.id.countryCapitalTextView)
        countryCapitalTextView.text = country.capital
        val countryFlagImageView = view.findViewById<ImageView>(R.id.countryFlagImageView)

        Log.d("MyTag", "country: ${countryFlagImageView}")


        //ne marche pas :  les drapeaux
        Glide.with(holder.itemView.context)
            .load(country.flag)
            .error(R.drawable.error_image)
            .into(countryFlagImageView)

        // Initialiser l'état du bouton étoile (éteint par défaut)
        val favoriteButton = view.findViewById<ImageButton>(R.id.favoriteButton)
        var isFavorite = false

        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_on)
            } else {
                favoriteButton.setImageResource(android.R.drawable.btn_star_big_off)
            }
        }

        // Gérer le clic sur la CardView
        val countryCardView = view.findViewById<CardView>(R.id.country_view_cardview)
        countryCardView.setOnClickListener {
            val intent = Intent(it.context, DetailCountryActivity::class.java)
            it.context.startActivity(intent)
        }
    }
}

@GlideModule
class MyAppGlideModule : AppGlideModule()
