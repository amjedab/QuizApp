package com.cricut.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import com.cricut.quizapp.model.Question
import com.cricut.quizapp.model.QuestionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuizViewModel : ViewModel() {

  // Questions and their user responses
  private val _questions = listOf(
    Question(
      title = "ViewModel survives configuration changes and holds UI-related data?",
      options = listOf("True", "False"),
      answer = true,
      type = QuestionType.TRUE_FALSE
    ),
    Question(
      title = "Which layout is recommended when building flexible, responsive UI components in Jetpack Compose?",
      options = listOf("ConstraintLayout", "LinearLayout", "Column", "RelativeLayout"),
      answer = 3,
      type = QuestionType.SINGLE_CHOICE
    ),
    Question(
      title = "Which of the following are part of the Android Jetpack suite? (Choose all that apply)",
      options = listOf("DataStore", "Hilt", "BroadcastReceiver", "Navigation Component"),
      answer = setOf(0, 2, 3),
      type = QuestionType.MULTIPLE_CHOICE
    ),
    Question(
      title = "What is the latest android OS version",
      answer = "15",
      type = QuestionType.TEXT_INPUT
    )
  )

  // Mutable StateFlows to hold state
  private val _currentIndex = MutableStateFlow(0)
  private val _userResponses = MutableStateFlow<List<Any?>>(List(_questions.size) { null })

  val currentIndex: StateFlow<Int> = _currentIndex
  val userResponses: StateFlow<List<Any?>> = _userResponses

  // Current question derived from the index
  val currentQuestion: StateFlow<Question>
    get() = MutableStateFlow(_questions[_currentIndex.value])

  // Functions for navigating questions
  fun nextQuestion() {
    if (_currentIndex.value < _questions.size - 1) {
      _currentIndex.value++
    }
  }

  fun previousQuestion() {
    if (_currentIndex.value > 0) {
      _currentIndex.value--
    }
  }

  // Function for recording a user response
  fun setUserResponse(response: Any?) {
    _userResponses.value = _userResponses.value.toMutableList().also {
      it[_currentIndex.value] = response
    }
  }

  fun getUserResponse(questionIndex: Int): Any? {
    return _userResponses.value[questionIndex]
  }

}
