package fr.epf.min1.androidsearchcountryapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class DetailCountryActivity : AppCompatActivity() {

    private lateinit var favoriteButtonDetail: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private var isFavorite: Boolean = false
    private lateinit var countryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_coutry)

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
