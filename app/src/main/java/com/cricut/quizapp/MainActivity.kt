package com.cricut.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import com.cricut.quizapp.ui.screens.QuizScreen
import com.cricut.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      QuizAppTheme {
        Scaffold(
          topBar = {
            TopAppBar(
              colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
              ),
              title = {
                Text("Quiz App")
              }
            )
          },
          containerColor = MaterialTheme.colorScheme.background,
          modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
          Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            QuizScreen()
          }
        }
      }
    }
  }
}