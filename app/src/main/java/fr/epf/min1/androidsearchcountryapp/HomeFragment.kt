package fr.epf.min1.androidsearchcountryapp

import QuizzFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragement_home, container, false)

        val searchField = view.findViewById<TextView>(R.id.searchField)
        val buttonsearchByCountry = view.findViewById<Button>(R.id.searchByCountryButton)
        val buttonsearchByCapital = view.findViewById<Button>(R.id.searchByCapitalButton)
        val buttonViewFavorites = view.findViewById<Button>(R.id.viewFavoritesButton)
        val buttonQuizz = view.findViewById<Button>(R.id.QuizzButton)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)



        buttonsearchByCountry.setOnClickListener {
            val searchTerm = searchField.text.toString()
            if (searchTerm.isNotEmpty()) {

                val fragment = CountryListFragment()
                val bundle = Bundle()
                bundle.putString("searchTerm", searchTerm)
                bundle.putString("searchByType", "country")
                fragment.arguments = bundle
                //bottomNavigationView.selectedItemId = R.id.nav_search//MARCHE PAS

                // Utiliser le gestionnaire de fragments pour remplacer le fragment actuel par le nouveau
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            } else {
                Toast.makeText(requireContext(), "Veuillez saisir un terme de recherche", Toast.LENGTH_SHORT).show()
            }
        }

        buttonsearchByCapital.setOnClickListener(){
            val searchTerm = searchField.text.toString()
            if (searchTerm.isNotEmpty()) {
                val fragment = CountryListFragment()
                val bundle = Bundle()
                bundle.putString("searchTerm", searchTerm)
                bundle.putString("searchByType", "capital")
                fragment.arguments = bundle


                //bottomNavigationView.selectedItemId = R.id.nav_search

                // Utiliser le gestionnaire de fragments pour remplacer le fragment actuel par le nouveau
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            } else {
                Toast.makeText(requireContext(), "Veuillez saisir un terme de recherche", Toast.LENGTH_SHORT).show()
            }
        }

        // Gérer le clic sur le bouton pour afficher les favoris
        buttonViewFavorites.setOnClickListener {
            Log.d("MyTag", "Vers Favoris")
            val fragment = FavoriteCountriesFragment()
            //bottomNavigationView.selectedItemId = R.id.nav_favorites

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        buttonQuizz.setOnClickListener {
            Log.d("MyTag", "Vers Quizz")
            val fragment = QuizzFragment()

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return view

    }

}

