package fr.epf.min1.androidsearchcountryapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import fr.epf.mm.gestionclient.model.Country

class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)

interface CountryItemClickListener {
    fun onCountryItemClicked(countryName: String)
}

class CountryAdapter(val countries: List<Country>, private val clickListener: CountryItemClickListener?=null) : RecyclerView.Adapter<CountryViewHolder>() {

    private lateinit var context: Context
    private lateinit var sharedPreferences: SharedPreferences

    private val favoriteChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                val countryName = intent.getStringExtra("countryName") ?: return
                val isFavorite = intent.getBooleanExtra("isFavorite", false)
                updateFavoriteStatus(countryName, isFavorite)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        context = parent.context
        sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        LocalBroadcastManager.getInstance(context).registerReceiver(favoriteChangedReceiver, IntentFilter("fr.epf.min1.androidsearchcountryapp.FAVORITE_CHANGED"))
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        val view = holder.itemView
        val countryNameTextView = view.findViewById<TextView>(R.id.countryNameTextView)
        countryNameTextView.text = country.name.common

        val countryCapitalTextView = view.findViewById<TextView>(R.id.countryCapitalTextView)
        countryCapitalTextView.text = country.capital.get(0)
        val countryFlagImageView = view.findViewById<ImageView>(R.id.countryFlagImageView)

        Glide.with(holder.itemView.context)
            .load(country.flags.png)
            .error(R.drawable.error_image)
            .into(countryFlagImageView)

        val favoriteButton = view.findViewById<ImageButton>(R.id.favoriteButton)
        val isFavorite = sharedPreferences.getBoolean(country.name.common, false)
        updateFavoriteButton(favoriteButton, isFavorite)

        favoriteButton.setOnClickListener {
            val newFavoriteState = !isFavorite
            sharedPreferences.edit().putBoolean(country.name.common, newFavoriteState).apply()
            updateFavoriteButton(favoriteButton, newFavoriteState)
            sendFavoriteChangedBroadcast(country.name.common, newFavoriteState)
        }


        val countryCardView = view.findViewById<CardView>(R.id.country_view_cardview)
        countryCardView.setOnClickListener {
            clickListener?.onCountryItemClicked(country.name.common)
            val intent = Intent(it.context, DetailCountryFragment::class.java)
            intent.putExtra("countryNameFavorite", country.name)
            intent.putExtra("country",country)
            it.context.startActivity(intent)
        }
    }

    private fun updateFavoriteButton(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            button.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }

    private fun sendFavoriteChangedBroadcast(countryName: String, isFavorite: Boolean) {
        val intent = Intent("fr.epf.min1.androidsearchcountryapp.FAVORITE_CHANGED")
        intent.putExtra("countryName", countryName)
        intent.putExtra("isFavorite", isFavorite)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    private fun updateFavoriteStatus(countryName: String, isFavorite: Boolean) {
        val position = countries.indexOfFirst { it.name.common == countryName }
        if (position != -1) {
            notifyItemChanged(position)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        LocalBroadcastManager.getInstance(context).unregisterReceiver(favoriteChangedReceiver)
    }
}

@GlideModule
class MyAppGlideModule : AppGlideModule()
