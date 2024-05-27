import retrofit2.http.GET

interface RestCountriesService {
    @GET("all")
    suspend fun getAllCountries(): List<Country>
}