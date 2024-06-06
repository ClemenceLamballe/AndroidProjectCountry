package fr.epf.min1.androidsearchcountryapp.api

import fr.epf.mm.gestionclient.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("all")
    suspend fun getCountries(): Response<List<Country>>
}