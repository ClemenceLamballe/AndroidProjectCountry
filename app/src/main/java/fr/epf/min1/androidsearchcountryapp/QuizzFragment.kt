import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import fr.epf.min1.androidsearchcountryapp.Question
import fr.epf.min1.androidsearchcountryapp.R
import fr.epf.mm.gestionclient.model.Country

class QuizzFragment : Fragment() {

    private lateinit var country: Country // Assurez-vous d'initialiser cet objet avec les informations du pays
    private lateinit var questions: List<String>
    private var questionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quizz, container, false)

        country = Country(name = Country.Name(common = "France", official = "French Republic"), independent = true, status = "Independent state", unMember = true, currencies = mapOf("EUR" to Country.Currency(name = "Euro", symbol = "€")), idd = Country.Idd(root = "+33", suffixes = listOf("")), capital = listOf("Paris"), altSpellings = listOf("FR", "French Republic", "République française"), region = "Europe", subregion = "Western Europe", languages = mapOf("fra" to "French"), latlng = listOf(46.0, 2.0), landlocked = false, borders = listOf("BEL", "DEU", "ITA", "LUX", "MCO", "ESP", "CHE", "AND"), flag = "https://restcountries.com/data/fra.svg", maps = Country.Maps(googleMaps = "https://maps.google.com/maps?q=France", openStreetMaps = "https://www.openstreetmap.org/relation/2202160"), population = 66710000, continents = listOf("Europe"), flags = Country.Flags(png = "https://restcountries.com/data/fra.png", svg = "https://restcountries.com/data/fra.svg", alt = null))


        // Récupérer les vues du layout
        val questionTextView = view.findViewById<TextView>(R.id.questionTextView)
        val answerEditText = view.findViewById<EditText>(R.id.answerEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)

        // Initialiser les questions
        val questions = listOf(
            Question("What is the official name of this country?", country.name.official),
            Question("Is this country independent?", country.independent.toString()),
            Question("What is the political status of this country?", country.status),
            Question("Is this country a member of the United Nations?", country.unMember.toString()),
            Question("What is the currency used in this country?", country.currencies.values.joinToString(", ") { it.name }),
            Question("What is the international dialing code of this country?", country.idd.root),
            Question("What is the capital of this country?", country.capital.joinToString(", ")),
            Question("What are the alternative names used for this country?", country.altSpellings.joinToString(", ")),
            Question("In which region is this country located?", country.region),
            Question("In which subregion is this country located?", country.subregion),
            Question("Which languages are spoken in this country?", country.languages.values.joinToString(", ")),
            Question("What are the geographical coordinates of this country?", country.latlng.joinToString(", ")),
            Question("Is this country landlocked?", country.landlocked.toString()),
            Question("Which countries border this country?", country.borders.joinToString(", ")),
            Question("What is the population of this country?", country.population.toString()),
            Question("On which continents is this country located?", country.continents.joinToString(", "))
        )

        country?.let {
            view.findViewById<TextView>(R.id.questionTextView).text = questions.get(1).title
        }


        /*// Afficher la première question
        showQuestion(questionIndex)

        // Gérer le clic sur le bouton de soumission
        submitButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString()
            val correctAnswer = getCorrectAnswer(questionIndex)
            val isCorrect = checkAnswer(userAnswer, correctAnswer)
            showAnswerFeedback(isCorrect)

            // Passer à la question suivante ou afficher le score final
            questionIndex++
            if (questionIndex < questions.size) {
                showQuestion(questionIndex)
            } else {
                showFinalScore()
            }
        }*/

        return view
    }

    private fun showQuestion(index: Int) {
        val questionTextView = requireView().findViewById<TextView>(R.id.questionTextView)
        val answerEditText = requireView().findViewById<EditText>(R.id.answerEditText)

        questionTextView.text = questions[index]
        answerEditText.text.clear()
    }

    private fun getCorrectAnswer(index: Int): String {
        // Logique pour obtenir la réponse correcte en fonction de l'index de la question
        return when (index) {
            0 -> country.capital.joinToString(", ") // Si la capitale est une liste de valeurs
            1 -> country.population.toString()
            // Ajoutez d'autres cas pour d'autres questions
            else -> ""
        }
    }

    private fun checkAnswer(userAnswer: String, correctAnswer: String): Boolean {
        // Comparaison des réponses de l'utilisateur et de la réponse correcte
        return userAnswer.equals(correctAnswer, ignoreCase = true)
    }

    private fun showAnswerFeedback(isCorrect: Boolean) {
        val message = if (isCorrect) "Bonne réponse !" else "Mauvaise réponse..."
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showFinalScore() {
        // Afficher le score final de l'utilisateur à la fin du quiz
        // Vous pouvez compter le nombre de réponses correctes et l'afficher
    }
}

