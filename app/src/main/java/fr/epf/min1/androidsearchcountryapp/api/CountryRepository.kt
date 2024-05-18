class CountryRepository(private val countryService: CountryService) {
    suspend fun searchCountries(query: String): List<Country> {
        return countryService.searchCountries(query)
    }
}