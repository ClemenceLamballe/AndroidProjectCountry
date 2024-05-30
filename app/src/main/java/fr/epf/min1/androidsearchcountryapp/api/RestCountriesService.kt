package fr.epf.min1.androidsearchcountryapp.api

import Country
import retrofit2.http.GET

interface RestCountriesService {
    @GET("all")
    suspend fun getAllCountries(): List<Country>
}