package fr.epf.mm.gestionclient.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: Name,
    val independent: Boolean,
    val status: String,
    val unMember: Boolean,
    val currencies: Map<String, Currency>,
    val idd: Idd,
    val capital: List<String>,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String,
    val languages: Map<String, String>,
    val latlng: List<Double>,
    val landlocked: Boolean,
    val borders: List<String>,
    val flag: String,
    val maps: Maps,
    val population: Int,
    val continents: List<String>,
    val flags: Flags,
) : Parcelable {

    @Parcelize
    data class Name(
        val common: String,
        val official: String,
    ) : Parcelable

    @Parcelize
    data class Currency(
        val name: String,
        val symbol: String
    ) : Parcelable

    @Parcelize
    data class Idd(
        val root: String,
        val suffixes: List<String>
    ) : Parcelable


    @Parcelize
    data class Maps(
        val googleMaps: String,
        val openStreetMaps: String
    ) : Parcelable

    @Parcelize
    data class Flags(
        val png: String,
        val svg: String,
        val alt: String?
    ) : Parcelable
}
