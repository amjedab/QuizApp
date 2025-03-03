package com.cricut.quizapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cricut.quizapp.model.Question
import com.cricut.quizapp.model.QuestionType
import com.cricut.quizapp.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel()) {

  val currentIndex by viewModel.currentIndex.collectAsState()
  val currentQuestion = viewModel.currentQuestion.collectAsState()
  val userResponses by viewModel.userResponses.collectAsState()

  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(text = "Question ${currentIndex + 1}", style = MaterialTheme.typography.titleLarge)

    Spacer(modifier = Modifier.height(16.dp))

    QuizQuestion(
      question = currentQuestion.value,
      userResponse = userResponses[currentIndex],
      onResponseChange = { response -> viewModel.setUserResponse(response) }
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Button(
        onClick = { viewModel.previousQuestion() },
        enabled = currentIndex > 0
      ) {
        Text("Previous")
      }
      Button(
        onClick = { viewModel.nextQuestion() },
        enabled = currentIndex < currentQuestion.value.options.size - 1
      ) {
        Text("Next")
      }
    }
  }

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuizQuestion(
  question: Question,
  userResponse: Any?,
  onResponseChange: (Any?) -> Unit
) {

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight(),
  ) {
    Text(
      text = question.title,
      style = MaterialTheme.typography.titleMedium,
      modifier = Modifier.padding(bottom = 8.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    when (question.type) {
      QuestionType.TRUE_FALSE -> {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(vertical = 4.dp)
        ) {
          RadioButton(
            selected = userResponse == true,
            onClick = { onResponseChange(true) }
          )
          Text(
            "True",
            modifier = Modifier.padding(start = 8.dp)
          )
        }
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(vertical = 4.dp)
        ) {
          RadioButton(
            selected = userResponse == false,
            onClick = { onResponseChange(false) }
          )
          Text(
            "False",
            modifier = Modifier.padding(start = 8.dp)
          )
        }
      }

      QuestionType.SINGLE_CHOICE -> {
        question.options.forEachIndexed { index, option ->
          Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
              selected = userResponse == index,
              onClick = { onResponseChange(index) }
            )
            Text(
              option,
              modifier = Modifier.padding(start = 8.dp)
            )
          }
        }
      }

      QuestionType.MULTIPLE_CHOICE -> {
        val response = userResponse as? Set<Int> ?: setOf()
        question.options.forEachIndexed { index, option ->
          Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
              checked = index in response,
              onCheckedChange = { checked ->
                val updatedResponse = if (checked) {
                  response + index
                } else {
                  response - index
                }
                onResponseChange(updatedResponse.toSet())
              }
            )
            Text(option)
          }
        }
      }

      QuestionType.TEXT_INPUT -> {
        var text by remember { mutableStateOf(TextFieldValue(userResponse as? String ?: "")) }

        TextField(
          value = text,
          onValueChange = {
            text = it
            onResponseChange(it.text)
          },
          modifier = Modifier.fillMaxWidth(),
          placeholder = { Text("Enter your answer...") }
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun QuizQuestionTrueFalsePreview() {
  QuizQuestion(
    question = Question(
      title = "ViewModel survives configuration changes and holds UI-related data?",
      options = listOf("True", "False"),
      answer = true,
      type = QuestionType.TRUE_FALSE
    ),
    userResponse = null,
    onResponseChange = {}
  )
}

@Preview(showBackground = true)
@Composable
fun QuizQuestionSingleChoicePreview() {
  QuizQuestion(
    question =     Question(
      title = "Which layout is recommended when building flexible, responsive UI components in Jetpack Compose?",
      options = listOf("ConstraintLayout", "LinearLayout", "Column", "RelativeLayout"),
      answer = 3,
      type = QuestionType.SINGLE_CHOICE
    ),
    userResponse = null,
    onResponseChange = {}
  )
}

@Preview(showBackground = true)
@Composable
fun QuizQuestionMultipleChoicePreview() {
  QuizQuestion(
    question = Question(
      title = "Which of the following are part of the Android Jetpack suite? (Choose all that apply)",
      options = listOf("DataStore", "Hilt", "BroadcastReceiver", "Navigation Component"),
      answer = setOf(0, 2, 3),
      type = QuestionType.MULTIPLE_CHOICE
    ),
    userResponse = null,
    onResponseChange = {}
  )
}

@Preview(showBackground = true)
@Composable
fun QuizQuestionTextInputPreview() {
  QuizQuestion(
    question =  Question(
      title = "What is the latest android OS version",
      answer = "15",
      type = QuestionType.TEXT_INPUT
    ),
    userResponse = null,
    onResponseChange = {}
  )
}
