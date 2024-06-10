package fr.epf.min1.androidsearchcountryapp

import QuizzFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        FavoriteCountriesRepository.loadFavorites(this)



        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        bottomNavigationView.selectedItemId = R.id.nav_home//MARCHE PAS


        // Gérer les clics sur les éléments de la barre de navigation
        bottomNavigationView.setOnNavigationItemSelectedListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.nav_favorites -> {
                    replaceFragment(FavoriteCountriesFragment())
                    true
                }
                R.id.nav_quiz -> {
                    replaceFragment(QuizzFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        FavoriteCountriesRepository.saveFavorites(this)
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}