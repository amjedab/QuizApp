package com.cricut.quizapp.model

data class Question(
  val title: String,
  val options: List<String> = emptyList(),
  val answer: Any,
  val type: QuestionType,
)