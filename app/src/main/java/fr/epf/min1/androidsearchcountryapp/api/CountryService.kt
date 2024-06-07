package fr.epf.min1.androidsearchcountryapp.api

import fr.epf.mm.gestionclient.model.Country
import retrofit2.Response
import retrofit2.http.GET
import android.content.Intent
import retrofit2.http.Path
import retrofit2.http.Query

interface CountryService {

    @GET("all")
    suspend fun getCountries(): Response<List<Country>>

    @GET("name/{searchTerm}")
    suspend fun searchCountryByName(@Path("searchTerm") searchTerm: String): Response<List<Country>>

    @GET("capital/{searchTerm}")
    suspend fun searchCountryByCapital(@Path("searchTerm") searchTerm: String): Response<List<Country>>

}