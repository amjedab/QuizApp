package com.cricut.quizapp.model

data class Question(
  val title: String,
  val options: List<String> = emptyList(),
  val answer: Any,
  val type: QuestionType,
) {

  fun isValidAnswer(selectedAnswer: Any): Boolean {
    return when (type) {
      QuestionType.TRUE_FALSE -> {
        answer is Boolean && selectedAnswer is Boolean && answer == selectedAnswer
      }

      QuestionType.SINGLE_CHOICE -> {
        answer is Int && selectedAnswer is Int && answer == selectedAnswer
      }

      QuestionType.MULTIPLE_CHOICE -> {
        answer is Set<*> && selectedAnswer is Set<*> && answer == selectedAnswer
      }

      QuestionType.TEXT_INPUT -> {
        answer is String && selectedAnswer is String &&
            answer.trim().equals(selectedAnswer.trim(), ignoreCase = true)
      }
    }
  }
}