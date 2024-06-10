import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.epf.min1.androidsearchcountryapp.Question
import fr.epf.min1.androidsearchcountryapp.R
import fr.epf.min1.androidsearchcountryapp.data.FavoriteCountriesRepository
import fr.epf.mm.gestionclient.model.Country

class QuizzFragment : Fragment() {

    private lateinit var country: Country
    private var questionIndex = 0
    private lateinit var questions: List<Question>
    private var score = 0
    private lateinit var countryNameSelected:TextView
    private lateinit var questionTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quizz, container, false)

        FavoriteCountriesRepository.loadFavorites(requireContext())
        val favoriteCountries = FavoriteCountriesRepository.favoriteCountries

        val startQuizzButton: Button = view.findViewById(R.id.StartQuizzButton)
        val changeCountryButton: Button = view.findViewById(R.id.changeCountryButton)
        val questionPhraseTextView: TextView = view.findViewById(R.id.questionPhraseTextView)
        val answerEditText: EditText = view.findViewById(R.id.answerEditText)
        val submitButton: Button = view.findViewById(R.id.submitButton)
        questionTextView= view.findViewById<TextView>(R.id.questionTextView)
        val nameCountryText: TextView = view.findViewById(R.id.CountryNameTextViewQuizz)
        val nextButton: Button = view.findViewById(R.id.nextButton)
        val feedbackTextView: TextView = view.findViewById(R.id.feedbackTextView)
        val scoreTextView: TextView = view.findViewById<TextView>(R.id.ScoreTextViewQuizz)
        val resultTextView: TextView = view.findViewById<TextView>(R.id.ResultTextViewQuizz)
        val errorTextView: TextView = view.findViewById<TextView>(R.id.errorTextViewQuizz)
        val introTextView: TextView = view.findViewById<TextView>(R.id.IntroTextViewQuizz)
        val submitAndNextButtonLayout: LinearLayout = view.findViewById<LinearLayout>(R.id.submitAndNextButton)
        countryNameSelected=view.findViewById(R.id.CountryNameTextViewQuizz)
        questionTextView.text = "Question 1:"


        submitButton.isEnabled = false
        nextButton.isEnabled = false

        startQuizzButton.setOnClickListener {
            if (favoriteCountries.isEmpty()) {
                errorTextView.visibility = View.VISIBLE
            } else {
                startQuizzButton.visibility = View.GONE
                introTextView.visibility = View.GONE
                nameCountryText.visibility = View.VISIBLE
                questionTextView.visibility = View.VISIBLE
                answerEditText.visibility = View.VISIBLE
                submitAndNextButtonLayout.visibility = View.VISIBLE
                changeCountryButton.visibility = View.VISIBLE

                initializeQuiz(favoriteCountries, questionPhraseTextView, answerEditText, feedbackTextView, submitButton, nextButton, scoreTextView)

                changeCountryButton.setOnClickListener {
                    initializeQuiz(favoriteCountries, questionPhraseTextView, answerEditText, feedbackTextView, submitButton, nextButton, scoreTextView)
                }
            }
        }

        submitButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString().trim()
            val currentQuestion = questions[questionIndex]
            val correctAnswer = currentQuestion.answer
            val isCorrect = if (currentQuestion.numericAnswer != null) {
                isApproximateCorrect(userAnswer, currentQuestion.numericAnswer)
            } else {
                userAnswer.equals(correctAnswer, ignoreCase = true)
            }

            if (isCorrect) {
                if(currentQuestion.numericAnswer != null) feedbackTextView.text = "Correct! The exact answer is $correctAnswer."
                else feedbackTextView.text = "Correct!"
                score++
                scoreTextView.text = "${score}/${questions.size}"
            } else {
                feedbackTextView.text = "Wrong! The correct answer is $correctAnswer."
            }
            submitButton.isEnabled = false
            nextButton.isEnabled = true
        }

        nextButton.setOnClickListener {
            questionIndex++
            questionTextView.text = "Question ${questionIndex+ 1} :"
            if (questionIndex >= questions.size) {
                displayFinalScore(feedbackTextView, nameCountryText, answerEditText, submitAndNextButtonLayout, changeCountryButton, resultTextView, scoreTextView, questionPhraseTextView)
            } else {
                displayQuestion(questionPhraseTextView, answerEditText, feedbackTextView)
                submitButton.isEnabled = true
                nextButton.isEnabled = false
            }
        }

        return view
    }

    private fun initializeQuiz(favoriteCountries: List<Country>, questionPhraseTextView: TextView, answerEditText: EditText, feedbackTextView: TextView, submitButton: Button, nextButton: Button, scoreTextView: TextView) {
        score = 0
        country = favoriteCountries.random()
        countryNameSelected.text = "About ${country.name}..."
        questions = listOf(
            Question("Is ${country.name} an independent country?", booleanToYesNo(country.independent)),
            Question("What is the currency used in ${country.name}?", country.currencies.joinToString(", ") { it.name }),
            Question("What is the capital of ${country.name}?", country.capital),
            Question("In which region is ${country.name} located?", country.region),
            Question("In which subregion is ${country.name} located?", country.subregion),
            Question("Which languages are spoken in ${country.name}?", country.languages.joinToString(", ") { it.name }),
            Question("What are the geographical coordinates of ${country.name}?", "${country.latlng[0]}° : ${country.latlng[1]}°"),
            Question("What is the population of ${country.name}?",
                "${country.population} people",
                country.population),
            Question("What is the area of ${country.name}?",
                "${country.area?.toInt()} km2" ?: "Unknown",
                country.area?.toInt()),
            Question("What is the Gini index of ${country.name}?",
                country.gini?.let { "%.1f".format(it)} ?: "Unknown",
                country.gini?.toInt()),
            Question("What is the native name of ${country.name}?", country.nativeName),
            Question("What is the numeric code of ${country.name}?", country.numericCode),
            Question("What is the calling code of ${country.name}?", country.callingCodes.joinToString(", "))
        )
        questionIndex = 0
        displayQuestion(questionPhraseTextView, answerEditText, feedbackTextView)
        submitButton.isEnabled = true
        nextButton.isEnabled = false
        scoreTextView.text = "${score}/${questions.size}"
    }

    private fun displayQuestion(questionPhraseTextView: TextView, answerEditText: EditText, feedbackTextView: TextView) {
        questionPhraseTextView.text = questions[questionIndex].title
        answerEditText.text.clear()
        feedbackTextView.text = ""
    }

    private fun displayFinalScore(feedbackTextView: TextView, nameCountryText: TextView, answerEditText: EditText, submitAndNextButtonLayout: LinearLayout, changeCountryButton: Button, resultTextView: TextView, scoreTextView: TextView, questionPhraseTextView: TextView) {
        val message = when {
            score == questions.size -> "Excellent! You got all questions right."
            score > questions.size / 2 -> "Good job! You scored $score out of ${questions.size}."
            else -> "You scored $score out of ${questions.size}. Better luck next time!"
        }
        feedbackTextView.text = message
        nameCountryText.visibility = View.GONE
        questionTextView.visibility = View.GONE
        answerEditText.visibility = View.GONE
        submitAndNextButtonLayout.visibility = View.GONE
        changeCountryButton.visibility = View.GONE
        resultTextView.visibility = View.VISIBLE
        scoreTextView.visibility = View.GONE
        questionPhraseTextView.text = "Result of the Quizz on ${country.name}!"
        resultTextView.text = "${score}/${questions.size}"
    }

    private fun isApproximateCorrect(userAnswer: String, correctAnswer: Int?, tolerance: Double = 0.1): Boolean {
        val userValue = userAnswer.toDoubleOrNull()
        return if (userValue != null && correctAnswer != null) {
            Math.abs(userValue - correctAnswer) / correctAnswer <= tolerance
        } else {
            false
        }
    }

    fun booleanToYesNo(booleanAnswer: Boolean) = if (booleanAnswer) "yes" else "no"
}
