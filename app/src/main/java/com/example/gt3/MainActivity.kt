package com.example.gt3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.gt3.ui.theme.Gt3Theme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gt3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    var targetNumber by remember { mutableStateOf((1..1000).random()) }
    var guessCount by remember { mutableStateOf(0) }
    var guessTextFieldValue by remember { mutableStateOf("") }
    var feedbackText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var isGuessButtonEnabled by remember { mutableStateOf(true) }
    var isNewGameButtonEnabled by remember { mutableStateOf(true) }

    fun newGame() {
        isGuessButtonEnabled = true
        guessTextFieldValue = ""
        targetNumber = (1..1000).random()
        guessCount = 0
        resultText = ""
        feedbackText = ""
    }

    fun guessButtonClick() {
        val guess = guessTextFieldValue.toIntOrNull()
        if (guess == null || guess < 1 || guess > 1000) {
            guessTextFieldValue = ""
            feedbackText = "Please enter a valid number."
        } else {
            guessCount++
            if (guess == targetNumber) {
                guessCount-- //
                isGuessButtonEnabled = false
                resultText = if (guessCount == 1) {
                    "Congratulations! You won in $guessCount guess!"
                } else {
                    "Congratulations! You won in $guessCount guesses!"
                }
            } else {
                feedbackText = if (guess < targetNumber) {
                    "Hint: $guess is too low."
                } else {
                    "Hint: $guess is too high."
                }
            }
            guessTextFieldValue = ""
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.instructions),
            modifier = Modifier
                .padding(bottom = 16.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Divider(
            color = colorResource(id = R.color.greyLight),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        if (resultText.isNotBlank()) {
            Text(
                text = resultText,
                color = colorResource(id = R.color.yellow),
                modifier = Modifier.padding(16.dp),
                fontSize = 19.sp
            )
        } else if (feedbackText == stringResource(id = R.string.enter_valid_number)) {
            Text(
                text = feedbackText,
                color = Color.Red,
                modifier = Modifier.padding(16.dp),
                fontSize = 19.sp
            )
        } else {
            Text(
                text = feedbackText,
                color = colorResource(id = R.color.colorPrimaryDark),
                modifier = Modifier.padding(16.dp),
                fontSize = 19.sp
            )
        }
        EditNumberField(
            value = guessTextFieldValue,
            onValueChange = { guessTextFieldValue = it },
            isGuessButtonEnabled = isGuessButtonEnabled,
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            Text(
//                text = stringResource(id = R.string.guess_count, guessCount),
//                modifier = Modifier.padding(20.dp),
//                color = colorResource(id = R.color.yellow),
//                fontSize = 20.sp,
//            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                enabled = isGuessButtonEnabled,
                onClick = { guessButtonClick() },
                modifier = Modifier
                    .padding(8.dp)
                    .width(250.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.yellowLight),
                    contentColor = colorResource(id = R.color.white)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.guess_button),
                    fontSize = 18.sp
                )
            }
            Button(
                enabled = isNewGameButtonEnabled,
                onClick = { newGame() },
                modifier = Modifier
                    .padding(8.dp)
                    .width(250.dp)
                    .height(45.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.new_game_button),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    isGuessButtonEnabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = R.string.guess_label)) },
        enabled = isGuessButtonEnabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        modifier = Modifier
            .width(300.dp)
            .padding(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Gt3Theme() {
        GameScreen()
    }
}
