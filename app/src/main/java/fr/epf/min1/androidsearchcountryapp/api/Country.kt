package fr.epf.mm.gestionclient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val alpha3Code: String,
    val callingCodes: List<String>,
    val capital: String,
    val subregion: String,
    val region: String,
    val population: Int,
    val latlng: List<Double>,
    val area: Double?,
    val gini: Double?,
    val timezones: List<String>,
    val borders: List<String>,
    val nativeName: String,
    val numericCode: String,
    val flags: Flags,
    val currencies: List<Currency>,
    val languages: List<Language>,
    val independent: Boolean
) : Parcelable {

    @Parcelize
    data class Flags(
        val png: String
    ) : Parcelable

    @Parcelize
    data class Currency(
        val name: String,
        val symbol: String
    ) : Parcelable

    @Parcelize
    data class Language(
        val name: String,
    ) : Parcelable

    @Parcelize
    data class RegionalBloc(
        val acronym: String,
        val name: String
    ) : Parcelable
}
