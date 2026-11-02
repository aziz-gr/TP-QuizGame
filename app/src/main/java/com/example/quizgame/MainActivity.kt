package com.example.quizgame

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions = listOf(
        Question(
            questionText = "Quelle est cette forme géométrique ayant trois côtés ?",
            imageResId = R.drawable.ic_shape_triangle,
            options = listOf("Triangle", "Carré", "Cercle"),
            correctAnswerIndex = 0,
            contentDescription = "Un triangle bleu à trois côtés",
        ),
        Question(
            questionText = "Combien de côtés égaux possède cette figure ?",
            imageResId = R.drawable.ic_shape_square,
            options = listOf("3 côtés", "4 côtés", "5 côtés"),
            correctAnswerIndex = 1,
            contentDescription = "Un carré vert à quatre côtés égaux",
        ),
        Question(
            questionText = "Quelle figure géométrique n'a aucun sommet ni côté droit ?",
            imageResId = R.drawable.ic_shape_circle,
            options = listOf("Rectangle", "Étoile", "Cercle"),
            correctAnswerIndex = 2,
            contentDescription = "Un cercle rouge parfait",
        ),
        Question(
            questionText = "Combien de branches possède cette étoile ?",
            imageResId = R.drawable.ic_shape_star,
            options = listOf("5 branches", "6 branches", "4 branches"),
            correctAnswerIndex = 0,
            contentDescription = "Une étoile jaune à cinq branches",
        ),
        Question(
            questionText = "Quelle est cette forme géométrique aux côtés opposés égaux ?",
            imageResId = R.drawable.ic_shape_rectangle,
            options = listOf("Cercle", "Rectangle", "Triangle"),
            correctAnswerIndex = 1,
            contentDescription = "Un rectangle violet",
        )
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var hasAnsweredCurrentQuestion = false
    private var selectedChoiceIndex = -1
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0)
            score = savedInstanceState.getInt(KEY_SCORE, 0)
            hasAnsweredCurrentQuestion = savedInstanceState.getBoolean(KEY_HAS_ANSWERED, false)
            selectedChoiceIndex = savedInstanceState.getInt(KEY_SELECTED_CHOICE, -1)
            isGameOver = savedInstanceState.getBoolean(KEY_GAME_OVER, false)
        }

        if (isGameOver) {
            showFinalResults()
        } else {
            displayQuestion(currentQuestionIndex)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_INDEX, currentQuestionIndex)
        outState.putInt(KEY_SCORE, score)
        outState.putBoolean(KEY_HAS_ANSWERED, hasAnsweredCurrentQuestion)
        outState.putInt(KEY_SELECTED_CHOICE, selectedChoiceIndex)
        outState.putBoolean(KEY_GAME_OVER, isGameOver)
    }

    private fun setupClickListeners() {
        binding.btnChoix1.setOnClickListener { onChoiceSelected(0) }
        binding.btnChoix2.setOnClickListener { onChoiceSelected(1) }
        binding.btnChoix3.setOnClickListener { onChoiceSelected(2) }

        binding.btnNext.setOnClickListener {
            if (currentQuestionIndex < (questions.size - 1)) {
                currentQuestionIndex++
                hasAnsweredCurrentQuestion = false
                selectedChoiceIndex = -1
                displayQuestion(currentQuestionIndex)
            } else {
                showFinalResults()
            }
        }

        binding.btnRestart.setOnClickListener {
            restartGame()
        }
    }

    private fun displayQuestion(index: Int) {
        val question = questions[index]

        // Update top bar score and progress bar
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)
        val progress = if (hasAnsweredCurrentQuestion) index + 1 else index
        binding.progressQuiz.progress = progress

        // Question count and content
        binding.txtQuestionNum.text = getString(R.string.question_counter_format, index + 1, questions.size)
        binding.imgQuestion.setImageResource(question.imageResId)
        binding.imgQuestion.contentDescription = question.contentDescription
        binding.txtQuestion.text = question.questionText

        // Choice button texts
        binding.btnChoix1.text = question.options[0]
        binding.btnChoix2.text = question.options[1]
        binding.btnChoix3.text = question.options[2]

        // Update button text depending on whether it's the last question
        if (index == questions.size - 1) {
            binding.btnNext.text = getString(R.string.btn_see_results)
        } else {
            binding.btnNext.text = getString(R.string.btn_next_question)
        }

        if (hasAnsweredCurrentQuestion) {
            setChoicesEnabled(enabled = false)
            binding.btnNext.isEnabled = true
            showAnswerFeedback(selectedChoiceIndex)
        } else {
            setChoicesEnabled(enabled = true)
            binding.btnNext.isEnabled = false
            binding.txtFeedback.visibility = View.GONE
        }

        binding.layoutQuiz.visibility = View.VISIBLE
        binding.layoutResult.visibility = View.GONE
    }

    private fun onChoiceSelected(choiceIndex: Int) {
        if (hasAnsweredCurrentQuestion) return

        hasAnsweredCurrentQuestion = true
        selectedChoiceIndex = choiceIndex

        val currentQuestion = questions[currentQuestionIndex]
        val isCorrect = choiceIndex == currentQuestion.correctAnswerIndex

        if (isCorrect) {
            score++
        }

        // Update score & progress bar
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)
        binding.progressQuiz.progress = currentQuestionIndex + 1

        // Disable choices to prevent multiple attempts
        setChoicesEnabled(enabled = false)

        // Show feedback message
        showAnswerFeedback(choiceIndex)

        // Enable next button
        binding.btnNext.isEnabled = true
    }

    private fun showAnswerFeedback(choiceIndex: Int) {
        val currentQuestion = questions[currentQuestionIndex]
        val isCorrect = choiceIndex == currentQuestion.correctAnswerIndex

        if (isCorrect) {
            binding.txtFeedback.text = getString(R.string.feedback_correct)
            binding.txtFeedback.setTextColor(ContextCompat.getColor(this, R.color.color_correct))
        } else {
            val correctAnswerText = currentQuestion.options[currentQuestion.correctAnswerIndex]
            binding.txtFeedback.text = getString(R.string.feedback_incorrect, correctAnswerText)
            binding.txtFeedback.setTextColor(ContextCompat.getColor(this, R.color.color_incorrect))
        }
        binding.txtFeedback.visibility = View.VISIBLE
    }

    private fun setChoicesEnabled(enabled: Boolean) {
        binding.btnChoix1.isEnabled = enabled
        binding.btnChoix2.isEnabled = enabled
        binding.btnChoix3.isEnabled = enabled
    }

    private fun showFinalResults() {
        isGameOver = true

        binding.progressQuiz.progress = questions.size
        binding.txtScore.text = getString(R.string.score_format, score, questions.size)

        binding.layoutQuiz.visibility = View.GONE
        binding.layoutResult.visibility = View.VISIBLE

        binding.txtFinalScore.text = getString(R.string.final_score_format, score, questions.size)

        val finalMessage = when (score) {
            questions.size -> getString(R.string.final_feedback_perfect)
            in 3 until questions.size -> getString(R.string.final_feedback_good)
            else -> getString(R.string.final_feedback_try_again)
        }
        binding.txtFinalMessage.text = finalMessage
    }

    private fun restartGame() {
        currentQuestionIndex = 0
        score = 0
        hasAnsweredCurrentQuestion = false
        selectedChoiceIndex = -1
        isGameOver = false

        displayQuestion(0)
    }

    companion object {
        private const val KEY_CURRENT_INDEX = "KEY_CURRENT_INDEX"
        private const val KEY_SCORE = "KEY_SCORE"
        private const val KEY_HAS_ANSWERED = "KEY_HAS_ANSWERED"
        private const val KEY_SELECTED_CHOICE = "KEY_SELECTED_CHOICE"
        private const val KEY_GAME_OVER = "KEY_GAME_OVER"
    }
}