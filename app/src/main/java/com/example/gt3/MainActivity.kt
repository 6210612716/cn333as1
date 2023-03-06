package com.example.gt3
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.view.inputmethod.EditorInfo
//import com.example.gt3.R
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var targetNumber = 0
    private var guessCount = 0
    private lateinit var guessEditText: EditText
    private lateinit var guessButton: Button
    private lateinit var newGameButton: Button
    private lateinit var feedbackTextView: TextView
    private lateinit var guessCountTextView: TextView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guessEditText = findViewById(R.id.guessEditText)
        guessButton = findViewById(R.id.guessButton)
        newGameButton = findViewById(R.id.newGameButton)
        feedbackTextView = findViewById(R.id.feedbackTextView)
        guessCountTextView = findViewById(R.id.guessCountTextView)
        resultTextView = findViewById(R.id.resultTextView)

        guessEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                guessButton.performClick()
                true
            } else {
                false
            }
        }

        newGame()
        guessButton.setOnClickListener {
            val guessIsBlank = guessEditText.text.toString()
            if (guessIsBlank.isBlank()) {
                guessEditText.error = "Don't leave it blank."
                return@setOnClickListener
            }
            val guessValid = guessIsBlank.toIntOrNull()
            if (guessValid == null || guessValid < 1 || guessValid > 1000) {
                guessEditText.text.clear()
                guessEditText.error = "Please enter a valid number."
                return@setOnClickListener
            }
            val guess = guessEditText.text.toString().toInt()
            feedbackTextView.text = ""
            guessCount++
            guessCountTextView.text = "Guesses: $guessCount"
            if (guess == targetNumber) {
                endGame(true)
                guessButton.isEnabled = false
                guessEditText.isEnabled = false

                guessEditText.hint = ""
                guessButton.visibility = Button.GONE
                newGameButton.text = "Play again"

            } else if (guessCount < 1000) {
                if (guess < targetNumber) {
                    feedbackTextView.text = "Hint: Your guess is too low."
                } else {
                    feedbackTextView.text = "Hint: Your guess is too high."
                }
            }
            guessEditText.text.clear()
        }

        newGameButton.setOnClickListener {
            newGame()
            guessButton.isEnabled = true
            guessEditText.isEnabled = true
        }
    }

    private fun newGame() {
        targetNumber = (1..1000).random()
        guessCount = 0
        guessCountTextView.text = "Guesses: $guessCount"
        resultTextView.text = ""
        feedbackTextView.text = ""

        guessEditText.hint = "Enter your guess"
        guessButton.visibility = Button.VISIBLE
        newGameButton.text = "New Game"
    }

    private fun endGame(win: Boolean) {
        if (win) {
            resultTextView.text = "Congratulation!\nYou won in ${guessCount} guesses!"
        }
    }
}
