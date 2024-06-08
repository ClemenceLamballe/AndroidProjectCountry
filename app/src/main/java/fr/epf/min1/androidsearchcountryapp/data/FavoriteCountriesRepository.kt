package fr.epf.min1.androidsearchcountryapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.epf.mm.gestionclient.model.Country

object FavoriteCountriesRepository {
    val favoriteCountries: MutableList<Country> = mutableListOf()

    fun saveFavorites(context: Context) {
        val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(favoriteCountries)
        editor.putString("favoriteCountries", json)
        editor.apply()
    }

    fun loadFavorites(context: Context) {
        val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("favoriteCountries", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Country>>() {}.type
            favoriteCountries.clear()
            favoriteCountries.addAll(Gson().fromJson(json, type))
        }
    }
}
