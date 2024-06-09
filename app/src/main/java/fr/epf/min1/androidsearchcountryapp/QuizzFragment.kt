import android.os.Bundle
import android.util.Log
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
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository
import fr.epf.mm.gestionclient.model.Country
import kotlin.random.Random

class QuizzFragment : Fragment() {

    private lateinit var country: Country
    private var questionIndex = 0
    private lateinit var questions: List<Question>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quizz, container, false)

        FavoriteCountriesRepository.loadFavorites(requireContext())
        val favoriteCountries = FavoriteCountriesRepository.favoriteCountries

        val startQuizButton: Button = view.findViewById(R.id.StartQuizzButton)
        val questionPhraseTextView: TextView = view.findViewById(R.id.questionPhraseTextView)
        val answerEditText: EditText = view.findViewById(R.id.answerEditText)
        val submitButton: Button = view.findViewById(R.id.submitButton)

        val nameCountryText: TextView = view.findViewById(R.id.CountryNameTextViewQuizz)

        val nextButton: Button = view.findViewById(R.id.nextButton)
        val feedbackTextView: TextView = view.findViewById(R.id.feedbackTextView)

        submitButton.isEnabled = false
        nextButton.isEnabled = false



        startQuizButton.setOnClickListener {
            if (favoriteCountries.isEmpty()) {
                view.findViewById<TextView>(R.id.errorTextViewQuizz).visibility = View.VISIBLE
            } else {
                country = favoriteCountries.random()
                nameCountryText.text = "How much do you know about ${country.name.common}?"
                questions = listOf(
                    Question("What is the official name of ${country.name.common}?", country.name.official),
                    Question("Is ${country.name.common} independent?", country.independent.toString()),
                    Question("What is the political status of ${country.name.common}?", country.status),
                    Question("Is ${country.name.common} a member of the United Nations?", country.unMember.toString()),
                    Question("What is the currency used in ${country.name.common}?", country.currencies.values.joinToString(", ") { it.name }),
                    Question("What is the international dialing code of ${country.name.common}?", country.idd.root),
                    Question("What is the capital of ${country.name.common}?", country.capital.joinToString(", ")),
                    Question("What are the alternative names used for ${country.name.common}?", country.altSpellings.joinToString(", ")),
                    Question("In which region is ${country.name.common} located?", country.region),
                    Question("In which subregion is ${country.name.common} located?", country.subregion),
                    Question("Which languages are spoken in ${country.name.common}?", country.languages.values.joinToString(", ")),
                    Question("What are the geographical coordinates of ${country.name.common}?", country.latlng.joinToString(", ")),
                    Question("Is ${country.name.common} landlocked?", country.landlocked.toString()),
                    Question("Which countries border ${country.name.common}?", country.borders.joinToString(", ")),
                    Question("What is the population of ${country.name.common}?", country.population.toString()),
                    Question("On which continents is ${country.name.common} located?", country.continents.joinToString(", "))
                )
                questionIndex = 0//Random.nextInt(0, questions.size)//changer pour
                questionIndex = 0
                displayQuestion(questionPhraseTextView, answerEditText, feedbackTextView)
                submitButton.isEnabled = true
                nextButton.isEnabled = false

            }

        }

        submitButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString().trim()
            val correctAnswer = questions[questionIndex].answer
            if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
                feedbackTextView.text = "Correct!"
            } else {
                feedbackTextView.text = "Wrong! The correct answer is $correctAnswer."
            }
            submitButton.isEnabled = false
            nextButton.isEnabled = true
        }

        nextButton.setOnClickListener {
            questionIndex = (questionIndex + 1) % questions.size
            displayQuestion(questionPhraseTextView, answerEditText, feedbackTextView)
            submitButton.isEnabled = true
            nextButton.isEnabled = false
        }




        return view
    }


    private fun displayQuestion(
        questionPhraseTextView: TextView,
        answerEditText: EditText,
        feedbackTextView: TextView,
    ) {
        questionPhraseTextView.text = questions[questionIndex].title
        answerEditText.text.clear()
        feedbackTextView.text = ""
    }



}

