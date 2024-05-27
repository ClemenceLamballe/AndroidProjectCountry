package fr.epf.min1.androidsearchcountryapp
import Country
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule


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

        val countryFlagImageView = view.findViewById<ImageView>(R.id.countryFlagImageView)
        Log.d("MyTag", "country: ${countryFlagImageView}")

        //ne marche pas :  les drapeaux
        Glide.with(holder.itemView.context)
            .load(country.flag)
            .into(countryFlagImageView)




        //val cardView = view.findViewById<CardView>(R.id.countryCardView)
        //cardView.setOnClickListener {
            // Gérer le clic sur un pays si nécessaire
        //}
    }
}
