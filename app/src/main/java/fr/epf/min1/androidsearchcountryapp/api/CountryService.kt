import retrofit2.http.GET
import retrofit2.http.Query

interface CountryService {
    @GET("v2/name/")
    suspend fun searchCountries(@Query("search") query: String): List<Country>
}