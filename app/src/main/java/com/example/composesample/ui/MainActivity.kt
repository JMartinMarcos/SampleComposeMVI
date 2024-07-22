package com.example.composesample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.composesample.ui.navigation.AppNavigation
import com.example.composesample.ui.theme.ComposeSampleTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ComposeSampleTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {}
        ) { innerPadding ->
          Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.padding(innerPadding)) {
            AppNavigation()
          }
        }
      }
    }
  }
}

