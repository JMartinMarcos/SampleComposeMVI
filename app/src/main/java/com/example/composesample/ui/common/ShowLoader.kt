package com.example.composesample.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable

@Composable
fun ShowLoader(isLoading: Boolean) {
  AnimatedVisibility(visible = isLoading) {
    Progress()
  }
}